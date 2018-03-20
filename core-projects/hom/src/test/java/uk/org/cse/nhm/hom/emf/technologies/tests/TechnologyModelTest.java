/**
 */
package uk.org.cse.nhm.hom.emf.technologies.tests;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.junit.Assert;

import junit.framework.TestCase;
import junit.textui.TestRunner;
import uk.org.cse.nhm.energycalculator.api.IConstants;
import uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorParameters;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologiesFactory;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologyModel;
import uk.org.cse.nhm.hom.emf.technologies.boilers.IBoiler;
import uk.org.cse.nhm.hom.emf.technologies.boilers.IBoilersFactory;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>Technology Model</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following operations are tested:
 * <ul>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.IVisitorAccepter#accept(IConstants, IEnergyCalculatorParameters, uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorVisitor, java.util.concurrent.atomic.AtomicInteger, double, double) <em>Accept</em>}</li>
 * </ul>
 * </p>
 * @generated
 */
public class TechnologyModelTest extends TestCase {

	/**
	 * The fixture for this Technology Model test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ITechnologyModel fixture = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(TechnologyModelTest.class);
	}

	/**
	 * Constructs a new Technology Model test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TechnologyModelTest(String name) {
		super(name);
	}

	/**
	 * Sets the fixture for this Technology Model test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void setFixture(ITechnologyModel fixture) {
		this.fixture = fixture;
	}

	/**
	 * Returns the fixture for this Technology Model test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ITechnologyModel getFixture() {
		return fixture;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#setUp()
	 * @generated
	 */
	@Override
	protected void setUp() throws Exception {
		setFixture(ITechnologiesFactory.eINSTANCE.createTechnologyModel());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#tearDown()
	 * @generated
	 */
	@Override
	protected void tearDown() throws Exception {
		setFixture(null);
	}

	/**
	 * Tests the '{@link uk.org.cse.nhm.hom.emf.technologies.IVisitorAccepter#accept(IConstants, IEnergyCalculatorParameters, uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorVisitor, java.util.concurrent.atomic.AtomicInteger, double, double) <em>Accept</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see uk.org.cse.nhm.hom.emf.technologies.IVisitorAccepter#accept(IConstants, IEnergyCalculatorParameters, uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorVisitor, java.util.concurrent.atomic.AtomicInteger, double, double)
	 * @generated no
	 */
	public void testAccept__IInternalParameters_IEnergyCalculatorVisitor_AtomicInteger() {
		// TODO: implement this operation test method
		// Ensure that you remove @generated or mark it @generated NOT
//		fail();
	}
	
	public void testCachingOfTotalOperationalCost() {
		ITechnologyModel fixture = getFixture();
		final double initialOpex = fixture.getTotalOperationalCost();
		Assert.assertEquals("The initial opex for a tech model should be zero", 0, initialOpex, 0);
		
		final IBoiler b = IBoilersFactory.eINSTANCE.createBoiler();
		
		b.setAnnualOperationalCost(100);
		
		fixture.setIndividualHeatSource(b);
		
		Assert.assertEquals("After adding the boiler, opex should be 100", 100, fixture.getTotalOperationalCost(), 0);
		Assert.assertEquals("After adding the boiler, opex should be 100, on second call", 100, fixture.getTotalOperationalCost(), 0);
		
		b.setAnnualOperationalCost(50);
		
		Assert.assertEquals("After modifying the contained boiler, opex should be 50", 50, fixture.getTotalOperationalCost(), 0);
		
		fixture.setIndividualHeatSource(null);
		
		Assert.assertEquals("After removing the boiler, opex should be 0", 0, fixture.getTotalOperationalCost(), 0);
		
	}
	
	public void testOperationalCostCacheDoesNotAffectEquality() {
		ITechnologyModel fixture = getFixture();
		final IBoiler b = IBoilersFactory.eINSTANCE.createBoiler();
		b.setAnnualOperationalCost(100);
		fixture.setIndividualHeatSource(b);
		
		final ITechnologyModel copy = EcoreUtil.copy(fixture);
		
		Assert.assertTrue("Fixture and copy should be identical to start with", 
				EcoreUtil.equals(fixture, copy));
		
		fixture.getTotalOperationalCost();
		
		Assert.assertTrue("Fixture and copy should be identical when one has updated cached cost", 
				EcoreUtil.equals(fixture, copy));
		
		copy.getTotalOperationalCost();
		
		Assert.assertTrue("Fixture and copy should be identical when both cached", 
				EcoreUtil.equals(fixture, copy));
		
		fixture.getIndividualHeatSource().setAnnualOperationalCost(50);
		copy.getIndividualHeatSource().setAnnualOperationalCost(50);
		
		Assert.assertTrue("Fixture and copy should be identical after identical cache invalidations", 
				EcoreUtil.equals(fixture, copy));
	}
	
	public void testOperationalCostCacheImprovesSpeed() {
		ITechnologyModel fixture = getFixture();
		final IBoiler boiler = IBoilersFactory.eINSTANCE.createBoiler();
		boiler.setAnnualOperationalCost(100);
		fixture.setIndividualHeatSource(boiler);
		
		long firsts = 0;
		long seconds = 0;
		
		for (int i = 0; i<100000; i++) {
			final ITechnologyModel copy = EcoreUtil.copy(fixture);
			long now = System.currentTimeMillis();
			final double a = copy.getTotalOperationalCost();
			long delta = System.currentTimeMillis() - now;
			
			firsts += delta;
			
			now = System.currentTimeMillis();
			final double b = copy.getTotalOperationalCost();
			delta = System.currentTimeMillis() - now;
			
			Assert.assertEquals("should be the same, but anyway that's not what this test is about", a, b, 0);
		}
		
		Assert.assertTrue(firsts + " ought to be more than " + seconds, firsts > seconds);
		System.err.println(String.format("uncached: %f, cached: %f",firsts/1000d, seconds/1000d));
	}
	
	public void testOperationalCostAdapterCopying() {
		ITechnologyModel fixture = getFixture();
		final IBoiler boiler = IBoilersFactory.eINSTANCE.createBoiler();
		boiler.setAnnualOperationalCost(100);
		fixture.setIndividualHeatSource(boiler);
		
		fixture.getTotalOperationalCost();
		Assert.assertEquals(1, fixture.eAdapters().size());
		
		final ITechnologyModel two = EcoreUtil.copy(fixture);
		Assert.assertEquals(0, two.eAdapters().size());
		two.getTotalOperationalCost();
		Assert.assertEquals(1, two.eAdapters().size());
	}
	
	public void testIsCopyable() {
		Assert.assertTrue(getFixture().copy() instanceof ITechnologyModel);
	}

} //TechnologyModelTest
