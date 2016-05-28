package wkoutpage.view;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import wkoutpage.model.Workout;


/**
 * Dialog that prepares a new workout that will be created by the user
 * 
 * @author Marco Jakob
 */
public class CreateNewWorkoutDialogController {

    @FXML
    private TextField titleField;
    @FXML
    private TextField creatorField;
    



    private Stage dialogStage;
    private Workout workout;
    private boolean nextClicked = false;

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
    }
    
    public void setWorkout(Workout workout) {
    	this.workout = workout;
    }

    /**
     * Sets the stage of this dialog.
     * 
     * @param dialogStage
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    /**
     * Returns true if the user clicked OK, false otherwise.
     * 
     * @return
     */
    public boolean isNextClicked() {
        return nextClicked;
    }

    /**
     * Called when the user clicks ok.
     */
    @FXML
    private void handleNext() {
        if (isInputValid()) {
        	
        	workout.setTitle(titleField.getText());
        	workout.setCreator(creatorField.getText());
        	
        	// TODO: setting image with the return of browser

            nextClicked = true;
            dialogStage.close();
            
            // TODO: Take the user to the create workout page
        }
    }

    /**
     * Called when the user clicks cancel.
     */
    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    /**
     * Validates the user input in the text fields.
     * 
     * @return true if the input is valid
     */
    private boolean isInputValid() {
        String errorMessage = "";

        // if the text fields input are empty, create an error message
        if (titleField.getText() == null || titleField.getText().length() == 0) {
            errorMessage += "No valid title!\n"; 
        }
        if (creatorField.getText() == null || creatorField.getText().length() == 0) {
            errorMessage += "No valid creator name!\n"; 
        }


        // if the empty message has no characters, there was no error
        if (errorMessage.length() == 0) { // input is valid
            return true;
        } else { // input is invalid
            // Show the error message.
            Alert alert = new Alert(AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(errorMessage); // show generated error message

            alert.showAndWait();

            return false; // input was invalid
        }
    }
}
