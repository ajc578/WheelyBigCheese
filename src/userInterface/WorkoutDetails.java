package userInterface;

import java.awt.event.ActionListener;
import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import parser.ExerciseInfo;
import parser.XMLParser;
import presentationViewer.PresentationFx;

public class WorkoutDetails extends GridPane{
	
	boolean playbackMode;
	
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
	    setNodeCursor(beginWorkout);
	    beginWorkout.setOnAction(new EventHandler<ActionEvent>(){
			
			public void handle (ActionEvent event){

				//create and add all slides to presentation
				PresentationFx workoutPresent = new PresentationFx(filename);
				
				//when the presentation finishes, close the application
				workoutPresent.addActionListener(new ActionListener(){


					@Override
					public void actionPerformed(java.awt.event.ActionEvent e) {
						WorkoutEndCard endCard = new WorkoutEndCard (screenWidth, screenHeight,
								workoutPresent.getCompletedExercises());
						

					}
					
				});
				
				BorderPane presentationPane = new BorderPane();
				GridPane presentationControlPane = new GridPane();
				presentationPane.setTop(presentationControlPane);
				presentationControlPane.setHgap(screenWidth*0.001);
				presentationControlPane.setVgap(screenWidth*0.001);
				
				Label title = new Label(workoutPresent.getTitle());
				title.setAlignment(Pos.CENTER);
				title.setFont(Font.font(40));
				presentationControlPane.add(title, 2, 0,2,1);
				title.setPrefSize(screenWidth*0.4, screenHeight*0.1);
				
				Button quitPresentation = new Button("QUIT");
				setNodeCursor(quitPresentation);
				quitPresentation.setPrefSize(screenWidth*0.3, screenHeight*0.1);
				quitPresentation.setOnAction(new EventHandler<ActionEvent>(){
	
					@Override
					public void handle(ActionEvent event) {
						workoutPresent.quit();
					}	
				});
				presentationControlPane.add(quitPresentation, 0, 0,2,1);
				
				Button advanceManual = new Button("next");
				advanceManual.setPrefSize(screenWidth*0.15, screenHeight*0.1);
				setNodeCursor(advanceManual);
				advanceManual.setOnAction(new EventHandler<ActionEvent>(){
					
					@Override
					public void handle(ActionEvent event) {
						workoutPresent.advanceManualEvents();
					}	
				});
				
				Button changeModePresentation = new Button("Switch to Manual Play");
				changeModePresentation.setPrefSize(screenWidth*0.3, screenHeight*0.1);
				setNodeCursor(changeModePresentation);
				playbackMode = true;
				presentationControlPane.add(changeModePresentation, 4, 0,2,1);
				changeModePresentation.setOnAction(new EventHandler<ActionEvent>(){
	
					@Override
					public void handle(ActionEvent event) {

						if (playbackMode == true){
							playbackMode = false;
							presentationControlPane.getChildren().remove(changeModePresentation);
							presentationControlPane.getChildren().remove(advanceManual);
							changeModePresentation.setText("Switch to Auto Play");
							changeModePresentation.setPrefSize(screenWidth*0.15, screenHeight*0.1);
							presentationControlPane.add(changeModePresentation, 4, 0,1,1);
							presentationControlPane.add(advanceManual, 5, 0,1,1);
							workoutPresent.setManualPlay();
						}else{
							playbackMode = true;
							presentationControlPane.getChildren().remove(changeModePresentation);
							presentationControlPane.getChildren().remove(advanceManual);
							changeModePresentation.setText("Switch to Manual Play");
							changeModePresentation.setPrefSize(screenWidth*0.3, screenHeight*0.1);
							presentationControlPane.add(changeModePresentation, 4, 0,2,1);
							workoutPresent.setAutoPlay();
						}						
					}	
				});
				
				presentationPane.setBottom(workoutPresent.Play(screenWidth, screenHeight*0.9));
				root.setBottom(presentationPane);
			}
		});    
	}
	
	public void setNodeCursor (Node node) {
		
		node.setOnMouseEntered(event -> setCursor(Cursor.HAND));
		node.setOnMouseExited(event -> setCursor(Cursor.DEFAULT));
	}
	
}
