/**
 */
package uk.org.cse.nhm.hom.emf.technologies;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Storage Heater Control Type</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see uk.org.cse.nhm.hom.emf.technologies.ITechnologiesPackage#getStorageHeaterControlType()
 * @model
 * @generated
 */
public enum StorageHeaterControlType implements Enumerator {
	/**
	 * The '<em><b>Manual Charge Control</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #MANUAL_CHARGE_CONTROL_VALUE
	 * @generated
	 * @ordered
	 */
	MANUAL_CHARGE_CONTROL(0, "ManualChargeControl", "ManualChargeControl"),

	/**
	 * The '<em><b>Automatic Charge Control</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #AUTOMATIC_CHARGE_CONTROL_VALUE
	 * @generated
	 * @ordered
	 */
	AUTOMATIC_CHARGE_CONTROL(1, "AutomaticChargeControl", "AutomaticChargeControl"),

	/**
	 * The '<em><b>Celect Charge Control</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #CELECT_CHARGE_CONTROL_VALUE
	 * @generated
	 * @ordered
	 */
	CELECT_CHARGE_CONTROL(2, "CelectChargeControl", "CelectChargeControl");

	/**
	 * The '<em><b>Manual Charge Control</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Manual Charge Control</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #MANUAL_CHARGE_CONTROL
	 * @model name="ManualChargeControl"
	 * @generated
	 * @ordered
	 */
	public static final int MANUAL_CHARGE_CONTROL_VALUE = 0;

	/**
	 * The '<em><b>Automatic Charge Control</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Automatic Charge Control</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #AUTOMATIC_CHARGE_CONTROL
	 * @model name="AutomaticChargeControl"
	 * @generated
	 * @ordered
	 */
	public static final int AUTOMATIC_CHARGE_CONTROL_VALUE = 1;

	/**
	 * The '<em><b>Celect Charge Control</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Celect Charge Control</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #CELECT_CHARGE_CONTROL
	 * @model name="CelectChargeControl"
	 * @generated
	 * @ordered
	 */
	public static final int CELECT_CHARGE_CONTROL_VALUE = 2;

	/**
	 * An array of all the '<em><b>Storage Heater Control Type</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final StorageHeaterControlType[] VALUES_ARRAY =
		new StorageHeaterControlType[] {
			MANUAL_CHARGE_CONTROL,
			AUTOMATIC_CHARGE_CONTROL,
			CELECT_CHARGE_CONTROL,
		};

	/**
	 * A public read-only list of all the '<em><b>Storage Heater Control Type</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<StorageHeaterControlType> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Storage Heater Control Type</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static StorageHeaterControlType get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			StorageHeaterControlType result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Storage Heater Control Type</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static StorageHeaterControlType getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			StorageHeaterControlType result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Storage Heater Control Type</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static StorageHeaterControlType get(int value) {
		switch (value) {
			case MANUAL_CHARGE_CONTROL_VALUE: return MANUAL_CHARGE_CONTROL;
			case AUTOMATIC_CHARGE_CONTROL_VALUE: return AUTOMATIC_CHARGE_CONTROL;
			case CELECT_CHARGE_CONTROL_VALUE: return CELECT_CHARGE_CONTROL;
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
	private StorageHeaterControlType(int value, String name, String literal) {
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
	
} //StorageHeaterControlType
