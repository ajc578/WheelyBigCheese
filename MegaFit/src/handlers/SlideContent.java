package handlers;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

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
