/**
 */
package uk.org.cse.nhm.hom.emf.technologies.impl;

import java.util.concurrent.atomic.AtomicInteger;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;


import uk.org.cse.nhm.energycalculator.api.*;
import uk.org.cse.nhm.energycalculator.api.types.*;
import uk.org.cse.nhm.energycalculator.api.types.steps.EnergyCalculationStep;

import uk.org.cse.nhm.energycalculator.mode.EnergyCalculatorType.ECGeneration;

import uk.org.cse.nhm.hom.IHeatProportions;
import uk.org.cse.nhm.hom.constants.SolarConstants;
import uk.org.cse.nhm.hom.constants.SplitRateConstants;
import uk.org.cse.nhm.hom.emf.technologies.ISolarPhotovoltaic;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologiesPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>Solar Photovoltaic</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.impl.SolarPhotovoltaicImpl#getPeakPower <em>Peak Power</em>}</li>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.impl.SolarPhotovoltaicImpl#getOwnUseProportion <em>Own Use Proportion</em>}</li>
 * </ul>
 *
 * @generated
 */
public class SolarPhotovoltaicImpl extends MinimalEObjectImpl implements ISolarPhotovoltaic {
	/*
	 BEISDOC
	 NAME: Solar Generation Factor
	 DESCRIPTION: The number '0.8' which is used to adjust the cheese.
	 TYPE: value UNIT: m^2 / W
         SAP: (M1)
         SAP_COMPLIANT: Yes
	 BREDEM: 10A
         BREDEM_COMPLIANT: Yes
	 CONVERSION: From m^2/kW in SAP to m^2/W in the NHM, divide by 1000.
	 ID: solar-generation-factor
	 CODSIEB
	 */
	private final double solarGenerationFactor = 0.8 / 1000;

	private final double sapOwnUseProportion = 0.5;

	private final OvershadingType overshading = OvershadingType.AVERAGE;

	/**
	 * A set of bit flags representing the values of boolean attributes and whether unsettable features have been set.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected int flags = 0;

	/**
	 * The default value of the '{@link #getPeakPower() <em>Peak Power</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getPeakPower()
	 * @generated
	 * @ordered
	 */
	protected static final double PEAK_POWER_EDEFAULT = 0.0;

	/**
	 * The cached value of the '{@link #getPeakPower() <em>Peak Power</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getPeakPower()
	 * @generated
	 * @ordered
	 */
	protected double peakPower = PEAK_POWER_EDEFAULT;

	/**
	 * The default value of the '{@link #getOwnUseProportion() <em>Own Use Proportion</em>}' attribute.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @see #getOwnUseProportion()
	 * @generated
	 * @ordered
	 */
	protected static final double OWN_USE_PROPORTION_EDEFAULT = 0.0;

