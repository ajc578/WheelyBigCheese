package userInterface;

import java.io.File;
import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.*;

public class WorkoutLibrary extends HBox {
	
	File workoutFolder;
	ArrayList<File> listOfWorkouts;
	ScrollPane exerciseListScrollPane;
	VBox exerciseList;

	public WorkoutLibrary (double screenWidth, double screenHeight, BorderPane root){
		workoutFolder = new File("src/res/xml");
		listOfWorkouts = new ArrayList<File>();
		exerciseList = new VBox();
		exerciseListScrollPane = new ScrollPane();
		
		
		for(File i : workoutFolder.listFiles()){
			String filename = i.getName();
			if(filename.endsWith(".xml")||filename.endsWith(".XML"))
			{
				listOfWorkouts.add(i);
				Button tempButton = new Button(filename);
				tempButton.setOnAction(new EventHandler<ActionEvent>(){
					
					public void handle (ActionEvent event){
						//TODO have presentation play
						
					}
				});
				exerciseList.getChildren().add(tempButton);
				
			}
		}
		
		exerciseList.setSpacing(screenHeight*0.05);
		
		exerciseListScrollPane.setContent(exerciseList);		
		exerciseListScrollPane.setHbarPolicy(ScrollBarPolicy.NEVER);
		exerciseListScrollPane.setVbarPolicy(ScrollBarPolicy.ALWAYS);
		exerciseListScrollPane.setMinWidth(screenWidth*0.5);		
		exerciseListScrollPane.setMinHeight(screenHeight*0.5);
		
		getChildren().add(exerciseListScrollPane);
		
			
	}
}
