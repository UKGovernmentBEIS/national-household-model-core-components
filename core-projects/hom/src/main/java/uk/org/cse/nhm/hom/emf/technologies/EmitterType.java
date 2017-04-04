/**
 */
package uk.org.cse.nhm.hom.emf.technologies;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Emitter Type</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see uk.org.cse.nhm.hom.emf.technologies.ITechnologiesPackage#getEmitterType()
 * @model
 * @generated
 */
public enum EmitterType implements Enumerator {
	/**
	 * The '<em><b>Radiators</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #RADIATORS_VALUE
	 * @generated
	 * @ordered
	 */
	RADIATORS(0, "Radiators", "Radiators"),

	/**
	 * The '<em><b>Underfloor Timber</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #UNDERFLOOR_TIMBER_VALUE
	 * @generated
	 * @ordered
	 */
	UNDERFLOOR_TIMBER(1, "UnderfloorTimber", "UnderfloorTimber"), /**
	 * The '<em><b>Underfloor Screed</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #UNDERFLOOR_SCREED_VALUE
	 * @generated
	 * @ordered
	 */
	UNDERFLOOR_SCREED(2, "UnderfloorScreed", "UnderfloorScreed"), /**
	 * The '<em><b>Underfloor Concrete</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #UNDERFLOOR_CONCRETE_VALUE
	 * @generated
	 * @ordered
	 */
	UNDERFLOOR_CONCRETE(3, "UnderfloorConcrete", "UnderfloorConcrete"), /**
	 * The '<em><b>Warm Air Fan Coil</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #WARM_AIR_FAN_COIL_VALUE
	 * @generated
	 * @ordered
	 */
	WARM_AIR_FAN_COIL(4, "WarmAirFanCoil", "WarmAirFanCoil");

	/**
	 * The '<em><b>Radiators</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Radiators</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #RADIATORS
	 * @model name="Radiators"
	 * @generated
	 * @ordered
	 */
	public static final int RADIATORS_VALUE = 0;

	/**
	 * The '<em><b>Underfloor Timber</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Underfloor Timber</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #UNDERFLOOR_TIMBER
	 * @model name="UnderfloorTimber"
	 * @generated
	 * @ordered
	 */
	public static final int UNDERFLOOR_TIMBER_VALUE = 1;

	/**
	 * The '<em><b>Underfloor Screed</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Underfloor Screed</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #UNDERFLOOR_SCREED
	 * @model name="UnderfloorScreed"
	 * @generated
	 * @ordered
	 */
	public static final int UNDERFLOOR_SCREED_VALUE = 2;

	/**
	 * The '<em><b>Underfloor Concrete</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Underfloor Concrete</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #UNDERFLOOR_CONCRETE
	 * @model name="UnderfloorConcrete"
	 * @generated
	 * @ordered
	 */
	public static final int UNDERFLOOR_CONCRETE_VALUE = 3;

	/**
	 * The '<em><b>Warm Air Fan Coil</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Warm Air Fan Coil</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #WARM_AIR_FAN_COIL
	 * @model name="WarmAirFanCoil"
	 * @generated
	 * @ordered
	 */
	public static final int WARM_AIR_FAN_COIL_VALUE = 4;

	/**
	 * An array of all the '<em><b>Emitter Type</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final EmitterType[] VALUES_ARRAY =
		new EmitterType[] {
			RADIATORS,
			UNDERFLOOR_TIMBER,
			UNDERFLOOR_SCREED,
			UNDERFLOOR_CONCRETE,
			WARM_AIR_FAN_COIL,
		};

	/**
	 * A public read-only list of all the '<em><b>Emitter Type</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<EmitterType> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Emitter Type</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param literal the literal.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static EmitterType get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			EmitterType result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Emitter Type</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param name the name.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static EmitterType getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			EmitterType result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Emitter Type</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the integer value.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static EmitterType get(int value) {
		switch (value) {
			case RADIATORS_VALUE: return RADIATORS;
			case UNDERFLOOR_TIMBER_VALUE: return UNDERFLOOR_TIMBER;
			case UNDERFLOOR_SCREED_VALUE: return UNDERFLOOR_SCREED;
			case UNDERFLOOR_CONCRETE_VALUE: return UNDERFLOOR_CONCRETE;
			case WARM_AIR_FAN_COIL_VALUE: return WARM_AIR_FAN_COIL;
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private final int value;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private final String name;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private final String literal;

	/**
	 * Only this class can construct instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EmitterType(int value, String name, String literal) {
		this.value = value;
		this.name = name;
		this.literal = literal;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getValue() {
	  return value;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
	  return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getLiteral() {
	  return literal;
	}

	/**
	 * Returns the literal value of the enumerator, which is its string representation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		return literal;
	}
	
} //EmitterType
