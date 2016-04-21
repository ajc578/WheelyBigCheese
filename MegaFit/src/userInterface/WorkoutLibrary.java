package userInterface;

import java.io.File;

import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class WorkoutLibrary extends ScrollPane {
	
	File workoutFolder;
	VBox exerciseList;

	public WorkoutLibrary (double screenWidth, double screenHeight, BorderPane root){
		workoutFolder = new File("src/res/xml");
		exerciseList = new VBox();
		
		for(File i : workoutFolder.listFiles()){
			String filename = i.getName();
			if(filename.toUpperCase().endsWith("WORKOUT.XML"))
			{
				WorkoutDetails workoutView = new WorkoutDetails(screenWidth, screenHeight, filename, root);
				exerciseList.getChildren().add(workoutView);
				
			}
		}
		
		exerciseList.setSpacing(screenHeight*0.05);
		exerciseList.setPadding(new Insets(screenHeight*0.01, screenWidth*0.02, screenHeight*0.01, screenWidth*0.02));
		
		setContent(exerciseList);
		
		setHbarPolicy(ScrollBarPolicy.NEVER);
		setVbarPolicy(ScrollBarPolicy.ALWAYS);
		
		setMinWidth(screenWidth*0.85);		
		setMinHeight(screenHeight*0.7);
		setMaxWidth(screenWidth*0.85);		
		setMaxHeight(screenHeight*0.7);
	}
}
