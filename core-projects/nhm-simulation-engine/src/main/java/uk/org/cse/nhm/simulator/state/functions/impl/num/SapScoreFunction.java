package uk.org.cse.nhm.simulator.state.functions.impl.num;

import java.util.EnumSet;
import java.util.Set;

import org.joda.time.DateTime;

import com.google.common.base.Optional;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

import uk.org.cse.nhm.energycalculator.api.types.ServiceType;
import uk.org.cse.nhm.hom.emf.technologies.FuelType;
import uk.org.cse.nhm.hom.structure.StructureModel;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.dimensions.energy.IEnergyMeter;
import uk.org.cse.nhm.simulator.state.dimensions.energy.IPowerTable;
import uk.org.cse.nhm.simulator.state.dimensions.fuel.cost.ITariffs;

public class SapScoreFunction extends FuelCostFunction {
	/**
	 * Taken from SAP2009 9.90 (March 2010) p32 equation (10)
	 */
	private static final double FLOOR_AREA_OFFSET = 45;
	
	/**
	 * Taken from SAP2009 9.90 (March 2010) p32 equation (11) (12)
	 */
	private static final double ECF_THRESHOLD = 3.5;

	/**
	 * Taken from SAP2009 9.90 (March 2010) p32 equation (11)
	 */
	private static final double HIGH_ECF_MULTIPLIER = 121d;

	/**
	 * Taken from SAP2009 9.90 (March 2010) p32 equation (11)
	 */
	private static final double HIGH_ECF_FIRST_TERM = 117d;

	/**
	 * Taken from SAP2009 9.90 (March 2010) p32 equation (12)
	 */
	private static final double LOW_ECF_FIRST_TERM = 100d;

	/**
	 * Taken from SAP2009 9.90 (March 2010) p32 equation (12)
	 */
	private static final double LOW_ECF_MULTIPLIER = 13.95d;
	
	
	private final double deflator;
	
	final IDimension<StructureModel> structure;
	
	@AssistedInject
	public SapScoreFunction(
			final Set<IDimension<?>> allDimensions,
			final IDimension<IEnergyMeter> meterDimension,
			final IDimension<IPowerTable> powerDimension,
			final IDimension<StructureModel> structureDimension,
			final IDimension<ITariffs> tariffs,
			@Assisted final double deflator) {
		super(allDimensions, 
				meterDimension, 
				powerDimension, 
				tariffs, 
				Optional.<FuelType>absent(), 
				EnumSet.of(ServiceType.APPLIANCES, ServiceType.COOKING)
				);
		structure = structureDimension;
		this.deflator = deflator;
	}
	
	@Override
	public Double compute(final IComponentsScope scope, final ILets lets) {
		final double fuelBill = super.compute(scope, lets);
		final double floorArea = scope.get(structure).getFloorArea();
		
		return clampedScore(fuelBill, floorArea, deflator);
	}

	protected static double clampedScore(final double fuelBill, final double floorArea, final double deflator) {
		return clamp(score(ecf(floorArea, fuelBill, deflator)));
	}

	@Override
	public Set<IDimension<?>> getDependencies() {
		return super.getDependencies();
	}

	@Override
	public Set<DateTime> getChangeDates() {
		return super.getChangeDates();
	}

	protected static double ecf(final double tfa, final double totalCost, final double deflator) {
		return deflator * totalCost / (tfa + FLOOR_AREA_OFFSET);
	}
	
	protected static double score(final double ecf) {
		if (ecf >= ECF_THRESHOLD) {
			return HIGH_ECF_FIRST_TERM - HIGH_ECF_MULTIPLIER * Math.log10(ecf);
		} else {
			return LOW_ECF_FIRST_TERM - LOW_ECF_MULTIPLIER * ecf;
		}
	}
	
	protected static double clamp(final double score) {
		if (score < 1) return 1;
		else return Math.round(score);
	}
}
