package userInterface;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.stage.Screen;
import javafx.scene.layout.HBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import javax.xml.bind.JAXBException;


public class Main extends Application {



		double screenWidth;
		double screenHeight;
		Button exit, settings;
		Image exitApp, settingsIcon;
		String[] mealNames;
		String[] mealTypes;

		public ScreenFlowController mainController;

		public BorderPane innerRoot = new BorderPane();
		public BorderPane outerRoot = new BorderPane();
		private HBox mainMenuButtons = new HBox();


		/**--------------------------------------------------------------------
		 * ID and file variables for screen controlling
		 */
		// Screen IDs and resource paths for FXML files made with Scene Builder
		public static String workoutLibraryID = "workoutPage";
		public static String workoutPageFile = "wkoutpage/workoutOverview.fxml";



		// Screen IDs for nodes made with Java code,
		public static String menuID = "menu";
		public static String workoutMenuID = "workoutMenu";
		public static String loginID = "login";
		public static String signUpID = "signUp";

		// nodes are built in start()

		/**--------------------------------------------------------------------**/



		public void start(Stage primaryStage) {




				Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
				screenWidth = primaryScreenBounds.getWidth();
				screenHeight = primaryScreenBounds.getHeight();

				/**
				 * build screens: classNameFile, File added for consistency
				 */
				// load the java files



				// Set up the controller
				mainController = new ScreenFlowController();
				mainController.setMainApp(this);

				/**
				 * Load all screens
				 */

				mainController.loadFXMLScreen(Main.workoutLibraryID, Main.workoutPageFile);

				// load java screens
				Menu menuFile = new Menu(screenWidth, screenHeight);
				mainController.loadJavaWrittenScreen(menuID, menuFile);
				WorkoutMenu workoutMenuFile = new WorkoutMenu(screenWidth, screenHeight);
				mainController.loadJavaWrittenScreen(Main.workoutMenuID, workoutMenuFile);
				LoginMenu loginFile = new LoginMenu(screenWidth, screenHeight);
				mainController.loadJavaWrittenScreen(Main.loginID, loginFile);
				SignUpMenu signUpMenuFile = new SignUpMenu(screenWidth, screenHeight);
				mainController.loadJavaWrittenScreen(Main.signUpID, signUpMenuFile);



				/**
				 * Set the first screen
				 */
				mainController.setScreen(loginID);



				/**
				 * The main controller is the stack pane which is set to the screen
				 * (set above)
				 */

				HBox topScreen = buildTopBorder(primaryStage);
				outerRoot.setTop(topScreen);


				mainMenuButtons = buildMenuOptionButtons(screenWidth, screenHeight);
				innerRoot.setCenter(mainController);

				outerRoot.setCenter(innerRoot);

				Scene scene = new Scene(outerRoot,screenWidth,screenHeight);

				scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
				primaryStage.setScene(scene);
				primaryStage.setFullScreen(true);
				primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
				primaryStage.show();


			try {
				Recipes.marshallMealInfo();
				System.out.println("[Main] Marshalling of meal objects complete");
			} catch (JAXBException e) {
				e.printStackTrace();
			}

				
				//Recipes.unmarshallMealInfo(mealNames, mealTypes);
				

		}

		private HBox buildTopBorder(final Stage primaryStage) {
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
			return topScreen;
		}



		private HBox buildMenuOptionButtons(double screenWidth, double screenHeight) {
			HBox hBox = new HBox();
			hBox.setPadding(new Insets(screenWidth*0.001, screenWidth*0.001, screenWidth*0.001, screenWidth*0.001));
			hBox.setSpacing(screenWidth*0.001);
			//define a banner along the top of the menu area in which the sub menu
			//buttons will be loaded

			Button buttonWorkouts = new Button("WORKOUTS");
			buttonWorkouts.setPrefSize(screenWidth*0.25, screenHeight*0.05);

			buttonWorkouts.setOnAction(new EventHandler<ActionEvent>(){
				// TODO  workoutMenu
				@Override
				public void handle(ActionEvent event) {
					//WorkoutMenu workout = new WorkoutMenu(screenWidth, screenHeight);
					try {
						//root.setBottom(workout);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});

			// TODO ensure node cursors still works
			//setNodeCursor(buttonWorkouts);

			Button buttonDiet = new Button("DIET");
			buttonDiet.setPrefSize(screenWidth*0.25, screenHeight*0.05);


			buttonDiet.setOnAction(new EventHandler<ActionEvent>(){

				public void handle(ActionEvent event) {
					// TODO  dietPlanner
					//DietPlanner diet = new DietPlanner(screenWidth, screenHeight);
					try{
						//root.setBottom(diet);
					}catch (Exception e){
						e.printStackTrace();
					}
				}
			});

			//setNodeCursor(buttonDiet);

			Button buttonCharacter = new Button("CHARACTER");
			buttonCharacter.setPrefSize(screenWidth*0.25, screenHeight*0.05);

			buttonCharacter.setOnAction(new EventHandler<ActionEvent>(){

				public void handle (ActionEvent event) {
					// TODO
					//CharacterMenu character = new CharacterMenu(screenWidth, screenHeight, root);
					try {
						//root.setBottom(character);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});

			//
			//setNodeCursor(buttonCharacter);


			Button buttonSocial = new Button("SOCIAL");
			buttonSocial.setPrefSize(screenWidth*0.25, screenHeight*0.05);

			buttonSocial.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent e){

					//SocialMenu social = new SocialMenu(screenWidth, screenHeight, root);
					try {
						//root.setBottom(social);
					} catch (Exception f) {
						f.printStackTrace();
					}

				}
			});

			// TODO node cursor
			//setNodeCursor(buttonSocial);

			hBox.getChildren().addAll(buttonWorkouts, buttonDiet, buttonCharacter, buttonSocial);
			hBox.setSpacing(screenWidth*0.001);




			return hBox;


		}


		public static void main(String[] args) {
			launch(args);
			
		}



		public void getUpdatedScreenID(final String screenID) {
			System.out.println("called with screenID:" + screenID);
			innerRoot.setTop(mainMenuButtons);

			if (screenID == workoutLibraryID) {
				innerRoot.setTop(mainMenuButtons);

			}
			else
			{
				if (innerRoot.getTop() != null) {
					innerRoot.setTop(null);
					System.out.println("remove menu bar called");
				}

			}


		}
		

		
	}

