package parser;

import java.io.ByteArrayInputStream;

/*
 * Author : Oliver Rushton
 * Group: 4
 * Description: This module creates an object representation (XMLObject) of the xml file
 * 				and detects the type of objects in that XMLObject. The appropriate handlers
 * 				are then called for each object type. All the handlers are added onto a slide
 * 				and that slide is added to the ArrayList of slides called 'allSlides'.
 */

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import javafx.scene.control.Alert.AlertType;
import javafx.scene.paint.Color;
import parser.Presentation.Defaults;
import parser.Presentation.DocumentInfo;
import parser.Presentation.Slide.AudioType;
import parser.Presentation.Slide.ImageType;
import parser.Presentation.Slide.Interactable;
import parser.Presentation.Slide.PolygonType;
import parser.Presentation.Slide.ShadingType;
import parser.Presentation.Slide.ShapeType;
import parser.Presentation.Slide.TextType;
import parser.Presentation.Slide.VideoType;
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
import userInterface.HistoryAnalyser;
/**
 * This Class implements the JAXB parser for unmarshalling data from the workout/presentation
 * XML's to the Presentation class - the object representation of the XML's. This Class
 * also provides retrieval methods for obtaining the basic information that describes a
 * workout or exercise that can be used in the {@link HistoryAnalyser}. 
 * <p>
 * The Slide Objects (e.g. AudioType, VideoType, etc... - from the Presentation.Slide class)
 * are then passed to the corresponding handlers from the <code>presentationViewer</code> package 
 * which create the javaFX content to be displayed in a presentation/workout, managed by the 
 * {@link PresentationFx} class.
 *
 * <p> <STRONG> Developed by </STRONG> <p>
 * Oliver Rushton
 * <p> <STRONG> Tested by </STRONG> <p>
 * Oliver Rushton
 * <p> <STRONG> Developed for </STRONG> <p>
 * BOSS
 * @author Oliver Rushton
 */
public class XMLParser {

