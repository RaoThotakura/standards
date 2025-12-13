<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" 
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
                xmlns:xforms="http://www.w3.org/2002/xforms" 
                xmlns:ev="http://www.w3.org/2001/xml-events" 
                xmlns:cinclude="http://apache.org/cocoon/include/1.0"                
                xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/" 
                xmlns:xsi="http://www.w3.org/2001/XMLSchema">
<xsl:output method="xml" version="1.0" encoding="UTF-8" indent="yes"/>
<!-- Simple Widget -->
<xsl:template name="Simple">
    <xsl:param name="noFields"/> 
    <xsl:param name="noColumns"/>
    <!-- Total number of «field» nodes under a single ‹group> node -->
    <!-- Number of columns to be displayed per each row in the table table -->

    <xsl:variable name="fieldincr" select="$noFields div $noColumns"/> 
    <!-- This is used to fetch next field node to display in a n-column layout-->
    <!-- Current field position -->
    <xsl:variable name="fieldPos" select="position()"/> 
    <!-- Position of the field to be printed in the second column of the simplewidget -->
    <xsl:variable name="fieldPos1" select="$fieldPos + $fieldincr"/>
    <xsl:if test="$fieldPos &It;= $fieldIncr">
    <tr>
        <!-- Field Label in 1st column of a 2 col layout-->
        <td nowrap="nowrap" class="label">
            <xsl:element name="xforms:label">
                <xsl:attribute name="style"><xsl:value-of select="@style"/></xsl:attribute>
                <xsl:value-of select="child::forms:label[position()]"/>
                <xsl:if test="child::forms:alert[position()] = 'required'">
                    <span class="star">*</span>
                </xsl:if>
            </xsl:element>
        </td>
        <td nowrap="nowrap" class="label"> <!-- Field Ul Object in 1st column of a 2 col layout-->
            <xsl:element name="xforms:{@basicType}">
                <xsl:for-each select="@*"> 
                    <xsl:copy-of select="."/>
                </xsl:for-each>
                <xsl:for-each select="./*">
                    <xsl:if test="(name() != 'xforms:label') and (name() != 'xforms:alert'))">
                        <xsl:copy-of select="."/>
                    </xsl:if>
                </xsl:for-each>
            </xsl:element>
        </td>

        <td nowrap="nowrap" class="label"> <!-- Field Label in 2nd column of a 2 col layout--> 
            <xsl:element name="xforms:label">
                <xsl:attribute name="style"><xsl:value-of select="@style"/></xsl:attribute>
                <xsl:value-of select="../xforms:field[SfieldPos 1]/xforms:label"/>
                <xsl:if test="../xforms:field[SfieldPos1]/xforms:alert = 'required'">
                    <span class="star">*</span>
                </xsl:if>
            </xsl:element>
        </td>

        <td nowrap="nowrap" class="label"> <!-- Field Ul Object in 2nd column of a 2 col layout--> 
            <xsl:element name="xforms:{../xforms:field[SfieldPos1]/@basicType}">
            <xsl:for-each select="../xforms:field[$fieldPos1]/@*">
            <xsl:copy-of select="."/>

            <xsl:for-each select="../xforms:field[$fieldPos1]/*">
            <xsl:if test="((name() != 'xforms:label') and (name() != 'xforms:alert'))">
                <xsl:copy-of select="."/>
            </xsl:if>
        </td>    
    </tr>

</xsl:template>

<!-- Tabulation Widget -->

