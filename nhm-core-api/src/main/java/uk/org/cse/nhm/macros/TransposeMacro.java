package uk.org.cse.nhm.macros;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import com.larkery.jasb.sexp.Comment;
import com.larkery.jasb.sexp.ISExpression;
import com.larkery.jasb.sexp.Invocation;
import com.larkery.jasb.sexp.Node;
import com.larkery.jasb.sexp.SExpressions;
import com.larkery.jasb.sexp.Seq;
import com.larkery.jasb.sexp.errors.IErrorHandler;
import com.larkery.jasb.sexp.errors.UnfinishedExpressionException;
import com.larkery.jasb.sexp.parse.IMacroExpander;
import com.larkery.jasb.sexp.parse.MacroModel;
import com.larkery.jasb.sexp.parse.SimpleMacro;

public class TransposeMacro extends SimpleMacro {
    final String name;
    
    public TransposeMacro(final String name) {
        this.name = name;
    }

    @Override
	public Set<String> getAllowedArgumentNames() {
        return Collections.emptySet();
	}

	@Override
	public int getMaximumArgumentCount() {
		return Integer.MAX_VALUE;
	}

	@Override
	public int getMinimumArgumentCount() {
		return 1;
	}

	@Override
	public String getName() {
		return name;
	}

    @Override
    public ISExpression doTransform(final Invocation in, final IMacroExpander e, final IErrorHandler err) {
        int length = -1;
        final List<Seq.Builder> out = new LinkedList<>();
        for (final Node n_ : in.remainder) {
            try {
                final List<Node> ns = Node.copyAll(e.expand(n_));
                for (final Node n : ns) {
                    if (n instanceof Comment) continue;
                    if (n instanceof Seq) {
                        final Seq s = (Seq) n;
                        final List<Node> parts = s.exceptComments();
                        if (length == -1) {
                            length = parts.size();
                            for (final Node p : parts) {
                                final Seq.Builder b = Seq.builder(s.getLocation(), s.getDelimeter());
                                b.add(p);
                                out.add(b);
                            }
                        } else {
                            if (length != parts.size()) {
                                err.error(n, "%s requires a rectangular input (all the input lists must be of equal length)", getName());
                            } else {
                                final Iterator<Seq.Builder> bs = out.iterator();
                                for (final Node p : parts) {
                                    bs.next().add(p);
                                }
                            }
                        }
                    } else {
                        err.error(n, "%s requires a sequence of lists to operate on.", getName());
                    }
                }
            } catch (final UnfinishedExpressionException uee) {
                err.handle(uee.getError());
            }
        }

        final List<ISExpression> expressions = new LinkedList<>();
        for (final Seq.Builder b : out) {
            expressions.add(b.build(in.node.getEndLocation()));
        }
        return SExpressions.inOrder(expressions);
    }
    
	@Override
	public Set<String> getRequiredArgumentNames() {
		return Collections.emptySet();
	}

    	@Override
	public MacroModel getModel() {
		return MacroModel.builder()
            .desc("The transpose macro transposes a sequence of lists, so if given two three-element lists, it will produce three two-element lists instead, the first containing the first element of each input list, the second the second elements, and so on.")
            .desc("All of the inputs will be fully expanded before the table is transposed.")
            .pos()
            .remainder("A series of lists to transpose.")
            .and()
            .build();
	}

    
}


