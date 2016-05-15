package userInterface;

import java.io.File;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import userInterface.wkoutpage.model.Workout;

public class WorkoutLibrary extends VBox {
	
	File workoutFolder;
	VBox exerciseList;


	BorderPane root = new BorderPane();
	
	ScrollPane scrollPane = new ScrollPane();
	
	Button backButton;
	
	Image back;
	
	ImageView buttonImageView;

	public WorkoutLibrary (double screenWidth, double screenHeight, BorderPane root){
		workoutFolder = new File("src/res/xml");
		exerciseList = new VBox();
		
		for(File i : workoutFolder.listFiles()){
			String filename = i.getName();
			if(filename.toUpperCase().endsWith("WORKOUT.XML"))
			{
				WorkoutDetails workoutView = new WorkoutDetails(screenWidth, screenHeight, filename, root);
				exerciseList.getChildren().add(workoutView);
				
			}
		}
		
		exerciseList.setSpacing(screenHeight*0.05);
		exerciseList.setPadding(new Insets(screenHeight*0.01, screenWidth*0.02, screenHeight*0.05, screenWidth*0.02));
		
		scrollPane.setContent(exerciseList);
		
		scrollPane.setHbarPolicy(ScrollBarPolicy.NEVER);
		scrollPane.setVbarPolicy(ScrollBarPolicy.ALWAYS);
		
				
		scrollPane.setMinWidth(screenWidth*0.85);		
		scrollPane.setMinHeight(screenHeight*0.7);
		scrollPane.setMaxWidth(screenWidth*0.85);		
		scrollPane.setMaxHeight(screenHeight*0.7);
		scrollPane.setPadding(new Insets(screenHeight*0.05, screenWidth*0.05, screenHeight*0.05, screenWidth*0.05));
	
		
		back = new Image("res/images/backButton.png");
		buttonImageView = new ImageView(back);
		buttonImageView.setImage(back);
		buttonImageView.setFitWidth(screenWidth*0.05);
		buttonImageView.setFitHeight(screenHeight*0.05);
		backButton = new Button ("", buttonImageView);
		
		backButton.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent event){
				WorkoutMenu workoutMenu = new WorkoutMenu (screenWidth, screenHeight, root);
				try{
					root.setBottom(workoutMenu);
				} catch (Exception e){
					e.printStackTrace();
				}
			}
		});
		
		setNodeCursor(backButton);
		

		getChildren().addAll(scrollPane, backButton);
		
		
		
	}
	public void setNodeCursor (Node node) {
		node.setOnMouseEntered(event -> setCursor(Cursor.HAND));
		node.setOnMouseExited(event -> setCursor(Cursor.DEFAULT));
	}
	
	
}
