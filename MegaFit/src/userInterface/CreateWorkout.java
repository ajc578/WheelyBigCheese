package userInterface;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class CreateWorkout extends VBox {
	
	/* create arrays of type String for the exercises and descriptions, they are
	 * repeated only to show the scroll pane works. These will eventually be in 
	 * an XML file that will be parsed into the code.*/
	String[] exercises = {
			"Hammer Curls",
			"Bodyweight Squat",
			"Stiff-legged deadlift",
			"Pull-ups",
			"Hammer Curls",
			"Bodyweight Squat",
			"Stiff-legged deadlift",
			"Pull-ups",
			"Hammer Curls",
			"Bodyweight Squat",
			"Stiff-legged deadlift",
			"Pull-ups",
			"Hammer Curls",
			"Bodyweight Squat",
			"Stiff-legged deadlift",
			"Pull-ups"
	};
	String[] descriptions = {
			"Targets the bicep.",
			"Targets the glutes and quads.",
			"Targets the glutes and hamstrings.",
			"Targets the shoulders and upper back.",
			"Targets the bicep.",
			"Targets the glutes and quads.",
			"Targets the glutes and hamstrings.",
			"Targets the shoulders and upper back.",
			"Targets the bicep.",
			"Targets the glutes and quads.",
			"Targets the glutes and hamstrings.",
			"Targets the shoulders and upper back.",
			"Targets the bicep.",
			"Targets the glutes and quads.",
			"Targets the glutes and hamstrings.",
			"Targets the shoulders and upper back."
	};
		
	TextField nameWorkout;
	VBox exerciseSearch, searchArea, workoutBuilder, builderArea;
	ScrollPane searchBox;
	ScrollPane workoutBox;
	String selectedExercise;
	String selectedAmount;
	Button beginWorkout;
	
	HBox areasBox = new HBox();
	
	public CreateWorkout(double screenWidth, double screenHeight){		
		
		BorderPane root = new BorderPane();
		
		exerciseSearch = new VBox();
		workoutBuilder = new VBox();
		searchArea = new VBox();
		builderArea = new VBox();		
		searchBox = new ScrollPane();
		workoutBox = new ScrollPane();		
		beginWorkout = new Button("START");
		beginWorkout.setPrefSize(screenWidth*0.3, screenHeight*0.05);
		setNodeCursor(beginWorkout);
		
		/* set the contents of the workout VBox to be the selected 
		 * exercises, disable the horizontal scroll and set the vertical 
		 * one to always be visible and set the minimum width and height
		 * so the box is always the same size.*/
		workoutBox.setContent(workoutBuilder);
		workoutBox.setHbarPolicy(ScrollBarPolicy.NEVER);
		workoutBox.setVbarPolicy(ScrollBarPolicy.ALWAYS);
		workoutBox.setMinWidth(screenWidth*0.3);		
		workoutBox.setMinHeight(screenHeight*0.5);
		
		/* create text fields for naming the workout that's being created.*/
		nameWorkout = new TextField("Name Workout...");
		
		/* for loop that cycles through the content builder that adds everything
		 * in the arrays to HBoxes and add those HBoxes to an overall VBox.*/		
		for(int i=0; i<exercises.length; i++){
			ExerciseContent searchContent = new ExerciseContent(screenWidth, screenHeight, exercises[i],
			descriptions[i], workoutBuilder);
			exerciseSearch.getChildren().add(searchContent);
			
		}
		
		/* set the spacing of the search VBox so that items aren't bunched together*/
		exerciseSearch.setSpacing(screenHeight*0.05);
		
		/* set the content of the search scroll box to be the list of available
		 * exercises, disable the horizontal scroll bar and set the minimum width and height
		 * so the box is always the same size.*/
		searchBox.setContent(exerciseSearch);		
		searchBox.setHbarPolicy(ScrollBarPolicy.NEVER);		
		searchBox.setMinWidth(screenWidth*0.5);		
		searchBox.setMinHeight(screenHeight*0.5);
		
		/* set the content of the searchArea VBox to be the search text field and the
		 * scroll box with the available exercises.*/
		searchArea.getChildren().add(searchBox);
		searchArea.setSpacing(screenHeight*0.01);
		
		/* set the content of the builderArea VBox to be the text field for naming the 
		 * workout, the scroll box with the selected exercises and the buton to being
		 * the workout.*/
		builderArea.getChildren().addAll(nameWorkout, workoutBox, beginWorkout);
		builderArea.setSpacing(screenHeight*0.01);
		
		/* set the content of the overall HBox to be the search and builder areas and
		 * set the spacing and padding so that there is space around the edge of each 
		 * item in the HBox.*/
		
		ImageView backButton = BackImageButton (screenWidth, screenHeight);
		setNodeCursor(backButton);
		HBox backButtonBox = new HBox();
		backButtonBox.getChildren().add(backButton);
		
		backButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			WorkoutMenu workoutMenu = new WorkoutMenu (screenWidth, screenHeight, root);
			@Override
			public void handle(MouseEvent event) {
				getChildren().add(workoutMenu);
				getChildren().removeAll(areasBox, backButtonBox);
				/* prevents from overwriting the insets */
				setPadding(new Insets(0, 0, 0, 0));
			}
			
		});
		areasBox.getChildren().addAll(searchArea, builderArea);
		areasBox.setSpacing(screenWidth*0.05);
		getChildren().addAll(areasBox, backButtonBox);
		setSpacing(screenHeight*0.05);
		setPadding(new Insets(screenHeight*0.05, screenWidth*0.05, screenHeight*0.05, screenWidth*0.05));
		
	
	}	
	
	public ImageView BackImageButton (double screenWidth, double screenHeight) {
		
		HBox buttonImageBox = new HBox();
		buttonImageBox.setAlignment(Pos.BOTTOM_LEFT);
		
		Image backButton = new Image("res/images/back_arrow.jpg");
		ImageView buttonImageView = new ImageView(backButton);
		buttonImageView.setImage(backButton);
		buttonImageView.setFitWidth(screenWidth*0.05);
		buttonImageView.setFitHeight(screenHeight*0.05);
		
		return buttonImageView;
	
	}
	
	public void setNodeCursor (Node node) {
		
		node.setOnMouseEntered(event -> setCursor(Cursor.HAND));
		node.setOnMouseExited(event -> setCursor(Cursor.DEFAULT));
	}
	
}
		
		