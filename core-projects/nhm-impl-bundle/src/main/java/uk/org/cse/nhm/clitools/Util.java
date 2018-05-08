package uk.org.cse.nhm.clitools;

import java.nio.file.Path;
import java.nio.file.Paths;

import com.larkery.jasb.sexp.Location;
import com.larkery.jasb.sexp.errors.BaseErrorHandler;
import com.larkery.jasb.sexp.errors.ErrorCollector;
import com.larkery.jasb.sexp.errors.IErrorHandler;
import com.larkery.jasb.sexp.parse.DataSourceSnapshot;

import uk.org.cse.nhm.ipc.api.tasks.IScenarioSnapshot;
import uk.org.cse.nhm.ipc.api.tasks.impl.ScenarioSnapshot;

public class Util {

    public static IErrorHandler errors(final Path base) {
        return new BaseErrorHandler() {
            @Override
            public void handle(final IError e) {
                final Location loc = e.getLocation();
                final Path p = base.relativize(Paths.get(loc.sourceLocation.name));

                System.out.println(String.format("%s:%d:%d:%s", p,
                        loc.sourceLocation.line, loc.sourceLocation.column,
                        e.getMessage()));
            }
        };
    }

    public static IScenarioSnapshot loadSnapshot(final Path path, final Path base) {
        final ErrorCollector ec = new ErrorCollector();
        return new ScenarioSnapshot(DataSourceSnapshot.copy(new PathDataSource(path), ec), ec.getErrors());
    }
}
