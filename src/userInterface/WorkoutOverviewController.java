package userInterface;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import parser.ExerciseInfo;
import parser.WorkoutInfo;


import java.util.*;

/**
 * Controller class for the nodes expressed in userInterface.WorkoutOverview.fxml
 *
 * Allows the user to browse the workout library and launch a presentation.
 * Allows the user to see details about each workout to make an informed choice of the
 * workout about to be done.
 *
 * Has a button that can take a user to the CreateWorkout screen should user want
 * to build their own workouts.
 *
 * @see HistoryAnalyser
 * @see Main
 * @see CreateWorkout
 * @see
 *
 *
 *  <p> <STRONG> Developed by </STRONG> <p>
 * Sebastien Corrigan
 * <p> <STRONG> Developed for </STRONG> <p>
 * BOSS
 * @author Sebastien Corrigan
 */
public class WorkoutOverviewController implements Controllable{
    //================================================================================
    // Fields
    //================================================================================

    //----------------------------------------------------------//
    // Screen belongs to these
    private Main mainApp;
    private StackPaneUpdater screenParent;


    //----------------------------------------------------------//
    // Table View
	@FXML
    private TableView<WorkoutInfo> workoutTable;
    @FXML
    private TableColumn<WorkoutInfo, String>  workoutNameColumn;
    @FXML
    private TableColumn<WorkoutInfo, String>  descriptionColumn;
    @FXML
    private TableColumn<WorkoutInfo, String> lastCompletedColumn;

    //----------------------------------------------------------//
    // Selected workout labels
    @FXML
    private Label workoutNameLabel;
    @FXML
    private Label authorLabel;
    @FXML
    private Label durationLabel;
    @FXML
    private Label totalPointsLabel;

    private Text descriptionText;
    @FXML
    private TextFlow descriptionTextFlow;

    //----------------------------------------------------------//
    // Selected workout exercise list view
    @FXML
    private ListView listView;



    //----------------------------------------------------------//
    // Workout and exercise data
    private ArrayList<WorkoutInfo> workoutData;
    private ObservableList<WorkoutInfo> workoutDataForTable;
    private WorkoutInfo selectedWorkout;

    private List<String> exerciseNameList = new ArrayList<>();
    private ObservableList exercisesInWorkout = FXCollections.observableArrayList();



    //================================================================================
    // Contructor and initialize
    //================================================================================

    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public WorkoutOverviewController() {

    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded and so the fields annotated by @FXML can be accessed
     */
    @FXML
    private void initialize() {
        /**
         * Adding workouts and last completed dates to table view
         */
        // convert ArrayList<> to ObservableList for TableView

        // finds matching workout entries in workout history
        // and updates last completed dates
        workoutData = HistoryAnalyser.getWorkoutLibraryWithLastCompletedDates();

        // workout data has all fields set (including last completed)
        workoutDataForTable = FXCollections.observableList(workoutData);
        workoutTable.setItems(workoutDataForTable);


        // Initialize the workout table with the two columns.
        workoutNameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        descriptionColumn.setCellValueFactory(cellData -> cellData.getValue().descriptionProperty());
        lastCompletedColumn.setCellValueFactory(cellData -> cellData.getValue().lastCompletedDateProperty());

        /**
         *  Description text displayed on RHS
         */
        descriptionText = new Text();
        descriptionTextFlow.getChildren().add(descriptionText);

        /**
         * Listening for row selection changes
         */
        // Listen for selection changes and show the workout details when changed.
        workoutTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldSelected, newSelected)
                        // whenever the user selects a workout, this method is executed
                        // show details of the selected workout on the right half of split pane
                        -> showWorkoutDetails(newSelected));

        /**
         * Listening for row selection changes
         */
        workoutTable.getSelectionModel().selectFirst();
    }


    //================================================================================
    // Members
    //================================================================================
    /**
     * Fills selected workout text fields to show details about the workout. Adds exercises
     * present in the selected workout to the exercise list view.
     *
     * If the specified workout is null, all text fields are cleared.
     * @param selectedWorkout the workout selected in the table
     */
    private void showWorkoutDetails(WorkoutInfo selectedWorkout) {
        if (selectedWorkout != null) {

            // Fill the labels with info from the workout object.
            workoutNameLabel.setText(selectedWorkout.getName());
            authorLabel.setText(selectedWorkout.getAuthor());
            durationLabel.setText(Integer.toString(selectedWorkout.getDuration()));
            descriptionText.setText(selectedWorkout.getDescription());

            totalPointsLabel.setText(Integer.toString(selectedWorkout.getTotalPoints()));

            ArrayList<ExerciseInfo> exerciseList = selectedWorkout.getExerciseList();

            // clear the string list for new exercise list selection
            exerciseNameList.clear();
            String exerciseName;
            for (int i = 0; i < exerciseList.size(); i++) {
                exerciseName = exerciseList.get(i).getName();
                // do not display slides that are not exercise slides. intSlide is an instructional
                // branch slide that shows a video.
                if((!exerciseName.equals("none"))&&(!exerciseName.equals("intSlide"))) exerciseNameList.add(exerciseName);
            }

            exercisesInWorkout.setAll(exerciseNameList);

            listView.setItems(FXCollections.observableList(exercisesInWorkout));

            // update the selected workout field so that its presentation can be launched with handleBeginPresentation
            this.selectedWorkout = selectedWorkout;

        } else {
            // Workout is null, remove all the text.
            workoutNameLabel.setText("");
            authorLabel.setText("");
            durationLabel.setText("");

        }
    }

    /**
     * Handles user click on the createWorkout button.
     * <b>Note</b>: this button is does not need a field in this class however it is present in
     * WorkoutOverview.fxml
     */
    @FXML
    private void handleGoToCreateWorkout() {
        screenParent.setScreen(Main.createWorkoutID);
    }

    /**
     * Handles user click on the Begin button to launch the presentation for the selected workout
     * <p>Calls {@link Main#launchPresentation(String) Main.launchPresentation(workoutFileName)}
     * so that the main's stackpane can have the in app screens removed and replaced with the presentation screen.
     */
    @FXML
    private void handleBeginPresentationOfSelectedWorkout() {
        // load presentation with filename
        String filename = selectedWorkout.getFileName();
        mainApp.launchPresentation(filename);
    }


    /**
     * Sets the parent stack pane for this controller
     * @param screenParent
     */
    @Override
    public void setScreenParent(StackPaneUpdater screenParent) {
        this.screenParent = screenParent;
    }

    /**
     * Gives this controller the instance of the main app.
     * @param mainApp
     */
    @Override
    public void setMainApp(Main mainApp) {
    this.mainApp = mainApp;
    }
}