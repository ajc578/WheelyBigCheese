package wkoutpage;


import java.io.File;
import java.io.IOException;
import java.util.prefs.Preferences;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javafx.application.Application;
import javafx.stage.Stage;


import wkoutpage.model.Workout;
import wkoutpage.model.WorkoutListWrapper;
import wkoutpage.view.CreateNewWorkoutDialogController;
import wkoutpage.view.RootLayoutController;
import wkoutpage.view.WorkoutOverviewController;

/**
 * Main app
 * handles reading and writing of xml
 * @author Seb
 * Most of code is adapted from http://code.makery.ch/library/javafx-8-tutorial/ parts 1 through 5
 * 
 */


public class MainApp extends Application {
	
    private Stage primaryStage;
    private BorderPane rootLayout;
    
    /**
     * The data as an observable list of Persons.
     */
    private ObservableList<Workout> workoutData = FXCollections.observableArrayList();

    /**
     * Constructor
     */
    public MainApp() {
        // Add some sample data
        workoutData.add(new Workout("Workout 1", "Nikos"));
        workoutData.add(new Workout("Workout 2", "Henry"));
        workoutData.add(new Workout("Workout 4", "Kamil"));
        workoutData.add(new Workout("Workout 5 ", "Jenn"));
        workoutData.add(new Workout("Workout 6", "Ollie"));
        workoutData.add(new Workout("Workout 7", "Lexxy"));
        
        //
        
    }

    /**
     * Returns the data as an observable list of Persons. 
     * @return
     */
    public ObservableList<Workout> getWorkoutData() {
        return workoutData;
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Workout Page");

        initRootLayout();

        showWorkoutOverview();
    }
    
    /**
     * Initializes the root layout and tries to load the last opened
     * person file.
     */
    public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class
                    .getResource("view/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);

            // Give the controller access to the main app.
            RootLayoutController controller = loader.getController();
            controller.setMainApp(this);

            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Try to load last opened person file.
        File file = getWorkoutFilePath();
        if (file != null) {
            loadWorkoutDataFromFile(file);
        }
    }
    
    /**
     * Shows the person overview inside the root layout.
     */
    public void showWorkoutOverview() {
        try {
            // Load workout overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/WorkoutOverview.fxml"));
            BorderPane personOverview = (BorderPane) loader.load();

            // Set workout overview into the center of root layout.
            rootLayout.setCenter(personOverview);
            
            // Give the controller access to the main app.
            WorkoutOverviewController controller = loader.getController();
            controller.setMainApp(this);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Opens a dialog to give title to a new workout. If the user
     * clicks OK, the changes are saved into the provided workout object and true
     * is returned.
     * @param tempWorkout 
     * 
     * 
     * @return true if the user clicked Next, false otherwise.
     */
    public boolean showCreateNewWorkoutDialog(Workout workout) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/CreateNewWorkoutDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("New Workout");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the workout into the controller
            CreateNewWorkoutDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setWorkout(workout);
            

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return controller.isNextClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Returns the workout file preference, i.e. the file that was last opened.
     * The preference is read from the OS specific registry. If no such
     * preference can be found, null is returned.
     * 
     * @return
     */
    public File getWorkoutFilePath() {
        Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
        String filePath = prefs.get("filePath", null);
        if (filePath != null) {
            return new File(filePath);
        } else {
            return null;
        }
    }

    /**
     * Sets the file path of the currently loaded file. The path is persisted in
     * the OS specific registry.
     * 
     * @param file the file or null to remove the path
     */
    public void setWorkoutFilePath(File file) {
        Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
        if (file != null) {
            prefs.put("filePath", file.getPath());

            // Update the stage title.
            primaryStage.setTitle("WorkoutApp - " + file.getName());
        } else {
            prefs.remove("filePath");

            // Update the stage title.
            primaryStage.setTitle("WorkoutApp");
        }
    }

    /**
     * Returns the main stage.
     * @return
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
    
    /** XML
     * Reading and writing data with JAXB
     */
    
    /**
     * Loads workout data from the specified file. The current workout data will
     * be replaced.
     * 
     * @param file
     */
    public void loadWorkoutDataFromFile(File file) {
        try {
            JAXBContext context = JAXBContext
                    .newInstance(WorkoutListWrapper.class);
            Unmarshaller um = context.createUnmarshaller();

            // Reading XML from the file and unmarshalling.
            WorkoutListWrapper wrapper = (WorkoutListWrapper) um.unmarshal(file);

            workoutData.clear();
            workoutData.addAll(wrapper.getWorkouts());

            // Save the file path to the registry.
            setWorkoutFilePath(file);

        } catch (Exception e) { // catches ANY exception
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Could not load data");
            alert.setContentText("Could not load data from file:\n" + file.getPath());

            alert.showAndWait();
        }
    }

    /**
     * Saves the current workout data to the specified file.
     * 
     * @param file
     */
    public void saveWorkoutDataToFile(File file) {
        try {
            JAXBContext context = JAXBContext
                    .newInstance(WorkoutListWrapper.class);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            // Wrapping our workout data.
            WorkoutListWrapper wrapper = new WorkoutListWrapper();
            wrapper.setWorkouts(workoutData);

            // Marshalling and saving XML to the file.
            m.marshal(wrapper, file);

            // Save the file path to the registry.
            setWorkoutFilePath(file);
        } catch (Exception e) { // catches ANY exception
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Could not save data");
            alert.setContentText("Could not save data to file:\n" + file.getPath());

            alert.showAndWait();
        }
    }

}
