package userInterface;

import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import presentationViewer.PresentationFx;

public class WorkoutLibrary extends HBox {
	
	File workoutFolder;
	ScrollPane exerciseListScrollPane;
	VBox exerciseList;

	public WorkoutLibrary (double screenWidth, double screenHeight, BorderPane root){
		workoutFolder = new File("src/res/xml");
		exerciseList = new VBox();
		exerciseListScrollPane = new ScrollPane();
		
		WorkoutLibrary self = this;
		
		for(File i : workoutFolder.listFiles()){
			String filename = i.getName();
			if(filename.toUpperCase().endsWith("WORKOUT.XML"))
			{
				Button tempButton = new Button(filename);
				tempButton.setOnAction(new EventHandler<ActionEvent>(){
					
					public void handle (ActionEvent event){

						//create and add all slides to presentation
						PresentationFx tempPresent = new PresentationFx(filename);
						
						//when the presentation finishes, close the application
						tempPresent.addActionListener(new ActionListener(){

							@Override
							public void actionPerformed(java.awt.event.ActionEvent e) {
								root.setCenter(self);
								
							}
							
						});
						
						
					   root.setCenter(tempPresent.Play(screenWidth, screenHeight));
						
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
