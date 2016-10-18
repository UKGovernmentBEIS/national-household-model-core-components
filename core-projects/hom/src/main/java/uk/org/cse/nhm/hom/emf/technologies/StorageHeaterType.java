/**
 */
package uk.org.cse.nhm.hom.emf.technologies;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Storage Heater Type</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see uk.org.cse.nhm.hom.emf.technologies.ITechnologiesPackage#getStorageHeaterType()
 * @model
 * @generated
 */
public enum StorageHeaterType implements Enumerator {
	/**
	 * The '<em><b>Old Large Volume</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #OLD_LARGE_VOLUME_VALUE
	 * @generated
	 * @ordered
	 */
	OLD_LARGE_VOLUME(0, "OldLargeVolume", "OldLargeVolume"),

	/**
	 * The '<em><b>Slimline</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #SLIMLINE_VALUE
	 * @generated
	 * @ordered
	 */
	SLIMLINE(1, "Slimline", "Slimline"),

	/**
	 * The '<em><b>Convector</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #CONVECTOR_VALUE
	 * @generated
	 * @ordered
	 */
	CONVECTOR(2, "Convector", "Convector"),

	/**
	 * The '<em><b>Fan</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #FAN_VALUE
	 * @generated
	 * @ordered
	 */
	FAN(3, "Fan", "Fan"),

	/**
	 * The '<em><b>Integrated Direct Acting</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #INTEGRATED_DIRECT_ACTING_VALUE
	 * @generated
	 * @ordered
	 */
	INTEGRATED_DIRECT_ACTING(4, "IntegratedDirectActing", "IntegratedDirectActing");

	/**
	 * The '<em><b>Old Large Volume</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Old Large Volume</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #OLD_LARGE_VOLUME
	 * @model name="OldLargeVolume"
	 * @generated
	 * @ordered
	 */
	public static final int OLD_LARGE_VOLUME_VALUE = 0;

	/**
	 * The '<em><b>Slimline</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Slimline</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #SLIMLINE
	 * @model name="Slimline"
	 * @generated
	 * @ordered
	 */
	public static final int SLIMLINE_VALUE = 1;

	/**
	 * The '<em><b>Convector</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Convector</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #CONVECTOR
	 * @model name="Convector"
	 * @generated
	 * @ordered
	 */
	public static final int CONVECTOR_VALUE = 2;

	/**
	 * The '<em><b>Fan</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Fan</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #FAN
	 * @model name="Fan"
	 * @generated
	 * @ordered
	 */
	public static final int FAN_VALUE = 3;

	/**
	 * The '<em><b>Integrated Direct Acting</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Integrated Direct Acting</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #INTEGRATED_DIRECT_ACTING
	 * @model name="IntegratedDirectActing"
	 * @generated
	 * @ordered
	 */
	public static final int INTEGRATED_DIRECT_ACTING_VALUE = 4;

	/**
	 * An array of all the '<em><b>Storage Heater Type</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final StorageHeaterType[] VALUES_ARRAY =
		new StorageHeaterType[] {
			OLD_LARGE_VOLUME,
			SLIMLINE,
			CONVECTOR,
			FAN,
			INTEGRATED_DIRECT_ACTING,
		};

	/**
	 * A public read-only list of all the '<em><b>Storage Heater Type</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<StorageHeaterType> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Storage Heater Type</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static StorageHeaterType get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			StorageHeaterType result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Storage Heater Type</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static StorageHeaterType getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			StorageHeaterType result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Storage Heater Type</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static StorageHeaterType get(int value) {
		switch (value) {
			case OLD_LARGE_VOLUME_VALUE: return OLD_LARGE_VOLUME;
			case SLIMLINE_VALUE: return SLIMLINE;
			case CONVECTOR_VALUE: return CONVECTOR;
			case FAN_VALUE: return FAN;
			case INTEGRATED_DIRECT_ACTING_VALUE: return INTEGRATED_DIRECT_ACTING;
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
	private StorageHeaterType(int value, String name, String literal) {
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
	
} //StorageHeaterType
