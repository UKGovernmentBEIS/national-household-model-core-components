/**
 */
package uk.org.cse.nhm.hom.emf.technologies.impl;

import java.util.concurrent.atomic.AtomicInteger;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import uk.org.cse.nhm.energycalculator.api.IConstants;
import uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorHouseCase;
import uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorParameters;
import uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorVisitor;
import uk.org.cse.nhm.energycalculator.api.IEnergyState;
import uk.org.cse.nhm.energycalculator.api.IEnergyTransducer;
import uk.org.cse.nhm.energycalculator.api.IInternalParameters;
import uk.org.cse.nhm.energycalculator.api.ISpecificHeatLosses;
import uk.org.cse.nhm.energycalculator.api.types.EnergyType;
import uk.org.cse.nhm.energycalculator.api.types.ServiceType;
import uk.org.cse.nhm.energycalculator.api.types.TransducerPhaseType;
import uk.org.cse.nhm.hom.IHeatProportions;
import uk.org.cse.nhm.hom.emf.technologies.ISolarPhotovoltaic;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologiesPackage;
import uk.org.cse.nhm.hom.emf.util.Efficiency;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Solar Photovoltaic</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.impl.SolarPhotovoltaicImpl#getArea <em>Area</em>}</li>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.impl.SolarPhotovoltaicImpl#getEfficiency <em>Efficiency</em>}</li>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.impl.SolarPhotovoltaicImpl#getOwnUseProportion <em>Own Use Proportion</em>}</li>
 * </ul>
 *
 * @generated
 */
public class SolarPhotovoltaicImpl extends MinimalEObjectImpl implements ISolarPhotovoltaic {
	/**
	 * A set of bit flags representing the values of boolean attributes and whether unsettable features have been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected int flags = 0;

	/**
	 * The default value of the '{@link #getArea() <em>Area</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getArea()
	 * @generated
	 * @ordered
	 */
	protected static final double AREA_EDEFAULT = 0.0;

	/**
	 * The cached value of the '{@link #getArea() <em>Area</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getArea()
	 * @generated
	 * @ordered
	 */
	protected double area = AREA_EDEFAULT;

	/**
	 * The default value of the '{@link #getEfficiency() <em>Efficiency</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEfficiency()
	 * @generated
	 * @ordered
	 */
	protected static final Efficiency EFFICIENCY_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getEfficiency() <em>Efficiency</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEfficiency()
	 * @generated
	 * @ordered
	 */
	protected Efficiency efficiency = EFFICIENCY_EDEFAULT;

	/**
	 * The default value of the '{@link #getOwnUseProportion() <em>Own Use Proportion</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOwnUseProportion()
	 * @generated
	 * @ordered
	 */
	protected static final double OWN_USE_PROPORTION_EDEFAULT = 0.0;

