package userInterface;

import javafx.scene.layout.GridPane;
/**
 * A class that creates a javaFX GridPane with a specific index.
 * The index is normally used to reference an element in a list.
 * 
 * <p> <STRONG> Generated by </STRONG> <p>
 * JAXB
 * <p> <STRONG> Developed by </STRONG> <p>
 * Oliver Rushton
 * <p> <STRONG> Tested by </STRONG> <p>
 * Oliver Rushton
 * <p> <STRONG> Developed for </STRONG> <p>
 * BOSS
 * @see GridPane
 * 
 */
public class IndexGridPane extends GridPane {
		
	private int index;
	/**
	 * Creates a javaFX grid pane and adds an index to it..
	 * 
	 * @param index the index of this gridpane.
	 */
	public IndexGridPane(int index) {
		this.index = index;
	}
	/**
	 * Gets the value of the grid pane's index.
	 * @return the index of the grid pane.
	 */
	public int getIndex() {
		return index;
	}
	/**
	 * Sets the value of the grid pane's index.
	 * @param index the new index of the grid pane.
	 */
	public void setIndex(int index) {
		this.index = index;
	}
	
}

