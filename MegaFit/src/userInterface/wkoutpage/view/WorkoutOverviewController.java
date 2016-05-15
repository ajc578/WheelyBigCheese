package userInterface.wkoutpage.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import userInterface.Controllable;
import userInterface.Main;
import userInterface.ScreenFlowController;
import userInterface.wkoutpage.model.Workout;

public class WorkoutOverviewController implements Controllable{
    
	@FXML
    private TableView<Workout> workoutTable;
    @FXML
    private TableColumn<Workout, String> titleColumn;
    @FXML
    private TableColumn<Workout, String> creatorColumn;

    @FXML
    private Label titleLabel;
    @FXML
    private Label creatorLabel;
    @FXML
    private Label timeLabel;
    @FXML
    private Label gainzLabel;
    @FXML
    private Label workoutIconLabel;
    
    @FXML
    private Button dietButton;

    // Screen controller will be injected in setScreenParent
    private ScreenFlowController screenParent;


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
        // Initialize the workout table with the two columns.
        titleColumn.setCellValueFactory(cellData -> cellData.getValue().titleProperty());
        creatorColumn.setCellValueFactory(cellData -> cellData.getValue().creatorProperty());
        

        // Clear workout details.
        showWorkoutDetails(null);

        // Listen for selection changes and show the workout details when changed.
        workoutTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldSelected, newSelected)     
             // whenever the user selects a workout, this method is executed
             // show details of the selected workout on the right half of split pane
                -> showWorkoutDetails(newSelected)); 
    }


    
    /**
     * Fills all text fields to show details about the workout.
     * If the specified workout is null, all text fields are cleared.
     * 
     * @param workout the workout or null
     */
    private void showWorkoutDetails(Workout workout) {
        if (workout != null) {
            // Fill the labels with info from the workout object.
            titleLabel.setText(workout.getTitle());
            creatorLabel.setText(workout.getCreator());
            timeLabel.setText(workout.getTime());
            gainzLabel.setText(Integer.toString(workout.getTotalWorkoutGainz()));
            

            // TODO: We need a way to display the workout image icon
            
        } else {
            // Workout is null, remove all the text.
            titleLabel.setText("");
            creatorLabel.setText("");
            timeLabel.setText("");
            gainzLabel.setText("");
        }
    }


    @Override
    public void setScreenParent(ScreenFlowController screenParent) {
        this.screenParent = screenParent;
    }

    @Override
    public void setMainApp(Main mainApp) {

    }
}