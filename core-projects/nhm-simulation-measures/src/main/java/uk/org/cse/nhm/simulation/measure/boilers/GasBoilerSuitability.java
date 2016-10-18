package uk.org.cse.nhm.simulation.measure.boilers;

import uk.org.cse.nhm.hom.emf.technologies.FuelType;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologyModel;
import uk.org.cse.nhm.hom.emf.util.ITechnologyOperations;
import uk.org.cse.nhm.hom.structure.StructureModel;

public class GasBoilerSuitability {
	public static boolean isSuitableIfGas(FuelType fuelType,
			final ITechnologyOperations operations,
			final StructureModel structure, final ITechnologyModel technologies) {

		if (fuelType.isGas()) {
			if (fuelType == FuelType.MAINS_GAS && !structure.isOnGasGrid()) {
				return false;
			}
			if (operations.hasCommunitySpaceHeating(technologies)
					|| operations.hasCommunityWaterHeating(technologies)) {
				return false;
			}
		}

		return true;
	}
}
