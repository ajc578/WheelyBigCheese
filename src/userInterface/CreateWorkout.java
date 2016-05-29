package userInterface;

import javafx.scene.control.Label;

import java.io.File;
import java.util.ArrayList;

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
import javafx.scene.input.MouseEvent;

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
	
	private double screenWidth, screenHeight;
	
	private TextField nameWorkout, searchText;
	private VBox exerciseSearch, searchArea, workoutBuilder, builderArea;
	private ScrollPane searchBox;
	private ScrollPane workoutBox;
	private String selectedExercise;
	private String selectedAmount;
	private Button beginWorkout;
	private Label name, description, amount, sets;
	private HBox areasBox, labelsBox;
	private ArrayList<SelectedInfo> chosenExercises;
	
	public CreateWorkout(double screenWidth, double screenHeight){		
		
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;
		
		areasBox = new HBox();
			
		chosenExercises = new ArrayList<SelectedInfo>();
		searchText = new TextField();
		searchText.setPromptText("Search...");
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
		
		File folder = new File("src/res/xml");
		File[] listOfFiles = folder.listFiles();
		
		/* for loop that cycles through the content builder that adds everything
		 * in the arrays to HBoxes and add those HBoxes to an overall VBox.*/		
		for(File sourceFile: listOfFiles) {
			if (sourceFile.toString().toUpperCase().endsWith("EXERCISE.XML")) {
			ExerciseContent searchContent = new ExerciseContent(screenWidth, screenHeight, sourceFile, this);
			exerciseSearch.getChildren().add(searchContent);
			}	
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
				screenParent.setScreen(Main.workoutLibraryID);
			}	
		});
		
		setNodeCursor(backButton);
		
		beginWorkout.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent event) {
				if (chosenExercises.size() != 0){
					
				}
			}	
		});
		
		setNodeCursor(beginWorkout);
			
		areasBox.getChildren().addAll(searchArea, builderArea);
		areasBox.setSpacing(screenWidth*0.05);
		getChildren().addAll(areasBox, backButton);
		setSpacing(screenHeight*0.05);
		setPadding(new Insets(screenHeight*0.05, screenWidth*0.05, screenHeight*0.05, screenWidth*0.05));
		
	
	}	
	
	public void addToList(String fileName, String name, int sets, int reps){
		chosenExercises.add(new SelectedInfo(fileName,name,sets,reps));
		updateWorkoutBuilder();
	}
	
	public void updateWorkoutBuilder(){
		
		workoutBuilder.getChildren().clear();
		
		workoutBuilder.setSpacing(screenWidth*0.01);
		workoutBuilder.setSpacing(screenWidth*0.005);
		workoutBuilder.setPadding(new Insets(0, 0, 0, screenWidth*0.01));
		
		for (SelectedInfo selectedInfo : chosenExercises) {
			HBox selectedItem = new HBox();
			Label tempName = new Label(selectedInfo.name);
			tempName.setMinWidth(screenWidth*0.1);
			Label tempRepAmount = new Label(Integer.toString(selectedInfo.reps));
			tempRepAmount.setMinWidth(screenWidth*0.05);
			Label tempSetAmount = new Label(Integer.toString(selectedInfo.sets));
			tempSetAmount.setMinWidth(screenWidth*0.05);
			Button remove = new Button("REMOVE");
			remove.setPrefSize(screenWidth*0.1, screenHeight*0.025);
			
			selectedItem.getChildren().addAll(tempName, tempRepAmount, tempSetAmount, remove);
			selectedItem.setSpacing(screenWidth*0.005);
			workoutBuilder.getChildren().addAll(selectedItem);
			remove.setOnAction(new EventHandler<ActionEvent>(){
				
				public void handle (ActionEvent event){
					chosenExercises.remove(selectedInfo);
					updateWorkoutBuilder();
				}
			});
			remove.setOnMouseEntered(new EventHandler<MouseEvent>() {
			    public void handle(MouseEvent event) {
			        setCursor(Cursor.HAND); //Change cursor to hand
			    }
			});
		}
		
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
		
		