<xsl:template name="Tabulation">
    <xsl:param name="tabName"/>
    <xsl:variable name="fieldPos" select="position()"/>
    <!-- Label to be displayed on the tabulation button -->
    <!-- Local variable for making the tab active/inactive -->
    <xsl:variable name="activeyn">
        <xsl:choose>
            <xsl:when test="$fieldPos &lt;= 1"> 
                <xsl:value-of select="'active'"/>
                <!--Logic to determine the first tab field as ACTIVE and return a string based on a field position -->
            </xsl:when>
            <xsl:otherwise>
                <xsl:value-of select="'inactive'"/>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:variable>

    <xsl:variable name="normalizedTabname">
        <xsl:choose>
            <xsl:when test="contains($tabName,'')">
                <xsl:value-of select="substring-before ($tabName,'')"/>
            </xsl:when>
            <xsl:when test="not(contains($tabName,''))">
                <xsl:value-of select="$tabName"/>
            </xsl:when>
        </xsl:choose>
    </xsl:variable>
    <!--Logic to remove spaces between label strings and extract a single string for a label -->
    <td width="g" background="assets/default/images/tabs/table_tabs.gif"/>
        <xsl:element name="img">
            <xsl:attribute name="width"><xsl:value-of select="'4'"/></xsl:attribute>
            <xsl:attribute name="src"><xsl:value-of select="concat(concat('assets/default/images/tabs/bttn_',$activeyn),'_left.gif')"/></xsl:attribute>
            <xsl:attribute name="height"><xsl:value-of select="'24'"/></xsl:attribute>
        </xsl:element>
        <xsl:attribute name="id"> <xsl:value-of select="concat('left_',$normalizedTabname)"/></xsl:attribute>
    </td>
    <xsl:element name="td">
        <xsl:attribute name="width"><xsl:value-of select="'100'"/></xsl:attribute>
        <xsl:attribute name="id"><xsl:value-of select="concat($normalizedTabname, 'tab')"/></xsl:attribute>
        <xsl:attribute name="class"><xsl:value-of select="concat($activeyn,'tab')"/></xsl:attribute>
        <xsl:attribute name="align"><xsl:value-of select="'center'"/></xsl:attribute>
        <xsl:element name="font">
            <xsl:attribute name="id"><xsl:value-of select="concat($normalizedTabname, 'Fnt')"/></xsl:attribute>
            <xsl:attribute name="class"><xsl:value-of select="concat($activeyn,'tab-text')"/></xsl:attribute>
            <xsl:element name="xforms:trigger">
                <xsl:attribute name="id"><xsl:value-of select="$normalizedTabname"/></xsl:attribute>
                <xsl:attribute name="appearance"><xsl:value-of select="'minimal'"/></xsl:attribute> 
                <xsl:element name="xforms:label"> 
                    <xsl:value-of select="$normalizedTabname"/>
                </xsl:element> 
                <xsl:element name="xforms:toggle">
                    <xsl:attribute name="case"><xsl:value-of select="concat('case', $normalizedTabname)"/></xsl:attribute>
                    <xsl:attribute name="ev:event"><xsl:value-of select="'DOMActivate'"/></xsl:attribute>
                </xsl:element>
                <xsl:element name="xforms:action">
                    <xsl:attribute name="ev:event"> <xsl:value-of select="'DOMActivate'"/></xsl:attribute>
                    <xsl:element name="xforms:refresh">
                        <xsl:attribute name="ev:event"><xsl:value-of select="'xforms-focus'"/></xsl:attribute>
                    </xsl:element>
                </xsl:element>
            </xsl:element>
        </xsl:element>
    </xsl:element>
    <td width="4">
        <xsl:element name="img">
            <xsl:attribute name="src"><xsl:value-of select="concat(concat('assets/default/images/tabs/bttn_',$activeyn),'_right.gif')"/></xsl:attribute>
            <xsl:attribute name="width"><xsl:value-of select="'4'"/></xsl:attribute>
            <xsl:attribute name="height"><xsl:value-of select="'24'"/></xsl:attribute>
            <xsl:attribute name="id"><xsl:value-of select="concat('right_': $normalizedTabname)"/></xsl:attribute>
        </xsl:element>
    </td>
</xsl:template>

<!-- TEMPLATE DEFINITION TO OBTAIN WIDGET HEADING -->
<xsl:template name="ListHeading">
    <xsl:param name="tabName"/>
    <xsl:choose>
        <xsl:when test="contains($tabName,'')">
            <xsl:value-of select="substring-before($tabName,'')"/>
        </xsl:when>
        <xsl:when test="not(contains($tabName,''))">
            <xsl:value-of select="$tabName"/>
        </xsl:when>
    </xsl:choose>
</xsl:template>

<xsl:template name="ListContent">
    <xsl:element name="xforms:group">
        <xsl:attribute name="model"><xsl:value-of select="@model"/></xsl:attribute>
        <xsl:attribute name="id"><xsl:value-of select="@id"/></xsl:attribute>
        <xsl:attribute name="ref"><xsl:value-of select="@ref"/></xsl:attribute>
        <table cellpadding="1" cellspacing="0" width="99%" align="center" border="0">
            <tr>
                <td bgcolor="#cccc">
                    <table border="0" width="100%" cellpadding="1" cellspacing="1">
                        <tr height="22px">
                            <xsl:for-each select="..//xforms:output">
                                <td class="bg_grey" width="15%">
                                    <b>
                                        <xsl:element name="xforms:output">
                                            <xsl:attribute name="class"> <xsl:value-of select="'label'"/></xsl:attribute>
                                            <xsl:attribute name="value"> <xsl:value-of select="@label"/></xsl:attribute>
                                            <xsl:value-of select="@label"/>
                                        </xsl:element> <!-- end of forms:output-->
                                    </b>
                                </td>
                            </xsl:for-each>
                        </tr>
                        <tr height="22px">
                            <xsl:for-each select="..//xforms:repeat">
                                <xsl:element name="xforms:repeat">
                                    <xsl:attribute name="id"><xsl:value-of select="@id"/></xsl:attribute> 
                                    <xsl:attribute name="nodeset"><xsl:value-of select="@nodeset"/></xsl:attribute>
                                    <xsl:for-each select="*">
                                        <td valign="top" nowrap="nowrap" class="bg_white">
                                            <xsl:copy-of select="."/>
                                        </td>
                                    </xsl:for-each>
                                </xsl:element>
                                <!-- end of xforms:repeat -->
                            </xsl:for-each>
                        </tr>
                    </table>
                </td>
            </tr>
        </table>
    </xsl:element>
    <!-- end of forms:group -->
