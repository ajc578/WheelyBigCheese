package userInterface;

import java.awt.event.ActionListener;
import java.io.File;

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


	public WorkoutView (double screenWidth, double screenHeight, String filename){

		// Parser sets absolute file path for each WorkoutInfo
		// Get name for presentation fx
		File file = new File(filename);
		String name = file.getName();

		//create and add all slides to presentation
		PresentationFx workoutPresent = new PresentationFx(name);

		//when the presentation finishes, close the application
		workoutPresent.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) {
				WorkoutEndCard endCard = new WorkoutEndCard (screenWidth, screenHeight,
						workoutPresent.getCompletedExercises());
				endCard.setScreenParent(screenParent);
				screenParent.displayNode(endCard);
			}

		});

		BorderPane presentationPane = new BorderPane();
		GridPane presentationControlPane = new GridPane();
		presentationPane.setTop(presentationControlPane);
		presentationControlPane.setHgap(screenWidth*0.001);
		presentationControlPane.setVgap(screenWidth*0.001);


		Label title = new Label(workoutPresent.getTitle());
		title.setAlignment(Pos.CENTER);
		title.setFont(Font.font(40));
		presentationControlPane.add(title, 2, 0,2,1);
		title.setPrefSize(screenWidth*0.4, screenHeight*0.1);

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

		Button advanceManual = new Button("next");
		advanceManual.setPrefSize(screenWidth*0.15, screenHeight*0.1);
		setNodeCursor(advanceManual);
		advanceManual.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent event) {
				workoutPresent.advanceManualEvents();
			}
		});

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
