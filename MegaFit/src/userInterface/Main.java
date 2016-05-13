package userInterface;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.stage.Screen;
import javafx.scene.layout.HBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;


	public class Main extends Application {
		
		double screenWidth;
		double screenHeight;
		Button exit, settings;
		Image exitApp, settingsIcon;

		public void start(Stage primaryStage) {
			try {
				Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
				screenWidth = primaryScreenBounds.getWidth();
				screenHeight = primaryScreenBounds.getHeight();
				
				BorderPane root = new BorderPane();				
				Scene scene = new Scene(root,screenWidth,screenHeight);
								
				Image prodLogo = new Image("res/images/product_logo.jpg");
				ImageView prodLogoView = new ImageView(prodLogo);
				prodLogoView.setPreserveRatio(true);
				prodLogoView.setFitHeight(screenHeight*0.125);	
				
				exitApp = new Image("res/images/download.jpg");
				ImageView quitApp = new ImageView(exitApp);
				quitApp.setPreserveRatio(true);
				quitApp.setFitWidth(screenHeight*0.05);	
				exit = new Button("", quitApp);
				
				settingsIcon = new Image("res/images/settings.png");
				ImageView settingsIconView = new ImageView(settingsIcon);
				settingsIconView.setPreserveRatio(true);
				settingsIconView.setFitWidth(screenHeight*0.05);
				
				settings = new Button("", settingsIconView);
				
				HBox topScreen = new HBox();
				topScreen.setAlignment(Pos.TOP_CENTER);
				topScreen.setId("image-box");
				topScreen.getChildren().addAll(settings, prodLogoView, exit);
				topScreen.setSpacing(screenWidth*0.325);
				
				
				exit.setOnAction (new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent event) {
						try{
							primaryStage.close();
						} catch (Exception e){
							e.printStackTrace();
						}
					
					}
				
				});
				
				settings.setOnAction (new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent event) {
						try{
							//settings menu will be called here when it has been made
						} catch (Exception e){
							e.printStackTrace();
						}
					
					}
				
				});
				
				
				scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
				primaryStage.setScene(scene);
				primaryStage.setFullScreen(true);
				primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
				primaryStage.show();
				LoginMenu loginMenu = new LoginMenu(screenWidth, screenHeight, root);
				root.setTop(topScreen);
				root.setCenter(loginMenu);
				
				Recipes.marshallMealInfo();
				System.out.println("[Main] Marshalling of meal objects complete");
				
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		public static void main(String[] args) {
			launch(args);
			
		}
		

		
	}

