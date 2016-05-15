package userInterface;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;

/**
 * Class defining the layout and functionality of the main menu 
 * screen of the application.
 * 
 * @author - company - B.O.S.S
 * @author - coders - Jennifer Thorpe
 * 
 */

public class Menu extends HBox {
	
	/**
	 * Constructor will build the main menu screen adding the logo at the top
	 * and all the interactive panels underneath
	 * 
	 * @param screenWidth - absolute width of the screen the 
	 *                      application will be render on.
	 * @param screenHeight - absolute height of the screen the 
	 *                      application will be render on
	 * @param root - the Layout manager to which all visible 
	 *               content to be shown on the screen is added                 
	 */
	
	public Menu(double screenWidth, double screenHeight, BorderPane root){		
		

		addMainButtonsBox(screenWidth, screenHeight, root);	
		WorkoutMenu mainMenu = new WorkoutMenu();
		root.setBottom(mainMenu);

	}
	
	/**
	 * Constructor will build the main menu screen adding the logo at the top
	 * and all the interactive panels underneath
	 * 
	 * @param screenWidth - absolute width of the screen the 
	 *                      application will be render on.
	 * @param screenHeight - absolute height of the screen the 
	 *                      application will be render on
	 * @param root - the Layout manager to which all visible 
	 *               content to be shown on the screen is added                 
	 */
	private void addMainButtonsBox(double screenWidth, double screenHeight, BorderPane root) {
		HBox menuOptions = new HBox();
		menuOptions.setPadding(new Insets(screenWidth*0.001, screenWidth*0.001, screenWidth*0.001, screenWidth*0.001));
		menuOptions.setSpacing(screenWidth*0.001);
		//define a banner along the top of the menu area in which the sub menu
		//buttons will be loaded
		
		Button buttonWorkouts = new Button("WORKOUTS");
		buttonWorkouts.setPrefSize(screenWidth*0.25, screenHeight*0.05);
		
		buttonWorkouts.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent event) {
				WorkoutMenu workout = new WorkoutMenu();
				try {
					root.setBottom(workout);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}	
		});
		
		setNodeCursor(buttonWorkouts);
		
		Button buttonDiet = new Button("DIET");
		buttonDiet.setPrefSize(screenWidth*0.25, screenHeight*0.05);
		
		buttonDiet.setOnAction(new EventHandler<ActionEvent>(){

			public void handle(ActionEvent event) {
				DietPlanner diet = new DietPlanner(screenWidth, screenHeight);
				try{
					root.setBottom(diet);
				}catch (Exception e){
					e.printStackTrace();
				}
			}	
		});
		
		setNodeCursor(buttonDiet);
		
		Button buttonCharacter = new Button("CHARACTER");
		buttonCharacter.setPrefSize(screenWidth*0.25, screenHeight*0.05);
		
		buttonCharacter.setOnAction(new EventHandler<ActionEvent>(){
			
			public void handle (ActionEvent event) {
				CharacterMenu character = new CharacterMenu(screenWidth, screenHeight, root);
				try {
					root.setBottom(character);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		setNodeCursor(buttonCharacter);
		
		
		Button buttonSocial = new Button("SOCIAL");
		buttonSocial.setPrefSize(screenWidth*0.25, screenHeight*0.05);
		
		buttonSocial.setOnAction(new EventHandler<ActionEvent>() {
		
		@Override
		public void handle(ActionEvent e){
			
			SocialMenu social = new SocialMenu(screenWidth, screenHeight, root);
			try {
				root.setBottom(social);
			} catch (Exception f) {
				f.printStackTrace();
			}
			
		}
		});
		
		setNodeCursor(buttonSocial);
		
		getChildren().addAll(buttonWorkouts, buttonDiet, buttonCharacter, buttonSocial);
		setSpacing(screenWidth*0.001);
		
		
	}
	
	
		
	
	
	public void setNodeCursor (Node node) {
		
		node.setOnMouseEntered(event -> setCursor(Cursor.HAND));
		node.setOnMouseExited(event -> setCursor(Cursor.DEFAULT));
	}
	

}
