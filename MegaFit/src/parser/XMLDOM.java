//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.03.25 at 01:28:05 PM GMT 
//


package parser;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 * 					Note: i) All coordinates represent the top-left corner
 * 					of the object in question. ii) All coordinates are
 * 					specified as a percentage of the display area
 * 					(top-to-bottom, left-to-right) (between 0 and 1) 
 * 					iii) All URL's are absolute or relative to current URL.
 * 					iv) All colour's are represented by a 6 digit hex code
 * 					of the form 'ffffff'
 * 				
 * 
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="documentInfo"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="Title" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="Author" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                   &lt;element name="Version" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                   &lt;element name="Comment" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="defaults"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="backgroundColour"&gt;
 *                     &lt;simpleType&gt;
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}hexBinary"&gt;
 *                         &lt;pattern value="\w{6}"/&gt;
 *                       &lt;/restriction&gt;
 *                     &lt;/simpleType&gt;
 *                   &lt;/element&gt;
 *                   &lt;element name="font" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="fontsize"&gt;
 *                     &lt;simpleType&gt;
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int"&gt;
 *                         &lt;minInclusive value="1"/&gt;
 *                       &lt;/restriction&gt;
 *                     &lt;/simpleType&gt;
 *                   &lt;/element&gt;
 *                   &lt;element name="fontColour"&gt;
 *                     &lt;simpleType&gt;
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}hexBinary"&gt;
 *                         &lt;pattern value="\w{6}"/&gt;
 *                       &lt;/restriction&gt;
 *                     &lt;/simpleType&gt;
 *                   &lt;/element&gt;
 *                   &lt;element name="lineColour"&gt;
 *                     &lt;simpleType&gt;
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}hexBinary"&gt;
 *                         &lt;pattern value="\w{6}"/&gt;
 *                       &lt;/restriction&gt;
 *                     &lt;/simpleType&gt;
 *                   &lt;/element&gt;
 *                   &lt;element name="fillColour"&gt;
 *                     &lt;simpleType&gt;
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}hexBinary"&gt;
 *                         &lt;pattern value="\w{6}"/&gt;
 *                       &lt;/restriction&gt;
 *                     &lt;/simpleType&gt;
 *                   &lt;/element&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="slide" maxOccurs="unbounded" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="backgroundColour" minOccurs="0"&gt;
 *                     &lt;simpleType&gt;
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}hexBinary"&gt;
 *                         &lt;pattern value="\w{6}"/&gt;
 *                       &lt;/restriction&gt;
 *                     &lt;/simpleType&gt;
 *                   &lt;/element&gt;
 *                   &lt;sequence maxOccurs="unbounded"&gt;
 *                     &lt;element name="text" type="{SwengPWS}TextType" minOccurs="0"/&gt;
 *                     &lt;element name="shape" type="{SwengPWS}ShapeType" minOccurs="0"/&gt;
 *                     &lt;element name="polygon" type="{SwengPWS}PolygonType" minOccurs="0"/&gt;
 *                     &lt;element name="image" type="{SwengPWS}ImageType" minOccurs="0"/&gt;
 *                     &lt;element name="video" type="{SwengPWS}VideoType" minOccurs="0"/&gt;
 *                     &lt;element name="audio" type="{SwengPWS}AudioType" minOccurs="0"/&gt;
 *                     &lt;element name="interactable" minOccurs="0"&gt;
 *                       &lt;complexType&gt;
 *                         &lt;complexContent&gt;
 *                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                             &lt;choice&gt;
 *                               &lt;element name="text" type="{SwengPWS}TextType" minOccurs="0"/&gt;
 *                               &lt;element name="shape" type="{SwengPWS}ShapeType" minOccurs="0"/&gt;
 *                               &lt;element name="polygon" type="{SwengPWS}PolygonType" minOccurs="0"/&gt;
 *                               &lt;element name="image" type="{SwengPWS}ImageType" minOccurs="0"/&gt;
 *                               &lt;element name="video" type="{SwengPWS}VideoType" minOccurs="0"/&gt;
 *                             &lt;/choice&gt;
 *                             &lt;attribute name="targetSlide" use="required" type="{http://www.w3.org/2001/XMLSchema}int" /&gt;
 *                           &lt;/restriction&gt;
 *                         &lt;/complexContent&gt;
 *                       &lt;/complexType&gt;
 *                     &lt;/element&gt;
 *                   &lt;/sequence&gt;
 *                 &lt;/sequence&gt;
 *                 &lt;attribute name="slideID" use="required" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" /&gt;
 *                 &lt;attribute name="nextSlide" type="{http://www.w3.org/2001/XMLSchema}integer" /&gt;
 *                 &lt;attribute name="duration" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" /&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "documentInfo",
    "defaults",
    "slide"
})
@XmlRootElement(name = "Presentation")
public class XMLDOM {

