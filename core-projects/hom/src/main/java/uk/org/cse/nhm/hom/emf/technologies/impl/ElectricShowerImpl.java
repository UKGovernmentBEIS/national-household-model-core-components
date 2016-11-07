/**
 */
package uk.org.cse.nhm.hom.emf.technologies.impl;

import java.util.concurrent.atomic.AtomicInteger;
import org.eclipse.emf.ecore.EClass;
import uk.org.cse.nhm.energycalculator.api.IConstants;
import uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorParameters;
import uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorVisitor;
import uk.org.cse.nhm.hom.IHeatProportions;
import uk.org.cse.nhm.hom.emf.technologies.IElectricShower;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologiesPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Electric Shower</b></em>'.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public class ElectricShowerImpl extends ShowerImpl implements IElectricShower {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ElectricShowerImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ITechnologiesPackage.Literals.ELECTRIC_SHOWER;
	}

	@Override
	public double hotWaterVolumePerShower() {
		return 0;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated no
	 */
	@Override
	public double solarAdjustment() {
		return 0.64;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated no
	 */
	@Override
	public void accept(final IConstants constants, final IEnergyCalculatorParameters parameters, final IEnergyCalculatorVisitor visitor, final AtomicInteger heatingSystemCounter, final IHeatProportions heatProportions) {
		// get number of showers
		// make a special Sink

		switch (parameters.getCalculatorType()) {
		case SAP2012:
			break;
		case BREDEM2012:
			visitor.visitEnergyTransducer(new ElectricShowerTransducer(numShowers(parameters.getNumberOfOccupants())));
			break;
		default:
			throw new UnsupportedOperationException("Unknown energy calculator type when visiting electric shower " + parameters.getCalculatorType());
		}
	}

} //ElectricShowerImpl