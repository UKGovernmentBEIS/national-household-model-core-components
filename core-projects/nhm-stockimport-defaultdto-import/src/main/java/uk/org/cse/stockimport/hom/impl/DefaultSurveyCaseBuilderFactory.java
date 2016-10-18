package uk.org.cse.stockimport.hom.impl;

import uk.org.cse.stockimport.hom.ISurveyCaseBuilder;
import uk.org.cse.stockimport.hom.ISurveyCaseBuilderFactory;
import uk.org.cse.stockimport.hom.impl.steps.AdditionalHouseCasePropertyStep;
import uk.org.cse.stockimport.hom.impl.steps.BasicAttributesBuildStep;
import uk.org.cse.stockimport.hom.impl.steps.ElevationBuildStep;
import uk.org.cse.stockimport.hom.impl.steps.FinancialAttributesBuilderStep;
import uk.org.cse.stockimport.hom.impl.steps.InternalWallBuildStep;
import uk.org.cse.stockimport.hom.impl.steps.PeopleBuildStep;
import uk.org.cse.stockimport.hom.impl.steps.RoofBuildStep;
import uk.org.cse.stockimport.hom.impl.steps.StoreyBuildStep;
import uk.org.cse.stockimport.hom.impl.steps.StructureInitializingBuildStep;
import uk.org.cse.stockimport.hom.impl.steps.WarningsStep;
import uk.org.cse.stockimport.hom.impl.steps.imputation.DoorWranglingStep;
import uk.org.cse.stockimport.hom.impl.steps.imputation.MainImputationStep;
import uk.org.cse.stockimport.hom.impl.steps.services.CookerBuildStep;
import uk.org.cse.stockimport.hom.impl.steps.services.HotWaterBuildStep;
import uk.org.cse.stockimport.hom.impl.steps.services.LightingBuildStep;
import uk.org.cse.stockimport.hom.impl.steps.services.SpaceHeatingBuildStep;
import uk.org.cse.stockimport.hom.impl.steps.services.heating.HeatSourceBuilder;
import uk.org.cse.stockimport.hom.impl.steps.services.heating.RoomHeaterBuilder;
import uk.org.cse.stockimport.hom.impl.steps.services.heating.SecondaryHeatingSystemBuilder;
import uk.org.cse.stockimport.hom.impl.steps.services.heating.StorageHeaterBuilder;
import uk.org.cse.stockimport.hom.impl.steps.services.heating.WarmAirSystemBuilder;
import uk.org.cse.stockimport.imputation.ISchemaForImputation;
import uk.org.cse.stockimport.imputation.house.HousePropertyImputer;

public class DefaultSurveyCaseBuilderFactory implements ISurveyCaseBuilderFactory {    
    /**
     * Returns an instance of an {@link ISurveyCaseBuilder} set up with the default build steps.
     * 
     * @return
     * @since 1.5.0
     */
    @Override
	public ISurveyCaseBuilder getSurveyCaseBuilder(final ISchemaForImputation imputationSchema) {
        //TODO: Should be initialise SurveyCaseBuilder via spring ctx.
        final SurveyCaseBuilder caseBuilder = new SurveyCaseBuilder();
        
        caseBuilder.addStep(new BasicAttributesBuildStep());
        caseBuilder.addStep(new AdditionalHouseCasePropertyStep());
        caseBuilder.addStep(new StructureInitializingBuildStep(new HousePropertyImputer(imputationSchema.getHousePropertyTables())));
        caseBuilder.addStep(new StoreyBuildStep());
        caseBuilder.addStep(new ElevationBuildStep());
        caseBuilder.addStep(new RoofBuildStep());
        caseBuilder.addStep(new DoorWranglingStep());
        caseBuilder.addStep(new MainImputationStep(imputationSchema)); 
        caseBuilder.addStep(new LightingBuildStep());
        caseBuilder.addStep(new CookerBuildStep());
        caseBuilder.addStep(new InternalWallBuildStep());
        caseBuilder.addStep(new FinancialAttributesBuilderStep());
        caseBuilder.addStep(new PeopleBuildStep());
        caseBuilder.addStep(new WarningsStep(caseBuilder.getExistingStepIdentifiers()));
        
        {
            final SpaceHeatingBuildStep spaceHeating = new SpaceHeatingBuildStep();
            
            spaceHeating.setHeatSourceBuilder(new HeatSourceBuilder());
            spaceHeating.setRoomHeaterBuilder(new RoomHeaterBuilder());
            spaceHeating.setSecondaryBuilder(new SecondaryHeatingSystemBuilder());
            spaceHeating.setStorageHeaterBuilder(new StorageHeaterBuilder());
            spaceHeating.setWarmAirBuilder(new WarmAirSystemBuilder());
            
            final HotWaterBuildStep waterHeating = new HotWaterBuildStep();
            waterHeating.setHeatSourceBuilder(spaceHeating.getHeatSourceBuilder());
            
            caseBuilder.addStep(spaceHeating);
            caseBuilder.addStep(waterHeating);
        }

        caseBuilder.initialize();
        
        return caseBuilder;
    }
}