    @XmlElement(required = true)
    protected XMLDOM.DocumentInfo documentInfo;
    @XmlElement(required = true)
    protected XMLDOM.Defaults defaults;
    protected List<XMLDOM.Slide> slide;
    @XmlAttribute(name = "workoutName")
    protected String workoutName;
    @XmlAttribute(name = "workoutDuration")
    protected int workoutDuration;
    @XmlAttribute(name = "description")
    protected String description;

    /**
     * Gets the value of the documentInfo property.
     * 
     * @return
     *     possible object is
     *     {@link XMLDOM.DocumentInfo }
     *     
     */
    public XMLDOM.DocumentInfo getDocumentInfo() {
        return documentInfo;
    }

    /**
     * Sets the value of the documentInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLDOM.DocumentInfo }
     *     
     */
    public void setDocumentInfo(XMLDOM.DocumentInfo value) {
        this.documentInfo = value;
    }

    /**
     * Gets the value of the defaults property.
     * 
     * @return
     *     possible object is
     *     {@link XMLDOM.Defaults }
     *     
     */
    public XMLDOM.Defaults getDefaults() {
        return defaults;
    }

    /**
     * Sets the value of the defaults property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLDOM.Defaults }
     *     
     */
    public void setDefaults(XMLDOM.Defaults value) {
        this.defaults = value;
    }

    /**
     * Gets the value of the slide property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the slide property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSlide().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link XMLDOM.Slide }
     * 
     * 
     */
    public List<XMLDOM.Slide> getSlide() {
        if (slide == null) {
            slide = new ArrayList<XMLDOM.Slide>();
        }
        return this.slide;
    }
    
    /**
     * Gets the value of the workoutName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWorkoutName() {
        return workoutName;
    }

    /**
     * Sets the value of the workoutName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWorkoutName(String value) {
        this.workoutName = value;
    } 
    
    /**
     * Gets the value of the workoutDuration property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public int getWorkoutDuration() {
        return workoutDuration;
    }

    /**
     * Sets the value of the workoutName property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setWorkoutDuration(int value) {
        this.workoutDuration = value;
    } 
    
    /**
     * Gets the value of the description property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescription(String value) {
        this.description = value;
    }


    /**
     * Default appearance settings for all slides
     * 
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType&gt;
     *   &lt;complexContent&gt;
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *       &lt;sequence&gt;
     *         &lt;element name="backgroundColour"&gt;
     *           &lt;simpleType&gt;
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}hexBinary"&gt;
     *               &lt;pattern value="\w{6}"/&gt;
     *             &lt;/restriction&gt;
     *           &lt;/simpleType&gt;
     *         &lt;/element&gt;
     *         &lt;element name="font" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="fontsize"&gt;
     *           &lt;simpleType&gt;
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int"&gt;
     *               &lt;minInclusive value="1"/&gt;
     *             &lt;/restriction&gt;
     *           &lt;/simpleType&gt;
     *         &lt;/element&gt;
     *         &lt;element name="fontColour"&gt;
     *           &lt;simpleType&gt;
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}hexBinary"&gt;
     *               &lt;pattern value="\w{6}"/&gt;
     *             &lt;/restriction&gt;
     *           &lt;/simpleType&gt;
     *         &lt;/element&gt;
     *         &lt;element name="lineColour"&gt;
     *           &lt;simpleType&gt;
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}hexBinary"&gt;
     *               &lt;pattern value="\w{6}"/&gt;
     *             &lt;/restriction&gt;
     *           &lt;/simpleType&gt;
     *         &lt;/element&gt;
     *         &lt;element name="fillColour"&gt;
     *           &lt;simpleType&gt;
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}hexBinary"&gt;
     *               &lt;pattern value="\w{6}"/&gt;
     *             &lt;/restriction&gt;
     *           &lt;/simpleType&gt;
     *         &lt;/element&gt;
     *       &lt;/sequence&gt;
     *     &lt;/restriction&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "backgroundColour",
        "font",
        "fontsize",
        "fontColour",
        "lineColour",
        "fillColour"
    })
    public static class Defaults {

        @XmlElement(required = true, type = String.class)
        protected String backgroundColour = null;
        @XmlElement(required = true)
        protected String font;
        protected int fontsize;
        @XmlElement(required = true, type = String.class)
        protected String fontColour = null;
        @XmlElement(required = true, type = String.class)
        protected String lineColour = null;
        @XmlElement(required = true, type = String.class)
        protected String fillColour = null;

        /**
         * Gets the value of the backgroundColour property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getBackgroundColour() {
            return backgroundColour;
        }

        /**
         * Sets the value of the backgroundColour property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setBackgroundColour(String value) {
            this.backgroundColour = value;
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
         */
        public int getFontsize() {
            return fontsize;
        }

