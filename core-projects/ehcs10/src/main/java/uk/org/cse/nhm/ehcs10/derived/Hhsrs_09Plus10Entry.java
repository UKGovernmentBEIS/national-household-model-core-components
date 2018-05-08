package uk.org.cse.nhm.ehcs10.derived;

import uk.org.cse.nhm.ehcs10.derived.types.Enum31;
import uk.org.cse.nhm.spss.wrap.SavVariableMapping;
import uk.org.cse.stockimport.spss.SurveyEntry;

public interface Hhsrs_09Plus10Entry extends SurveyEntry {

    @SavVariableMapping("NHSR_26")
    public Integer getHHSRS_OverallNumberOfHazards_26HazardModel_();

    @SavVariableMapping("NHSR_15")
    public Integer getHHSRS_OverallNumberOfHazards_15HazardModel_();

    @SavVariableMapping("HSRCST15")
    public Double getHHSRS_CostToMakeSafe_15HazardModel_();

    @SavVariableMapping("HSRCOST")
    public Double getHHSRS_CostToMakeSafe_26HazardModel_();

    @SavVariableMapping("HSRDHYG")
    public Enum31 getHealth_SafetyRating_DomesticHygiene();

    @SavVariableMapping("HSRHOTSF")
    public Enum31 getHealth_SafetyRating_HotSurfaces();

    @SavVariableMapping("HSRPHYG")
    public Enum31 getHealth_SafetyRating_Sanitation();

    @SavVariableMapping("HSRALL26")
    public Enum31 getHHSRS_Overall_26HazardModel_();

    @SavVariableMapping("HSRELEC")
    public Enum31 getHealth_SafetyRating_ElectricalProblems();

    @SavVariableMapping("HSRALL15")
    public Enum31 getHHSRS_Overall_15HazardModel_();

    @SavVariableMapping("HSRSTRUC")
    public Enum31 getHealth_SafetyRating_StructuralCollapse();

    @SavVariableMapping("HSRBTLEV")
    public Enum31 getHealth_SafetyRating_FallsBetweenLevels();

    @SavVariableMapping("HSREXHT")
    public Enum31 getHealth_SafetyRating_ExcessHeat();

    @SavVariableMapping("HSRUNGAS")
    public Enum31 getHealth_SafetyRating_UncombustedFuelGas();

    @SavVariableMapping("HSRCLD09")
    public Enum31 getHealth_SafetyRating_ColdHomes();

    @SavVariableMapping("HSREXPLO")
    public Enum31 getHealth_SafetyRating_Explosions();

    @SavVariableMapping("HSRFOOD")
    public Enum31 getHealth_SafetyRating_FoodSafety();

    @SavVariableMapping("HSRERGO")
    public Enum31 getHealth_SafetyRating_Ergonomics();

    @SavVariableMapping("HSRRADON")
    public Enum31 getHealth_SafetyRating_Radon();

    @SavVariableMapping("HSRSTAIR")
    public Enum31 getHealth_SafetyRating_FallsOnStairs();

    @SavVariableMapping("HSRCO")
    public Enum31 getHealth_SafetyRating_CarbonMonoxide();

    @SavVariableMapping("HSRLIGHT")
    public Enum31 getHealth_SafetyRating_Lighting();

    @SavVariableMapping("HSRWATER")
    public Enum31 getHealth_SafetyRating_WaterSupply();

    @SavVariableMapping("HSRDAMP")
    public Enum31 getHealthAndSafetyRating_DampAndMould();

    @SavVariableMapping("HSRBATH")
    public Enum31 getHealthAndSafetyRating_FallsAssociatedWithBaths();

    @SavVariableMapping("HSRONLEV")
    public Enum31 getHealth_SafetyRating_FallsOnTheLevel();

    @SavVariableMapping("HSRENTRY")
    public Enum31 getHealthAndSafetyRating_EntryByIntruders();

    @SavVariableMapping("HSRCOLL")
    public Enum31 getHealthAndSafetyRating_CollisionAndEntrapment();

    @SavVariableMapping("HSRCROWD")
    public Enum31 getHealth_SafetyRating_Overcrowding();

    @SavVariableMapping("HSRLEAD")
    public Enum31 getHealth_SafetyRating_Lead();

    @SavVariableMapping("HSRNOISE")
    public Enum31 getHealthAndSafetyRating_Noise();

    @SavVariableMapping("HSRFIRE")
    public Enum31 getHealth_SafetyRating_Fire();

}
