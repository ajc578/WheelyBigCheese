package parser;


import java.io.File;
import java.util.ArrayList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import handlers.ImageFx;
import handlers.MediaFx;
import handlers.PolygonFx;
import handlers.PresentationFx;
import handlers.ShadingFx;
import handlers.ShapeFx;
import handlers.SlideContent;
import handlers.SlideFx;
import handlers.TextFx;
import javafx.scene.paint.Color;
import parser.Presentation.Slide.Audio;
import parser.Presentation.Slide.Image;
import parser.Presentation.Slide.Interactable;
import parser.Presentation.Slide.Polygon;
import parser.Presentation.Slide.Polygon.Shading;
import parser.Presentation.Slide.Shape;
import parser.Presentation.Slide.Text;
import parser.Presentation.Slide.Video;

public class XMLParser {
	
	private ArrayList<SlideFx> allSlides;
	
	public XMLParser(String sourceXML) {
		
		try {
			File sourceFile = new File("src/res/xml/" + sourceXML);
			JAXBContext jaxbContext = JAXBContext.newInstance(Presentation.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			Presentation presentation = (Presentation) jaxbUnmarshaller.unmarshal(sourceFile);
			findInstances(presentation);
		} catch (JAXBException e) {
			System.out.println("The file could not be parsed");
			e.printStackTrace();
		}
		
	}
	
	public ArrayList<SlideFx> getAllSlides() {
		return allSlides;
	}
	
	private void findInstances(Presentation presentation) {
		allSlides = new ArrayList<SlideFx>();
		ArrayList<SlideContent> slideContent = new ArrayList<SlideContent>();
		for (int i = 0; i < presentation.getSlide().size(); i++) {
			for(int j = 0; j < presentation.getSlide().get(i).getContentsOfSlide().size(); j++) {
				Object object = presentation.getSlide().get(i).getContentsOfSlide().get(j);
				if (object instanceof Text) {
					slideContent.add(createText(object));
				} else if (object instanceof Shape) {
					slideContent.add(createShape(object));
				} else if (object instanceof Polygon) {
					slideContent.add(createPolygon(object));
				} else if (object instanceof Image) {
					slideContent.add(createImage(object));
				} else if (object instanceof Video) {
					slideContent.add(createVideo(object));
				} else if (object instanceof Audio) {
					slideContent.add(createAudio(object));
				} else if (object instanceof Interactable) {
					Interactable intract = (Interactable) object;
					if (intract.getText() != null) {
						slideContent.add(createText(object));
					} else if (intract.getShape() != null) {
						slideContent.add(createShape(object));
					} else if (intract.getPolygon() != null) {
						slideContent.add(createPolygon(object));
					} else if (intract.getImage() != null) {
						slideContent.add(createImage(object));
					} else if (intract.getVideo() != null) {
						slideContent.add(createVideo(object));
					} 
				}

			}
			//add content to slide
			SlideFx slide = new SlideFx(presentation.getSlide().get(i).getDuration(),
									slideContent,presentation.getSlide().get(i).getNextSlide(), 
									presentation.getSlide().get(i).getSlideID(), 
									retrieveColour(presentation.getSlide().get(i).getBackgroundColour()));
			
			//add slide to presentation
			allSlides.add(slide);
		}
	}
	
	private MediaFx createAudio(Object object) {
		Audio audio = (Audio) object;
		MediaFx audH = new MediaFx(audio.getStarttime(), audio.getDuration(), audio.getSourceFile(), audio.isLoop());
		return audH;
	}
	
	private MediaFx createVideo(Object object) {
		Video video = null;
		if (object instanceof Interactable) {
			video = ((Interactable) object).getVideo();
		} else {
			video = (Video) object;
		}
		MediaFx vidH = new MediaFx(video.getStarttime(), video.getDuration(), video.getXstart(), video.getYstart(),
					video.getWidth(), video.getHeight(), video.getSourceFile(), video.isLoop(),
					object instanceof Interactable ? ((Interactable) object).getTargetSlide() : null);
		
		return vidH;
	}
	
	private TextFx createText(Object object) {
		Text text = null;
		if (object instanceof Interactable) {
			text = ((Interactable) object).getText();
		} else {
			text = (Text) object;
		}
		TextFx txtH = new TextFx(text.getStarttime(), text.getDuration(), text.getXstart(), text.getYstart(), 
					text.getContent().toString(), text.getFont(), text.getFontsize(), retrieveColour(text.getFontcolour()),-1);
		
		return txtH;
	}
	
	private ImageFx createImage(Object object) {
		Image image = null;
		if (object instanceof Interactable) {
			image = ((Interactable) object).getImage();
		} else {
			image = (Image) object;
		}
		ImageFx imgH = new ImageFx(image.getStarttime(), image.getDuration(),image.getXstart(), 
					image.getYstart(), image.getWidth(), image.getHeight(), image.getSourceFile(),
					object instanceof Interactable ? ((Interactable) object).getTargetSlide() : null);
		
		return imgH;
	}
	
	private ShapeFx createShape(Object object) {
		Shape shape = null;
		if (object instanceof Interactable) {
			shape = ((Interactable) object).getShape();
		} else {
			shape = (Shape) object;
		}
		ShapeFx shpH = new ShapeFx(shape.getStarttime(), shape.getDuration(),shape.getXstart(), 
					shape.getYstart(), shape.getWidth(), shape.getHeight(), shape.getType(),
					retrieveColour(shape.getLineColour()), retrieveColour(shape.getFillColour()),
					retrieveShading(shape.getShading()), 
					object instanceof Interactable ? ((Interactable) object).getTargetSlide() : null);
		return shpH;
	}
	
	private PolygonFx createPolygon(Object object) {
		Polygon polygon = null;
		if (object instanceof Interactable) {
			polygon = ((Interactable) object).getPolygon();
		} else {
			polygon = (Polygon) object;
		}
		PolygonFx plyH = new PolygonFx(polygon.getStarttime(),polygon.getDuration(),polygon.getSourceFile(), retrieveShading(polygon.getShading()),
				retrieveColour(polygon.getLineColour()), retrieveColour(polygon.getFillColour()),
				object instanceof Interactable ? ((Interactable) object).getTargetSlide() : null);
		
		return plyH;
	}
	
	private static ShadingFx retrieveShading(Shading gradient) {
		ShadingFx shading = new ShadingFx(gradient.getX1(), gradient.getY1(), gradient.getX2(), 
				gradient.getY2(), retrieveColour(gradient.getColour1()), retrieveColour(gradient.getColour1()));
		return shading;	
	}
	
	private static Color retrieveColour(byte[] hexCode) {
		Color colour = null;
		String stringCode = null;
		
		for (int i = 0; i < hexCode.length;i++ ) {
			if (Integer.toHexString(hexCode[i] & 0xFF).matches("0")) {
				if (stringCode == null) {
					stringCode = "00";
				} else {
					stringCode += ("00");
				}
			} else {
				if (stringCode == null) {
					stringCode = Integer.toHexString(hexCode[i] & 0xFF);
				} else {
					stringCode += (Integer.toHexString(hexCode[i] & 0xFF));
				}
			}
		}
		colour = Color.web(stringCode);
		
		System.out.println(stringCode);
		
		return colour;
	}

	/*public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		try {
			
			File file = new File("src/res/xml/test.xml");
			JAXBContext jaxbContext = JAXBContext.newInstance(Presentation.class);
			
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			Presentation presentation = (Presentation) jaxbUnmarshaller.unmarshal(file);
			if (presentation.getSlide().get(0).getContentsOfSlide().get(4) instanceof Video) {
				System.out.println(presentation.getSlide().get(0).getContentsOfSlide().size());
				System.out.println(((Text)presentation.getSlide().get(0).getContentsOfSlide().get(0)).getFontcolour());
			}
			
			byte[] bytes = ((Text)presentation.getSlide().get(0).getContentsOfSlide().get(0)).getFontcolour();
			Color colour = retrieveColour(bytes);
			System.out.println(colour);
			
			Marshaller marshaller = jaxbContext.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			marshaller.marshal(presentation, System.out);
			
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		
	}*/
	

}
