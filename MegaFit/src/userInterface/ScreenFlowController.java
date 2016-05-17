package userInterface;


import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;

import java.util.HashMap;


/**
 * Controller that loads, adds and removes screens from
 * a Stack Pane.
 * 
 * @author Seb
 *
 */
public class ScreenFlowController extends StackPane {
	/**
	 * Hashmap is a map of all of the screens
	 * String is the node's ID
	 * Node is 
	 */
	// Reference to the main application.
    private Main mainApp;

	private HashMap<String, Node> screenMap = new HashMap<>();
	
	public void setMainApp(Main mainApp) {
		this.mainApp = mainApp;
	}
	
	public ScreenFlowController() {
		// create the stack pane
		super();
	}
	
	public void addScreen(String screenID, Node screen) {
		screenMap.put(screenID, screen);

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

	public void loadJavaWrittenScreen(String screenID, Node javaScreen)
	{
		// get top level node
		Parent parent = (Parent) javaScreen;
		if (parent != null)
		{
			// allow use of setScreenParent and setMainApp
			Controllable javaScreenClass = (Controllable) javaScreen;
			javaScreenClass.setScreenParent(this);
			javaScreenClass.setMainApp(mainApp);
			addScreen(screenID, parent);
			System.out.println("java screen added");

		}
		// TODO remove tests
		else
		{
			System.out.println("java parent is null");
		}


	}
	
	/** 
	 * Show the new screen with a transition
	 * @param screenID the new screen to be shown
	 * @return
	 */
	public boolean setScreen(final String screenID) {
		/** Ensure new screen ID is valid by checking hashmap **/
		if (screenMap.get(screenID) != null) {
			 /** if there is already a screen displayed, transition between
			 *  old and new screen.
			 */
			if (!getChildren().isEmpty()) { // if the node is not empty				
				// remove the node at index 0 ie the top layer
				getChildren().remove(0);
				// get the new screen, add it to the node
				// TODO transitions can be coded here
				getChildren().add(0, screenMap.get(screenID));

						
				} else {
					getChildren().add(screenMap.get(screenID));
				}
			mainApp.getUpdatedScreenID(screenID);

			System.out.println("screen set");
			return true;
					
		} else {	
			System.out.println("setScreen: screen not in hashmap \n");
			return false;
		}


	}
	
	public HashMap<String, Node> getScreenMap() {
		return screenMap;
	}

	
}
