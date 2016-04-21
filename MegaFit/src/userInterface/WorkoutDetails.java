package userInterface;

import java.awt.event.ActionListener;
import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import parser.ExerciseInfo;
import parser.XMLParser;
import presentationViewer.PresentationFx;

public class WorkoutDetails extends GridPane{

	public WorkoutDetails (double screenWidth, double screenHeight, String filename, BorderPane root){
		
		XMLParser parser = new XMLParser(filename);
		String title = parser.getDocumentInfo().getTitle();
		String author = parser.getDocumentInfo().getAuthor();
		String comment = parser.getDocumentInfo().getComment();
		ArrayList<ExerciseInfo> exercises = XMLParser.retrieveWorkoutInfo(filename).getExerciseList();
		
		this.setHgap(screenWidth*0.001);
	    this.setVgap(screenWidth*0.001);
	    
	    //add the title field with label
	    this.add(new Label("Title:"), 0, 0);
	    this.add(new Label(title), 1, 0);
	    
	    //add author field with label
	    this.add(new Label("Author:"), 3, 0);
	    this.add(new Label(author), 4, 0);
	    
	    //add comment field with label
	    this.add(new Label("Comment:"), 6, 0);
	    this.add(new Label(comment),7,0,1,2);
	    
	    //add what exercises are contained in the workout
	    this.add(new Label("Workout Details:"), 3, 1,2,1);
	    int row = 2;
	    for (ExerciseInfo exercise: exercises) {
			if (exercise.getName() != null){
				this.add(new Label(exercise.getName() + ": " +
								   exercise.getSets() + " sets of " +
								   exercise.getReps() + " reps, for " +
								   exercise.getPoints() + " points."), 0, row,8,1);
				row++;
			}
		}
	    
	    //button to begin the workout
	    Button beginWorkout = new Button("BEGIN");
	    this.add(beginWorkout, 0, 1);
	    beginWorkout.setOnAction(new EventHandler<ActionEvent>(){
			
			public void handle (ActionEvent event){

				//create and add all slides to presentation
				PresentationFx tempPresent = new PresentationFx(filename);
				
				//when the presentation finishes, close the application
				tempPresent.addActionListener(new ActionListener(){

					@Override
					public void actionPerformed(java.awt.event.ActionEvent e) {
						//TODO
						WorkoutEndCard endCard = new WorkoutEndCard (screenWidth, screenHeight, root,
								tempPresent.getCompletedExercises());
						
						root.setTop(endCard);
						root.setCenter(null);
					}
					
				});
				
				root.setCenter(tempPresent.Play(screenWidth, screenHeight*0.9));
				
				Button quitPresentation = new Button("QUIT");
				quitPresentation.setPrefSize(screenWidth*0.9, screenHeight*0.1);
				quitPresentation.setOnAction(new EventHandler<ActionEvent>(){
	
					@Override
					public void handle(ActionEvent event) {
						tempPresent.quit();
					}	
				});
				
				root.setTop(quitPresentation);
			   
			}
		});
	    
	    
	}
	
}
