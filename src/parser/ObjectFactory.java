//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.03.25 at 01:28:05 PM GMT 
//


package parser;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the parser package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: parser
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link XMLDOM }
     * 
     */
    public XMLDOM createPresentation() {
        return new XMLDOM();
    }

    /**
     * Create an instance of {@link XMLDOM.Slide }
     * 
     */
    public XMLDOM.Slide createPresentationSlide() {
        return new XMLDOM.Slide();
    }

    /**
     * Create an instance of {@link XMLDOM.DocumentInfo }
     * 
     */
    public XMLDOM.DocumentInfo createPresentationDocumentInfo() {
        return new XMLDOM.DocumentInfo();
    }

    /**
     * Create an instance of {@link XMLDOM.Defaults }
     * 
     */
    public XMLDOM.Defaults createPresentationDefaults() {
        return new XMLDOM.Defaults();
    }

    /**
     * Create an instance of {@link TextType }
     * 
     */
    public TextType createTextType() {
        return new TextType();
    }

    /**
     * Create an instance of {@link ShadingType }
     * 
     */
    public ShadingType createShadingType() {
        return new ShadingType();
    }

    /**
     * Create an instance of {@link ShapeType }
     * 
     */
    public ShapeType createShapeType() {
        return new ShapeType();
    }

    /**
     * Create an instance of {@link PolygonType }
     * 
     */
    public PolygonType createPolygonType() {
        return new PolygonType();
    }

    /**
     * Create an instance of {@link ImageType }
     * 
     */
    public ImageType createImageType() {
        return new ImageType();
    }

    /**
     * Create an instance of {@link VideoType }
     * 
     */
    public VideoType createVideoType() {
        return new VideoType();
    }

    /**
     * Create an instance of {@link AudioType }
     * 
     */
    public AudioType createAudioType() {
        return new AudioType();
    }

    /**
     * Create an instance of {@link XMLDOM.Slide.Interactable }
     * 
     */
    public XMLDOM.Slide.Interactable createPresentationSlideInteractable() {
        return new XMLDOM.Slide.Interactable();
    }

}