	/**
	 * The cached value of the '{@link #getOwnUseProportion() <em>Own Use Proportion</em>}' attribute.
	 * <!-- begin-user-doc -->
	BEISDOC
	NAME: PV Own Use Proportion
	DESCRIPTION: The proportion of generated solar PV electricity which is used in the home instead of exported.
	TYPE: value
	UNIT: Dimensionless
	SAP: Appendix M (M1 section 3)
	SET: measure.solar-photovoltaic
	ID: pv-own-use-proportion
	CODSIEB
	 * <!-- end-user-doc -->
	 * @see #getOwnUseProportion()
	 * @generated
	 * @ordered
	 */
	protected double ownUseProportion = OWN_USE_PROPORTION_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected SolarPhotovoltaicImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ITechnologiesPackage.Literals.SOLAR_PHOTOVOLTAIC;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public double getArea() {
		return area;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setArea(double newArea) {
		double oldArea = area;
		area = newArea;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ITechnologiesPackage.SOLAR_PHOTOVOLTAIC__AREA, oldArea, area));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Efficiency getEfficiency() {
		return efficiency;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEfficiency(Efficiency newEfficiency) {
		Efficiency oldEfficiency = efficiency;
		efficiency = newEfficiency;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ITechnologiesPackage.SOLAR_PHOTOVOLTAIC__EFFICIENCY, oldEfficiency, efficiency));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public double getOwnUseProportion() {
		return ownUseProportion;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated no
	 */
	@Override
	public void accept(final IConstants constants, final IEnergyCalculatorParameters parameters, final IEnergyCalculatorVisitor visitor, final AtomicInteger heatingSystemCounter, final IHeatProportions heatProportions) {
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
			public void generate(final IEnergyCalculatorHouseCase house,
					final IInternalParameters parameters, final ISpecificHeatLosses losses,
					final IEnergyState state) {
				
				final double electricityDemand = state.getUnsatisfiedDemand(EnergyType.FuelPEAK_ELECTRICITY);
				
				final double incidentRaditionPerUnitArea = parameters.getClimate().getSolarFlux(Math.PI / 6, Math.PI);
				final double photons = area * incidentRaditionPerUnitArea;
				
				/*
				BEISDOC
				NAME: Solar PV Generated Electricity
				DESCRIPTION: The electricity generated by solar panels.
				TYPE: formula
				UNIT: W
				SAP: (233), (M1)
				BREDEM: 10A
				DEPS: effective-solar-flux,overshading-factor
				GET: house.energy-use
				NOTES: TODO where is overshading?
				ID: pv-electricity-generated
				CODSIEB
				*/
				final double generation = photons * efficiency.value;
				
				/*
				BEISDOC
				NAME: Used Generated Electricity
				DESCRIPTION: The electricity which is used in the home instead of exported. 
				TYPE: formula
				UNIT: W
				SAP: Appendix M (M1 section 3)
				DEPS: pv-electricity-generated,pv-own-use-proportion
				ID: pv-useful-electricity
				CODSIEB
				*/
				final double usefulElectricity = Math.max(0, Math.min(electricityDemand, generation * getOwnUseProportion()));
				
				state.increaseSupply(EnergyType.FuelPEAK_ELECTRICITY, usefulElectricity);
			
				/*
				BEISDOC
				NAME: PV Exported Electricity
				DESCRIPTION: The amount of electricity exported from Solar Panels 
				TYPE: formula
				UNIT: W
				SAP: (233)
				DEPS: pv-electricity-generated,pv-own-use-proportion
				ID: pv-exported-electricity
				CODSIEB
				*/
				state.increaseSupply(EnergyType.GenerationExportedElectricity, generation - usefulElectricity);
				state.increaseDemand(EnergyType.FuelPHOTONS, photons);
			}
		});
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ITechnologiesPackage.SOLAR_PHOTOVOLTAIC__AREA:
				return getArea();
			case ITechnologiesPackage.SOLAR_PHOTOVOLTAIC__EFFICIENCY:
				return getEfficiency();
			case ITechnologiesPackage.SOLAR_PHOTOVOLTAIC__OWN_USE_PROPORTION:
				return getOwnUseProportion();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case ITechnologiesPackage.SOLAR_PHOTOVOLTAIC__AREA:
				setArea((Double)newValue);
				return;
			case ITechnologiesPackage.SOLAR_PHOTOVOLTAIC__EFFICIENCY:
				setEfficiency((Efficiency)newValue);
				return;
			case ITechnologiesPackage.SOLAR_PHOTOVOLTAIC__OWN_USE_PROPORTION:
				setOwnUseProportion((Double)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case ITechnologiesPackage.SOLAR_PHOTOVOLTAIC__AREA:
				setArea(AREA_EDEFAULT);
				return;
			case ITechnologiesPackage.SOLAR_PHOTOVOLTAIC__EFFICIENCY:
				setEfficiency(EFFICIENCY_EDEFAULT);
				return;
			case ITechnologiesPackage.SOLAR_PHOTOVOLTAIC__OWN_USE_PROPORTION:
				setOwnUseProportion(OWN_USE_PROPORTION_EDEFAULT);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case ITechnologiesPackage.SOLAR_PHOTOVOLTAIC__AREA:
				return area != AREA_EDEFAULT;
			case ITechnologiesPackage.SOLAR_PHOTOVOLTAIC__EFFICIENCY:
				return EFFICIENCY_EDEFAULT == null ? efficiency != null : !EFFICIENCY_EDEFAULT.equals(efficiency);
			case ITechnologiesPackage.SOLAR_PHOTOVOLTAIC__OWN_USE_PROPORTION:
				return ownUseProportion != OWN_USE_PROPORTION_EDEFAULT;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (area: ");
		result.append(area);
		result.append(", efficiency: ");
		result.append(efficiency);
		result.append(", ownUseProportion: ");
		result.append(ownUseProportion);
		result.append(')');
		return result.toString();
	}

} //SolarPhotovoltaicImpl