        /**
         * Sets the value of the fontsize property.
         * 
         */
        public void setFontsize(int value) {
            this.fontsize = value;
        }

        /**
         * Gets the value of the fontColour property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getFontColour() {
            return fontColour;
        }

        /**
         * Sets the value of the fontColour property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setFontColour(String value) {
            this.fontColour = value;
        }

        /**
         * Gets the value of the lineColour property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getLineColour() {
            return lineColour;
        }

        /**
         * Sets the value of the lineColour property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setLineColour(String value) {
            this.lineColour = value;
        }

        /**
         * Gets the value of the fillColour property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getFillColour() {
            return fillColour;
        }

        /**
         * Sets the value of the fillColour property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setFillColour(String value) {
            this.fillColour = value;
        }

    }


    /**
     * Information about the current presentation.
     * 
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType&gt;
     *   &lt;complexContent&gt;
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *       &lt;sequence&gt;
     *         &lt;element name="Title" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="Author" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *         &lt;element name="Version" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *         &lt;element name="Comment" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *       &lt;/sequence&gt;
     *     &lt;/restriction&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "title",
        "author",
        "version",
        "comment"
    })
    public static class DocumentInfo {

        @XmlElement(name = "Title", required = true)
        protected String title;
        @XmlElement(name = "Author")
        protected String author;
        @XmlElement(name = "Version")
        protected String version;
        @XmlElement(name = "Comment")
        protected String comment;

        /**
         * Gets the value of the title property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getTitle() {
            return title;
        }

        /**
         * Sets the value of the title property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setTitle(String value) {
            this.title = value;
        }

        /**
         * Gets the value of the author property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getAuthor() {
            return author;
        }

        /**
         * Sets the value of the author property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setAuthor(String value) {
            this.author = value;
        }

        /**
         * Gets the value of the version property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getVersion() {
            return version;
        }

        /**
         * Sets the value of the version property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setVersion(String value) {
            this.version = value;
        }

        /**
         * Gets the value of the comment property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getComment() {
            return comment;
        }

        /**
         * Sets the value of the comment property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setComment(String value) {
            this.comment = value;
        }

    }


    /**
     * 
     * 								The individual slides that make up the content of a
     * 								presentation
     * 							
     * 
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType&gt;
     *   &lt;complexContent&gt;
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *       &lt;sequence&gt;
     *         &lt;element name="backgroundColour" minOccurs="0"&gt;
     *           &lt;simpleType&gt;
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}hexBinary"&gt;
     *               &lt;pattern value="\w{6}"/&gt;
     *             &lt;/restriction&gt;
     *           &lt;/simpleType&gt;
     *         &lt;/element&gt;
     *         &lt;sequence maxOccurs="unbounded"&gt;
     *           &lt;element name="text" type="{SwengPWS}TextType" minOccurs="0"/&gt;
     *           &lt;element name="shape" type="{SwengPWS}ShapeType" minOccurs="0"/&gt;
     *           &lt;element name="polygon" type="{SwengPWS}PolygonType" minOccurs="0"/&gt;
     *           &lt;element name="image" type="{SwengPWS}ImageType" minOccurs="0"/&gt;
     *           &lt;element name="video" type="{SwengPWS}VideoType" minOccurs="0"/&gt;
     *           &lt;element name="audio" type="{SwengPWS}AudioType" minOccurs="0"/&gt;
     *           &lt;element name="interactable" minOccurs="0"&gt;
     *             &lt;complexType&gt;
     *               &lt;complexContent&gt;
     *                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                   &lt;choice&gt;
     *                     &lt;element name="text" type="{SwengPWS}TextType" minOccurs="0"/&gt;
     *                     &lt;element name="shape" type="{SwengPWS}ShapeType" minOccurs="0"/&gt;
     *                     &lt;element name="polygon" type="{SwengPWS}PolygonType" minOccurs="0"/&gt;
     *                     &lt;element name="image" type="{SwengPWS}ImageType" minOccurs="0"/&gt;
     *                     &lt;element name="video" type="{SwengPWS}VideoType" minOccurs="0"/&gt;
     *                   &lt;/choice&gt;
     *                   &lt;attribute name="targetSlide" use="required" type="{http://www.w3.org/2001/XMLSchema}int" /&gt;
     *                 &lt;/restriction&gt;
     *               &lt;/complexContent&gt;
     *             &lt;/complexType&gt;
     *           &lt;/element&gt;
     *         &lt;/sequence&gt;
     *       &lt;/sequence&gt;
     *       &lt;attribute name="slideID" use="required" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" /&gt;
     *       &lt;attribute name="nextSlide" type="{http://www.w3.org/2001/XMLSchema}integer" /&gt;
     *       &lt;attribute name="duration" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" /&gt;
     *     &lt;/restriction&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "backgroundColour",
        "allContent"
    })
    public static class Slide {

        @XmlElement(type = String.class)
        protected String backgroundColour;
        @XmlElements({
            @XmlElement(name = "text", type = TextType.class),
            @XmlElement(name = "shape", type = ShapeType.class),
            @XmlElement(name = "polygon", type = PolygonType.class),
            @XmlElement(name = "image", type = ImageType.class),
            @XmlElement(name = "video", type = VideoType.class),
            @XmlElement(name = "audio", type = AudioType.class),
            @XmlElement(name = "interactable", type = XMLDOM.Slide.Interactable.class)
        })
        protected List<Object> allContent;
        @XmlAttribute(name = "slideID", required = true)
        @XmlSchemaType(name = "nonNegativeInteger")
        protected Integer slideID;
        @XmlAttribute(name = "nextSlide")
        protected Integer nextSlide;
        @XmlAttribute(name = "duration")
        @XmlSchemaType(name = "nonNegativeInteger")
        protected Integer duration;
        @XmlAttribute(name = "exerciseName")
        protected String exerciseName;
        @XmlAttribute(name = "sets")
        protected int sets = 0;
        @XmlAttribute(name = "reps")
        protected int reps = 0;
        @XmlAttribute(name = "points")
        protected int points = 0;
        @XmlAttribute(name = "speed")
        protected double speed = 0.0;
        @XmlAttribute(name = "strength")
        protected double strength = 0.0;
        @XmlAttribute(name = "endurance")
        protected double endurance = 0.0;
        @XmlAttribute(name = "agility")
        protected double agility = 0.0;

        /**
         * Gets the value of the backgroundColour property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getBackgroundColour() {
            return backgroundColour;
        }

        /**
         * Sets the value of the backgroundColour property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setBackgroundColour(String value) {
            this.backgroundColour = value;
        }

        /**
         * Gets the value of the textAndShapeAndPolygon property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the textAndShapeAndPolygon property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getTextAndShapeAndPolygon().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link TextType }
         * {@link ShapeType }
         * {@link PolygonType }
         * {@link ImageType }
         * {@link VideoType }
         * {@link AudioType }
         * {@link XMLDOM.Slide.Interactable }
         * 
         * 
         */
        public List<Object> getAllContent() {
            if (allContent == null) {
            	allContent = new ArrayList<Object>();
            }
            return this.allContent;
        }

