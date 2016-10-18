/**
 */
package uk.org.cse.nhm.hom.emf.technologies.impl;

import java.util.Set;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

import com.google.common.collect.ImmutableSet;

import uk.org.cse.nhm.energycalculator.api.IConstants;
import uk.org.cse.nhm.energycalculator.api.IEnergyState;
import uk.org.cse.nhm.energycalculator.api.types.EnergyType;
import uk.org.cse.nhm.hom.emf.technologies.ICommunityCHP;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologiesPackage;
import uk.org.cse.nhm.hom.emf.util.Efficiency;

/**
 * <!-- begin-user-doc -->
 * The difference between this and community heat source is just that it generates a bit of electricity for carbon offsetting purposes.
 * As such, all it does on top of the base class is (a) generate some electricity through the {@link #consumeSystemFuel(IEnergyState, double)} method, and
 * (b) modify the transducer outputs to include non-leading community electricity.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.impl.CommunityCHPImpl#getElectricalEfficiency <em>Electrical Efficiency</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class CommunityCHPImpl extends CommunityHeatSourceImpl implements ICommunityCHP {
	/**
	 * The default value of the '{@link #getElectricalEfficiency() <em>Electrical Efficiency</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getElectricalEfficiency()
	 * @generated
	 * @ordered
	 */
	protected static final Efficiency ELECTRICAL_EFFICIENCY_EDEFAULT = null;
	/**
	 * The cached value of the '{@link #getElectricalEfficiency() <em>Electrical Efficiency</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getElectricalEfficiency()
	 * @generated
	 * @ordered
	 */
	protected Efficiency electricalEfficiency = ELECTRICAL_EFFICIENCY_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected CommunityCHPImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ITechnologiesPackage.Literals.COMMUNITY_CHP;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Efficiency getElectricalEfficiency() {
		return electricalEfficiency;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setElectricalEfficiency(Efficiency newElectricalEfficiency) {
		Efficiency oldElectricalEfficiency = electricalEfficiency;
		electricalEfficiency = newElectricalEfficiency;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ITechnologiesPackage.COMMUNITY_CHP__ELECTRICAL_EFFICIENCY, oldElectricalEfficiency, electricalEfficiency));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ITechnologiesPackage.COMMUNITY_CHP__ELECTRICAL_EFFICIENCY:
				return getElectricalEfficiency();
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
			case ITechnologiesPackage.COMMUNITY_CHP__ELECTRICAL_EFFICIENCY:
				setElectricalEfficiency((Efficiency)newValue);
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
			case ITechnologiesPackage.COMMUNITY_CHP__ELECTRICAL_EFFICIENCY:
				setElectricalEfficiency(ELECTRICAL_EFFICIENCY_EDEFAULT);
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
			case ITechnologiesPackage.COMMUNITY_CHP__ELECTRICAL_EFFICIENCY:
				return ELECTRICAL_EFFICIENCY_EDEFAULT == null ? electricalEfficiency != null : !ELECTRICAL_EFFICIENCY_EDEFAULT.equals(electricalEfficiency);
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated no
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		return String.format("%s DCHP %f%% %f%%",
				getFuel() == null ? "" : getFuel().getName(),
						heatEfficiency.value * 100,
						electricalEfficiency.value * 100);
	}

	@Override
	protected void consumeSystemFuel(final IConstants constants, final IEnergyState state, final double communityHeatRequired) {
		
		
		final double fuelForSpace = 
				(communityHeatRequired 
//				 * constants.get(CommunityHeatingConstants.DEFAULT_DISTRIBUTION_LOSS_FACTOR)
				)
				/ getHeatEfficiency().value;
		final double electricity = fuelForSpace * getElectricalEfficiency().value;
		
		state.increaseDemand(getFuel().getCommunityEnergyType(), fuelForSpace);
		state.increaseSupply(EnergyType.CommunityELECTRICITY, electricity);
	}

	@Override
	protected Set<EnergyType> getHeatingOutputs() {
		return ImmutableSet.of( EnergyType.DemandsHEAT, EnergyType.CommunityELECTRICITY );
	}
} //CommunityCHPImpl
