package uk.org.cse.nhm.ehcs10.physical.impl;

import uk.org.cse.nhm.ehcs10.derived.types.Enum10;
import uk.org.cse.nhm.ehcs10.physical.DamppcEntry;
import uk.org.cse.nhm.ehcs10.physical.types.Enum1282;
import uk.org.cse.nhm.ehcs10.physical.types.Enum1284;
import uk.org.cse.stockimport.spss.SurveyEntryImpl;

public class DamppcEntryImpl extends SurveyEntryImpl implements DamppcEntry {
	private Integer BACK_Leave;
	private Integer BACK_TenthsOfLength;
	private Integer FRONT_ReplaceInstall;
	private Integer BACK_ReplaceInstall;
	private Integer FRONT_TenthsOfLength;
	private Integer FRONT_Leave;
	private Integer BACK_ReplacementPeriod;
	private Integer FRONT_ReplacementPeriod;
	private Enum1282 BACK_Faults;
	private Enum10 BACK_Urgent;
	private Enum1284 TypeOfDampProofCourse;
	private Enum10 FRONT_Urgent;
	private Enum1282 FRONT_Faults;
	public Integer getBACK_Leave() {
		return BACK_Leave;	}

	public void setBACK_Leave(final Integer BACK_Leave) {
		this.BACK_Leave = BACK_Leave;	}

	public Integer getBACK_TenthsOfLength() {
		return BACK_TenthsOfLength;	}

	public void setBACK_TenthsOfLength(final Integer BACK_TenthsOfLength) {
		this.BACK_TenthsOfLength = BACK_TenthsOfLength;	}

	public Integer getFRONT_ReplaceInstall() {
		return FRONT_ReplaceInstall;	}

	public void setFRONT_ReplaceInstall(final Integer FRONT_ReplaceInstall) {
		this.FRONT_ReplaceInstall = FRONT_ReplaceInstall;	}

	public Integer getBACK_ReplaceInstall() {
		return BACK_ReplaceInstall;	}

	public void setBACK_ReplaceInstall(final Integer BACK_ReplaceInstall) {
		this.BACK_ReplaceInstall = BACK_ReplaceInstall;	}

	public Integer getFRONT_TenthsOfLength() {
		return FRONT_TenthsOfLength;	}

	public void setFRONT_TenthsOfLength(final Integer FRONT_TenthsOfLength) {
		this.FRONT_TenthsOfLength = FRONT_TenthsOfLength;	}

	public Integer getFRONT_Leave() {
		return FRONT_Leave;	}

	public void setFRONT_Leave(final Integer FRONT_Leave) {
		this.FRONT_Leave = FRONT_Leave;	}

	public Integer getBACK_ReplacementPeriod() {
		return BACK_ReplacementPeriod;	}

	public void setBACK_ReplacementPeriod(final Integer BACK_ReplacementPeriod) {
		this.BACK_ReplacementPeriod = BACK_ReplacementPeriod;	}

	public Integer getFRONT_ReplacementPeriod() {
		return FRONT_ReplacementPeriod;	}

	public void setFRONT_ReplacementPeriod(final Integer FRONT_ReplacementPeriod) {
		this.FRONT_ReplacementPeriod = FRONT_ReplacementPeriod;	}

	public Enum1282 getBACK_Faults() {
		return BACK_Faults;	}

	public void setBACK_Faults(final Enum1282 BACK_Faults) {
		this.BACK_Faults = BACK_Faults;	}

	public Enum10 getBACK_Urgent() {
		return BACK_Urgent;	}

	public void setBACK_Urgent(final Enum10 BACK_Urgent) {
		this.BACK_Urgent = BACK_Urgent;	}

	public Enum1284 getTypeOfDampProofCourse() {
		return TypeOfDampProofCourse;	}

	public void setTypeOfDampProofCourse(final Enum1284 TypeOfDampProofCourse) {
		this.TypeOfDampProofCourse = TypeOfDampProofCourse;	}

	public Enum10 getFRONT_Urgent() {
		return FRONT_Urgent;	}

	public void setFRONT_Urgent(final Enum10 FRONT_Urgent) {
		this.FRONT_Urgent = FRONT_Urgent;	}

	public Enum1282 getFRONT_Faults() {
		return FRONT_Faults;	}

	public void setFRONT_Faults(final Enum1282 FRONT_Faults) {
		this.FRONT_Faults = FRONT_Faults;	}

}

