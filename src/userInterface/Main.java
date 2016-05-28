package userInterface;

import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.xml.bind.JAXBException;

import account.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import parser.ExerciseInfo;


public class Main extends Application {

	private static final String clientDir = "src/res/clientAccounts/";
	private static final String activeAccountPath = "src/res/clientAccounts/activeAccount.txt";

	double screenWidth;
	double screenHeight;
	Button exit, settings;
	Image exitApp, settingsIcon;
	String[] mealNames;
	String[] mealTypes;

	public StackPaneUpdater controllableCenterScreen;

	public BorderPane innerRoot = new BorderPane();
	public BorderPane outerRoot = new BorderPane();
	private HBox mainMenuButtons = new HBox();



	//ClientSide comms
	protected static ClientSide client = null;
	protected static boolean serverDetected = false;

	//Global Account
	public static Account account = null;

	/**--------------------------------------------------------------------
	 * ID and file variables for screen controlling
	 */
	// Screen IDs and resource paths for FXML files made with Scene Builder
	public static String workoutLibraryID 	= "workoutPage";
	public static String workoutPageFile 	= "wkoutpage/workoutOverview.fxml";

	public static String characterDashID 	= "chardash";
	public static String characterDashFile  = "characterDashBoard.fxml";

	// Diet Planner is defined here to allow access to public addButton method
	private DietPlanner dietPlannerInstance;

	// Screen IDs for nodes made with Java code, used as index for Hashmap<String, Node>
	public static String characterMenuID 	= "characterMenu";
	public static String createCharacterID  = "createCharacter";
	public static String createWorkoutID 	= "createWorkout";
	public static String dietMenuID 		= "dietMenu";
	public static String dietPlannerID		= "dietPlanner";
	public static String loginID 			= "login";
	public static String menuID 			= "menu";
	public static String shopMenuID			= "shopMenu";
	public static String signUpID 			= "signUp";
	public static String socialMenuID		= "socialMenu";
	public static String workoutEndCardID	= "workoutEndCard";
	public static String workoutMenuID 		= "workoutMenu";
	public static String presentationID		= "presentation";

	// nodes are built in start()

	/**--------------------------------------------------------------------**/





