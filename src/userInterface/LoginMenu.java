/**
 * Class containing text fields accepting user's username and password at the stage
 * of logging in
 * @company - B.O.S.S
 * @author - Kamil Sledziewski
 * 
 * last updated: 10/04/2016
 */

package userInterface;

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
		
		
		VBox allComponentsVBox = new VBox();
		allComponentsVBox.setId("allComponentsBox");
		
		Label variableLabel = new Label("Please Enter a Name And Password");
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

		// TODO signUp
		signUpButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
			screenParent.setScreen(Main.signUpID);
			}
		});
		
		setNodeCursor(signUpButton);
		
		Button loginButton = new Button("LOGIN");


		// TODO login confirm credentials are correct, if not propose sign up
		// event handler for the login button
		loginButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				
				if (userTextField.getText().equals("") || passwordField.getText().equals("")) {
					try {
						loadDefaultLogin(userTextField, passwordField);
					} catch (NullPointerException npe) {}
					
				}
				
				if (Main.serverDetected && Main.client.isAccessible()) {
					try {
						String username = userTextField.getText();
						String password = passwordField.getText();
						if (username != null && !username.equals("")) {
							Main.client.login(username, password);
							while (true) {
								String output = Main.client.receive();
								if (output.equals(Protocol.SUCCESS)) {
									Main.account = Main.client.getAccount();
									setActiveAccount();

									// load screens that need the account set
									screenParent.loadDietPlanner();
									screenParent.loadWorkoutLibrary();

									screenParent.setScreen(Main.characterMenuID);
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
						Main.account = accHandler.getAccount();
						setActiveAccount();

						// load screens that need the account set
						screenParent.loadDietPlanner();
						screenParent.loadWorkoutLibrary();
						screenParent.setScreen(Main.characterMenuID);
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
