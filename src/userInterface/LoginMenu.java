/**
 * Class containing text fields accepting user's username and password at the stage
 * of logging in
 * @company - B.O.S.S
 * @author - Kamil Sledziewski
 * 
 * last updated: 10/04/2016
 */

package userInterface;

/**
 * LoginMenu creates a login screen which allows the user to enter their 
 * username and password to appropriate text fields
 * <p>
 * The class extends the VBox, as this is the most outer layout manager into which 
 * within which all the components in this class are placed and laid out
 * 
 * @author Kamil Sledziewski (GUI)
 * @author Oliver Rushton (Integration with the Account module)
 *
 */

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.xml.bind.JAXBException;

import account.Account;
import account.AccountHandler;
import account.LoginStatus;
import account.Protocol;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;



public class LoginMenu extends VBox implements Controllable {

	private static final String activeAccountPath = "src/res/clientAccounts/activeAccount.txt";
	private static final String clientDir = "src/res/clientAccounts/";
		Button exit;
		Image exitApp;
	private StackPaneUpdater screenParent;
	private Main mainApp;
	private Account account = null;
	
	private double screenWidth, screenHeight;

	/**
	 * Constructs a LoginMenu
	 * @param screenWidth parameter that adjusts the majority of the horizontal spacings and padding constraints according to the width of machine's screen
	 * @param screenHeight parameter that adjusts the majority of the vertical spacings and padding constraints according to the height of machine's screen
	 * <p>
	 * The constructor lays out all the nodes and encloses them appropriately within corresponding boxes
	 * <p>
	 * userNameBox - a VBox that encloses the "username" label and corresponding text field
	 * <p>
	 * passwordBox - a VBox that encloses the "password" label and corresponding text field
	 * <p>
	 * userDataBox - a VBox that encloses the two aforementioned boxes to distinguish 
	 * them from the two buttons at the bottom of the screen, as different positioning measueres 
	 * are applied to these components
	 * <p> 
	 * buttonBox - an HBox that encloses the "LOGIN" and "SIGN UP" buttons
	 * <p>
	 * The "LOGIN" button navigates the user to the main character menu if username and password 
	 * match their data
	 * <p> 
	 * The "SIGN UP" button navigates the user to the sign-up menu
	 * <p> 
	 * allComponentsVBox - a VBox that encloses the sub-containers(userDataBox, buttonBox)
	 * with appropriate padding applied in order to place it in the middle of the screen
	 */
	
