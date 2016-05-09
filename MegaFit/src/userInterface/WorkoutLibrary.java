package userInterface;

import java.io.File;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class WorkoutLibrary extends VBox {
	
	File workoutFolder;
	VBox exerciseList;
	
	BorderPane root = new BorderPane();
	
	ScrollPane scrollPane = new ScrollPane();

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
		
	
		
		getChildren().add(scrollPane);
		scrollPane.setPadding(new Insets(screenHeight*0.05, screenWidth*0.05, screenHeight*0.05, screenWidth*0.05));
		
		putBackImageButton(screenWidth, screenHeight, root);
		
	}
	
	public void putBackImageButton (double screenWidth, double screenHeight, BorderPane root) {
		
		WorkoutMenu workoutMenu = new WorkoutMenu (screenWidth, screenHeight, root);
		
		HBox buttonImageBox = new HBox();
		buttonImageBox.setAlignment(Pos.BOTTOM_LEFT);
		
		Image backButton = new Image("res/images/back_arrow.jpg");
		ImageView buttonImageView = new ImageView(backButton);
		buttonImageView.setImage(backButton);
		buttonImageView.setFitWidth(screenWidth*0.05);
		buttonImageView.setFitHeight(screenHeight*0.05);
		
		buttonImageView.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				getChildren().add(workoutMenu);
				getChildren().removeAll(scrollPane, buttonImageBox);
				/* prevents from overwriting the insets */
				setPadding(new Insets(0, 0, 0, 0));
			}
			
		});
		
		buttonImageView.setOnMouseEntered(event -> setCursor(Cursor.HAND));
		
		buttonImageBox.getChildren().add(buttonImageView);
		buttonImageBox.setPadding(new Insets(screenHeight*0.1, screenWidth*0.9, 0, 0));
		getChildren().add(buttonImageBox);
		
	}
	
	
}