	private ArrayList<SlideFx> allSlides;
	private Presentation xml;
	/**
	 * Calls the JAXB parser to load the input <code>sourceXML</code> file.
	 * <p>
	 * Then calls the <code>findInstances</code> method to pass all the object data to
	 * the handlers to create the slide content.
	 * 
	 * @param sourceXML the name of the presentation XML to use to construct the presentation.
	 * @see #findInstances(Presentation)
	 * @see #cleantextTags(File)
	 * @see JAXBContext
	 * @see Unmarshaller
	 * @see Presentation
	 */
	public XMLParser(String sourceXML) {
		/* Constructor retrieves the xml file, creates an object representation of the xml
		 * and fills that object with the corresponding metadata */
		try {
			File sourceFile = new File("src/res/xml/" + sourceXML);
			JAXBContext jaxbContext = JAXBContext.newInstance(Presentation.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			//changes the bold and italic tags from <> to %% so they can be recognised by the jaxb parser
			//then unmarshal the cleaned file into the xml field.
			xml = (Presentation) jaxbUnmarshaller.unmarshal(cleantextTags(sourceFile));
			//set up the handlers
			findInstances(xml);
		} catch (JAXBException e) {
			System.out.println("The file could not be parsed");
			
			e.printStackTrace();
		}

	}
	/**
	 * Gets the default attributes from the xml (presentation) field.
	 * @return The defaults values for a xml presentation.
	 * @see Defaults
	 */
	//returns the default values for this presentation
	public Defaults getDefaults() {
		return xml.getDefaults();
	}
	/**
	 * Gets the information describing the presentation.
	 * @return the document information specific to the presentation.
	 * @see DocumentInfo
	 */
	//returns the document info for the presentation
	public DocumentInfo getDocumentInfo() {
		//sets default values if the current values are null.
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
	/**
	 * Gets the array list of Slides for the presentation.
	 * <p>
	 * Each slide contains the slide objects created by the handlers.
	 * @return an array list of the slides for a presentation
	 */
	//returns the list of slides
	public ArrayList<SlideFx> getAllSlides() {
		return allSlides;
	}
	/**
	 * Parses the source xml into a <code>Presentation</code> object and then 
	 * cycles through each slide to obtain the exercise related information specific
	 * to MegaFit workouts. The <code>ExerciseInfo</code>'s are then added to a
	 * <code>WorkoutInfo</code> which stores all the workout related information 
	 * for a workout presentation.
	 * 
	 * @param sourceXML the source xml file to retrieve the workout information from.
	 * @return A WorkoutInfo object containing all the workout related information from 
	 * 		   <code>sourceFile</code>.
	 * @see ExerciseInfo
	 * @see WorkoutInfo
	 */
	public static WorkoutInfo retrieveWorkoutInfo(String sourceXML) {
		File sourceFile = new File("src/res/xml/" + sourceXML);
		JAXBContext jaxbContext;
		Presentation temp = null;
		try {
			//parse the xml file into the Presentation object
			jaxbContext = JAXBContext.newInstance(Presentation.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			temp = (Presentation) jaxbUnmarshaller.unmarshal(sourceFile);
		} catch (JAXBException e) {
			return null;
		}
		WorkoutInfo workout = new WorkoutInfo();

		if (sourceFile.toString().toUpperCase().endsWith("WORKOUT.XML")) {
			for (int j = 0; j < temp.getSlide().size(); j++) {
				//get the next slide
				Presentation.Slide tempSlide = temp.getSlide().get(j);
				//retrieve the exercise information from each slide and construct and ExerciseInfo object
				ExerciseInfo info = new ExerciseInfo(tempSlide.getExerciseName(), tempSlide.getSets(),
						tempSlide.getReps(), tempSlide.getPoints(),
						tempSlide.getSpeed(), tempSlide.getStrength(),
						tempSlide.getEndurance(), tempSlide.getAgility());
				//adds the exercise to the array list in the WorkoutInfo object
				workout.addExercise(info);
			}
		}
		//set the remaining details for the workout
		workout.setName(temp.getDocumentInfo().getTitle());
		workout.setDescription(temp.getDocumentInfo().getComment());
		workout.setAuthor(temp.getDocumentInfo().getAuthor());
		workout.setDuration(temp.getWorkoutDuration());
		//sum the points gained from each slide
		workout.sumTotalPoints();
		workout.setFileName(sourceFile.getAbsolutePath());

		return workout;
	}
	/**
	 * Obtains all the workout information from each file stored in the workout 
	 * files directory - Only obtains workout information from the XML files
	 * ending in "WORKOUT.xml". See the <code>retrieveWorkoutInfo</code> for more 
	 * details.
	 * 
	 * @return A list of all the workout information for each workout XML in the workout
	 * 		   directory.
	 * @see #retrieveWorkoutInfo(String)
	 * @see WorkoutInfo
	 * @see ExerciseInfo
	 */
	public static ArrayList<WorkoutInfo> retrieveAllWorkoutInfo() {
		File folder = new File("src/res/xml");
		File[] listOfFiles = folder.listFiles();
		ArrayList<WorkoutInfo> output = new ArrayList<WorkoutInfo>();
		//iterate through each file in the workout directory
		for(File sourceFile: listOfFiles) {
			try {
				Presentation temp;
				//check to make sure only workout presentations or PWS presentations are iterated through
				if (!sourceFile.toString().toUpperCase().endsWith("EXERCISE.XML") &&
						!sourceFile.toString().toUpperCase().endsWith("REST.XML")) {
					//check that the file is not null
					if (cleantextTags(sourceFile) != null) {
						//parse the XML workout to the Presentation Object
						JAXBContext jaxbContext = JAXBContext.newInstance(Presentation.class);
						Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
						temp = (Presentation) jaxbUnmarshaller.unmarshal(sourceFile);
						WorkoutInfo workout = new WorkoutInfo();

						/**
                         * If it is a MegaFit workout, build the exercise list with the optional
						 * xml elements.
						 */
						if (sourceFile.toString().toUpperCase().endsWith("WORKOUT.XML")) {
							for (int j = 0; j < temp.getSlide().size(); j++) {
								Presentation.Slide tempSlide = temp.getSlide().get(j);
								//retrieve the exercise information from each slide and construct and ExerciseInfo object
								ExerciseInfo info = new ExerciseInfo(tempSlide.getExerciseName(), tempSlide.getSets(),
										tempSlide.getReps(), tempSlide.getPoints(),
										tempSlide.getSpeed(), tempSlide.getStrength(),
										tempSlide.getEndurance(), tempSlide.getAgility());
								//add the exercise information to the list in the workout info
								workout.addExercise(info);
							}
						}
						//set the remaining attributes of the workout info
						workout.setName(temp.getDocumentInfo().getTitle());
						workout.setDuration(temp.getWorkoutDuration());
						workout.setDescription(temp.getDocumentInfo().getComment());
						workout.setAuthor(temp.getDocumentInfo().getAuthor());
						workout.setFileName(sourceFile.getAbsolutePath());
						//sum all the points earned from each exercise
						workout.sumTotalPoints();
						//add the workout information to the output list
						output.add(workout);
					}
				}
			} catch (JAXBException e) {
				return null;
			}
		}
		return output;
	}
	/**
	 * Detects object type and calls the constructor for that object.
	 * The object handler is then returned and added to slide content.
	 * <p>
	 * The <code>VerifyXML</code> class is used to check whether the input data to
	 * the handlers is correct.
	 * 
	 * @param xml the Presentation - Object representation of the xml
	 * @see VerifyXML
	 */
	private void findInstances(Presentation xml) {
		allSlides = new ArrayList<SlideFx>();
		//iterate through each Slide
		for (int i = 0; i < xml.getSlide().size(); i++) {
			ArrayList<SlideContent> slideContent = new ArrayList<SlideContent>();
			VerifyXML.loadSlideID(xml.getSlide().get(i).getSlideID());
			VerifyXML.loadDefaults(xml.getDefaults());
			//iterate through each object on that slide
			for(int j = 0; j < xml.getSlide().get(i).getAllContent().size(); j++) {
				Object object = xml.getSlide().get(i).getAllContent().get(j);
				
				if (object instanceof Interactable) {
					//type cast to Interactable and then find the corresponding slide object 
					//the Interactable if referring to.
					//Then verify that that object contains all the necessary data before 
					//passing to the handlers.
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
					//Then check if the object is any of the other slide content objects
					//and add the constructed handlers to the slide content list.
				} else if (object instanceof TextType) {
					if (VerifyXML.verifyText(((TextType)object)))
						slideContent.add(createText(((TextType)object),-3));
				} else if (object instanceof ShapeType) {
					if (VerifyXML.verifyShape(((ShapeType)object)))
						slideContent.add(createShape(((ShapeType)object),-3));
				} else if (object instanceof PolygonType) {
					if (VerifyXML.verifyPolygon(((PolygonType)object)))
						slideContent.add(createPolygon(((PolygonType)object),-3));
				} else if (object instanceof ImageType) {
					if (VerifyXML.verifyImage(((ImageType)object)))
						slideContent.add(createImage(((ImageType)object),-3));
				} else if (object instanceof VideoType) {
					if (VerifyXML.verifyVideo(((VideoType)object)))
						slideContent.add(createVideo(((VideoType)object),-3));
				} else if (object instanceof AudioType) {
					if (VerifyXML.verifyAudio(((AudioType)object)))
						slideContent.add(createAudio((AudioType)object));
				}

			}

			//Set Duration and destination to escape values if not specified
			Integer slideDuration = 0;
			if (xml.getSlide().get(i).getDuration() != null)
				slideDuration = xml.getSlide().get(i).getDuration();
			else
				slideDuration = -1;
			Integer nextSlide = 0;
			if (xml.getSlide().get(i).getNextSlide() != null)
				nextSlide = xml.getSlide().get(i).getNextSlide();
			else
				nextSlide = -1;

			//add content to slide
			SlideFx slide = new SlideFx(slideDuration,slideContent,nextSlide,
					xml.getSlide().get(i).getSlideID(),
					retrieveColour(xml.getSlide().get(i).getBackgroundColour(), "backgroundColour"));

			//add slide to xml
			allSlides.add(i,slide);

		}
	}

	/**
	 * Constructs the audio (player) handler from the metadata in the input AudioType.
	 * 
	 * @param audio an object containing the information needed to construct an audio handler.
	 * @return The audio handler - <code>MediaFx</code>.
	 * @see MediaFx
	 * @see AudioType
	 */
	private MediaFx createAudio(AudioType audio) {
		//sets the default duration to unconfirmed
		double duration = PresentationFx.durationUnconfirmed;
		if (audio.getDuration() != null) {
			//updates the duration to the actual value if set in AudioType object
			duration = audio.getDuration();
		}
		//construct audio player
		MediaFx audH = new MediaFx(audio.getStarttime(), (int) duration, audio.getSourceFile(), audio.isLoop());
		return audH;
	}
	/**
	 * 
	 * Constructs the video (player) handler from the metadata in the input VideoType.
	 * 
	 * @param video an object containing the information needed to construct a video handler.
	 * @param target The target slide ID to branch to if the object is clicked.
	 * @return The video handler - <code>MediaFx</code>.
	 * @see MediaFx
	 * @see VideoType
	 */
	private MediaFx createVideo(VideoType video, Integer target) {
		//sets the default duration to unconfirmed
		int duration = PresentationFx.durationUnconfirmed;
		if (video.getDuration() != null) {
			//updates the duration to the actual value if set in VideoType object
			duration = video.getDuration();
		}
		//construct the video player
		MediaFx vidH = new MediaFx(video.getStarttime(), (int) duration, video.getXstart(), video.getYstart(),
				video.getWidth(), video.getHeight(), video.getSourceFile(), video.isLoop(),
				target);

		return vidH;
	}
	/**
	 * Creates an input stream of the modified source XML file. The modification
	 * is to replace the bold and italic tags with ones that can be recognised by the JAXB
	 * parser and the <code>TextFx</code> handler.
	 * 
	 * @param sourceFile the XML file to change the bold/italic tags for.
	 * @return an input stream to be used by the JAXB <code>Unmarshaller</code>.
	 */
	private static ByteArrayInputStream cleantextTags(File sourceFile) {
		ByteArrayInputStream output = null;
		if (sourceFile.exists() && sourceFile.isFile()) {
			Path path = Paths.get(sourceFile.getPath());
			Charset charset = StandardCharsets.UTF_8;

			String content = null;
			try {
				//reads all the bytes from the file using the UTF_8 standards
				content = new String(Files.readAllBytes(path), charset);
			} catch (IOException e1) {
				return null;
			}
			//replace all normal bold tags with %% tags for the JAXB parser.
			content = content.replaceAll("<b>", "%b%");
			content = content.replaceAll("<i>", "%i%");
			content = content.replaceAll("</b>", "%/b%");
			content = content.replaceAll("</i>", "%/i%");
			output = new ByteArrayInputStream(content.getBytes(charset));
		}

		return output;
	}
	/**
	 * Constructs the text handler from the metadata in the input TextType.
	 * 
	 * @param text an object containing the information needed to construct a text handler.
	 * @param target The target slide ID to branch to if the object is clicked.
	 * @return The text handler - <code>TextFx</code>.
	 * @see TextFx
	 * @see TextType
	 */
	private TextFx createText(TextType text, Integer target) {
		TextFx txtH = new TextFx(text.getStarttime(), text.getDuration(), text.getXstart(), text.getYstart(), text.getWidth(), text.getHeight(),
				text.getText(), text.getFont(), text.getFontsize(), retrieveColour(text.getFontcolour(), "fontColour"),
				target);

		return txtH;
	}
	/**
	 * Constructs the image handler from the metadata in the input ImageType.
	 * 
	 * @param image an object containing the information needed to construct a image handler.
	 * @param target The target slide ID to branch to if the object is clicked.
	 * @return The image handler - <code>ImageFx</code>.
	 * @see ImageFx
	 * @see ImageType
	 */
	private ImageFx createImage(ImageType image, Integer target) {
		ImageFx imgH = new ImageFx(image.getStarttime(), image.getDuration(),image.getXstart(),
				image.getYstart(), image.getWidth(), image.getHeight(), image.getSourceFile(),
				target);

		return imgH;
	}
	/**
	 * Constructs the shape handler from the metadata in the input ShapeType.
	 * 
	 * @param shape an object containing the information needed to construct a shape handler.
	 * @param target The target slide ID to branch to if the object is clicked.
	 * @return The shape handler - <code>ShapeFx</code>.
	 * @see ShapeFx
	 * @see ShapeType
	 */
	private ShapeFx createShape(ShapeType shape, Integer target) {
		ShapeFx shpH = new ShapeFx(shape.getStarttime(), shape.getDuration(),shape.getXstart(),
				shape.getYstart(), shape.getWidth(), shape.getHeight(), shape.getType(),
				retrieveColour(shape.getLineColour(), "lineColour"), retrieveColour(shape.getFillColour(), "fillColour"),
				retrieveShading(shape.getShading()), target);
		return shpH;
	}
	/**
	 * Constructs the polygon handler from the metadata in the input PolygonType.
	 * 
	 * @param polygon an object containing the information needed to construct a polygon handler.
	 * @param target The target slide ID to branch to if the object is clicked.
	 * @return The polygon handler - <code>PolygonFx</code>.
	 * @see PolygonFx
	 * @see PolygonType
	 */
	private PolygonFx createPolygon(PolygonType polygon, Integer target) {
		PolygonFx plyH = new PolygonFx(polygon.getStarttime(),polygon.getDuration(),polygon.getSourceFile(), retrieveShading(polygon.getShading()),
				retrieveColour(polygon.getLineColour(), "lineColour"), retrieveColour(polygon.getFillColour(), "fillColour"),
				target);

		return plyH;
	}
	/**
	 * Constructs the shading handler from the metadata in the input ShadingType.
	 * 
	 * @param gradient an object containing the information needed to construct a shading handler.
	 * @return The shading handler - <code>ShadingFx</code>.
	 * @see ShadingFx
	 * @see ShadingType
	 */
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
	/**
	 * Gets the javaFX <code>Color</code> from the hexadecimal string input. If the
	 * Color cannot be created, then the default colour is retrieved using the
	 * <code>colourType</code> input.
	 * 
	 * @param hexCode the hexadecimal code of the desired colour
	 * @param colourType selects which default colour is used.
	 * @return a javaFX <code>Color</code>.
	 */
	private Color retrieveColour(String hexCode, String colourType) {
		System.out.println("String color code is: " + hexCode);
		Color colour = null;
		if (hexCode != null) {
			try {
				//try to create the colour
				colour = Color.web(hexCode);
			} catch (IllegalArgumentException e) {
				ExceptionFx ex = new ExceptionFx(e, AlertType.WARNING, "Colour Code Execption", "Incorrect Colour code",
						"The colour code: " + hexCode + " contains an illegal numeric value.\nThe Default "
								+ colourType + " will be used to create the object instead.");
				ex.show();
				colour = getDefaultColour(colourType);
			}
			//if the hex input is null, return the selected default colour
		} else colour = getDefaultColour(colourType);
		return colour;
	}
	/**
	 * Gets the default colour based on the <code>colourType</code> String.
	 * 
	 * @param colourType the type of default colour to return.
	 * @return the selected default colour from the <code>Defaults</code> list.
	 */
	private Color getDefaultColour(String colourType) {
		Color colour = null;

		switch (colourType) {
			case "backgroundColour":
				colour = Color.web(getDefaults().getBackgroundColour());
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
