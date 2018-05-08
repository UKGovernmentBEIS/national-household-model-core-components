package uk.org.cse.nhm.spss.impl;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Stack;

/**
 * This is a helper class which wraps an {@link InputStream} to provide
 * utilities for reading SAV file parts
 *
 * @author hinton
 *
 */
public class SavDataStream {
    // these are codes used in the compression table

    private static final int COMPRESSED_BLANKS = 254;
    private static final int INCOMPRESSIBLE = 253;
    private static final int STRING_BLOCK_SIZE = 8;

    private final InputStream stream;

    private final Charset charset = Charset.defaultCharset();

    private boolean reverseEndian = false;

    private final Stack<Integer> pushedBack = new Stack<Integer>();
    private boolean compressed;
    private double compressionBias;

    public SavDataStream(final InputStream stream) {
        this.stream = stream;
    }

    /**
     * This reads a string of variable length; apparently this is only really
     * used in one place
     *
     * @param padding the block size for the string (the number of bytes used
     * will be rounded up to a factor of this many)
     * @return
     * @throws IOException
     */
    public String readVarString(final int padding) throws IOException {
        final int length = readByte();

        final String result = readString(length);

        if ((length + 1) % padding != 0) {
            skip(padding - (length + 1) % padding);
        }

        return result.trim();
    }

    /**
     * Read a string of the given length from the stream
     *
     * @param size
     * @return
     * @throws IOException
     */
    public String readString(final int size) throws IOException {
        if (!pushedBack.isEmpty()) {
            throw new IOException("Cannot read a string if there are pushed back integers remaining");
        }
        final byte[] buffer = readBytes(size);
        return new String(buffer, charset);
    }

    /**
     * Read a single byte from the stream
     *
     * @return
     * @throws IOException
     */
    public int readByte() throws IOException {
        return stream.read();
    }

    /**
     * Read an SPSS int from the stream
     *
     * @return
     * @throws IOException
     */
    public int readInt() throws IOException {
        if (!pushedBack.isEmpty()) {
            return pushedBack.pop();
        }

        final byte[] buffer = readBytes(4);

        if (reverseEndian) {
            reverse(buffer);
        }

        int n = 0;
        for (int i = 0; i < 4; i++) {
            n |= (buffer[i] & 0xFF) << (i * 8);
        }
        return n;
    }

    /**
     * Used to fix byte order
     *
     * @param buffer
     */
    private void reverse(final byte[] buffer) {
        for (int i = 0; i < buffer.length / 2; i++) {
            final byte swap = buffer[i];
            buffer[i] = buffer[buffer.length - (i + 1)];
            buffer[buffer.length - (i + 1)] = swap;
        }
    }

    /**
     * Get a double-precision floating point from the backing stream
     *
     * @return
     * @throws IOException
     */
    public double readDouble() throws IOException {
        if (!pushedBack.isEmpty()) {
            throw new IOException("Cannot read a double if there are pushed back integers remaining");
        }
        final byte[] buffer = readBytes(8);

        if (reverseEndian) {
            reverse(buffer);
        }

        final long lvalue = (((long) (buffer[7]) << 56) + ((long) (buffer[6] & 0xFF) << 48) + ((long) (buffer[5] & 0xFF) << 40) + ((long) (buffer[4] & 0xFF) << 32)
                + ((long) (buffer[3] & 0xFF) << 24) + ((long) (buffer[2] & 0xFF) << 16) + ((long) (buffer[1] & 0xFF) << 8) + (buffer[0] & 0xFF));
        return (Double.longBitsToDouble(lvalue));
    }

    byte[] readBytes(final int len) throws IOException {
        final byte[] buffer = new byte[len];
        for (int i = 0; i < len; i++) {
            final int result = stream.read(buffer, i, 1);
            if (result == -1) {
                break;
            }
        }
        return buffer;
    }

    /**
     * Make the stream work big-endian
     */
    public void setBigEndian() {
        reverseEndian = true;
    }

    /**
     * Pass over i bytes of input
     *
     * @param i
     * @throws IOException
     */
    public void skip(final int i) throws IOException {
        stream.skip(i);
    }

    public void pushBack(final int nextTypeCode) {
        pushedBack.push(nextTypeCode);
    }

    /**
     * Tell the stream to use cluster compression on the
     * {@link #readNumericData()} and {@link #readStringData(int)} methods
     *
     * @param compressed
     * @param compressionBias
     */
    public void setCompressedData(final boolean compressed, final double compressionBias) {
        this.compressed = compressed;
        this.compressionBias = compressionBias;
    }

    int compressionClusterIndex = 8;
    byte[] compressionCluster = new byte[8];

    // read methods for data records
    public double readNumericData() throws IOException {
        if (compressed) {
            final int nextByte = advanceCluster();

            switch (nextByte) {
                case 0:
                    return readNumericData(); // recur
                case 252:
                    throw new IOException("Unexpected end of compressed data");
                case 253:
                    // uncompressed data directly follows
                    return readDouble();
                case 254: // zero value
                    return 0;
                case 255: // system missing value
                    return Double.NaN;
                default:
                    // a compressed value, done using the bias field
                    return nextByte - compressionBias;
            }
        } else {
            return readDouble();
        }
    }

    public String readStringData(int length) throws IOException {
        // strings are stored in blocks of 8 bytes.
        int blocksRemaining = ((length - 1) / STRING_BLOCK_SIZE) + 1;

        String result = "";

        while (blocksRemaining > 0) {
            result += readStringBlock(length);
            length -= STRING_BLOCK_SIZE;
            blocksRemaining--;
        }

        return result.trim();
    }

    private String readStringBlock(final int remaining) throws IOException {
        if (compressed) {
            final int next = advanceCluster();
            switch (next) {
                case 0:
                    return "";
                case INCOMPRESSIBLE:
                    // read uncompressed data
                    final int blockLength = Math.min(STRING_BLOCK_SIZE, remaining);
                    final String result = readString(blockLength);
                    if (remaining < STRING_BLOCK_SIZE) {
                        skip(STRING_BLOCK_SIZE - remaining);
                    }
                    return result;
                case COMPRESSED_BLANKS:
                    return "";
                default:
                    throw new IOException("Unexpected code in compressed string block " + next);
            }
        } else {
            final int readCount = Math.min(STRING_BLOCK_SIZE, remaining);
            final String result = readString(readCount);
            return result;
        }
    }

    private int advanceCluster() throws IOException {
        // read next compression cluster if required
        if (compressionClusterIndex >= compressionCluster.length) {
            compressionCluster = readBytes(8);
            compressionClusterIndex = 0;
        }

        return 0xFF & (compressionCluster[compressionClusterIndex++]);
    }
}
