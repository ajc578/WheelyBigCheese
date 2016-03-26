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
 * <p>Java class for VideoType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="VideoType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;attribute name="starttime" use="required" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" /&gt;
 *       &lt;attribute name="duration" type="{http://www.w3.org/2001/XMLSchema}int" /&gt;
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
 *       &lt;attribute name="sourceFile" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="loop" use="required" type="{http://www.w3.org/2001/XMLSchema}boolean" /&gt;
 *       &lt;attribute name="width" use="required"&gt;
 *         &lt;simpleType&gt;
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}float"&gt;
 *             &lt;minInclusive value="0"/&gt;
 *             &lt;maxInclusive value="1"/&gt;
 *           &lt;/restriction&gt;
 *         &lt;/simpleType&gt;
 *       &lt;/attribute&gt;
 *       &lt;attribute name="height" use="required"&gt;
 *         &lt;simpleType&gt;
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}float"&gt;
 *             &lt;minInclusive value="0"/&gt;
 *             &lt;maxInclusive value="1"/&gt;
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
@XmlType(name = "VideoType")
public class VideoType {

    @XmlAttribute(name = "starttime", required = true)
    @XmlSchemaType(name = "nonNegativeInteger")
    protected int starttime;
    @XmlAttribute(name = "duration")
    protected Integer duration;
    @XmlAttribute(name = "xstart", required = true)
    protected Float xstart = null;
    @XmlAttribute(name = "ystart", required = true)
    protected Float ystart = null;
    @XmlAttribute(name = "sourceFile", required = true)
    protected String sourceFile = null;
    @XmlAttribute(name = "loop", required = true)
    protected boolean loop;
    @XmlAttribute(name = "width", required = true)
    protected Float width = null;
    @XmlAttribute(name = "height", required = true)
    protected Float height = null;
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
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getDuration() {
        return duration;
    }

    /**
     * Sets the value of the duration property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
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
     * Gets the value of the sourceFile property.
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
     * Sets the value of the sourceFile property.
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
     * Gets the value of the loop property.
     * 
     */
    public boolean isLoop() {
        return loop;
    }

    /**
     * Sets the value of the loop property.
     * 
     */
    public void setLoop(boolean value) {
        this.loop = value;
    }

    /**
     * Gets the value of the width property.
     * 
     */
    public Float getWidth() {
        return width;
    }

    /**
     * Sets the value of the width property.
     * 
     */
    public void setWidth(Float value) {
        this.width = value;
    }

    /**
     * Gets the value of the height property.
     * 
     */
    public Float getHeight() {
        return height;
    }

    /**
     * Sets the value of the height property.
     * 
     */
    public void setHeight(Float value) {
        this.height = value;
    }

}
