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
 * Each meal will store an integer referencing the index of the meal. '-1' is used for meals that haven't been set.
 * 
 * <p>Java class for dayDiet complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="dayDiet"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;attribute name="breakfast" use="required" type="{http://www.w3.org/2001/XMLSchema}int" /&gt;
 *       &lt;attribute name="lunch" use="required" type="{http://www.w3.org/2001/XMLSchema}int" /&gt;
 *       &lt;attribute name="dinner" use="required" type="{http://www.w3.org/2001/XMLSchema}int" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "dayDiet")
public class DayDiet implements java.io.Serializable {

    @XmlAttribute(name = "breakfast", required = true)
    protected int breakfast;
    @XmlAttribute(name = "lunch", required = true)
    protected int lunch;
    @XmlAttribute(name = "dinner", required = true)
    protected int dinner;

    /**
     * Gets the value of the breakfast property.
     * 
     */
    public int getBreakfast() {
        return breakfast;
    }

    /**
     * Sets the value of the breakfast property.
     * 
     */
    public void setBreakfast(int value) {
        this.breakfast = value;
    }

    /**
     * Gets the value of the lunch property.
     * 
     */
    public int getLunch() {
        return lunch;
    }

    /**
     * Sets the value of the lunch property.
     * 
     */
    public void setLunch(int value) {
        this.lunch = value;
    }

    /**
     * Gets the value of the dinner property.
     * 
     */
    public int getDinner() {
        return dinner;
    }

    /**
     * Sets the value of the dinner property.
     * 
     */
    public void setDinner(int value) {
        this.dinner = value;
    }

}
