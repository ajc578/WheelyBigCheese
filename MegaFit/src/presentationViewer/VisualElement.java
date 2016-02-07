package presentationViewer;

import java.awt.Graphics;

abstract class VisualElement extends Element {	
	protected float x_pos, y_pos;
	protected String targetLoc;
	
	public String getTargetLoc() {
		return targetLoc;
	}
	
	//Dummy methods to be overwritten by subclasses
	public void display(Graphics g, int containerW, int containerH){
		
	}
	public boolean contains(int x, int y, int containerW, int containerH) {
		return false;
	}
}
