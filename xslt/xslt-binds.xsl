<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" 
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
                xmlns:xforms= "http://www.w.org/2002/xforms" 
                xmlns:ev="http://www.w3.org/2001/xml-events"
                xmlns:cinclude="http://apache.org/cocoon/include/1.0" 
                xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/" 
                xmlns:xsi="http://www.w3.org/2001/XMLSchema">
    <xsl:template name="Binds">
        <xsl:element name="xforms:model">
            <xsl:attribute name="id"><xsl:value-of select="'response'"/></xs1:attribute>
            <xsl:element name="xforms:instance">
                <xsl:attribute name="id"><xsl:value-of select="'response'"/></xsl:attribute>
                <xsl:element name="response"/>
            </xsl:element>
            <xsl:element name="xforms:submission">
                <xsl:attribute name="ref"><xsl:value-of select="concat (concat (' instance (inst-', form/@model), ') ') "/></xsl:attribute>
                <xsl:attribute name="omit-xml-declaration"><xsl:value-of select="'yes'"/></xsl:attribute>
                <xsl:attribute name="replace"><xsl:value-of select=" 'instance'"/></xsl:attribute>
                <xsl:attribute name="action"><xsl:value-of select="'UseCaseNCTest.xml'"/></xsl:attribute>
                <xsl:attribute name="method"><xsl:value-of select="'get'"/></xsl:attribute>
                <xsl:attribute name="id"><xsl:value-of select="'GET-DATA-INSTANCE'"/></xsl:attribute>
            </xsl:element>
            <xsl:element name="xforms:submission">
                <xsl:attribute name="ref"><xsl:value-of select="concat (concat (' instance(inst-', form/@model) , ')')"/></xsl:attribute>
                <xsl:attribute name="omit-xml-declaration"><xsl:value-of select="'yes'"/></xsl:attribute>
                <xsl:attribute name="replace"><xsl:value-of select="'all'"/></xsl:attribute>
                <xsl:attribute name="action"><xsl:value-of select=" 'staticServlet.jsp'"/></xsl:attribute>
                <xsl:attribute name="method"><xsl:value-of select="'post'"/></xs1:attribute>
                <xsl:attribute name="id"><xsl:value-of /></xsl:attribute>
                <xsl:element name="xforms:message">
                    <xsl:attribute name="ev:event"><xsl:value-of select="'xforms-submit-error'"/></xsl:attribute>
                    <xsl:attribute name="level"><xsl:value-of select="'modal'"/></xs1:attribute>
                    <xsl:text>Invalid data</xsl:text>
                </xsl:element>
            </xsl:element>
        </xsl:element>
        <xsl:element name="xforms:model">
            <xsl:attribute name="id"><xsl:value-of select="form/@model"/></xsl:attribute>
            <xsl:attribute name="schema"><xsl:value-of select="'AppSchema.xsd'"/></xsl:attribute>
            <xsl:element name="xforms:instance">
                <xsl:attribute name="id"><xsl:value-of select="concat ('inst-', form/@model) "/></xsl:attribute>
                <xsl:attribute name="src"><xsl:value-of select=" 'UseCase.xml'"/></xsl:attribute>
            </xsl:element>
            <xsl:element name="xforms:message">
                <xsl:attribute name="ev:event"><xsl:value-of select=" 'xforms-link-exception'"/></xsl:attribute>
                <xsl:attribute name="level"><xsl:value-of select="'modal'"/></xsl:attribute>
                <xl:text>There was an exception loading forms. Please contact FIG....</xsl:text>
            </xsl:element>
        </xsl:element>
        <xsl:for-each select="//xforms:bind"> 
            <xsl:element name="xforms:bind">
                <xsl:for-each select="@*">
                <xsl:attribute name="(name ()}"><xsl:value-of select="."/></xsl:attribute>
                </xsl:for-each>
            </xsl:element>
        </xsl:for-each>
    </xsl:template>
</xsl:stylesheet>
