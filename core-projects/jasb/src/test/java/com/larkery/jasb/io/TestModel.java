package com.larkery.jasb.io;

import org.junit.Assert;
import org.junit.Test;

public class TestModel extends JasbIOTest {
	@Test
	public void modelIsConstucted() {
		Assert.assertNotNull(context.getModel());
	}
}
