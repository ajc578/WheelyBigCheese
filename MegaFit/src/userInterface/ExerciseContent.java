package userInterface;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.geometry.Insets;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class ExerciseContent extends HBox {

	Label exerciseLabel;
	Label descriptionLabel;
	Label selectedExercise;
	String selectedRep;
	String selectedEx;
	Label selectedAmount;
	Button addExercise, remove;
	TextField amount;
	HBox selectedItem;
	
	public ExerciseContent(double screenWidth, double screenHeight, String exercise, String description, 
			VBox workoutBuilder) {
	
		
		
		exerciseLabel = new Label(exercise);
		exerciseLabel.setMinWidth(screenWidth*0.1);
		
		descriptionLabel = new Label(description);
		descriptionLabel.setMinWidth(screenWidth*0.2);
		
		amount = new TextField();
		amount.setPrefWidth(screenWidth*0.05);
		
		addExercise = new Button("ADD");
		addExercise.setPrefSize(screenWidth*0.05, screenHeight*0.025);
		
		remove = new Button("REMOVE");
		remove.setPrefSize(screenWidth*0.05, screenHeight*0.025);
		
		addExercise.setOnAction(new EventHandler<ActionEvent>(){

			public void handle(ActionEvent event) {
				selectedItem = new HBox();
				selectedEx = exerciseLabel.getText();
				selectedExercise = new Label(selectedEx);
				selectedExercise.setMinWidth(screenWidth*0.1);
				selectedRep = amount.getText();
				selectedAmount = new Label(selectedRep);
				selectedAmount.setMinWidth(screenWidth*0.05);
				remove = new Button("REMOVE");
				remove.setPrefSize(screenWidth*0.1, screenHeight*0.025);
				selectedItem.getChildren().addAll(selectedExercise, selectedAmount, remove);
				selectedItem.setSpacing(screenWidth*0.01);
				workoutBuilder.getChildren().addAll(selectedItem);
				workoutBuilder.setSpacing(screenWidth*0.01);
				workoutBuilder.setPadding(new Insets(0, 0, 0, screenWidth*0.01));
				remove.setOnAction(new EventHandler<ActionEvent>(){
					
					public void handle (ActionEvent event){
						selectedItem.getChildren().removeAll(selectedExercise, selectedAmount, remove);
					}
				});
				
			}	
		});
		
		
		getChildren().addAll(exerciseLabel, descriptionLabel, amount, addExercise);
		setWidth(screenWidth*0.4);
		setHeight(screenHeight*0.2);
		setSpacing(screenWidth*0.01);
		setPadding(new Insets(0, 0, 0, screenWidth*0.01));
	}
	
	
	
}
