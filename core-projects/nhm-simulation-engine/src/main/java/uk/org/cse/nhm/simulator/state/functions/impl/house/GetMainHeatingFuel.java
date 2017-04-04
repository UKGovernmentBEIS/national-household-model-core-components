package uk.org.cse.nhm.simulator.state.functions.impl.house;

import javax.inject.Inject;

import uk.org.cse.nhm.hom.emf.technologies.FuelType;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologyModel;
import uk.org.cse.nhm.hom.emf.util.ITechnologyOperations;
import uk.org.cse.nhm.language.definition.enums.XFuelType;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.state.IDimension;

public class GetMainHeatingFuel extends MainHeatingFuelFunction<XFuelType> {
	@Inject
	public GetMainHeatingFuel(final ITechnologyOperations operations, final IDimension<ITechnologyModel> bad) {
		super(operations, bad);
	}

	@Override
	public XFuelType compute(final IComponentsScope scope, final ILets lets) {

		final FuelType mainHeatingFuel = getMainHeatingFuel(scope);

		if (mainHeatingFuel == null) throw new RuntimeException("Main heating fuel should never be null.");

		switch (mainHeatingFuel) {
		case BIOMASS_PELLETS: return XFuelType.BiomassPellets;
		case BIOMASS_WOOD: return XFuelType.BiomassWood;
		case BIOMASS_WOODCHIP: return XFuelType.BiomassWoodchip;
		case BOTTLED_LPG: return XFuelType.BottledLPG;
		case BULK_LPG: return XFuelType.BulkLPG;
		case COMMUNITY_HEAT: return XFuelType.CommunityHeat;
		case ELECTRICITY: return XFuelType.Electricity;
		case EXPORTED_ELECTRICITY: return XFuelType.ExportedElectricity;
		case HOUSE_COAL: return XFuelType.HouseCoal;
		case MAINS_GAS: return XFuelType.MainsGas;
		case OFF_PEAK_ELECTRICITY: return XFuelType.OffPeakElectricity;
		case OIL: return XFuelType.Oil;
		case PEAK_ELECTRICITY: return XFuelType.PeakElectricity;
		case PHOTONS: return XFuelType.Photons;
		default:
			throw new UnsupportedOperationException("Unknown fuel type " + mainHeatingFuel);
		}
	}
}
