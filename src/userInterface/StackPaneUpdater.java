package userInterface;


import java.util.HashMap;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;


/**
 * Controller that loads, adds and removes screens from
 * a Stack Pane.
 * 
 * @author Seb
 *
 */
public class StackPaneUpdater extends StackPane {
	private final double screenWidth;
	private final double screenHeight;
	
	private DietMenu dietMenu;
	/**
	 * Hashmap is a map of all of the screens
	 * String is the node's ID
	 * Node is
	 */
	// Reference to the main application.
	private Main mainApp;

	private DietPlanner dietPlanner;
	private WorkoutView workoutDetails;
	private CreateCharacter createCharacter;
	private SocialMenu socialMenu;

	private HashMap<String, Node> screenMap = new HashMap<>();

	public void setMainApp(Main mainApp) {
		this.mainApp = mainApp;
	}

	public StackPaneUpdater(double screenWidth, double screenHeight) {
		// create the stack pane
		super();
		// screen dimensions will be used to build screens
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;


	}

	public void addScreen(String screenID, Node screen) {
		// put is a hashmap method that associates a string
		// with a value
		// in this case the string is the screen name
		// the value is the sceen's nodes
		screenMap.put(screenID, screen);
		// .put returns the previous if given duplicate ID
		// since the return is not assigned, this method can be
		// used to update a screen in the hashmap
		// the old screen will be garbage collected

	}

	public Node getScreen(String screenID) {
		return screenMap.get(screenID);
	}

	public boolean loadFXMLScreen(String screenID, String resource) {
		try {

			FXMLLoader myLoader = new FXMLLoader(getClass().getResource(resource));

			// get node
			Parent parent = (Parent) myLoader.load();
			// get the controller
			Controllable fxmlController = (Controllable) myLoader.getController();
			// tell the controller that this is the parent node
			fxmlController.setScreenParent(this);
			fxmlController.setMainApp(mainApp);
			// add the node to hashmap
			addScreen(screenID, parent);
			return true;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println("loadFXMLScreen: fxml file could not be loaded");
			System.out.println(resource);
			return false;
		}
	}

	public void loadJavaWrittenScreen(String screenID, Node javaScreen) {
		// get top level node
		Parent parent = (Parent) javaScreen;
		if (parent != null) {
			// allow use of setScreenParent and setMainApp
			Controllable javaScreenClass = (Controllable) javaScreen;
			javaScreenClass.setScreenParent(this);
			javaScreenClass.setMainApp(mainApp);
			addScreen(screenID, parent);
		}
		// TODO remove tests
		else {
			System.out.println("java parent is null");
		}


	}

	/**
	 * Show the new screen with a transition
	 *
	 * @param screenID the new screen to be shown
	 * @return
	 */
	public boolean setScreen(final String screenID) {
		/** Ensure new screen ID is valid by checking hashmap **/
		if (screenMap.get(screenID) != null) {

			removeTopLayerThenAddNewTopLayer(screenID);

			mainApp.getUpdatedScreenID(screenID);

			System.out.println("screen flow controller screen set: " + screenID);
			return true;

		} else {
			System.out.println("setScreen: screen not in hashmap: " + screenID);
			return false;
		}


	}

	public HashMap<String, Node> getScreenMap() {
		return screenMap;
	}


	public void removeTopLayerThenAddNewTopLayer(String screenID) {
		if (!getChildren().isEmpty()) { // if the node is not empty
			// remove the node at index 0 ie the top layer
			getChildren().remove(0);

			// get the new screen, add it to the node
			// TODO screen transitions can be coded here
			getChildren().add(0, screenMap.get(screenID));

		} else {
			getChildren().add(screenMap.get(screenID));
		}

	}



	public void loadWorkoutLibrary() {
		loadFXMLScreen(Main.workoutLibraryID, Main.workoutPageFile);
	}
	
	public void loadDietMenu() {
		dietMenu = new DietMenu(screenWidth, screenHeight);
		loadJavaWrittenScreen(Main.dietMenuID, dietMenu);
	}

	public void loadDietPlanner() {
		dietPlanner = new DietPlanner(screenWidth, screenHeight);
		dietPlanner.addButtons();
		loadJavaWrittenScreen(Main.dietPlannerID, dietPlanner);

	}
	public void displayNode(Node temporaryNode) {
		this.getChildren().remove(0);
		this.getChildren().add(temporaryNode);
	}

	public void loadCharacterDashboard() {
		loadFXMLScreen(Main.characterDashID, Main.characterDashFile);
	}
	
	public void loadSocialMenu() {
		socialMenu = new SocialMenu(screenWidth, screenHeight);
		loadJavaWrittenScreen(Main.socialMenuID, socialMenu);
	}
}
