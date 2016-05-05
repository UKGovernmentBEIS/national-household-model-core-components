package uk.org.cse.nhm.ipc.api;

import java.net.URI;
import java.net.URISyntaxException;

import org.junit.Assert;
import org.junit.Test;

import com.larkery.jasb.sexp.Delim;
import com.larkery.jasb.sexp.Seq;

public class TestIncludeAddress {
	@Test
	public void parsesIdURI() throws URISyntaxException {
		final IncludeAddress address = IncludeAddress.parse(new URI("nhm", null, "/id/asdf", null));
		Assert.assertTrue(address.isID());
		Assert.assertFalse(address.isNamedVersion());
		
		Assert.assertEquals(URI.create("nhm:/id/asdf"), address.toURI());
		Assert.assertEquals(Seq.builder(null, Delim.Paren).add("include").add("id", "asdf").build(null), address.toSeq());
		Assert.assertEquals("asdf", address.getId());
	}
	
	@Test
	public void parsesNameVersionURI() throws URISyntaxException {
		final IncludeAddress address = IncludeAddress.parse(new URI("nhm", null, "/name/my lovely scenario", "3"));
		Assert.assertFalse(address.isID());
		Assert.assertTrue(address.isNamedVersion());
		
		Assert.assertEquals(URI.create("nhm:/name/my%20lovely%20scenario#3"), address.toURI());
		Assert.assertEquals(Seq.builder(null, Delim.Paren).add("include").add("name", "my lovely scenario").add("version", "3").build(null), address.toSeq());
		Assert.assertEquals("my lovely scenario", address.getName());
		Assert.assertEquals(3, address.getVersion());
	}
	
	@Test
	public void parsesIdSeq() {
		final IncludeAddress address = IncludeAddress.parse(
				Seq.builder(null, Delim.Paren).add("include").add("id", "foo").build(null));
		
		Assert.assertTrue("Should be an ID", address.isID());
		Assert.assertEquals("should have ID foo", "foo", address.getId());
	}
	
	@Test
	public void parsesNameVersionSeq() {
		final IncludeAddress address = IncludeAddress.parse(
				Seq.builder(null, Delim.Paren).add("include").add("name", "graham").add("version", "9").build(null));
		
		Assert.assertTrue(address.isNamedVersion());
		Assert.assertEquals("graham", address.getName());
		Assert.assertEquals(9, address.getVersion());
	}
	
	@Test
	public void parsesNameVersionSeqWithoutKey() {
		final IncludeAddress address = IncludeAddress.parse(
				Seq.builder(null, Delim.Paren).add("include").add("graham").add("version", "9").build(null));
		
		Assert.assertTrue(address.isNamedVersion());
		Assert.assertEquals("graham", address.getName());
		Assert.assertEquals(9, address.getVersion());
    }

    @Test
    public void parsesNameVersionSeqWithoutKeyOtherWay() {
        final IncludeAddress address = IncludeAddress.parse(
            Seq.builder(null, Delim.Paren).add("include").add("name", "graham").add("9").build(null));

        Assert.assertTrue(address.isNamedVersion());
        Assert.assertEquals("graham", address.getName());
        Assert.assertEquals(9, address.getVersion());
    }
	
	@Test
	public void parsesNameVersionSeqWithoutEitherKey() {
		final IncludeAddress address = IncludeAddress.parse(
				Seq.builder(null, Delim.Paren).add("include").add("graham").add("9").build(null));
		
		Assert.assertTrue(address.isNamedVersion());
		Assert.assertEquals("graham", address.getName());
		Assert.assertEquals(9, address.getVersion());
	}
}
