package uk.org.cse.nhm.language.validate;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javax.validation.ConstraintValidatorContext;
import javax.validation.ConstraintValidatorContext.ConstraintViolationBuilder;

import org.junit.Assert;
import org.junit.Test;

public class BoundedDoubleValidatorTest {
	@Test
	public void testBoundedDoubleValidatorPass() {
		final BoundedDoubleValidator bdv = new BoundedDoubleValidator();
		bdv.setBounds(0, 1);
		
		ConstraintValidatorContext context = mock(ConstraintValidatorContext.class);
		Assert.assertTrue("Lower bound should be included", bdv.isValid(0d, context));
		Assert.assertTrue("Middle should be included", bdv.isValid(0.4d, context));
		Assert.assertTrue("Upper bound should be included", bdv.isValid(1d, context));
	}
	
	@Test
	public void testBoundedDoubleValidatorFailBelowAndAbove() {
		final BoundedDoubleValidator bdv = new BoundedDoubleValidator();
		bdv.setBounds(0, 1);
		
		ConstraintValidatorContext context = mock(ConstraintValidatorContext.class);
		when(context.buildConstraintViolationWithTemplate(any(String.class))).thenReturn(mock(ConstraintViolationBuilder.class));
		Assert.assertFalse("Below lower bound should be excluded", bdv.isValid(0d - Double.MIN_VALUE, context));
		Assert.assertFalse("Above upper bound should be excluded", bdv.isValid(1.1d, context));
		Assert.assertFalse("null should be excluded", bdv.isValid(null, context));
	}
}
