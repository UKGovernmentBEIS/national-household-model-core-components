package uk.org.cse.nhm.ehcs10.derived;

import uk.org.cse.nhm.spss.wrap.SavVariableMapping;
import uk.org.cse.stockimport.spss.SurveyEntry;

public interface Standardised_Costs_09Plus10Entry extends SurveyEntry {

    @SavVariableMapping("XNOMRCE")
    public Double getComprehensiveExternalCosts();

    @SavVariableMapping("KXSNE")
    public Double getKitchenInstallationCosts();

    @SavVariableMapping("XWINRE")
    public Double getWindowsComprehensiveCosts();

    @SavVariableMapping("XWINUE")
    public Double getWindowsUrgentCosts();

    @SavVariableMapping("CWINCE")
    public Double getCommonPartsWindowsRepairCosts();

    @SavVariableMapping("SRVRE")
    public Double getServicesCosts();

    @SavVariableMapping("BAMCE")
    public Double getBathroomCosts();

    @SavVariableMapping("BXSNE")
    public Double getBathroomInstallationCosts();

    @SavVariableMapping("XRUSRE")
    public Double getRoofStructureComprehensiveCosts();

    @SavVariableMapping("XWAFBE")
    public Double getWallFinishBasicCosts();

    @SavVariableMapping("XDOOUE")
    public Double getExternalDoorsUrgentCosts();

    @SavVariableMapping("XDOORE")
    public Double getExternalDoorsComprehensiveCosts();

    @SavVariableMapping("SHARCE")
    public Double getSharedFacilitiesCosts();

    @SavVariableMapping("TECOUE")
    public Double getTotalUrgentCosts();

    @SavVariableMapping("CFLOCE")
    public Double getCommonPartsFloorRepairCosts();

    @SavVariableMapping("TECORE")
    public Double getTotalComprehensiveCosts();

    @SavVariableMapping("CWALCE")
    public Double getCommonPartsWallRepairCosts();

    @SavVariableMapping("UPCRE")
    public Double getUpliftCostsForComprehensiveRepairs();

    @SavVariableMapping("XCHIUE")
    public Double getChimneysUrgentCosts();

    @SavVariableMapping("PRERE")
    public Double getPrelimCostsForComprehensiveRepairs();

    @SavVariableMapping("NNOME")
    public Double getTotalInternalFabricCosts();

    @SavVariableMapping("XRUSUE")
    public Double getRoofStructureUrgentCosts();

    @SavVariableMapping("XWINBE")
    public Double getWindowsBasicCosts();

    @SavVariableMapping("CNOME")
    public Double getTotalCommonPartsAndSharedFacilitiesCosts();

    @SavVariableMapping("AXCUE")
    public Double getAccessCostsForUrgentRepairs();

    @SavVariableMapping("XRUCBE")
    public Double getRoofCoveringBasicCosts();

    @SavVariableMapping("TECOBE")
    public Double getTotalBasicCosts();

    @SavVariableMapping("XPLOBE")
    public Double getPlotLevelsBasicCosts();

    @SavVariableMapping("UPCBE")
    public Double getUpliftCostsForBasicRepairs();

    @SavVariableMapping("XNOMBE")
    public Double getBasicExternalCosts();

    @SavVariableMapping("XBAYBE")
    public Double getDormers_BaysBasicCosts();

    @SavVariableMapping("XBAYRE")
    public Double getDormers_BaysComprehensiveCosts();

    @SavVariableMapping("NWALE")
    public Double getInternalWallsCosts();

    @SavVariableMapping("PREBE")
    public Double getPrelimCostsForBasicRepairs();

    @SavVariableMapping("NSTAE")
    public Double getInternalStairCosts();

    @SavVariableMapping("ANOME")
    public Double getTotalAmenitiesCosts();

    @SavVariableMapping("XDAMBE")
    public Double getDpcBasicCosts();

    @SavVariableMapping("XWAFUE")
    public Double getWallFinishUrgentCosts();

    @SavVariableMapping("XRUFRE")
    public Double getRoofFeaturesComprehensiveCosts();

    @SavVariableMapping("CCEICE")
    public Double getCommonPartsCeilingRepairCosts();

    @SavVariableMapping("XDAMRE")
    public Double getDpcComprehensiveCosts();

    @SavVariableMapping("CBALCE")
    public Double getCommonPartsBalustradesRepairCosts();

    @SavVariableMapping("XWAFRE")
    public Double getWallFinishComprehensiveCosts();

    @SavVariableMapping("NDOORE")
    public Double getInternalDoorCosts();

    @SavVariableMapping("XCHIBE")
    public Double getChimneysBasicCosts();

    @SavVariableMapping("XRUSBE")
    public Double getRoofStructureBasicCosts();

    @SavVariableMapping("CLITCE")
    public Double getCommonPartsLightingRepairCosts();

    @SavVariableMapping("XRUCUE")
    public Double getRoofCoveringUrgentCosts();

    @SavVariableMapping("XBAYUE")
    public Double getDormers_BaysUrgentCosts();

    @SavVariableMapping("XRUCRE")
    public Double getRoofCoveringComprehensiveCosts();

    @SavVariableMapping("AXCRE")
    public Double getAccessCostsForComprehensiveRepairs();

    @SavVariableMapping("STRUCE")
    public Double getStructuralWorksCosts();

    @SavVariableMapping("UPCUE")
    public Double getUpliftCostsForUrgentRepairs();

    @SavVariableMapping("CDOOCE")
    public Double getCommonPartsDoorsRepairCosts();

    @SavVariableMapping("PREUE")
    public Double getPrelimCostsForUrgentRepairs();

    @SavVariableMapping("XRUFUE")
    public Double getRoofFeaturesUrgentCosts();

    @SavVariableMapping("AXCBE")
    public Double getAccessCostsForBasicRepairs();

    @SavVariableMapping("XWASRE")
    public Double getWallStructureComprehensiveCosts();

    @SavVariableMapping("XNOMUE")
    public Double getUrgentExternalCosts();

    @SavVariableMapping("XCHIRE")
    public Double getChimneysComprehensiveCosts();

    @SavVariableMapping("KAMCE")
    public Double getKitchenCosts();

    @SavVariableMapping("CFIRCE")
    public Double getFirePrecautionsToCommonPartsCosts();

    @SavVariableMapping("XDAMUE")
    public Double getDpcUrgentCosts();

    @SavVariableMapping("XWASUE")
    public Double getWallStructureUrgentCosts();

    @SavVariableMapping("NCEIE")
    public Double getInternalCeilingCosts();

    @SavVariableMapping("XRUFBE")
    public Double getRoofFeaturesBasicCosts();

    @SavVariableMapping("XDOOBE")
    public Double getExternalDoorsBasicCosts();

    @SavVariableMapping("XWASBE")
    public Double getWallStrucBasicCosts();

    @SavVariableMapping("NFLOE")
    public Double getInternalFloorCosts();

}
