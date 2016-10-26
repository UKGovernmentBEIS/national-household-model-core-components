/**
 */
package uk.org.cse.nhm.hom.emf.technologies.boilers;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * Where a boiler's efficiency numbers came from.
 * <!-- end-user-doc -->
 * @see uk.org.cse.nhm.hom.emf.technologies.boilers.IBoilersPackage#getEfficiencySourceType()
 * @model
 * @generated
 */
public enum EfficiencySourceType implements Enumerator {
	/**
	 * The '<em><b>SAP Default</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * Efficiency numbers looked up in SAP 2012 Table 4a or 4b 
	 * <!-- end-user-doc -->
	 * @see #SAP_DEFAULT_VALUE
	 * @generated
	 * @ordered
	 */
	SAP_DEFAULT(0, "SAPDefault", "SAPDefault"),

	/**
	 * The '<em><b>Manufacturer Declared</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * Efficiency numbers from the Product Characteristics Database
	 * <!-- end-user-doc -->
	 * @see #MANUFACTURER_DECLARED_VALUE
	 * @generated
	 * @ordered
	 */
	MANUFACTURER_DECLARED(1, "ManufacturerDeclared", "ManufacturerDeclared");

	/**
	 * The '<em><b>SAP Default</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>SAP Default</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #SAP_DEFAULT
	 * @model name="SAPDefault"
	 * @generated
	 * @ordered
	 */
	public static final int SAP_DEFAULT_VALUE = 0;

	/**
	 * The '<em><b>Manufacturer Declared</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Manufacturer Declared</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #MANUFACTURER_DECLARED
	 * @model name="ManufacturerDeclared"
	 * @generated
	 * @ordered
	 */
	public static final int MANUFACTURER_DECLARED_VALUE = 1;

	/**
	 * An array of all the '<em><b>Efficiency Source Type</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final EfficiencySourceType[] VALUES_ARRAY =
		new EfficiencySourceType[] {
			SAP_DEFAULT,
			MANUFACTURER_DECLARED,
		};

	/**
	 * A public read-only list of all the '<em><b>Efficiency Source Type</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<EfficiencySourceType> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Efficiency Source Type</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param literal the literal.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static EfficiencySourceType get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			EfficiencySourceType result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Efficiency Source Type</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param name the name.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static EfficiencySourceType getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			EfficiencySourceType result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Efficiency Source Type</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the integer value.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static EfficiencySourceType get(int value) {
		switch (value) {
			case SAP_DEFAULT_VALUE: return SAP_DEFAULT;
			case MANUFACTURER_DECLARED_VALUE: return MANUFACTURER_DECLARED;
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
	private EfficiencySourceType(int value, String name, String literal) {
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
	
} //EfficiencySourceType
