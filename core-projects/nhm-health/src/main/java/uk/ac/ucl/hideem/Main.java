package uk.ac.ucl.hideem;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;

import uk.org.cse.nhm.energycalculator.api.types.RegionType;
import uk.org.cse.nhm.energycalculator.api.types.BuiltFormType;

public class Main {

    //Temperature Calculations using Hamilton relation
    private static double calcSIT(final double eValue) {
        final double livingRoomSIT = (Constants.LR_SIT_CONSTS[4] + (Constants.LR_SIT_CONSTS[3] * Math.pow(eValue, 1)) + (Constants.LR_SIT_CONSTS[2] * Math.pow(eValue, 2))
                + (Constants.LR_SIT_CONSTS[1] * Math.pow(eValue, 3)) + (Constants.LR_SIT_CONSTS[0] * Math.pow(eValue, 4)));
        final double bedRoomSIT = (Constants.BR_SIT_CONSTS[4] + (Constants.BR_SIT_CONSTS[3] * Math.pow(eValue, 1)) + (Constants.BR_SIT_CONSTS[2] * Math.pow(eValue, 2))
                + (Constants.BR_SIT_CONSTS[1] * Math.pow(eValue, 3)) + (Constants.BR_SIT_CONSTS[0] * Math.pow(eValue, 4)));
        final double averageSIT = ((livingRoomSIT + bedRoomSIT) / 2);

        return averageSIT;
    }

    public static void main(final String[] args) throws IOException {
        final HealthModule module = new HealthModule();

        // load the people into here; keyed on house ID
        // format expected to be:
        // code, age, sex, smoker
        final ListMultimap<String, Person> people = ArrayListMultimap.create();

        System.out.println("Reading people from " + args[0]);

        for (final Map<String, String> row : CSV.mapReader(Files.newBufferedReader(Paths.get(args[0]), StandardCharsets.UTF_8))) {
            final Person p = Person.readPerson(row);
            people.put(row.get("code"), p);
        }

        // now process the houses
        System.out.println("Reading house data from " + args[1]);

        try (final PrintWriter exposuresOut = new PrintWriter(args[2]);
                final PrintWriter qalysOut = new PrintWriter(args[3]);
                final PrintWriter morbQalysOut = new PrintWriter(args[4]);
                final PrintWriter costsOut = new PrintWriter(args[5]);) {

            for (final Map<String, String> row : CSV.mapReader(Files.newBufferedReader(Paths.get(args[1]), StandardCharsets.UTF_8))) {
                // Should get the totals here

                final double e1 = Double.parseDouble(row.get("e1"));
                final double e2 = Double.parseDouble(row.get("e2"));
                //or if e-value
                final double t1 = calcSIT(e1);
                final double t2 = calcSIT(e2);

                //dirty region conversion
                int nhmRegionNo = 0;
                if (Integer.parseInt(row.get("gor_ehs")) == 1) {
                    nhmRegionNo = 2;
                } else if (Integer.parseInt(row.get("gor_ehs")) == 2) {
                    nhmRegionNo = 4;
                } else if (Integer.parseInt(row.get("gor_ehs")) == 3) {
                    nhmRegionNo = 12;
                } else if (Integer.parseInt(row.get("gor_ehs")) == 4) {
                    nhmRegionNo = 3;
                } else if (Integer.parseInt(row.get("gor_ehs")) == 5) {
                    nhmRegionNo = 5;
                } else if (Integer.parseInt(row.get("gor_ehs")) == 6) {
                    nhmRegionNo = 6;
                } else if (Integer.parseInt(row.get("gor_ehs")) == 7) {
                    nhmRegionNo = 8;
                } else if (Integer.parseInt(row.get("gor_ehs")) == 8) {
                    nhmRegionNo = 10;
                } else if (Integer.parseInt(row.get("gor_ehs")) == 9) {
                    nhmRegionNo = 9;
                } else if (Integer.parseInt(row.get("gor_ehs")) == 10) {
                    nhmRegionNo = 7;
                }

                //TODO: This is wrongly matched up NHM matching
                final RegionType region = getRegionTypefromGorEHSNumber(nhmRegionNo);

                final HealthOutcome outcome = module.effectOf(
                        CumulativeHealthOutcome.factory(Integer.parseInt(row.get("horizon"))),
                        // get fields from row here
                        t1,
                        t2,
                        Double.parseDouble(row.get("p1")),
                        Double.parseDouble(row.get("p2")),
                        0,
                        0,
                        BuiltFormType.valueOf(row.get("form")),
                        Double.parseDouble(row.get("floor_area")),
                        region,
                        Integer.parseInt(row.get("level")),
                        Boolean.valueOf(row.get("extract")),
                        Boolean.valueOf(row.get("trickle")),
                        Boolean.valueOf(row.get("extract2")),
                        Boolean.valueOf(row.get("trickle2")),
                        Boolean.valueOf(row.get("dblglazing80pctplus")),
                        // todo:
                        Boolean.valueOf(row.get("dblglazing80pctplus")),
                        people.get(row.get("code")));

                //put outputs into files
                exposuresOut.print(outcome.printExposures(row.get("code")));
                qalysOut.print(outcome.printQalys(row.get("code")));
                morbQalysOut.print(outcome.printMorbQalys(row.get("code")));
                costsOut.print(outcome.printCost(row.get("code")));

                // this doesn't work any more because I broke it.
            }
        };
    }

    private static RegionType getRegionTypefromGorEHSNumber(int nhmRegionNo) {
        return RegionType.values()[nhmRegionNo];
    }
}
