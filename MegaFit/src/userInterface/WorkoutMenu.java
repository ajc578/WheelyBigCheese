package userInterface;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;

public class WorkoutMenu extends BorderPane implements Controllable {

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

	public WorkoutMenu (double screenWidth, double screenHeight){

		createWorkout = new Button("Create New Workout");
		createWorkout.setMinSize(screenWidth*0.25, screenHeight*0.125);

		bar = new LevelBar(screenWidth, screenHeight);
		menuOptions = new HBox();
		buttons = new VBox();
		newsFeed = new VBox();
		newsScroll = new ScrollPane();

		/* Begin Integration amendments (KS) */
		// TODO set screen for create workout
		createWorkout.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				screenController.setScreen(Main.createWorkoutID);
			}
		});

		/* End Integration Amendments (KS) */

		openLibrary = new Button("Open Workout Library");
		openLibrary.setMinSize(screenWidth*0.25, screenHeight*0.125);

		/* Begin integration amendments (KS) */
		openLibrary.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				screenController.setScreen(mainApp.workoutLibraryID);

			}

		});

		buttons.getChildren().addAll(createWorkout, openLibrary);
		buttons.setSpacing(screenHeight*0.05);
		buttons.setPadding(new Insets(screenHeight*0.1, 0, 0, 0));


		for(int i=0; i<news.length; i++){
			NewsFeed newsContents = new NewsFeed(screenWidth, screenHeight, news[i], title[i], author[i]);
			newsFeed.getChildren().add(newsContents);
		}

		newsFeed.setSpacing(screenHeight*0.05);

		newsScroll.setContent(newsFeed);
		newsScroll.setHbarPolicy(ScrollBarPolicy.NEVER);
		newsScroll.setMinWidth(screenWidth*0.4);
		newsScroll.setMinHeight(screenHeight*0.6);
		newsScroll.setPadding(new Insets(screenHeight*0.01, screenWidth*0.01, screenHeight*0.01, screenWidth*0.01));


		Button workout1 = new Button("Recent Workout 1");
		workout1.setMinSize(screenWidth*0.125, screenWidth*0.125);
		Button workout2 = new Button("Recent Workout 2");
		workout2.setMinSize(screenWidth*0.125, screenWidth*0.125);
		Button workout3 = new Button("Recent Workout 3");
		workout3.setMinSize(screenWidth*0.125, screenWidth*0.125);
		Button workout4 = new Button("Recent Workout 4");
		workout4.setMinSize(screenWidth*0.125, screenWidth*0.125);

		recentWorkouts = new GridPane();

		recentWorkouts.setVgap(0);
		recentWorkouts.setHgap(0);
		recentWorkouts.add(workout1, 1, 1);
		recentWorkouts.add(workout3, 1, 2);
		recentWorkouts.add(workout2, 2, 1);
		recentWorkouts.add(workout4, 2, 2);
		recentWorkouts.setMinWidth(screenWidth*0.25);
		recentWorkouts.setPadding(new Insets(screenHeight*0.025, 0, 0, 0));

		menuOptions.getChildren().addAll(recentWorkouts, buttons, newsScroll);
		menuOptions.setSpacing(screenWidth*0.05);
		menuOptions.setPadding(new Insets(screenHeight*0.1, screenWidth*0.025, screenHeight*0.01, screenWidth*0.025));

		getChildren().addAll(bar, menuOptions);

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
