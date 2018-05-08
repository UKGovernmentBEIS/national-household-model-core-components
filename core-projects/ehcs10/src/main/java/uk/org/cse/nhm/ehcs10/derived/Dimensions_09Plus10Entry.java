package uk.org.cse.nhm.ehcs10.derived;

import uk.org.cse.nhm.spss.wrap.SavVariableMapping;
import uk.org.cse.stockimport.spss.SurveyEntry;

public interface Dimensions_09Plus10Entry extends SurveyEntry {

    @SavVariableMapping("FLMLOW")
    public Integer getLowestFloorOfMainPart();

    @SavVariableMapping("FLOORSTR")
    public Integer getFloorAreaOccupiedByStairs();

    @SavVariableMapping("FLMHIGH")
    public Integer getHighestFloorOfMainPart();

    @SavVariableMapping("NFLORA")
    public Integer getNumberOfFloorsInTheAdditionalPart();

    @SavVariableMapping("FLOORBAL")
    public Integer getFloorAreaOccupiedByBalconies();

    @SavVariableMapping("FLAHIGH")
    public Integer getHighestFloorOfAdditionalPart();

    @SavVariableMapping("NFLORM")
    public Integer getNumberOfFloorsInTheMainPart();

    @SavVariableMapping("FLOORGAR")
    public Integer getFloorAreaOccupiedByGarages();

    @SavVariableMapping("FLALOW")
    public Integer getLowestFloorOfAdditionalPart();

    @SavVariableMapping("EFRFPAR")
    public Double getExternalFrontRoofPlanArea();

    @SavVariableMapping("FLOORPAR")
    public Double getFloorAreaOccupiedByPartitionWalls();

    @SavVariableMapping("EBRFPAR")
    public Double getExternalBackRoofPlanArea();

    @SavVariableMapping("EMEVEHT")
    public Double getExternalMainEavesHeight();

    @SavVariableMapping("EFRFSAR")
    public Double getExternalFrontRoofSlopeArea();

    @SavVariableMapping("EBWALAR")
    public Double getExternalBackWallArea();

    @SavVariableMapping("EFEVEPE")
    public Double getExternalFrontEavesPerimeter();

    @SavVariableMapping("EBWINAR")
    public Double getExternalBackWindowArea();

    @SavVariableMapping("EFWINAR")
    public Double getExternalFrontWindowArea();

    @SavVariableMapping("INTWALAR")
    public Double getInternalWallArea();

    @SavVariableMapping("EAEVEHT")
    public Double getExternalBackEavesHeight();

    @SavVariableMapping("EFDPCPE")
    public Double getExternalFrontDPCPerimeter();

    @SavVariableMapping("EFWALAR")
    public Double getExternalFrontWallArea();

    @SavVariableMapping("EBEVEPE")
    public Double getExternalBackEavesPerimeter();

    @SavVariableMapping("FLOORARE")
    public Double getTotalFloorArea();

    @SavVariableMapping("EBDPCPE")
    public Double getExternalBackDPCPerimeter();

    @SavVariableMapping("V3_A")
    public Double getTotalFloorArea_50_ExternalWall();

    @SavVariableMapping("EBRFSAR")
    public Double getExternalBackroofSlopeArea();

}
