package uk.org.cse.nhm.clitools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.io.input.BOMInputStream;

import com.larkery.jasb.sexp.Node;
import com.larkery.jasb.sexp.Seq;
import com.larkery.jasb.sexp.errors.IErrorHandler;
import com.larkery.jasb.sexp.parse.IDataSource;

public class PathDataSource implements IDataSource<Path> {

    private final Path root;

    public PathDataSource(Path root) {
        super();
        this.root = root;
    }

    @Override
    public Reader open(Path arg0) throws IOException {
        final BOMInputStream stream = new BOMInputStream(Files.newInputStream(arg0));
        final BufferedReader r = new BufferedReader(new InputStreamReader(stream,
                stream.getBOMCharsetName() == null ? "UTF-8"
                : stream.getBOMCharsetName()
        ));
        return r;
    }

    @Override
    public Path resolve(Seq arg0, IErrorHandler arg1) {
        final Path base = Paths.get(arg0.getLocation().name);
        final StringBuffer sb = new StringBuffer();
        for (final Node n : arg0.getTail()) {
            if (sb.length() > 0) {
                sb.append(" ");
            }
            sb.append(String.valueOf(n));
        }
        return base.getParent().resolve(sb.toString());
    }

    @Override
    public Path root() {
        return root;
    }

}
