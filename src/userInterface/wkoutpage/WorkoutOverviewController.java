package userInterface.wkoutpage;

import account.WorkoutEntry;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import parser.ExerciseInfo;
import parser.WorkoutInfo;
import userInterface.HistoryAnalyser;
import userInterface.Controllable;
import userInterface.Main;
import userInterface.StackPaneUpdater;


import java.util.*;

public class WorkoutOverviewController implements Controllable{

    private Main mainApp;

    private StackPaneUpdater screenParent;

	@FXML
    private TableView<WorkoutInfo> workoutTable;
    @FXML
    private TableColumn<WorkoutInfo, String>  workoutNameColumn;
    @FXML
    private TableColumn<WorkoutInfo, String>  descriptionColumn;
    @FXML
    private TableColumn<WorkoutInfo, String> lastCompletedColumn;

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

    @FXML
    private SplitPane splitPane;



    private ArrayList<WorkoutInfo> workoutData;
    private ObservableList<WorkoutInfo> workoutDataForTable;


    private List<String> stringList = new ArrayList<>();
    private ObservableList names = FXCollections.observableArrayList();

    // Create new list to contain record of completed workouts
    public List<WorkoutEntry> workoutHistoryList = new ArrayList<WorkoutEntry>();
    // Create dummy workout entries for testing
    public WorkoutEntry workoutEntry0 = new WorkoutEntry();
    public WorkoutEntry workoutEntry1 = new WorkoutEntry();
    public WorkoutEntry workoutEntry2 = new WorkoutEntry();
    public WorkoutEntry workoutEntry3 = new WorkoutEntry();
    public WorkoutEntry workoutEntry4 = new WorkoutEntry();
    public WorkoutEntry workoutEntryRealLatest = new WorkoutEntry();
    public WorkoutEntry workoutEntryRealOldest = new WorkoutEntry();
    private WorkoutInfo selectedWorkout;
    private StringJoiner completedWorkouts;


    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public WorkoutOverviewController() {

    }

    private void setUpDummyHistory() {
        /**
         * Setting up a dummy workout history
         */
        // Add workout entries to history list
        workoutHistoryList.add(workoutEntry0);
        workoutHistoryList.add(workoutEntry1);
        workoutHistoryList.add(workoutEntry2);
        workoutHistoryList.add(workoutEntry3);
        workoutHistoryList.add(workoutEntry4);


        // set workout entry fields with dummy strings for testing
        // Workout entry1, 2, 3, 4 will have their fields set to (strings) 1, 2, 3, 4
        int j = 0;
        for (WorkoutEntry i : workoutHistoryList) {
            i.setWorkoutDate(Integer.toString(j));
            i.setWorkoutName(Integer.toString(j));
            i.setWorkoutTime(j);
            j++;
        }

        // In initialize one of the workouts is going to have its
        // date set to 2015/05/11 08:00

        // create a workout entry whose name matches with the name
        // of one of the workouts in xml folder
        workoutEntryRealLatest.setWorkoutName("Full Body Workout (No Weights)"); //workout1_
        workoutEntryRealLatest.setWorkoutTime(10);
        workoutEntryRealLatest.setWorkoutDate("20150513");
        workoutHistoryList.add(workoutEntryRealLatest);

        // create a workout entry whose name matches with the name
        // of one of the workouts in xml folder that is
        // older than the workout entry above
        // but newer than the one in workout data
        workoutEntryRealOldest.setWorkoutName("Full Body Workout (No Weights)"); //workout1_
        workoutEntryRealOldest.setWorkoutTime(20);
        workoutEntryRealOldest.setWorkoutDate("20150512");
        workoutHistoryList.add(workoutEntryRealOldest);


        /** ---------------------------------- **/
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {

        /**
         * Adding workouts and last completed dates to table view
         */
        // convert ArrayList<> to ObservableList for TableView

        //workoutData = XMLParser.retrieveAllWorkoutInfo();

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



    /**
     * Fills all text fields to show details about the workout.
     * If the specified workout is null, all text fields are cleared.
     *
     * @param selectedWorkout the workout or null
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
            stringList.clear();
            String exerciseName;
            for (int i = 0; i < exerciseList.size(); i++) {
                exerciseName = exerciseList.get(i).getName();
                stringList.add(exerciseName);
            }

            names.setAll(stringList);

            listView.setItems(FXCollections.observableList(names));

            this.selectedWorkout = selectedWorkout;


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

    @FXML
    private void handleBeginPresentationOfSelectedWorkout() {
        // load presentation with filename
        String filename = selectedWorkout.getFileName();
        screenParent.loadPresentation(filename);
        screenParent.setScreen(Main.presentationID);
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