        /**
         * Gets the value of the slideID property.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public Integer getSlideID() {
            return slideID;
        }

        /**
         * Sets the value of the slideID property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setSlideID(Integer value) {
            this.slideID = value;
        }

        /**
         * Gets the value of the nextSlide property.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public Integer getNextSlide() {
            return nextSlide;
        }

        /**
         * Sets the value of the nextSlide property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setNextSlide(Integer value) {
            this.nextSlide = value;
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
         * Gets the value of the exerciseName property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getExerciseName() {
            return exerciseName;
        }

        /**
         * Sets the value of the exerciseName property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setExerciseName(String value) {
            this.exerciseName = value;
        }
        
        /**
         * Gets the value of the sets property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public int getSets() {
            return sets;
        }

        /**
         * Sets the value of the sets property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setSets(int value) {
            this.sets = value;
        }
        
        /**
         * Gets the value of the reps property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public int getReps() {
            return reps;
        }

        /**
         * Sets the value of the reps property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setReps(int value) {
            this.reps = value;
        }
        
        /**
         * Gets the value of the points property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public int getPoints() {
            return points;
        }

        /**
         * Sets the value of the points property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setPoints(int value) {
            this.points = value;
        }
        
        /**
         * Gets the value of the speed property.
         * 
         * @return
         *     possible object is
         *     {@link Double }
         *     
         */
        public double getSpeed() {
            return speed;
        }

        /**
         * Sets the value of the speed property.
         * 
         * @param value
         *     allowed object is
         *     {@link Double }
         *     
         */
        public void setSpeed(double value) {
            this.speed = value;
        }
        
        /**
         * Gets the value of the strength property.
         * 
         * @return
         *     possible object is
         *     {@link Double }
         *     
         */
        public double getStrength() {
            return strength;
        }