	public void start(Stage primaryStage) {

		//setup client side
		setupComms();

		Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
		screenWidth = primaryScreenBounds.getWidth();
		screenHeight = primaryScreenBounds.getHeight();

		// Set up the controller
		controllableCenterScreen = new StackPaneUpdater(screenWidth,screenHeight);
		controllableCenterScreen.setMainApp(this);

		/**
		 * Load all screens into controller's hashmap
		 */

		// load java screens
		loadJavaScreens();
		// load fxml screens


		mainMenuButtons = buildMenuOptionButtons(screenWidth, screenHeight);
		/**
		 * Set the first screen
		 */
		controllableCenterScreen.setScreen(loginID);

		/**
		 * The main controller is the stack pane which is set to the screen
		 * (set above)
		 */

		HBox topScreen = buildTopBorder(primaryStage);
		outerRoot.setTop(topScreen);


		innerRoot.setCenter(controllableCenterScreen);

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

	private void loadJavaScreens() {
		// TODO  do loading with for loop
		Menu menuInstance = new Menu(screenWidth, screenHeight);
		controllableCenterScreen.loadJavaWrittenScreen(menuID, menuInstance);


		WorkoutMenu workoutMenuInstance = new WorkoutMenu(screenWidth, screenHeight);
		controllableCenterScreen.loadJavaWrittenScreen(Main.workoutMenuID, workoutMenuInstance);

		LoginMenu loginInstance = new LoginMenu(screenWidth, screenHeight);
		controllableCenterScreen.loadJavaWrittenScreen(Main.loginID, loginInstance);

		SignUpMenu signUpMenuInstance = new SignUpMenu(screenWidth, screenHeight);
		controllableCenterScreen.loadJavaWrittenScreen(Main.signUpID, signUpMenuInstance);

		DietMenu dietMenuInstance = new DietMenu(screenWidth, screenHeight);
		controllableCenterScreen.loadJavaWrittenScreen(dietMenuID, dietMenuInstance);

		CreateWorkout createWorkoutInstance = new CreateWorkout(screenWidth, screenHeight);
		controllableCenterScreen.loadJavaWrittenScreen(createWorkoutID, createWorkoutInstance);

		CharacterMenu characterMenuInstance = new CharacterMenu(screenWidth, screenHeight);
		controllableCenterScreen.loadJavaWrittenScreen(characterMenuID, characterMenuInstance);


		dietPlannerInstance = new DietPlanner(screenWidth, screenHeight);
		controllableCenterScreen.loadJavaWrittenScreen(dietPlannerID, dietPlannerInstance );

		ShopMenu shopMenuInstance = new ShopMenu(screenWidth, screenHeight);
		controllableCenterScreen.loadJavaWrittenScreen(shopMenuID, shopMenuInstance);

		SocialMenu socialMenuInstance = new SocialMenu(screenWidth, screenHeight);
		controllableCenterScreen.loadJavaWrittenScreen(socialMenuID, socialMenuInstance);

//		WorkoutEndCard workoutEndCardInstance = new WorkoutEndCard(screenWidth, screenHeight, completedExercises);
//		controllableCenterScreen.loadJavaWrittenScreen(workoutEndCardID, workoutEndCardInstance);


	}

	private void setupComms() {
		int portNumber = 4444;
		boolean clientConnected = false;

		for (int i = 0; i < 5; i++) {
			try {
				Main.client = new ClientSide(portNumber);
				Thread.sleep(2000);
				if (!client.isConnectionError())
					clientConnected = true;
				break;
			} catch (UnknownHostException e1) {
				System.out.println("could not connected to port 4444");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		if (clientConnected) {
			serverDetected = true;
		} else {
			System.out.println("The server is not running");
		}

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
		topScreen.setSpacing(50);

		exit.setOnAction (new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				try{
					if (Main.serverDetected && Main.client.isAccessible()) {
						client.logout(Main.account);
						while (true) {
							String output = client.receive();
							if (output.equals(Protocol.LOGOUT_SUCCESS)) {
								break;
							} else if (output.startsWith(Protocol.ERROR)) {
								System.out.println("Error returned in clinet main: " + output);
								break;
							}
						}
					} else {
						AccountHandler accHandler = new AccountHandler();
						accHandler.setAccount(Main.account);
						accHandler.logout(clientDir);
					}
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
				// TODO  change variable to screenParent for consistency with other classes?
				controllableCenterScreen.setScreen(Main.workoutLibraryID);
			}
		});

		// TODO ensure node cursors still works
		//setNodeCursor(buttonWorkouts);

		Button buttonDiet = new Button("DIET");
		buttonDiet.setPrefSize(screenWidth*0.25, screenHeight*0.05);


		buttonDiet.setOnAction(new EventHandler<ActionEvent>(){

			public void handle(ActionEvent event) {
				controllableCenterScreen.setScreen(Main.dietPlannerID);
			}
		});

		//setNodeCursor(buttonDiet);

		Button buttonCharacter = new Button("CHARACTER");
		buttonCharacter.setPrefSize(screenWidth*0.25, screenHeight*0.05);

		buttonCharacter.setOnAction(new EventHandler<ActionEvent>(){

			public void handle (ActionEvent event) {
				controllableCenterScreen.loadCharacterDashboard();
				controllableCenterScreen.setScreen(Main.characterDashID);
			}
		});

		//
		//setNodeCursor(buttonCharacter);


		Button buttonSocial = new Button("SOCIAL");
		buttonSocial.setPrefSize(screenWidth*0.25, screenHeight*0.05);

		buttonSocial.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e){
				if (serverDetected) {
					controllableCenterScreen.setScreen(Main.socialMenuID);
				} else {

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

		updateInnerRootDependingOnScreen(screenID);

	}

	public void updateInnerRootDependingOnScreen(final String screenID) {
		// TODO test if the mainMenuButtons is already in Top to avoid adding them
		if ((screenID != loginID) && (screenID != signUpID))

		{
			innerRoot.setTop(mainMenuButtons);
			System.out.println("innerRoot top set to mainMenuButtons");
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

