package uk.org.cse.nhm.language.definition;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.namespace.NamespaceContext;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamWriter;

import uk.org.cse.nhm.language.visit.IVisitor;

import com.google.common.base.Joiner;

public class BaseExamples implements Iterable<IExample> {
	private final List<IExample> contents = new ArrayList<IExample>();
	
	protected void add(final String title, final String description, final XElement object) {
		add(title, new String[] {description}, object);
	}
	
	protected void add(final String title, final String[] description, final XElement object) {
		try {
			final Set<Class<?>> classes = new HashSet<Class<?>>();
			object.accept(new IVisitor<XElement>() {
				
				@Override
				public void visit(XElement v) {
					classes.add(v.getClass());
				}
				
				@Override
				public void leave(XElement v) {
				}
				
				@Override
				public boolean enter(XElement v) {
					return true;
				}
			});
			
			final JAXBContext context = JAXBContext.newInstance(classes.toArray(new Class<?>[classes.size()]));
			
			final Marshaller m = context.createMarshaller();
			final StringWriter sw = new StringWriter();
			
			final XMLStreamWriter xsw = XMLOutputFactory.newInstance().createXMLStreamWriter(sw);

			// strip off namespaces for examples
			
			xsw.setNamespaceContext(new NamespaceContext() {
				@SuppressWarnings("rawtypes")
				@Override
				public Iterator getPrefixes(String arg0) {
					return null;
				}
				
				@Override
				public String getPrefix(String arg0) {
					return "";
				}
				
				@Override
				public String getNamespaceURI(String arg0) {
					return null;
				}
			});
			
			m.marshal(object, xsw);
			
			final String s = sw.toString();
			final String d = Joiner.on('\n').join(description);
			
			contents.add(new IExample() {
				
				@Override
				public String getTitle() {
					return title;
				}
				
				@Override
				public String getExample() {
					return s;
				}
				
				@Override
				public String getDescription() {
					return d;
				}
			});
			
		} catch (Exception e) {
			throw new RuntimeException("Problem creating example " + title, e);
		}
		
	}
	
	
	@Override
	public Iterator<IExample> iterator() {
		return contents.iterator();
	}
	
}
