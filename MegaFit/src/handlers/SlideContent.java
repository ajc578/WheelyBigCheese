package handlers;

/*
 * Author : Alexander Chapman
 * Group: 4
 * Description: All objects to be displayed on a slide will extend this class.
 * 				This class is used by the presentation viewer to get the correct
 * 				display timings for that object.
 */

import javafx.scene.Node;
import javafx.scene.Scene;

public abstract class SlideContent {
	private int startTime, duration, elementID;
	private Integer targetLoc;

	public SlideContent(int startTime, int duration, Integer targetLoc) {
		super();
		this.startTime = startTime;
		this.duration = duration;
		this.targetLoc = targetLoc;
	}

	
	public int getStartTime() {
		return startTime;
	}
	
	public int getEndTime() {
		if (duration == -1){
			return -1;
		}else return (startTime + duration);
	}
	
	public void setDuration(int newDuration) {
		this.duration = newDuration;
	}
	
	public Integer getDuration() {
		return this.duration;
	}
	
	public Integer getTargetLoc() {
		return targetLoc;
	}
	
	public Node createContent(Scene parent){
		return null;
	}
	
	public Node getContent(){
		return null;
	}
	
	public String getType() {
		return null;
	}
	
	public int getElementID() {
		return elementID;
	}
	
	public void setElementID(int elementID) {
		this.elementID = elementID;
	}
	
}
