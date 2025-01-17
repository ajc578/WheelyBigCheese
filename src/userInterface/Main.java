package userInterface;

import java.net.UnknownHostException;

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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import presentationViewer.ExceptionFx;

/**
 * Main app for the MegaFit application
 *
 * @author
 */

public class Main extends Application {

	//================================================================================
	// Fields
	//================================================================================
	private static final String clientDir = "src/res/clientAccounts/";

	/**
	 * screen dimensions
	 */
	double screenWidth, screenHeight;

	Button exit;
	Image exitApp;

	/**
	 * This object extends StackPane and serves as the parent node for all in-app screens
	 * @see StackPaneUpdater
	 */
	public StackPaneUpdater controllableCenterScreen;

	/**
	 * an inner BorderPane that holds the <code>controllableCenterScreen</code> in its <code>CENTER</code>
	 */
	public BorderPane innerRoot = new BorderPane();
	/**
	 * outer BorderPane that shows the level bar, logo and <code>mainMenuButtons</code> in its top border
	 * and the innerRoot in its center.
	 */
	public BorderPane outerRoot = new BorderPane();
	/**
	 * Parent for the <code>LevelBar</code>, the MegaFit logo and the logout button
	 */
	public GridPane topScreenGrid = new GridPane();
	/**
	 * Container for the level bar and associated level and XP labels
	 */
	public HBox levBar = new HBox();
	/**
	 * Container for the row of tab-like buttons that allow inter-screen navigation
	 */
	private HBox mainMenuButtons = new HBox();

	/**
	 * The root node for all of the graphical elements in the app.
	 */
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
	// Screen IDs and resource paths

	public static String workoutLibraryID 	= "workoutPage";
	public static String workoutPageFile 	= "workoutOverview.fxml";
	public static String characterDashID 	= "chardash";
	public static String characterDashFile  = "characterDashBoard.fxml";

	// Screen IDs for nodes built with Java compiler, used as index for Hashmap<String, Node>
	public static String createWorkoutID 	= "createWorkout";
	public static String dietMenuID 		= "dietMenu";
	public static String dietPlannerID		= "dietPlanner";
	public static String loginID 			= "login";
	public static String menuID 			= "menu";
	public static String shopMenuID			= "shopMenu";
	public static String signUpID 			= "signUp";
	public static String socialMenuID		= "socialMenu";
	public static String endCardID			= "endCard";

	protected Stage primaryStage;

	//================================================================================
	// Start
	//================================================================================

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
		outerRoot.setTop(topScreenGrid);

		innerRoot.setCenter(controllableCenterScreen);

		outerRoot.setCenter(innerRoot);

		stackPaneRoot.getChildren().add(0, outerRoot);

		Scene scene = new Scene(stackPaneRoot,screenWidth,screenHeight);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

