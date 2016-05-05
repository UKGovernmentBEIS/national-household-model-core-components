package uk.org.cse.nhm.ehcs10.physical;

import uk.org.cse.nhm.ehcs10.derived.types.Enum10;
import uk.org.cse.nhm.ehcs10.physical.types.Enum1933;
import uk.org.cse.nhm.ehcs10.physical.types.Enum1974;
import uk.org.cse.nhm.spss.wrap.SavVariableMapping;
import uk.org.cse.stockimport.spss.SurveyEntry;

public interface StructureEntry extends SurveyEntry {
	@SavVariableMapping("FSTSPRSP")
	public String getRoofSpreading_Other_Specify();

	@SavVariableMapping("FSTSULSP")
	public String getSulphateAttack_Other_Specify();

	@SavVariableMapping("FSTBULSP")
	public String getWallBulging_Other_Specify();

	@SavVariableMapping("FSTMOVSP")
	public String getDifferentialMovement_Other_Specify();

	@SavVariableMapping("FSTBALSP")
	public String getAdequacyOfBalconies_Other_Specify();

	@SavVariableMapping("FSTFOUSP")
	public String getFoundationSettlement_Other_Specify();

	@SavVariableMapping("FSTIWPSP")
	public String getIntegrityOfWallPanels_Other_Specify();

	@SavVariableMapping("FSTOTHST")
	public String getAnyOtherProblems_SpecifyTreatment();

	@SavVariableMapping("FSTOTHSE")
	public String getAnyOtherProblems_SpecifyExtent();

	@SavVariableMapping("FSTMOVLM")
	public Integer getDifferentialMovement_Joint_LinearMetres();

	@SavVariableMapping("FSTSULLM")
	public Integer getSulphateAttack_Chimney_Liner_LinearMetres();

	@SavVariableMapping("FSTBULTN")
	public Integer getWallBulging_TieRods_Number();

	@SavVariableMapping("FSTISFWA")
	public Integer getIntegrityOfFrame_WallArea();

	@SavVariableMapping("FSTSPRNO")
	public Integer getRoofSpreading_Tie_Ing_Number();

	@SavVariableMapping("FSTLINNO")
	public Integer getLintelFailure_ReplaceLintels_Number();

	@SavVariableMapping("FSTFOUUN")
	public Integer getFoundationSettlement_Underpin();

	@SavVariableMapping("FSTBULSN")
	public Integer getWallBulging_Strapping_Number();

	@SavVariableMapping("FSTTIEWA")
	public Integer getWallTieFailure_InsertWallTies_WallArea();

	@SavVariableMapping("FSTBALNO")
	public Integer getAdequacyOfBalconies_TotalNumber();

	@SavVariableMapping("FSTIWPNO")
	public Integer getIntegrityOfWallPanels_TotalNumber();

	@SavVariableMapping("FSTFOULM")
	public Integer getFoundationSettlement_Underpin_LinearMetres();

	@SavVariableMapping("FSTTIEMN")
	public Enum10 getWallTieFeature_Monitor_ExamineFurther();

	@SavVariableMapping("FSTBWCEL")
	public Enum10 getBoundaryWallHorizontalCracking_ActionDescribedElsewhere();

	@SavVariableMapping("FSTISFEL")
	public Enum10 getIntegrityOfFrame_ActionDescribedElsewhere();

	@SavVariableMapping("FSTTIEIN")
	public Enum10 getWallTieFailure_InsertWallTies_Treatment();

	@SavVariableMapping("FSTSULCL")
	public Enum10 getSulphateAttack_ChimneyLiner_Treatment();

	@SavVariableMapping("FSTLINEL")
	public Enum10 getLintelFailure_ActionDescribedElsewhere();

	@SavVariableMapping("FSTBWPDE")
	public Enum10 getBoundaryWallOutOfPlumb_Defect();

	@SavVariableMapping("FSTUNSAC")
	public Enum10 getUnstableFloorsEtc_ActionRequired();

	@SavVariableMapping("FSTBWHAC")
	public Enum10 getBoundaryWallUnsafeHeight_ActionRequired();

