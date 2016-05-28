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

public class VerifyXML {

	private static final String[] COLOUR_TYPES = {"background colour","line colour", "fill colour", "font colour"};

	private static Defaults defaults;
	private static int slideID;

	public static boolean loadDefaults(Defaults defaults) {
		VerifyXML.defaults = defaults;
		return verifyDefaults(defaults);
	}

	public static void loadSlideID(int slideID) {
		VerifyXML.slideID = slideID;
	}

	private static boolean verifyDefaults(Defaults defaults) {
		ArrayList<String> defaultColours = new ArrayList<String>();
		defaultColours.add(defaults.getBackgroundColour());
		defaultColours.add(defaults.getLineColour());
		defaultColours.add(defaults.getFillColour());
		defaultColours.add(defaults.getFontColour());
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
		if (Font.loadFont(defaults.getFont(), defaults.getFontsize()) == null)
			return false;

		return true;
	}

	public static boolean verifyAudio(AudioType audio) {
		boolean audioValid = true;
		if (audio.getStarttime() == null)
			audioValid = false;
		File file = new File("src/res/audio/" + audio.getSourceFile());
		try {
			if (!file.exists()) {
				ExceptionFx ex = new ExceptionFx(AlertType.ERROR, "Media Exception", "Media file could not be found.",
						"The source filename provided (" + audio.getSourceFile() + ") does not exist in the audio resources folder.");
				ex.show();
				audioValid = false;
			} else {
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

		validityCheck(audioValid, "Audio");
		return audioValid;
	}

	public static boolean verifyVideo(VideoType video) {
		boolean videoValid = true;
		if (video.getStarttime() == null)
			videoValid = false;
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
			if (!file.exists()) {
				ExceptionFx ex = new ExceptionFx(AlertType.ERROR, "Media Exception", "Media file could not be found.",
						"The source filename provided (" + video.getSourceFile() + ") does not exist in the video resources folder.");
				ex.show();
				videoValid = false;
			} else {
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
		if (text.getFont() == null)
			System.out.println("Text font empty. Default font used instead");
			text.setFont(defaults.getFont());
		if (text.getFontsize() == null)
			System.out.println("Text font size empty. Default font size used instead");
			text.setFontsize(defaults.getFontsize());
		if (text.getFontcolour() == null) {
			System.out.println("Text font colour empty. Default font colour used instead");
			text.setFontcolour(defaults.getFontColour());
		}
		validityCheck(textValid, "Text");
		return textValid;
	}

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

	public static boolean verifyPolygon(PolygonType polygon) {
		boolean polygonValid = true;

		if (polygon.getStarttime() == null)
			polygonValid = false;
		if (polygon.getDuration() == null)
			polygonValid = false;
		if (polygon.getLineColour() == null) {
			System.out.println("Polygon line colour colour empty. Default line colour used instead");
			polygon.setLineColour(defaults.getLineColour());
		}
		if (polygon.getFillColour() == null && !verifyShading(polygon.getShading(), polygon.getFillColour() != null ?
				polygon.getFillColour() : defaults.getFillColour())) {
			System.out.println("Polygon fill colour and shading are empty. Default fill colour used instead");
			polygon.setFillColour(defaults.getFillColour());
		}
		File file = new File("src/res/graphics/" + polygon.getSourceFile());
		try {
			if (!file.exists()) {
				ExceptionFx ex = new ExceptionFx(AlertType.ERROR, "Graphics Exception", "File does not exist",
						"The graphics source filename provided (" + polygon.getSourceFile() + ") does not exist in the graphics resources folder.");
				ex.show();
				polygonValid = false;
			} else {
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
		File file = new File("src/res/images/" + image.getSourceFile());
		try {
			if (!file.exists()) {
				ExceptionFx ex = new ExceptionFx(AlertType.ERROR, "Image Exception", "File does not exist",
						"The image source filename provided (" + image.getSourceFile() + ") does not exist in the image resources folder.");
				ex.show();
				imageValid = false;
			} else {
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
			if (!shadingValid) {
				shading.setX1(Float.valueOf(0));
				shading.setX2(Float.valueOf(0));
				shading.setY1(Float.valueOf(1));
				shading.setY2(Float.valueOf(1));
			}
		}

		return shadingValid;
	}

	private static void validityCheck(boolean valid, String objectType) {
		if (!valid) {
			ExceptionFx objectException = new ExceptionFx(AlertType.ERROR, "Slide Exception", "Object could not be created.",
					"A  " + objectType + " object could not be created for slide: " + slideID);
			objectException.show();
		}
	}

}
