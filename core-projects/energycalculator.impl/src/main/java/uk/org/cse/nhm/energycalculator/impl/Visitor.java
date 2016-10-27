package uk.org.cse.nhm.energycalculator.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.base.Optional;

import uk.org.cse.nhm.energycalculator.api.IConstants;
import uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorParameters;
import uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorVisitor;
import uk.org.cse.nhm.energycalculator.api.IEnergyTransducer;
import uk.org.cse.nhm.energycalculator.api.IHeatingSystem;
import uk.org.cse.nhm.energycalculator.api.IVentilationSystem;
import uk.org.cse.nhm.energycalculator.api.ThermalMassLevel;
import uk.org.cse.nhm.energycalculator.api.types.AreaType;
import uk.org.cse.nhm.energycalculator.api.types.EnergyType;
import uk.org.cse.nhm.energycalculator.api.types.OvershadingType;
import uk.org.cse.nhm.energycalculator.impl.demands.LightingDemand09;
import uk.org.cse.nhm.energycalculator.impl.gains.SolarGainsSource;

/**
 * Helper class for the calculator, which implements the visitor interface and connects up default transducers.
 * <p>
 * It has various public fields, which is normally evil, but it should only be constructed by {@link EnergyCalculatorCalculator}, 
 * and only be used by same in a tightly controlled way. If you find yourself using it anywhere else, it is time to refactor it.
 * <p>
 * The main jobs it does are:
 * 
 * <ol>
 * 	<li>Collecting all the {@link IEnergyTransducer}s into {@link #transducers}, and adding a {@link SolarGainsSource}, {@link GainLoadRatioAdjuster}, and {@link LightingDemand09}</li>
 * 	<li>Collecting all the {@link IHeatingSystem}s into {@link #heatingSystems}</li>
 *  <li>Collecting all the {@link IVentilationSystem}s into {@link #ventilationSystems}</li>
 *  <li>Accumulating the specific heat loss, external area and thermal mass into {@link #totalSpecificHeatLoss}, {@link #totalExternalArea}, and {@link #totalThermalMass}</li>
 *  <li>Passing ventilation information to {@link #infiltration}, the {@link IStructuralInfiltrationAccumulator}</li>
 *  <li>Passing lighting information from the visitor to {@link #lightingDemand} and {@link #solarGains}</li>
 * </ol>
 * 
 * @author hinton
 *
 */
class Visitor implements IEnergyCalculatorVisitor {
	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(Visitor.class);

	public final IStructuralInfiltrationAccumulator infiltration;
	
	public final List<IEnergyTransducer> transducers;
	public final List<IHeatingSystem> heatingSystems = new ArrayList<IHeatingSystem>();
	public final Map<IHeatingSystem, Double> proportions = new HashMap<>();
	public final List<IVentilationSystem> ventilationSystems = new ArrayList<IVentilationSystem>();

	public final double[][] areasByType = new double[2][AreaType.values().length];

	public double totalSpecificHeatLoss, totalExternalArea;
	
	public double[] thermalMassAreas = new double[ThermalMassLevel.values().length];

	public final GainLoadRatioAdjuster glrAdjuster;

	private final LightingDemand09 lightingDemand;

	private final SolarGainsSource solarGains;

	public Visitor(final IConstants constants, final IEnergyCalculatorParameters parameters, final List<IEnergyTransducer> defaultTransducers) {
		this.solarGains = new SolarGainsSource(constants, EnergyType.GainsSOLAR_GAINS);
		this.glrAdjuster = new GainLoadRatioAdjuster();
		this.lightingDemand = new LightingDemand09(constants);
		this.transducers = new ArrayList<IEnergyTransducer>(defaultTransducers.size() + 15);
		this.transducers.addAll(defaultTransducers);
		this.infiltration = new StructuralInfiltrationAccumulator(constants);
		transducers.add(solarGains);
		transducers.add(glrAdjuster);
		transducers.add(lightingDemand);
	}

	@Override
	public void visitHeatingSystem(final IHeatingSystem system, double proportion) {
		heatingSystems.add(system);
		proportions.put(system, proportion);
	}
	
	@Override
	public double heatSystemProportion(IHeatingSystem system) {
		if (proportions.containsKey(system)) {
			return proportions.get(system);
			
		} else {
			throw new RuntimeException("No proportion found for system " + system.toString());
		}
	}

	@Override
	public void visitEnergyTransducer(final IEnergyTransducer transducer) {
		transducers.add(transducer);
	}

	@Override
	public void visitVentilationSystem(final IVentilationSystem ventilation) {
		ventilationSystems.add(ventilation);
	}

	@Override
	public void visitFabricElement(final AreaType name, final double area, final double u, final Optional<ThermalMassLevel> thermalMassLevel) {
		log.debug("VISIT, {}, {}, {}, {}", name, area, u, thermalMassLevel);
		totalSpecificHeatLoss += u * area;
		
		if (thermalMassLevel.isPresent()) {
			thermalMassAreas[thermalMassLevel.get().ordinal()] += area;
		}
		
		totalExternalArea += name.isExternal() ? area : 0;
		areasByType[0][name.ordinal()] += area;
		areasByType[1][name.ordinal()] += area * u;
	}

	@Override
	public void addFanInfiltration(final int fans) {
		infiltration.addFanInfiltration(fans);
	}

	@Override
	public void addFloorInfiltration(final double floorArea, final double airChangeRate) {
		infiltration.addFloorInfiltration(floorArea, airChangeRate);
	}
	

	@Override
	public void addVentInfiltration(int vents) {
		infiltration.addVentInfiltration(vents);
	}

	@Override
	public void addFlueInfiltration() {
		infiltration.addFlueInfiltration();
	}

	@Override
	public void addChimneyInfiltration() {
		infiltration.addChimneyInfiltration();
	}

	@Override
	public void addWallInfiltration(final double wallArea, final double airChangeRate) {
		infiltration.addWallInfiltration(wallArea, airChangeRate);
	}

	@Override
	public void visitTransparentElement(final double visibleLightTransmittivity, final double solarGainTransmissivity, final double horizontalOrientation,
			final double verticalOrientation, final OvershadingType overshading) {
		solarGains.addTransparentElement(visibleLightTransmittivity, solarGainTransmissivity, horizontalOrientation, verticalOrientation, overshading);
		lightingDemand.addTransparentElement(visibleLightTransmittivity, solarGainTransmissivity, horizontalOrientation, verticalOrientation, overshading);
	}
	
	@Override
	public double getTotalThermalMass() {
		/*
		BEISDOC
		NAME: Thermal Mass
		DESCRIPTION: Choose the thermal mass parameter based on which level has the largest wall area.
		TYPE: lookup
		UNIT: kJ/m^2.℃
		SAP: Table 1f
		BREDEM: 4A
		DEPS: thermal-mass-level,wall-thermal-mass-category
		ID: thermal-mass
		CODSIEB
		*/
		double highestArea = 0;
		ThermalMassLevel level = ThermalMassLevel.MEDIUM;
		for (int i = 0; i < thermalMassAreas.length; i++) {
			if (thermalMassAreas[i] > highestArea) {
				level = ThermalMassLevel.values()[i];
			}
		}
		
		return level.getThermalMassParameter();
	}
	
	@Override
	public String toString() {
		return "Visitor [totalSpecificHeatLoss=" + totalSpecificHeatLoss + ", totalExternalArea=" + totalExternalArea + ", totalThermalMass="
				+ getTotalThermalMass() + "]";
	}
}
