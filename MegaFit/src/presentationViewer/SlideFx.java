package presentationViewer;

import java.util.ArrayList;

import javafx.scene.paint.Color;
/**
 * the Slide object holds all the information associated with an individual slide
 * <p> <STRONG> Developed by </STRONG> <p>
 * Alexander Chapman
 * <p> <STRONG> Tested by </STRONG> <p>
 * Alexander Chapman and Oliver Rushton
 * <p> <STRONG> Developed for </STRONG> <p>
 * BOSS
 * @author Alexander Chapman
 * 
 */
public class SlideFx {
	
	private int ID;
	private int destination;
	private Color backgroundColour;
	private ArrayList<SlideContent> elements;
	private int duration;
	/*Fields -
	 * ID - (natural number) The ID number of the current slide, must be 
	 *       unique to all the other slides in the presentation. (0 is the first slide)
	 * destination - the slide that this one will move to automatically when it expires
	 * backgroundColour - the background colour of the slide
	 * elements - a list of all the objects that this slide will contain
	 * duration - how many milliseconds this slide will last for before expiring and 
	 *            advancing to the next one. (a value of Presentation.persistTimeStamp
	 *            means the presentation will not time out and will only advance upon
	 *            user selection.)
	 **/
	
	/**Construct a slide with all the data that will need to be contained within it
	 * @param duration
	 * @param elements
	 * @param destination
	 * @param ID
	 * @param backgroundColour
	 */
	public SlideFx(int duration,ArrayList<SlideContent> elements,int destination, int ID, Color backgroundColour) {
		this.destination = destination;
		this.ID = ID;
		this.elements = elements;
		this.duration = duration;
		this.backgroundColour = backgroundColour;
	}

	/**
	 * @return ID (natural) unique identifier of this slide
	 */
	public int getID() {
		return ID;
	}

	/**
	 * @return destination (int) the slide that this one will move to automatically when it expires
	 */
	public int getDestination() {
		return destination;
	}
	
	/**
	 * @return the background colour of this slide
	 */
	public Color getbackgroundColour() {
		return backgroundColour;
	}

	/**
	 * @return the list of content elements that this slide contains
	 */
	public ArrayList<SlideContent> getElements() {
		return elements;
	}

	/**
	 * @return how many milliseconds the slide will last for before proceeding to its destination
	 */
	public int getDuration() {
		return duration;
	}
	
	/**allows the duration of the slide to be manually set as required.
	 * @param duration (integer)
	 */
	public void setDuration(int duration) {
		this.duration = duration;
	}

}
