//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.03.25 at 01:28:05 PM GMT 
//


package parser;

import java.math.BigInteger;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * Text to be displayed
 * 
 * <p>Java class for TextType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TextType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;attribute name="content" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="starttime" use="required" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" /&gt;
 *       &lt;attribute name="duration" use="required" type="{http://www.w3.org/2001/XMLSchema}int" /&gt;
 *       &lt;attribute name="xstart" use="required"&gt;
 *         &lt;simpleType&gt;
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}float"&gt;
 *             &lt;minInclusive value="0"/&gt;
 *             &lt;maxInclusive value="1"/&gt;
 *           &lt;/restriction&gt;
 *         &lt;/simpleType&gt;
 *       &lt;/attribute&gt;
 *       &lt;attribute name="ystart" use="required"&gt;
 *         &lt;simpleType&gt;
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}float"&gt;
 *             &lt;minInclusive value="0"/&gt;
 *             &lt;maxInclusive value="1"/&gt;
 *           &lt;/restriction&gt;
 *         &lt;/simpleType&gt;
 *       &lt;/attribute&gt;
 *       &lt;attribute name="font" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="fontsize"&gt;
 *         &lt;simpleType&gt;
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int"&gt;
 *             &lt;minInclusive value="1"/&gt;
 *           &lt;/restriction&gt;
 *         &lt;/simpleType&gt;
 *       &lt;/attribute&gt;
 *       &lt;attribute name="fontcolour"&gt;
 *         &lt;simpleType&gt;
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}hexBinary"&gt;
 *             &lt;pattern value="\w{6}"/&gt;
 *           &lt;/restriction&gt;
 *         &lt;/simpleType&gt;
 *       &lt;/attribute&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TextType")
public class TextType {

    @XmlAttribute(name = "sourceFile", required = true)
    protected String sourceFile;
    @XmlAttribute(name = "starttime", required = true)
    @XmlSchemaType(name = "nonNegativeInteger")
    protected Integer starttime = null;
    @XmlAttribute(name = "duration", required = true)
    protected Integer duration = null;
    @XmlAttribute(name = "xstart", required = true)
    protected Float xstart = null;
    @XmlAttribute(name = "ystart", required = true)
    protected Float ystart = null;
    @XmlAttribute(name = "font")
    protected String font;
    @XmlAttribute(name = "fontsize")
    protected Integer fontsize;
    @XmlAttribute(name = "fontcolour")
    protected String fontcolour = null;

    /**
     * Gets the value of the content property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSourceFile() {
        return sourceFile;
    }

    /**
     * Sets the value of the content property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSourceFile(String value) {
        this.sourceFile = value;
    }

    /**
     * Gets the value of the starttime property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public Integer getStarttime() {
        return starttime;
    }

    /**
     * Sets the value of the starttime property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setStarttime(Integer value) {
        this.starttime = value;
    }

    /**
     * Gets the value of the duration property.
     * 
     */
    public Integer getDuration() {
        return duration;
    }

    /**
     * Sets the value of the duration property.
     * 
     */
    public void setDuration(Integer value) {
        this.duration = value;
    }

    /**
     * Gets the value of the xstart property.
     * 
     */
    public Float getXstart() {
        return xstart;
    }

    /**
     * Sets the value of the xstart property.
     * 
     */
    public void setXstart(Float value) {
        this.xstart = value;
    }

    /**
     * Gets the value of the ystart property.
     * 
     */
    public Float getYstart() {
        return ystart;
    }

    /**
     * Sets the value of the ystart property.
     * 
     */
    public void setYstart(Float value) {
        this.ystart = value;
    }

    /**
     * Gets the value of the font property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFont() {
        return font;
    }

    /**
     * Sets the value of the font property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFont(String value) {
        this.font = value;
    }

    /**
     * Gets the value of the fontsize property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getFontsize() {
        return fontsize;
    }

    /**
     * Sets the value of the fontsize property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setFontsize(Integer value) {
        this.fontsize = value;
    }

    /**
     * Gets the value of the fontcolour property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFontcolour() {
        return fontcolour;
    }

    /**
     * Sets the value of the fontcolour property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFontcolour(String value) {
        this.fontcolour = value;
    }

}
