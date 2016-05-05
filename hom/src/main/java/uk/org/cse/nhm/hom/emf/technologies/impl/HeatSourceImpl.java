/**
 */
package uk.org.cse.nhm.hom.emf.technologies.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import uk.org.cse.nhm.energycalculator.api.IConstants;
import uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorParameters;
import uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorVisitor;
import uk.org.cse.nhm.energycalculator.api.IEnergyState;
import uk.org.cse.nhm.energycalculator.api.IInternalParameters;
import uk.org.cse.nhm.hom.constants.adjustments.ResponsivenessAdjustments;
import uk.org.cse.nhm.hom.emf.technologies.EmitterType;
import uk.org.cse.nhm.hom.emf.technologies.FuelType;
import uk.org.cse.nhm.hom.emf.technologies.HeatingSystemControlType;
import uk.org.cse.nhm.hom.emf.technologies.ICentralHeatingSystem;
import uk.org.cse.nhm.hom.emf.technologies.IHasInstallationYear;
import uk.org.cse.nhm.hom.emf.technologies.IHasOverrideResponsiveness;
import uk.org.cse.nhm.hom.emf.technologies.IHeatSource;
import uk.org.cse.nhm.hom.emf.technologies.IMainWaterHeater;
import uk.org.cse.nhm.hom.emf.technologies.IOperationalCost;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologiesPackage;
import uk.org.cse.nhm.hom.emf.technologies.IWaterTank;
import uk.org.cse.nhm.hom.emf.technologies.boilers.FlueType;
import uk.org.cse.nhm.hom.emf.technologies.impl.util.HotWaterUtilities;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Heat Source</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.impl.HeatSourceImpl#getFuel <em>Fuel</em>}</li>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.impl.HeatSourceImpl#getFlueType <em>Flue Type</em>}</li>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.impl.HeatSourceImpl#getAnnualOperationalCost <em>Annual Operational Cost</em>}</li>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.impl.HeatSourceImpl#getOverrideResponsiveness <em>Override Responsiveness</em>}</li>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.impl.HeatSourceImpl#getInstallationYear <em>Installation Year</em>}</li>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.impl.HeatSourceImpl#getWaterHeater <em>Water Heater</em>}</li>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.impl.HeatSourceImpl#getSpaceHeater <em>Space Heater</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public abstract class HeatSourceImpl extends MinimalEObjectImpl implements IHeatSource {
	/**
	 * A set of bit flags representing the values of boolean attributes and whether unsettable features have been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected int flags = 0;

	/**
	 * The default value of the '{@link #getFuel() <em>Fuel</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFuel()
	 * @generated
	 * @ordered
	 */
	protected static final FuelType FUEL_EDEFAULT = FuelType.MAINS_GAS;

	/**
	 * The offset of the flags representing the value of the '{@link #getFuel() <em>Fuel</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected static final int FUEL_EFLAG_OFFSET = 0;

	/**
	 * The flags representing the default value of the '{@link #getFuel() <em>Fuel</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected static final int FUEL_EFLAG_DEFAULT = FUEL_EDEFAULT.ordinal() << FUEL_EFLAG_OFFSET;

	/**
	 * The array of enumeration values for '{@link FuelType Fuel Type}'
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	private static final FuelType[] FUEL_EFLAG_VALUES = FuelType.values();

	/**
	 * The flags representing the value of the '{@link #getFuel() <em>Fuel</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFuel()
	 * @generated
	 * @ordered
	 */
	protected static final int FUEL_EFLAG = 0xf << FUEL_EFLAG_OFFSET;

	/**
	 * The default value of the '{@link #getFlueType() <em>Flue Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFlueType()
	 * @generated
	 * @ordered
	 */
	protected static final FlueType FLUE_TYPE_EDEFAULT = FlueType.CHIMNEY;

	/**
	 * The offset of the flags representing the value of the '{@link #getFlueType() <em>Flue Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected static final int FLUE_TYPE_EFLAG_OFFSET = 4;

	/**
	 * The flags representing the default value of the '{@link #getFlueType() <em>Flue Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected static final int FLUE_TYPE_EFLAG_DEFAULT = FLUE_TYPE_EDEFAULT.ordinal() << FLUE_TYPE_EFLAG_OFFSET;

	/**
	 * The array of enumeration values for '{@link FlueType Flue Type}'
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	private static final FlueType[] FLUE_TYPE_EFLAG_VALUES = FlueType.values();

	/**
	 * The flags representing the value of the '{@link #getFlueType() <em>Flue Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFlueType()
	 * @generated
	 * @ordered
	 */
	protected static final int FLUE_TYPE_EFLAG = 0x7 << FLUE_TYPE_EFLAG_OFFSET;

	/**
	 * The default value of the '{@link #getAnnualOperationalCost() <em>Annual Operational Cost</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAnnualOperationalCost()
	 * @generated
	 * @ordered
	 */
	protected static final double ANNUAL_OPERATIONAL_COST_EDEFAULT = 0.0;

	/**
	 * The cached value of the '{@link #getAnnualOperationalCost() <em>Annual Operational Cost</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAnnualOperationalCost()
	 * @generated
	 * @ordered
	 */
	protected double annualOperationalCost = ANNUAL_OPERATIONAL_COST_EDEFAULT;

	/**
	 * The default value of the '{@link #getOverrideResponsiveness() <em>Override Responsiveness</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOverrideResponsiveness()
	 * @generated
	 * @ordered
	 */
	protected static final double OVERRIDE_RESPONSIVENESS_EDEFAULT = 0.0;

	/**
	 * The cached value of the '{@link #getOverrideResponsiveness() <em>Override Responsiveness</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOverrideResponsiveness()
	 * @generated
	 * @ordered
	 */
	protected double overrideResponsiveness = OVERRIDE_RESPONSIVENESS_EDEFAULT;

	/**
	 * The flag representing whether the Override Responsiveness attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected static final int OVERRIDE_RESPONSIVENESS_ESETFLAG = 1 << 7;

	/**
	 * The default value of the '{@link #getInstallationYear() <em>Installation Year</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInstallationYear()
	 * @generated
	 * @ordered
	 */
	protected static final int INSTALLATION_YEAR_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getInstallationYear() <em>Installation Year</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInstallationYear()
	 * @generated
	 * @ordered
	 */
	protected int installationYear = INSTALLATION_YEAR_EDEFAULT;

	/**
	 * The flag representing whether the Installation Year attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected static final int INSTALLATION_YEAR_ESETFLAG = 1 << 8;

	/**
	 * The cached value of the '{@link #getWaterHeater() <em>Water Heater</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getWaterHeater()
	 * @generated
	 * @ordered
	 */
	protected IMainWaterHeater waterHeater;

	/**
	 * The cached value of the '{@link #getSpaceHeater() <em>Space Heater</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSpaceHeater()
	 * @generated
	 * @ordered
	 */
	protected ICentralHeatingSystem spaceHeater;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected HeatSourceImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ITechnologiesPackage.Literals.HEAT_SOURCE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public FuelType getFuel() {
		return FUEL_EFLAG_VALUES[(flags & FUEL_EFLAG) >>> FUEL_EFLAG_OFFSET];
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setFuel(FuelType newFuel) {
		FuelType oldFuel = FUEL_EFLAG_VALUES[(flags & FUEL_EFLAG) >>> FUEL_EFLAG_OFFSET];
		if (newFuel == null) newFuel = FUEL_EDEFAULT;
		flags = flags & ~FUEL_EFLAG | newFuel.ordinal() << FUEL_EFLAG_OFFSET;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ITechnologiesPackage.HEAT_SOURCE__FUEL, oldFuel, newFuel));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public FlueType getFlueType() {
		return FLUE_TYPE_EFLAG_VALUES[(flags & FLUE_TYPE_EFLAG) >>> FLUE_TYPE_EFLAG_OFFSET];
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setFlueType(FlueType newFlueType) {
		FlueType oldFlueType = FLUE_TYPE_EFLAG_VALUES[(flags & FLUE_TYPE_EFLAG) >>> FLUE_TYPE_EFLAG_OFFSET];
		if (newFlueType == null) newFlueType = FLUE_TYPE_EDEFAULT;
		flags = flags & ~FLUE_TYPE_EFLAG | newFlueType.ordinal() << FLUE_TYPE_EFLAG_OFFSET;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ITechnologiesPackage.HEAT_SOURCE__FLUE_TYPE, oldFlueType, newFlueType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public double getAnnualOperationalCost() {
		return annualOperationalCost;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setAnnualOperationalCost(double newAnnualOperationalCost) {
		double oldAnnualOperationalCost = annualOperationalCost;
		annualOperationalCost = newAnnualOperationalCost;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ITechnologiesPackage.HEAT_SOURCE__ANNUAL_OPERATIONAL_COST, oldAnnualOperationalCost, annualOperationalCost));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public double getOverrideResponsiveness() {
		return overrideResponsiveness;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setOverrideResponsiveness(double newOverrideResponsiveness) {
		double oldOverrideResponsiveness = overrideResponsiveness;
		overrideResponsiveness = newOverrideResponsiveness;
		boolean oldOverrideResponsivenessESet = (flags & OVERRIDE_RESPONSIVENESS_ESETFLAG) != 0;
		flags |= OVERRIDE_RESPONSIVENESS_ESETFLAG;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ITechnologiesPackage.HEAT_SOURCE__OVERRIDE_RESPONSIVENESS, oldOverrideResponsiveness, overrideResponsiveness, !oldOverrideResponsivenessESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void unsetOverrideResponsiveness() {
		double oldOverrideResponsiveness = overrideResponsiveness;
		boolean oldOverrideResponsivenessESet = (flags & OVERRIDE_RESPONSIVENESS_ESETFLAG) != 0;
		overrideResponsiveness = OVERRIDE_RESPONSIVENESS_EDEFAULT;
		flags &= ~OVERRIDE_RESPONSIVENESS_ESETFLAG;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, ITechnologiesPackage.HEAT_SOURCE__OVERRIDE_RESPONSIVENESS, oldOverrideResponsiveness, OVERRIDE_RESPONSIVENESS_EDEFAULT, oldOverrideResponsivenessESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isSetOverrideResponsiveness() {
		return (flags & OVERRIDE_RESPONSIVENESS_ESETFLAG) != 0;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public IMainWaterHeater getWaterHeater() {
		if (waterHeater != null && waterHeater.eIsProxy()) {
			InternalEObject oldWaterHeater = (InternalEObject)waterHeater;
			waterHeater = (IMainWaterHeater)eResolveProxy(oldWaterHeater);
			if (waterHeater != oldWaterHeater) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, ITechnologiesPackage.HEAT_SOURCE__WATER_HEATER, oldWaterHeater, waterHeater));
			}
		}
		return waterHeater;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IMainWaterHeater basicGetWaterHeater() {
		return waterHeater;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetWaterHeater(IMainWaterHeater newWaterHeater, NotificationChain msgs) {
		IMainWaterHeater oldWaterHeater = waterHeater;
		waterHeater = newWaterHeater;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ITechnologiesPackage.HEAT_SOURCE__WATER_HEATER, oldWaterHeater, newWaterHeater);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setWaterHeater(IMainWaterHeater newWaterHeater) {
		if (newWaterHeater != waterHeater) {
			NotificationChain msgs = null;
			if (waterHeater != null)
				msgs = ((InternalEObject)waterHeater).eInverseRemove(this, ITechnologiesPackage.MAIN_WATER_HEATER__HEAT_SOURCE, IMainWaterHeater.class, msgs);
			if (newWaterHeater != null)
				msgs = ((InternalEObject)newWaterHeater).eInverseAdd(this, ITechnologiesPackage.MAIN_WATER_HEATER__HEAT_SOURCE, IMainWaterHeater.class, msgs);
			msgs = basicSetWaterHeater(newWaterHeater, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ITechnologiesPackage.HEAT_SOURCE__WATER_HEATER, newWaterHeater, newWaterHeater));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ICentralHeatingSystem getSpaceHeater() {
		if (spaceHeater != null && spaceHeater.eIsProxy()) {
			InternalEObject oldSpaceHeater = (InternalEObject)spaceHeater;
			spaceHeater = (ICentralHeatingSystem)eResolveProxy(oldSpaceHeater);
			if (spaceHeater != oldSpaceHeater) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, ITechnologiesPackage.HEAT_SOURCE__SPACE_HEATER, oldSpaceHeater, spaceHeater));
			}
		}
		return spaceHeater;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ICentralHeatingSystem basicGetSpaceHeater() {
		return spaceHeater;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSpaceHeater(ICentralHeatingSystem newSpaceHeater, NotificationChain msgs) {
		ICentralHeatingSystem oldSpaceHeater = spaceHeater;
		spaceHeater = newSpaceHeater;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ITechnologiesPackage.HEAT_SOURCE__SPACE_HEATER, oldSpaceHeater, newSpaceHeater);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setSpaceHeater(ICentralHeatingSystem newSpaceHeater) {
		if (newSpaceHeater != spaceHeater) {
			NotificationChain msgs = null;
			if (spaceHeater != null)
				msgs = ((InternalEObject)spaceHeater).eInverseRemove(this, ITechnologiesPackage.CENTRAL_HEATING_SYSTEM__HEAT_SOURCE, ICentralHeatingSystem.class, msgs);
			if (newSpaceHeater != null)
				msgs = ((InternalEObject)newSpaceHeater).eInverseAdd(this, ITechnologiesPackage.CENTRAL_HEATING_SYSTEM__HEAT_SOURCE, ICentralHeatingSystem.class, msgs);
			msgs = basicSetSpaceHeater(newSpaceHeater, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ITechnologiesPackage.HEAT_SOURCE__SPACE_HEATER, newSpaceHeater, newSpaceHeater));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int getInstallationYear() {
		return installationYear;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setInstallationYear(int newInstallationYear) {
		int oldInstallationYear = installationYear;
		installationYear = newInstallationYear;
		boolean oldInstallationYearESet = (flags & INSTALLATION_YEAR_ESETFLAG) != 0;
		flags |= INSTALLATION_YEAR_ESETFLAG;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ITechnologiesPackage.HEAT_SOURCE__INSTALLATION_YEAR, oldInstallationYear, installationYear, !oldInstallationYearESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetInstallationYear() {
		int oldInstallationYear = installationYear;
		boolean oldInstallationYearESet = (flags & INSTALLATION_YEAR_ESETFLAG) != 0;
		installationYear = INSTALLATION_YEAR_EDEFAULT;
		flags &= ~INSTALLATION_YEAR_ESETFLAG;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, ITechnologiesPackage.HEAT_SOURCE__INSTALLATION_YEAR, oldInstallationYear, INSTALLATION_YEAR_EDEFAULT, oldInstallationYearESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetInstallationYear() {
		return (flags & INSTALLATION_YEAR_ESETFLAG) != 0;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void acceptFromHeating(IConstants constants, IEnergyCalculatorParameters parameters, IEnergyCalculatorVisitor visitor, double proportion, int priority) {
		// TODO: implement this method
		// Ensure that you remove @generated or mark it @generated NOT
		throw new UnsupportedOperationException();
	}

	/**
	 * <!-- begin-user-doc -->
	 * The default behaviour is to assume SAP Table 4d, since most heat sources will follow that table.
	 * <!-- end-user-doc -->
	 * @generated no
	 */
	@Override
	public final double getResponsiveness(final IConstants constants, final EList<HeatingSystemControlType> controls, final EmitterType emitterType) {
		if (isSetOverrideResponsiveness()) {
			return getOverrideResponsiveness();
		} else {
			return getResponsivenessImpl(constants, controls, emitterType);
		}
	}
	
	protected final double getSAPTable4dResponsiveness(final IConstants constants, final EList<HeatingSystemControlType> controls, final EmitterType emitterType) {
		return constants.get(ResponsivenessAdjustments.WET_SYSTEM_RESPONSIVENESS, emitterType);
	}
	
	protected abstract double getResponsivenessImpl(final IConstants constants, final EList<HeatingSystemControlType> controls, final EmitterType emitterType);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public double getDemandTemperatureAdjustment(IInternalParameters parameters, EList<HeatingSystemControlType> controlTypes) {
		// TODO: implement this method
		// Ensure that you remove @generated or mark it @generated NOT
		throw new UnsupportedOperationException();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public double generateHotWaterAndPrimaryGains(IInternalParameters parameters, IEnergyState state, IWaterTank store, boolean storeIsPrimary, double primaryCorrectionFactor, double distributionLossFactor, double proportion) {
		// TODO: implement this method
		// Ensure that you remove @generated or mark it @generated NOT
		throw new UnsupportedOperationException();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void generateHotWaterSystemGains(IInternalParameters parameters, IEnergyState state, IWaterTank store, boolean storeIsPrimary, double systemLosses) {
		// TODO: implement this method
		// Ensure that you remove @generated or mark it @generated NOT
		throw new UnsupportedOperationException();
	}

	/**
	 * <!-- begin-user-doc -->
	 * The default behaviour for storage temperature factor, which is impemented in {@link HotWaterUtilities}.
	 * <!-- end-user-doc -->
	 * @generated no
	 */
	@Override
	public double getStorageTemperatureFactor(final IInternalParameters parameters, final IWaterTank store, final boolean storeInPrimaryCircuit) {
		return HotWaterUtilities.getStorageTemperatureFactor(parameters, store, storeInPrimaryCircuit, 
				getWaterHeater().getSystem().isSeparatelyTimeControlled());
	}

	/**
	 * <!-- begin-user-doc -->
	 * Most things don't have a contained tank, so I have implemented this one.
	 * <!-- end-user-doc -->
	 * @generated no
	 */
	@Override
	public double getContainedTankLosses(final IInternalParameters parameters) {
		return 0;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public double getZoneTwoControlParameter(IInternalParameters parameters, EList<HeatingSystemControlType> controls, EmitterType emitterType) {
		// TODO: implement this method
		// Ensure that you remove @generated or mark it @generated NOT
		throw new UnsupportedOperationException();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ITechnologiesPackage.HEAT_SOURCE__WATER_HEATER:
				if (waterHeater != null)
					msgs = ((InternalEObject)waterHeater).eInverseRemove(this, ITechnologiesPackage.MAIN_WATER_HEATER__HEAT_SOURCE, IMainWaterHeater.class, msgs);
				return basicSetWaterHeater((IMainWaterHeater)otherEnd, msgs);
			case ITechnologiesPackage.HEAT_SOURCE__SPACE_HEATER:
				if (spaceHeater != null)
					msgs = ((InternalEObject)spaceHeater).eInverseRemove(this, ITechnologiesPackage.CENTRAL_HEATING_SYSTEM__HEAT_SOURCE, ICentralHeatingSystem.class, msgs);
				return basicSetSpaceHeater((ICentralHeatingSystem)otherEnd, msgs);
		}
		return super.eInverseAdd(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ITechnologiesPackage.HEAT_SOURCE__WATER_HEATER:
				return basicSetWaterHeater(null, msgs);
			case ITechnologiesPackage.HEAT_SOURCE__SPACE_HEATER:
				return basicSetSpaceHeater(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ITechnologiesPackage.HEAT_SOURCE__FUEL:
				return getFuel();
			case ITechnologiesPackage.HEAT_SOURCE__FLUE_TYPE:
				return getFlueType();
			case ITechnologiesPackage.HEAT_SOURCE__ANNUAL_OPERATIONAL_COST:
				return getAnnualOperationalCost();
			case ITechnologiesPackage.HEAT_SOURCE__OVERRIDE_RESPONSIVENESS:
				return getOverrideResponsiveness();
			case ITechnologiesPackage.HEAT_SOURCE__INSTALLATION_YEAR:
				return getInstallationYear();
			case ITechnologiesPackage.HEAT_SOURCE__WATER_HEATER:
				if (resolve) return getWaterHeater();
				return basicGetWaterHeater();
			case ITechnologiesPackage.HEAT_SOURCE__SPACE_HEATER:
				if (resolve) return getSpaceHeater();
				return basicGetSpaceHeater();
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
			case ITechnologiesPackage.HEAT_SOURCE__FUEL:
				setFuel((FuelType)newValue);
				return;
			case ITechnologiesPackage.HEAT_SOURCE__FLUE_TYPE:
				setFlueType((FlueType)newValue);
				return;
			case ITechnologiesPackage.HEAT_SOURCE__ANNUAL_OPERATIONAL_COST:
				setAnnualOperationalCost((Double)newValue);
				return;
			case ITechnologiesPackage.HEAT_SOURCE__OVERRIDE_RESPONSIVENESS:
				setOverrideResponsiveness((Double)newValue);
				return;
			case ITechnologiesPackage.HEAT_SOURCE__INSTALLATION_YEAR:
				setInstallationYear((Integer)newValue);
				return;
			case ITechnologiesPackage.HEAT_SOURCE__WATER_HEATER:
				setWaterHeater((IMainWaterHeater)newValue);
				return;
			case ITechnologiesPackage.HEAT_SOURCE__SPACE_HEATER:
				setSpaceHeater((ICentralHeatingSystem)newValue);
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
			case ITechnologiesPackage.HEAT_SOURCE__FUEL:
				setFuel(FUEL_EDEFAULT);
				return;
			case ITechnologiesPackage.HEAT_SOURCE__FLUE_TYPE:
				setFlueType(FLUE_TYPE_EDEFAULT);
				return;
			case ITechnologiesPackage.HEAT_SOURCE__ANNUAL_OPERATIONAL_COST:
				setAnnualOperationalCost(ANNUAL_OPERATIONAL_COST_EDEFAULT);
				return;
			case ITechnologiesPackage.HEAT_SOURCE__OVERRIDE_RESPONSIVENESS:
				unsetOverrideResponsiveness();
				return;
			case ITechnologiesPackage.HEAT_SOURCE__INSTALLATION_YEAR:
				unsetInstallationYear();
				return;
			case ITechnologiesPackage.HEAT_SOURCE__WATER_HEATER:
				setWaterHeater((IMainWaterHeater)null);
				return;
			case ITechnologiesPackage.HEAT_SOURCE__SPACE_HEATER:
				setSpaceHeater((ICentralHeatingSystem)null);
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
			case ITechnologiesPackage.HEAT_SOURCE__FUEL:
				return (flags & FUEL_EFLAG) != FUEL_EFLAG_DEFAULT;
			case ITechnologiesPackage.HEAT_SOURCE__FLUE_TYPE:
				return (flags & FLUE_TYPE_EFLAG) != FLUE_TYPE_EFLAG_DEFAULT;
			case ITechnologiesPackage.HEAT_SOURCE__ANNUAL_OPERATIONAL_COST:
				return annualOperationalCost != ANNUAL_OPERATIONAL_COST_EDEFAULT;
			case ITechnologiesPackage.HEAT_SOURCE__OVERRIDE_RESPONSIVENESS:
				return isSetOverrideResponsiveness();
			case ITechnologiesPackage.HEAT_SOURCE__INSTALLATION_YEAR:
				return isSetInstallationYear();
			case ITechnologiesPackage.HEAT_SOURCE__WATER_HEATER:
				return waterHeater != null;
			case ITechnologiesPackage.HEAT_SOURCE__SPACE_HEATER:
				return spaceHeater != null;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eBaseStructuralFeatureID(int derivedFeatureID, Class<?> baseClass) {
		if (baseClass == IOperationalCost.class) {
			switch (derivedFeatureID) {
				case ITechnologiesPackage.HEAT_SOURCE__ANNUAL_OPERATIONAL_COST: return ITechnologiesPackage.OPERATIONAL_COST__ANNUAL_OPERATIONAL_COST;
				default: return -1;
			}
		}
		if (baseClass == IHasOverrideResponsiveness.class) {
			switch (derivedFeatureID) {
				case ITechnologiesPackage.HEAT_SOURCE__OVERRIDE_RESPONSIVENESS: return ITechnologiesPackage.HAS_OVERRIDE_RESPONSIVENESS__OVERRIDE_RESPONSIVENESS;
				default: return -1;
			}
		}
		if (baseClass == IHasInstallationYear.class) {
			switch (derivedFeatureID) {
				case ITechnologiesPackage.HEAT_SOURCE__INSTALLATION_YEAR: return ITechnologiesPackage.HAS_INSTALLATION_YEAR__INSTALLATION_YEAR;
				default: return -1;
			}
		}
		return super.eBaseStructuralFeatureID(derivedFeatureID, baseClass);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eDerivedStructuralFeatureID(int baseFeatureID, Class<?> baseClass) {
		if (baseClass == IOperationalCost.class) {
			switch (baseFeatureID) {
				case ITechnologiesPackage.OPERATIONAL_COST__ANNUAL_OPERATIONAL_COST: return ITechnologiesPackage.HEAT_SOURCE__ANNUAL_OPERATIONAL_COST;
				default: return -1;
			}
		}
		if (baseClass == IHasOverrideResponsiveness.class) {
			switch (baseFeatureID) {
				case ITechnologiesPackage.HAS_OVERRIDE_RESPONSIVENESS__OVERRIDE_RESPONSIVENESS: return ITechnologiesPackage.HEAT_SOURCE__OVERRIDE_RESPONSIVENESS;
				default: return -1;
			}
		}
		if (baseClass == IHasInstallationYear.class) {
			switch (baseFeatureID) {
				case ITechnologiesPackage.HAS_INSTALLATION_YEAR__INSTALLATION_YEAR: return ITechnologiesPackage.HEAT_SOURCE__INSTALLATION_YEAR;
				default: return -1;
			}
		}
		return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
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
		result.append(" (fuel: ");
		result.append(FUEL_EFLAG_VALUES[(flags & FUEL_EFLAG) >>> FUEL_EFLAG_OFFSET]);
		result.append(", flueType: ");
		result.append(FLUE_TYPE_EFLAG_VALUES[(flags & FLUE_TYPE_EFLAG) >>> FLUE_TYPE_EFLAG_OFFSET]);
		result.append(", annualOperationalCost: ");
		result.append(annualOperationalCost);
		result.append(", overrideResponsiveness: ");
		if ((flags & OVERRIDE_RESPONSIVENESS_ESETFLAG) != 0) result.append(overrideResponsiveness); else result.append("<unset>");
		result.append(", installationYear: ");
		if ((flags & INSTALLATION_YEAR_ESETFLAG) != 0) result.append(installationYear); else result.append("<unset>");
		result.append(')');
		return result.toString();
	}

} //HeatSourceImpl
