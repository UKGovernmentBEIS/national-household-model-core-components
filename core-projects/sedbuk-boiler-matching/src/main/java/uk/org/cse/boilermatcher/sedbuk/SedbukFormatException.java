package uk.org.cse.boilermatcher.sedbuk;

import java.io.IOException;

/**
 * @since 1.0
 */
@SuppressWarnings("serial")
public class SedbukFormatException extends IOException {

    /**
     * @since 1.0
     */
    public SedbukFormatException(String string) {
        super(string);
    }

    /**
     * @since 1.0
     */
    public void setInputLine(String thisLine) {

    }

    /**
     * @since 1.0
     */
    public void setLineNumber(int lineCounter) {

    }
}