        /**
         * Sets the value of the strength property.
         * 
         * @param value
         *     allowed object is
         *     {@link Double }
         *     
         */
        public void setStrength(double value) {
            this.strength = value;
        }
        
        /**
         * Gets the value of the endurance property.
         * 
         * @return
         *     possible object is
         *     {@link Double }
         *     
         */
        public double getEndurance() {
            return endurance;
        }

        /**
         * Sets the value of the endurance property.
         * 
         * @param value
         *     allowed object is
         *     {@link Double }
         *     
         */
        public void setEndurance(double value) {
            this.endurance = value;
        }
        
        /**
         * Gets the value of the agility property.
         * 
         * @return
         *     possible object is
         *     {@link Double }
         *     
         */
        public double getAgility() {
            return agility;
        }

        /**
         * Sets the value of the agility property.
         * 
         * @param value
         *     allowed object is
         *     {@link Double }
         *     
         */
        public void setAgility(double value) {
            this.agility = value;
        }


        /**
         * Defines a graphics element that when clicked will redirect the presentation to a specified slide.
         * 
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType&gt;
         *   &lt;complexContent&gt;
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *       &lt;choice&gt;
         *         &lt;element name="text" type="{SwengPWS}TextType" minOccurs="0"/&gt;
         *         &lt;element name="shape" type="{SwengPWS}ShapeType" minOccurs="0"/&gt;
         *         &lt;element name="polygon" type="{SwengPWS}PolygonType" minOccurs="0"/&gt;
         *         &lt;element name="image" type="{SwengPWS}ImageType" minOccurs="0"/&gt;
         *         &lt;element name="video" type="{SwengPWS}VideoType" minOccurs="0"/&gt;
         *       &lt;/choice&gt;
         *       &lt;attribute name="targetSlide" use="required" type="{http://www.w3.org/2001/XMLSchema}int" /&gt;
         *     &lt;/restriction&gt;
         *   &lt;/complexContent&gt;
         * &lt;/complexType&gt;
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "text",
            "shape",
            "polygon",
            "image",
            "video"
        })
        public static class Interactable {

            protected TextType text;
            protected ShapeType shape;
            protected PolygonType polygon;
            protected ImageType image;
            protected VideoType video;
            @XmlAttribute(name = "targetSlide", required = true)
            protected Integer targetSlide = null;

            /**
             * Gets the value of the text property.
             * 
             * @return
             *     possible object is
             *     {@link TextType }
             *     
             */
            public TextType getText() {
                return text;
            }

            /**
             * Sets the value of the text property.
             * 
             * @param value
             *     allowed object is
             *     {@link TextType }
             *     
             */
            public void setText(TextType value) {
                this.text = value;
            }

            /**
             * Gets the value of the shape property.
             * 
             * @return
             *     possible object is
             *     {@link ShapeType }
             *     
             */
            public ShapeType getShape() {
                return shape;
            }

            /**
             * Sets the value of the shape property.
             * 
             * @param value
             *     allowed object is
             *     {@link ShapeType }
             *     
             */
            public void setShape(ShapeType value) {
                this.shape = value;
            }

            /**
             * Gets the value of the polygon property.
             * 
             * @return
             *     possible object is
             *     {@link PolygonType }
             *     
             */
            public PolygonType getPolygon() {
                return polygon;
            }

            /**
             * Sets the value of the polygon property.
             * 
             * @param value
             *     allowed object is
             *     {@link PolygonType }
             *     
             */
            public void setPolygon(PolygonType value) {
                this.polygon = value;
            }

            /**
             * Gets the value of the image property.
             * 
             * @return
             *     possible object is
             *     {@link ImageType }
             *     
             */
            public ImageType getImage() {
                return image;
            }

            /**
             * Sets the value of the image property.
             * 
             * @param value
             *     allowed object is
             *     {@link ImageType }
             *     
             */
            public void setImage(ImageType value) {
                this.image = value;
            }

            /**
             * Gets the value of the video property.
             * 
             * @return
             *     possible object is
             *     {@link VideoType }
             *     
             */
            public VideoType getVideo() {
                return video;
            }

            /**
             * Sets the value of the video property.
             * 
             * @param value
             *     allowed object is
             *     {@link VideoType }
             *     
             */
            public void setVideo(VideoType value) {
                this.video = value;
            }

            /**
             * Gets the value of the targetSlide property.
             * 
             */
            public Integer getTargetSlide() {
                return targetSlide;
            }

            /**
             * Sets the value of the targetSlide property.
             * 
             */
            public void setTargetSlide(Integer value) {
                this.targetSlide = value;
            }

        }

    }

}
