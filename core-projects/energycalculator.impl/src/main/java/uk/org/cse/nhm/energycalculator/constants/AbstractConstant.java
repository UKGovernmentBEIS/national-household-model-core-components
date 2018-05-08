package uk.org.cse.nhm.energycalculator.constants;

import uk.org.cse.nhm.energycalculator.api.IConstant;

/**
 * This cannot be used to super an enum, which is annoying
 *
 * @author hinton
 *
 */
public abstract class AbstractConstant implements IConstant {

    private final double[] values;

    protected AbstractConstant(final double... values) {
        this.values = values;
    }

    @Override
    public <T> T getValue(Class<T> clazz) {
        if (clazz.isAssignableFrom(double[].class)) {
            if (values == null) {
                throw new RuntimeException(this + " cannot be read as a double[]");
            }
            return clazz.cast(values);
        } else if (clazz.isAssignableFrom(Double.class)) {
            if (values.length != 1) {
                throw new RuntimeException(this + " cannot be read as a double");
            }
            return clazz.cast(values[0]);
        } else {
            throw new RuntimeException(this + " cannot be read as a " + clazz.getSimpleName());
        }
    }

}
