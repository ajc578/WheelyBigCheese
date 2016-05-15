package userInterface;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
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
		String[] mealNames;
		String[] mealTypes;

		public ScreenFlowController mainController;
		public static String workoutPageID = "workoutPage";
		public static String workoutPageFile = "wkoutpage/workoutOverview.fxml";
		public static String workoutMenuID = "workoutMenu";


		public void start(Stage primaryStage) {
			try {



				Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
				screenWidth = primaryScreenBounds.getWidth();
				screenHeight = primaryScreenBounds.getHeight();

				WorkoutMenu workoutMenuFile = new WorkoutMenu();
				mainController = new ScreenFlowController();
				mainController.setMainApp(this);
				mainController.loadFXMLScreen(Main.workoutPageID , Main.workoutPageFile);
				mainController.loadJavaWrittenScreen(Main.workoutMenuID, workoutMenuFile);

				// set screen
				mainController.setScreen(Main.workoutMenuID);

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

				settingsIcon = new Image("res/images/Settings-02.png");
				ImageView settingsIconView = new ImageView(settingsIcon);
				settingsIconView.setPreserveRatio(true);
				settingsIconView.setFitWidth(screenHeight*0.05);

				settings = new Button("", settingsIconView);

				HBox topScreen = new HBox();
				topScreen.setAlignment(Pos.TOP_CENTER);
				topScreen.setId("image-box");
				topScreen.getChildren().addAll(settings, exit);
				topScreen.setSpacing(screenWidth*0.325);

				root.setTop(topScreen);
				root.setCenter(mainController);

				
				scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
				primaryStage.setScene(scene);
				primaryStage.setFullScreen(true);
				primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
				primaryStage.show();


				
				Recipes.marshallMealInfo();
				System.out.println("[Main] Marshalling of meal objects complete");
				
				//Recipes.unmarshallMealInfo(mealNames, mealTypes);
				
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		public static void main(String[] args) {
			launch(args);
			
		}
		

		
	}

