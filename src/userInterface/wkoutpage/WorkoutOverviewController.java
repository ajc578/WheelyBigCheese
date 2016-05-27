package userInterface.wkoutpage;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import parser.WorkoutInfo;
import parser.XMLParser;
import userInterface.Controllable;
import userInterface.Main;
import userInterface.StackPaneUpdater;


import java.util.ArrayList;

public class WorkoutOverviewController implements Controllable{
    
	@FXML
    private TableView<WorkoutInfo> workoutTable;
    @FXML
    private TableColumn<WorkoutInfo, String>  workoutNameColumn;
    @FXML
    private TableColumn<WorkoutInfo, String>  descriptionColumn;
    @FXML
    private TableColumn<WorkoutInfo, String> durationColumn;

    @FXML
    private Label workoutNameLabel;
    @FXML
    private Label authorLabel;
    @FXML
    private Label durationLabel;
    @FXML
    private Label totalPointsLabel;
    @FXML
    private ImageView workoutImage;

    @FXML
    private Label accountLevelLabel;


    private Text descriptionText;
    @FXML
    private TextFlow descriptionTextFlow;
    
    @FXML
    private Button dietButton;

    // Screen controller will be injected in setScreenParent
    private StackPaneUpdater screenParent;

    private ArrayList<WorkoutInfo> workoutData;
    private ObservableList<WorkoutInfo> workoutDataForTable;



    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public WorkoutOverviewController() {
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {




        // convert ArrayList<> to ObservableList for TableView
        XMLParser parser = new XMLParser("");
        workoutData = parser.retrieveAllWorkoutInfo();
        workoutDataForTable = FXCollections.observableList(workoutData);
        workoutTable.setItems(workoutDataForTable);


        // Initialize the workout table with the two columns.
        workoutNameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());

        descriptionColumn.setCellValueFactory(cellData -> cellData.getValue().descriptionProperty());
        durationColumn.setCellValueFactory(cellData -> cellData.getValue().durationProperty());


        descriptionText = new Text();
        descriptionTextFlow.getChildren().add(descriptionText);


        // Listen for selection changes and show the workout details when changed.
        workoutTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldSelected, newSelected)     
             // whenever the user selects a workout, this method is executed
             // show details of the selected workout on the right half of split pane
                -> showWorkoutDetails(newSelected));

        //Select first item
        workoutTable.getSelectionModel().selectFirst();
    }



    /**
     * Fills all text fields to show details about the workout.
     * If the specified workout is null, all text fields are cleared.
     *
     * @param workout the workout or null
     */
    private void showWorkoutDetails(WorkoutInfo workout) {
        if (workout != null) {
            // Fill the labels with info from the workout object.
            workoutNameLabel.setText(workout.getName());
            authorLabel.setText(workout.getAuthor());
            durationLabel.setText(Integer.toString(workout.getDuration()));
            descriptionText.setText(workout.getDescription());


            workoutImage.setImage(new Image("res/images/dumbellbenchpressup.jpg"));
            totalPointsLabel.setText(Integer.toString(workout.getTotalPoints()));




        } else {
            // Workout is null, remove all the text.
            workoutNameLabel.setText("");
            authorLabel.setText("");
            durationLabel.setText("");

        }
    }

    @FXML
    private void handleCreateWorkout() {
        screenParent.setScreen(Main.createWorkoutID);
    }


    @Override
    public void setScreenParent(StackPaneUpdater screenParent) {
        this.screenParent = screenParent;
    }

    @Override
    public void setMainApp(Main mainApp) {

    }
}