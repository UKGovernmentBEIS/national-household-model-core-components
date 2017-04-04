package uk.org.cse.nhm.language.definition.batch.inputs.random;

import uk.org.cse.nhm.language.definition.batch.inputs.XSingleInput;

abstract class XDistributionInput extends XSingleInput {
	@Override
	public boolean hasBound() {
		return false;
	}
}
