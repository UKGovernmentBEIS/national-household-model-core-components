package uk.org.cse.nhm.spss.main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collections;

import uk.org.cse.nhm.spss.codegen.CodeGenerator;

/**
 * Entry point for code generator.
 * @author hinton
 *
 */
public class SPSSCodeGenerator {
	
	public static void main(final String[] args) throws FileNotFoundException, IOException {
		if (args.length != 3) {
			System.err.println("Usage: InterfaceGenerator <package> <output directory> <input directory>");
			return;
		}
		
		final CodeGenerator cg = new CodeGenerator(args[0]);
		cg.loadDirectory(new File(args[2]));
		cg.generate(new File(args[1]), "SurveyEntryImpl", "SurveyEntry", Collections.singleton("AACODE"));;
	}
}
