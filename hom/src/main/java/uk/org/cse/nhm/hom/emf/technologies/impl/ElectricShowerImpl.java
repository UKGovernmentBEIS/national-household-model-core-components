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
import uk.org.cse.nhm.hom.emf.technologies.impl.util.ShowerTransducer;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Electric Shower</b></em>'.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public class ElectricShowerImpl extends WaterHeaterImpl implements IElectricShower {
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

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated no
	 */
	public void accept(IConstants constants, IEnergyCalculatorParameters parameters, IEnergyCalculatorVisitor visitor, AtomicInteger heatingSystemCounter, IHeatProportions heatProportions) {
		// TODO extract magic number into constants table.
		visitor.visitEnergyTransducer(new ShowerTransducer(ShowerTransducer.DEFAULT_POWER));
	}

} //ElectricShowerImpl