	/*
	 BEISDOC
	 NAME: PV Own Use Proportion
	 DESCRIPTION: The proportion of generated solar PV electricity which is used in the home instead of exported.
	 TYPE: value
	 UNIT: Dimensionless
	 SAP: Appendix M (M1 section 3)
         SAP_COMPLIANT: SAP mode only
         BREDEM_COMPLIANT: N/A - out of scope
	 SET: measure.solar-photovoltaic
	 NOTES: In SAP 2012 mode, this will always be 0.5, regardless of which value was put in by measure.solar-photovoltaic.
	 ID: pv-own-use-proportion
	 CODSIEB
	 */
	/**
	 * The cached value of the '{@link #getOwnUseProportion() <em>Own Use Proportion</em>}' attribute.
	 * <!-- begin-user-doc --><!-- end-user-doc -->
	 * @see #getOwnUseProportion()
	 * @generated
	 * @ordered
	 */
	protected double ownUseProportion = OWN_USE_PROPORTION_EDEFAULT;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	protected SolarPhotovoltaicImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ITechnologiesPackage.Literals.SOLAR_PHOTOVOLTAIC;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public double getPeakPower() {
		return peakPower;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setPeakPower(double newPeakPower) {
		double oldPeakPower = peakPower;
		peakPower = newPeakPower;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ITechnologiesPackage.SOLAR_PHOTOVOLTAIC__PEAK_POWER, oldPeakPower, peakPower));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public double getOwnUseProportion() {
		return ownUseProportion;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setOwnUseProportion(double newOwnUseProportion) {
		double oldOwnUseProportion = ownUseProportion;
		ownUseProportion = newOwnUseProportion;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ITechnologiesPackage.SOLAR_PHOTOVOLTAIC__OWN_USE_PROPORTION, oldOwnUseProportion, ownUseProportion));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated no
	 */
	@Override
	public void accept(final IConstants constants, final IEnergyCalculatorParameters parameters,
			final IEnergyCalculatorVisitor visitor, final AtomicInteger heatingSystemCounter,
			final IHeatProportions heatProportions) {
		visitor.visitEnergyTransducer(new IEnergyTransducer() {

			@Override
			public ServiceType getServiceType() {
				return ServiceType.GENERATION;
			}

			@Override
			public int getPriority() {
				return 0;
			}

			@Override
			public TransducerPhaseType getPhase() {
				return TransducerPhaseType.AfterEverything;
			}

			@Override
			public void generate(final IEnergyCalculatorHouseCase house, final IInternalParameters parameters,
					final ISpecificHeatLosses losses, final IEnergyState state) {

				final double incidentRaditionPerUnitArea = parameters.getClimate().getSolarFlux(
						// 30 degrees of tilt
						Math.PI / 6,
						// South facing
						Math.PI);

				final double overshadingFactor = constants.get(SolarConstants.OVERSHADING_FACTOR,
						double[].class)[overshading.ordinal()];

				/*
				 BEISDOC
				 NAME: Solar PV Generated Electricity
				 DESCRIPTION: The electricity generated by solar panels.
				 TYPE: formula
				 UNIT: W
				 SAP: (233), (M1)
                                 SAP_COMPLIANT: Yes
				 BREDEM: 10A
                                 BREDEM_COMPLIANT: Yes
				 DEPS: effective-solar-flux,solar-overshading-factor,solar-generation-factor
				 GET: house.energy-use
				 CONVERSION: No conversion is required. We have already converted all of the inputs to Watts by this point.
				 ID: pv-electricity-generated
				 CODSIEB
				 */
				final double generation = solarGenerationFactor * getPeakPower() * incidentRaditionPerUnitArea
						* overshadingFactor;

				StepRecorder.recordStep(EnergyCalculationStep.Generation_PhotoVoltaic, generation);

				final double usefulElectricity = useElectricityInDwelling(
						state,
						generation,
						parameters.getCalculatorType().generation == ECGeneration.SAP2012 ? sapOwnUseProportion : getOwnUseProportion(),
						parameters.getConstants().get(SplitRateConstants.DEFAULT_FRACTIONS, double[].class)
								[parameters.getTarrifType().ordinal()]
					);

				/*
				 BEISDOC
				 NAME: PV Exported Electricity
				 DESCRIPTION: The amount of electricity exported from Solar Panels
				 TYPE: formula
				 UNIT: W
				 SAP: (233)
                                 SAP_COMPLIANT: Yes
                                 BREDEM_COMPLIANT: N/A - out of scope
				 DEPS: pv-electricity-generated,pv-own-use-proportion
				 ID: pv-exported-electricity
				 CODSIEB
				 */
				state.increaseSupply(EnergyType.GenerationExportedElectricity, generation - usefulElectricity);
				state.increaseDemand(EnergyType.FuelPHOTONS, generation);
			}

			private double useElectricityInDwelling(
					final IEnergyState state,
					final double generation,
					final double ownUseProportion,
					final double highRateFraction) {

				/*
				 BEISDOC
				 NAME: Used Generated Electricity
				 DESCRIPTION: The electricity which is used in the home instead of exported.
				 TYPE: formula
				 UNIT: W
				 SAP: Appendix M (M1 section 3)
                                 SAP_COMPLIANT: Yes
                                 BREDEM_COMPLIANT: N/A - out of scope
				 DEPS: pv-electricity-generated,pv-own-use-proportion,default-split-rate
				 NOTES: The dwelling will not use more electricity than it has demand for.
				 ID: pv-useful-electricity
				 CODSIEB
				 */

				final double peakElectricityDemand = state.getUnsatisfiedDemand(EnergyType.FuelPEAK_ELECTRICITY);
				final double offpeakElectricityDemand = state.getUnsatisfiedDemand(EnergyType.FuelOFFPEAK_ELECTRICITY);

				final double ownUseAmount = generation * ownUseProportion;

				final double peakSupply = Math.max(
						0,
						Math.min(
								peakElectricityDemand,
								ownUseAmount * highRateFraction));

				final double offpeakSupply = Math.max(
						0,
						Math.min(
								offpeakElectricityDemand,
								ownUseAmount * (1 - highRateFraction)));

				state.increaseSupply(EnergyType.FuelPEAK_ELECTRICITY, peakSupply);
				state.increaseSupply(EnergyType.FuelOFFPEAK_ELECTRICITY, offpeakSupply);

				return peakSupply + offpeakSupply;
			}
		});
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ITechnologiesPackage.SOLAR_PHOTOVOLTAIC__PEAK_POWER:
				return getPeakPower();
			case ITechnologiesPackage.SOLAR_PHOTOVOLTAIC__OWN_USE_PROPORTION:
				return getOwnUseProportion();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case ITechnologiesPackage.SOLAR_PHOTOVOLTAIC__PEAK_POWER:
				setPeakPower((Double)newValue);
				return;
			case ITechnologiesPackage.SOLAR_PHOTOVOLTAIC__OWN_USE_PROPORTION:
				setOwnUseProportion((Double)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case ITechnologiesPackage.SOLAR_PHOTOVOLTAIC__PEAK_POWER:
				setPeakPower(PEAK_POWER_EDEFAULT);
				return;
			case ITechnologiesPackage.SOLAR_PHOTOVOLTAIC__OWN_USE_PROPORTION:
				setOwnUseProportion(OWN_USE_PROPORTION_EDEFAULT);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case ITechnologiesPackage.SOLAR_PHOTOVOLTAIC__PEAK_POWER:
				return peakPower != PEAK_POWER_EDEFAULT;
			case ITechnologiesPackage.SOLAR_PHOTOVOLTAIC__OWN_USE_PROPORTION:
				return ownUseProportion != OWN_USE_PROPORTION_EDEFAULT;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (peakPower: ");
		result.append(peakPower);
		result.append(", ownUseProportion: ");
		result.append(ownUseProportion);
		result.append(')');
		return result.toString();
	}

} // SolarPhotovoltaicImpl