	@SavVariableMapping("FSTBOREL")
	public Enum10 getWoodBorerInfestation_ActionDescribedElsewhere();

	@SavVariableMapping("FSTOTHDE")
	public Enum10 getAnyOtherProblems_Defect();

	@SavVariableMapping("FSTBWCAC")
	public Enum10 getBoundaryWallHorizontalCracking_ActionRequired();

	@SavVariableMapping("FSTIWPDE")
	public Enum10 getIntegrityOfWallPanels_Defect();

	@SavVariableMapping("FSTISFMN")
	public Enum10 getIntegrityOfFrame_Monitor_ExamineFurther();

	@SavVariableMapping("FSTSPREL")
	public Enum10 getRoofSpreading_ActionDescribedElsewhere();

	@SavVariableMapping("FSTHUMEL")
	public Enum10 getRoofHumping_ActionDescribedElsewhere();

	@SavVariableMapping("FSTRETMN")
	public Enum10 getUnstableRetainingWall_Monitor_ExamineFurther();

	@SavVariableMapping("FSTOTHAC")
	public Enum10 getAnyOtherProblems_ActionRequired();

	@SavVariableMapping("FSTPRES")
	public Enum10 getSECTION21STRUCTURALDEFECTS_DefectsPresent_();

	@SavVariableMapping("FSTROTTR")
	public Enum10 getDryRot_WetRot_Wall_TimberTreatment();

	@SavVariableMapping("FSTHUMDE")
	public Enum10 getRoofHumping_Defect();

	@SavVariableMapping("FSTSAGEL")
	public Enum10 getRoofSagging_ActionDescribedElsewhere();

	@SavVariableMapping("FSTBALOT")
	public Enum10 getAdequacyOfBalconies_Other();

	@SavVariableMapping("FSTOTHMN")
	public Enum10 getAnyOtherProblems_Monitor_ExamineFurther();

	@SavVariableMapping("FSTBULST")
	public Enum10 getWallBulging_Strapping_Treatment();

	@SavVariableMapping("FSTROTAC")
	public Enum10 getDryRot_WetRot_ActionRequired();

	@SavVariableMapping("FSTMOVEL")
	public Enum10 getDifferentialMovement_ActionDescribedElsewhere();

	@SavVariableMapping("FSTSULAC")
	public Enum10 getSulphateAttack_ActionRequired();

	@SavVariableMapping("FSTBORAC")
	public Enum10 getWoodBorerInfestation_ActionRequired();

	@SavVariableMapping("FSTMOVMJ")
	public Enum10 getDifferentialMovement_Joint_Treatment();

	@SavVariableMapping("FSTBWPMN")
	public Enum10 getBoundaryWallOutOfPlumb_Monitor_ExamineFurther();

	@SavVariableMapping("FSTBWCMN")
	public Enum10 getBoundaryWallHorizontalCracking_Monitor_ExamineFurther();

	@SavVariableMapping("FSTIWPOT")
	public Enum10 getIntegrityOfWallPanels_Other();

	@SavVariableMapping("FSTLINMN")
	public Enum10 getLintelFailure_Monitor_ExamineFurther();

	@SavVariableMapping("FSTBWHMN")
	public Enum10 getBoundaryWallUnsafeHeight_Monitor_ExamineFurther();

	@SavVariableMapping("FSTSPRTI")
	public Enum10 getRoofSpreading_Tie_Ing_Treatment();

	@SavVariableMapping("FSTMOVMN")
	public Enum10 getDifferentialMovement_Monitor_ExamineFurther();

	@SavVariableMapping("FSTPARMN")
	public Enum10 getUnstableParapets_Monitor_ExamineFurther();

	@SavVariableMapping("FSTPARAC")
	public Enum10 getUnstableParapets_ActionRequired();

	@SavVariableMapping("FSTROTDE")
	public Enum10 getDryRot_WetRot_Defect();

	@SavVariableMapping("FSTIWPMN")
	public Enum10 getIntegrityOfWallPanels_Monitor_ExamineFurther();

