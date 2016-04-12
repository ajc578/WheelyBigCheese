/**
 * Class containing text fields accepting user's username and password at the stage
 * of logging in
 * @company - B.O.S.S
 * @author - Kamil Sledziewski
 * 
 * last updated: 10/04/2016
 */

package userInterface;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;


public class LoginMenu extends VBox {

	
	public LoginMenu (double screenWidth, double screenHeight, BorderPane root) {
		
		putImage(screenWidth, screenHeight);
		addComponents(screenWidth, screenHeight, root);
	}
	
	public void putImage(double screenWidth, double screenHeight) {
		HBox imageBox = new HBox();
		imageBox.setAlignment(Pos.TOP_CENTER);
		imageBox.setId("image-box");
		
		Image prodLogo = new Image("res/images/product_logo.jpg");
		ImageView prodLogoView = new ImageView(prodLogo);
		prodLogoView.setImage(prodLogo);
		prodLogoView.setFitWidth(screenWidth*0.4);
		prodLogoView.setFitHeight(screenHeight*0.125);
		
		imageBox.setAlignment(Pos.TOP_CENTER);
		imageBox.getChildren().addAll(prodLogoView);
		getChildren().addAll(imageBox);
		
	}
	
	public void addComponents(double screenWidth, double screenHeight, BorderPane root) {
		
		VBox allComponentsVBox = new VBox();
		allComponentsVBox.setId("allComponentsBox");
		
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
		
		signUpButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				SignUpMenu signUpMenu = new SignUpMenu(screenWidth, screenHeight, root);
				try {
					root.setTop(signUpMenu);
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		Button loginButton = new Button("LOGIN");
		
		// event handler for the login button
		loginButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				Menu menu = new Menu(screenWidth, screenHeight, root);
				try {
					root.setTop(menu);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		
		});
		
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
}