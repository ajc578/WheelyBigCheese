package userInterface;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.stage.Stage;

import java.io.IOException;

public class WorkoutMenu extends VBox implements Controllable {

	/* create arrays of type String for the exercises and descriptions, these
	 * will eventually be in an XML file that will be parsed into the code.*/
	String[] news = {
			"New workout added!",
			"New workout added!",
			"New recipe added!",
			"New workout added!"
	};
	String[] title = {
			"Bicep Blaster!",
			"6 Minute Abs",
			"Peanut Butter Protein Cookies",
			"Insane Cardio"
	};
	String[] author = {
			"MegaFit",
			"Emily Skye",
			"Emily Skye",
			"Shaun T"
	};

	Button createWorkout, openLibrary;
	VBox buttons, newsFeed;
	HBox menuOptions;
	LevelBar bar;
	ScrollPane newsScroll;
	GridPane recentWorkouts;
	private ScreenFlowController screenController;
	private Main mainApp;

	public WorkoutMenu (){

		/* End Integration Amendments (KS) */

		openLibrary = new Button("Open Workout Library");


		/* Begin integration amendments (KS) */
		openLibrary.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				screenController.setScreen(mainApp.workoutPageID);

			}

		});

		getChildren().addAll(openLibrary);




	}

	@Override
	public void setScreenParent(ScreenFlowController screenParent) {
		this.screenController = screenParent;
		// TODO screenWidth and height are set here rather than passed by contructor
	}

	@Override
	public void setMainApp(Main mainApp) {
		this.mainApp = mainApp;
	}
}
