package uk.org.cse.nhm.spss.main;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import uk.org.cse.nhm.spss.SavInputStream;
import uk.org.cse.nhm.spss.SavVariable;
import uk.org.cse.nhm.spss.SavVariableType;
import uk.org.cse.nhm.spss.impl.SavInputStreamImpl;

public class DumpMetadata {

    /**
     * @param args
     * @throws IOException
     * @throws FileNotFoundException
     */
    public static void main(final String[] args) throws FileNotFoundException, IOException {
        final SavInputStream sis = new SavInputStreamImpl(new FileInputStream(args[0]), true);

        for (final SavVariable variable : sis.getMetadata().getVariables()) {
            if (variable.getType() == SavVariableType.STRING_CONTINUATION) {
                continue;
            }
            System.out.println(variable);
        }

        /*
		while (sis.hasNext()) {
			final SavEntry e = sis.next();
			System.out.println(e);
		}
         */
    }

}
