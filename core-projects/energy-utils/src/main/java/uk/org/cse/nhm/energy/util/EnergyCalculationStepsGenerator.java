package uk.org.cse.nhm.energy.util;

import uk.org.cse.nhm.energycalculator.api.types.EnergyCalculationStep;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Inspects EnergyCalculationStep.java and generates the following:
 * 1. XEnergyCalculationStep.java
 * 2. MapEnum.energyCalculationstepMapping
 */
public class EnergyCalculationStepsGenerator {
    public static void main(final String[] args) {
        if (args.length > 0) {
            throw new UnsupportedOperationException("EnergyCalculationStepsGenerator doesn't support any arguments yet.");
        }

        final StringWriter enumStr = new StringWriter();
        final StringWriter mapStr = new StringWriter();
        final PrintWriter enumDef = new PrintWriter(enumStr);
        final PrintWriter mapDef = new PrintWriter(mapStr);


        writeEnumHead(enumDef);
        writeMapHead(mapDef);

        for (EnergyCalculationStep step : EnergyCalculationStep.values()) {
            if (!step.isSkipped()) {
                writeEnumValue(enumDef, step);
                writeMapValue(mapDef, step);
            }
        }

        writeEnumFoot(enumDef);
        writeMapFoot(mapDef);

        System.out.println(enumStr.toString());
        System.out.println(mapStr.toString());
    }

    private static void writeMapHead(PrintWriter mapDef) {
        mapDef.println("/** Generated by EnergyCalculationstepsGenerate.java */");
        mapDef.println("final static Map<XEnergyCalculationStep, EnergyCalculationStep> energyCalculationStepMapping = ImmutableMap.<XEnergyCalculationStep, EnergyCalculationStep>builder()");
    }

    private static void writeMapValue(PrintWriter mapDef, EnergyCalculationStep step) {
        mapDef.println(String.format(
                ".put(XEnergyCalculationStep.%s, EnergyCalculationStep.%s)",
                step.name(),
                step.name()
        ));
    }

    private static void writeMapFoot(PrintWriter mapDef) {
        mapDef.println(".build();");
    }

    private static void writeEnumHead(PrintWriter enumDef) {
        enumDef.println("package uk.org.cse.nhm.language.definition.enums;");
        enumDef.println();
        enumDef.println("import uk.org.cse.nhm.language.definition.Category;");
        enumDef.println("import uk.org.cse.nhm.language.definition.Category.CategoryType;");
        enumDef.println("import uk.org.cse.nhm.language.definition.Doc;");
        enumDef.println();
        enumDef.println("/** Generated by EnergyCalculationstepsGenerate.java */");
        enumDef.println("@Doc({");
        enumDef.println("\"Steps in the SAP 2012 worksheet http://www.bre.co.uk/filelibrary/SAP/2012/SAP-2012_9-92.pdf which can be output by the model.\",");
        enumDef.println("\"In the future, it may be extended to include steps from BREDEM 2012.\"");
        enumDef.println("})");
        enumDef.println("@Category(CategoryType.CATEGORIES)");
        enumDef.println("public enum XEnergyCalculationStep {");
    }

    private static void writeEnumValue(PrintWriter enumDef, EnergyCalculationStep step) {
        enumDef.println("@Doc({");

        if (step.sapLocation.isPresent()) {
            enumDef.println("\"" + step.sapLocation.get().toString() + "\",");
        }

        if (step.hasDefault()) {
            enumDef.println(String.format("\"Always has value %.1f in the NHM.\",", step.getDefault()));
        }

        enumDef.println("\"" + step.period.toString() + "\",");

        enumDef.println("\"" + step.conversion.toString() + "\"");

        enumDef.println("})");
        enumDef.println(step.name() + ",");
    }

    private static void writeEnumFoot(PrintWriter enumDef) {
        enumDef.println(";");
        enumDef.println("}");
    }
}
