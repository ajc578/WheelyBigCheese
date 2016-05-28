package tests;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import parser.ExerciseInfo;
import parser.XMLParser;
import presentationViewer.PresentationFx;
/**
 * A test application that builds a presentation from file
 * using the parser and plays it to allow visual testing
 * and demostration of the presentation and parser integration.
 * <p> <STRONG> Developed by </STRONG> <p>
 * Alexander Chapman and Oliver Rushton
 * <p> <STRONG> Tested by </STRONG> <p>
 * Alexander Chapman and Oliver Rushton
 * <p> <STRONG> Developed for </STRONG> <p>
 * BOSS
 * @author Alexander Chapman
 */
public class TestParserPresentationIntegration extends Application{

	PresentationFx testPresent;
	//the test presentation object

	public static void main(String[] args) { launch(args); }

	@Override
	public void start(Stage frame) throws Exception {
		
		testPresent = new PresentationFx("testies_WORKOUT.xml");
	
		testPresent.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("You completed:");
				ArrayList<ExerciseInfo> completedExercises = testPresent.getCompletedExercises();
				int pointsTotal = 0;
				for (ExerciseInfo exercise : completedExercises) {
					System.out.println(exercise.getName() + ": " +
							   exercise.getSets() + " sets of " +
							   exercise.getReps() + " reps, for " +
							   exercise.getPoints() + " points.");
					pointsTotal += exercise.getPoints();
				}
				System.out.println(new Label("For a total of: " + pointsTotal + " points."));
				frame.close();
			}

		});


		//plays presentation
		SubScene present = testPresent.Play(400, 300);
		Group holder = new Group();
		Scene scene = new Scene(holder, 400,300);
		holder.getChildren().add(present);

		//maintains window aspect ratio
		frame.setScene(scene);
		frame.setWidth(400);
		frame.setHeight(300);
		frame.setResizable(false);
		frame.setTitle(testPresent.getTitle());
		frame.show();

		scene.setOnKeyTyped(keyHandler);

	}

	/**A handler to allow keys to be pressed to test some of the user controllability
	 * of the presentation class.
	 */
	EventHandler<KeyEvent> keyHandler = new EventHandler<KeyEvent>() {

		@Override
		public void handle(KeyEvent ke) {
			switch(ke.getCharacter()){
				case "q":
					testPresent.quit();
				case "m":
					testPresent.setManualPlay();
				case "a":
					testPresent.setAutoPlay();
				case "p":
					testPresent.advanceManualEvents();
			}

		}
	};

}
