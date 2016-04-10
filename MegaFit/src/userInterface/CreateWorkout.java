package userInterface;

import javafx.geometry.Insets;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;

public class CreateWorkout extends HBox {
	
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
	
	public CreateWorkout(double screenWidth, double screenHeight){		
		
		
		exerciseSearch = new VBox();
		workoutBuilder = new VBox();
		searchArea = new VBox();
		builderArea = new VBox();		
		searchBox = new ScrollPane();
		workoutBox = new ScrollPane();		
		beginWorkout = new Button("START");
		beginWorkout.setPrefSize(screenWidth*0.3, screenHeight*0.05);
		
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
		getChildren().addAll(searchArea, builderArea);
		setSpacing(screenWidth*0.1);
		setPadding(new Insets(screenHeight*0.1, screenWidth*0.05, screenHeight*0.1, screenWidth*0.05));
		
	}	
}
		
		