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

/**
 * A screen that displays at the end of a workout, shows 
 * what exercise the user completed, calculates how many points to
 * award and updates thee users account with them
 * 
 * <p> <STRONG> Developed by </STRONG> <p>
 * Alexander Chapman
 * <p> <STRONG> Developed for </STRONG> <p>
 * BOSS
 * @author - company - B.O.S.S
 * @author - coders - Alexander Chapman
 */
public class WorkoutEndCard extends VBox implements Controllable {

	private StackPaneUpdater screenParent;
	private Main mainApp;

	/**Display the workout end card and updates user information
	 * 
	 * @param screenWidth
	 * @param screenHeight
	 * @param completedExercises - the list of exercise slides completed given
	 * 		  by the presentation viewer
	 * @param mainApp - reference to the top bar of the application
	 */
	public WorkoutEndCard (double screenWidth, double screenHeight,
						   ArrayList<ExerciseInfo> completedExercises, Main mainApp) {
		
		this.mainApp = mainApp;
		
		//update one of the users stat points based on what this workout is heaviest in
		String attributeGained = addSkillPoints(completedExercises); 
			
			//Condense the list of completed exercise slides into groups of sets
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
			
			//calculate the user's level
			int Level = getLevel(Main.account.getXp());
			
			//award the account a skill point each time it levels up
			Main.account.setSkillPoints(Main.account.getSkillPoints()+(Level - Main.account.getLevel()));
			
			
			//update the users level add a level bar to the endcard and update the level bar 
			//on the top bar of the app
			Main.account.setLevel(Level);
			int xpBarLower = levelCurve(Level);
			int xpBarHigher = levelCurve(Level+1);		
			if (xpBarLower<0)xpBarLower=0;
			getChildren().add(new LevelBar(screenWidth*0.5,
					 screenHeight*0.1,
					 xpBarLower,
					 Main.account.getXp(),
					 xpBarHigher,
					 Level));			
			mainApp.setLevelBar(xpBarLower, Main.account.getXp(), xpBarHigher, Level);

			getChildren().add(new Label("You currently have " + Main.account.getGainz() + " Gainz."));
			getChildren().add(new Label("You were awarded a point in " + attributeGained + " for completing this exercise."));
			
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
	
	
	/** calculate which attribute the completed workout is strongest in and give
	 * the user a point in that attribute
	 * @param completedExercises
	 * @return string saying which attribute ws increased
	 */
	private String addSkillPoints(ArrayList<ExerciseInfo> completedExercises) {
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
		
		String attribute = "";
		if (maxValue == strength) {
			Main.account.getCharacterAttributes().setStrength(Main.account.getCharacterAttributes().getStrength() + 1);
			attribute = "Strength";
		} else if (maxValue == speed) {
			Main.account.getCharacterAttributes().setSpeed(Main.account.getCharacterAttributes().getSpeed() + 1);
			attribute = "Speed";
		} else if (maxValue == agility) {
			Main.account.getCharacterAttributes().setAgility(Main.account.getCharacterAttributes().getAgility() + 1);
			attribute = "Agility";
		} else if (maxValue == endurance) {
			Main.account.getCharacterAttributes().setEndurance(Main.account.getCharacterAttributes().getEndurance() + 1);
			attribute = "Endurance";
		}
		return attribute;
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


	/**
	 * calculate what level a user is from their xp (points)
	 *  by iterating through level curve
	 * @param userExp
	 * @return user level
	 */
	private int getLevel(int userExp){
		int n = 1;

		while (userExp > levelCurve(n+1)){
			n++;
		};

		return n;
	};

	/**
	 * calculates how many points are needed to reach a certain level
	 * @param n - which level to calculate
	 * @return how much experience (points) are required
	 */
	private int levelCurve(int n){
		int levelBoundary;

		levelBoundary = Math.round(Math.round((Math.exp(n/7)*150) + ((n-1)*75)-174));

		return levelBoundary;
	};
}
