package userInterface.wkoutpage;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.util.Callback;
import parser.ExerciseInfo;
import parser.WorkoutInfo;
import parser.XMLParser;
import userInterface.Controllable;
import userInterface.Main;
import userInterface.StackPaneUpdater;


import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    private Label accountLevelLabel;


    private Text descriptionText;
    @FXML
    private TextFlow descriptionTextFlow;

    @FXML
    private ListView listView;
    
    @FXML
    private Button dietButton;

    // Screen controller will be injected in setScreenParent
    private StackPaneUpdater screenParent;

    private ArrayList<WorkoutInfo> workoutData;
    private ObservableList<WorkoutInfo> workoutDataForTable;


    private List<String> stringList = new ArrayList<>();
    private ObservableList names = FXCollections.observableArrayList();


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



            totalPointsLabel.setText(Integer.toString(workout.getTotalPoints()));

            ArrayList<ExerciseInfo> exerciseList = workout.getExerciseList();
            // clear the string list for new exercise list selection
            stringList.clear();
            String exerciseName;
            for (int i = 0; i < exerciseList.size(); i++) {
                exerciseName = exerciseList.get(i).getName();
                stringList.add(exerciseName);
            }

            names.setAll(stringList);

            listView.setItems(FXCollections.observableList(names));



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