		primaryStage.setScene(scene);
		primaryStage.setFullScreen(true);
		primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
		primaryStage.show();

	}

	//================================================================================
	// Members
	//================================================================================

	/**
	 * Builds the required nodes for user login/signup and loads these nodes into the
	 * controllableCenterScreen.
	 * @see StackPaneUpdater
	 */
	private void loadLoginAndSignUp() {

		LoginMenu loginInstance = new LoginMenu(screenWidth, screenHeight);
		controllableCenterScreen.loadJavaWrittenScreen(Main.loginID, loginInstance);

		SignUpMenu signUpMenuInstance = new SignUpMenu(screenWidth, screenHeight);
		controllableCenterScreen.loadJavaWrittenScreen(Main.signUpID, signUpMenuInstance);
	}

	/**
	 * Initialises the client server communications. If the server is not detected then the
	 * application enters an offline mode that allows the user to use the app without the social
	 * social features.
	 * @author Oliver Rushton
	 *
	 * @see ClientSide
	 */
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

	/**
	 * Orders the top border elements within a {@link #topScreenGrid}
	 */
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

		topScreenGrid = new GridPane();
		topScreenGrid.minWidthProperty().bind(outerRoot.widthProperty().subtract(10));

		ColumnConstraints genericColumn = new ColumnConstraints();
		ColumnConstraints fillerColumn = new ColumnConstraints();

		fillerColumn.setHgrow(Priority.ALWAYS);
		genericColumn.maxWidthProperty().bind(outerRoot.widthProperty().multiply(0.3));
		genericColumn.minWidthProperty().bind(outerRoot.widthProperty().multiply(0.3));
		topScreenGrid.getColumnConstraints().add(genericColumn);
		topScreenGrid.getColumnConstraints().add(fillerColumn);
		topScreenGrid.getColumnConstraints().add(genericColumn);
		topScreenGrid.getColumnConstraints().add(fillerColumn);
		topScreenGrid.getColumnConstraints().add(genericColumn);

		topScreenGrid.setId("image-box");
		LevelBar tempLevelBar = new LevelBar(screenWidth*0.3,screenHeight*0.05);
		tempLevelBar.setVisible(false);


		Label filler1 = new Label("   ");
		Label filler2 = new Label("   ");

		topScreenGrid.add(filler1, 1, 0);
		topScreenGrid.add(prodLogoView, 2, 0);
		topScreenGrid.add(filler2, 3, 0);
		topScreenGrid.add(exit, 4, 0);
		levBar.getChildren().add(tempLevelBar);
		topScreenGrid.add(levBar, 0, 0);
		
		exit.setAlignment(Pos.CENTER_RIGHT);

		GridPane.setHalignment(levBar, HPos.LEFT);
		GridPane.setHalignment(exit, HPos.RIGHT);
		GridPane.setHalignment(exit, HPos.CENTER);

		exit.setOnAction (new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				//possible alert here to check if you really want to logout.
				ExceptionFx except = new ExceptionFx(AlertType.CONFIRMATION, "Logout and quit the application?",
						 "Are you sure you want to exit the application.",
						 "", primaryStage);
				if (except.showAndWait()) {
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

			}

		});
	}

	/**
	 * Builds a row of buttons that allows the user to switch between the in-app screens. Handles the user
	 * clicks for the buttons.
	 * @param screenWidth
	 * @param screenHeight
     * @return <code>HBox</code> menu option buttons
     */
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


		Button buttonDiet = new Button("DIET");
		buttonDiet.setPrefSize(screenWidth*0.25, screenHeight*0.05);


		buttonDiet.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent event) {
				controllableCenterScreen.setScreen(Main.dietPlannerID);
			}
		});


		Button buttonCharacter = new Button("CHARACTER");
		buttonCharacter.setPrefSize(screenWidth*0.25, screenHeight*0.05);

		buttonCharacter.setOnAction(new EventHandler<ActionEvent>(){

			public void handle (ActionEvent event) {
				controllableCenterScreen.setScreen(Main.characterDashID);
			}
		});

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


		hBox.getChildren().addAll(buttonWorkouts, buttonDiet, buttonCharacter, buttonSocial);
		hBox.setSpacing(screenWidth*0.001);

		return hBox;
	}


	/**
	 * Adds the <code>mainMenuButtons</code> if the user has entered the app.
	 * @see StackPaneUpdater
	 * @param screenID
     */
	public void setUpdatedScreenID(final String screenID) {
		// TODO test if the mainMenuButtons is already in Top to avoid adding them
		if ((screenID != loginID) && (screenID != signUpID))
		{
			innerRoot.setTop(mainMenuButtons);
		}
		else
		{
			if (innerRoot.getTop() != null) {
				innerRoot.setTop(null);
			}
		}
	}

	/**
	 * Creates a new Level bar with that is updated with the user's XP progress
	 * @param startXP
	 * @param currentXP
	 * @param endXP
	 * @param currentLevel
     */
	public void setLevelBar(int startXP, int currentXP, int endXP, int currentLevel) {
		LevelBar levelBar;
		if (currentLevel!=1){
			levelBar = new LevelBar(screenWidth*0.3,
			     						 screenHeight*0.05,
			     						 startXP,
			     						 currentXP,
			     						 endXP,
			     						 currentLevel);
			}else {
				levelBar = new LevelBar(screenWidth*0.3,
						 screenHeight*0.05,
						 0,
						 currentXP,
						 endXP,
						 1);
			}
		levBar.setAlignment(Pos.CENTER_LEFT);
		levBar.getChildren().clear();
		levBar.getChildren().add(levelBar);
	}

	/**
	 * Starts a new workout view and displays it as a full screen presentation
	 * Removes the <code>outerRoot</code> from the <code>stackPaneRoot</code> and replaces it with
	 * the newly created <code>WorkoutView</code>. Also gives a reference to <code>this</code> so that
	 * the user can be taken back to the application once the workout is completed.
	 * @see WorkoutView the class that enables the user to view the presentation and workout
	 * @see presentationViewer.PresentationFx the class that
	 * @param filename
     */
	public void launchPresentation(String filename) {
		WorkoutView workoutView = new WorkoutView(this.screenWidth, (this.screenHeight), filename, this);
		workoutView.setMainApp(this);
		workoutView.setScreenParent(controllableCenterScreen);
		stackPaneRoot.getChildren().remove(outerRoot);
		stackPaneRoot.getChildren().add(0, workoutView);
	}

	/**
	 * Called at the end of a presentation to swap from the full screen presentation to the <code>outerRoot</code>
	 * that displays the application screens.
	 */
	public void returnToAppScreens() {
		stackPaneRoot.getChildren().remove(0);
		stackPaneRoot.getChildren().add(0, outerRoot);
	}

	/**
	 * launches the application
	 * @param args
     */
	public static void main(String[] args) {
		launch(args);
	}
}

