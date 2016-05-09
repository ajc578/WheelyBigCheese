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

public class WorkoutEndCard extends VBox{
	
	public WorkoutEndCard (double screenWidth, double screenHeight, BorderPane root,
			ArrayList<ExerciseInfo> completedExercises) {
			
			putImage(screenWidth, screenHeight);
			
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
					Menu menu = new Menu(screenWidth, screenHeight, root);
					WorkoutMenu workout = new WorkoutMenu(screenWidth, screenHeight, root);
					try {
						root.setTop(menu);
						root.setCenter(workout);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			
			});
			
			setNodeCursor(returnButton);
			
		}
	
	public void putImage(double screenWidth, double screenHeight) {
		HBox imageBox = new HBox();
		imageBox.setAlignment(Pos.TOP_CENTER);
		imageBox.setId("image-box");
		
		Image prodLogo = new Image("res/images/product_logo.jpg");
		ImageView prodLogoView = new ImageView(prodLogo);
		prodLogoView.setImage(prodLogo);
		prodLogoView.setFitWidth(screenWidth*0.4);
		prodLogoView.setFitHeight(screenHeight*0.125);
		
		imageBox.setAlignment(Pos.TOP_CENTER);
		imageBox.getChildren().addAll(prodLogoView);
		getChildren().addAll(imageBox);
		
	}
	
	public void setNodeCursor (Node node) {
		
		node.setOnMouseEntered(event -> setCursor(Cursor.HAND));
		node.setOnMouseExited(event -> setCursor(Cursor.DEFAULT));
	}
}
