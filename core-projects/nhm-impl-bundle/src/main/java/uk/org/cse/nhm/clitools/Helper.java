package uk.org.cse.nhm.clitools;

import com.larkery.jasb.io.IModel;
import com.larkery.jasb.io.IModel.IArgument;
import com.larkery.jasb.io.IModel.IElement;
import com.larkery.jasb.io.IModel.IInvocationModel;
import com.larkery.jasb.io.impl.JASB;

import uk.org.cse.nhm.language.sexp.Defaults;

public class Helper {
    public static final String USAGE = "\n\tGenerate an s-expression description of the model language, for emacs";
    public static void main(String[] args) {
        Validator.disableLogging();
        final JASB jasb = Defaults.CONTEXT;
        final IModel model = jasb.getModel();
        /*        System.out.println("(");
        System.out.println("(primitives");
        for (final IAtomModel atom : model.getAtoms()) {
            System.out.println("(" + atom.getName().replace(" ", "-"));
            System.out.println(")");
        }
        System.out.println(")");
        System.out.println("(terms");*/
        System.out.println("(");
        for (final IInvocationModel inv : model.getInvocations()) {
            final StringBuffer sb = new StringBuffer();

            sb.append("\t(");
            sb.append(inv.getName());
            for (final IArgument arg : inv.getArguments()) {
                sb.append("\n\t\t");
                sb.append("(");
                if (arg.isNamedArgument()) {
                    sb.append(arg.getName().get() + ":");
                } else if (arg.isPositionalArgument()) {
                    sb.append(arg.getPosition().get());
                } else if (arg.isRemainderArgument()) {
                    sb.append("&rest");
                }

                sb.append(arg.isMultiple() ?
                          " multiple" : " single");
                
                sb.append(" (");

                for (final IElement element : arg.getLegalValues()) {
                    sb.append(element.getName().replace(" ", "-"));
                    sb.append(" ");
                }
                
                sb.append("))");
            }
            sb.append(")");

            System.out.println(sb.toString());
        }
        System.out.println(")");
    }
}

