package uk.org.cse.nhm.language.definition.action;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Size;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Category;
import uk.org.cse.nhm.language.definition.Category.CategoryType;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.function.num.XNumber;
import uk.org.cse.nhm.language.definition.reporting.two.XReportDefinition;

import uk.org.cse.commons.Glob;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindNamedArgument;
import com.larkery.jasb.bind.BindRemainingArguments;

@Bind("in-order")
@Doc({"This action applies to a set of houses, and performs some other actions in the best order with respect to a given objective.",
	  "The objective: argument must be provided; before any action is taken, the objective value is computed hypothetically for each house under each of the contained actions.",
	  "The set of houses is then sorted using the best value obtained by applying one of the actions to the house.",
	  "The model proceeds through the set in this order, and tries to apply the action which produced the best value; if the action succeeds, then it moves onto the next house.",
	  "If the action fails, then the house is re-inserted into the remainder of the set according to the value of its next-best action.",
	  
	  "The packages: argument extends the ordering to include multiple measures for each house taken from the supplied set, so the ordering is over measure-house pairs,",
	  "rather than over the best single measure for each house."
})
@Category(CategoryType.ACTIONCOMBINATIONS)
public class XOrderedAction extends XAction implements IXFlaggedAction {
	public static final class P {
		public static final String DELEGATES = "delegates";
		public static final String ascending = "ascending";
		public static final String objective = "objective";
		public static final String packages = "packages";
	}
	private List<XDwellingAction> delegates = new ArrayList<XDwellingAction>();

	private boolean ascending = true;
	
	private XNumber objective;
	
	private boolean packages = false;
	
	@Doc({
		"If this is true, the same dwelling may receive more than one measure; a dwelling will not be removed from consideration once it has had one of the alternatives, so all measure-dwelling combinations are considered, rather than just the best for each dwelling.",
		"The objective will be recalculated for each remaining measure when a house is given a measure; this means that the correctness of the ordering is preserved so long as installing a measure in a house only ever makes the other measures that might be installed worse.",
		"However, it also makes the computation somewhat slower, because there are actions * actions * dwellings / 2 objective values to be computed, rather than just actions * dwellings.",
		"If an action is unsuitable for a house at some point, it will be excluded from consideration, even if it is later made suitable by the application of a different action."
	})
	@BindNamedArgument
	@Prop(P.packages)
	public boolean isPackages() {
		return packages;
	}

	public void setPackages(boolean packages) {
		this.packages = packages;
	}

	@Doc({"If this is true, then smaller values of the objective function will be considered to be better, and houses will be processed in descending order of its value.", 
		"Otherwise, houses will be processed in descending order of the objective."})
	@BindNamedArgument
	@Prop(P.ascending)
	public boolean isAscending() {
		return ascending;
	}

	public void setAscending(boolean ascending) {
		this.ascending = ascending;
	}

	@Doc({
		"This is the objective function used to determine which action/house combinations are best. It will be evaluated once for each house for each measure in a hypothetical condition where the measure has been applied to the house.",
		"The objective is not re-evaluated at any point during the selection of which measures to apply, so if the objective is not independent between houses (or between measures, if packages: is true) then the ordering followed will not",
		"reflect the cumulative effect of the changes that take place."
	})
	@BindNamedArgument
	@Prop(P.objective)
	public XNumber getObjective() {
		return objective;
	}

	public void setObjective(XNumber objective) {
		this.objective = objective;
	}

	@BindRemainingArguments
	@Prop(P.DELEGATES)
	@Doc("The actions which are available to be applied. If there is only one action here, it will simply be performed in order of objective. If there are several actions, the best one will be chosen for each house, in objective order.")
	@Size(min = 1, message = "in-order must have at least one action to consider")
	public List<XDwellingAction> getDelegates() {
		return delegates;
	}

	public void setDelegates(final List<XDwellingAction> delegates) {
		this.delegates = delegates;
	}
	
	
	
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

	@Doc("Only houses which have all of these flags will be considered.")
	@BindNamedArgument("test-flags")
	@Override
	public List<Glob> getTestFlags() {
		return testFlags;
	}

	public void setTestFlags(final List<Glob> testFlags) {
		this.testFlags = testFlags;
	}

	@Doc("All houses which are affected by this action will have the negative flags in this list removed and the positive ones added.")
	@BindNamedArgument("update-flags")
	@Override
	public List<Glob> getUpdateFlags() {
		return updateFlags;
	}

	public void setUpdateFlags(final List<Glob> updateFlags) {
		this.updateFlags = updateFlags;
	}

	@Doc({
		"The names of reports defined with def-report, or def-report itself. All houses in consideration will be sent to this report.",
		"First every house will be sent before being processed, and then houses which are affected will be sent afterwards."
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
