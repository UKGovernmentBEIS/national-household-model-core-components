package uk.org.cse.nhm.ehcs10.physical;

import uk.org.cse.nhm.ehcs10.derived.types.Enum10;
import uk.org.cse.nhm.ehcs10.physical.types.Enum1224;
import uk.org.cse.nhm.ehcs10.physical.types.Enum1225;
import uk.org.cse.nhm.ehcs10.physical.types.Enum1230;
import uk.org.cse.nhm.spss.wrap.SavVariableMapping;
import uk.org.cse.stockimport.spss.SurveyEntry;

public interface CommacEntry extends SurveyEntry {

    @SavVariableMapping("FCPFLRRP")
    public Integer getFloors_Treads_RepairSurface();

    @SavVariableMapping("FCPFLRRN")
    public Integer getFloors_Treads_RenewSurface();

    @SavVariableMapping("FCPCLNPA")
    public Integer getCeilings_Soffits_RepaintSurface();

    @SavVariableMapping("FCPCLNMO")
    public Integer getCeilings_Soffits_ModifyStructure();

    @SavVariableMapping("FCPAXDRN")
    public Integer getAccessDoors_Screens_Replace();

    @SavVariableMapping("FCPAXWRP")
    public Integer getAccesswayWindows_Repair();

    @SavVariableMapping("FCPWLSRP")
    public Integer getWalls_RepairSurface();

    @SavVariableMapping("FCPWLSMO")
    public Integer getWalls_ModifyStructure();

    @SavVariableMapping("FCPAXLSW")
    public Integer getAccesswayLighting_ReplaceLightSwitches();

    @SavVariableMapping("FCPBALRN")
    public Integer getBalustrades_Replace();

    @SavVariableMapping("FCPAXDPA")
    public Integer getAccessDoors_Screens_Repaint();

    @SavVariableMapping("FCPAXDRP")
    public Integer getAccessDoors_Screens_Repair_Rehang();

    @SavVariableMapping("FCPAXLFT")
    public Integer getAccesswayLighting_ReplaceLightFittings();

    @SavVariableMapping("FCPBALRP")
    public Integer getBalustrades_Repair();

    @SavVariableMapping("FCPWLSPA")
    public Integer getWalls_RepaintSurface();

    @SavVariableMapping("FCPCLNRN")
    public Integer getCeilings_Soffits_RenewSurface();

    @SavVariableMapping("FCPFLRMO")
    public Integer getFloors_Treads_ModifyStructure();

    @SavVariableMapping("FCPAXWPA")
    public Integer getAccesswayWindows_Repaint();

    @SavVariableMapping("FCPCLNRP")
    public Integer getCeilings_Soffits_RepairSurface();

    @SavVariableMapping("FCPAXWRN")
    public Integer getAccesswayWindows_Replace();

    @SavVariableMapping("FCPWLSRN")
    public Integer getWalls_RenewSurface();

    @SavVariableMapping("FCPPRES")
    public Enum10 getDoCommonPartsExist_();

    @SavVariableMapping("FCPDFXVE")
    public Enum10 getDefects_Ventilation();

    @SavVariableMapping("FCPENCLO")
    public Enum10 getEnclosed();

    @SavVariableMapping("FCPWLSFL")
    public Enum10 getWalls_Faults();

    @SavVariableMapping("FCPEXIST")
    public Enum10 getDoesAccess_AreaExist_();

    @SavVariableMapping("FCPBALFL")
    public Enum10 getBalustrades_Faults();

    @SavVariableMapping("FCPFLRFL")
    public Enum10 getFloors_Treads_Faults();

    @SavVariableMapping("FCPTYPES")
    public Enum1224 getBalcony_Deck_Corridor_Lobby();

    @SavVariableMapping("TYPE")
    public Enum1225 getTypeOfAccessway();

    @SavVariableMapping("FCPCLNFL")
    public Enum10 getCeilings_Soffits_Faults();

    @SavVariableMapping("FCPAXDFL")
    public Enum10 getAccessDoors_Screens_Faults();

    @SavVariableMapping("FCPDFXAL")
    public Enum10 getDefects_ArtificialLighting();

    @SavVariableMapping("FCPINMOD")
    public Enum10 getInModule();

    @SavVariableMapping("FCPSIZES")
    public Enum1230 getSpacious_Average_Tight();

    @SavVariableMapping("FCPAXLFL")
    public Enum10 getAccesswayLighting_Faults();

    @SavVariableMapping("FCPAXWFL")
    public Enum10 getAccesswayWindows_Faults();

}
