package presentationViewer;

/**
 * A class to create textflow objects for use in building and 
 * displaying presentations, it can detect and process bold 
 * and italic tags in the 
 * <p> <STRONG> Developed by </STRONG> <p>
 * Oliver Rushton and Alexander Chapman
 * <p> <STRONG> Developed for </STRONG> <p>
 * BOSS
 * @author Oliver Rushton and Alexander Chapman
 */
import java.util.ArrayList;

import javafx.beans.binding.NumberBinding;
import javafx.scene.Node;
import javafx.scene.SubScene;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class TextFx extends SlideContent {
	
	private static final int NORMAL = 0, BOLD = 1, ITALIC = 2, BOTH = 3;
	
	private int fontSize;
	private double xStart, yStart, width, height;
	private String text, font;
	private Color textColour;
	private TextFlow content;
	private ArrayList<String> sentenceStorage;
	private ArrayList<Integer> styleStorage;
	/*Fields-
	 *fontSize - (integer > 0) size of the text to be drawn
	 *xStart - (integer between 0 and 1) proportion across the slide the left edge of the textFlow should be
	 *yStart - (integer between 0 and 1) proportion down the slide the top edge of the textFlow should be
	 *width - (integer between 0 and 1) proportion across the slide the textFlow should span
	 *height - (integer between 0 and 1) proportion down the slide the textFlow should span
	 *text - the string of text, with tags, to be processed and then printed
	 *font - the name of the font to draw the text in
	 *textColour - the colour to draw the text in
	 *content - the actual textFlow object which will be placed in the slide
	 *sentenceStorage - An arraylist of segments of the text to be printed, where each segment is a different style
	 *styleStorage - An arraylist that holds the styles corresponding to the text segments stored in sentenceStorage*/

	
	
	/**
	 * Constructor simply passes in all the parameters 
	 * (which will be read from the xml by the interpreter)
	 * <p>The TextFlow will not actually be created however, until
	 * the <tt>createContent</tt> method is called. 
	 * 
	 * @param startTime - how many milliseconds into the slide the text should appear at 
	 * @param duration - how many milliseconds the text should last for
	 * @param xStart - (integer between 0 and 1) proportion across the slide the left edge of the textFlow should be
	 * @param yStart - (integer between 0 and 1) proportion down the slide the top edge of the textFlow should be
	 * @param width - (integer between 0 and 1) proportion across the slide the textFlow should span
	 * @param height - (integer between 0 and 1) proportion down the slide the textFlow should span
	 * @param text - the string of text, with tags, to be processed and then printed
	 * @param font - the name of the font to draw the text in
	 * @param fontSize - (integer > 0) size of the text to be drawn
	 * @param textColour - the colour to draw the text in
	 * @param targetLoc - the slide ID to move to when this object is clicked (or other special value)
	 */
	public TextFx(int startTime, int duration, double xStart, double yStart, double width, double height, 
				String text, String font, int fontSize, Color textColour, Integer targetLoc) {
		super(startTime, duration, targetLoc);
		this.xStart = xStart;
		this.yStart = yStart;
		this.width = width;
		this.height = height;
		this.text = text;
		this.font = font;
		this.fontSize = fontSize;
		this.textColour = textColour;
	}
	
	/**This method creates a TextFlow object, using all the parameters specified when
	 * its <tt>constructor</tt> was called, and processing the style tags included in 
	 * the string for display within the specified scene,.
	 * @param parent - the scene in which this ellipse will be drawn
	 * @return An ellipse object (which is a sub-type of Node)
	 */
	public Node createContent(SubScene parent) {
		
		//Make sure that the TextFlow is dynamically sized as a percentage of the presentation screen
		content = new TextFlow();
		NumberBinding textX = parent.widthProperty().multiply(xStart);	
		NumberBinding textY = parent.heightProperty().multiply(yStart);
		NumberBinding textWidth = parent.widthProperty().multiply(width);
		NumberBinding textHeight = parent.heightProperty().multiply(height);
		content.layoutXProperty().bind(textX);
		content.layoutYProperty().bind(textY);
		
		if (height != -1)content.prefHeightProperty().bind(textHeight);
		if (width != -1)content.prefWidthProperty().bind(textWidth);
		
		//process the style tags in the text string
		detectTextStyle();
		
		content.setVisible(false);
		return content;
	}
	
	/**Return the textflow object generated in <tt>createContent</tt>
	 * @return content - The textFlow object
	 */
	public Node getContent() {
		return content;
	}
	
	/**Returns the type of SlideElement that this is
	 * @return "TextFx"
	 */
	public String getType() {
		return "TextFx";
	}
	

	/** Return the amount of characters through the string to be printed the last style change was
	 * @param sentenceStorage
	 * @return changeIndex - the total number of characters already processed
	 */
	private int getLastChangeIndex(ArrayList<String> sentenceStorage) {
		int changeIndex = 0;
		if (sentenceStorage.size() == 0) {
			return 0;
		} else {
			for (int i = 0; i < sentenceStorage.size(); i++) {
				changeIndex += sentenceStorage.get(i).length();
			}
			return changeIndex;
		}
	}
	
	/**
	 * Scan through the text  entered in the constructor and remove the tags 
	 * from it setting the styles as appropriate and then add the resultant 
	 * information to the textFlow
	 * 
	 */
	private void detectTextStyle() {
		sentenceStorage = new ArrayList<String>();
		styleStorage = new ArrayList<Integer>();
		int currentStyle = NORMAL;
		boolean bold = false, italic = false, change = false;
		
		//iterate through all the characters
		for (int i = 0; i < text.length(); i++) {
			
			//if a bold tag is detected indicate that the text has been made bold
			//and that the string has ended a segment of constant style (style has changed)
			if (!(i+3 > text.length())) {
				if (text.substring(i, i+3).equals("%b%")) {
					text = text.replaceFirst("%b%", "");
					bold = true;
					change = true;
				} 
			}
			//if an italic tag is detected indicate that the text has been made italic
			//and that the string has ended a segment of constant style (style has changed)
			if (!(i+3 > text.length())) {
				if (text.substring(i,i+3).equals("%i%")) {
					text = text.replaceFirst("%i%", "");
					italic = true;
					change = true;
				}
			}
			//if a end-bold tag is detected indicate that the text is no longer bold
			//and that the string has ended a segment of constant style
			if (!(i+4 > text.length())) {
				if (text.substring(i, i+4).equals("%/b%")) {
					text = text.replaceFirst("%/b%", "");
					bold= false;
					change = true;
				}
			}
			//if a end-italic tag is detected indicate that the text is no longer italic
			//and that the string has ended a segment of constant style
			if (!(i+4 > text.length())) {
				if (text.substring(i,i+4).equals("%/i%")) {
					text = text.replaceFirst("%/i%", "");
					italic = false;
					change = true;
				}
			}
			
			//if the last character has been reached signal the end of this string segment.
			if (i + 1 == text.length()) {
				change = true;
			}
			
			//if you have reached the end of a string segment of constant style add that segment 
			//to the sentence storage adding the coressponding style to style storage and then 
			//set the style for the next segment according to trhe character just read
			if (change) {
				if(i + 1 == text.length())  {
					sentenceStorage.add(text.substring(getLastChangeIndex(sentenceStorage), i+1));
					styleStorage.add(currentStyle);
				} else {
					if (i != getLastChangeIndex(sentenceStorage)) {
						sentenceStorage.add(text.substring(getLastChangeIndex(sentenceStorage), i));
						styleStorage.add(currentStyle);
					}
				}
				if (!bold && !italic) {
					currentStyle = NORMAL;
				} else if (bold && !italic) {
					currentStyle = BOLD;
				} else if (!bold && italic) {
					currentStyle = ITALIC;	
				} else {
					currentStyle = BOTH;
				}
				change = false;
				if (!(i == text.length() - 1) && i != 0) {
					i-=1;
				}
			}
			
		}
		
		//add all the text to the TextFlow setting the font style as appropriate 
		//for each text segment
		for (int j = 0; j < sentenceStorage.size(); j++) {
			Text text = new Text(sentenceStorage.get(j));
			if (styleStorage.get(j) == NORMAL) {
				text.setFont(Font.font(font,FontWeight.NORMAL,FontPosture.REGULAR,fontSize));
			} else if (styleStorage.get(j) == BOLD) {
				text.setFont(Font.font(font,FontWeight.BOLD,FontPosture.REGULAR,fontSize));
			} else if (styleStorage.get(j) == ITALIC) {
				text.setFont(Font.font(font,FontWeight.NORMAL,FontPosture.ITALIC,fontSize));
			} else {
				text.setFont(Font.font(font,FontWeight.BOLD,FontPosture.ITALIC,fontSize));
			}
			text.setFill(textColour);
			content.getChildren().add(text);
		}
		
	}
	
/*-------------Testing methods-------------*/
	
	/**Returns the String that will be written post processing for testing purposes
	 * @return text - the string after the bold and italic tags have been processed
	 */
	public String getText() {
		String text = "";
		for (int i = 0; i < sentenceStorage.size(); i++) {
			text += sentenceStorage.get(i);
		}
		return text;
	}
	
	/**
	 * @return num 
	 */
	public int getNumOfStyleChanges() {
		int num = sentenceStorage.size();
		return num;
	}
	
	/**Returns the fontSize - used in testing
	 * @return fontSize
	 */
	public int getFontSize() {
		return fontSize;
	}
	
	/**Returns the font - used in testing
	 * @return font
	 */
	public String getFont() {
		return font;
	}
	
	/**Returns the fontColur - used in testing
	 * @return fontColour
	 */
	public Color getFontColour() {
		return textColour;
	}
	
	
}
