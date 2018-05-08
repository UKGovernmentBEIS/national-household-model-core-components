/**
 * The technology model describes all the technologies present in a house. In order to accommodate convenient
 * containment, removal and similar operations, this part of the system is modelled using EMF, the Eclipse
 * Modelling Framework, an XMI-compatible modelling toolkit. The
 * <a href="http://www.eclipse.org/modeling/emf/">EMF page</a> is a good place
 * to start with an introduction to this system, but in summary:
 * <ul>
 * <li>The <code>technologies.ecore</code> file in <code>src/main/model/</code>
 * defines the class structure, attributes, operations and containment hierarchy
 * for all the technology elements</li>
 * <li>The associated <code>technologies.genmodel</code> contains instructions
 * for the EMF code generator, which turns the model description into java
 * implementation code</li>
 * </ul>
 * <p>
 * The code generator has an effective merging strategy which makes it safe to
 * mix generated and non-generated code. When a generated method has been
 * modified and should not be overwritten the "@generated false" attribute is
 * set on the appropriate element.
 * </p>
 * <p>
 * The technology model is composed of a few main parts, which store information
 * about the technologies and their relationships; the most complex parts of
 * these are the relationships between {@link uk.org.cse.nhm.hom.emf.technologies.IHeatSource},
 * {@link uk.org.cse.nhm.hom.emf.technologies.ICentralHeatingSystem}, and
 * {@link uk.org.cse.nhm.hom.emf.technologies.ICentralWaterSystem}.
 * </p>
 * <p>
 * </p>
 * <p>
 * To interact with the energy calculator, some parts of the technology model
 * implement the {@link uk.org.cse.nhm.hom.emf.technologies.IVisitorAccepter}
 * interface. When the
 * {@link uk.org.cse.nhm.hom.emf.technologies.ITechnologyModel} is asked to
 * accept an
 * {@link  uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorVisitor}, it
 * will pass that visitor to all of its contained elements which also implement
 * {@link  uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorVisitor}; they
 * then have the opportunity to supply
 * {@link  uk.org.cse.nhm.energycalculator.api.IEnergyTransducer} or
 * {@link  uk.org.cse.nhm.energycalculator.api.IHeatingSystem} implementations
 * into the energy calculation as they see fit.
 * </p>
 */
package uk.org.cse.nhm.hom.emf.technologies;
