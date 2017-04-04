/**
 */
package uk.org.cse.nhm.hom.emf.technologies;

import java.util.concurrent.atomic.AtomicInteger;

import org.eclipse.emf.ecore.EObject;

import uk.org.cse.nhm.energycalculator.api.IConstants;
import uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorParameters;
import uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorVisitor;
import uk.org.cse.nhm.hom.IHeatProportions;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Visitor Accepter</b></em>'.
 * <!-- end-user-doc -->
 *
 *
 * @see uk.org.cse.nhm.hom.emf.technologies.ITechnologiesPackage#getVisitorAccepter()
 * @model interface="true" abstract="true"
 * @generated
 */
public interface IVisitorAccepter extends EObject {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model constantsDataType="uk.org.cse.nhm.hom.emf.technologies.IConstants" constantsRequired="true" parametersDataType="uk.org.cse.nhm.hom.emf.technologies.IEnergyCalculatorParameters" parametersRequired="true" visitorDataType="uk.org.cse.nhm.hom.emf.technologies.IEnergyCalculatorVisitor" visitorRequired="true" heatingSystemCounterDataType="uk.org.cse.nhm.hom.emf.technologies.AtomicInteger" heatingSystemCounterRequired="true" heatProportionsDataType="uk.org.cse.nhm.hom.emf.technologies.HeatProportions"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='/*\n * Default accept() method does nothing\n \052/'"
	 * @generated
	 */
	void accept(IConstants constants, IEnergyCalculatorParameters parameters, IEnergyCalculatorVisitor visitor, AtomicInteger heatingSystemCounter, IHeatProportions heatProportions);

} // IVisitorAccepter
