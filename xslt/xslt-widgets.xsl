
<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" 
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
    xmlns:xforms="http://www.w3.org/2002/xforms" 
    xmlns:ev="http://www.w3.org/2001/xml-events"
    xmlns:cinclude="http://apache.org/cocoon/include/1.0" 
    xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/" 
    xmlns:xsi="http://www.w.org/2001/XMLSchema">

<!-- Simple Widget -->
<xsl:template name="Simple">
    <xsl:param name="noFields"/>
    <xsl:param name="noColumns"/>
    <xsl:variable name="fieldIner" select="$noFields div $noColumns"/>
    <xsl:variable name="fieldPos" select="position ()"/>
    <xsl:variable name="fieldPos1" select="$fieldPos + $fieldIner"/>
    <!--layout second column of the simple widget -->
    <xsl:if test="$fieldPos &lt; = $fieldIncr">
        <tr>
            <xsl:call-template name="Field">
                <xsl:with-param name="fieldlabelXPath" select="child::xforms:label[position()]"/>   
                <xsl:with-param name="avoidAlertXPath" select="child::xforms:alert[position()]='required'"/>
                <xsl:with-param name="fieldcontrolXPath" select="@basicType"/>
                <xsl:with-param name="fieldattributeXPath" select="@*"/>
                <xsl:with-param name="fieldchildXPath"  select="./*"/>
            </xsl:call-template>

            <xsl:call-template name="Field">
                <xsl:with-param name="fieldlabelXPath" select="../xforms:fieldl[$kfieldPos1]/xforms:label"/>
                <xsl:with-param name="avoidAlertXPath" select="../xforms:field[$fieldPos1]/xforms:alert='required'"/>
                <xsl:with-param name="fieldcontrolXPath" select="../xforms:field[$fieldPos1]/@basicType"/>
                <xsl:with-param name="fieldattributeXPath" select="../xforms:field[$fieldPos1]/@*"/>
                <xsl:with-param name="fieldchildXPath" select="../xforms:field[$fieldPos1] /*"/>
            </xsl:call-template>
        </tr>
    </xsl:if>
</xsl:template>

<xsl:template name="Field">
    <xsl:param name="fieldlabelXPath"/>
    <xsl:param name="avoidAlertXPath"/>
    <xsl:param name="fieldcontrolXPath"/>
    <xs1:param name="fieldattributeXPath"/>
    <xsl:param name="fieldchildXPath"/>
    <td nowrap="nowrap" class="label">
        <xsl:element name="xforms: label">
            <xsl:attribute name="style">
                <xsl:value-of select="xforms:label/@style"/>
            </xsl:attribute>
            <xsl:value-of select="$fieldlabelXPath"/>
            <xsl:if test="$avoidAlertXPath">
                <span class="star">*</span>
            </xsl:if>
        </xsl:element>
    </td>
    <td nowrap="nowrap" class="label">
        <!-- Field UI Object -->
        <xsl:element name="xforms:{$fieldcontrolXPath}">
            <xsl:for-each select="$fieldattributeXPath">
                <xsl:copy-of select="."/>
            </xsl:for-each>
            <xsl:for-each select="$fieldchildXPath">
                <xsl:if test="((name() != 'xforms:label') and (name() != 'xforms :alert'))"> 
                    <xsl:copy-of select="."/>
                </xsl:if>
            </xsl:for-each>
        </xsl:element>
    </td>
</xsl:template>

<xsl:template name="ActiveInactive">
    <xsl:param name="fieldPos"/>
    <xsl:choose>
        <xsl:when test="$fieldPos &lt;= 1">
            <xsl:value-of select="'active'"/>
        </xsl:when>
        <xsl:otherwise>
            <xsl:value-of select="'inactive'"/>
        </xsl:otherwise >
    </xs1:choose>
</xsl:template>

