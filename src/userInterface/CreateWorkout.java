package userInterface;

import java.io.File;
import java.util.ArrayList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import parser.Presentation;
import parser.Presentation.Slide;

/**
 * Class defining the layout and functionality of the page where
 * the user creates their own workouts.
 * 
 * @author - company - B.O.S.S
 * @author - coders - Alexander Chapman, Jennifer Thorpe, Kamil Sledziewski
 * 
 */

public class CreateWorkout extends VBox implements Controllable {

	private StackPaneUpdater screenParent;
	private Main mainApp;
	
	private double screenWidth, screenHeight;
	
	private TextField nameWorkout, searchText, commentWorkout;
	private VBox exerciseSearch, searchArea, workoutBuilder, builderArea;
	private ScrollPane searchBox;
	private ScrollPane workoutBox;
	private Button beginWorkout;
	private Label name, description, amount, sets, rests;
	private CheckBox active;
	private HBox areasBox, labelsBox, restsBox;
	private ArrayList<SelectedInfo> chosenExercises;
	//chosenExercises is a list of all the exercises, with reps and sets, that the user
	//has chosen for their workout

	
	
	/**Builds and displays the create workout screen.
	 * 
	 * @param screenWidth
	 * @param screenHeight
	 */
	public CreateWorkout(double screenWidth, double screenHeight){		
		
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;
		
		areasBox = new HBox();
		restsBox = new HBox();
			
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
		rests = new Label("rests: ");
		rests.setPrefSize(screenWidth*0.15, screenHeight*0.05);
		active = new CheckBox("Active");
		active.setPrefSize(screenWidth*0.15, screenHeight*0.05);
		setNodeCursor(active);
		active.setSelected(true);
		restsBox.getChildren().addAll(rests,active);


		
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
		commentWorkout = new TextField("Description...");
		
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
		builderArea.getChildren().addAll(nameWorkout, workoutBox, restsBox, commentWorkout, beginWorkout);
		builderArea.setSpacing(screenHeight*0.01);
		
		/* set the content of the overall HBox to be the search and builder areas and
		 * set the spacing and padding so that there is space around the edge of each 
		 * item in the HBox.*/
		
		//create the back button
		Image back = new Image("res/images/backButton.png");
		ImageView buttonImageView = new ImageView(back);
		buttonImageView.setImage(back);
		buttonImageView.setFitWidth(screenWidth*0.05);
		buttonImageView.setFitHeight(screenHeight*0.05);
		Button backButton = new Button("", buttonImageView);
		
		//the back button returns the user to the workout library screen
		backButton.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent event) {
				screenParent.setScreen(Main.workoutLibraryID);
			}	
		});	
		setNodeCursor(backButton);
		
		//the begin workout button will create a new workout xml using
		//the selected exercises, and then play it
		beginWorkout.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent event) {

				if (chosenExercises.size() != 0){
							
					try {
						
						Presentation newPresent = new Presentation();		
						//set the document info
						parser.Presentation.DocumentInfo newDocInfo = new parser.Presentation.DocumentInfo();
						newDocInfo.setTitle(nameWorkout.getText());
						newDocInfo.setAuthor(Main.account.getUsername());
						newDocInfo.setComment(commentWorkout.getText());
						newDocInfo.setVersion("1");
						newPresent.setDocumentInfo(newDocInfo);
						
						//set some generic default values, for use in case of omission from any of the
						//elements in an exercise
						parser.Presentation.Defaults newDefaults = new parser.Presentation.Defaults();
						newDefaults.setBackgroundColour("FFFFFF");
						newDefaults.setFillColour("333333");
						newDefaults.setFont("Arial");
						newDefaults.setFontColour("000000");
						newDefaults.setFontsize(12);
						newDefaults.setLineColour("000000");
						newPresent.setDefaults(newDefaults);
						
						int currentID = 0;
						int workoutDuration = 0;
						ArrayList<Slide> workoutSlides = new ArrayList<Slide>();
						JAXBContext jaxbContext;
						
						//unpack the details for rests and active rests
						jaxbContext = JAXBContext.newInstance(Presentation.class);
						Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
						Presentation rest = (Presentation) jaxbUnmarshaller.unmarshal(new File("src/res/xml/REST.xml"));
						Presentation aRest = (Presentation) jaxbUnmarshaller.unmarshal(new File("src/res/xml/AREST.xml"));
						
						//iterate through each exercise added to the workout by the user adding those slides to the presentation.
						for (SelectedInfo Exercise : chosenExercises) {
							File sourceFile = new File("src/res/xml/" + Exercise.filename);
								Presentation xml = (Presentation) jaxbUnmarshaller.unmarshal(sourceFile);
								
								Slide tempSlide;
								
								for (int setNum = Exercise.sets; setNum > 0; setNum--) {
									
									//Add the exercise slide setting the sets and reps labels and making sure that it fits the correct ID
									//position within the slideshow
									tempSlide = xml.getSlide().get(0).clone();
									((parser.Presentation.Slide.TextType)tempSlide.getAllContent().get(0)).setText((
											(parser.Presentation.Slide.TextType)tempSlide.getAllContent().get(0)).getText() + Exercise.reps);
									tempSlide.setReps(Exercise.reps);
									((parser.Presentation.Slide.TextType)tempSlide.getAllContent().get(1)).setText(
											((parser.Presentation.Slide.TextType)tempSlide.getAllContent().get(1)).getText() + setNum);
									((parser.Presentation.Slide.Interactable)tempSlide.getAllContent().get(3)).setTargetSlide(currentID + 1);
									((parser.Presentation.Slide.Interactable)tempSlide.getAllContent().get(4)).setTargetSlide(currentID + 2);
									tempSlide.setNextSlide(currentID+2);
									if(tempSlide.getDuration()!= -1)tempSlide.setDuration(tempSlide.getDuration()*Exercise.reps);
									tempSlide.setSlideID(currentID);
									workoutSlides.add(tempSlide);
									currentID++;
									
									//add the instructional slide to the correct ID position in the slideshow
									tempSlide = xml.getSlide().get(1).clone();
									tempSlide.setSlideID(currentID);
									workoutSlides.add(tempSlide);
									currentID++;
									
									//add this exercise's duration to the duration of the workout
									workoutDuration += (xml.getWorkoutDuration()*Exercise.reps);
									
									//Add the appropriate rest and its duration
									if(active.isSelected()){
										tempSlide = aRest.getSlide().get(0).clone();
										workoutDuration += aRest.getWorkoutDuration();
									}else{
										tempSlide = rest.getSlide().get(0).clone();
										workoutDuration += rest.getWorkoutDuration();
									}
									tempSlide.setNextSlide(currentID+1);
									tempSlide.setSlideID(currentID);
									workoutSlides.add(tempSlide);
									currentID++;
								}
								
						}
						
						//make sure the last slide has the quit destination
						currentID--;
						workoutSlides.get(currentID).setNextSlide(-1);
						
						//set the workoutDuration
						newPresent.setWorkoutDuration(workoutDuration);

						
						//set the slides
						newPresent.setSlides(workoutSlides);
						Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
						jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
						jaxbMarshaller.marshal(newPresent, new File("src/res/xml/" + nameWorkout.getText() + "_WORKOUT.xml"));
						
						//play the presentation
				        String filename ="src/res/xml/" + nameWorkout.getText() + "_WORKOUT.xml";
				        mainApp.launchPresentation(filename);
						
					} catch (JAXBException e) {
						e.printStackTrace();
					}

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
	
	
	/**This method when called will add an element with the selected details to
	 * the list of selected exercises. It will be called by the ExerciseContent Objects
	 * that appear in a list on the left of this screen
	 * @param fileName
	 * @param name
	 * @param sets
	 * @param reps
	 */
	public void addToList(String fileName, String name, int sets, int reps){
		chosenExercises.add(new SelectedInfo(fileName,name,sets,reps));
		updateWorkoutBuilder();
	}
	
	/**
	 * update the list on the RHS of the screen (workout builder)
	 * to display the contents of the chosenExercises list
	 */
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

	/** Change the style of a cursor when hovering over a node.
	 * Used by the buttons
	 * @param node
	 */
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
		
		