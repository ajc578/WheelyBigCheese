package userInterface;

import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import parser.ExerciseInfo;

public class WorkoutEndCard extends VBox implements Controllable {

	private ScreenFlowController screenParent;
	private Main mainApp;

	public WorkoutEndCard (double screenWidth, double screenHeight,
						   ArrayList<ExerciseInfo> completedExercises) {
			
			getChildren().add(new Label("You completed:"));
			
			
			int pointsTotal = 0;
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
					screenParent.setScreen(Main.workoutMenuID);
				}
			
			});
			
			setNodeCursor(returnButton);
			
		}
		
	public void setNodeCursor (Node node) {
		
		node.setOnMouseEntered(event -> setCursor(Cursor.HAND));
		node.setOnMouseExited(event -> setCursor(Cursor.DEFAULT));
	}

	@Override
	public void setScreenParent(ScreenFlowController screenParent) {
		this.screenParent = screenParent;
	}

	@Override
	public void setMainApp(Main mainApp) {
		this.mainApp = mainApp;
	}
}
