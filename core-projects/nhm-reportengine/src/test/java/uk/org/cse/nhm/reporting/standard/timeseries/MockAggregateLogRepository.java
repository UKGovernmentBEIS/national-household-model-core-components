package uk.org.cse.nhm.reporting.standard.timeseries;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.joda.time.DateTime;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableTable;
import com.google.common.collect.Table;

import uk.org.cse.nhm.logging.logentry.AggregateLogEntry;

public class MockAggregateLogRepository {

    private static final String GROUP = "group";
    private static Set<String> empty = Collections.emptySet();

    public List<AggregateLogEntry> findByScenarioExecutionId(String scenarioExecutionId) {
        Builder<AggregateLogEntry> builder = ImmutableList.builder();
        builder.addAll(meaninglessReport());
        builder.addAll(multiValuedReport());
        return builder.build();
    }

    private Iterable<? extends AggregateLogEntry> multiValuedReport() {
        Builder<AggregateLogEntry> builder = ImmutableList.<AggregateLogEntry>builder();
        String scenario = "test";
        String report = "insulation report";

        com.google.common.collect.ImmutableTable.Builder<String, String, Double> tableBuilder = ImmutableTable.builder();
        tableBuilder.put("Cavity Insulated", "Count", 0d);
        tableBuilder.put("Cavity Insulated", "Average Energy Usage", 0d);
        tableBuilder.put("Cavity Uninsulated", "Count", 10000000d);
        tableBuilder.put("Cavity Uninsulated", "Average Energy Usage", 40d);
        tableBuilder.put("Solid Insulated", "Count", 0d);
        tableBuilder.put("Solid Insulated", "Average Energy Usage", 0d);
        tableBuilder.put("Solid Uninsulated", "Count", 6000000d);
        tableBuilder.put("Solid Uninsulated", "Average Energy Usage", 50d);
        addMultipleValuesForGroups(builder, scenario, report, ImmutableSet.of("Stock Creator"), 1950, tableBuilder.build());

        tableBuilder = ImmutableTable.builder();
        tableBuilder.put("Cavity Insulated", "Count", 1000000d);
        tableBuilder.put("Cavity Insulated", "Average Energy Usage", 30d);
        tableBuilder.put("Cavity Uninsulated", "Count", 10000000d);
        tableBuilder.put("Cavity Uninsulated", "Average Energy Usage", 50d);
        tableBuilder.put("Solid Insulated", "Count", 0d);
        tableBuilder.put("Solid Insulated", "Average Energy Usage", 0d);
        tableBuilder.put("Solid Uninsulated", "Count", 7000000d);
        tableBuilder.put("Solid Uninsulated", "Average Energy Usage", 55d);
        addMultipleValuesForGroups(builder, scenario, report, empty, 1960, tableBuilder.build());

        tableBuilder = ImmutableTable.builder();
        tableBuilder.put("Cavity Insulated", "Count", 3000000d);
        tableBuilder.put("Cavity Insulated", "Average Energy Usage", 35d);
        tableBuilder.put("Cavity Uninsulated", "Count", 9000000d);
        tableBuilder.put("Cavity Uninsulated", "Average Energy Usage", 55d);
        tableBuilder.put("Solid Insulated", "Count", 1000000d);
        tableBuilder.put("Solid Insulated", "Average Energy Usage", 40d);
        tableBuilder.put("Solid Uninsulated", "Count", 7000000d);
        tableBuilder.put("Solid Uninsulated", "Average Energy Usage", 60d);
        addMultipleValuesForGroups(builder, scenario, report, empty, 1974, tableBuilder.build());

        tableBuilder = ImmutableTable.builder();
        tableBuilder.put("Cavity Insulated", "Count", 7000000d);
        tableBuilder.put("Cavity Insulated", "Average Energy Usage", 40d);
        tableBuilder.put("Cavity Uninsulated", "Count", 6000000d);
        tableBuilder.put("Cavity Uninsulated", "Average Energy Usage", 60d);
        tableBuilder.put("Solid Insulated", "Count", 2000000d);
        tableBuilder.put("Solid Insulated", "Average Energy Usage", 45d);
        tableBuilder.put("Solid Uninsulated", "Count", 6000000d);
        tableBuilder.put("Solid Uninsulated", "Average Energy Usage", 65d);
        addMultipleValuesForGroups(builder, scenario, report, ImmutableSet.of("Cavity insulation subsidies"), 1980, tableBuilder.build());

        return builder.build();
    }

    private List<AggregateLogEntry> meaninglessReport() {
        Builder<AggregateLogEntry> builder = ImmutableList.<AggregateLogEntry>builder();
        String scenario = "test";
        String var = "frequency";
        String report = "fuel report";

        addSingleValueForGroups(builder, scenario, report, ImmutableSet.of("Stock Creator"), 1950, var, ImmutableMap.of(
                "Gas", 1d,
                "Oil", 2d,
                "Electricity", 3d,
                "Biomass", 2d,
                "Coal", 5d
        ));

        addSingleValueForGroups(builder, scenario, report, empty, 1960, var, ImmutableMap.of(
                "Gas", 2d,
                "Oil", 3d,
                "Electricity", 4d,
                "Biomass", 1d,
                "Coal", 6d
        ));

        addSingleValueForGroups(builder, scenario, report, ImmutableSet.of("Oil crisis"), 1970, var, ImmutableMap.of(
                "Gas", 2d,
                "Oil", 1d,
                "Electricity", 4d,
                "Biomass", 1d,
                "Coal", 8d
        ));

        addSingleValueForGroups(builder, scenario, report, empty, 1980, var, ImmutableMap.of(
                "Gas", 2d,
                "Oil", 2d,
                "Electricity", 5d,
                "Biomass", 1d,
                "Coal", 7d
        ));

        return builder.build();
    }

    private void addSingleValueForGroups(Builder<AggregateLogEntry> builder, String scenario, String report, Set<String> causes, int year, String var, Map<String, Double> valuesByGroup) {

        for (String group : valuesByGroup.keySet()) {
            builder.add(new AggregateLogEntry(
                    report,
                    causes,
                    ImmutableMap.of(GROUP, group),
                    new DateTime(year, 0, 0, 0, 0),
                    ImmutableMap.of(var, valuesByGroup.get(group)
                    )));
        }
    }

    private void addMultipleValuesForGroups(Builder<AggregateLogEntry> builder, String scenario, String report, Set<String> causes, int year, Table<String, String, Double> valuesByGroup) {

        for (String group : valuesByGroup.rowKeySet()) {
            builder.add(new AggregateLogEntry(
                    report,
                    causes,
                    ImmutableMap.of(GROUP, group),
                    new DateTime(year, 0, 0, 0, 0),
                    ImmutableMap.copyOf(valuesByGroup.rowMap().get(group))));
        }
    }
}
