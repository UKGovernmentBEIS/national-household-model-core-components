package uk.org.cse.nhm.language.sexp;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;

import com.larkery.jasb.sexp.Atom;
import com.larkery.jasb.sexp.Delim;
import com.larkery.jasb.sexp.Node;
import com.larkery.jasb.sexp.PrettyPrinter;
import com.larkery.jasb.sexp.Seq;

/**
 * Should convert scenarios from 4.1 to 4.2
 *
 * @author hinton
 *
 */
public class ScenarioConverter4_1_to_4_2 extends ScenarioConverter {

    abstract class FlagsRule extends OutputRule {

        @Override
        public void handleAttribute(final String attribute, final InvocationBuilder builder) {
            final String[] parts = attribute.split("\\s+");
            final Seq.Builder seq = Seq.builder(null, Delim.Bracket);

            for (final String part : parts) {
                if (part.isEmpty()) {
                    continue;
                }
                seq.add(part.trim());
            }
            final Seq build = seq.build(null);
            if (build.size() == 1) {
                handleNode(builder, build.getHead());
            } else {
                handleNode(builder, build);
            }
        }
    }

    class FlattenRule extends OutputRule {

        final OutputRule delegate;

        FlattenRule(final OutputRule delegate) {
            super();
            this.delegate = delegate;
        }

        @Override
        protected void handleNode(final InvocationBuilder builder, final Node node) {
            if (node instanceof Seq) {
                for (final Node child : ((Seq) node)) {
                    handleNode(builder, child);
                }
            } else {
                delegate.handleNode(builder, node);
            }
        }
    }

    OutputRule namedFlags(final String name) {
        return new FlagsRule() {
            @Override
            protected void handleNode(final InvocationBuilder builder, final Node node) {
                builder.named(name, node);
            }
        };
    }

    OutputRule remainderFlags() {
        return new FlagsRule() {
            @Override
            protected void handleNode(final InvocationBuilder builder, final Node node) {
                if (node instanceof Seq) {
                    for (final Node child : ((Seq) node)) {
                        builder.remainder(child);
                    }
                } else {
                    builder.remainder(node);
                }
            }
        };
    }

