package userInterface;

import java.io.File;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import parser.XMLParser;

public class ExerciseContent extends HBox {

	Label exerciseLabel;
	Label descriptionLabel;
	Label selectedExercise;
	Label selectedRepAmount;
	Label selectedSetAmount;
	File sourceFile;
	String selectedRep;
	String selectedSet;
	String selectedEx;
	Button addExercise, remove;
	TextField repAmount, setAmount;
	HBox selectedItem;
	
	public ExerciseContent(double screenWidth, double screenHeight, File sourceFile, 
			CreateWorkout workoutBuilder) {
		
		this.sourceFile = sourceFile;
	
		XMLParser parser = new XMLParser(sourceFile.getName());
		
		exerciseLabel = new Label(parser.getDocumentInfo().getTitle());
		exerciseLabel.setMinWidth(screenWidth*0.1);
		exerciseLabel.setMinWidth(screenWidth*0.075);
		
		descriptionLabel = new Label(parser.getDocumentInfo().getComment());
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
		
		addExercise.setOnAction(new EventHandler<ActionEvent>(){

			public void handle(ActionEvent event) {
				
				if((isInt(setAmount.getText()))&&(isInt(repAmount.getText()))){
				workoutBuilder.addToList(sourceFile.getName(), exerciseLabel.getText(),
						Integer.parseInt(setAmount.getText()), Integer.parseInt(repAmount.getText()));
				}
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
	
	public static boolean isInt(String str){
	    
		if(!str.isEmpty()){
		try
	    {
	      int i = Integer.parseInt(str);
	    }
	    catch(NumberFormatException nfe)
	    {
	      return false;
	    }
	    return true;
	    }else return false;
	}
	
}
