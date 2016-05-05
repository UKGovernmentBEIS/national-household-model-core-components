package uk.org.cse.nhm.hom.util;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.util.EcoreUtil;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class EObjectSerializer {
	/**
	 * Every {@link EObject} is turned into a bit of BSON which contains the attribute ___eclass___; this
	 * tells the {@link Reader} what class of EObject to turn a thing into when deserializing
	 */
	private static final String ECLASS_KEY = "___eclass___";
	/**
	 * Since {@link EClass} instances are defined within an {@link EPackage}, we need also to know which
	 * {@link EPackage} we're looking in. Since {@link EPackage}s have an XMI schema with a globally unique
	 * namespace URI, that is the best way to name a package.
	 * 
	 * If an EObject is mapped which contains another EObject from the same package, the namespace key will only
	 * be written for the container and not the containee, to avoid wasting space.
	 * 
	 * This string is used to denote the namespace int the bson.
	 */
	private static final String NAMESPACE_KEY = "___ns___";
	/**
	 * For the purposes of handling cross-references, every converted EObject is given a unique ID within the 
	 * serialization that is taking place. This is then used to restore the cross-references when reading.
	 * 
	 * This string is the key for that property in the bson.
	 */
	private static final String OBJECT_ID_KEY = "___id___";
	
	public static class Serializer<T extends EObject> extends StdSerializer<T> {
		public Serializer(final Class<T> clazz) {
			super(clazz);
		}

		/**
		 * A helper class used to assign unique IDs to EObjects during the write process, so as to allow
		 * cross-references to work
		 * 
		 * @author hinton
		 *
		 */
		private static class Counter {
			private int counter = 0;
			private final IdentityHashMap<Object, Integer> map = new IdentityHashMap<Object, Integer>();
			
			/**
			 * @param object
			 * @return a unique int for object, which will always be the same across subsequent calls.
			 */
			public int get(final Object object) {
				if (map.containsKey(object)) return map.get(object);
				final int k = counter++;
				map.put(object, k);
				return k;
			}
		}
		
		@Override
		public void serialize(final EObject in, final JsonGenerator out, final SerializerProvider sp)
				throws IOException, JsonProcessingException {
			serialize(in, out, sp, new Counter());
			
		}

		private void serialize(final EObject in, final JsonGenerator out, final SerializerProvider sp, final Counter counter) throws JsonGenerationException, IOException {
			out.writeStartObject();
			
			out.writeNumberField(OBJECT_ID_KEY, counter.get(in));
			
			if (in.eContainer() == null || 
					in.eContainer().eClass().getEPackage() != in.eClass().getEPackage()) {
				out.writeStringField(NAMESPACE_KEY, in.eClass().getEPackage().getNsURI());
			}
			
			out.writeStringField(ECLASS_KEY, in.eClass().getName());

			addAttributes(in, out, sp);
			
			addContainedObjects(in, out, sp, counter);
			
			addCrossReferences(in, out, sp, counter);
			
			out.writeEndObject();
		}

		@SuppressWarnings("unchecked")
		private void addCrossReferences(final EObject in, final JsonGenerator out, final SerializerProvider sp, final Counter counter) throws JsonGenerationException, IOException {
			for (final EReference reference : in.eClass().getEAllReferences()) {
				if (reference.isContainment()) continue;
				if (reference.isContainer()) continue;
				if (reference.isUnsettable() && !in.eIsSet(reference)) continue;
				
				if (reference.isMany()) {
					out.writeArrayFieldStart(reference.getName());
					
					for (final EObject o : ((List<EObject>) in.eGet(reference))) {
						out.writeNumber(counter.get(o));
					}
					
					out.writeEndArray();
				} else {
					final Object o = in.eGet(reference);
					if (o != null) out.writeNumberField(reference.getName(), counter.get(o));
				}
			}
		}

		@SuppressWarnings("unchecked")
		private void addContainedObjects(final EObject in, final JsonGenerator out,
				final SerializerProvider sp, final Counter counter) 
						throws JsonGenerationException, IOException {
			for (final EReference reference : in.eClass().getEAllContainments()) {
				if (reference.isUnsettable() && !in.eIsSet(reference)) continue;
				if (reference.isMany()) {
					out.writeArrayFieldStart(reference.getName());
					
					for (final EObject o : ((List<EObject>) in.eGet(reference))) {
						serialize(o, out, sp, counter);
					}
					
					out.writeEndArray();
				} else {
					final EObject o = (EObject) in.eGet(reference);
					if (o != null) {
						out.writeFieldName(reference.getName());
						serialize(o, out, sp, counter);
					}
				}
			}
		}

		@SuppressWarnings("unchecked")
		private void addAttributes(final EObject in, final JsonGenerator out, final SerializerProvider sp) throws JsonProcessingException, IOException {
			for (final EAttribute attribute : in.eClass().getEAllAttributes()) {
				final EDataType dataType = (EDataType) attribute.getEType();
				if (attribute.isUnsettable() && !in.eIsSet(attribute)) continue;
				
				if (attribute.isMany()) {
					out.writeArrayFieldStart(attribute.getName());
					
					for (final Object o : (List<Object>) in.eGet(attribute)) {
						out.writeString(EcoreUtil.convertToString(dataType, o));
					}
					
					out.writeEndArray();
				} else {
					out.writeStringField(attribute.getName(), EcoreUtil.convertToString(dataType, in.eGet(attribute)));
				}
			}
		}
	}
	
	@SuppressWarnings("serial")
	public static class Deserializer<T extends EObject> extends StdDeserializer<T> {
		public Deserializer(final Class<T> clazz, final List<EPackage> of) {
			super(clazz);
			for (final EPackage p : of) {
				packageByNamespace.put(p.getNsURI(), p);
			}
		}

		/**
		 * Maps from packages' namespace URIs to the instances
		 */
		private final Map<String, EPackage> packageByNamespace = new HashMap<String, EPackage>();
		
		
		private static class XRef {
			/**
			 * The index of the referent; that is the value to which its {@link EObjectConverter#OBJECT_ID_KEY} 
			 * property is mapped in the BSON
			 */
			public final int index;
			/**
			 * The thing which owns the reference that needs patching up
			 */
			public final EObject target;
			/**
			 * The reference that is going to be patched up
			 */
			public final EReference reference;
			
			public XRef(final int index, final EObject target, final EReference reference) {
				this.index = index;
				this.target = target;
				this.reference = reference;
			}
			
			/**
			 * Using the index, restore {@link #reference} on {@link #target} to point at
			 * theindex.get({@link #index})
			 * 
			 * @param theindex the mapping from int to referent, built during the deserialization process
			 */
			@SuppressWarnings("unchecked")
			public void resolve(final Map<Integer, Object> theindex) {
				final Object referent = theindex.get(this.index);
				if (referent != null) {
					if (reference.isMany()) {
						((List<Object>) target.eGet(reference)).add(referent);
					} else {
						target.eSet(reference, referent);
					}
				}
			}
		}
		
		@SuppressWarnings("unchecked")
		@Override
		public T deserialize(final JsonParser in, final DeserializationContext context)
				throws IOException, JsonProcessingException {
			final ObjectCodec oc = in.getCodec();
			final JsonNode node = oc.readTree(in);
			
			// create the index
			final HashMap<Integer, Object> index = new HashMap<Integer, Object>();
			// create a list to hold xrefs that need fixing up
			final ArrayList<XRef> xrefs = new ArrayList<XRef>();
			
			final EObject top = deserialize(node, "", index, xrefs);
			
			for (final XRef xref : xrefs) {
				xref.resolve(index);
			}
			
			return (T) top;
		}

		private EObject deserialize(final JsonNode node, String namespace,
				final HashMap<Integer, Object> index, final ArrayList<XRef> xrefs) {
			if (node.has(NAMESPACE_KEY)) {
				namespace = node.get(NAMESPACE_KEY).asText();
			}
			
			final String eClassName = node.get(ECLASS_KEY).asText();
			
			/**
			 * The EPackage with namespace URI given by namespaceURI
			 */
			final EPackage p = packageByNamespace.get(namespace);
			
			if (p == null) throw new RuntimeException("Cannot deserialize EObject from package " + namespace + " - namespace not registered");
			
			/**
			 * This is the classifier with name eClassName, if there is one in the package
			 */
			final EClassifier classifier = p.getEClassifier(eClassName);
			
			if (!(classifier instanceof EClass)) throw new RuntimeException("Cannot find EClass with name " + eClassName + " in " + p);
			
			final EClass ec = (EClass) classifier;
			
			/**
			 * The newly-created instance which we are going to populate
			 */
			final EObject obj = p.getEFactoryInstance().create(ec);
			
			/*
			 * Put the object into the index, so we can do xrefs to it
			 */
			index.put(node.get(OBJECT_ID_KEY).asInt(), obj);
			
			populateAttributes(node, obj);
			
			populateContainedObjects(node, obj, namespace, index, xrefs);
			
			prepareXRefs(node, obj, xrefs);
			
			return obj;
		}

		private void prepareXRefs(final JsonNode node, final EObject obj, final ArrayList<XRef> xrefs) {
			for (final EReference reference : obj.eClass().getEAllReferences()) {
				if (reference.isContainment()) continue;
				if (reference.isContainer()) continue;
				
				if (node.has(reference.getName()) == false) continue;
				
				if (reference.isMany()) {
					final Iterator<JsonNode> iterator = node.get(reference.getName()).elements();
					
					while (iterator.hasNext()) {
						xrefs.add(new XRef(iterator.next().asInt(), obj, reference));
					}
				} else {
					xrefs.add(new XRef(node.get(reference.getName()).asInt(), 
							obj, reference));
				}
			}
		}

		@SuppressWarnings("unchecked")
		private void populateContainedObjects(final JsonNode node, final EObject obj,
				final String namespace, final HashMap<Integer, Object> index,
				final ArrayList<XRef> xrefs) {
			for (final EReference ref : obj.eClass().getEAllContainments()) {
				if (node.has(ref.getName())) {
					if (ref.isMany()) {
						final List<Object> l = (List<Object>) obj.eGet(ref);
						
						final Iterator<JsonNode> iterator = node.get(ref.getName()).elements();
						
						while (iterator.hasNext()) {
							l.add(deserialize(iterator.next(), namespace, index, xrefs));
						}
					} else {
						obj.eSet(ref, deserialize(node.get(ref.getName()), namespace, index, xrefs));
					}
				}
			}
		}

		private Object decodeAttribute(final EAttribute attr, final String s) {
			if (attr.getEAttributeType() instanceof EEnum) {
				try {
					final EEnum ee = (EEnum) attr.getEAttributeType();
					return ee.getEEnumLiteral(s).getInstance();
				} catch (final NullPointerException npe) {
					return EcoreUtil.createFromString(attr.getEAttributeType(), s);
				}
			} else {
				return EcoreUtil.createFromString(attr.getEAttributeType(), s);
			}
		}
		
		@SuppressWarnings("unchecked")
		private void populateAttributes(final JsonNode node, final EObject obj) {
			for (final EAttribute attr : obj.eClass().getEAllAttributes()) {
				if (node.has(attr.getName())) {
					if (attr.isMany()) {
						final Iterator<JsonNode> iterator = node.get(attr.getName()).elements();
						final List<Object> l = (List<Object>) obj.eGet(attr);
						
						while (iterator.hasNext()) {
							l.add(decodeAttribute(attr,
											iterator.next().asText()));
						}
					} else {
						obj.eSet(attr,decodeAttribute(attr, node.get(attr.getName()).asText()));
					}
				}
			}
		}
		
	}
}
