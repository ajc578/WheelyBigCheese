package userInterface;
import javafx.scene.layout.BorderPane;
import javafx.scene.Cursor;
import javafx.scene.Node;

/**
 * Class defining the layout and functionality of the main menu 
 * screen of the application.
 * 
 * @author - company - B.O.S.S
 * @author - coders - Jennifer Thorpe
 * 
 */

public class Menu extends BorderPane implements Controllable {

	private StackPaneUpdater screenParent;
	private Main mainApp;


	/**
	 * Constructor will build the main menu screen adding the logo at the top
	 * and all the interactive panels underneath
	 * 
	 * @param screenWidth - absolute width of the screen the 
	 *                      application will be render on.
	 * @param screenHeight - absolute height of the screen the 
	 *                      application will be render on
	 * @param  - the Layout manager to which all visible
	 *               content to be shown on the screen is added                 
	 */
	
	public Menu(double screenWidth, double screenHeight){

		//addMainButtonsBox(screenWidth, screenHeight);


	}
	
	/**
	 * Constructor will build the main menu screen adding the logo at the top
	 * and all the interactive panels underneath
	 *
	 * @param screenWidth - absolute width of the screen the
	 *                      application will be render on.
	 * @param screenHeight - absolute height of the screen the
	 *                      application will be render on
	 * @param  - the Layout manager to which all visible
	 *               content to be shown on the screen is added
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
