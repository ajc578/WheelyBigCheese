package userInterface;

import java.awt.Insets;

import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import parser.WorkoutInfo;

public class WorkoutDetails extends GridPane{

	public WorkoutDetails (double screenWidth, double screenHeight, String title, String author,
			String comment, WorkoutInfo WorkoutInfo){
		this.setHgap(screenWidth*0.025);
	    this.setVgap(screenWidth*0.025);
	    
	    
	}
	
}
