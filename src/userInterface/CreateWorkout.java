package userInterface;

import javafx.scene.control.Label;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.TextField;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Class defining the layout and functionality of the page where
 * the user creates their own workouts.
 * 
 * @author - company - B.O.S.S
 * @author - coders - Jennifer Thorpe, Kamil Sledziewski
 * 
 */

public class CreateWorkout extends VBox implements Controllable {

	private StackPaneUpdater screenParent;
	private Main mainApp;
	
	/* create arrays of type String for the exercises and descriptions, they are
	 * repeated only to show the scroll pane works. These will eventually be in 
	 * an XML file that will be parsed into the code.*/
	String[] exercises = {
			"Plank",
			"Wall Plank",
			"Side Plank",
			"Dumbbell Shoulder Press",
			"Barbell Squat",
			"Dumbbell Bench Press"
	};
	String[] descriptions = {
			"Full body exercise that primarily targets the abdominals, obliques, shoulders and quads. Also engages lower back, calves and glutes. The time for this exercise is entered in seconds.",
			"blah blah blah.",
			"blah blah blah.",
			"blah blah blah.",
			"blah blah blah.",
			"blah blah blah.",
			
	};
		
	TextField nameWorkout, searchText;
	VBox exerciseSearch, searchArea, workoutBuilder, builderArea;
	ScrollPane searchBox;
	ScrollPane workoutBox;
	String selectedExercise;
	String selectedAmount;
	Button beginWorkout;
	Label name, description, amount, sets;
	HBox areasBox, labelsBox;
	
	public CreateWorkout(double screenWidth, double screenHeight){		
		
		
		areasBox = new HBox();
		
		BorderPane root = new BorderPane();
		
		searchText = new TextField("Search...");
		exerciseSearch = new VBox();
		workoutBuilder = new VBox();
		searchArea = new VBox();
		builderArea = new VBox();		
		searchBox = new ScrollPane();
		name = new Label ("Exercise Name");
		name.setPadding(new Insets(0, 0, 0, screenWidth*0.01));
		description = new Label ("Exercise Description");
		description.setPadding(new Insets(0, 0, 0, screenWidth*0.1));
		amount = new Label("Reps/time/distance");
		amount.setPadding(new Insets(0, 0, 0, screenWidth*0.11));
		sets = new Label("number of sets");
		sets.setPadding(new Insets(0, 0, 0, screenWidth*0.012));
		workoutBox = new ScrollPane();		
		beginWorkout = new Button("START");
		beginWorkout.setPrefSize(screenWidth*0.3, screenHeight*0.05);
		setNodeCursor(beginWorkout);
		
		labelsBox = new HBox();
		labelsBox.getChildren().addAll(name, description, amount, sets);
		
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
		searchBox.setVbarPolicy(ScrollBarPolicy.ALWAYS);
		searchBox.setMinWidth(screenWidth*0.5);		
		searchBox.setMinHeight(screenHeight*0.5);
		searchBox.setMaxHeight(screenHeight*0.6);
		
		/* set the content of the searchArea VBox to be the search text field and the
		 * scroll box with the available exercises.*/
		searchArea.getChildren().addAll(searchText, labelsBox, searchBox);
		searchArea.setSpacing(screenHeight*0.01);
		
		/* set the content of the builderArea VBox to be the text field for naming the 
		 * workout, the scroll box with the selected exercises and the buton to being
		 * the workout.*/
		builderArea.getChildren().addAll(nameWorkout, workoutBox, beginWorkout);
		builderArea.setSpacing(screenHeight*0.01);
		
		/* set the content of the overall HBox to be the search and builder areas and
		 * set the spacing and padding so that there is space around the edge of each 
		 * item in the HBox.*/
		
		
		Image back = new Image("res/images/backButton.png");
		ImageView buttonImageView = new ImageView(back);
		buttonImageView.setImage(back);
		buttonImageView.setFitWidth(screenWidth*0.05);
		buttonImageView.setFitHeight(screenHeight*0.05);
		Button backButton = new Button("", buttonImageView);
			
		backButton.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent event) {
				screenParent.setScreen(Main.workoutMenuID);
			}	
		});
		
		setNodeCursor(backButton);
			
		areasBox.getChildren().addAll(searchArea, builderArea);
		areasBox.setSpacing(screenWidth*0.05);
		getChildren().addAll(areasBox, backButton);
		setSpacing(screenHeight*0.05);
		setPadding(new Insets(screenHeight*0.05, screenWidth*0.05, screenHeight*0.05, screenWidth*0.05));
		
	
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
		
		