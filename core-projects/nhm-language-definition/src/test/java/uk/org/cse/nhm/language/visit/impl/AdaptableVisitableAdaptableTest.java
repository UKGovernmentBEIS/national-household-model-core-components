package uk.org.cse.nhm.language.visit.impl;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Collections;

import org.junit.Assert;
import org.junit.Test;

import com.google.common.base.Optional;

import uk.org.cse.nhm.language.adapt.IAdapter;
import uk.org.cse.nhm.language.adapt.IAdaptingScope;

public class AdaptableVisitableAdaptableTest {

    public static class AdaptableVisitableImpl extends AdaptableVisitable<AdaptableVisitableImpl> {

    }

    @Test
    public void isAdaptableToObject() {
        final AdaptableVisitableImpl a = new AdaptableVisitableImpl();
        // everything is an object, after all
        Assert.assertTrue(a.adapts(Object.class));

    }

    @Test
    public void testNotAdaptableToStartWith() {
        final AdaptableVisitableImpl a = new AdaptableVisitableImpl();

        Assert.assertFalse(a.adapts(Whatsit.class));
    }

    static class Whatsit {

    }

    @Test
    @SuppressWarnings("unchecked") // Can't put generic types on mocks.
    public void testAdaptableWithAdapter() {
        final AdaptableVisitableImpl a = new AdaptableVisitableImpl();

        final IAdapter adapter = mock(IAdapter.class);
        when(adapter.adapts(a)).thenReturn(true);
        when(adapter.adapts(eq(a), eq(String.class))).thenReturn(true);

        final IAdaptingScope scope = mock(IAdaptingScope.class);
        when(adapter.adapt(a, String.class, scope)).thenReturn("Hello, world");

        when(scope.getFromCache(any(Object.class))).thenReturn(Collections.emptyList());

        when(scope.getFromCache(any(Object.class), any(Class.class))).thenReturn(Optional.absent());

        a.addAdapter(adapter);

        Assert.assertFalse("a should remain unadaptable to object", a.adapts(Whatsit.class));
        Assert.assertTrue("a should now be adaptable to string", a.adapts(String.class));

        final String s = a.adapt(String.class, scope);
        Assert.assertEquals("We should get the string we are expecting out", "Hello, world", s);
    }
}
