package uk.org.cse.nhm.documentation;

import java.io.PrintWriter;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import org.apache.commons.lang3.StringEscapeUtils;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Sets;

/**
 * Helper to write docbook. This could of course use SAX to talk to a contenthandler,
 * but writing that stuff is so annoying it is unmanageable, in particular because we want
 * to allow structured text to be written in the doc strings themselves, so you'd need to hook
 * up a delegating handler parsing those and so on.
 * 
 */
public class DocbookWriter {
	private static final String DOCBOOK = "http://docbook.org/ns/docbook";
	
	final PrintWriter pw;
	
	final Stack<String> tags = new Stack<>();
	
	public DocbookWriter(final PrintWriter delegate) {
		super();
		this.pw = delegate;
	}
	
	private void println(final String format, final Object... objects) {
		if (objects.length == 0) {
			pw.println(format);
		} else {
			pw.println(String.format(format, objects));
		}
	}
	
	private void print(final String format, final Object... objects) {
		if (objects.length == 0) {
			pw.print(format);
		} else {
			pw.print(String.format(format, objects));
		}
	}

	private void tag(final String tag, Map<String, String> attribs, final boolean newline) {
		final StringBuffer b = new StringBuffer();
		
		if (tags.isEmpty()) {
			attribs = 
					ImmutableMap.<String, String>builder()
						.putAll(attribs)
						.put("xmlns", DOCBOOK)
					.build();
		}
		
		for (final String k : attribs.keySet()) {
			b.append(String.format(" %s=\"%s\"", k, attribs.get(k).replaceAll("\"", "\\\"")));
		}
		
		if (attribs.containsKey("id")) {
			final String key = attribs.get("id");
			if (definedKeys.contains(key)) {
				throw new RuntimeException("Doubly-defined key in docbook xml: " + key + " (defined again for " + tag + " " + attribs + ")");
			}
			definedKeys.add(key);
		}
		
		if (newline) {
			println("<%s%s>", tag, b);			
		} else {
			print("<%s%s>", tag, b);			
		}
		tags.push(tag);
	}
	
	private void gat() {
		println("</%s>", tags.pop());
		if (tags.isEmpty()) {
			final Set<String> missingKeys = Sets.difference(usedKeys, definedKeys);
			if (!missingKeys.isEmpty()) {
				throw new RuntimeException("Missing keys from docbook xml: " + missingKeys);
			}
		}
	}
	
	private void tag(final String string) {
		tag(string, ImmutableMap.<String, String>of(), true);
	}
	
	public void startDocument() {
		tag("book", ImmutableMap.of("xmlns", DOCBOOK), true);
	}
	
	public String esc(final String s) {
		if (s.startsWith("<![CDATA[")) return s;
		return String.format("<![CDATA[%s]]>", s);
	}
	
	private String escXML(final String s) {
		return StringEscapeUtils.escapeXml(s);
	}
	
	public void endDocument() {
		gat();
	}
	
	private void title(final String title) {
		tag("title");
		print(esc(title));
		gat();
	}
	
	final Set<String> definedKeys = new HashSet<>();
	final Set<String> usedKeys = new HashSet<>();
	
	public String createXrefToKey(final String key) {
		final String linkend = escXML(key);
		usedKeys.add(linkend);
		return String.format("<xref linkend=\"%s\" />", linkend);
	}
	
	public void startSection(final String title, final String key_, final Optional<String> xreflabel) {
		final String key = escXML(key_);
		
		if (xreflabel.isPresent()) {
			tag("section", ImmutableMap.of("id", key, "xreflabel", escXML(xreflabel.get())), true);
		} else {
			tag("section", ImmutableMap.of("id", key), true);
		}
		title(title);
	}
	
	public void startSection(final String title) {
		tag("section");
		title(title);
	}

	public void endSection() {
		gat();
	}

	public void writeParagraph(final String paragraph) {
		tag("para");
		println(paragraph);
		gat();
	}
	
	public void writeParagraph(final String tag, final String paragraph) {
		tag(tag);
		writeParagraph(paragraph);
		gat();
	}

	public void writeFormalParagraph(final String string, final String string2) {
		startFormalParagraph(string);
		writeParagraph(string2);
		endFormalParagraph();
	}

	public void writeMacroExample(final String title, final String description, final String input, final String output) {
		tag("example");
		title(title);
		writeParagraph(description);
		tag("programlisting", ImmutableMap.of("language", "lisp"), false);
		print(esc(input.trim()));
		gat();
		
		writeParagraph("is equivalent to writing");
		
		tag("programlisting", ImmutableMap.of("language", "lisp"), false);
		print(esc(output.trim()));
		gat();
		
		gat();
	}
	
	public void startProgramListing(final String language) {
		tag("programlisting", ImmutableMap.of("language", language), false);
	}
	
	public void endProgramListing() {
		gat();
	}

	public void writeRaw(final String string) {
		print(esc(string.trim()));
	}
	
	public void writeProgramListing(final String language, final String body) {
		startProgramListing(language);
		writeRaw(body);
		endProgramListing();
	}
	
	public void writeExample(final String title, final String description, final String body, final List<String> additionalParts) {
		tag("example");
		title(title);
		writeParagraph(description);
		writeProgramListing("lisp", body);
		
		for(final String extra : additionalParts) {
			print(extra);
		}
		
		gat();
	}
	
	public void startInformalTable(final String... header) {
		tag("informaltable");
		tag("tgroup", ImmutableMap.of("cols", ""+header.length), true);
		tableHead(header);
		tag("tbody");
	}
	
	public void endInformalTable() {
		gat();
		gat();
		gat();
	}
	
	public void tableRow(final String... string) {
		tag("row");
		for (final String s : string) {
			tag("entry");
			print(s);
			gat();
		}
		gat();
	}
	
	public void tableHead(final String... string) {
		tag("thead");
		tableRow(string);
		gat();
	}

	public void startChapter(final String string) {
		tag("chapter");
		title(string);
	}
	
	public void startChapter(final String string, final String id) {
		tag("chapter", ImmutableMap.of("id", id), true);
		title(string);
	}
	
	public void endChapter() {
		gat();
	}

	public void startVariableList(final String string) {
		tag("variablelist");
		title(string);
	}
	
	public void startVarListEntry(final String term) {
		tag("varlistentry");
		tag("term");
		print(term);
		gat();//term
		tag("listitem");
	}
	
	public void endVarListEntry() {
		gat();//listitem
		gat(); //varlistentry
	}

	public void writeVarListEntry(final String name, final String or) {
		startVarListEntry(name);
		
		writeParagraph(or);
		
		endVarListEntry();
	}

	public void endVariableList() {
		gat();
	}

	public void startFormalParagraph(final String string) {
		tag("formalpara");
		title(string);
	}

	public void endFormalParagraph() {
		gat(); //formal paragraph
	}
	
	public void startSimpleList() {
		tag("simplelist", ImmutableMap.of("type", "inline"), true);
	}
	
	public void writeSimpleListMember(final String content) {
		tag("member");
		print(content);
		gat();
	}
	
	public void endSimpleList() {
		gat();
	}

	public void startItemizedList() {
		tag("itemizedlist");
	}
	
	public void startListItem() {
		tag("listitem");
	}
	
	public void endListItem() {
		gat();
	}

	public void endItemizedList() {
		gat();
	}

	public void startPart(final String string) {
		tag("part");
		title(string);
	}
	
	public void endPart() {
		gat();
        }

        public void addIndexTerm(String term) {
            tag("indexterm");
            tag("primary");
            writeRaw(term);
            gat();
            gat();
        }
}