	@SavVariableMapping("FSTBWPEL")
	public Enum10 getBoundaryWallOutOfPlumb_ActionDescribedElsewhere();

	@SavVariableMapping("FSTBULDE")
	public Enum10 getWallBulging_Defect();

	@SavVariableMapping("FSTROTMN")
	public Enum10 getDryRot_WetRot_Monitor_ExamineFurther();

	@SavVariableMapping("FSTROTEL")
	public Enum10 getDryRot_WetRot_ActionDescribedElsewhere();

	@SavVariableMapping("FSTBWHEL")
	public Enum10 getBoundaryWallUnsafeHeight_ActionDescribedElsewhere();

	@SavVariableMapping("FSTBOREX")
	public Enum1933 getWoodBorerInfestation_Extent();

	@SavVariableMapping("FSTISFAC")
	public Enum10 getIntegrityOfFrame_ActionRequired();

	@SavVariableMapping("FSTSAGDE")
	public Enum10 getRoofSagging_Defect();

	@SavVariableMapping("FSTIWPRN")
	public Enum10 getIntegrityOfWallPanels_ReplaceFixings();

	@SavVariableMapping("FSTISFMG")
	public Enum10 getIntegrityOfFrame_MakingGood();

	@SavVariableMapping("FSTTIEDE")
	public Enum10 getWallTieFeature_Defect();

	@SavVariableMapping("FSTUNSMN")
	public Enum10 getUnstableFloorsEtc_Monitor_ExamineFurther();

	@SavVariableMapping("FSTUNSEL")
	public Enum10 getUnstableFloorsEtc_ActionDescribedElsewhere();

	@SavVariableMapping("FSTTIEEL")
	public Enum10 getWallTieFeature_ActionDescribedElsewhere();

	@SavVariableMapping("FSTSPRDE")
	public Enum10 getRoofSpreading_Defect();

	@SavVariableMapping("FSTBWPAC")
	public Enum10 getBoundaryWallOutOfPlumb_ActionRequired();

	@SavVariableMapping("FSTUNSDE")
	public Enum10 getUnstableFloorsEtc_Defect();

	@SavVariableMapping("FSTBULOT")
	public Enum10 getWallBulging_Other_Treatment();

	@SavVariableMapping("FSTFOUEL")
	public Enum10 getFoundationSettlement_ActionDescribedElsewhere();

	@SavVariableMapping("FSTHUMAC")
	public Enum10 getRoofHumping_ActionRequired();

	@SavVariableMapping("FSTBORTR")
	public Enum10 getWoodBorerInfestation_TimberTreatment();

	@SavVariableMapping("FSTFOUAC")
	public Enum10 getFoundationSettlement_ActionRequired();

	@SavVariableMapping("FSTRETEL")
	public Enum10 getUnstableRetainingWall_ActionDescribedElsewhere();

	@SavVariableMapping("FSTSULMN")
	public Enum10 getSulphateAttack_Monitor_ExamineFurther();

	@SavVariableMapping("FSTPAREL")
	public Enum10 getUnstableParapets_ActionDescribedElsewhere();

	@SavVariableMapping("FSTBULMN")
	public Enum10 getWallBulging_Monitor_ExamineFurther();

	@SavVariableMapping("FSTFOUDE")
	public Enum10 getFoundationSettlement_Defect();

	@SavVariableMapping("FSTSPROT")
	public Enum10 getRoofSpreading_Other_Treatnent();

	@SavVariableMapping("FSTMOVAC")
	public Enum10 getDifferentialMovement_ActionRequired();

	@SavVariableMapping("FSTBALAC")
	public Enum10 getAdequacyOfBalconies_ActionRequired();

	@SavVariableMapping("FSTISFRN")
	public Enum10 getIntegrityOfFrame_Replace();

	@SavVariableMapping("FSTBORMN")
	public Enum10 getWoodBorerInfestation_Monitor_ExamineFurther();

	@SavVariableMapping("FSTSAGMN")
	public Enum10 getRoofSagging_Monitor_ExamineFurther();

