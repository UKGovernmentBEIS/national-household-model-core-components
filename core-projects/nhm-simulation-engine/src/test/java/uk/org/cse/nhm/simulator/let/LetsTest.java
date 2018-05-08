package uk.org.cse.nhm.simulator.let;

import java.util.Collections;

import org.junit.Assert;
import org.junit.Test;

public class LetsTest {

    @Test
    public void emptyIsNotNull() {
        Assert.assertNotNull(ILets.EMPTY);
    }

    @Test
    public void emptyHasNothingInIt() {
        Assert.assertFalse(
                ILets.EMPTY.get(new Object(), Object.class).isPresent());
    }

    @Test
    public void emptyWithNothingAddedIsEmpty() {
        Assert.assertTrue(
                ILets.EMPTY
                        .withBindings(Collections.emptyMap()) instanceof Lets.Empty);
    }

    @Test
    public void emptyWithSomethingAddedContainsIt() {
        final Object k = new Object();
        final Object v = new Object();
        final ILets withBinding = ILets.EMPTY
                .withBinding(k, v);

        Assert.assertTrue(withBinding.
                get(k, Object.class).isPresent());
    }

    @Test
    public void emptyWithOverride() {
        final Object k = new Object();
        final Object v = new Object();
        final Object v2 = new Object();
        final ILets withBinding = ILets.EMPTY
                .withBinding(k, v);

        final ILets withOverride = withBinding.withBinding(k, v2);

        Assert.assertSame(v, withBinding.get(k, Object.class).get());
        Assert.assertSame(v2, withOverride.get(k, Object.class).get());
    }

    @Test
    public void assignableToFiltersThings() {
        final Object k = new Object();
        final Object v = new Object();
        final ILets withBinding = ILets.EMPTY
                .withBinding(k, v);
        final ILets withoutThings
                = withBinding.assignableTo(String.class);

        Assert.assertFalse(withoutThings.get(k, Object.class).isPresent());

        Assert.assertTrue(withoutThings == ILets.EMPTY);
    }

    @Test
    public void overridingWithLetsWorksWhenDoingNothing() {
        final Object k = new Object();
        final Object v = new Object();
        final ILets withBinding = ILets.EMPTY
                .withBinding(k, v);

        final ILets same = withBinding.withLets(ILets.EMPTY);

        Assert.assertSame(withBinding, same);
    }

    @Test
    public void overridingWithLetsWorksWhenActuallyDoingSomething() {
        final Object k = new Object();
        final Object v = new Object();
        final Object v2 = new Object();

        final ILets withBinding = ILets.EMPTY
                .withBinding(k, v);

        final ILets withBinding2 = ILets.EMPTY
                .withBinding(k, v2);

        final ILets different = withBinding.withLets(withBinding2);

        Assert.assertSame(v2, different.get(k, Object.class).get());
    }

    @Test
    public void filteringOverridenLetsWorks() {
        final Object k = new Object();
        final Object v = new Object();
        final Object v2 = new Object();

        final ILets withBinding = ILets.EMPTY
                .withBinding(k, v);

        final ILets withBinding2 = ILets.EMPTY
                .withBinding(k, v2);

        final ILets different = withBinding.withLets(withBinding2);

        Assert.assertSame(ILets.EMPTY, different.assignableTo(String.class));
    }
}
