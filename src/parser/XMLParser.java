package parser;

/*
 * Author : Oliver Rushton
 * Group: 4
 * Description: This module creates an object representation (XMLObject) of the xml file
 * 				and detects the type of objects in that XMLObject. The appropriate handlers
 * 				are then called for each object type. All the handlers are added onto a slide
 * 				and that slide is added to the ArrayList of slides called 'allSlides'.
 */

import java.io.File;
import java.util.ArrayList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import presentationViewer.ExceptionFx;
import presentationViewer.ImageFx;
import presentationViewer.MediaFx;
import presentationViewer.PolygonFx;
import presentationViewer.PresentationFx;
import presentationViewer.ShadingFx;
import presentationViewer.ShapeFx;
import presentationViewer.SlideContent;
import presentationViewer.SlideFx;
import presentationViewer.TextFx;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.paint.Color;
import parser.XMLDOM.Defaults;
import parser.XMLDOM.DocumentInfo;
import parser.XMLDOM.Slide.Interactable;

public class XMLParser {

	private ArrayList<SlideFx> allSlides;
	private XMLDOM xml;

	public XMLParser(String sourceXML) {
		/* Constructor retrieves the xml file, creates an object representation of the xml
		 * and fills that object with the corresponding metadata */
		try {
			File sourceFile = new File("src/res/xml/" + sourceXML);
			JAXBContext jaxbContext = JAXBContext.newInstance(XMLDOM.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			xml = (XMLDOM) jaxbUnmarshaller.unmarshal(sourceFile);
			findInstances(xml);
		} catch (JAXBException e) {
			System.out.println("The file could not be parsed");
			e.printStackTrace();
		}

	}
	//returns the default values for this presentation
	public Defaults getDefaults() {
		return xml.getDefaults();
	}

	//returns the document info for the presentation
	public DocumentInfo getDocumentInfo() {
		if (xml.getDocumentInfo().getTitle()== null)
			xml.getDocumentInfo().setTitle("Untitled");
		if (xml.getDocumentInfo().getAuthor()== null)
			xml.getDocumentInfo().setAuthor("Unknown Author");
		if (xml.getDocumentInfo().getVersion()== null)
			xml.getDocumentInfo().setVersion("??.??");
		if (xml.getDocumentInfo().getComment()== null)
			xml.getDocumentInfo().setComment("No Description");
		return xml.getDocumentInfo();
	}

	//returns the list of slides
	public ArrayList<SlideFx> getAllSlides() {
		return allSlides;
	}

	public static WorkoutInfo retrieveWorkoutInfo(String sourceXML) {
		File sourceFile = new File("src/res/xml/" + sourceXML);
		JAXBContext jaxbContext;
		XMLDOM temp = null;
		try {
			jaxbContext = JAXBContext.newInstance(XMLDOM.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			temp = (XMLDOM) jaxbUnmarshaller.unmarshal(sourceFile);
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		WorkoutInfo workout = new WorkoutInfo();
		for (int j = 0; j < temp.getSlide().size(); j++) {
			XMLDOM.Slide tempSlide = temp.getSlide().get(j);
			ExerciseInfo info = new ExerciseInfo(tempSlide.getExerciseName(), tempSlide.getSets(),
					tempSlide.getReps(), tempSlide.getPoints(),
					tempSlide.getSpeed(), tempSlide.getStrength(),
					tempSlide.getEndurance(), tempSlide.getAgility());
			workout.addExercise(info);
		}
		workout.setName(temp.getWorkoutName());
		workout.setDescription(temp.getDescription());
		workout.setAuthor(temp.getDocumentInfo().getAuthor());
		workout.setDuration(temp.getWorkoutDuration());
		workout.sumTotalPoints();
		workout.setFileName(sourceFile.getAbsolutePath());

		return workout;
	}

	public static ArrayList<WorkoutInfo> retrieveAllWorkoutInfo() {
		File folder = new File("src/res/xml");
		File[] listOfFiles = folder.listFiles();
		ArrayList<WorkoutInfo> output = new ArrayList<WorkoutInfo>();


		for(int i = 0; i < new File("src/res/xml").listFiles().length; i++) {
			try {
				XMLDOM temp;
				if (listOfFiles[i].isFile() && listOfFiles[i].exists()) {
					File sourceFile = listOfFiles[i];
					System.out.println("Parsing: "  + sourceFile.getAbsolutePath());
					JAXBContext jaxbContext = JAXBContext.newInstance(XMLDOM.class);
					Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
					temp = (XMLDOM) jaxbUnmarshaller.unmarshal(sourceFile);
					WorkoutInfo workout = new WorkoutInfo();
					for (int j = 0; j < temp.getSlide().size(); j++) {
						XMLDOM.Slide tempSlide = temp.getSlide().get(j);
						ExerciseInfo info = new ExerciseInfo(tempSlide.getExerciseName(), tempSlide.getSets(),
								tempSlide.getReps(), tempSlide.getPoints(),
								tempSlide.getSpeed(), tempSlide.getStrength(),
								tempSlide.getEndurance(), tempSlide.getAgility());

								workout.addExercise(info);
					}
					workout.setName(temp.getDocumentInfo().getTitle());
					workout.setDuration(temp.getWorkoutDuration());
					workout.setDescription(temp.getDescription());
					workout.setAuthor(temp.getDocumentInfo().getAuthor());
					workout.sumTotalPoints();
					workout.setFileName(sourceFile.getAbsolutePath());
					output.add(workout);
				}

			} catch (JAXBException e) {
				System.out.println("WorkoutInfo could not be obtained");
				e.printStackTrace();
			}
		}
		return output;
	}
	/* Detects object type and calls the constructor for that object
	 * The object handler is then returned and added to slide content */
	private void findInstances(XMLDOM xml) {
		allSlides = new ArrayList<SlideFx>();
		if (!VerifyXML.loadDefaults(xml.getDefaults())) {
			//TODO load MegaFit defaults from style sheet
		}
		for (int i = 0; i < xml.getSlide().size(); i++) {
			ArrayList<SlideContent> slideContent = new ArrayList<SlideContent>();
			VerifyXML.loadSlideID(xml.getSlide().get(i).getSlideID());
			for(int j = 0; j < xml.getSlide().get(i).getAllContent().size(); j++) {
				Object object = xml.getSlide().get(i).getAllContent().get(j);

				if (object instanceof Interactable) {
					Interactable intract = (Interactable) object;
					if (intract.getText() != null) {
						if (VerifyXML.verifyText(intract.getText()))
							slideContent.add(createText(intract.getText(),intract.getTargetSlide()));
					} else if (intract.getShape() != null) {
						if (VerifyXML.verifyShape(intract.getShape()))
							slideContent.add(createShape(intract.getShape(),intract.getTargetSlide()));
					} else if (intract.getPolygon() != null) {
						if (VerifyXML.verifyPolygon(intract.getPolygon()))
							slideContent.add(createPolygon(intract.getPolygon(),intract.getTargetSlide()));
					} else if (intract.getImage() != null) {
						if (VerifyXML.verifyImage(intract.getImage()))
							slideContent.add(createImage(intract.getImage(),intract.getTargetSlide()));
					} else if (intract.getVideo() != null) {
						if (VerifyXML.verifyVideo(intract.getVideo()))
							slideContent.add(createVideo(intract.getVideo(),intract.getTargetSlide()));
					}
				} else if (object instanceof TextType) {
					if (VerifyXML.verifyText(((TextType)object)))
						slideContent.add(createText(((TextType)object),null));
				} else if (object instanceof ShapeType) {
					if (VerifyXML.verifyShape(((ShapeType)object)))
						slideContent.add(createShape(((ShapeType)object),null));
				} else if (object instanceof PolygonType) {
					if (VerifyXML.verifyPolygon(((PolygonType)object)))
						slideContent.add(createPolygon(((PolygonType)object),null));
				} else if (object instanceof ImageType) {
					if (VerifyXML.verifyImage(((ImageType)object)))
						slideContent.add(createImage(((ImageType)object),null));
				} else if (object instanceof VideoType) {
					if (VerifyXML.verifyVideo(((VideoType)object)))
						slideContent.add(createVideo(((VideoType)object),null));
				} else if (object instanceof AudioType) {
					if (VerifyXML.verifyAudio(((AudioType)object)))
						slideContent.add(createAudio((AudioType)object));
				}

			}

			//Calculate duration if not specified
			Integer slideDuration = 0;
			if (xml.getSlide().get(i).getDuration() != null)
				slideDuration = xml.getSlide().get(i).getDuration();
			else
				slideDuration = calculateDuration(slideContent);

			//add content to slide
			SlideFx slide = new SlideFx(slideDuration,slideContent,xml.getSlide().get(i).getNextSlide(),
					xml.getSlide().get(i).getSlideID(),
					retrieveColour(xml.getSlide().get(i).getBackgroundColour(), "backgroundColour"));

			//add slide to xml
			allSlides.add(i,slide);

		}
	}

	private Integer calculateDuration(ArrayList<SlideContent> slideContent) {
		Integer duration = 0;
		for (SlideContent i : slideContent) {
			if (duration < i.getEndTime())
				duration = i.getEndTime();
		}
		return duration;
	}

	private MediaFx createAudio(AudioType audio) {
		double duration = PresentationFx.durationUnconfirmed;
		if (audio.getDuration() != null) {
			duration = audio.getDuration();
		}
		System.out.println("Audio Duration is: " + duration);
		MediaFx audH = new MediaFx(audio.getStarttime(), (int) duration, audio.getSourceFile(), audio.isLoop());
		return audH;
	}
	/* If the object is interactable, need to add the associated targetLoc to the handler 
	 * Else set targetLoc to null */
	private MediaFx createVideo(VideoType video, Integer target) {
		int duration = PresentationFx.durationUnconfirmed;
		if (video.getDuration() != null) {
			duration = video.getDuration();
		}
		MediaFx vidH = new MediaFx(video.getStarttime(), (int) duration, video.getXstart(), video.getYstart(),
				video.getWidth(), video.getHeight(), video.getSourceFile(), video.isLoop(),
				target);

		return vidH;
	}

	private TextFx createText(TextType text, Integer target) {
		TextFx txtH = new TextFx(text.getStarttime(), text.getDuration(), text.getXstart(), text.getYstart(),
				text.getSourceFile(), text.getFont(), text.getFontsize(), retrieveColour(text.getFontcolour(), "fontColour"),
				target);

		return txtH;
	}

	private ImageFx createImage(ImageType image, Integer target) {
		ImageFx imgH = new ImageFx(image.getStarttime(), image.getDuration(),image.getXstart(),
				image.getYstart(), image.getWidth(), image.getHeight(), image.getSourceFile(),
				target);

		return imgH;
	}

	private ShapeFx createShape(ShapeType shape, Integer target) {
		ShapeFx shpH = new ShapeFx(shape.getStarttime(), shape.getDuration(),shape.getXstart(),
				shape.getYstart(), shape.getWidth(), shape.getHeight(), shape.getType(),
				retrieveColour(shape.getLineColour(), "lineColour"), retrieveColour(shape.getFillColour(), "fillColour"),
				retrieveShading(shape.getShading()), target);
		return shpH;
	}

	private PolygonFx createPolygon(PolygonType polygon, Integer target) {
		PolygonFx plyH = new PolygonFx(polygon.getStarttime(),polygon.getDuration(),polygon.getSourceFile(), retrieveShading(polygon.getShading()),
				retrieveColour(polygon.getLineColour(), "lineColour"), retrieveColour(polygon.getFillColour(), "fillColour"),
				target);

		return plyH;
	}
	//calls shading (gradient) handler
	private ShadingFx retrieveShading(ShadingType gradient) {
		// gradient.getX1() != gradient.getX2() && gradient.getY1() != gradient.getY2()
		// && (gradient.getColour1() != null && gradient.getColour2() != null)
		ShadingFx shading = null;
		if (gradient != null) {
			shading = new ShadingFx(gradient.getX1(), gradient.getY1(), gradient.getX2(),
					gradient.getY2(), retrieveColour(gradient.getColour1(), "gradientColour"), retrieveColour(gradient.getColour1(), "gradientColour"));
		}
		return shading;
	}

	private Color retrieveColour(String hexCode, String colourType) {
		System.out.println("String color code is: " + hexCode);
		Color colour = null;
		if (hexCode != null) {
			try {
				colour = Color.web(hexCode);
			} catch (IllegalArgumentException e) {
				ExceptionFx ex = new ExceptionFx(e, AlertType.WARNING, "Colour Code Execption", "Incorrect Colour code",
						"The colour code: " + hexCode + " contains an illegal numeric value.\nThe Default "
								+ colourType + " will be used to create the object instead.");
				ex.show();
				colour = getDefaultColour(colourType);
			}
		}
		return colour;
	}

	private Color getDefaultColour(String colourType) {
		Color colour = null;

		switch (colourType) {
			case "backgroundColour":
				//colour = Color.web(getDefaults().getBackgroundColour());
				break;
			case "lineColour":
				colour = Color.web(getDefaults().getLineColour());
				break;
			case "fillColour":
				colour = Color.web(getDefaults().getFillColour());
				break;
			case "gradientColour":
				colour = Color.web(getDefaults().getFillColour());
				break;
			case "fontColour":
				colour = Color.web(getDefaults().getFontColour());
				break;
		}

		return colour;
	}

}