</xsl:template>

<xsl:template match="/">
    <xsl:element name="html">
        <xsl:element name="object">
            <xsl:attribute name="id"><xsl:value-of select="'FormsPlayer'"/></xsl:attribute>
            <xsl:attribute name="classid"> <xsl:value-of select="'CLSID:4D0ABA11-C5F0-4478-991A-375C4B648F58'"/></xsl:attribute> 
            <xsl:attribute name="width"> <xsl:value-of select="'0'"/></xsl:attribute>
            <xsl:attribute name="height"> <xsl:value-of select="'0'"/></xsl:attribute> 
            <xsl:text>FormsPlayer has failed to load! Please check your installation.</xsl:text> 
            <!--the object tag should have open and close elements instead of just an empty tag in order for FormPlayer to recognize it -->
        </xsl:element>
        <!-- XForms Data Model Instance -->
        <!-- XForms User Interface Model -->
        <xsl:processing-instruction name="import">namespace="xforms" implementation="#FormsPlayer"</xsl:processing-instruction>
        <xsl:element name="xforms:xforms">
            <link rel="stylesheet" href="assets/default/styles/master.css" type="text/css"/>
            <center>
                <font color="blue" size="4">
                    <xsl:value-of select="/form/xforms:layout/xforms:group/xforms:group/xforms:group/xforms:group/@model"/>
                </font>
            </center>
            <br/><br/>
            <b>TABULATION</b>
            <p/><p/>
            <table width="70%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <xsl:for-each select="/form/forms:layout/forms:group/forms:group/xforms:group">
                        <xsl:call-template name="Tabulation">
                            <xsl:with-param name="tabName" select="@label"/>
                        </xsl:call-template>
                    </xsl:for-each>
                </tr>
            </table>
            <br/><br/>
            <b>SIMPLE WIDGET </b><p/><p/>
            <xsl:for-each select="/form/xforms:layout/xforms:group/xforms:group/xforms:group[1]/xforms:group[@type='simple']">
                <xsl:if test="@type='simple'">
                    <xsl:variable name="totalFieldNodes" select="count(child::*)"/>
                    <table cellspacing="0" cellpadding="0" width="100%" align="center" border="1" borderstyle="solid" bordercolor= "black">
                        <xsl:for-each select="child::*">
                            <xsl:call-template name="Simple">
                                <xsl:with-param name="noFields" select="$totalFieldNodes"/>
                                <xsl:with-param name="noColumns" select="'2'"/>
                            </xsl:call-template>
                        </xsl:for-each>
                    </table>
                    <br/><br/>
                </xsl:if>
            </xsl:for-each>

            <b>LIST WIDGET </b><p/>
            <xsl:for-each select="/form/xforms:layout/xforms:group/xforms:group/xforms:group/xforms:group">
                <xsl:if test="@type=list">
                    <xsl:variable name="listHeading">
                        <xsl:call-template name="ListHeading">
                            <xsl:with-param name="tabName" select="../@label"/>
                        </xsl:call-template>
                    </xsl:variable>
                    <xsl:element name="xforms:case">
                        <xsl:attribute name="id"><xsl:value-of select="concat('case', $listHeading)"/></xsl:attribute>
                        <xsl:element name="div">
                            <xsl:attribute name="style"><xsl:value-of select="@style"/></xsl:attribute> <!-- The style attribute belongs to the inner ‹group> tag -->
                            <br/>
                            <table width="100%" bgcolor="#DCE6CF">
                                <tr>
                                    <td>
                                        <b><xsl:value-of select="$listHeading"/></b> <!-- The label attribute belongs to the outer «group» tag for ex:Measurements -->
                                    </td>
                                </tr>
                            </table>
                        </xsl:element>
                    </xsl:element>
                </xsl:if>
            </xsl:for-each>   
        </xsl:element>     
    </xsl:element>    
</xsl:template>                    