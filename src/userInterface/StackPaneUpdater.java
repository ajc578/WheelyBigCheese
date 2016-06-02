package userInterface;


import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;

import java.util.HashMap;


/**
 * A screen controller that allows switching between in app screens.
 * This class extends a <code>StackPane</code> which is used as the parent of all in app screens.
 * Includes methods to load nodes into a <code>HashMap</code> and access these nodes by their <code>key</code>.
 * This allows a screen to be removed and replaced by the next screen using the {@link #setScreen(String) setScreen(nextScreenID)}
 * method that belongs to this class.
 *
 * <p> <STRONG> Developed by </STRONG> <p>
 * Sebastien Corrigan
 * <p> <STRONG> Tested by </STRONG> <p>
 * Sebastien Corrigan
 * <p> <STRONG> Developed for </STRONG> <p>
 * BOSS
 * @author Sebastien Corrigan
 */
public class StackPaneUpdater extends StackPane {
	//================================================================================
	// Fields
	//================================================================================
	private final double screenWidth;
	private final double screenHeight;
	
	private DietMenu dietMenu;

	/**
	 * Reference to the main application.
	 */
	private Main mainApp;

	private DietPlanner dietPlanner;
	private WorkoutView workoutDetails;
	private CreateCharacter createCharacter;
	private SocialMenu socialMenu;

	/**
	 * Holds a map of in app screens.
	 * Type of Key maintained by this map: String.
	 * Type of mapped values: Nodes.
	 */
	private HashMap<String, Node> screenMap = new HashMap<>();

	//================================================================================
	// Constructor
	//================================================================================

	/**
	 * Constructor for this class
	 * @param screenWidth dimensions used to give screen width and height to the extended <code>StackPane</code>
	 * @param screenHeight
     */
	public StackPaneUpdater(double screenWidth, double screenHeight) {
		// create the stack pane
		super();
		// screen dimensions will be used for constructors of built screens
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;
	}



	//================================================================================
	// Members
	//================================================================================
	/**
	 * Uses the {@link java.util.HashMap#put Hashmap.put()} method to associate a screen's node
	 * with a screenID and load the screen into <code>screenMap</code> so that it can later be displayed
	 * <code>put</code> returns the previous value at the associated screenID and since this return
	 * is not assigned, the old screen nodes become elligible for garbage collection.
	 *
	 * @param screenID associated string key
	 * @param screen screen node value
     */
	public void addScreen(String screenID, Node screen) {
		screenMap.put(screenID, screen);
	}
	/**
	 * Uses {@link javafx.fxml.FXMLLoader#load() FXMLLoader.load()} to build the screen nodes expressed
	 * by an FXML file to then add it to the <code>screenMap</code>
	 *
	 * @param screenID the ID to be associated with the loaded fxml file's nodes
	 * @param resource relative path to the fxml file to be loaded
     * @return
     */
	public boolean loadFXMLScreen(String screenID, String resource) {
		try {
			FXMLLoader myLoader = new FXMLLoader(getClass().getResource(resource));
			// get node
			Parent parent = (Parent) myLoader.load();
			// get the controller and use interface casting to be able to invoke the
			// .setScreen and .setMainApp methods so that it can be Controllable
			Controllable fxmlController = (Controllable) myLoader.getController();
			// tell the controller that this is the parent node
			fxmlController.setScreenParent(this);
			fxmlController.setMainApp(mainApp);
			// add the node screenMap
			addScreen(screenID, parent);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * This method takes the parent node of a screen that has already been built from
	 * the instantiation of its object. This method then associates the node with the
	 * <code>screenID</code> <code>Key</code> and uses <code>addScreen</code> to load it in the <code>screenMap</code>
	 * @param screenID associated string key
	 * @param node screen node value
     */
	public void loadJavaWrittenScreen(String screenID, Node node) {
		// get top level node
		Parent parent = (Parent) node;
		if (parent != null) {
			// allow use of setScreenParent and setMainApp
			Controllable javaScreenClass = (Controllable) node;
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
	 * Shows the new screen
	 *
	 * @param screenID the new screen to be shown
	 * @return true/false if screen set
	 */
	public boolean setScreen(final String screenID) {
		/** Ensure new screen ID is valid by checking hashmap **/
		if (screenMap.get(screenID) != null) {

			removeTopLayerThenAddNewTopLayer(screenID);

			mainApp.setUpdatedScreenID(screenID);

			System.out.println("screen flow controller screen set: " + screenID);
			return true;

		} else {
			System.out.println("setScreen: screen not in hashmap: " + screenID);
			return false;
		}


	}

	/**
	 * Removes the top layer of <code>this</code> and places the screen associated with the parameter
	 * @param screenID key value for the screen to be swapped to
     */
	public void removeTopLayerThenAddNewTopLayer(String screenID) {
		if (!getChildren().isEmpty()) {
			// remove the node at index 0, the previous screen
			getChildren().remove(0);

			// get the new screen, add it to this StackPane
			getChildren().add(0, screenMap.get(screenID));

		} else { // this is the first screen so there is nothing to remove
			getChildren().add(screenMap.get(screenID));
		}
	}


	/**
	 * loads a new WorkoutLibrary screen into the screen map at the same ID as previous WorkoutLibrary
	 * Call when an updated WorkoutLibrary needs to be put in the <code>screenMap</code> using
	 * {@link StackPaneUpdater#loadFXMLScreen(String,String) loadFXMLScreen()}
	 */
	public void loadWorkoutLibrary() {
		loadFXMLScreen(Main.workoutLibraryID, Main.workoutPageFile);
	}

	/**
	 * loads a new DietMenu screen into the screen map at the same ID as previous DietMenu
	 * Call when an updated DietMenu needs to be put in the <code>screenMap</code> using
	 * {@link StackPaneUpdater#loadJavaWrittenScreen(String, Node) loadJavaWrittenScreen}
	 */
	public void loadDietMenu() {
		dietMenu = new DietMenu(screenWidth, screenHeight);
		loadJavaWrittenScreen(Main.dietMenuID, dietMenu);
	}

	/**
	 * loads a new DietPlanner screen into the screen map at the same ID as previous DietPlanner
	 * Call when an updated DietPlanner needs to be put in the <code>screenMap</code> using
	 * {@link StackPaneUpdater#loadJavaWrittenScreen(String, Node) loadJavaWrittenScreen}
	 */
	public void loadDietPlanner() {
		dietPlanner = new DietPlanner(screenWidth, screenHeight);
		dietPlanner.addButtons();
		loadJavaWrittenScreen(Main.dietPlannerID, dietPlanner);

	}

	/**
	 * Call at end of presentation
	 * @param temporaryNode
     */
	public void displayNode(Node temporaryNode) {
		this.getChildren().remove(0);
		this.getChildren().add(temporaryNode);
	}

	/**
	 * creates a new CharacterDashBoard screen into the screen map at the same ID as previous DietPlanner
	 * Call when an updated DietPlanner needs to be put in the <code>screenMap</code> using
	 * {@link StackPaneUpdater#loadFXMLScreen(String, String) loadFXMLScreen}
	 */
	public void loadCharacterDashboard() {
		loadFXMLScreen(Main.characterDashID, Main.characterDashFile);
	}
	
	public void loadSocialMenu() {
		socialMenu = new SocialMenu(screenWidth, screenHeight);
		loadJavaWrittenScreen(Main.socialMenuID, socialMenu);
	}

	/**
	 * sets the <code>mainApp</code> reference for this class which will be passed on to screens that implement
	 *
	 * @param mainApp
     */
	public void setMainApp(Main mainApp) {
		this.mainApp = mainApp;
	}
}