<xsl:template name="Tabulation">
    <xsl:param name="tabName"/>
    <xsl:variable name="fieldPos" select="position ()"/>
    <xsl:variable name="activeyn">
        <xsl:call-template name="ActiveInactive">
            <xsl:with-param name="fieldPos" select="$fieldPos"/>
        </xsl:call-template>
    </xsl:variable>
    <xsl:variable name="normalizedTabname">
        <xsl:call-template name="Heading"> 
            <xsl:with-param name="tabName" select="$tabName"/>
        </xsl:call-template>
    </xsl:variable>
    <td width="g" background="assets/default/images/tabs/table_tabs.gif"/> 
    <xsl:call-template name="TabEdgeImage">
        <xsl:with-param name="activeyn" select="$activeyn"/>
        <xsl:with-param name="direction" select="'left'"/>
        <xsl:with-param name="normalizedTabname" select="$normalizedTabname"/>
    </xsl:call-template>
    <xsl:element name="td">
        <xsl:attribute name="width">
            <xsl:value-of select="'100'"/>
        </xsl:attribute>
        <xsl:attribute name="id">
            <xsl:value-of select="concat ($normalizedTabname,'tab')"/>
        </xsl:attribute>
        <xsl:attribute name="class">
            <xsl:value-of select="concat ($activeyn,'tab')"/>
        </xsl:attribute>
        <xsl:attribute name="align">
            <xsl:value-of select="'center"/>
        </xsl:attribute>
   

        <xsl:element name="font">
            <xsl:attribute name="id">
                <xsl:value-of select="concat ($normalizedTabname, 'Ent')"/>
            </xsl:attribute>
            <xsl:attribute name="class">
                <xsl:value-of select="concat ($activeyn, 'tab-text')"/>
            </xsl:attribute>

    
            <xsl:element name="xforms:trigger">
                <xsl:attribute name="id">
                    <xsl:value-of select="$normalizedTabname"/>
                </xsl:attribute>
                <xsl:attribute name="appearance">
                    <xsl:value-of select="'minimal'"/>
                </xsl:attribute>
    

                <xsl:element name="xforms:label">
                    <xsl:attribute name="id">
                        <xsl:value-of select="concat ('label_', $normalizedTabname)"/>
                    </xsl:attribute>
                    <xsl:value-of select="$normalizedTabname"/>
                </xsl:element>

                <xsl:element name="xforms:toggle">
                    <xsl:attribute name="case">
                        <xsl:value-of select="concat ('case', $normalizedTabname)"/>
                    </xsl:attribute>
                    <xsl:attribute name="ev:event">
                        <xsl:value-of select="'DOMActivate'"/>
                    </xsl:attribute>
                </xsl:element>

                <xsl:element name="xforms:action">
                    <xsl:attribute name="ev:event">
                        <xsl:value-of select="'DOMActivate'"/>
                    </xsl:attribute>
                    <xsl:element name="xforms:refresh">
                        <xsl:attribute name="ev:event">
                            <xsl:value-of select="'forms-focus'"/>
                        </xsl:attribute>
                    </xsl:element>
                </xsl:element>
            </xsl:element>
        </xsl:element>
    </xsl:element>

    <xsl:call-template name="TabEdgeImage" >
        <xsl:with-param name="activeyn" select="$activeyn"/>
        <xsl:with-param name="direction" select="'right'"/> 
        <xsl:with-param name="normalizedTabname" select="$normalizedTabname"/>
    </xsl:call-template>
</xsl:template>

<xsl:template name="TabEdgeImage">
    <xsl:param name="activeyn"/>
    <xsl:param name="direction"/>
    <xsl:param name="normalizedTabname"/>
    <td width="4">
        <xsl:element name="img">
            <xsl:attribute name="src">
                <xsl:value-of select="concat (concat ( concat(concat ('assets/default/images/tabs/bttn_', Sactiveyn),'_'), $direction),'.gif') "/>
            </xsl:attribute>
            <xsl:attribute name="width"><xsl:value-of select="'4'"/></xsl:attribute>
            <xsl:attribute name="height"><xsl:value-of select="'24'"/></xsl:attribute>
            <xsl:attribute name="id">
                <xsl:value-of select="concat (concat ($direction,'-'), §normalizedIabname)"/>
            </xsl:attribute>
        </xsl:element>
    </td>
