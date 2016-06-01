package parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javafx.scene.control.Alert.AlertType;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import parser.Presentation.Defaults;
import parser.Presentation.Slide.AudioType;
import parser.Presentation.Slide.ImageType;
import parser.Presentation.Slide.PolygonType;
import parser.Presentation.Slide.ShadingType;
import parser.Presentation.Slide.ShapeType;
import parser.Presentation.Slide.TextType;
import parser.Presentation.Slide.VideoType;
import presentationViewer.ExceptionFx;
/**
 * This Class checks that each slide object belonging to a {@link Presentation}
 * contains the correct information. If any fields are empty, the default values
 * (also retrieved from the <code>Presentation</code> class) are then assigned to 
 * those fields. If a default value doesn't exist for those fields, then the 
 * static method checking that slide object will return false and that object won't be
 * created and added to the slide content in the {@link XMLParser} class.
 * <p>
 * Alerts appear on screen if a slide object is missing fundamental fields.
 *
 * <p> <STRONG> Developed by </STRONG> <p>
 * Oliver Rushton
 * <p> <STRONG> Tested by </STRONG> <p>
 * Oliver Rushton
 * <p> <STRONG> Developed for </STRONG> <p>
 * BOSS
 * @author Oliver Rushton
 */
public class VerifyXML {

	private static final String[] COLOUR_TYPES = {"background colour","line colour", "fill colour", "font colour"};

