package presentationViewer;

import javafx.scene.Node;
import javafx.scene.SubScene;
/**
 * Abstract Class defining the parameters and methods all the content elements 
 * in a Slide will require.
 * <p>All objects to be displayed on a slide will extend this class.<p>
 * <p> <STRONG> Developed by </STRONG> <p>
 * Alexander Chapman
 * <p> <STRONG> Tested by </STRONG> <p>
 * Alexander Chapman and Oliver Rushton
 * <p> <STRONG> Developed for </STRONG> <p>
 * BOSS
 * @author Alexander Chapman
 * 
 */
public abstract class SlideContent {
	private int startTime, duration, elementID;
	private Integer targetLoc;
	/*Fields-
	 *startTime - the time in milliseconds into the slide before an element will present itself
	 *duration - how many milliseconds the elements will remain (a value of Presentation.persistTimeStamp 
	 *           means the element will remain indefinitely)
	 *elementID - an identifier for this particular element used for controlling the media player
	 *targetLoc - the destination to be navigated to when the element is clicked (a value of Presentation.quitDestination
	 *			  means the presentation will end, and a value of Presentation.nonValidDestination will cause the click to
	 *			  be ignored.)*/
	
	/**
	 * Any constructor for any slide content element will need to set these parameters.
	 * 
	 * @param startTime (integer)
	 * @param duration (integer)
	 * @param targetLoc (integer)
	 */
	public SlideContent(int startTime, int duration, Integer targetLoc) {
		super();
		this.startTime = startTime;
		this.duration = duration;
		this.targetLoc = targetLoc;
	}

	/**
	 * @return startTime (integer)
	 */
	public int getStartTime() {
		return startTime;
	}
	
	/**
	 * @return EndTime (integer) (the time in milliseconds through the slide
	 * when the element will disappear (startTime + duration))
	 */
	public int getEndTime() {
		if (duration < 0){
			return duration;
		}else return (startTime + duration);
	}
	
	/**allows the duration to be manually set
	 * @param newDuration (int)
	 */
	public void setDuration(int newDuration) {
		this.duration = newDuration;
	}
	
	/**
	 * @return duration (integer)
	 */
	public Integer getDuration() {
		return this.duration;
	}
	
	/**
	 * @return targetLoc (integer)
	 */
	public Integer getTargetLoc() {
		return targetLoc;
	}
	
	/**creates the element to be added to the slide based on the parameters when the object was constructed
	 * 
	 * @param parent (Scene) The scene the slide will be embedded in (to enable binding the sizes)
	 * @return (Node) the node that will be embedded in the slide
	 */
	public Node createContent(SubScene parent){
		return null;
	}
	
	/**gets the content node (<tt>createContent</tt> must have already been called)
	 * @return (node)
	 */
	public Node getContent(){
		return null;
	}
	
	/**Dummy method overwritten by the shape handler to allow the type of 
	 * the shape to be read*/
	public String getType() {
		return null;
	}
	
	/**
	 * @return elementID (integer)
	 */
	public int getElementID() {
		return elementID;
	}
	

	/**allows the elementID to be manually set as required.
	 * @param elementID (integer)
	 */
	public void setElementID(int elementID) {
		this.elementID = elementID;
	}
	
}
