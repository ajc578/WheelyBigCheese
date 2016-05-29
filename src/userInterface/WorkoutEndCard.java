package userInterface;

import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import parser.ExerciseInfo;

public class WorkoutEndCard extends VBox implements Controllable {

	private StackPaneUpdater screenParent;
	private Main mainApp;

	public WorkoutEndCard (double screenWidth, double screenHeight,
						   ArrayList<ExerciseInfo> completedExercises) {
			
			getChildren().add(new Label("You completed:"));
			
			
			System.out.println("You completed:");
			int pointsTotal = 0;
			for (ExerciseInfo exercise : completedExercises) {
				System.out.println(exercise.getName() + ": " +
						   exercise.getSets() + " sets of " +
						   exercise.getReps() + " reps, for " +
						   exercise.getPoints() + " points.");
				pointsTotal += exercise.getPoints();
			}
			System.out.println(new Label("For a total of: " + pointsTotal + " points."));



			pointsTotal = 0;
			for (ExerciseInfo exercise : completedExercises) {
				getChildren().add(new Label(exercise.getName() + ": " +
						   exercise.getSets() + " sets of " +
						   exercise.getReps() + " reps, for " +
						   exercise.getPoints() + " points."));
				pointsTotal += exercise.getPoints();
			}
			getChildren().add(new Label("For a total of: " + pointsTotal + " points."));
			
			Button returnButton = new Button("Return to menu");
			getChildren().add(returnButton);
			returnButton.setOnAction(new EventHandler<ActionEvent>() {


				@Override
				public void handle(ActionEvent event) {
					screenParent.setScreen(Main.workoutLibraryID);
				}
			
			});
			
			setNodeCursor(returnButton);
			
		}
		
	public void setNodeCursor (Node node) {
		
		node.setOnMouseEntered(event -> setCursor(Cursor.HAND));
		node.setOnMouseExited(event -> setCursor(Cursor.DEFAULT));
	}

	@Override
	public void setScreenParent(StackPaneUpdater screenParent) {
		this.screenParent = screenParent;
	}

	@Override
	public void setMainApp(Main mainApp) {
		this.mainApp = mainApp;
	}
}
