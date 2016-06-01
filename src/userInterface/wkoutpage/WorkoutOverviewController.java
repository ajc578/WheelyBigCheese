package userInterface.wkoutpage;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import account.WorkoutEntry;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import parser.ExerciseInfo;
import parser.WorkoutInfo;
import userInterface.Controllable;
import userInterface.HistoryAnalyser;
import userInterface.Main;
import userInterface.StackPaneUpdater;

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
    private WorkoutInfo selectedWorkout;


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



//        Stage theStage = (Stage) screenParent.getScene().getWindow();
//        double screenWidth = theStage.getWidth();
//        SplitPane.Divider divider = splitPane.getDividers().get(0);
//        divider.setPosition(screenWidth * 1.6180);

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

    private void setWorkoutInfosLastCompletedDates() {
        /**
         * Updating the workout info data for last completed
         */
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.ENGLISH);
        String dataPointerName;
        String histPointerName;

        Date histPointerDate;
        Date dataPointerDate;
        Date histLastCompleted;
        String initDate = "2015/05/10 01:01";

        int histDate;
        int dataDate;
        int lastDate;
        for (WorkoutInfo wLibPointer: workoutData) {

            dataPointerName = wLibPointer.getName();
            lastDate = 0;

            for (WorkoutEntry wHistPointer: workoutHistoryList) {

                histPointerName = wHistPointer.getWorkoutName();

                // find match with workout in workout library
                if (dataPointerName.equals(histPointerName)) {
                    System.out.println("match with " + histPointerName);
                    System.out.println("workout time " + wHistPointer.getWorkoutTime());

                    // get dates for both history and data to compare
                    histDate = Integer.parseInt(wHistPointer.getWorkoutDate());
                    dataDate = Integer.parseInt(wLibPointer.getLastCompletedDate());

                    System.out.println("data date is: " + dataDate);

                    // if history date is most recent, update lastDate
                    if (histDate > dataDate && histDate > lastDate) {
                            lastDate = histDate;
                    }
                }
            }
            if (lastDate == 0) { // there was no matching workouts in the history

            }
            else {
                wLibPointer.setLastCompletedDate(Integer.toString(lastDate));
            }

        }

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
                if((!exerciseName.equals("none"))&&(!exerciseName.equals("intSlide")))stringList.add(exerciseName);
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
//        ExerciseInfo testExercise = new ExerciseInfo("name", 2, 2, 2, 5, 5, 5, 5);
//        ArrayList<ExerciseInfo> testList = new ArrayList<>();
//        testList.add(testExercise);
//        WorkoutEndCard workoutEndCard = new WorkoutEndCard(500, 500, testList);
//        screenParent.loadJavaWrittenScreen("endcard", workoutEndCard);
//        screenParent.setScreen("endcard");
        mainApp.launchPresentation(filename);
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