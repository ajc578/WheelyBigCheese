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
			
			Parent parent = (Parent) myLoader.load();
			
			Controllable myScreenController = (Controllable) myLoader.getController();
			myScreenController.setScreenParent(this);
			myScreenController.setMainApp(mainApp);
			addScreen(screenID, parent);
			return true;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println("loadFXMLScreen: fxml file could not be loaded");
			System.out.println(resource);
			return false;
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
				// remove the node at index 0
				getChildren().remove(0);
				// get the new screen, add it to the node with a fade in
				getChildren().add(0, screenMap.get(screenID));
						
				} else {
					getChildren().add(screenMap.get(screenID));
				}
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
