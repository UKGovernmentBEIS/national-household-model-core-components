/**
 */
package uk.org.cse.nhm.hom.emf.technologies;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Heat Pump Source
 * Type</b></em>', and utility methods for working with them.
 * <!-- end-user-doc -->
 *
 * @see
 * uk.org.cse.nhm.hom.emf.technologies.ITechnologiesPackage#getHeatPumpSourceType()
 * @model
 * @generated
 */
public enum HeatPumpSourceType implements Enumerator {
    /**
     * The '<em><b>Ground</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @see #GROUND_VALUE
     * @generated
     * @ordered
     */
    GROUND(0, "Ground", "Ground"),
    /**
     * The '<em><b>Air</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @see #AIR_VALUE
     * @generated
     * @ordered
     */
    AIR(1, "Air", "Air");

    /**
     * The '<em><b>Ground</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>Ground</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     *
     * @see #GROUND
     * @model name="Ground"
     * @generated
     * @ordered
     */
    public static final int GROUND_VALUE = 0;

    /**
     * The '<em><b>Air</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>Air</b></em>' literal object isn't clear, there
     * really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     *
     * @see #AIR
     * @model name="Air"
     * @generated
     * @ordered
     */
    public static final int AIR_VALUE = 1;

    /**
     * An array of all the '<em><b>Heat Pump Source Type</b></em>' enumerators.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     */
    private static final HeatPumpSourceType[] VALUES_ARRAY
            = new HeatPumpSourceType[]{
                GROUND,
                AIR,};

    /**
     * A public read-only list of all the '<em><b>Heat Pump Source
     * Type</b></em>' enumerators.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     */
    public static final List<HeatPumpSourceType> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

    /**
     * Returns the '<em><b>Heat Pump Source Type</b></em>' literal with the
     * specified literal value.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @param literal the literal.
     * @return the matching enumerator or <code>null</code>.
     * @generated
     */
    public static HeatPumpSourceType get(String literal) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
            HeatPumpSourceType result = VALUES_ARRAY[i];
            if (result.toString().equals(literal)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>Heat Pump Source Type</b></em>' literal with the
     * specified name.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @param name the name.
     * @return the matching enumerator or <code>null</code>.
     * @generated
     */
    public static HeatPumpSourceType getByName(String name) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
            HeatPumpSourceType result = VALUES_ARRAY[i];
            if (result.getName().equals(name)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>Heat Pump Source Type</b></em>' literal with the
     * specified integer value.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @param value the integer value.
     * @return the matching enumerator or <code>null</code>.
     * @generated
     */
    public static HeatPumpSourceType get(int value) {
        switch (value) {
            case GROUND_VALUE:
                return GROUND;
            case AIR_VALUE:
                return AIR;
        }
        return null;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc --> @generated
     */
    private final int value;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc --> @generated
     */
    private final String name;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc --> @generated
     */
    private final String literal;

    /**
     * Only this class can construct instances.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     */
    private HeatPumpSourceType(int value, String name, String literal) {
        this.value = value;
        this.name = name;
        this.literal = literal;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc --> @generated
     */
    public int getValue() {
        return value;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc --> @generated
     */
    public String getName() {
        return name;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc --> @generated
     */
    public String getLiteral() {
        return literal;
    }

    /**
     * Returns the literal value of the enumerator, which is its string
     * representation.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String toString() {
        return literal;
    }

} //HeatPumpSourceType
