package userInterface;

import java.awt.event.ActionListener;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import account.WorkoutEntry;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import presentationViewer.PresentationFx;

public class WorkoutView extends BorderPane implements Controllable {


	boolean playbackMode;
	private StackPaneUpdater screenParent;
	private Main mainApp;


	/**Play the presentation located by the fil
	 * 
	 * @param screenWidth
	 * @param screenHeight
	 * @param filename
	 * @param mainApp
	 */
	public WorkoutView (double screenWidth, double screenHeight, String filename,Main mainApp){
		this.mainApp = mainApp;
		
		//get the last portion of the filename
		File file = new File(filename);
		String namefile = file.getName();
		
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmm");
		// record start time of presentation
		final Date startDate = new Date();
		final long startTime = Long.parseLong(dateFormat.format(startDate).toString());
		//create the presentation
		PresentationFx workoutPresent = new PresentationFx(namefile);

		//when the presentation finishes, display the end card
		//and add an entry to the user's workout history
		workoutPresent.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) {
				WorkoutEndCard endCard = new WorkoutEndCard (screenWidth, screenHeight,
															workoutPresent.getCompletedExercises(),mainApp);

				String titleFX = workoutPresent.getTitle();

				Date endDate = new Date();

				long endTime = Long.parseLong(dateFormat.format(endDate).toString());

				final long presentationDuration = endTime-startTime;

				WorkoutEntry entry = new WorkoutEntry();
				entry.setWorkoutDate( Long.toString(startTime) );
				entry.setWorkoutTime(presentationDuration);
				entry.setWorkoutName(workoutPresent.getTitle());

				Main.account.addWorkoutEntry(entry);

				// Reload screens that must be updated when the user has completed a workout
				screenParent.loadWorkoutLibrary();
				screenParent.loadCharacterDashboard();

				if (namefile.toUpperCase().endsWith("_WORKOUT.XML")) {
					screenParent.loadJavaWrittenScreen(Main.endCardID, endCard);
					screenParent.displayNode(endCard);
				}

				else { // the presentation just played was not a MegaFit presentation
					// Return to Workout library
					screenParent.setScreen(Main.workoutLibraryID);
				}
				// remove the presentation from the screen and add the in-app screens to view
				mainApp.returnToAppScreens();
			}

		});

		BorderPane presentationPane = new BorderPane();
		GridPane presentationControlPane = new GridPane();
		presentationPane.setTop(presentationControlPane);
		presentationControlPane.setHgap(screenWidth*0.001);
		presentationControlPane.setVgap(screenWidth*0.001);

		//place a label in the center above the presentation giving its title
		Label title = new Label(workoutPresent.getTitle());
		title.setAlignment(Pos.CENTER);
		title.setFont(Font.font(40));
		presentationControlPane.add(title, 2, 0,2,1);
		title.setPrefSize(screenWidth*0.4, screenHeight*0.1);
		
		//place a button to close the presentation
		Button quitPresentation = new Button("QUIT");
		setNodeCursor(quitPresentation);
		quitPresentation.setPrefSize(screenWidth*0.3, screenHeight*0.1);
		quitPresentation.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent event) {
				workoutPresent.quit();
			}
		});
		presentationControlPane.add(quitPresentation, 0, 0,2,1);
		
		//add a button to advance the presentation when in manual mode
		Button advanceManual = new Button("next");
		advanceManual.setPrefSize(screenWidth*0.15, screenHeight*0.1);
		setNodeCursor(advanceManual);
		advanceManual.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent event) {
				workoutPresent.advanceManualEvents();
			}
		});
		
		//add a button to switch between manual and auto mode
		Button changeModePresentation = new Button("Switch to Manual Play");
		changeModePresentation.setPrefSize(screenWidth*0.3, screenHeight*0.1);
		setNodeCursor(changeModePresentation);
		playbackMode = true;
		presentationControlPane.add(changeModePresentation, 4, 0,2,1);
		changeModePresentation.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent event) {

				if (playbackMode == true){
					playbackMode = false;
					presentationControlPane.getChildren().remove(changeModePresentation);
					presentationControlPane.getChildren().remove(advanceManual);
					changeModePresentation.setText("Switch to Auto Play");
					changeModePresentation.setPrefSize(screenWidth*0.15, screenHeight*0.1);
					presentationControlPane.add(changeModePresentation, 4, 0,1,1);
					presentationControlPane.add(advanceManual, 5, 0,1,1);
					workoutPresent.setManualPlay();
				}else{
					playbackMode = true;
					presentationControlPane.getChildren().remove(changeModePresentation);
					presentationControlPane.getChildren().remove(advanceManual);
					changeModePresentation.setText("Switch to Manual Play");
					changeModePresentation.setPrefSize(screenWidth*0.3, screenHeight*0.1);
					presentationControlPane.add(changeModePresentation, 4, 0,2,1);
					workoutPresent.setAutoPlay();
				}
			}
		});

		presentationPane.setBottom(workoutPresent.Play(screenWidth, screenHeight*0.9));
		this.setCenter(presentationPane);
	}
	
	/** Change the style of a cursor when hovering over a node.
	 * Used by the buttons
	 * @param node
	 */
	public void setNodeCursor (Node node) {

		node.setOnMouseEntered(event -> setCursor(Cursor.HAND));
		node.setOnMouseExited(event -> setCursor(Cursor.DEFAULT));
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
