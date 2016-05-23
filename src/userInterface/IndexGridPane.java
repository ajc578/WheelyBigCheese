package userInterface;

import javafx.scene.layout.GridPane;

public class IndexGridPane extends GridPane {
		
	private int index;

	public IndexGridPane(int index) {
		this.index = index;
	}
	
	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
	
}