    public ScenarioConverter4_1_to_4_2() {
        final String[] uk_org_cse_nhm_language_definition_batch_inputs_XInputs = {
            "gaussian", "concat", "uniform", "zip", "table", "repetitions",
            "cross", "uniform-integers", "bounded", "triangular",
            "discrete", "range"};
        final String[] uk_org_cse_nhm_language_definition_action_XDwellingAction = {
            "register.add", "measure.solar-dhw",
            "measure.change-boiler-efficiency", "choice", "register.set",
            "counterfactual.weather", "action.set-living-area-fraction",
            "measure.heat-pump", "measure.point-of-use-hot-water",
            "measure.wall-insulation", "measure.ref", "measure.with-cost",
            "action.set-tariffs", "finance.with-subsidy",
            "measure.loft-insulation", "action.all-of",
            "measure.combi-boiler", "measure.break-boiler",
            "action.any-of", "action.reimpute", "measure.standard-boiler",
            "finance.with-loan", "action.do-nothing", "action.probe",
            "let", "sap.occupancy", "finance.fully", "action.set-wall-u",
            "action.set-heating-schedule", "yield", "measure.room-heater",
            "measure.district-heating", "action.set-heating-temperatures",
            "counterfactual.carbon", "action.flag"};
        final String[] uk_org_cse_nhm_language_definition_function_XFunction = {
            "house.loans.balance-paid", "npv",
            "house.loft-insulation-thickness-is", "sum",
            "house.is-on-tariff", "house.meter-reading", "any",
            "function.quadratic", "house.main-heating-fuel", "max",
            "function.let", "function.time-series", "probe.npv",
            "house.balance", "house.loans.balance-outstanding", "number",
            "size.m2", "house.tariff", "house.region", "house.u-value",
            "house.built-form-is", "function.simple-pricing",
            "house.peak-load", "difference", "house.tenure", "pow",
            "house.morphology", "product", "house.fuel-cost-index",
            "house.buildyear-is", "house.has-boiler", "snapshot.delta",
            "function.stepped-pricing", "function.steps",
            "house.tenure-is", "house.buildyear", "all", "none", "min",
            "register.get", "house.static-property-is", "house.emissions",
            "house.main-heating-fuel-is", "house.predominant-wall-type",
            "house.annual-maintenance", "house.morphology-is",
            "function.yield", "size.kw", "house.has-wall", "under",
            "ratio", "house.loft-insulation-thickness",
            "obligations.predict", "house.region-is", "house.age-is",
            "cost.capex", "get", "house.value-is", "house.test-flag",
            "house.fuel-cost", "house.flags", "exp", "cost.sum",
            "house.energy-use", "house.is-suitable", "function.probe",
            "house.built-form", "function.case", "sim.year-is"};
        final String[] uk_org_cse_nhm_language_definition_group_XGroup = {
            "group.random-sample", "group.filter", "group.union",
            "group.reference", "group.difference",
            "group.probabilistic-membership", "group.all",
            "group.intersection"};
        final String[] uk_org_cse_nhm_language_definition_fuel_XTariffBase = {
            "tariff.ref", "tariff.simple", "tariff"};
        final String[] uk_org_cse_nhm_language_definition_function_bool_XBoolean = {
            "house.region-is", "house.age-is", "house.tenure-is",
            "house.loft-insulation-thickness-is", "house.value-is",
            "house.is-on-tariff", "all", "none", "house.test-flag", "any",
            "house.built-form-is", "house.static-property-is",
            "house.has-boiler", "house.buildyear-is",
            "house.main-heating-fuel-is", "house.morphology-is",
            "house.flags", "house.is-suitable", "house.has-wall",
            "sim.year-is"};
        final String[] uk_org_cse_nhm_language_definition_function_num_XNumber = {
            "function.steps", "house.loans.balance-paid", "npv",
            "house.buildyear", "sum", "house.meter-reading", "min",
            "register.get", "function.quadratic", "house.emissions",
            "house.annual-maintenance", "max", "function.let",
            "function.yield", "size.kw", "function.time-series", "under",
            "ratio", "house.balance", "house.loft-insulation-thickness",
            "obligations.predict", "house.loans.balance-outstanding",
            "cost.capex", "size.m2", "get", "number", "house.u-value",
            "function.simple-pricing", "house.peak-load", "difference",
            "pow", "house.fuel-cost", "product", "house.fuel-cost-index",
            "snapshot.delta", "function.stepped-pricing", "exp",
            "cost.sum", "house.energy-use", "function.probe",
            "function.case"};
        final String[] uk_org_cse_nhm_language_definition_exposure_XSampler = {
            "sample.count", "sample.bernoulli", "sample.proportion"};
        final String[] uk_org_cse_nhm_language_definition_reporting_aggregate_XAggregation = {
            "aggregate.count", "aggregate.sum", "aggregate.mean"};
        final String[] uk_org_cse_nhm_language_definition_reporting_aggregate_XDivision = {
            "division.by-case", "division.by-combination",
            "division.by-group"};
        final String[] uk_org_cse_nhm_language_definition_reporting_modes_XReportMode = {
            "mode.sum", "mode.dates", "mode.integral", "mode.on-change"};
        final String[] uk_org_cse_nhm_language_definition_action_measure_heating_XRoomHeaterMeasure = {"measure.room-heater"};
        final String[] uk_org_cse_nhm_language_definition_action_measure_hotwater_XPointOfUseDHWMeasure = {"measure.point-of-use-hot-water"};
        final String[] uk_org_cse_nhm_language_definition_action_XAction = {
            "register.add", "measure.solar-dhw",
            "measure.change-boiler-efficiency", "choice", "register.set",
            "counterfactual.weather", "action.set-living-area-fraction",
            "measure.heat-pump", "measure.point-of-use-hot-water",
            "measure.wall-insulation", "measure.ref", "measure.with-cost",
            "action.set-tariffs", "finance.with-subsidy", "action.ref",
            "action.demolish", "measure.loft-insulation", "action.all-of",
            "measure.combi-boiler", "measure.break-boiler",
            "action.any-of", "action.reimpute", "measure.standard-boiler",
            "action.construct", "finance.with-loan", "action.do-nothing",
            "action.probe", "let", "sap.occupancy", "finance.fully",
            "action.set-wall-u", "action.set-heating-schedule", "yield",
            "measure.room-heater", "measure.district-heating",
            "action.set-heating-temperatures", "counterfactual.carbon",
            "action.flag"};
        final String[] uk_org_cse_nhm_language_definition_exposure_XExposure = {
            "schedule.repeat", "schedule.time-series",
            "schedule.on-group-entry"};
        final String[] uk_org_cse_nhm_language_definition_action_choices_XChoiceSelector = {
            "select.maximum", "select.weighted", "select.filter",
            "select.minimum"};
        final String[] uk_org_cse_nhm_language_definition_fuel_XSimpleTariff_XSimpleTariffFuel = {"function.simple-pricing"};
        final String[] uk_org_cse_nhm_language_definition_action_imputation_XImputationSchema = {"schema.rdsap"};
        final String[] uk_org_cse_nhm_language_definition_context_technology_XTechnology = {
            "technology.gshp", "technology.ashp",
            "technology.cavity-insulation", "technology.loft-insulation",
            "technology.wet-heating-system",
            "technology.external-wall-insulation",
            "technology.district-heating", "technology.boiler",
            "technology.internal-wall-insulation",
            "technology.solar-hot-water"};
        final String[] uk_org_cse_nhm_language_definition_reporting_modes_XDateMode_XReportingDates = {
            "between", "scenario-end", "scenario-start", "once"};

        final String[] rootElements = {
            "policy",
            "context.tariffs",
            "report.national-power",
            "report.aggregate",
            "report.dwellings",
            "report.houses-by-region",
            "report.global-accounts",
            "context.named-actions",
            "report.measure-installations",
            "context.weather",
            "report.fuel-costs",
            "report.transactions",
            "report.group-transitions",
            "report.state",
            "context.energy-constants",
            "context.carbon-factors",
            "context.technologies",
            "context.supply-chain",
            "report.aggregate-measure-costs",
            "report.technology-counts",
            "include",
            "xi:include"
        };

        // uk.org.cse.nhm.language.definition.batch.inputs.combinators.XCartesianProduct
        map("cross", "cross")
                .element(
                        uk_org_cse_nhm_language_definition_batch_inputs_XInputs,
                        remainder()).attribute("name", named("name"))
                .build();

        // uk.org.cse.nhm.language.definition.reporting.XDwellingActionProbe
        map("action.probe", "probe")
                .attribute("supply-chain", named("supply-chain"))
                .element(
                        uk_org_cse_nhm_language_definition_action_XDwellingAction,
                        positional(0)).attribute("name", named("name"))
                .element(new String[]{"capture"}, named("capture"))
                .attribute("test-flags", namedFlags("test-flags"))
                .attribute("update-flags", namedFlags("update-flags"))
                .build();

        // uk.org.cse.nhm.language.definition.reporting.aggregate.XCrossDivision
        map("division.by-combination", "division.by-combination")
                .element(uk_org_cse_nhm_language_definition_function_XFunction,
                        remainder())
                .attribute("name", named("name"))
                .element(uk_org_cse_nhm_language_definition_group_XGroup,
                        named("source"))
                .build();

        // uk.org.cse.nhm.language.definition.action.XAnyAction
        map("action.any-of", "action.any-of")
                .attribute("supply-chain", named("supply-chain"))
                .element(
                        uk_org_cse_nhm_language_definition_action_XDwellingAction,
                        remainder()).attribute("name", named("name"))
                .attribute("test-flags", namedFlags("test-flags"))
                .attribute("update-flags", namedFlags("update-flags"))
                .build();

        // uk.org.cse.nhm.language.definition.function.num.XLoanBalancePaid
        map("house.loans.balance-paid", "house.loans.balance-paid")
                .attribute("creditor", named("creditor"))
                .attribute("name", named("name"))
                .attribute("tagged", named("tagged"))
                .build();

        // uk.org.cse.nhm.language.definition.fuel.XChangeTariffsAction
        map("action.set-tariffs", "action.set-tariffs")
                .attribute("supply-chain", named("supply-chain"))
                .attribute("name", named("name"))
                .element(uk_org_cse_nhm_language_definition_fuel_XTariffBase,
                        remainder())
                .attribute("test-flags", namedFlags("test-flags"))
                .attribute("update-flags", namedFlags("update-flags"))
                .build();

        // uk.org.cse.nhm.language.definition.context.technology.insulation.XExternalWallInsulationTechnology
        map("technology.external-wall-insulation",
                "technology.external-wall-insulation")
                .element(new String[]{"capex"}, named("capex"))
                .attribute("name", named("name"))
                .build();

        // uk.org.cse.nhm.language.definition.function.bool.house.XBuiltFormIs
        map("house.built-form-is", "house.built-form-is")
                .attribute("equal-to", positional(0))
                .attribute("name", named("name"))
                .build();

        // uk.org.cse.nhm.language.definition.fuel.XTariffs
        map("context.tariffs", "context.tariffs")
                .element(new String[]{"defaults"}, named("defaults"))
                .attribute("name", named("name"))
                .element(new String[]{"others"}, named("others"))
                .build();

        // uk.org.cse.nhm.language.definition.function.num.XEnergyUse
        map("house.energy-use", "house.energy-use")
                .attribute("by-fuel", named("by-fuel"))
                .attribute("by-service", named("by-service"))
                .attribute("name", named("name"))
                .build();

        // uk.org.cse.nhm.language.definition.fuel.XTariffReference
        map("tariff.ref").idref("to").build();
        // uk.org.cse.nhm.language.definition.group.XFilterGroup
        map("group.filter", "group.filter")
                .element(
                        uk_org_cse_nhm_language_definition_function_bool_XBoolean,
                        positional(0))
                .attribute("name", named("name"))
                .element(uk_org_cse_nhm_language_definition_group_XGroup,
                        named("source"))
                .build();

        // uk.org.cse.nhm.language.definition.function.bool.house.XLoftInsulationThicknessIs
        map("house.loft-insulation-thickness-is",
                "house.loft-insulation-thickness-is")
                .attribute("above", named("above"))
                .attribute("below", named("below"))
                .attribute("exactly", named("exactly"))
                .attribute("name", named("name"))
                .build();

        // uk.org.cse.nhm.language.definition.reporting.XNationalPowerReport
        map("report.national-power", "report.national-power").attribute("name",
                named("name"))
                .build();

        // uk.org.cse.nhm.language.definition.group.XUnionGroup
        map("group.union", "group.union")
                .attribute("name", named("name"))
                .element(uk_org_cse_nhm_language_definition_group_XGroup,
                        remainder())
                .build();

        // uk.org.cse.nhm.language.definition.function.house.XPredominantWallType
        map("house.predominant-wall-type", "house.predominant-wall-type")
                .attribute("name", named("name"))
                .build();

        // uk.org.cse.nhm.language.definition.batch.inputs.random.XDiscrete
        map("discrete", "discrete")
                .element(new String[]{"choice"}, remainder())
                .attribute("name", named("name"))
                .attribute("placeholder", named("placeholder"))
                .build();
        map("discrete/choice", "choice").attribute("name", named("name"))
                .attribute("value", named("value"))
                .attribute("weight", named("weight"))
                .build();

        // uk.org.cse.nhm.language.definition.function.house.XMorphology
        map("house.morphology", "house.morphology").attribute("name",
                named("name"))
                .build();

        // uk.org.cse.nhm.language.definition.function.num.basic.XProduct
        map("product", "*")
                .element(
                        uk_org_cse_nhm_language_definition_function_num_XNumber,
                        remainder()).attribute("name", named("name"))
                .build();

        // uk.org.cse.nhm.language.definition.function.num.basic.XSum
        map("sum", "+")
                .element(
                        uk_org_cse_nhm_language_definition_function_num_XNumber,
                        remainder()).attribute("name", named("name"))
                .build();

        // uk.org.cse.nhm.language.definition.action.measure.insulation.XLoftInsulationMeasure
        map("measure.loft-insulation", "measure.loft-insulation")
                .element(new String[]{"capex"}, named("capex"))
                .attribute("supply-chain", named("supply-chain"))
                .attribute("name", named("name"))
                .element(new String[]{"resistance"}, named("resistance"))
                .attribute("test-flags", namedFlags("test-flags"))
                .attribute("thickness", named("thickness"))
                .attribute("top-up", named("top-up"))
                .attribute("update-flags", namedFlags("update-flags"))
                .element(new String[]{"u-value"}, named("u-value"))
                .build();

        // uk.org.cse.nhm.language.definition.function.num.XPredictObligations
        map("obligations.predict", "obligations.predict")
                .attribute("include", named("include"))
                .attribute("name", named("name"))
                .attribute("tags", named("tags"))
                .attribute("years", named("years"))
                .build();

        // uk.org.cse.nhm.language.definition.function.house.XHouseTariff
        map("house.tariff", "house.tariff").attribute("fuel", named("fuel"))
                .attribute("name", named("name"))
                .build();

        // uk.org.cse.nhm.language.definition.group.XIntersectionGroup
        map("group.intersection", "group.intersection")
                .attribute("name", named("name"))
                .element(uk_org_cse_nhm_language_definition_group_XGroup,
                        remainder())
                .build();

        // uk.org.cse.nhm.language.definition.exposure.XIntervalSchedule
        map("schedule.repeat", "schedule.repeat")
                .attribute("end-date", named("end-date"))
                .attribute("interval", named("interval"))
                .attribute("name", named("name"))
                .element(uk_org_cse_nhm_language_definition_exposure_XSampler,
                        positional(0))
                .attribute("start-date", named("start-date"))
                .build();

        // uk.org.cse.nhm.language.definition.action.measure.heating.XRoomHeaterMeasure
        map("measure.room-heater", "measure.room-heater")
                .attribute("backup", named("backup"))
                .element(new String[]{"capex"}, named("capex"))
                .attribute("supply-chain", named("supply-chain"))
                .attribute("efficiency", named("efficiency"))
                .attribute("fuel", named("fuel"))
                .attribute("name", named("name"))
                .attribute("replace-existing", named("replace-existing"))
                .element(new String[]{"size"}, named("size"))
                .attribute("test-flags", namedFlags("test-flags"))
                .attribute("update-flags", namedFlags("update-flags"))
                .build();
        map("measure.room-heater/size", "size")
                .attribute("max", named("max"))
                .attribute("min", named("min"))
                .attribute("name", named("name"))
                .element(
                        uk_org_cse_nhm_language_definition_function_num_XNumber,
                        named("value"))
                .build();

        // uk.org.cse.nhm.language.definition.exposure.XExposeOnEntry
        map("schedule.on-group-entry", "schedule.on-group-entry")
                .attribute("delay", named("delay"))
                .attribute("name", named("name"))
                .build();

        // uk.org.cse.nhm.language.definition.function.num.XLetFunction
        map("function.let", "function.let")
                .element(
                        uk_org_cse_nhm_language_definition_function_num_XNumber,
                        positional(0)).attribute("name", named("name"))
                .element(new String[]{"snapshot"}, named("snapshots"))
                .element(new String[]{"var"}, named("vars"))
                .build();
        map("function.let/snapshot", "snapshot")
                .attribute("name", named("name"))
                .element(
                        uk_org_cse_nhm_language_definition_action_XDwellingAction,
                        remainder())
                .build();
        map("function.let/var", "var")
                .element(
                        uk_org_cse_nhm_language_definition_function_num_XNumber,
                        positional(1)).attribute("name", named("name"))
                .attribute("value", positional(1))
                .attribute("var", positional(0))
                .build();

        // uk.org.cse.nhm.language.definition.function.bool.house.XSuitableFor
        map("house.is-suitable", "house.is-suitable-for")
                .attribute("for", positional(0))
                .attribute("name", named("name"))
                .build();

        // uk.org.cse.nhm.language.definition.reporting.aggregate.XAggregateReport
        map("report.aggregate", "report.aggregate")
                .element(
                        uk_org_cse_nhm_language_definition_reporting_aggregate_XAggregation,
                        remainder())
                .element(
                        uk_org_cse_nhm_language_definition_reporting_aggregate_XDivision,
                        named("division"))
                .element(
                        uk_org_cse_nhm_language_definition_reporting_modes_XReportMode,
                        named("mode")).attribute("name", named("name"))
                .build();

        // uk.org.cse.nhm.language.definition.batch.inputs.XRange
        map("range", "range").attribute("end", named("end"))
                .attribute("name", named("name"))
                .attribute("placeholder", named("placeholder"))
                .attribute("start", named("start"))
                .attribute("step", named("step"))
                .build();

        // uk.org.cse.nhm.language.definition.function.house.XTenure
        map("house.tenure", "house.tenure").attribute("name", named("name"))
                .build();

        // uk.org.cse.nhm.language.definition.exposure.XProportionSampler
        map("sample.proportion", "sample.proportion")
                .attribute("name", named("name"))
                .attribute("proportion", positional(0))
                .build();

        // uk.org.cse.nhm.language.definition.reporting.XDwellingsReport
        map("report.dwellings", "report.dwellings")
                .element(uk_org_cse_nhm_language_definition_function_XFunction,
                        remainder())
                .element(uk_org_cse_nhm_language_definition_group_XGroup,
                        named("group"))
                .element(
                        uk_org_cse_nhm_language_definition_reporting_modes_XReportMode,
                        named("mode")).attribute("name", named("name"))
                .build();

        // uk.org.cse.nhm.language.definition.reporting.modes.XIntegralMode
        map("mode.integral", "mode.integral").attribute("name", named("name"))
                .build();

        // uk.org.cse.nhm.language.definition.function.bool.house.XHasBoiler
        map("house.has-boiler", "house.has-boiler").attribute("name",
                named("name"))
                .build();

        // uk.org.cse.nhm.language.definition.function.bool.house.XBuildYearIs
        map("house.buildyear-is", "house.buildyear-is")
                .attribute("above", named("above"))
                .attribute("below", named("below"))
                .attribute("exactly", named("exactly"))
                .attribute("name", named("name"))
                .build();

        // uk.org.cse.nhm.language.definition.reporting.XHouseCountReport
        map("report.houses-by-region", "report.houses-by-region").attribute(
                "name", named("name"))
                .build();

        // uk.org.cse.nhm.language.definition.batch.inputs.combinators.XRepetitions
        map("repetitions", "repetitions")
                .attribute("count", named("count"))
                .element(
                        uk_org_cse_nhm_language_definition_batch_inputs_XInputs,
                        positional(0)).attribute("name", named("name"))
                .build();

        // uk.org.cse.nhm.language.definition.reporting.modes.XDateMode.XScenarioEnd
        map("scenario-end", "scenario-end").attribute("name", named("name"))
                .build();

        // uk.org.cse.nhm.language.definition.action.measure.heating.XCombiBoilerMeasure
        map("measure.combi-boiler", "measure.combi-boiler")
                .element(new String[]{"capex"}, named("capex"))
                .attribute("supply-chain", named("supply-chain"))
                .attribute("efficiency", named("efficiency"))
                .attribute("fuel", named("fuel"))
                .attribute("name", named("name"))
                .element(new String[]{"opex"}, named("opex"))
                .element(new String[]{"size"}, named("size"))
                .attribute("storage-volume", named("storage-volume"))
                .attribute("test-flags", namedFlags("test-flags"))
                .attribute("update-flags", namedFlags("update-flags"))
                .build();
        map("measure.combi-boiler/size", "size")
                .attribute("max", named("max"))
                .attribute("min", named("min"))
                .attribute("name", named("name"))
                .element(
                        uk_org_cse_nhm_language_definition_function_num_XNumber,
                        named("value"))
                .build();

//		// uk.org.cse.nhm.language.definition.function.num.XNumberConstant
//		map("number", "number").attribute("name", named("name"))
//				.attribute("value", positional(0))
//
//				.build();
        map("number").attribute("value", remainder());

        // uk.org.cse.nhm.language.definition.function.num.basic.XMinimum
        map("min", "min")
                .element(
                        uk_org_cse_nhm_language_definition_function_num_XNumber,
                        remainder()).attribute("name", named("name"))
                .build();

        // uk.org.cse.nhm.language.definition.function.num.basic.XExp
        map("exp", "exp")
                .element(
                        uk_org_cse_nhm_language_definition_function_num_XNumber,
                        positional(0)).attribute("name", named("name"))
                .build();

        // uk.org.cse.nhm.language.definition.function.num.XGet
        map("get", "get").attribute("default", named("default"))
                .attribute("name", named("name"))
                .attribute("var", positional(0))
                .build();

        // uk.org.cse.nhm.language.definition.function.num.XYieldFunction
        map("function.yield", "yield")
                .element(
                        uk_org_cse_nhm_language_definition_function_num_XNumber,
                        positional(0)).attribute("name", named("name"))
                .attribute("var", named("var"))
                .build();

        // uk.org.cse.nhm.language.definition.function.house.XMainHeatingFuel
        map("house.main-heating-fuel", "house.main-heating-fuel").attribute(
                "name", named("name"))
                .build();

        // uk.org.cse.nhm.language.definition.action.registers.XRegisterAddAction
        map("register.add", "register.add")
                .element(
                        uk_org_cse_nhm_language_definition_action_XDwellingAction,
                        named("action"))
                .attribute("supply-chain", named("supply-chain"))
                .attribute("name", named("name"))
                .attribute("test-flags", namedFlags("test-flags"))
                .attribute("update-flags", namedFlags("update-flags"))
                .element(
                        uk_org_cse_nhm_language_definition_function_num_XNumber,
                        named("value"))
                .build();

        // uk.org.cse.nhm.language.definition.money.XSubsidy
        map("finance.with-subsidy", "finance.with-subsidy")
                .element(
                        uk_org_cse_nhm_language_definition_action_XDwellingAction,
                        positional(0))
                .attribute("supply-chain", named("supply-chain"))
                .attribute("counterparty", named("counterparty"))
                .attribute("name", named("name"))
                .element(new String[]{"subsidy"}, named("subsidy"))
                .attribute("tags", named("tags"))
                .attribute("test-flags", namedFlags("test-flags"))
                .attribute("update-flags", namedFlags("update-flags"))
                .build();

        // uk.org.cse.nhm.language.definition.reporting.XGlobalAccountReport
        map("report.global-accounts", "report.global-accounts").attribute(
                "name", named("name"))
                .build();

        // uk.org.cse.nhm.language.definition.function.num.XStepwiseFunction
        map("function.steps", "function.steps")
                .attribute("round", named("round"))
                .attribute("name", named("name"))
                .element(new String[]{"step"},
                new FlattenRule(named("steps"))
                )
                .element(
                        uk_org_cse_nhm_language_definition_function_num_XNumber,
                        named("value"))
                .build();

        // uk.org.cse.nhm.language.definition.action.measure.heating.XBreakBoilerMeasure
        map("measure.break-boiler", "measure.break-boiler")
                .attribute("supply-chain", named("supply-chain"))
                .attribute("name", named("name"))
                .element(
                        uk_org_cse_nhm_language_definition_action_measure_heating_XRoomHeaterMeasure,
                        named("room-heater"))
                .attribute("test-flags", namedFlags("test-flags"))
                .attribute("update-flags", namedFlags("update-flags"))
                .element(
                        uk_org_cse_nhm_language_definition_action_measure_hotwater_XPointOfUseDHWMeasure,
                        named("water-heater"))
                .build();

        // uk.org.cse.nhm.language.definition.action.sap.XSapOccupancyAction
        map("sap.occupancy", "sap.occupancy")
                .attribute("supply-chain", named("supply-chain"))
                .attribute("name", named("name"))
                .attribute("test-flags", namedFlags("test-flags"))
                .attribute("update-flags", namedFlags("update-flags"))
                .build();

        // uk.org.cse.nhm.language.definition.function.bool.house.XTenureIs
        map("house.tenure-is", "house.tenure-is")
                .attribute("equal-to", positional(0))
                .attribute("name", named("name"))
                .build();

        // uk.org.cse.nhm.language.definition.context.XActionsContext
        map("context.named-actions", "context.named-actions")
                .element(uk_org_cse_nhm_language_definition_action_XAction,
                        remainder()).attribute("name", named("name"))
                .build();

        // uk.org.cse.nhm.language.definition.function.num.XNumberCase
        map("function.case", "function.case")
                .element(new String[]{"when"}, remainder())
                .attribute("name", named("name"))
                .element(new String[]{"otherwise"}, named("default"))
                .build();
        map("function.case/when", "when")
                .attribute("name", named("name"))
                .element(
                        uk_org_cse_nhm_language_definition_function_bool_XBoolean,
                        positional(0))
                .element(
                        uk_org_cse_nhm_language_definition_function_num_XNumber,
                        positional(1))
                .build();

        // uk.org.cse.nhm.language.definition.context.technology.insulation.XLoftInsulationTechnology
        map("technology.loft-insulation", "technology.loft-insulation")
                .element(new String[]{"capex"}, named("capex"))
                .attribute("name", named("name"))
                .build();

        // uk.org.cse.nhm.language.definition.batch.inputs.random.XUniformInteger
        map("uniform-integers", "uniform-integers")
                .attribute("end", named("end"))
                .attribute("name", named("name"))
                .attribute("placeholder", named("placeholder"))
                .attribute("start", named("start"))
                .build();

        // uk.org.cse.nhm.language.definition.function.bool.house.XHousePropertyIs
        map("house.static-property-is", "house.static-property-is")
                .attribute("above", named("above"))
                .attribute("below", named("below"))
                .attribute("equal-to", named("equal-to"))
                .attribute("name", named("name"))
                .build();

        // uk.org.cse.nhm.language.definition.function.num.XQuadratic
        map("function.quadratic", "function.quadratic")
                .attribute("a", named("a"))
                .attribute("b", named("b"))
                .attribute("c", named("c"))
                .attribute("name", named("name"))
                .element(
                        uk_org_cse_nhm_language_definition_function_num_XNumber,
                        named("x"))
                .build();

        // uk.org.cse.nhm.language.definition.batch.inputs.random.XGaussian
        map("gaussian", "gaussian").attribute("mean", named("mean"))
                .attribute("name", named("name"))
                .attribute("placeholder", named("placeholder"))
                .attribute("standardDeviation", named("standardDeviation"))
                .build();

        // uk.org.cse.nhm.language.definition.reporting.XInstallationLogReport
        map("report.measure-installations", "report.measure-installations")
                .attribute("name", named("name"))
                .build();

        // uk.org.cse.nhm.language.definition.action.sap.XCounterfactualCarbon
        map("counterfactual.carbon", "counterfactual.carbon")
                .attribute("biomass-pellets", named("biomass-pellets"))
                .attribute("biomass-wood", named("biomass-wood"))
                .attribute("biomass-woodchip", named("biomass-woodchip"))
                .attribute("bottled-lpg", named("bottled-lpg"))
                .attribute("bulk-lpg", named("bulk-lpg"))
                .attribute("community-heat", named("community-heat"))
                .attribute("supply-chain", named("supply-chain"))
                .attribute("house-coal", named("house-coal"))
                .attribute("mains-gas", named("mains-gas"))
                .attribute("name", named("name"))
                .attribute("off-peak-electricity",
                        named("off-peak-electricity"))
                .attribute("oil", named("oil"))
                .attribute("peak-electricity", named("peak-electricity"))
                .attribute("photons", named("photons"))
                .attribute("test-flags", namedFlags("test-flags"))
                .attribute("update-flags", namedFlags("update-flags"))
                .build();

        // uk.org.cse.nhm.language.definition.action.XHeatingScheduleAction
        map("action.set-heating-schedule", "action.set-heating-schedule")
                .attribute("supply-chain", named("supply-chain"))
                .attribute("name", named("name"))
                .element(new String[]{"schedule"}, remainder())
                .attribute("test-flags", namedFlags("test-flags"))
                .attribute("update-flags", namedFlags("update-flags"))
                .build();
        map("action.set-heating-schedule/schedule", "schedule")
                .element(new String[]{"heating"}, remainder())
                .attribute("name", named("name")).attribute("on", named("on"))
                .build();
        map("action.set-heating-schedule/schedule/heating", "heating")
                .attribute("and", named("and"))
                .attribute("between", named("between"))
                .attribute("name", named("name"))
                .build();

        // uk.org.cse.nhm.language.definition.context.technology.heating.XGroundSourceHeatPumpTechnology
        map("technology.gshp", "technology.gshp")
                .element(new String[]{"capex"}, named("capex"))
                .attribute("name", named("name"))
                .element(new String[]{"opex"}, named("opex"))
                .element(new String[]{"size"}, named("size"))
                .build();
        map("technology.gshp/size", "size")
                .attribute("max", named("max"))
                .attribute("min", named("min"))
                .attribute("name", named("name"))
                .element(
                        uk_org_cse_nhm_language_definition_function_num_XNumber,
                        named("value"))
                .build();

        // uk.org.cse.nhm.language.definition.function.bool.XNone
        map("none", "none")
                .element(
                        uk_org_cse_nhm_language_definition_function_bool_XBoolean,
                        remainder()).attribute("name", named("name"))
                .build();

        // uk.org.cse.nhm.language.definition.money.XLoanAction
        map("finance.with-loan", "finance.with-loan")
                .element(
                        uk_org_cse_nhm_language_definition_action_XDwellingAction,
                        positional(0))
                .attribute("supply-chain", named("supply-chain"))
                .attribute("counterparty", named("counterparty"))
                .attribute("name", named("name"))
                .element(new String[]{"principal"}, named("principal"))
                .attribute("rate", named("rate"))
                .attribute("tags", named("tags"))
                .attribute("term", named("term"))
                .attribute("test-flags", namedFlags("test-flags"))
                .attribute("tilt", named("tilt"))
                .attribute("update-flags", namedFlags("update-flags"))
                .build();

        // uk.org.cse.nhm.language.definition.action.choices.XMaximumSelector
        map("select.maximum", "select.maximum")
                .attribute("name", named("name"))
                .element(
                        uk_org_cse_nhm_language_definition_function_num_XNumber,
                        positional(0))
                .element(new String[]{"snapshot"}, named("snapshots"))
                .element(new String[]{"var"}, named("vars"))
                .build();
        map("select.maximum/snapshot", "snapshot")
                .attribute("name", named("name"))
                .element(
                        uk_org_cse_nhm_language_definition_action_XDwellingAction,
                        remainder())
                .build();
        map("select.maximum/var", "var")
                .element(
                        uk_org_cse_nhm_language_definition_function_num_XNumber,
                        positional(1)).attribute("name", named("name"))
                .attribute("value", positional(1))
                .attribute("var", positional(0))
                .build();

        // uk.org.cse.nhm.language.definition.function.num.XTimeSeries
        map("function.time-series", "function.time-series")
                .attribute("initial", named("initial"))
                .attribute("name", named("name"))
                .element(new String[]{"on"}, remainder())
                .build();
        map("function.time-series/on", "on").attribute("date", positional(0))
                .attribute("name", named("name"))
                .attribute("value", positional(1))
                .build();

        // uk.org.cse.nhm.language.definition.group.XDifferenceGroup
        map("group.difference", "group.difference")
                .attribute("name", named("name"))
                .element(uk_org_cse_nhm_language_definition_group_XGroup,
                        remainder())
                .build();

        // uk.org.cse.nhm.language.definition.function.num.XLoanBalanceOutstanding
        map("house.loans.balance-outstanding",
                "house.loans.balance-outstanding")
                .attribute("creditor", named("creditor"))
                .attribute("name", named("name"))
                .attribute("tagged", named("tagged"))
                .build();

        // uk.org.cse.nhm.language.definition.batch.inputs.random.XTriangular
        map("triangular", "triangular").attribute("end", named("end"))
                .attribute("name", named("name"))
                .attribute("peak", named("peak"))
                .attribute("placeholder", named("placeholder"))
                .attribute("start", named("start"))
                .build();

        // uk.org.cse.nhm.language.definition.group.XAllHousesGroup
        map("group.all", "group.all").attribute("name", named("name"))
                .build();

        // uk.org.cse.nhm.language.definition.fuel.XFullTariff
        map("tariff", "tariff").element(new String[]{"fuel"}, remainder())
                .attribute("name", named("name"))
                .build();
        map("tariff/fuel", "fuel")
                .element(new String[]{"charge"}, remainder())
                .attribute("type", named("type"))
                .attribute("name", named("name"))
                .build();
        map("tariff/fuel/charge", "charge")
                .attribute("name", named("name"))
                .attribute("payee", named("payee"))
                .element(
                        uk_org_cse_nhm_language_definition_function_num_XNumber,
                        positional(0))
                .build();

        // uk.org.cse.nhm.language.definition.exposure.XBernoulliSampler
        map("sample.bernoulli", "sample.bernoulli")
                .attribute("name", named("name"))
                .element(
                        uk_org_cse_nhm_language_definition_function_num_XNumber,
                        positional(0))
                .build();

        // uk.org.cse.nhm.language.definition.function.num.basic.XRatio
        map("ratio", "/")
                .element(
                        uk_org_cse_nhm_language_definition_function_num_XNumber,
                        remainder()).attribute("name", named("name"))
                .build();

        // uk.org.cse.nhm.language.definition.context.technology.renewable.XSolarHotWaterTechnology
        map("technology.solar-hot-water", "technology.solar-hot-water")
                .attribute("name", named("name"))
                .build();

        // uk.org.cse.nhm.language.definition.context.XWeatherContext
        map("context.weather", "context.weather")
                .attribute("name", named("name"))
                .element(new String[]{"weather"}, remainder())
                .build();
        map("context.weather/weather", "year")
                .element(new String[]{"april"}, named("april"))
                .element(new String[]{"august"}, named("august"))
                .element(new String[]{"december"}, named("december"))
                .element(new String[]{"february"}, named("february"))
                .element(new String[]{"january"}, named("january"))
                .element(new String[]{"july"}, named("july"))
                .element(new String[]{"june"}, named("june"))
                .element(new String[]{"march"}, named("march"))
                .element(new String[]{"may"}, named("may"))
                .attribute("name", named("name"))
                .element(new String[]{"november"}, named("november"))
                .element(new String[]{"october"}, named("october"))
                .element(new String[]{"september"}, named("september"))
                .element(
                        uk_org_cse_nhm_language_definition_function_bool_XBoolean,
                        named("test"))
                .build();
        map("context.weather/weather/april", "month")
                .attribute("insolation", named("insolation"))
                .attribute("name", named("name"))
                .attribute("temperature", named("temperature"))
                .attribute("windspeed", named("windspeed"))
                .build();
        map("context.weather/weather/august", "month")
                .attribute("insolation", named("insolation"))
                .attribute("name", named("name"))
                .attribute("temperature", named("temperature"))
                .attribute("windspeed", named("windspeed"))
                .build();
        map("context.weather/weather/december", "month")
                .attribute("insolation", named("insolation"))
                .attribute("name", named("name"))
                .attribute("temperature", named("temperature"))
                .attribute("windspeed", named("windspeed"))
                .build();
        map("context.weather/weather/february", "month")
                .attribute("insolation", named("insolation"))
                .attribute("name", named("name"))
                .attribute("temperature", named("temperature"))
                .attribute("windspeed", named("windspeed"))
                .build();
        map("context.weather/weather/january", "month")
                .attribute("insolation", named("insolation"))
                .attribute("name", named("name"))
                .attribute("temperature", named("temperature"))
                .attribute("windspeed", named("windspeed"))
                .build();
        map("context.weather/weather/july", "month")
                .attribute("insolation", named("insolation"))
                .attribute("name", named("name"))
                .attribute("temperature", named("temperature"))
                .attribute("windspeed", named("windspeed"))
                .build();
        map("context.weather/weather/june", "month")
                .attribute("insolation", named("insolation"))
                .attribute("name", named("name"))
                .attribute("temperature", named("temperature"))
                .attribute("windspeed", named("windspeed"))
                .build();
        map("context.weather/weather/march", "month")
                .attribute("insolation", named("insolation"))
                .attribute("name", named("name"))
                .attribute("temperature", named("temperature"))
                .attribute("windspeed", named("windspeed"))
                .build();
        map("context.weather/weather/may", "month")
                .attribute("insolation", named("insolation"))
                .attribute("name", named("name"))
                .attribute("temperature", named("temperature"))
                .attribute("windspeed", named("windspeed"))
                .build();
        map("context.weather/weather/november", "month")
                .attribute("insolation", named("insolation"))
                .attribute("name", named("name"))
                .attribute("temperature", named("temperature"))
                .attribute("windspeed", named("windspeed"))
                .build();
        map("context.weather/weather/october", "month")
                .attribute("insolation", named("insolation"))
                .attribute("name", named("name"))
                .attribute("temperature", named("temperature"))
                .attribute("windspeed", named("windspeed"))
                .build();
        map("context.weather/weather/september", "month")
                .attribute("insolation", named("insolation"))
                .attribute("name", named("name"))
                .attribute("temperature", named("temperature"))
                .attribute("windspeed", named("windspeed"))
                .build();

        // uk.org.cse.nhm.language.definition.function.num.XHeatCapacity
        map("size.kw", "size.kw").attribute("name", named("name"))
                .build();

        // uk.org.cse.nhm.language.definition.function.bool.house.XHouseIsOnTariff
        map("house.is-on-tariff", "house.is-on-tariff")
                .attribute("name", named("name"))
                .attribute("named", positional(0))
                .build();

        // uk.org.cse.nhm.language.definition.action.measure.insulation.XWallInsulationMeasure
        map("measure.wall-insulation", "measure.wall-insulation")
                .element(new String[]{"capex"}, named("capex"))
                .attribute("supply-chain", named("supply-chain"))
                .attribute("name", named("name"))
                .element(new String[]{"resistance"}, named("resistance"))
                .attribute("suitable-construction",
                        named("suitable-construction"))
                .attribute("suitable-insulation", named("suitable-insulation"))
                .attribute("test-flags", namedFlags("test-flags"))
                .attribute("thickness", named("thickness"))
                .attribute("type", named("type"))
                .attribute("update-flags", namedFlags("update-flags"))
                .element(new String[]{"u-value"}, named("u-value"))
                .build();

        // uk.org.cse.nhm.language.definition.reporting.aggregate.XCountAggregation
        map("aggregate.count", "aggregate.count").attribute("name",
                named("name"))
                .build();

        // uk.org.cse.nhm.language.definition.reporting.modes.XDateMode.XOn
        map("once", "on").attribute("name", named("name"))
                .attribute("on", positional(0))
                .build();

        // uk.org.cse.nhm.language.definition.XTarget
        map("target", "target")
                .element(uk_org_cse_nhm_language_definition_action_XAction,
                        named("action"))
                .element(uk_org_cse_nhm_language_definition_exposure_XExposure,
                        named("exposure"))
                .element(uk_org_cse_nhm_language_definition_group_XGroup,
                        named("group")).attribute("name", named("name"))
                .build();

        // uk.org.cse.nhm.language.definition.function.bool.house.XAnyWalls
        map("house.has-wall", "house.has-wall")
                .attribute("name", named("name"))
                .attribute("with-construction", named("with-construction"))
                .attribute("with-insulation", named("with-insulation"))
                .build();

        // uk.org.cse.nhm.language.definition.context.technology.insulation.XInternalWallInsulationTechnology
        map("technology.internal-wall-insulation",
                "technology.internal-wall-insulation")
                .element(new String[]{"capex"}, named("capex"))
                .attribute("name", named("name"))
                .build();

        // uk.org.cse.nhm.language.definition.reporting.XFuelCostsReport
        map("report.fuel-costs", "report.fuel-costs").attribute("name",
                named("name"))
                .build();

        // uk.org.cse.nhm.language.definition.function.num.XCarbonEmissions
        map("house.emissions", "house.emissions")
                .attribute("by-fuel", named("by-fuel"))
                .attribute("by-service", named("by-service"))
                .attribute("name", named("name"))
                .build();

        // uk.org.cse.nhm.language.definition.function.bool.XAll
        map("all", "all")
                .element(
                        uk_org_cse_nhm_language_definition_function_bool_XBoolean,
                        remainder()).attribute("name", named("name"))
                .build();

        // uk.org.cse.nhm.language.definition.reporting.aggregate.XCaseDivision
        map("division.by-case", "division.by-case")
                .element(
                        uk_org_cse_nhm_language_definition_function_bool_XBoolean,
                        remainder())
                .attribute("name", named("name"))
                .element(uk_org_cse_nhm_language_definition_group_XGroup,
                        named("source"))
                .build();

        // uk.org.cse.nhm.language.definition.action.XActionReference
        map("action.ref").idref("to").build();

        // uk.org.cse.nhm.language.definition.action.choices.XFilterSelector
        map("select.filter", "select.filter")
                .attribute("name", named("name"))
                .element(
                        uk_org_cse_nhm_language_definition_action_choices_XChoiceSelector,
                        named("selector"))
                .element(new String[]{"snapshot"}, named("snapshots"))
                .element(
                        uk_org_cse_nhm_language_definition_function_bool_XBoolean,
                        named("test"))
                .element(new String[]{"var"}, named("vars"))
                .build();
        map("select.filter/snapshot", "snapshot")
                .attribute("name", named("name"))
                .element(
                        uk_org_cse_nhm_language_definition_action_XDwellingAction,
                        remainder())
                .build();
        map("select.filter/var", "var")
                .element(
                        uk_org_cse_nhm_language_definition_function_num_XNumber,
                        positional(1)).attribute("name", named("name"))
                .attribute("value", positional(1))
                .attribute("var", positional(0))
                .build();

        // uk.org.cse.nhm.language.definition.reporting.aggregate.XMeanAggregation
        map("aggregate.mean", "aggregate.mean")
                .attribute("name", named("name"))
                .element(
                        uk_org_cse_nhm_language_definition_function_num_XNumber,
                        positional(0))
                .build();

        // uk.org.cse.nhm.language.definition.XCase
        map("case", "case")
                .attribute("name", named("name"))
                .element(new String[]{"otherwise"}, named("default"))
                .element(uk_org_cse_nhm_language_definition_group_XGroup,
                        named("source"))
                .element(new String[]{"when"}, named("cases"))
                .build();
        map("case/otherwise", "otherwise")
                .element(uk_org_cse_nhm_language_definition_action_XAction,
                        named("action"))
                .element(uk_org_cse_nhm_language_definition_exposure_XExposure,
                        named("exposure")).attribute("name", named("name"))
                .build();
        map("case/when", "case")
                .element(uk_org_cse_nhm_language_definition_action_XAction,
                        named("action"))
                .element(uk_org_cse_nhm_language_definition_exposure_XExposure,
                        named("exposure"))
                .attribute("name", named("name"))
                .element(
                        uk_org_cse_nhm_language_definition_function_bool_XBoolean,
                        named("test"))
                .build();

        // uk.org.cse.nhm.language.definition.batch.inputs.XTable
        map("table", "table")
                .element(new String[]{"header"}, named("header"))
                .attribute("name", named("name"))
                .element(new String[]{"row"}, remainder())
                .build();
        map("table/header", "row")
                .element(new String[]{"cell"}, remainder())
                .attribute("name", named("name"))
                .build();
        map("table/row", "row").element(new String[]{"cell"}, remainder())
                .attribute("name", named("name"))
                .build();

        // uk.org.cse.nhm.language.definition.action.choices.XMinimumSelector
        map("select.minimum", "select.minimum")
                .attribute("name", named("name"))
                .element(
                        uk_org_cse_nhm_language_definition_function_num_XNumber,
                        positional(0))
                .element(new String[]{"snapshot"}, named("snapshots"))
                .element(new String[]{"var"}, named("vars"))
                .build();
        map("select.minimum/snapshot", "snapshot")
                .attribute("name", named("name"))
                .element(
                        uk_org_cse_nhm_language_definition_action_XDwellingAction,
                        remainder())
                .build();
        map("select.minimum/var", "var")
                .element(
                        uk_org_cse_nhm_language_definition_function_num_XNumber,
                        positional(1)).attribute("name", named("name"))
                .attribute("value", positional(1))
                .attribute("var", positional(0))
                .build();

        // uk.org.cse.nhm.language.definition.reporting.XDwellingTransactionReport
        map("report.transactions", "report.transactions")
                .element(uk_org_cse_nhm_language_definition_group_XGroup,
                        named("group")).attribute("name", named("name"))
                .attribute("tags", named("tags"))
                .build();

        // uk.org.cse.nhm.language.definition.function.num.XAnnualMaintenance
        map("house.annual-maintenance", "house.annual-maintenance").attribute(
                "name", named("name"))
                .build();

        // uk.org.cse.nhm.language.definition.action.XFlagAction
        map("action.flag", "action.flag")
                .attribute("supply-chain", named("supply-chain"))
                .attribute("mode", named("mode"))
                .attribute("name", named("name"))
                .attribute("test-flags", namedFlags("test-flags"))
                .attribute("update-flags", namedFlags("update-flags"))
                .build();

        // uk.org.cse.nhm.language.definition.function.num.XSumOfCosts
        map("cost.sum", "cost.sum").attribute("name", named("name"))
                .attribute("tagged", named("tagged"))
                .build();

        // uk.org.cse.nhm.language.definition.reporting.XProbedNetPresentValue
        map("probe.npv", "probe.npv").attribute("name", named("name"))
                .build();

        // uk.org.cse.nhm.language.definition.action.XDestroyAction
        map("action.demolish", "action.demolish").attribute("name",
                named("name"))
                .build();

        // uk.org.cse.nhm.language.definition.context.technology.insulation.XCavityWallInsulationTechnology
        map("technology.cavity-insulation", "technology.cavity-insulation")
                .element(new String[]{"capex"}, named("capex"))
                .attribute("name", named("name"))
                .build();

        // uk.org.cse.nhm.language.definition.batch.inputs.combinators.XConcatenate
        map("concat", "concat")
                .element(
                        uk_org_cse_nhm_language_definition_batch_inputs_XInputs,
                        remainder()).attribute("name", named("name"))
                .build();

        // uk.org.cse.nhm.language.definition.function.bool.XYearIs
        map("sim.year-is", "sim.year-is").attribute("above", named("above"))
                .attribute("below", named("below"))
                .attribute("exactly", named("exactly"))
                .attribute("name", named("name"))
                .build();

        // uk.org.cse.nhm.language.definition.function.num.XHouseBalance
        map("house.balance", "house.balance").attribute("name", named("name"))
                .build();

        // uk.org.cse.nhm.language.definition.reporting.XGroupTransitionReport
        map("report.group-transitions", "report.group-transitions")
                .element(new String[]{"when"}, remainder())
                .element(uk_org_cse_nhm_language_definition_group_XGroup,
                        named("group")).attribute("name", named("name"))
                .build();
        map("report.group-transitions/when", "when")
                .attribute("name", named("name"))
                .element(
                        uk_org_cse_nhm_language_definition_function_bool_XBoolean,
                        positional(0))
                .build();

        // uk.org.cse.nhm.language.definition.group.XReferenceGroup
        map("group.reference").idref("to").build();

        // uk.org.cse.nhm.language.definition.action.XDoNothingAction
        map("action.do-nothing", "action.do-nothing")
                .attribute("supply-chain", named("supply-chain"))
                .attribute("name", named("name"))
                .attribute("test-flags", namedFlags("test-flags"))
                .attribute("update-flags", namedFlags("update-flags"))
                .build();

        // uk.org.cse.nhm.language.definition.exposure.XCountSampler
        map("sample.count", "sample.count").attribute("count", positional(0))
                .attribute("name", named("name"))
                .build();

        // uk.org.cse.nhm.language.definition.function.num.XNetPresentValue
        map("npv", "npv").attribute("discount", named("discount"))
                .attribute("horizon", named("horizon"))
                .attribute("name", named("name"))
                .build();

        // uk.org.cse.nhm.language.definition.reporting.XStateReport
        map("report.state", "report.state").attribute("name", named("name"))
                .build();

        // uk.org.cse.nhm.language.definition.function.house.XRegion
        map("house.region", "house.region").attribute("name", named("name"))
                .build();

        // uk.org.cse.nhm.language.definition.function.bool.house.XNumberFunctionIs
        map("house.value-is", "house.value-is")
                .attribute("above", named("above"))
                .attribute("below", named("below"))
                .attribute("exactly", named("exactly"))
                .attribute("name", named("name"))
                .element(
                        uk_org_cse_nhm_language_definition_function_num_XNumber,
                        positional(0))
                .build();

        // uk.org.cse.nhm.language.definition.function.bool.house.XFlagsAre
        map("house.flags", "house.flags-match").attribute("match", remainderFlags())
                .attribute("name", named("name"))
                .build();

        // uk.org.cse.nhm.language.definition.fuel.XSimpleTariff
        map("tariff.simple", "tariff.simple")
                .element(
                        uk_org_cse_nhm_language_definition_fuel_XSimpleTariff_XSimpleTariffFuel,
                        remainder()).attribute("name", named("name"))
                .build();

        // uk.org.cse.nhm.language.definition.function.bool.house.XMorphologyIs
        map("house.morphology-is", "house.morphology-is")
                .attribute("equal-to", positional(0))
                .attribute("name", named("name"))
                .build();

        // uk.org.cse.nhm.language.definition.fuel.XSimpleTariff.XSimpleTariffFuel
        map("function.simple-pricing", "function.simple-pricing")
                .attribute("fuel", named("fuel"))
                .attribute("name", named("name"))
                .attribute("standing-charge", named("standing-charge"))
                .attribute("unit-price", named("unit-price"))
                .build();

        // uk.org.cse.nhm.language.definition.reporting.modes.XDateMode.XInterval
        map("between", "between").attribute("end", named("end"))
                .attribute("interval", named("interval"))
                .attribute("name", named("name"))
                .attribute("start", named("start"))
                .build();

        // uk.org.cse.nhm.language.definition.function.num.XInsulationArea
        map("size.m2", "size.m2").attribute("name", named("name"))
                .build();

        // uk.org.cse.nhm.language.definition.batch.XBatch
        map("batch", "batch")
                .element(
                        uk_org_cse_nhm_language_definition_batch_inputs_XInputs,
                        named("inputs")).attribute("name", named("name"))
                .element(new String[]{"scenario"}, named("scenario"))
                .element(new String[]{"include", "xi:include"}, remainder())
                .attribute("seed", named("seed"))
                .build();

        // uk.org.cse.nhm.language.definition.group.XProbabilisticMembershipGroup
        map("group.probabilistic-membership", "group.probabilistic-membership")
                .element(
                        uk_org_cse_nhm_language_definition_function_num_XNumber,
                        named("function"))
                .attribute("name", named("name"))
                .element(uk_org_cse_nhm_language_definition_group_XGroup,
                        named("source"))
                .build();

        // uk.org.cse.nhm.language.definition.context.XEnergyConstantsContext
        map("context.energy-constants", "context.energy-constants")
                .element(new String[]{"constant"}, remainder())
                .attribute("name", named("name"))
                .build();
        map("context.energy-constants/constant", "constant")
                .attribute("constant", positional(0))
                .attribute("name", named("name"))
                .attribute("value", positional(1))
                .build();

        // uk.org.cse.nhm.language.definition.function.bool.house.XAgeIs
        map("house.age-is", "house.age-is").attribute("above", named("above"))
                .attribute("below", named("below"))
                .attribute("exactly", named("exactly"))
                .attribute("name", named("name"))
                .build();

        // uk.org.cse.nhm.language.definition.reporting.aggregate.XGroupDivision
        map("division.by-group", "division.by-group")
                .element(uk_org_cse_nhm_language_definition_group_XGroup,
                        remainder()).attribute("name", named("name"))
                .build();

        // uk.org.cse.nhm.language.definition.action.imputation.XReimputationAction
        map("action.reimpute", "action.reimpute")
                .attribute("supply-chain", named("supply-chain"))
                .attribute("name", named("name"))
                .element(
                        uk_org_cse_nhm_language_definition_action_imputation_XImputationSchema,
                        named("with"))
                .attribute("test-flags", namedFlags("test-flags"))
                .attribute("update-flags", namedFlags("update-flags"))
                .build();

        // uk.org.cse.nhm.language.definition.function.num.basic.XMaximum
        map("max", "max")
                .element(
                        uk_org_cse_nhm_language_definition_function_num_XNumber,
                        remainder()).attribute("name", named("name"))
                .build();

        // uk.org.cse.nhm.language.definition.action.measure.heating.XBoilerEfficiencyMeasure
        map("measure.change-boiler-efficiency",
                "measure.change-boiler-efficiency")
                .attribute("supply-chain", named("supply-chain"))
                .attribute("direction", named("direction"))
                .attribute("name", named("name"))
                .attribute("test-flags", namedFlags("test-flags"))
                .attribute("to", named("to"))
                .attribute("update-flags", namedFlags("update-flags"))
                .build();

        // uk.org.cse.nhm.language.definition.context.technology.heating.XBoilerTechnology
        map("technology.boiler", "technology.boiler")
                .attribute("type", named("type"))
                .element(new String[]{"capex"}, named("capex"))
                .attribute("fuel", named("fuel"))
                .attribute("name", named("name"))
                .element(new String[]{"opex"}, named("opex"))
                .element(new String[]{"size"}, named("size"))
                .build();
        map("technology.boiler/size", "size")
                .attribute("max", named("max"))
                .attribute("min", named("min"))
                .attribute("name", named("name"))
                .element(
                        uk_org_cse_nhm_language_definition_function_num_XNumber,
                        named("value"))
                .build();

        // uk.org.cse.nhm.language.definition.function.num.XFuelCost
        map("house.fuel-cost", "house.fuel-cost")
                .attribute("by-fuel", named("by-fuel"))
                .attribute("by-service", named("by-service"))
                .attribute("name", named("name"))
                .build();

        // uk.org.cse.nhm.language.definition.action.measure.heating.XDistrictHeatingMeasure
        map("measure.district-heating", "measure.district-heating")
                .element(new String[]{"capex"}, named("capex"))
                .attribute("supply-chain", named("supply-chain"))
                .attribute("cylinder-insulation-thickness",
                        named("cylinder-insulation-thickness"))
                .attribute("cylinder-volume", named("cylinder-volume"))
                .attribute("efficiency", named("efficiency"))
                .attribute("name", named("name"))
                .element(new String[]{"opex"}, named("opex"))
                .element(new String[]{"size"}, named("size"))
                .attribute("test-flags", namedFlags("test-flags"))
                .attribute("update-flags", namedFlags("update-flags"))
                .build();
        map("measure.district-heating/size", "size")
                .attribute("max", named("max"))
                .attribute("min", named("min"))
                .attribute("name", named("name"))
                .element(
                        uk_org_cse_nhm_language_definition_function_num_XNumber,
                        named("value"))
                .build();

        // uk.org.cse.nhm.language.definition.action.measure.hotwater.XPointOfUseDHWMeasure
        map("measure.point-of-use-hot-water", "measure.point-of-use-hot-water")
                .element(new String[]{"capex"}, named("capex"))
                .attribute("supply-chain", named("supply-chain"))
                .attribute("name", named("name"))
                .attribute("replace-existing", named("replace-existing"))
                .attribute("test-flags", namedFlags("test-flags"))
                .attribute("update-flags", namedFlags("update-flags"))
                .build();

        // uk.org.cse.nhm.language.definition.context.XCarbonFactorContext
        map("context.carbon-factors", "context.carbon-factors")
                .element(new String[]{"group"}, remainder())
                .attribute("name", named("name"))
                .build();
        map("context.carbon-factors/group", "group")
                .element(
                        uk_org_cse_nhm_language_definition_function_num_XNumber,
                        named("carbon-factor"))
                .element(new String[]{"fuel"}, new FlattenRule(named("fuels")))
                .attribute("name", named("name"))
                .build();

        map("context.carbon-factors/group/fuel")
                .text(remainder());

        // uk.org.cse.nhm.language.definition.context.technology.XTechnologyContextParameter
        map("context.technologies", "context.technologies")
                .attribute("name", named("name"))
                .element(
                        uk_org_cse_nhm_language_definition_context_technology_XTechnology,
                        remainder())
                .build();

        // uk.org.cse.nhm.language.definition.batch.inputs.random.XUniform
        map("uniform", "uniform").attribute("end", named("end"))
                .attribute("name", named("name"))
                .attribute("placeholder", named("placeholder"))
                .attribute("start", named("start"))
                .build();

        // uk.org.cse.nhm.language.definition.reporting.modes.XDateMode.XScenarioStart
        map("scenario-start", "scenario-start")
                .attribute("name", named("name"))
                .build();

        // uk.org.cse.nhm.language.definition.reporting.modes.XOnChangeMode
        map("mode.on-change", "mode.on-change")
                .attribute("name", named("name"))
                .build();

        // uk.org.cse.nhm.language.definition.function.house.XBuiltForm
        map("house.built-form", "house.built-form").attribute("name",
                named("name"))
                .build();

        // uk.org.cse.nhm.language.definition.function.num.XUnderFunction
        map("under", "under")
                .element(
                        uk_org_cse_nhm_language_definition_function_num_XNumber,
                        named("evaluate"))
                .element(
                        uk_org_cse_nhm_language_definition_action_XDwellingAction,
                        remainder()).attribute("name", named("name"))
                .attribute("snapshot", named("snapshot"))
                .build();

        // uk.org.cse.nhm.language.definition.context.technology.heating.XAirSourceHeatPumpTechnology
        map("technology.ashp", "technology.ashp")
                .element(new String[]{"capex"}, named("capex"))
                .attribute("name", named("name"))
                .element(new String[]{"opex"}, named("opex"))
                .element(new String[]{"size"}, named("size"))
                .build();
        map("technology.ashp/size", "size")
                .attribute("max", named("max"))
                .attribute("min", named("min"))
                .attribute("name", named("name"))
                .element(
                        uk_org_cse_nhm_language_definition_function_num_XNumber,
                        named("value"))
                .build();

        // uk.org.cse.nhm.language.definition.context.XSupplyChain
        map("context.supply-chain", "context.supply-chain")
                .attribute("name", named("name"))
                .element(new String[]{"supply"}, remainder())
                .build();
        map("context.supply-chain/supply", "supply")
                .attribute("increases-by", named("increases-by"))
                .attribute("every", named("every"))
                .attribute("name", named("name"))
                .attribute("from", named("from")).attribute("is", named("is"))
                .build();

        // uk.org.cse.nhm.language.definition.function.bool.XAny
        map("any", "any")
                .element(
                        uk_org_cse_nhm_language_definition_function_bool_XBoolean,
                        remainder()).attribute("name", named("name"))
                .build();

        // uk.org.cse.nhm.language.definition.function.bool.house.XRegionIs
        map("house.region-is", "house.region-is")
                .attribute("equal-to", positional(0))
                .attribute("name", named("name"))
                .build();

        // uk.org.cse.nhm.language.definition.batch.inputs.XInputBound
        map("bounded", "bounded")
                .attribute("bound", named("bound"))
                .element(
                        uk_org_cse_nhm_language_definition_batch_inputs_XInputs,
                        named("inputs")).attribute("name", named("name"))
                .build();

        // uk.org.cse.nhm.language.definition.function.num.XSapScore
        map("house.fuel-cost-index", "house.fuel-cost-index")
                .attribute("deflator", named("deflator"))
                .attribute("name", named("name"))
                .build();

        // uk.org.cse.nhm.language.definition.action.XSetLivingAreaFractionAction
        map("action.set-living-area-fraction",
                "action.set-living-area-fraction")
                .attribute("supply-chain", named("supply-chain"))
                .attribute("to", named("to")).attribute("name", named("name"))
                .attribute("test-flags", namedFlags("test-flags"))
                .attribute("update-flags", namedFlags("update-flags"))
                .build();

        // uk.org.cse.nhm.language.definition.action.XYieldAction
        map("yield", "yield")
                .attribute("supply-chain", named("supply-chain"))
                .element(
                        uk_org_cse_nhm_language_definition_action_XDwellingAction,
                        named("delegate")).attribute("name", named("name"))
                .attribute("test-flags", namedFlags("test-flags"))
                .attribute("update-flags", namedFlags("update-flags"))
                .element(new String[]{"var"}, named("vars"))
                .build();
        map("yield/var", "var")
                .element(
                        uk_org_cse_nhm_language_definition_function_num_XNumber,
                        positional(1)).attribute("name", named("name"))
                .attribute("value", positional(1))
                .attribute("var", positional(0))
                .build();

        // uk.org.cse.nhm.language.definition.action.measure.renewable.XSolarHotWaterMeasure
        map("measure.solar-dhw", "measure.solar-dhw")
                .attribute("area", named("area"))
                .attribute("cost", named("cost"))
                .attribute("supply-chain", named("supply-chain"))
                .attribute("cylinder-volume", named("cylinder-volume"))
                .attribute("name", named("name"))
                .attribute("test-flags", namedFlags("test-flags"))
                .attribute("update-flags", namedFlags("update-flags"))
                .build();

        // uk.org.cse.nhm.language.definition.function.num.basic.XDifference
        map("difference", "-")
                .element(
                        uk_org_cse_nhm_language_definition_function_num_XNumber,
                        remainder()).attribute("name", named("name"))
                .build();

        // uk.org.cse.nhm.language.definition.action.imputation.XRDSapSchema
        map("schema.rdsap", "schema.rdsap").attribute("name", named("name"))
                .build();

        // uk.org.cse.nhm.language.definition.reporting.modes.XDateMode
        map("mode.dates", "mode.dates")
                .attribute("name", named("name"))
                .element(
                        uk_org_cse_nhm_language_definition_reporting_modes_XDateMode_XReportingDates,
                        remainder())
                .build();

        // uk.org.cse.nhm.language.definition.exposure.XTimeSeriesSchedule
        map("schedule.time-series", "schedule.time-series")
                .element(new String[]{"expose"}, remainder())
                .attribute("name", named("name"))
                .build();
        map("schedule.time-series/expose", "expose")
                .attribute("name", named("name"))
                .attribute("on", named("on"))
                .element(uk_org_cse_nhm_language_definition_exposure_XSampler,
                        positional(0))
                .build();

        // uk.org.cse.nhm.language.definition.action.XMeasureReference
        map("measure.ref").idref("to").build();

        // uk.org.cse.nhm.language.definition.function.num.XHouseMeterReading
        map("house.meter-reading", "house.meter-reading")
                .attribute("fuel", named("fuel"))
                .attribute("name", named("name"))
                .build();

        // uk.org.cse.nhm.language.definition.action.sap.XCounterfactualWeather
        map("counterfactual.weather", "counterfactual.weather")
                .attribute("supply-chain", named("supply-chain"))
                .attribute("name", named("name"))
                .attribute("test-flags", namedFlags("test-flags"))
                .attribute("update-flags", namedFlags("update-flags"))
                .build();

        // uk.org.cse.nhm.language.definition.function.bool.house.XMainHeatingFuelIs
        map("house.main-heating-fuel-is", "house.main-heating-fuel-is")
                .attribute("equal-to", positional(0))
                .attribute("name", named("name"))
                .build();

        // uk.org.cse.nhm.language.definition.action.choices.XWeightedSelector
        map("select.weighted", "select.weighted")
                .attribute("name", named("name"))
                .element(new String[]{"snapshot"}, named("snapshots"))
                .element(new String[]{"var"}, named("vars"))
                .element(
                        uk_org_cse_nhm_language_definition_function_num_XNumber,
                        named("weight"))
                .build();
        map("select.weighted/snapshot", "snapshot")
                .attribute("name", named("name"))
                .element(
                        uk_org_cse_nhm_language_definition_action_XDwellingAction,
                        remainder())
                .build();
        map("select.weighted/var", "var")
                .element(
                        uk_org_cse_nhm_language_definition_function_num_XNumber,
                        positional(1)).attribute("name", named("name"))
                .attribute("value", positional(1))
                .attribute("var", positional(0))
                .build();

        // uk.org.cse.nhm.language.definition.fuel.XSteppedPricing
        map("function.stepped-pricing", "function.stepped-pricing")
                .attribute("always-apply", named("always-apply"))
                .attribute("name", named("name"))
                .element(new String[]{"range"}, remainder())
                .element(new String[]{"standing-charge"},
                named("standing-charge"))
                .element(
                        uk_org_cse_nhm_language_definition_function_num_XNumber,
                        named("units"))
                .build();
        map("function.stepped-pricing/range", "range")
                .attribute("name", named("name"))
                .attribute("to", named("to"))
                .element(
                        uk_org_cse_nhm_language_definition_function_num_XNumber,
                        named("unit-price"))
                .build();

        // uk.org.cse.nhm.language.definition.context.technology.heating.XDistrictHeatingTechnology
        map("technology.district-heating", "technology.district-heating")
                .element(new String[]{"capex"}, named("capex"))
                .attribute("name", named("name"))
                .element(new String[]{"opex"}, named("opex"))
                .element(new String[]{"size"}, named("size"))
                .build();
        map("technology.district-heating/size", "size")
                .attribute("max", named("max"))
                .attribute("min", named("min"))
                .attribute("name", named("name"))
                .element(
                        uk_org_cse_nhm_language_definition_function_num_XNumber,
                        named("value"))
                .build();

        // uk.org.cse.nhm.language.definition.reporting.XMeasureCostsReport
        map("report.aggregate-measure-costs", "report.aggregate-measure-costs")
                .attribute("name", named("name"))
                .build();

        // uk.org.cse.nhm.language.definition.context.technology.heating.XWetHeatingSystemTechnology
        map("technology.wet-heating-system", "technology.wet-heating-system")
                .element(new String[]{"capex"}, named("capex"))
                .attribute("name", named("name"))
                .element(new String[]{"opex"}, named("opex"))
                .element(new String[]{"size"}, named("size"))
                .build();
        map("technology.wet-heating-system/size", "size")
                .attribute("max", named("max"))
                .attribute("min", named("min"))
                .attribute("name", named("name"))
                .element(
                        uk_org_cse_nhm_language_definition_function_num_XNumber,
                        named("value"))
                .build();

        // uk.org.cse.nhm.language.definition.function.num.XLoftInsulationThickness
        map("house.loft-insulation-thickness",
                "house.loft-insulation-thickness").attribute("name",
                        named("name"))
                .build();

        // uk.org.cse.nhm.language.definition.action.measure.heating.XHeatPumpMeasure
        map("measure.heat-pump", "measure.heat-pump")
                .element(new String[]{"capex"}, named("capex"))
                .attribute("cop", named("cop"))
                .attribute("supply-chain", named("supply-chain"))
                .attribute("cylinder-insulation", named("cylinder-insulation"))
                .attribute("cylinder-volume", named("cylinder-volume"))
                .attribute("name", named("name"))
                .element(new String[]{"opex"}, named("opex"))
                .element(new String[]{"size"}, named("size"))
                .attribute("test-flags", namedFlags("test-flags"))
                .attribute("type", named("type"))
                .attribute("update-flags", namedFlags("update-flags"))
                .build();
        map("measure.heat-pump/size", "size")
                .attribute("max", named("max"))
                .attribute("min", named("min"))
                .attribute("name", named("name"))
                .element(
                        uk_org_cse_nhm_language_definition_function_num_XNumber,
                        named("value"))
                .build();

        // uk.org.cse.nhm.language.definition.action.registers.XRegisterSetAction
        map("register.set", "register.set")
                .element(
                        uk_org_cse_nhm_language_definition_action_XDwellingAction,
                        named("action"))
                .attribute("supply-chain", named("supply-chain"))
                .attribute("name", named("name"))
                .attribute("test-flags", namedFlags("test-flags"))
                .attribute("update-flags", namedFlags("update-flags"))
                .element(
                        uk_org_cse_nhm_language_definition_function_num_XNumber,
                        named("value"))
                .build();

        // uk.org.cse.nhm.language.definition.action.XAllAction
        map("action.all-of", "action.all-of")
                .attribute("supply-chain", named("supply-chain"))
                .element(
                        uk_org_cse_nhm_language_definition_action_XDwellingAction,
                        remainder()).attribute("name", named("name"))
                .attribute("test-flags", namedFlags("test-flags"))
                .attribute("update-flags", namedFlags("update-flags"))
                .build();

        // uk.org.cse.nhm.language.definition.function.house.XBuildYear
        map("house.buildyear", "house.buildyear").attribute("name",
                named("name"))
                .build();

        // uk.org.cse.nhm.language.definition.function.num.XSnapshotDelta
        map("snapshot.delta", "snapshot.delta")
                .attribute("after", named("after"))
                .attribute("before", named("before"))
                .element(
                        uk_org_cse_nhm_language_definition_function_num_XNumber,
                        positional(0)).attribute("name", named("name"))
                .build();

        // uk.org.cse.nhm.language.definition.function.num.XAverageUValue
        map("house.u-value", "house.u-value").attribute("name", named("name"))
                .attribute("of", named("of"))
                .build();

        // uk.org.cse.nhm.language.definition.batch.inputs.combinators.XZip
        map("zip", "zip")
                .element(
                        uk_org_cse_nhm_language_definition_batch_inputs_XInputs,
                        remainder()).attribute("name", named("name"))
                .build();

        // uk.org.cse.nhm.language.definition.money.XAdditionalCost
        map("measure.with-cost", "measure.with-cost")
                .element(
                        uk_org_cse_nhm_language_definition_action_XDwellingAction,
                        positional(0))
                .element(new String[]{"cost"}, named("cost"))
                .attribute("supply-chain", named("supply-chain"))
                .attribute("counterparty", named("counterparty"))
                .attribute("name", named("name"))
                .attribute("tags", named("tags"))
                .attribute("test-flags", namedFlags("test-flags"))
                .attribute("update-flags", namedFlags("update-flags"))
                .build();

        // uk.org.cse.nhm.language.definition.money.XFullSubsidy
        map("finance.fully", "finance.fully")
                .element(
                        uk_org_cse_nhm_language_definition_action_XDwellingAction,
                        positional(0))
                .attribute("supply-chain", named("supply-chain"))
                .attribute("counterparty", named("counterparty"))
                .attribute("name", named("name"))
                .attribute("tags", named("tags"))
                .attribute("test-flags", namedFlags("test-flags"))
                .attribute("update-flags", namedFlags("update-flags"))
                .build();

        // uk.org.cse.nhm.language.definition.function.num.XPeakHeatDemand
        map("house.peak-load", "house.peak-load")
                .attribute("external-temperature",
                        named("external-temperature"))
                .attribute("internal-temperature",
                        named("internal-temperature"))
                .attribute("name", named("name"))
                .build();

        // uk.org.cse.nhm.language.definition.action.choices.XChoiceAction
        map("choice", "choice")
                .attribute("supply-chain", named("supply-chain"))
                .element(
                        uk_org_cse_nhm_language_definition_action_XDwellingAction,
                        remainder())
                .attribute("name", named("name"))
                .element(
                        uk_org_cse_nhm_language_definition_action_choices_XChoiceSelector,
                        named("select"))
                .element(new String[]{"snapshot"}, named("snapshots"))
                .attribute("test-flags", namedFlags("test-flags"))
                .attribute("update-flags", namedFlags("update-flags"))
                .element(new String[]{"var"}, named("vars"))
                .build();
        map("choice/snapshot", "snapshot")
                .attribute("name", named("name"))
                .element(
                        uk_org_cse_nhm_language_definition_action_XDwellingAction,
                        remainder())
                .build();
        map("choice/var", "var")
                .element(
                        uk_org_cse_nhm_language_definition_function_num_XNumber,
                        positional(1)).attribute("name", named("name"))
                .attribute("value", positional(1))
                .attribute("var", positional(0))
                .build();

        // uk.org.cse.nhm.language.definition.action.XHeatingTemperaturesAction
        map("action.set-heating-temperatures",
                "action.set-heating-temperatures")
                .attribute("supply-chain", named("supply-chain"))
                .attribute("living-area-temperature",
                        named("living-area-temperature"))
                .attribute("name", named("name"))
                .attribute("rest-of-dwelling-temperature",
                        named("rest-of-dwelling-temperature"))
                .attribute("temperature-difference",
                        named("temperature-difference"))
                .attribute("test-flags", namedFlags("test-flags"))
                .attribute("threshold-external-temperature",
                        named("threshold-external-temperature"))
                .attribute("update-flags", namedFlags("update-flags"))
                .build();

        // uk.org.cse.nhm.language.definition.action.XLetAction
        map("let", "let")
                .attribute("supply-chain", named("supply-chain"))
                .element(
                        uk_org_cse_nhm_language_definition_action_XDwellingAction,
                        positional(0)).attribute("name", named("name"))
                .element(new String[]{"snapshot"}, named("snapshots"))
                .attribute("test-flags", namedFlags("test-flags"))
                .attribute("update-flags", namedFlags("update-flags"))
                .element(new String[]{"var"}, named("vars"))
                .build();
        map("let/snapshot", "snapshot")
                .attribute("name", named("name"))
                .element(
                        uk_org_cse_nhm_language_definition_action_XDwellingAction,
                        remainder())
                .build();
        map("let/var", "var")
                .element(
                        uk_org_cse_nhm_language_definition_function_num_XNumber,
                        positional(1)).attribute("name", named("name"))
                .attribute("value", positional(1))
                .attribute("var", positional(0))
                .build();

        // uk.org.cse.nhm.language.definition.XScenario
        map("scenario", "scenario")
                .attribute("demand-temperature", named("demand-temperature"))
                .attribute("end-date", named("end-date"))
                .attribute("quantum", named("quantum"))
                .attribute("name", named("name"))
                .element(new String[]{"description"}, named("scenarioDescription"))
                .attribute("seed", named("seed"))
                .attribute("start-date", named("start-date"))
                .attribute("stockID", named("stock-id"))
                .element(rootElements, remainder())
                .build();

        // uk.org.cse.nhm.language.definition.reporting.XNumberProbe
        map("function.probe", "function.probe")
                .element(
                        uk_org_cse_nhm_language_definition_function_num_XNumber,
                        positional(0)).attribute("name", named("name"))
                .build();

        // uk.org.cse.nhm.language.definition.function.bool.house.XFlagIs
        map("house.test-flag", "house.test-flag")
                .attribute("condition", named("condition"))
                .attribute("name", named("name"))
                .attribute("named", named("named"))
                .build();

        // uk.org.cse.nhm.language.definition.function.num.XRegisterGet
        map("register.get", "register.get")
                .attribute("default", named("default"))
                .attribute("name", named("name"))
                .build();

        // uk.org.cse.nhm.language.definition.action.XConstructAction
        map("action.construct", "action.construct").attribute("name",
                named("name"))
                .build();

        // uk.org.cse.nhm.language.definition.action.measure.heating.XStandardBoilerMeasure
        map("measure.standard-boiler", "measure.standard-boiler")
                .element(new String[]{"capex"}, named("capex"))
                .attribute("supply-chain", named("supply-chain"))
                .attribute("cylinder-insulation-thickness",
                        named("cylinder-insulation-thickness"))
                .attribute("cylinder-volume", named("cylinder-volume"))
                .attribute("efficiency", named("efficiency"))
                .attribute("fuel", named("fuel"))
                .attribute("name", named("name"))
                .element(new String[]{"opex"}, named("opex"))
                .attribute("required-exterior-area",
                        named("required-exterior-area"))
                .attribute("required-floor-area", named("required-floor-area"))
                .element(new String[]{"size"}, named("size"))
                .attribute("test-flags", namedFlags("test-flags"))
                .attribute("update-flags", namedFlags("update-flags"))
                .build();
        map("measure.standard-boiler/size", "size")
                .attribute("max", named("max"))
                .attribute("min", named("min"))
                .attribute("name", named("name"))
                .element(
                        uk_org_cse_nhm_language_definition_function_num_XNumber,
                        named("value"))
                .build();

        // uk.org.cse.nhm.language.definition.reporting.aggregate.XSumAggregation
        map("aggregate.sum", "aggregate.sum")
                .attribute("name", named("name"))
                .element(
                        uk_org_cse_nhm_language_definition_function_num_XNumber,
                        positional(0))
                .build();

        // uk.org.cse.nhm.language.definition.group.XRandomSampleGroup
        map("group.random-sample", "group.random-sample")
                .attribute("name", named("name"))
                .attribute("proportion", named("proportion"))
                .element(uk_org_cse_nhm_language_definition_group_XGroup,
                        named("source"))
                .build();

        // uk.org.cse.nhm.language.definition.reporting.modes.XSumMode
        map("mode.sum", "mode.sum").attribute("name", named("name"))
                .build();

        // uk.org.cse.nhm.language.definition.action.measure.insulation.XModifyUValueAction
        map("action.set-wall-u", "action.set-wall-u")
                .attribute("supply-chain", named("supply-chain"))
                .attribute("name", named("name"))
                .attribute("test-flags", namedFlags("test-flags"))
                .attribute("uValue", named("uValue"))
                .attribute("update-flags", namedFlags("update-flags"))
                .build();

        // uk.org.cse.nhm.language.definition.function.num.XCapex
        map("cost.capex", "cost.capex").attribute("name", named("name"))
                .build();

        // uk.org.cse.nhm.language.definition.function.num.basic.XPower
        map("pow", "pow")
                .element(
                        uk_org_cse_nhm_language_definition_function_num_XNumber,
                        remainder()).attribute("name", named("name"))
                .build();

        // uk.org.cse.nhm.language.definition.reporting.XTechnologyCountReport
        map("report.technology-counts", "report.technology-counts").attribute(
                "name", named("name"))
                .build();

        final OutputRule handleIncludes = new OutputRule() {
            @Override
            public void handleAttribute(final String attribute, final InvocationBuilder builder) {
                // process the uri

                // nhm:scenario/name/Technology Costs Specified By DECC/1
                if (attribute.startsWith("nhm:")) {
                    final String[] parts = attribute.split("/");
                    if (parts.length >= 4) {
                        builder.named("name", Atom.create(parts[2]));
                        builder.named("version", Atom.create(parts[3]));
                    }
                }
            }

            @Override
            protected void handleNode(final InvocationBuilder builder, final Node node) {

            }
        };

        map("policy", "policy")
                .attribute("name", named("name"))
                .element(new String[]{"target"}, remainder());

        map("include", "include")
                .attribute("href", handleIncludes);

        map("xi:include", "include")
                .attribute("href", handleIncludes);

        map("function.steps/step")
                .text(remainder());

        sortMappings();
    }

