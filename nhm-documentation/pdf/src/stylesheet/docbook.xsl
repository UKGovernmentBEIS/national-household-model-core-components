<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:html="http://www.w3.org/1999/xhtml"
                xmlns:fo="http://www.w3.org/1999/XSL/Format"
                xmlns:d="http://docbook.org/ns/docbook">
  
  <xsl:import href="urn:docbkx:stylesheet"/>
  <xsl:import href="urn:docbkx:stylesheet/highlight.xsl"/> 
  <xsl:import href="ourhighlight.xsl" />

  <!-- Make hyperlinks blue but still display the underlying URL -->
  <xsl:attribute-set name="xref.properties">
    <xsl:attribute name="color">blue</xsl:attribute>
  </xsl:attribute-set>

    <!-- Verbatim text formatting (programlistings) -->
    <xsl:attribute-set name="verbatim.properties">
        <xsl:attribute name="space-before.minimum">1em</xsl:attribute>
        <xsl:attribute name="space-before.optimum">1em</xsl:attribute>
        <xsl:attribute name="space-before.maximum">1em</xsl:attribute>
        <xsl:attribute name="border-color">#444444</xsl:attribute>
        <xsl:attribute name="border-style">solid</xsl:attribute>
        <xsl:attribute name="border-width">0.1pt</xsl:attribute>
        <xsl:attribute name="padding-top">0.5em</xsl:attribute>
        <xsl:attribute name="padding-left">0.5em</xsl:attribute>
        <xsl:attribute name="padding-right">0.5em</xsl:attribute>
        <xsl:attribute name="padding-bottom">0.5em</xsl:attribute>
        <xsl:attribute name="margin-left">0.5em</xsl:attribute>
        <xsl:attribute name="margin-right">0.5em</xsl:attribute>
    </xsl:attribute-set>

    <!-- Shade (background) programlistings -->
    <xsl:attribute-set name="shade.verbatim.style">
        <xsl:attribute name="background-color">#F0F0F0</xsl:attribute>
    </xsl:attribute-set>

	<xsl:attribute-set name="monospace.verbatim.properties">
	  <xsl:attribute name="wrap-option">wrap</xsl:attribute>
	  <xsl:attribute name="hyphenation-character">\</xsl:attribute>
	</xsl:attribute-set>

    <xsl:attribute-set name="monospace.properties">
        <xsl:attribute name="font-family">
            <xsl:value-of select="$monospace.font.family"/>
        </xsl:attribute>
        <xsl:attribute name="font-size">0.75em</xsl:attribute>
    </xsl:attribute-set>

	<xsl:attribute-set name="section.title.level2.properties">
	  <xsl:attribute name="font-weight">normal</xsl:attribute>
	  <xsl:attribute name="font-style">italic</xsl:attribute>
	</xsl:attribute-set>

	<xsl:param name="variablelist.as.blocks">1</xsl:param>

	<xsl:param name="ulink.show" select="0"></xsl:param>

	<xsl:template match="d:varlistentry" mode="vl.as.blocks">
	  <xsl:variable name="id"><xsl:call-template name="object.id"/></xsl:variable>
	  
	  <fo:block id="{$id}" xsl:use-attribute-sets="list.item.spacing"
				keep-together.within-column="always"
				keep-with-next.within-column="always">
		<fo:inline font-weight="bold">
          <xsl:apply-templates select="d:term"/>
		</fo:inline>
	  </fo:block>
	  
	  <fo:block margin-left="0.25in">
		<xsl:apply-templates select="d:listitem"/>
	  </fo:block>
	</xsl:template>

</xsl:stylesheet>
