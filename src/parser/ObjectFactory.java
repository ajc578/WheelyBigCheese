//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.05.27 at 02:50:18 PM BST 
//


package parser;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the newParser package. 
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

    private final static QName _PresentationSlideInteractableTextB_QNAME = new QName("", "b");
    private final static QName _PresentationSlideInteractableTextI_QNAME = new QName("", "i");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: newParser
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Presentation }
     * 
     */
    public Presentation createPresentation() {
        return new Presentation();
    }

    /**
     * Create an instance of {@link Presentation.Slide }
     * 
     */
    public Presentation.Slide createPresentationSlide() {
        return new Presentation.Slide();
    }

    /**
     * Create an instance of {@link Presentation.Slide.Interactable }
     * 
     */
    public Presentation.Slide.Interactable createPresentationSlideInteractable() {
        return new Presentation.Slide.Interactable();
    }

    /**
     * Create an instance of {@link Presentation.Slide.Interactable.Polygon }
     * 
     */
    public Presentation.Slide.PolygonType createPresentationSlideInteractablePolygon() {
        return new Presentation.Slide.PolygonType();
    }

    /**
     * Create an instance of {@link Presentation.Slide.Interactable.Shape }
     * 
     */
    public Presentation.Slide.ShapeType createPresentationSlideInteractableShape() {
        return new Presentation.Slide.ShapeType();
    }

    /**
     * Create an instance of {@link Presentation.Slide.Polygon }
     * 
     */
    public Presentation.Slide.PolygonType createPresentationSlidePolygon() {
        return new Presentation.Slide.PolygonType();
    }

    /**
     * Create an instance of {@link Presentation.Slide.Shape }
     * 
     */
    public Presentation.Slide.ShapeType createPresentationSlideShape() {
        return new Presentation.Slide.ShapeType();
    }

    /**
     * Create an instance of {@link Presentation.DocumentInfo }
     * 
     */
    public Presentation.DocumentInfo createPresentationDocumentInfo() {
        return new Presentation.DocumentInfo();
    }

    /**
     * Create an instance of {@link Presentation.Defaults }
     * 
     */
    public Presentation.Defaults createPresentationDefaults() {
        return new Presentation.Defaults();
    }

    /**
     * Create an instance of {@link Presentation.Slide.Text }
     * 
     */
    public Presentation.Slide.TextType createPresentationSlideText() {
        return new Presentation.Slide.TextType();
    }

    /**
     * Create an instance of {@link Presentation.Slide.Image }
     * 
     */
    public Presentation.Slide.ImageType createPresentationSlideImage() {
        return new Presentation.Slide.ImageType();
    }

    /**
     * Create an instance of {@link Presentation.Slide.Video }
     * 
     */
    public Presentation.Slide.VideoType createPresentationSlideVideo() {
        return new Presentation.Slide.VideoType();
    }

    /**
     * Create an instance of {@link Presentation.Slide.Audio }
     * 
     */
    public Presentation.Slide.AudioType createPresentationSlideAudio() {
        return new Presentation.Slide.AudioType();
    }

    /**
     * Create an instance of {@link Presentation.Slide.Interactable.Text }
     * 
     */
    public Presentation.Slide.TextType createPresentationSlideInteractableText() {
        return new Presentation.Slide.TextType();
    }

    /**
     * Create an instance of {@link Presentation.Slide.Interactable.Image }
     * 
     */
    public Presentation.Slide.ImageType createPresentationSlideInteractableImage() {
        return new Presentation.Slide.ImageType();
    }

    /**
     * Create an instance of {@link Presentation.Slide.Interactable.Video }
     * 
     */
    public Presentation.Slide.VideoType createPresentationSlideInteractableVideo() {
        return new Presentation.Slide.VideoType();
    }

    /**
     * Create an instance of {@link Presentation.Slide.Interactable.Polygon.Shading }
     * 
     */
    public Presentation.Slide.ShadingType createPresentationSlideInteractablePolygonShading() {
        return new Presentation.Slide.ShadingType();
    }

    /**
     * Create an instance of {@link Presentation.Slide.Interactable.Shape.Shading }
     * 
     */
    public Presentation.Slide.ShadingType createPresentationSlideInteractableShapeShading() {
        return new Presentation.Slide.ShadingType();
    }

    /**
     * Create an instance of {@link Presentation.Slide.Polygon.Shading }
     * 
     */
    public Presentation.Slide.ShadingType createPresentationSlidePolygonShading() {
        return new Presentation.Slide.ShadingType();
    }

    /**
     * Create an instance of {@link Presentation.Slide.Shape.Shading }
     * 
     */
    public Presentation.Slide.ShadingType createPresentationSlideShapeShading() {
        return new Presentation.Slide.ShadingType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "b", scope = Presentation.Slide.TextType.class)
    public JAXBElement<String> createPresentationSlideInteractableTextB(String value) {
        return new JAXBElement<String>(_PresentationSlideInteractableTextB_QNAME, String.class, Presentation.Slide.TextType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "i", scope = Presentation.Slide.TextType.class)
    public JAXBElement<String> createPresentationSlideInteractableTextI(String value) {
        return new JAXBElement<String>(_PresentationSlideInteractableTextI_QNAME, String.class, Presentation.Slide.TextType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "b", scope = Presentation.Slide.TextType.class)
    public JAXBElement<String> createPresentationSlideTextB(String value) {
        return new JAXBElement<String>(_PresentationSlideInteractableTextB_QNAME, String.class, Presentation.Slide.TextType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "i", scope = Presentation.Slide.TextType.class)
    public JAXBElement<String> createPresentationSlideTextI(String value) {
        return new JAXBElement<String>(_PresentationSlideInteractableTextI_QNAME, String.class, Presentation.Slide.TextType.class, value);
    }

}