</xsl:template>

<xsl:template name="Heading">
    <xsl:param name="tabName"/>
    <xsl:choose>
        <xsl:when test="contains($tabName,' ')">
            <xsl:value-of select="substring-before($tabName,' ') "/>
        </xsl:when>
        <xsl:when test="not(contains($tabName,' ')) ">
            <xsl:value-of select="$tabName"/>
        </xsl:when>
    </xsl:choose>
</xsl:template>

<xsl:template name="Content">
    <xsl:param name="className"/>
    <xsl:param name="tblDivWidth"/>
    <xsl:element name="xforms:group">
        <xsl:attribute name="model"><xsl:value-of select="@model"/></xsl:attribute>
        <xsl:attribute name="id"><xsl:value-of select="@id"/></xsl:attribute>
        <xsl:attribute name="ref"><xsl:value-of select="@ref"/></xs1:attribute>
        <table cellpadding="1" cellspacing="0" width="99g" align="center" border="0">
        <td bgcolor="#cccc">
            <table border="0" width="100%" cellpadding="1" cellspacing="1">
                <tr height="22px">
                    <xsl:for-each select="..//xforms:output">
                        <xsl:element name="td">
                            <xsl:attribute name="class"><xsl:value-of select="'bg_grey'"/></xsl:attribute>
                            <xsl:attribute name="width"><xsl:value-of select="$tblDivWidth"/></xsl:attribute>
                            <xsl:attribute name="nowrap">
                                <xsl:value-of select="'nowrap'"/>
                            </xsl:attribute>
                            <b>
                                <xsl:element name="xforms:output">
                                    <xsl:attribute name="class">
                                        <xsl:value-of select="$className"/></xsl:attribute>
                                    <xsl:attribute name="value">
                                        <xsl:value-of select="@label"/>
                                    </xsl: attribute>
                                    <xsl:value-of select="@label"/>
                                </xsl:element>

                            </b>
                        </xsl:element>
                    </xsl:for-each>
                </tr>
                <tr height="22px">
                    <xsl:for-each select="..//xforms:repeat">
                        <xsl:element name="xforms:repeat">
                            <xsl:attribute name="id">
                                <xsl:value-of select="@id"/>
                            </xsl:attribute>
                            <xsl:attribute name="nodeset">
                                <xsl:value-of select="@nodeset"/>
                            </xsl:attribute>
                            <xsl:for-each select="*">
                                <td valign="top" nowrap="nowrap" class="bg_white">
                                    <xsl: copy-of select="."/>
                                </td>
                            </xsl:for-each>
                        </xsl:element>
                    </xsl:for-each>
                </tr>
            </table>
        </td>
    </xsl:element>
</xsl:template>

</tr>
</table>

<xsl:template name="ButtonSet">
    <xsl:element name="xforms:trigger">
        <xsl:attribute name="appearance"><xsl:value-of select="@appearance"/></xsl:attribute>
        <xsl:attribute name="style"><xsl:value-of select="@style"/></xsl:attribute>
        <xsl:attribute name="class"><xsl:value-of select="'trig'"/></xsl:attribute>
        <xsl:for-each select="@*">
            <xsl:if test="name ()= 'bind'">
                 <xsl:attribute name="bind"><xsl:value-of select="."/></xsl:attribute>
            </xsl:if>         
        </xsl:for-each>
        <xsl:element name="xforms:label">
            <xsl:attribute name="class"><xsl:value-of select="'trigLabel'"/></xsl:attribute>
            <xsl:element name="img">
                <xsl:for-each select="descendant:: forms: img /@*">
                    <xs1:copy-of select="."/>
                </xsl:for-each>
            </xsl:element>
            <xsl:element name="forms:action">
                <xsl:attribute name="ev:event"><xsl:value-of select="xforms: action/@ev:event"/></xsl:attribute>
                <xsl:for-each select="descendant:: forms: setvalue">
                    <xsl:copy-of select="."/>
                </xsl:for-each>                        
                <xsl:element name="xforms:toggle">
                    <xsl:for-each select="descendant:: xforms: toggle/@*">
                        <xsl:copy-of select="."/>
                    </xsl:for-each>
                </xsl:element>
            </xsl:element>
        </xsl:element>
