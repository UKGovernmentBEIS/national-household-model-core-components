package com.larkery.jasb.io.atom;

import java.util.Collections;
import java.util.Set;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableSet;
import com.larkery.jasb.io.IAtomIO;

public class NumberAtomIO implements IAtomIO {

    @Override
    public boolean canWrite(final Object object) {
        return object instanceof Number;
    }

    @Override
    public String write(final Object object) {
        return object.toString();
    }

    @Override
    public boolean canReadTo(final Class<?> output) {
        return Number.class.isAssignableFrom(output);
    }

    @Override
    public <T> Optional<T> read(String in, final Class<T> out) {
        final boolean percent;
        if (in.endsWith("%") && in.length() > 1) {
            in = in.substring(0, in.length() - 1);
            percent = true;
        } else {
            percent = false;
        }
        Object result = null;

        if (out == Double.class) {
            try {
                final double parsed = Double.parseDouble(in);
                if (percent) {
                    result = parsed / 100d;
                } else {
                    result = parsed;
                }
            } catch (final NumberFormatException nfe) {
            }
        } else if (out == Integer.class) {
            try {
                final int parsed = Integer.parseInt(in);
                if (percent) {
                    result = parsed / 100;
                } else {
                    result = parsed;
                }
            } catch (final NumberFormatException nfe) {
            }
        } else if (out == Float.class) {
            try {
                final float parsed = Float.parseFloat(in);
                if (percent) {
                    result = parsed / 100f;
                } else {
                    result = parsed;
                }
            } catch (final NumberFormatException nfe) {
            }
        } else if (out == Long.class) {
            try {
                final long parsed = Long.parseLong(in);
                if (percent) {
                    result = parsed / 100;
                } else {
                    result = parsed;
                }
            } catch (final NumberFormatException nfe) {
            }
        }

        if (result == null) {
            return Optional.absent();
        } else {
            return Optional.of(out.cast(result));
        }

    }

    @Override
    public boolean isBounded() {
        return false;
    }

    @Override
    public Set<String> getLegalValues(final Class<?> out) {
        if (out == Double.class || out == Float.class) {
            return ImmutableSet.of("0.5", "10%", "1000");
        } else if (out == Integer.class || out == Long.class) {
            return ImmutableSet.of("-1", "0", "1", "2");
        } else {
            return Collections.emptySet();
        }
    }

    @Override
    public String getDisplayName(final Class<?> out) {
        if (out == Double.class || out == Float.class) {
            return "Real Number";
        } else if (out == Integer.class || out == Long.class) {
            return "Whole Number";
        } else {
            return "";
        }
    }
}