	public LoginMenu (double screenWidth, double screenHeight) {
			
//		exitApp = new Image("res/images/download.jpg");
//		ImageView quitApp = new ImageView(exitApp);
//		quitApp.setFitHeight(screenHeight*0.1);
//		quitApp.setFitWidth(screenWidth*0.1);
//		exit = new Button("", quitApp);
		
//		HBox imageBox = new HBox();
//		imageBox.setAlignment(Pos.TOP_CENTER);
//		imageBox.setId("image-box");
		
//		exit.setOnAction (new EventHandler<ActionEvent>() {

//			@Override
//			public void handle(ActionEvent event) {
//				try{
					
//				} catch (Exception e){
//					e.printStackTrace();
//				}
			
//			}
		
//		});
		
//		Image prodLogo = new Image("res/images/product_logo.jpg");
//		ImageView prodLogoView = new ImageView(prodLogo);
//		prodLogoView.setImage(prodLogo);
//		prodLogoView.setFitWidth(screenWidth*0.4);
//		prodLogoView.setFitHeight(screenHeight*0.125);
		
//		imageBox.setAlignment(Pos.TOP_CENTER);
//		imageBox.getChildren().addAll(prodLogoView);
//		getChildren().addAll(imageBox);
		
		
		this.screenWidth = screenWidth;
		this.screenHeight= screenHeight;
		
		VBox allComponentsVBox = new VBox();
		allComponentsVBox.setId("allComponentsBox");
		
		Label variableLabel = new Label("Please Enter a Name And Password");
		variableLabel.getStyleClass().add("smallerText");
		allComponentsVBox.getChildren().add(variableLabel);
		
		VBox userNameBox = new VBox();
		VBox passwordBox = new VBox();
		VBox userDataBox = new VBox();
		
		Label userName = new Label("USERNAME");
		TextField userTextField = new TextField();
		Label password = new Label("PASSWORD");
		PasswordField passwordField = new PasswordField();
		
		userNameBox.setAlignment(Pos.CENTER);
		userNameBox.getChildren().addAll(userName, userTextField);
		userNameBox.setSpacing(10);
		
		passwordBox.setAlignment(Pos.CENTER);
		passwordBox.getChildren().addAll(password, passwordField);
		passwordBox.setSpacing(10);
		
		userDataBox.getChildren().addAll(userNameBox, passwordBox);
		userDataBox.setSpacing(screenHeight/10);
		
		HBox buttonBox = new HBox();
		buttonBox.setId("buttonBox");
		
		Button signUpButton = new Button("SIGN UP");
		signUpButton.getStyleClass().add("loginButtons");

		// TODO signUp
		signUpButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
			screenParent.setScreen(Main.signUpID);
			}
		});
		
		setNodeCursor(signUpButton);
		
		Button loginButton = new Button("LOGIN");
		loginButton.getStyleClass().add("loginButtons");

		// TODO login confirm credentials are correct, if not propose sign up
		// event handler for the login button
		loginButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				
				/*if (userTextField.getText().equals("") || passwordField.getText().equals("")) {
					try {
						loadDefaultLogin(userTextField, passwordField);
					} catch (NullPointerException npe) {}
					
				}*/
				
				if (Main.serverDetected && Main.client.isAccessible()) {
					try {
						String username = userTextField.getText();
						String password = passwordField.getText();
						if (username != null && !username.equals("")) {
							Main.client.login(username, password);
							while (true) {
								String output = Main.client.receive();
								if (output.equals(Protocol.SUCCESS)) {
									Main.loginStatus = true;
									Main.account = Main.client.getAccount();
									setActiveAccount();
									try {
										Thread.sleep(200);
									} catch (InterruptedException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									// load screens that need the account set
									loadJavaScreens();
									screenParent.loadDietPlanner();
									screenParent.loadWorkoutLibrary();
									screenParent.loadCharacterDashboard();

									screenParent.setScreen(Main.characterDashID);
									break;
								} else if (output.startsWith(Protocol.ERROR)) {
									clearActiveAccount();
									System.out.println("Error returned in client main: " + output);
									variableLabel.setText("Incorrect Name or Password!");
									break;
								}
							}
						} else {
							variableLabel.setText("Empty Text Fields! Please enter your username and passwords.");
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					String username = userTextField.getText();
					String password = passwordField.getText();
					AccountHandler accHandler = new AccountHandler();
					if (accHandler.login(clientDir, Protocol.LOGIN + " : " + 
									username + "," + password).equals(LoginStatus.LOGGED_IN)) {
						Main.loginStatus = true;
						Main.account = accHandler.getAccount();
						setActiveAccount();
						// load screens that need the account set
						loadJavaScreens();
						screenParent.loadDietPlanner();
						screenParent.loadWorkoutLibrary();
						screenParent.loadCharacterDashboard();

						screenParent.setScreen(Main.characterDashID);
					} else {
						clearActiveAccount();
						variableLabel.setText("Incorrect Name or Password!");
					}
				}
			}
		
		});
		
		setNodeCursor(loginButton);
		
		buttonBox.setAlignment(Pos.BOTTOM_CENTER);
		buttonBox.setPadding(new Insets(10, 50, 10, 50));
		buttonBox.getChildren().addAll(signUpButton, loginButton);
		buttonBox.setSpacing(screenWidth/15);
		
		allComponentsVBox.setAlignment(Pos.CENTER);
		allComponentsVBox.getChildren().addAll(userDataBox, buttonBox);
		allComponentsVBox.setSpacing(screenHeight/15);
		allComponentsVBox.setPadding(new Insets(0.2*screenHeight, 0.35*screenWidth, 0.2*screenHeight, 0.35*screenWidth));
		getChildren().addAll(allComponentsVBox);
		
	}
	
	private void loadJavaScreens() {
		// TODO  do loading with for loop
		Menu menuInstance = new Menu(screenWidth, screenHeight);
		screenParent.loadJavaWrittenScreen(Main.menuID, menuInstance);

		DietMenu dietMenuInstance = new DietMenu(screenWidth, screenHeight);
		screenParent.loadJavaWrittenScreen(Main.dietMenuID, dietMenuInstance);

		CreateWorkout createWorkoutInstance = new CreateWorkout(screenWidth, screenHeight);
		screenParent.loadJavaWrittenScreen(Main.createWorkoutID, createWorkoutInstance);

		ShopMenu shopMenuInstance = new ShopMenu(screenWidth, screenHeight);
		screenParent.loadJavaWrittenScreen(Main.shopMenuID, shopMenuInstance);

		SocialMenu socialMenuInstance = new SocialMenu(screenWidth, screenHeight);
		screenParent.loadJavaWrittenScreen(Main.socialMenuID, socialMenuInstance);
		int xpBarLower = levelCurve(Main.account.getLevel());
		if (xpBarLower<0)xpBarLower=0;
		int xpBarHigher = levelCurve(Main.account.getLevel()+1);
		
		mainApp.setLevelBar(xpBarLower, Main.account.getXp(), xpBarHigher, Main.account.getLevel());

//		WorkoutEndCard workoutEndCardInstance = new WorkoutEndCard(screenWidth, screenHeight, completedExercises);
//		controllableCenterScreen.loadJavaWrittenScreen(workoutEndCardID, workoutEndCardInstance);


	}

	private int levelCurve(int n){
		int levelBoundary;

		levelBoundary = Math.round(Math.round((Math.exp(n/7)*150) + ((n-1)*75)-174));

		return levelBoundary;
	}
	
	private void setActiveAccount() {
		File activeAccount = new File(activeAccountPath);
		if (activeAccount.exists() && activeAccount.isFile()) {
			try (
				BufferedWriter	writer= new BufferedWriter(new FileWriter(activeAccount));
			) {
				writer.write(Main.account.getUsername());
			} catch (IOException ioe) {
				//TODO handle exception
				ioe.printStackTrace();
			}
		} else {
			System.out.println("Active Account text file does not exist. (LoginMenu)");
		}
	}
	
	private void clearActiveAccount() {
		File activeAccount = new File(activeAccountPath);
		if (activeAccount.exists() && activeAccount.isFile()) {
			try (
				BufferedWriter	writer= new BufferedWriter(new FileWriter(activeAccount));
			) {
				writer.write("");
			} catch (IOException ioe) {
				//TODO handle exception
				ioe.printStackTrace();
			}
		} else {
			System.out.println("Active Account text file does not exist. (LoginMenu)");
		}
	}
	
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
	
	private void loadDefaultLogin(TextField userTextField, PasswordField passwordField) {
		Account account = new Account();
		try {
			account = AccountHandler.accountLoad(clientDir, AccountHandler.getActiveAccount());
			String userName = account.getUsername();
			String password = account.getPassword();
			userTextField.setText(userName);
			passwordField.setText(password);
		} catch (JAXBException e) {}
	}
}