</xsl:template>

<xsl:template name="SelectAWidget">
    <xsl:param name="noFields"/>
    <xsl:param name="noColumns"/>
    <xsl:param name="containerName"/>
    <xsl:param name="containerPosition"/>
    <xsl:param name="widgetType"/>
    <xsl:element name="xforms:case"> 
        <xsl:variable name="caseId">
            <xsl:call-template name="Heading">
                <xsl:with-param name="tabName" select="$containerName"/>
            </xsl:call-template>
        </xsl:variable>
        <xsl:attribute name="id"><xs1:value-of select="concat ('case', $caseId) "/></xsl:attribute>
        <xsl:choose>
            <xsl:when test="$containerPosition = '1'">
                <xsl:attribute name="selected"><xsl:value-of select="'true'"/></xsl:attribute>
            </xsl:when>
            <xsl:otherwise>
                <xsl:attribute name="selected">
                    <xsl:value-of select="'false'"/></xsl:attribute>
                </xsl:otherwise>
        </xsl:choose>
        <xsl:element name="xforms:group">
            <xsl:for-each select="@*">
                <xsl:copy-of select="."/>
            </xsl:for-each>
            <xsl:choose>
                <xsl:when test="$widgetType='simple'">
                    <xsl:for-each select="child::xforms:field">
                        <xsl:call-template name="Simple">
                            <xsl:with-param name="noFields" select="$noFields"/>
                            <xsl:with-param name="noColumns" select="$noColumns"/>
                        </xsl:call-template>
                    </xsl:for-each>
                </xsl:when> 
                <xsl:when test="$widgetType='list'">
                    <table width="100%" bgcolor="#DCE6CF">
                        <b><xsl: value-of select="$caseId" /></b>
                    </table>
                    <br/>
                    <xsl:call-template name="Content">
                        <xsl:with-param name="className" select="'label'"/>
                        <xsl:with-param name="tblDivWidth" select="'15%'"/>
                    </xsl:call-template>
                </xsl:when> 
                <xsl:when test="$widgetType='table'">
                    <table width="100%" bgcolor="#DCE6CF">
                        <tr>
                            <td>
                            </td>
                            <b><xsl:value-of select="$caseId" /></b>
                        </tr>
                    </table>
                    <br/>
                    <xsl:call-template name="Content">
                        <xs1:with-param name="className" select="'forSource'"/>
                        <xsl:with-param name="tblDivWidth" select="'9%'"/>
                    </xsl:call-template>
                    <xsl:for-each select="descendant::xforms:trigger">
                        <td>
                            <xsl:call-template name="ButtonSet"/>
                        </td>
                    </xsl:for-each>
                </xsl:when>
            </xsl:choose>
        </xsl:element>
    </xsl:element>
</xsl:template>

<xsl:template name="Container">
    <xsl:param name="containers"/> 
    <xsl:for-each select="$containers">
        <xsl:variable name="containerPosition" select="position()"/> 
        <xsl:variable name="containerName" select="@label"/>
        <xsl:for-each select="child:: *">
            <xsl:variable name="totalFieldNodes" select="count (xforms: field)"/>
            <xsl:call-template name="SelectAWidget">
                <xsl:with-param name="noFields" select="$totalFieldNodes"/>
                <xsl:with-param name="noColumns" select="@columns" /> 
                <xsl:with-param name="containerName" select="$containerName"/>
                <xsl:with-param name="containerPosition" select="$containerPosition"/>
                <xsl:with-param name="widgetType" select="@type"/>
            </xsl:call-template>
        </xsl:for-each>
    </xs1:for-each>
</xsl:template>