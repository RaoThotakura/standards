<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" 
            xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
            xmlns:xforms="http://www.w3.org/2002/xforms" 
            xmlns:ev="http://www.w3.org/2001/xml-events"
            xmlns:cinclude="http://apache.org/cocoon/include/1.0" 
            xmlns:SOAP-ENV="https://schemas.xmlsoap.org/soap/envelope/" 
            xmlns:xsi="http://www.w3.org/2001/XMLSchema">
    <xsl:output method="xml" version="1.0" encoding="UTF-8" indent="yes" omit-xml-declaration="no"/>
    <xsl:include href="xslt-binds.xsl"/>
    <xsl:include href="xslt-script.xsl"/>
    <xsl:include href="xslt-widgets.xsl"/>
    <xsl:template match="/">
        <xsl:element name="html">
            <xsl:element name="object">
                <xsl:attribute name="id"><xsl:value-of select="'FormsPlayer'"/></xs1:attribute>
                <xsl:attribute name="classid"><xsl:value-of select="'CLSID: 4D0ABA11-C5F0-4478-991A-375C4B648F58'"/></xsl:attribute>
                <xsl:attribute name="width"><xsl:value-of select="'0'"/></xsl:attribute>
                <xsl:attribute name="height"><xsl:value-of select="'0'"/></xsl:attribute>
                <xsl:text>FormsPlayer has failed to load! Please check your installation.</xsl:text>
                <!--object tag should have open and close elements instead of justk
                empty tag in order for FormPlayer to recognize it -->
            </xsl:element>
            <xsl:processing-instruction name="import">namespace="xforms" implementation="#FormsPlayer"</xsl:processing-instruction>
            <xsl:element name="xforms: xforms">
                <!-- XForms Data Model Instance -->
                <xsl:call-template name="Binds"/>
                <!-- XForms User Interface Model -->
                <xsl:apply-templates select="//xforms:group[@type='tabulation']"/>
                <link rel="stylesheet" href="assets/default/styles/master.css" type="text/css"/>
                <p/><p/><b><font color="blue">CONTAINER WIDGET</ font></b><p/><p/>
                <table width="70%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                        <xsl:variable name="noTabulations" select="count (//xforms: group [@type='container' and @label != ''])"/>
                        <xsl:variable name="xPathTabulation" select="//xforms: group [@type='container' and (label != 11]"/>
                        <!-- XPath pattern to get all containers with a label (individual taby in a tabulation) from any input layout XMI Doc--> 
                        <xsl:for-each select="$xPathTabulation">
                            <xsl:call-template name="Tabulation">
                                <xsl:with-param name="tabName" select="@label"/>
                            </xsl:call-template>
                        </xsl:for-each>
                        <br/><br/>
                        <table cellspacing="0" cellpadding="0" width="100%" align="center" border="0" borderstyle="solid" bordercolor="black">
                            <xsl:element name="xforms:switch">
                                <xsl:attribute name="id"><xsl:value-of select="'mainTabs'"/></xsl:attribute>
                                <xsl:call-template name="Container">
                                    <xsl:with-param name="containers" select="//xforms:group [@type='container' and label != '']"/>
                                </xsl:call-template>
                            </xsl:element>
                        </table>
                    </tr>
                </table>
            </xsl:element>
        </xsl:element>
    </xsl:template>
</xsl:stylesheet>