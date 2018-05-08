package uk.org.cse.nhm.simulation.reporting.state;

import java.util.Set;

import javax.inject.Inject;

import com.google.common.collect.ImmutableSet;

import uk.org.cse.hom.housepropertystore.IHouseProperties;
import uk.org.cse.hom.money.FinancialAttributes;
import uk.org.cse.nhm.hom.BasicCaseAttributes;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologyModel;
import uk.org.cse.nhm.hom.emf.util.ITechnologyOperations;
import uk.org.cse.nhm.hom.people.People;
import uk.org.cse.nhm.hom.structure.StructureModel;
import uk.org.cse.nhm.ipc.api.tasks.report.ILogEntryHandler;
import uk.org.cse.nhm.logging.logentry.BasicAttributesLogEntry;
import uk.org.cse.nhm.simulator.state.IComponents;
import uk.org.cse.nhm.simulator.state.IDimension;

public class BasicAttributesFlattener extends NormalizingFlattener<BasicAttributesLogEntry.Details> {

    private final ILogEntryHandler loggingService;

    private final IDimension<BasicCaseAttributes> basicAttributes;
    private final IDimension<ITechnologyModel> technologyModel;
    private final IDimension<StructureModel> structureModel;
    private final IDimension<People> peopleModel;
    private final IDimension<FinancialAttributes> financialAttributes;
    private final IDimension<IHouseProperties> additionalHouseProperties;
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(BasicAttributesFlattener.class);

    private final ITechnologyOperations operations;

    @Inject
    public BasicAttributesFlattener(
            ITechnologyOperations operations,
            IDimension<BasicCaseAttributes> basicAttributes,
            IDimension<ITechnologyModel> technologyModel,
            IDimension<StructureModel> structureModel,
            IDimension<People> peopleModel,
            IDimension<FinancialAttributes> financialAttributes,
            IDimension<IHouseProperties> additionalHouseProperties,
            ILogEntryHandler loggingService) {
        this.operations = operations;
        this.basicAttributes = basicAttributes;
        this.technologyModel = technologyModel;
        this.structureModel = structureModel;
        this.peopleModel = peopleModel;
        this.financialAttributes = financialAttributes;
        this.additionalHouseProperties = additionalHouseProperties;
        this.loggingService = loggingService;
    }

    @Override
    protected void valueNormalized(BasicAttributesLogEntry.Details value, int id) {
        final BasicAttributesLogEntry sle = new BasicAttributesLogEntry(id, value);
        loggingService.acceptLogEntry(sle);
    }

    @Override
    protected BasicAttributesLogEntry.Details getValue(final IComponents dwelling) {
        try {
            return new BasicAttributesLogEntry.Details(
                    operations,
                    dwelling.get(basicAttributes),
                    dwelling.get(technologyModel),
                    dwelling.get(structureModel),
                    dwelling.get(peopleModel),
                    dwelling.get(financialAttributes),
                    dwelling.get(additionalHouseProperties));
        } catch (Throwable th) {
            log.debug("Fatal exception when constructing details entry", th);
            return null;
        }
    }

    @Override
    public Set<IDimension<?>> getComponents() {
        return ImmutableSet.<IDimension<?>>of(basicAttributes, technologyModel, structureModel, peopleModel, financialAttributes);
    }
}
