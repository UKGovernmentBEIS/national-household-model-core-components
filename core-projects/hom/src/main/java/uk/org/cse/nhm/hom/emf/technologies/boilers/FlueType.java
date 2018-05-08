/**
 */
package uk.org.cse.nhm.hom.emf.technologies.boilers;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Flue
 * Type</b></em>', and utility methods for working with them.
 * <!-- end-user-doc -->
 *
 * @see
 * uk.org.cse.nhm.hom.emf.technologies.boilers.IBoilersPackage#getFlueType()
 * @model
 * @generated
 */
public enum FlueType implements Enumerator {
    /**
     * The '<em><b>Chimney</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @see #CHIMNEY_VALUE
     * @generated
     * @ordered
     */
    CHIMNEY(0, "Chimney", "Chimney"),
    /**
     * The '<em><b>Open Flue</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @see #OPEN_FLUE_VALUE
     * @generated
     * @ordered
     */
    OPEN_FLUE(1, "OpenFlue", "OpenFlue"),
    /**
     * The '<em><b>Balanced Flue</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @see #BALANCED_FLUE_VALUE
     * @generated
     * @ordered
     */
    BALANCED_FLUE(2, "BalancedFlue", "BalancedFlue"),
    /**
     * The '<em><b>Fan Assisted Balanced Flue</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @see #FAN_ASSISTED_BALANCED_FLUE_VALUE
     * @generated
     * @ordered
     */
    FAN_ASSISTED_BALANCED_FLUE(3, "FanAssistedBalancedFlue", "FanAssistedBalancedFlue"),
    /**
     * The '<em><b>Not Applicable</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @see #NOT_APPLICABLE_VALUE
     * @generated
     * @ordered
     */
    NOT_APPLICABLE(4, "NotApplicable", "NotApplicable");

    /**
     * The '<em><b>Chimney</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>Chimney</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     *
     * @see #CHIMNEY
     * @model name="Chimney"
     * @generated
     * @ordered
     */
    public static final int CHIMNEY_VALUE = 0;

    /**
     * The '<em><b>Open Flue</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>Open Flue</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     *
     * @see #OPEN_FLUE
     * @model name="OpenFlue"
     * @generated
     * @ordered
     */
    public static final int OPEN_FLUE_VALUE = 1;

    /**
     * The '<em><b>Balanced Flue</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>Balanced Flue</b></em>' literal object isn't
     * clear, there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     *
     * @see #BALANCED_FLUE
     * @model name="BalancedFlue"
     * @generated
     * @ordered
     */
    public static final int BALANCED_FLUE_VALUE = 2;

    /**
     * The '<em><b>Fan Assisted Balanced Flue</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>Fan Assisted Balanced Flue</b></em>' literal
     * object isn't clear, there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     *
     * @see #FAN_ASSISTED_BALANCED_FLUE
     * @model name="FanAssistedBalancedFlue"
     * @generated
     * @ordered
     */
    public static final int FAN_ASSISTED_BALANCED_FLUE_VALUE = 3;

    /**
     * The '<em><b>Not Applicable</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>Not Applicable</b></em>' literal object isn't
     * clear, there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     *
     * @see #NOT_APPLICABLE
     * @model name="NotApplicable"
     * @generated
     * @ordered
     */
    public static final int NOT_APPLICABLE_VALUE = 4;

    /**
     * An array of all the '<em><b>Flue Type</b></em>' enumerators.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     */
    private static final FlueType[] VALUES_ARRAY
            = new FlueType[]{
                CHIMNEY,
                OPEN_FLUE,
                BALANCED_FLUE,
                FAN_ASSISTED_BALANCED_FLUE,
                NOT_APPLICABLE,};

    /**
     * A public read-only list of all the '<em><b>Flue Type</b></em>'
     * enumerators.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     */
    public static final List<FlueType> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

    /**
     * Returns the '<em><b>Flue Type</b></em>' literal with the specified
     * literal value.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @param literal the literal.
     * @return the matching enumerator or <code>null</code>.
     * @generated
     */
    public static FlueType get(String literal) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
            FlueType result = VALUES_ARRAY[i];
            if (result.toString().equals(literal)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>Flue Type</b></em>' literal with the specified name.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @param name the name.
     * @return the matching enumerator or <code>null</code>.
     * @generated
     */
    public static FlueType getByName(String name) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
            FlueType result = VALUES_ARRAY[i];
            if (result.getName().equals(name)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>Flue Type</b></em>' literal with the specified
     * integer value.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @param value the integer value.
     * @return the matching enumerator or <code>null</code>.
     * @generated
     */
    public static FlueType get(int value) {
        switch (value) {
            case CHIMNEY_VALUE:
                return CHIMNEY;
            case OPEN_FLUE_VALUE:
                return OPEN_FLUE;
            case BALANCED_FLUE_VALUE:
                return BALANCED_FLUE;
            case FAN_ASSISTED_BALANCED_FLUE_VALUE:
                return FAN_ASSISTED_BALANCED_FLUE;
            case NOT_APPLICABLE_VALUE:
                return NOT_APPLICABLE;
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
    private FlueType(int value, String name, String literal) {
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

} //FlueType
