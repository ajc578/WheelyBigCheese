//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.03.25 at 01:28:05 PM GMT 
//


package parser;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * custom colour gradients
 *
 * <p>Java class for ShadingType complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="ShadingType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;attribute name="x1" use="required"&gt;
 *         &lt;simpleType&gt;
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}float"&gt;
 *             &lt;minInclusive value="0"/&gt;
 *             &lt;maxInclusive value="1"/&gt;
 *           &lt;/restriction&gt;
 *         &lt;/simpleType&gt;
 *       &lt;/attribute&gt;
 *       &lt;attribute name="y1" use="required"&gt;
 *         &lt;simpleType&gt;
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}float"&gt;
 *             &lt;minInclusive value="0"/&gt;
 *             &lt;maxInclusive value="1"/&gt;
 *           &lt;/restriction&gt;
 *         &lt;/simpleType&gt;
 *       &lt;/attribute&gt;
 *       &lt;attribute name="colour1" use="required"&gt;
 *         &lt;simpleType&gt;
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}hexBinary"&gt;
 *             &lt;pattern value="\w{6}"/&gt;
 *           &lt;/restriction&gt;
 *         &lt;/simpleType&gt;
 *       &lt;/attribute&gt;
 *       &lt;attribute name="x2" use="required"&gt;
 *         &lt;simpleType&gt;
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}float"&gt;
 *             &lt;minInclusive value="0"/&gt;
 *             &lt;maxInclusive value="1"/&gt;
 *           &lt;/restriction&gt;
 *         &lt;/simpleType&gt;
 *       &lt;/attribute&gt;
 *       &lt;attribute name="y2" use="required"&gt;
 *         &lt;simpleType&gt;
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}float"&gt;
 *             &lt;minInclusive value="0"/&gt;
 *             &lt;maxInclusive value="1"/&gt;
 *           &lt;/restriction&gt;
 *         &lt;/simpleType&gt;
 *       &lt;/attribute&gt;
 *       &lt;attribute name="colour2" use="required"&gt;
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
@XmlType(name = "ShadingType")
public class ShadingType {

    @XmlAttribute(name = "x1", required = true)
    protected Float x1 = null;
    @XmlAttribute(name = "y1", required = true)
    protected Float y1 = null;
    @XmlAttribute(name = "colour1", required = true)
    protected String colour1 = null;
    @XmlAttribute(name = "x2", required = true)
    protected Float x2 = null;
    @XmlAttribute(name = "y2", required = true)
    protected Float y2 = null;
    @XmlAttribute(name = "colour2", required = true)
    protected String colour2 = null;

    /**
     * Gets the value of the x1 property.
     *
     */
    public Float getX1() {
        return x1;
    }

    /**
     * Sets the value of the x1 property.
     *
     */
    public void setX1(Float value) {
        this.x1 = value;
    }

    /**
     * Gets the value of the y1 property.
     *
     */
    public Float getY1() {
        return y1;
    }

    /**
     * Sets the value of the y1 property.
     *
     */
    public void setY1(Float value) {
        this.y1 = value;
    }

    /**
     * Gets the value of the colour1 property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getColour1() {
        return colour1;
    }

    /**
     * Sets the value of the colour1 property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setColour1(String value) {
        this.colour1 = value;
    }

    /**
     * Gets the value of the x2 property.
     *
     */
    public Float getX2() {
        return x2;
    }

    /**
     * Sets the value of the x2 property.
     *
     */
    public void setX2(Float value) {
        this.x2 = value;
    }

    /**
     * Gets the value of the y2 property.
     *
     */
    public Float getY2() {
        return y2;
    }

    /**
     * Sets the value of the y2 property.
     *
     */
    public void setY2(Float value) {
        this.y2 = value;
    }

    /**
     * Gets the value of the colour2 property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getColour2() {
        return colour2;
    }

    /**
     * Sets the value of the colour2 property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setColour2(String value) {
        this.colour2 = value;
    }

}
