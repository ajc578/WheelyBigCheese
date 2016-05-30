package userInterface;

import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import parser.ExerciseInfo;

public class WorkoutEndCard extends VBox implements Controllable {

	private StackPaneUpdater screenParent;
	private Main mainApp;

	public WorkoutEndCard (double screenWidth, double screenHeight,
						   ArrayList<ExerciseInfo> completedExercises, Main mainApp) {
		
		this.mainApp = mainApp;
		addSkillPoints(completedExercises); 
		
			ArrayList<ExerciseInfo> collapsedExerciseList = new ArrayList<ExerciseInfo>();

			for (ExerciseInfo exercise : completedExercises) {
				int head = collapsedExerciseList.size();
				if (head != 0){
					System.out.println(collapsedExerciseList.get(head-1).getName());
					System.out.println(exercise.getName());
					System.out.println(collapsedExerciseList.get(head-1).getName().contentEquals(exercise.getName()));
					if((collapsedExerciseList.get(head-1).getName().contentEquals(exercise.getName()))
							&& (collapsedExerciseList.get(head-1).getReps() == exercise.getReps())){
						collapsedExerciseList.get(head-1).setSets(collapsedExerciseList.get(head-1).getSets()
								+ 1);
					}else collapsedExerciseList.add(exercise);
				}else collapsedExerciseList.add(exercise);
			}

			getChildren().add(new Label("Workout Ended."));
			getChildren().add(new Label("You completed:"));

			int pointsTotal = 0;
			for (ExerciseInfo exercise : collapsedExerciseList) {
				getChildren().add(new Label(exercise.getName() + ": " +
						   exercise.getSets() + " sets of " +
						   exercise.getReps() + " reps, for " +
						   exercise.getPoints()*exercise.getSets()*exercise.getReps() + " points."));
				pointsTotal += exercise.getPoints()*exercise.getSets()*exercise.getReps();
			}
			getChildren().add(new Label("For a total of " + pointsTotal + " points awarded."));
			getChildren().add(new Label("And " + Math.round(Math.round(pointsTotal*0.1)) + " Gainz awarded."));

			//Add the points to the user account
			Main.account.setXp(Main.account.getXp()+pointsTotal);
			Main.account.setGainz(Main.account.getGainz()+Math.round(Math.round(pointsTotal*0.1)));
			
			int Level = getLevel(Main.account.getXp());
			
			Main.account.setSkillPoints(Main.account.getSkillPoints()+(Level - Main.account.getLevel()));
			
			Main.account.setLevel(Level);
			int xpBarLower = levelCurve(Level);
			int xpBarHigher = levelCurve(Level+1);
			
			getChildren().add(new LevelBar(screenWidth*0.5,
					 screenHeight*0.1,
					 xpBarLower,
					 Main.account.getXp(),
					 xpBarHigher,
					 Level));
			
			mainApp.setLevelBar(xpBarLower, Main.account.getXp(), xpBarHigher, Level);

			getChildren().add(new Label("You currently have " + Main.account.getGainz() + " Gainz."));
			
			Button returnButton = new Button("Return to menu");
			getChildren().add(returnButton);
			returnButton.setOnAction(new EventHandler<ActionEvent>() {


				@Override
				public void handle(ActionEvent event) {
					screenParent.setScreen(Main.workoutLibraryID);
				}
			
			});
			
			setNodeCursor(returnButton);
			
		}
	
	private void addSkillPoints(ArrayList<ExerciseInfo> completedExercises) {
		double strength = 0.0;
		double speed = 0.0;
		double agility = 0.0;
		double endurance = 0.0;
		for (ExerciseInfo i : completedExercises) {
			strength += i.getStrength();
			speed += i.getSpeed();
			agility += i.getAgility();
			endurance += i.getEndurance();
		}
		//first determine which skill is highest
		double maxValue = 0.0;
		if (strength > maxValue) 
			maxValue = strength;
		if (speed > maxValue)
			maxValue = speed;
		if (agility > maxValue)
			maxValue = agility;
		if (endurance > maxValue)
			maxValue = endurance;
		
		
		if (maxValue == strength) {
			Main.account.getCharacterAttributes().setStrength(Main.account.getCharacterAttributes().getStrength() + 1);
		} else if (maxValue == speed) {
			Main.account.getCharacterAttributes().setSpeed(Main.account.getCharacterAttributes().getSpeed() + 1);
		} else if (maxValue == agility) {
			Main.account.getCharacterAttributes().setAgility(Main.account.getCharacterAttributes().getAgility() + 1);
		} else if (maxValue == endurance) {
			Main.account.getCharacterAttributes().setEndurance(Main.account.getCharacterAttributes().getEndurance() + 1);
		}
		
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


	//get level by iterating through level curve
	private int getLevel(int userExp){
		int n = 1;

		while (userExp > levelCurve(n+1)){
			n++;
		};

		return n;
	};

	//define level curve
	private int levelCurve(int n){
		int levelBoundary;

		levelBoundary = Math.round(Math.round((Math.exp(n/7)*150) + ((n-1)*75)-174));

		return levelBoundary;
	};
}
