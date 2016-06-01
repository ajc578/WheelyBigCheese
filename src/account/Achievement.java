//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.05.15 at 03:07:05 PM BST 
//


package account;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

/**
 * A class used to store all the associated attributes of an 
 * <code>Achievement</code> for an {@link Account} loaded in by
 * the JAXB Marshaller from an xml file generated by the account.xsd.
 * This Class was generated by JAXB and then manually modified.
 * 
 * <p> <STRONG> Generated by </STRONG> <p>
 * JAXB
 * <p> <STRONG> Developed by </STRONG> <p>
 * Oliver Rushton
 * <p> <STRONG> Tested by </STRONG> <p>
 * Oliver Rushton
 * <p> <STRONG> Developed for </STRONG> <p>
 * BOSS
 * @author Oliver Rushton
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Achievement")
public class Achievement implements java.io.Serializable {

    private static final long serialVersionUID = 1L;
    @XmlAttribute(name = "index", required = true)
    protected int index;
    @XmlAttribute(name = "content", required = true)
    protected String content;
    @XmlAttribute(name = "points")
    protected Integer points;
    @XmlAttribute(name = "gainz")
    protected Integer gainz;
    @XmlAttribute(name = "currentValue", required = true)
    protected int currentValue;
    @XmlAttribute(name = "threshold", required = true)
    protected int threshold;
    @XmlAttribute(name = "complete", required = true)
    protected boolean complete;

    /**
     * Gets the value of the index property.
     *
     */
    public int getIndex() {
        return index;
    }

    /**
     * Sets the value of the index property.
     *
     */
    public void setIndex(int value) {
        this.index = value;
    }

    /**
     * Gets the value of the content property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getContent() {
        return content;
    }

    /**
     * Sets the value of the content property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setContent(String value) {
        this.content = value;
    }

    /**
     * Gets the value of the points property.
     *
     * @return
     *     possible object is
     *     {@link Integer }
     *
     */
    public Integer getPoints() {
        return points;
    }

    /**
     * Sets the value of the points property.
     *
     * @param value
     *     allowed object is
     *     {@link Integer }
     *
     */
    public void setPoints(Integer value) {
        this.points = value;
    }

    /**
     * Gets the value of the gainz property.
     *
     * @return
     *     possible object is
     *     {@link Integer }
     *
     */
    public Integer getGainz() {
        return gainz;
    }

    /**
     * Sets the value of the gainz property.
     *
     * @param value
     *     allowed object is
     *     {@link Integer }
     *
     */
    public void setGainz(Integer value) {
        this.gainz = value;
    }

    /**
     * Gets the value of the currentValue property.
     *
     */
    public int getCurrentValue() {
        return currentValue;
    }

    /**
     * Sets the value of the currentValue property.
     *
     */
    public void setCurrentValue(int value) {
        this.currentValue = value;
    }

    /**
     * Gets the value of the threshold property.
     *
     */
    public int getThreshold() {
        return threshold;
    }

    /**
     * Sets the value of the threshold property.
     *
     */
    public void setThreshold(int value) {
        this.threshold = value;
    }

    /**
     * Gets the value of the complete property.
     *
     */
    public boolean isComplete() {
        return complete;
    }

    /**
     * Sets the value of the complete property.
     *
     */
    public void setComplete(boolean value) {
        this.complete = value;
    }

}
