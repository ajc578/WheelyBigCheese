package userInterface;

import java.net.UnknownHostException;

import javax.xml.bind.JAXBException;

import account.Account;
import account.AccountHandler;
import account.ClientSide;
import account.Protocol;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.*;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import presentationViewer.ExceptionFx;


public class Main extends Application {

	private static final String clientDir = "src/res/clientAccounts/";
	private static final String activeAccountPath = "src/res/clientAccounts/activeAccount.txt";

	double screenWidth;
	double screenHeight;
	Button exit;
	Image exitApp;
	String[] mealNames;
	String[] mealTypes;

	public StackPaneUpdater controllableCenterScreen;

	public BorderPane innerRoot = new BorderPane();
	public BorderPane outerRoot = new BorderPane();
	public GridPane topScreen = new GridPane();
	public HBox levBar = new HBox();
	private HBox mainMenuButtons = new HBox();

	private StackPane stackPaneRoot = new StackPane();


	//ClientSide comms
	protected static ClientSide client = null;
	protected static boolean serverDetected = false;
	protected static boolean loginStatus = false;

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
	protected Stage primaryStage;

	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		
		this.primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {

            @Override
            public void handle(WindowEvent t) {
                System.exit(0);
            }

        });
		//setup client side
		setupComms();



		Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
		screenWidth = primaryScreenBounds.getWidth();
		screenHeight = primaryScreenBounds.getHeight();
		stackPaneRoot.setMinSize(screenWidth, screenHeight);

		// Set up the controller
		controllableCenterScreen = new StackPaneUpdater(screenWidth,screenHeight);
		controllableCenterScreen.setMainApp(this);

		/**
		 * Load all screens into controller's hashmap
		 */

		// load java screens - just login and signup menu
		loadLoginAndSignUp();
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

		buildTopBorder();
		outerRoot.setTop(topScreen);


		innerRoot.setCenter(controllableCenterScreen);

		outerRoot.setCenter(innerRoot);

		stackPaneRoot.getChildren().add(0, outerRoot);




		Scene scene = new Scene(stackPaneRoot,screenWidth,screenHeight);
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
	
	private void loadLoginAndSignUp() {

		LoginMenu loginInstance = new LoginMenu(screenWidth, screenHeight);
		controllableCenterScreen.loadJavaWrittenScreen(Main.loginID, loginInstance);

		SignUpMenu signUpMenuInstance = new SignUpMenu(screenWidth, screenHeight);
		controllableCenterScreen.loadJavaWrittenScreen(Main.signUpID, signUpMenuInstance);
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

	private void buildTopBorder() {
		Image prodLogo = new Image("res/images/product_logo.jpg");
		ImageView prodLogoView = new ImageView(prodLogo);
		prodLogoView.setPreserveRatio(true);
		prodLogoView.setFitHeight(screenHeight*0.125);

		exitApp = new Image("res/images/download.jpg");
		ImageView quitApp = new ImageView(exitApp);
		quitApp.setPreserveRatio(true);
		quitApp.setFitWidth(screenHeight*0.05);
		exit = new Button("", quitApp);

		topScreen = new GridPane();
		topScreen.minWidthProperty().bind(outerRoot.widthProperty().subtract(10));

		ColumnConstraints genericColumn = new ColumnConstraints();
		ColumnConstraints fillerColumn = new ColumnConstraints();

		fillerColumn.setHgrow(Priority.ALWAYS);
		genericColumn.maxWidthProperty().bind(outerRoot.widthProperty().multiply(0.3));
		genericColumn.minWidthProperty().bind(outerRoot.widthProperty().multiply(0.3));
		topScreen.getColumnConstraints().add(genericColumn);
		topScreen.getColumnConstraints().add(fillerColumn);
		topScreen.getColumnConstraints().add(genericColumn);
		topScreen.getColumnConstraints().add(fillerColumn);
		topScreen.getColumnConstraints().add(genericColumn);

		topScreen.setId("image-box");
		LevelBar tempLevelBar = new LevelBar(screenWidth*0.3,screenHeight*0.05);
		tempLevelBar.setVisible(false);


		Label filler1 = new Label("   ");
		Label filler2 = new Label("   ");

		topScreen.add(filler1, 1, 0);
		topScreen.add(prodLogoView, 2, 0);
		topScreen.add(filler2, 3, 0);
		topScreen.add(exit, 4, 0);
		levBar.getChildren().add(tempLevelBar);
		topScreen.add(levBar, 0, 0);
		
		exit.setAlignment(Pos.CENTER_RIGHT);

		GridPane.setHalignment(levBar, HPos.LEFT);
		GridPane.setHalignment(exit, HPos.RIGHT);
		GridPane.setHalignment(exit, HPos.CENTER);

		exit.setOnAction (new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				try{
					if (Main.serverDetected && Main.client.isAccessible() && Main.loginStatus) {
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
					} if (Main.serverDetected && Main.client.isAccessible() && !Main.loginStatus) {
						Main.client.closeConnection();
						Main.client.join();
					} else if (Main.loginStatus) {
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
					controllableCenterScreen.loadSocialMenu();
					controllableCenterScreen.setScreen(Main.socialMenuID);
					if (Main.account.getFriends().size() == 0) {
						ExceptionFx except = new ExceptionFx(AlertType.WARNING, "Reminder",
										 "You have not added any friends to your Friend List.",
										 "You can add friends by clicking the 'Add Friends' button"
										 + ", and searching for their accounts in the search box.", primaryStage);
						except.show();
					}
				} else {
					ExceptionFx except = new ExceptionFx(AlertType.WARNING, "Offline Error",
							 "You are not connected to the server",
							 "You're session has been switched to offline. This means"
							 + " that all social features wil be inaccessible. "
						 	 + "You will need to restart the program to reconnect.", primaryStage);
					except.show();
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

	public void setLevelBar(int startXP, int currentXP, int endXP, int currentLevel) {
		LevelBar levelBar = new LevelBar(screenWidth*0.3,
			     						 screenHeight*0.05,
			     						 startXP,
			     						 currentXP,
			     						 endXP,
			     						 currentLevel);
		levBar.setAlignment(Pos.CENTER_LEFT);
		levBar.getChildren().clear();
		levBar.getChildren().add(levelBar);

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

	public void launchPresentation(String filename) {
		WorkoutView workoutView = new WorkoutView(this.screenWidth, (this.screenHeight), filename, this);
		workoutView.setMainApp(this);
		workoutView.setScreenParent(controllableCenterScreen);
		stackPaneRoot.getChildren().remove(outerRoot);
		stackPaneRoot.getChildren().add(0, workoutView);
	}

	public void returnToAppScreens() {
		stackPaneRoot.getChildren().remove(0);
		stackPaneRoot.getChildren().add(0, outerRoot);
	}
}

