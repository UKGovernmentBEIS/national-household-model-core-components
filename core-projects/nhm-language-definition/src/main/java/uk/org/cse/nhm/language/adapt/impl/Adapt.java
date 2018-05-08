package uk.org.cse.nhm.language.adapt.impl;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import uk.org.cse.nhm.language.adapt.IAdaptable;
import uk.org.cse.nhm.language.adapt.IAdapter;
import uk.org.cse.nhm.language.adapt.IAdaptingScope;
import uk.org.cse.nhm.language.visit.impl.AdaptableVisitable;

/**
 * Marks a method as one that can be used by {@link ReflectingAdapter}. If a
 * class extends {@link ReflectingAdapter}, all the methods it contains with the
 * {@link Adapt} annotation will be examined, and used to help it satisfy the
 * {@link IAdapter} interface.
 * <p>
 * Parameters of the method should fall into the following categories:
 * <ol>
 * <li> Properties of the adapted type -
 *
 * These should be annotated with {@link Prop}. If they can be directly assigned
 * without conversion (i.e. the parameter is assignable from the property), then
 * the property getter will be called and that value will be used. The value of
 * the property annotation is the name of the property (java bean style, so a
 * method getTheThing() indicates the presence of property "TheThing". an
 * additional feature here is that if a method on the adapted type is itself
 * annotated with {@link Prop} with the same {@link Prop#value()}, the method
 * name is ignored and the getter is presumed to be the method so annotated.
 * This means you can rename the getter so long as there is a common property
 * annotation which is preserved.).
 *
 * If they cannot be assigned, then if they are instances of {@link IAdaptable},
 * they will be adapted to the correct type if possible. If adapting fails (the
 * value returns null when adapted), this adapting method will never be invoked.
 * </li>
 * <li>
 * The adapted type itself - if you have a parameter which is assignable from
 * the adapted type, with no annotations, the thing being adapted will be
 * assigned
 * </li>
 * <li>
 * The adapting scope - if you have a parameter of type {@link IAdaptingScope},
 * it will be set to the current adapting scope.
 * </li>
 * <li>
 * Things from the scope - if you annotate a parameter with {@link FromScope},
 * its value will be taken from the current adapting scope with the given key
 * (if possible)
 * </li>
 * </ol>
 *
 * Any parameter may also be annotated with {@link PutScope}, as a convenience
 * for putting them into the adapting scope. Parameters are put in the adapting
 * scope <em>as they are computed</em>. This means that if parameter 1 is placed
 * into the scope, it is available from the scope during adaptation of parameter
 * 2, 3 ...
 *
 * @author hinton
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Adapt {

    /**
     * The kind of thing which the adapter method <em>consumes</em>. The kind of
     * thing which is produced is determined from the method's return type.
     *
     * @return
     */
    public Class<?> value();

    /**
     * @return if true, subclasses of {@link #value()} will be acceptable to
     * this method. otherwise only things whose class is exactly
     * {@link #value()} will be adapted.
     */
    public boolean inheritable() default false;

    /**
     * Whether to cache the result of a particular adapter run - you will
     * typically want this behaviour.
     *
     * Values are cached by {@link ReflectingAdapter}, and uncached by
     * {@link AdaptableVisitable} which uses the
     * {@link IAdaptingScope#getFromCache(Object, Class)} method to try and
     * uncache.
     *
     * Consequently once something adaptable has been cached in form X, it will
     * always be reused in form X <em>or any form convertable from X with an
     * IConverter</em> (presuming the standard {@link AdaptingScope} is being
     * used, which employs the type converters for this purpose).
     *
     * @return
     */
    public boolean cache() default true;
}
