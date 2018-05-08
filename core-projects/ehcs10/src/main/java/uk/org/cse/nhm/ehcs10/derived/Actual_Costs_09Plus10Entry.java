package uk.org.cse.nhm.ehcs10.derived;

import uk.org.cse.nhm.spss.wrap.SavVariableMapping;
import uk.org.cse.stockimport.spss.SurveyEntry;

public interface Actual_Costs_09Plus10Entry extends SurveyEntry {

    @SavVariableMapping("NDOORF")
    public Double getInternalDoorCosts();

    @SavVariableMapping("XNOMUF")
    public Double getUrgentExternalCosts();

    @SavVariableMapping("XDAMBF")
    public Double getDpcBasicCosts();

    @SavVariableMapping("XRUSRF")
    public Double getRoofStructureComprehensiveCosts();

    @SavVariableMapping("CLITCF")
    public Double getCommonPartsLightingRepairCosts();

    @SavVariableMapping("XCHIRF")
    public Double getChimneysComprehensiveCosts();

    @SavVariableMapping("XDOORF")
    public Double getExternalDoorsComprehensiveCosts();

    @SavVariableMapping("XWASUF")
    public Double getWallStructureUrgentCosts();

    @SavVariableMapping("CWINCF")
    public Double getCommonPartsWindowsRepairCosts();

    @SavVariableMapping("PRERF")
    public Double getPrelimCostsForComprehensiveRepairs();

    @SavVariableMapping("NSTAF")
    public Double getInternalStairCosts();

    @SavVariableMapping("XWINBF")
    public Double getWindowsBasicCosts();

    @SavVariableMapping("XNOMBF")
    public Double getBasicExternalCosts();

    @SavVariableMapping("PREBF")
    public Double getPrelimCostsForBasicRepairs();

    @SavVariableMapping("XRUFBF")
    public Double getRoofFeaturesBasicCosts();

    @SavVariableMapping("CCEICF")
    public Double getCommonPartsCeilingRepairCosts();

    @SavVariableMapping("AXCBF")
    public Double getAccessCostsForBasicRepairs();

    @SavVariableMapping("XWINUF")
    public Double getWindowsUrgentCosts();

    @SavVariableMapping("XRUFRF")
    public Double getRoofFeaturesComprehensiveCosts();

    @SavVariableMapping("CDOOCF")
    public Double getCommonPartsDoorsRepairCosts();

    @SavVariableMapping("XRUCRF")
    public Double getRoofCoveringComprehensiveCosts();

    @SavVariableMapping("XCHIUF")
    public Double getChimneysUrgentCosts();

    @SavVariableMapping("XWAFRF")
    public Double getWallFinishComprehensiveCosts();

    @SavVariableMapping("KAMCF")
    public Double getKitchenCosts();

    @SavVariableMapping("XBAYBF")
    public Double getDormers_BaysBasicCosts();

    @SavVariableMapping("CNOMF")
    public Double getTotalCommonPartsAndSharedFacilitiesCosts();

    @SavVariableMapping("XWASBF")
    public Double getWallStrucBasicCosts();

    @SavVariableMapping("ANOMF")
    public Double getTotalAmenitiesCosts();

    @SavVariableMapping("XWINRF")
    public Double getWindowsComprehensiveCosts();

    @SavVariableMapping("AXCUF")
    public Double getAccessCostsForUrgentRepairs();

    @SavVariableMapping("SRVRF")
    public Double getServicesCosts();

    @SavVariableMapping("NCEIF")
    public Double getInternalCeilingCosts();

    @SavVariableMapping("XWASRF")
    public Double getWallStructureComprehensiveCosts();

    @SavVariableMapping("STRUCF")
    public Double getStructuralWorksCosts();

    @SavVariableMapping("XRUCUF")
    public Double getRoofCoveringUrgentCosts();

    @SavVariableMapping("SHARCF")
    public Double getSharedFacilitiesCosts();

    @SavVariableMapping("CWALCF")
    public Double getCommonPartsWallRepairCosts();

    @SavVariableMapping("XRUFUF")
    public Double getRoofFeaturesUrgentCosts();

    @SavVariableMapping("PREUF")
    public Double getPrelimCostsForUrgentRepairs();

    @SavVariableMapping("XPLOBF")
    public Double getPlotLevelsBasicCosts();

    @SavVariableMapping("XCHIBF")
    public Double getChimneysBasicCosts();

    @SavVariableMapping("UPCUF")
    public Double getUpliftCostsForUrgentRepairs();

    @SavVariableMapping("XDOOUF")
    public Double getExternalDoorsUrgentCosts();

    @SavVariableMapping("XDOOBF")
    public Double getExternalDoorsBasicCosts();

    @SavVariableMapping("BAMCF")
    public Double getBathroomCosts();

    @SavVariableMapping("XRUSBF")
    public Double getRoofStructureBasicCosts();

    @SavVariableMapping("XDAMRF")
    public Double getDpcComprehensiveCosts();

    @SavVariableMapping("CFLOCF")
    public Double getCommonPartsFloorRepairCosts();

    @SavVariableMapping("TECOBF")
    public Double getTotalBasicCosts();

    @SavVariableMapping("XRUCBF")
    public Double getRoofCoveringBasicCosts();

    @SavVariableMapping("XWAFBF")
    public Double getWallFinishBasicCosts();

    @SavVariableMapping("TECORF")
    public Double getTotalComprehensiveCosts();

    @SavVariableMapping("XNOMCF")
    public Double getComprehensiveExternalCosts();

    @SavVariableMapping("TECOUF")
    public Double getTotalUrgentCosts();

    @SavVariableMapping("XBAYRF")
    public Double getDormers_BaysComprehensiveCosts();

    @SavVariableMapping("XBAYUF")
    public Double getDormers_BaysUrgentCosts();

    @SavVariableMapping("AXCRF")
    public Double getAccessCostsForComprehensiveRepairs();

    @SavVariableMapping("XDAMUF")
    public Double getDpcUrgentCosts();

    @SavVariableMapping("XWAFUF")
    public Double getWallFinishUrgentCosts();

    @SavVariableMapping("UPCRF")
    public Double getUpliftCostsForComprehensiveRepairs();

    @SavVariableMapping("NFLOF")
    public Double getInternalFloorCosts();

    @SavVariableMapping("NWALF")
    public Double getInternalWallsCosts();

    @SavVariableMapping("XRUSUF")
    public Double getRoofStructureUrgentCosts();

    @SavVariableMapping("CBALCF")
    public Double getCommonPartsBalustradesRepairCosts();

    @SavVariableMapping("CFIRCF")
    public Double getFirePrecautionsToCommonPartsCosts();

    @SavVariableMapping("UPCBF")
    public Double getUpliftCostsForBasicRepairs();

    @SavVariableMapping("NNOMF")
    public Double getTotalInternalFabricCosts();

}
