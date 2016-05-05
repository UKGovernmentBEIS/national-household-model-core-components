package uk.org.cse.nhm.language.definition.action;

import java.util.ArrayList;
import java.util.List;

import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.reporting.two.XReportDefinition;

import uk.org.cse.commons.Glob;

import com.larkery.jasb.bind.BindNamedArgument;

@Unsuitability(value = {
		"lacks a flag specified by test-flags or has a flag forbidden by same",
		"supply chain named by the supply-chain attribute is empty" })
public abstract class XFlaggedDwellingAction extends XDwellingAction implements IXFlaggedAction {
	/**
	 * The flags which will be tested for suitability
	 */
	private List<Glob> testFlags = new ArrayList<>();
	/**
	 * The flags which will be applied after successful application
	 */
	private List<Glob> updateFlags = new ArrayList<>();
	
	/**
	 * Reports into which reporting will be done
	 */
	private List<XReportDefinition> report = new ArrayList<>();

	@Doc({
			"Flags to test for presence or absence before executing this action",
			"These flags will affect the suitability of the action; it will only be suitable",
			"for houses that have all the required flags and none of the forbidden flags." })
	@BindNamedArgument("test-flags")
	@Override
	public List<Glob> getTestFlags() {
		return testFlags;
	}

	public void setTestFlags(final List<Glob> testFlags) {
		this.testFlags = testFlags;
	}

	@Doc({
			"Flags to set and clear after successful execution of this action.",
			"These do not affect the suitability of the action, so asking to remove a flag",
			"which is not present has no effect and does not make the action unsuitable." })
	@BindNamedArgument("update-flags")
	@Override
	public List<Glob> getUpdateFlags() {
		return updateFlags;
	}

	public void setUpdateFlags(final List<Glob> updateFlags) {
		this.updateFlags = updateFlags;
	}

	@Doc({
		"The names of reports defined with def-report, or def-report itself. When this action is applied to a house,",
		"the house will be sent to def-report before the action is performed (whether or not the action is suitable), ",
		"and again after the action is performed if the action turned out to be suitable. The sent-from field in the",
		"report will be supplied from the name: of this action."
	})
	@BindNamedArgument("report")
	@Override
	public List<XReportDefinition> getReport() {
		return report;
	}

	public void setReport(List<XReportDefinition> reports) {
		this.report = reports;
	}
}
