package uk.org.cse.nhm.simulator.state.functions.impl.house;

import com.google.common.base.Optional;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

import uk.org.cse.nhm.hom.emf.technologies.FuelType;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologyModel;
import uk.org.cse.nhm.hom.emf.util.ITechnologyOperations;
import uk.org.cse.nhm.language.definition.enums.XFuelType;
import uk.org.cse.nhm.language.definition.enums.XHeatingSystem;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.state.IDimension;

public class GetMainHeatingFuel extends MainHeatingFuelFunction<XFuelType> {
	final XHeatingSystem system;
	final ITechnologyOperations operations;

	@AssistedInject
	public GetMainHeatingFuel(final ITechnologyOperations operations,
							  final IDimension<ITechnologyModel> tech,
							  @Assisted Optional<XHeatingSystem> system) {
		super(operations, tech);
		this.system = system.orNull();
		this.operations = operations;
	}

	private XFuelType convert(final FuelType fuel) {
		if (fuel == null) return null;
		switch (fuel) {
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
			throw new UnsupportedOperationException("Unknown fuel type " + fuel);
		}
	}

	@Override
	public XFuelType compute(final IComponentsScope scope, final ILets lets) {
		final FuelType type;
		if (system == null) {
			type = getMainHeatingFuel(scope);
		} else {
			final ITechnologyModel tech = getTechnologies(scope);
			switch (system) {
			case PrimarySpaceHeating:
				type = tech.getPrimaryHeatingFuel();
				break;
			case SecondarySpaceHeating:
				type = tech.getSecondaryHeatingFuel();
				break;
			case CentralHotWater:
				type = tech.getPrimaryHotWaterFuel();
				break;
			case AuxiliaryHotWater:
				type = tech.getSecondaryHotWaterFuel();
				break;
			default:
				throw new RuntimeException("Unknown system type "+system);
			}
		}


		return convert(type);
	}
}
