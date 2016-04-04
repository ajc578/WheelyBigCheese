package userInterface;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;

/**
 * Class defining the layout and functionality of the main menu 
 * screen of the application.
 * 
 * @author - company - B.O.S.S
 * @author - coders - Jennifer Thorpe
 * 
 */

public class Menu extends VBox {
	
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
		
		putImage(screenWidth, screenHeight);	
		addMainButtonsBox(screenWidth, screenHeight, root);		
		
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
		HBox hbox = new HBox();
		hbox.setPadding(new Insets(screenWidth*0.001, screenWidth*0.001, screenWidth*0.001, screenWidth*0.001));
		hbox.setSpacing(screenWidth*0.001);
		//define a banner along the top of the menu area in which the sub menu
		//buttons will be loaded
		
		Button buttonWorkouts = new Button("WORKOUTS");
		buttonWorkouts.setPrefSize(screenWidth*0.25, screenHeight*0.05);
		
		buttonWorkouts.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent event) {
				WorkoutMenu workout = new WorkoutMenu(screenWidth, screenHeight, root);
				try {
					root.setCenter(workout);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}	
		});
		
		Button buttonDiet = new Button("DIET");
		buttonDiet.setPrefSize(screenWidth*0.25, screenHeight*0.05);
		
		buttonDiet.setOnAction(new EventHandler<ActionEvent>(){

			public void handle(ActionEvent event) {
				DietMenu diet = new DietMenu(screenWidth, screenHeight);
				try{
					root.setCenter(diet);
				}catch (Exception e){
					e.printStackTrace();
				}
			}	
		});
		
		Button buttonCharacter = new Button("CHARACTER");
		buttonCharacter.setPrefSize(screenWidth*0.25, screenHeight*0.05);
		
		buttonCharacter.setOnAction(new EventHandler<ActionEvent>(){
			
			public void handle (ActionEvent event) {
				CharacterMenu character = new CharacterMenu(screenWidth, screenHeight, root);
				try {
					root.setCenter(character);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
			
		
		Button buttonSocial = new Button("SOCIAL");
		buttonSocial.setPrefSize(screenWidth*0.25, screenHeight*0.05);
		
		hbox.getChildren().addAll(buttonWorkouts, buttonDiet, buttonCharacter, buttonSocial);
		getChildren().addAll(hbox);
		
	}
	
	public void putImage(double screenWidth, double screenHeight) {
		ImageView imageView = new ImageView();
		HBox imageBox = new HBox();
		imageBox.setAlignment(Pos.TOP_CENTER);
		imageBox.setId("image-box");
		
		Image prodLogo = new Image("res/images/product_logo.jpg");
		ImageView prodLogoView = new ImageView(prodLogo);
		prodLogoView.setImage(prodLogo);
		prodLogoView.setFitWidth(screenWidth*0.4);
		prodLogoView.setFitHeight(screenHeight*0.125);
		
		StackPane.setAlignment(imageView, Pos.TOP_CENTER);
		imageBox.getChildren().addAll(prodLogoView);
		getChildren().addAll(imageBox);
		
	}
	

}
