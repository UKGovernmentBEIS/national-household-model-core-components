package uk.org.cse.nhm.ipc.api.tasks.impl;

import java.io.IOException;
import java.io.Reader;
import java.util.Collections;
import java.util.List;

import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;
import com.larkery.jasb.sexp.Delim;
import com.larkery.jasb.sexp.ISExpression;
import com.larkery.jasb.sexp.ISExpressionVisitor;
import com.larkery.jasb.sexp.Location;
import com.larkery.jasb.sexp.Node;
import com.larkery.jasb.sexp.Seq;
import com.larkery.jasb.sexp.SimplePrinter;
import com.larkery.jasb.sexp.errors.IErrorHandler;
import com.larkery.jasb.sexp.errors.IErrorHandler.IError;
import com.larkery.jasb.sexp.errors.JasbErrorException;
import com.larkery.jasb.sexp.parse.DataSourceSnapshot;
import com.larkery.jasb.sexp.parse.IDataSource;
import com.larkery.jasb.sexp.parse.IMacro;
import com.larkery.jasb.sexp.parse.StandardSource;
import com.larkery.jasb.sexp.parse.StandardSource.Expansion;

import uk.org.cse.nhm.ipc.api.tasks.IScenarioSnapshot;
import uk.org.cse.nhm.macros.ExtraMacros;

@AutoProperty
public class ScenarioSnapshot implements IScenarioSnapshot, IDataSource<String> {

    private final DataSourceSnapshot snapshot;

    private final List<IError> problems;

    @JsonCreator
    public ScenarioSnapshot(
            @JsonProperty("fragments") final DataSourceSnapshot snapshot,
            @JsonProperty("problems") final List<IError> problems) {
        this.snapshot = snapshot;
        this.problems = ImmutableList.copyOf(problems);
    }

    public DataSourceSnapshot getSnapshot() {
        return snapshot;
    }

    @Override
    public void accept(final ISExpressionVisitor visitor) {
        get(root(), IErrorHandler.RAISE).accept(visitor);
    }

    @Override
    public List<IError> getProblems() {
        return problems;
    }

    @JsonIgnore
    @Override
    public ISExpression getUntemplated() {
        return StandardSource.createUntemplated(this).get(root(), IErrorHandler.NOP);
    }

    @JsonIgnore
    public ISExpression get(final String address, final IErrorHandler errors) {
        return StandardSource.create(this, ExtraMacros.DEFAULT).get(address, errors);
    }

    @Override
    public ISExpression withErrorHandler(final IErrorHandler collector) {
        return get(root(), collector);
    }

    @Override
    public String toString() {
        return Pojomatic.toString(this);
    }

    @Override
    public boolean equals(final Object obj) {
        return Pojomatic.equals(this, obj);
    }

    @Override
    public int hashCode() {
        return Pojomatic.hashCode(this);
    }

    @JsonIgnore
    @Override
    public String resolve(Seq relation, IErrorHandler errors) {
        return snapshot.resolve(relation, errors);
    }

    @JsonIgnore
    @Override
    public String root() {
        return snapshot.root();
    }

    @JsonIgnore
    @Override
    public Reader open(String resolved) throws IOException {
        return snapshot.open(resolved);
    }

    static class QuickReturn extends RuntimeException {

        protected final String returnValue;
        private static final long serialVersionUID = 1L;

        QuickReturn(final String returnValue) {
            super();
            this.returnValue = returnValue;
        }
    }

    static class ExpansionWrapper implements IExpansion {

        private Expansion expand;

        public ExpansionWrapper(Expansion expand) {
            this.expand = expand;
        }

        @Override
        public List<IMacro> macros() {
            return expand.extraMacros;
        }

        @Override
        public List<Node> nodes() {
            return expand.nodes;
        }

        @Override
        public List<IError> errors() {
            return expand.errors;
        }
    }

    @JsonIgnore
    @Override
    public IExpansion expand() {
        return new ExpansionWrapper(StandardSource.expand(root(), this, ExtraMacros.DEFAULT));
    }

    @JsonIgnore
    @Override
    public Optional<String> getExpandedFirstElement() {
        try {
            // apologies here for using exceptions as flow control
            accept(new ISExpressionVisitor() {
                boolean afterHead = false;

                @Override
                public void open(final Delim arg0) {
                    afterHead = arg0 == Delim.Paren;
                }

                @Override
                public void locate(final Location arg0) {
                }

                @Override
                public void comment(final String arg0) {
                }

                @Override
                public void close(final Delim arg0) {
                    afterHead = false;
                }

                @Override
                public void atom(final String arg0) {
                    if (afterHead) {
                        throw new QuickReturn(arg0);
                    }
                }
            });
        } catch (final QuickReturn qr) {
            return Optional.of(qr.returnValue);
        } catch (final JasbErrorException jee) {
            throw jee;
        } catch (final Exception e) {
            return Optional.absent();
        }

        return Optional.absent();
    }

    public static IScenarioSnapshot fromString(String string) {
        return new ScenarioSnapshot(DataSourceSnapshot.just(string), Collections.<IError>emptyList());
    }

    @Override
    public DataSourceSnapshot contents() {
        return snapshot;
    }

    public static IScenarioSnapshot fromSExpression(ISExpression current) {
        return new ScenarioSnapshot(
                DataSourceSnapshot.just(
                        SimplePrinter.toString(current)
                ),
                Collections.<IError>emptyList());
    }
}
