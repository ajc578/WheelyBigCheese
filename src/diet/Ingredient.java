//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.05.15 at 01:29:45 PM BST 
//


package diet;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

/**
 * A class used to store the name and quantity of an ingredient in
 * a recipe. This class was originally generated 
 * from the account xsd file via JAXB, but has since been manually 
 * edited and corrected.
 * 
 * <p> <STRONG> Developed by </STRONG> <p>
 * Oliver Rushton
 * <p> <STRONG> Tested by </STRONG> <p>
 * Oliver Rushton
 * <p> <STRONG> Developed for </STRONG> <p>
 * BOSS
 * @author Oliver Rushton
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ingredient")
public class Ingredient {

    @XmlAttribute(name = "ingredientName", required = true)
    protected String ingredientName;
    @XmlAttribute(name = "quantity", required = true)
    protected String quantity;

    /**
     * Gets the value of the ingredientName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIngredientName() {
        return ingredientName;
    }

    /**
     * Sets the value of the ingredientName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIngredientName(String value) {
        this.ingredientName = value;
    }

    /**
     * Gets the value of the quantity property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getQuantity() {
        return quantity;
    }

    /**
     * Sets the value of the quantity property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setQuantity(String value) {
        this.quantity = value;
    }

}