    public static String convert(final String xml) {
        final ScenarioConverter4_1_to_4_2 converter = new ScenarioConverter4_1_to_4_2();
        final DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        try {
            final DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

            final Document doc = dBuilder.parse(
                    new InputSource(new StringReader(xml)));

            final Element documentElement = doc.getDocumentElement();

            documentElement.normalize();

            final Node node = converter.convert(documentElement);

            return PrettyPrinter.print(node);
        } catch (final Throwable e) {
            e.printStackTrace();
            return xml;
        }
    }

    public static void main(final String[] args) throws IOException, ParserConfigurationException {
        // walk files and try to convert them
        System.err.println("Converting all scenarios in " + args[0]);
        final ScenarioConverter4_1_to_4_2 converter = new ScenarioConverter4_1_to_4_2();
        final DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        final DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

        Files.walkFileTree(Paths.get(args[0]), new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(final Path file, final BasicFileAttributes attrs) throws IOException {
                if (file.getFileName().toString().endsWith(".xml")) {
                    System.out.println(file);
                    try {

                        final Document doc = dBuilder.parse(
                                new InputSource(
                                        new InputStreamReader(Files.newInputStream(file), StandardCharsets.UTF_8)
                                )
                        );

                        final Element documentElement = doc.getDocumentElement();

                        documentElement.normalize();

                        final Node node = converter.convert(documentElement);

                        final Path convertedFilePath = file.resolveSibling(file.getFileName().toString().replace(".xml", ".s"));

                        try (final OutputStream out = Files.newOutputStream(convertedFilePath)) {
                            try (final PrintWriter writer = new PrintWriter(out)) {
                                writer.print(PrettyPrinter.print(node));
                                writer.flush();
                            }
                        }
                    } catch (final Exception e) {
                        System.err.println("Malformed XML in " + file + ": " + e.getMessage());
                    }
                }

                return FileVisitResult.CONTINUE;
            }
        });
    }
}
