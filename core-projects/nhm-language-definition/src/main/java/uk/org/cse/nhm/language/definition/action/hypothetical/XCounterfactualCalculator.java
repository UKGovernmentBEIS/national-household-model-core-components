package uk.org.cse.nhm.language.definition.action.hypothetical;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindPositionalArgument;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.XScenario;
import uk.org.cse.nhm.language.definition.enums.XEnergyCalculatorType;

@Doc("Temporarily use a different energy calculator for this dwelling.")
@Bind("counterfactual.energy-calculator")
public class XCounterfactualCalculator extends XCounterfactualAction {

	private XEnergyCalculatorType calculatorType = XEnergyCalculatorType.SAP2012;

	@Prop(XScenario.P.CALCULATOR_TYPE)
	@BindPositionalArgument(value = 0)
	public XEnergyCalculatorType getCalculatorType() {
		return calculatorType;
	}

	public void setCalculatorType(final XEnergyCalculatorType type) {
		this.calculatorType = type;
	}
}
