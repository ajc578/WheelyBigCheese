package userInterface;

import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.geometry.Insets;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

public class ExerciseContent extends HBox {

	Label exerciseLabel;
	Label descriptionLabel;
	Label selectedExercise;
	Label selectedRepAmount;
	Label selectedSetAmount;
	String selectedRep;
	String selectedSet;
	String selectedEx;
	Button addExercise, remove;
	TextField repAmount, setAmount;
	HBox selectedItem;
	
	public ExerciseContent(double screenWidth, double screenHeight, String exercise, String description, 
			VBox workoutBuilder) {
	
		
		
		exerciseLabel = new Label(exercise);
		exerciseLabel.setMinWidth(screenWidth*0.1);
		exerciseLabel.setMinWidth(screenWidth*0.075);
		
		descriptionLabel = new Label(description);
		descriptionLabel.setMinWidth(screenWidth*0.2);
		descriptionLabel.setMaxWidth(screenWidth*0.2);
		descriptionLabel.setWrapText(true);
		
		repAmount = new TextField();
		repAmount.setPrefWidth(screenWidth*0.05);
		
		setAmount = new TextField();
		setAmount.setPrefWidth(screenWidth*0.05);
		
		addExercise = new Button("ADD");
		addExercise.setPrefSize(screenWidth*0.05, screenHeight*0.025);

		setNodeCursor(addExercise);
		
		
		
		//remove.setPrefSize(screenWidth*0.05, screenHeight*0.025);
		
		addExercise.setOnAction(new EventHandler<ActionEvent>(){

			public void handle(ActionEvent event) {
				selectedItem = new HBox();
				selectedEx = exerciseLabel.getText();
				selectedExercise = new Label(selectedEx);
				selectedExercise.setMinWidth(screenWidth*0.1);
				selectedRep = repAmount.getText();
				selectedRepAmount = new Label(selectedRep);
				selectedRepAmount.setMinWidth(screenWidth*0.05);
				selectedSet = setAmount.getText();
				selectedSetAmount = new Label(selectedSet);
				selectedSetAmount.setMinWidth(screenWidth*0.05);
				remove = new Button("REMOVE");
				remove.setPrefSize(screenWidth*0.1, screenHeight*0.025);
				
				selectedItem.getChildren().addAll(selectedExercise, selectedRepAmount, selectedSetAmount, remove);
				selectedItem.setSpacing(screenWidth*0.005);
				workoutBuilder.getChildren().addAll(selectedItem);
				workoutBuilder.setSpacing(screenWidth*0.01);
				workoutBuilder.setSpacing(screenWidth*0.005);
				workoutBuilder.setPadding(new Insets(0, 0, 0, screenWidth*0.01));
				remove.setOnAction(new EventHandler<ActionEvent>(){
					
					public void handle (ActionEvent event){
					if (selectedRep != "")
							selectedItem.getChildren().removeAll(selectedExercise, selectedRepAmount, selectedSetAmount, remove);
					}
				});
				remove.setOnMouseEntered(new EventHandler<MouseEvent>() {
				    public void handle(MouseEvent event) {
				        setCursor(Cursor.HAND); //Change cursor to hand
				    }
				});
				
			}
			
			
			
		});
		
		
		getChildren().addAll(exerciseLabel, descriptionLabel, repAmount, setAmount, addExercise);
		setWidth(screenWidth*0.4);
		setHeight(screenHeight*0.2);
		setSpacing(screenWidth*0.01);
		setPadding(new Insets(0, 0, 0, screenWidth*0.01));
	}
	
	public void setNodeCursor (Node node) {
		
		node.setOnMouseEntered(event -> setCursor(Cursor.HAND));
		node.setOnMouseExited(event -> setCursor(Cursor.DEFAULT));
	}
	
}
