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



    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public WorkoutOverviewController() {
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



//        Stage theStage = (Stage) screenParent.getScene().getWindow();
//        double screenWidth = theStage.getWidth();
//        SplitPane.Divider divider = splitPane.getDividers().get(0);
//        divider.setPosition(screenWidth * 1.6180);





        // convert ArrayList<> to ObservableList for TableView

        workoutData = parser.retrieveAllWorkoutInfo();
        // setting a last completed date for the matching library workout
        // since it hasn't been parsed yet
        workoutData.get(0).setLastCompletedDate("20150511");

        // finds matching workout entries in workout history
        // and updates last completed dates
        setWorkoutInfosLastCompletedDates();

        // workout data has all fields set (including last completed)

        workoutData = XMLParser.retrieveAllWorkoutInfo();
        workoutDataForTable = FXCollections.observableList(workoutData);
        workoutTable.setItems(workoutDataForTable);


        // Initialize the workout table with the two columns.
        workoutNameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());

        descriptionColumn.setCellValueFactory(cellData -> cellData.getValue().descriptionProperty());
        lastCompletedColumn.setCellValueFactory(cellData -> cellData.getValue().lastCompletedDateProperty());


        descriptionText = new Text();
        descriptionTextFlow.getChildren().add(descriptionText);

        /**
         *  Description text displayed on RHS
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

            wLibPointer.setLastCompletedDate(Integer.toString(lastDate));
            System.out.println("last date for " + dataPointerName + wLibPointer.getLastCompletedDate());

        }

//        for (WorkoutInfo wLibPointer: workoutData) {
//            dataPointerName = wLibPointer.getName();
//
//            try {
//                histLastCompleted = dateFormat.parse(initDate);
//
//                for (WorkoutEntry wHistPointer: workoutHistoryList) {
//                    histPointerName = wHistPointer.getWorkoutName();
//
//                    // Find match in account history and workout library
//                    if (dataPointerName.equals(histPointerName)) {
//                        System.out.println("match with " + histPointerName);
//
//                        // Parse dates using date format before comparing
//                        histPointerDate = dateFormat.parse(wHistPointer.getWorkoutDate());
//                        System.out.println("wh date:" + histPointerDate);
//                        dataPointerDate = dateFormat.parse(wLibPointer.getLastCompletedDate());
//                        System.out.println("wL date:" + dataPointerDate);
//
//                        System.out.println("evaluate after: " + histPointerDate.after(dataPointerDate));
//
//                        // compare dates, if date in history is most recent then update
//                        // the workout's last completed date
//                        if (histPointerDate.after(dataPointerDate)) {
//                            System.out.println(histPointerDate  + " is after " + dataPointerDate);
//
//                            System.out.println("evaluate after stored: " + histPointerDate.after(histLastCompleted));
//                            if (histPointerDate.after(histLastCompleted)) {
//                                histLastCompleted = histPointerDate;
//
//                                System.out.println("last   :" + histLastCompleted);
//                            }
//
//
//                        }
//                    }
//                }
//
//                wLibPointer.setLastCompletedDate(histLastCompleted);
//
//
//            } catch (ParseException e) {
//                    e.printStackTrace();
//            }
//
//
//            System.out.println("wL date set to: " + wLibPointer.getLastCompletedDate());
//
//        }
    }


    /**
     * Fills all text fields to show details about the workout.
     * If the specified workout is null, all text fields are cleared.
     *
     * @param workout the workout or null
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