	@SavVariableMapping("FSTBALEL")
	public Enum10 getAdequacyOfBalconies_ActionDescribedElsewhere();

	@SavVariableMapping("FSTRETDE")
	public Enum10 getUnstableRetainingWall_Defect();

	@SavVariableMapping("FSTBULTR")
	public Enum10 getWallBulging_TieRods_Treatment();

	@SavVariableMapping("FSTMOVDE")
	public Enum10 getDifferentialMovement_Defect();

	@SavVariableMapping("FSTBWHDE")
	public Enum10 getBoundaryWallUnsafeHeight_Defect();

	@SavVariableMapping("FSTSULDE")
	public Enum10 getSulphateAttack_Present();

	@SavVariableMapping("FSTBORDE")
	public Enum10 getWoodBorerInfestation_Defect();

	@SavVariableMapping("FSTLINDE")
	public Enum10 getLintelFailure_Defect();

	@SavVariableMapping("FSTISFDE")
	public Enum10 getIntegrityOfFrame_Defect();

	@SavVariableMapping("FSTFOUMN")
	public Enum10 getFoundationSettlement_Monitor_ExamineFurther();

	@SavVariableMapping("FSTIWPAC")
	public Enum10 getIntegrityOfWallPanels_ActionRequired();

	@SavVariableMapping("FSTMOVOT")
	public Enum10 getDifferentialMovement_Other_Treatment();

	@SavVariableMapping("FSTBWCDE")
	public Enum10 getBoundaryWallHorizontalCracking_Defect();

	@SavVariableMapping("FSTHSSCO")
	public Enum1974 getHHSRS_StructuralCollapse();

	@SavVariableMapping("FSTSPRAC")
	public Enum10 getRoofSpreading_ActionRequired();

	@SavVariableMapping("FSTSULOT")
	public Enum10 getSulphateAttack_Other_Treatment();

	@SavVariableMapping("FSTBALRN")
	public Enum10 getAdequacyOfBalconies_ReplaceFixings();

	@SavVariableMapping("FSTHUMMN")
	public Enum10 getRoofHumping_Monitor_ExamineFurther();

	@SavVariableMapping("FSTBALMN")
	public Enum10 getAdequacyOfBalconies_Monitor_ExamineFurther();

	@SavVariableMapping("FSTLINAC")
	public Enum10 getLintelFailure_ActionRequired();

	@SavVariableMapping("FSTTIEAC")
	public Enum10 getWallTieFailure_ActionRequired();

	@SavVariableMapping("FSTRETAC")
	public Enum10 getUnstableRetainingWall_ActionRequired();

	@SavVariableMapping("FSTROTEX")
	public Enum1933 getDryRot_WetRot_Extent();

	@SavVariableMapping("FSTBULAC")
	public Enum10 getWallBulging_ActionRequired();

	@SavVariableMapping("FSTBALDE")
	public Enum10 getAdequacyOfBalconies_Defect();

	@SavVariableMapping("FSTFOUOT")
	public Enum10 getFoundationSettlement_Other();

	@SavVariableMapping("FSTSAGAC")
	public Enum10 getRoofSagging_ActionRequired();

	@SavVariableMapping("FSTIWPEL")
	public Enum10 getIntegrityOfWallPanels_ActionDescribedElsewhere();

	@SavVariableMapping("FSTLINRN")
	public Enum10 getLintelFailure_ReplaceLintels_Treatment();

	@SavVariableMapping("FSTSULEL")
	public Enum10 getSulphateAttack_ActionDescribedElsewhere();

	@SavVariableMapping("FSTBULEL")
	public Enum10 getWallBulging_ActionDescribedElsewhere();

	@SavVariableMapping("FSTPARDE")
	public Enum10 getUnstableParapets_Defect();

	@SavVariableMapping("FSTOTHEL")
	public Enum10 getAnyOtherProblems_ActionDescribedElsewhere();

	@SavVariableMapping("FSTSPRMN")
	public Enum10 getRoofSpreading_Monitor_ExamineFurther();

}

