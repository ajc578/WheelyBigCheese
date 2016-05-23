package userInterface;

import javafx.scene.control.Button;

public class IndexButton extends Button  {
	
	private int index;
	
	public IndexButton(String name, int index){
		super(name);
		this.index = index;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
	
}