	private static Defaults defaults;
	private static int slideID;
	/**
	 * Sets the value of the static <code>defaults</code> field.
	 * 
	 * @param defaults the default values for some of the fields in the slide objects.
	 * @return True if the defaults contain all the necessary data.
	 */
	public static boolean loadDefaults(Defaults defaults) {
		VerifyXML.defaults = defaults;
		return verifyDefaults(defaults);
	}
	/**
	 * Loads the slide number of the current slide's objects we are
	 * verifying.
	 * 
	 * @param slideID the current slide we are verifying.
	 */
	public static void loadSlideID(int slideID) {
		VerifyXML.slideID = slideID;
	}
	/**
	 * Verifies the input <code>Default</code> object fields are fully completed.
	 * 
	 * @param defaults the default values for those slide object that can take them.
	 * @return True if the defaults contain all the necessary data.
	 * @see Defaults
	 */
	private static boolean verifyDefaults(Defaults defaults) {
		ArrayList<String> defaultColours = new ArrayList<String>();
		defaultColours.add(defaults.getBackgroundColour());
		defaultColours.add(defaults.getLineColour());
		defaultColours.add(defaults.getFillColour());
		defaultColours.add(defaults.getFontColour());
		//Iterates through each default colour and checks that they can
		//be created. If they can't, then an exception is raised to the user.
		for (int i = 0; i < defaultColours.size(); i++) {
			try {
				Color.web(defaultColours.get(i));
			} catch (NullPointerException npe) {
				ExceptionFx ex = new ExceptionFx(npe, AlertType.ERROR, "Colour Code Exception", "Default colour code is null",
						"The default colour code for " + COLOUR_TYPES[i] +
								" is empty.\nPlease ensure the xml defaults are filled out correctly before running the program.");
				ex.show();
				return false;
			} catch (IllegalArgumentException iae) {
				ExceptionFx ex = new ExceptionFx(iae, AlertType.ERROR, "Colour Code Exception", "Default colour code contains an illegal numeric value",
						"The default colour code: " + defaultColours.get(i) + " for " + COLOUR_TYPES[i] +
								" contains an illegal numeric value.\nPlease ensure the xml defaults are filled out correctly before running the program.");
				ex.show();
				return false;
			}
		}
		//checks that the default font name and size can create the correct javaFX Font object correctly
		if (Font.loadFont(defaults.getFont(), defaults.getFontsize()) == null)
			return false;

		return true;
	}
	/**
	 * Verifies that the contents of audio will allow the corresponding slide 'fx' object to be created.
	 * <p>
	 * The main check occurs when testing that the file path to the object
	 * doesn't return an empty file.
	 * 
	 * @param audio the audio object to verify
	 * @return True if the audio object contains the correct fields, false otherwise.
	 * @see AudioType
	 */
	public static boolean verifyAudio(AudioType audio) {
		boolean audioValid = true;
		if (audio.getStarttime() == null)
			audioValid = false;
		//retrieve the audio file from the path
		File file = new File("src/res/audio/" + audio.getSourceFile());
		try {
			//check the fiel exists
			if (!file.exists()) {
				ExceptionFx ex = new ExceptionFx(AlertType.ERROR, "Media Exception", "Media file could not be found.",
						"The source filename provided (" + audio.getSourceFile() + ") does not exist in the audio resources folder.");
				ex.show();
				audioValid = false;
			} else {
				//check that the file has contents
				BufferedReader br = new BufferedReader(new FileReader(file));
				if (br.readLine() == null)
					audioValid = false;
				br.close();
			}
		} catch (IOException ioe) {
			ExceptionFx ex = new ExceptionFx(ioe, AlertType.ERROR, "Audio Exception", "File content empty",
					"The audio source filename provided (" + audio.getSourceFile() + ") is empty.\n"
							+ "Therefore the audio object can not be created.");
			ex.show();
			audioValid = false;
		} catch (SecurityException se) {
			ExceptionFx ex2 = new ExceptionFx(se,AlertType.ERROR, "Access Denied Exception", "Security Manager denied access to file.",
					"Access to the source filename provided (" + audio.getSourceFile() + ") has been restricted by the system security manager.");
			ex2.show();
			audioValid = false;
		}
		//sets another exception if the value of audioValid is false
		validityCheck(audioValid, "Audio");
		return audioValid;
	}
	/**
	 * Verifies that the contents of the video will allow the corresponding slide 'fx' object to be created.
	 * <p>
	 * The main check occurs when testing that the file path to the object
	 * doesn't return an empty file.
	 * @param video the video object to verify.
	 * @return True if the video object contains the correct fields, false otherwise.
	 * @see VideoType
	 */
	public static boolean verifyVideo(VideoType video) {
		boolean videoValid = true;
		if (video.getStarttime() == null)
			videoValid = false;
		//checks that the value isn't null and also that it is a percentage between 0 -> 1.
		if (video.getXstart() == null || 0 > video.getXstart() || video.getXstart() > 1)
			videoValid = false;
		if (video.getYstart() == null || 0 > video.getYstart() || video.getYstart() > 1)
			videoValid = false;
		if (video.getWidth() == null || 0 > video.getWidth() || video.getWidth() > 1)
			videoValid = false;
		if (video.getHeight() == null || 0 > video.getHeight() || video.getHeight() > 1)
			videoValid = false;
		File file = new File("src/res/videos/" + video.getSourceFile());
		try {
			//check if the video path field points to an existing file
			if (!file.exists()) {
				ExceptionFx ex = new ExceptionFx(AlertType.ERROR, "Media Exception", "Media file could not be found.",
						"The source filename provided (" + video.getSourceFile() + ") does not exist in the video resources folder.");
				ex.show();
				videoValid = false;
			} else {
				//checks that the file found has content
				BufferedReader br = new BufferedReader(new FileReader(file));
				if (br.readLine() == null)
					videoValid = false;
				br.close();
			}
		} catch (IOException ioe) {
			ExceptionFx ex = new ExceptionFx(ioe, AlertType.ERROR, "Video Exception", "File content empty",
					"The video source filename provided (" + video.getSourceFile() + ") is empty.\n"
							+ "Therefore the video object can not be created.");
			ex.show();
			videoValid = false;
		} catch (SecurityException se) {
			ExceptionFx ex2 = new ExceptionFx(se,AlertType.ERROR, "Access Denied Exception", "Security Manager denied access to file.",
					"Access to the source filename provided (" + video.getSourceFile() + ") has been restricted by the system security manager.");
			ex2.show();
			videoValid = false;
		}
		
		validityCheck(videoValid, "Video");
		return videoValid;
	}
	/**
	 * Verifies that the contents of text will allow the corresponding slide 'fx' object to be created.
	 * 
	 * @param text the text object to verify.
	 * @return True if the text object contains the correct fields, false otherwise.
	 * @see TextType
	 */
	public static boolean verifyText(TextType text) {
		boolean textValid = true;
		if (text.getStarttime() == null)
			textValid = false;
		if (text.getDuration() == null)
			textValid = false;
		if (text.getXstart() == null || 0 > text.getXstart() || text.getXstart() > 1)
			textValid = false;
		if (text.getYstart() == null || 0 > text.getYstart() || text.getYstart() > 1)
			textValid = false;
		if (text.getWidth() == null || 0 > text.getWidth() || text.getWidth() > 1)
			text.setWidth((float)-1);
		if (text.getHeight() == null || 0 > text.getHeight() || text.getHeight() > 1)
			text.setHeight((float)-1);
		if (text.getFont() == null) {
			text.setFont(defaults.getFont());
			}
		if (text.getFontsize() == null){
			text.setFontsize(defaults.getFontsize());
			}
		if (text.getFontcolour() == null) {
			text.setFontcolour(defaults.getFontColour());
		}
		validityCheck(textValid, "Text");
		return textValid;
	}
	/**
	 * Verifies that the contents of shape will allow the corresponding slide 'fx' object to be created.
	 * 
	 * @param shape the shape object to verify.
	 * @return True if the shape object contains the correct fields, false otherwise.
	 * @see ShapeType
	 */
	public static boolean verifyShape(ShapeType shape) {
		boolean shapeValid = true;

		if (shape.getStarttime() == null)
			shapeValid = false;
		if (shape.getDuration() == null)
			shapeValid = false;
		if (shape.getXstart() == null || 0 > shape.getXstart() || shape.getXstart() > 1)
			shapeValid = false;
		if (shape.getYstart() == null || 0 > shape.getYstart() || shape.getYstart() > 1)
			shapeValid = false;
		if (shape.getWidth() == null || 0 > shape.getWidth() || shape.getWidth() > 1)
			shapeValid = false;
		if (shape.getHeight() == null || 0 > shape.getHeight() || shape.getHeight() > 1)
			shapeValid = false;
		if (shape.getLineColour() == null) {
			System.out.println("Shape line colour colour empty. Default line colour used instead");
			shape.setLineColour(defaults.getLineColour());
		}
		if (shape.getFillColour() == null && !verifyShading(shape.getShading(), shape.getFillColour() != null ?
				shape.getFillColour() : defaults.getFillColour())) {
			System.out.println("Shape fill colour and shading are empty. Default fill colour used instead");
			shape.setFillColour(defaults.getFillColour());
		}
		if (shape.getType() == "circle" || shape.getType() == "rectangle" || shape.getType() == "rounded rectangle")
			shapeValid = false;

		validityCheck(shapeValid, "Shape");
		return shapeValid;
	}
	/**
	 * Verifies that the contents of polygon will allow the corresponding slide 'fx' object to be created.
	 * 
	 * @param polygon the polygon object to verify.
	 * @return True if the polygon object contains the correct fields, false otherwise.
	 * @see PolygonType
	 */
	public static boolean verifyPolygon(PolygonType polygon) {
		boolean polygonValid = true;

		if (polygon.getStarttime() == null)
			polygonValid = false;
		if (polygon.getDuration() == null)
			polygonValid = false;
		//adds default colours if they aren't already set
		if (polygon.getLineColour() == null) {
			System.out.println("Polygon line colour colour empty. Default line colour used instead");
			polygon.setLineColour(defaults.getLineColour());
		}
		if (polygon.getFillColour() == null && !verifyShading(polygon.getShading(), polygon.getFillColour() != null ?
				polygon.getFillColour() : defaults.getFillColour())) {
			System.out.println("Polygon fill colour and shading are empty. Default fill colour used instead");
			polygon.setFillColour(defaults.getFillColour());
		}
		//checks that the csv file exists
		File file = new File("src/res/graphics/" + polygon.getSourceFile());
		try {
			if (!file.exists()) {
				ExceptionFx ex = new ExceptionFx(AlertType.ERROR, "Graphics Exception", "File does not exist",
						"The graphics source filename provided (" + polygon.getSourceFile() + ") does not exist in the graphics resources folder.");
				ex.show();
				polygonValid = false;
			} else {
				//check that the csv file has content
				BufferedReader br = new BufferedReader(new FileReader(file));
				if (br.readLine() == null)
					polygonValid = false;
				br.close();
			}
		} catch (IOException e) {
			ExceptionFx ex = new ExceptionFx(e, AlertType.ERROR, "Graphics Exception", "File content empty",
					"The graphics source filename provided (" + polygon.getSourceFile() + ") is empty.\n"
							+ "Therefore the graphic object can not be created.");
			ex.show();
			polygonValid = false;
		} catch (SecurityException se) {
			ExceptionFx ex2 = new ExceptionFx(se,AlertType.ERROR, "Access Denied Exception", "Security Manager denied access to file.",
					"Access to the source filename provided (" + polygon.getSourceFile() + ") has been restricted by the system security manager.");
			ex2.show();
			polygonValid = false;
		}

		validityCheck(polygonValid, "Polygon");
		return polygonValid;
	}
	/**
	 * Verifies that the contents of image will allow the corresponding slide 'fx' object to be created.
	 * 
	 * @param image the image object to verify.
	 * @return True if the image object contains the correct fields, false otherwise.
	 * @see ImageType
	 */
	public static boolean verifyImage(ImageType image) {
		boolean imageValid = true;

		if (image.getStarttime() == null)
			imageValid = false;
		if (image.getDuration() == null)
			imageValid = false;
		if (image.getXstart() == null || 0 > image.getXstart() || image.getXstart() > 1)
			imageValid = false;
		if (image.getYstart() == null || 0 > image.getYstart() || image.getYstart() > 1)
			imageValid = false;
		if (image.getWidth() == null || 0 > image.getWidth() || image.getWidth() > 1)
			imageValid = false;
		if (image.getHeight() == null || 0 > image.getHeight() || image.getHeight() > 1)
			imageValid = false;
		//check that the image file exists
		File file = new File("src/res/images/" + image.getSourceFile());
		try {
			if (!file.exists()) {
				ExceptionFx ex = new ExceptionFx(AlertType.ERROR, "Image Exception", "File does not exist",
						"The image source filename provided (" + image.getSourceFile() + ") does not exist in the image resources folder.");
				ex.show();
				imageValid = false;
			} else {
				//check that the image file has content
				BufferedReader br = new BufferedReader(new FileReader(file));
				if (br.readLine() == null)
					imageValid = false;
				br.close();
			}
		} catch (IOException e) {
			ExceptionFx ex = new ExceptionFx(e, AlertType.ERROR, "Image Exception", "File content empty",
					"The image source filename provided (" + image.getSourceFile() + ") is empty.\n"
							+ "Therefore the image object can not be created.");
			ex.show();
			imageValid = false;
		} catch (SecurityException se) {
			ExceptionFx ex2 = new ExceptionFx(se,AlertType.ERROR, "Access Denied Exception", "Security Manager denied access to file.",
					"Access to the source filename provided (" + image.getSourceFile() + ") has been restricted by the system security manager.");
			ex2.show();
			imageValid = false;
		}

		validityCheck(imageValid, "Image");
		return imageValid;
	}
	/**
	 * Verifies that the contents of shading will allow the corresponding slide 'fx' object to be created.
	 * 
	 * @param shading the shading object to verify.
	 * @param fillColour the default fill colour to use if the shading fields are not complete.
	 * @return True if the shading object contains the correct fields, false otherwise.
	 * @see ShadingType
	 */
	private static boolean verifyShading(ShadingType shading, String fillColour) {
		boolean shadingValid = true;
		if (shading == null) {
			shadingValid = false;
		} else {
			if (shading.getX1() == null || 0 > shading.getX1() || shading.getX1() > 1)
				shadingValid = false;
			if (shading.getY1() == null || 0 > shading.getY1() || shading.getY1() > 1)
				shadingValid = false;
			if (shading.getX2() == null || 0 > shading.getX2() || shading.getX2() > 1)
				shadingValid = false;
			if (shading.getY2() == null || 0 > shading.getY2() || shading.getY2() > 1)
				shadingValid = false;
			if (shading.getColour1() == null) {
				System.out.println("Shading colour empty. Default fill colour used instead");
				shading.setColour1(fillColour);
			}
			if (shading.getColour2() == null) {
				System.out.println("Shading colour empty. Default fill colour used instead");
				shading.setColour2(fillColour);
			}
			//if the gradient points are incorrect, set there values to defaults
			if (!shadingValid) {
				shading.setX1(Float.valueOf(0));
				shading.setX2(Float.valueOf(1));
				shading.setY1(Float.valueOf(0));
				shading.setY2(Float.valueOf(1));
			}
		}

		return shadingValid;
	}
	/**
	 * Checks the sign of the boolean and if false, generates an Alert.
	 * @param valid the boolean to check
	 * @param objectType the object corresponding to the valid boolean.
	 */
	private static void validityCheck(boolean valid, String objectType) {
		if (!valid) {
			ExceptionFx objectException = new ExceptionFx(AlertType.ERROR, "Slide Exception", "Object could not be created.",
					"A  " + objectType + " object could not be created for slide: " + slideID);
			objectException.show();
		}
	}

}
