package handlers;

import java.util.ArrayList;

import javafx.scene.paint.Color;

public class SlideFx {
	
	private int ID;
	private int destination;
	private Color backgroundColour;
	private ArrayList<SlideContent> elements;
	private int duration;
	
	public SlideFx(int duration,ArrayList<SlideContent> elements,int destination, int ID, Color backgroundColour) {
		this.destination = destination;
		this.ID = ID;
		this.elements = elements;
		this.duration = duration;
		this.backgroundColour = backgroundColour;
	}

	public int getID() {
		return ID;
	}

	public int getDestination() {
		return destination;
	}
	
	public Color getbackgroundColour() {
		return backgroundColour;
	}

	public ArrayList<SlideContent> getElements() {
		return elements;
	}

	public int getDuration() {
		return duration;
	}

}
