package userInterface;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import javafx.event.ActionEvent;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
//import serialisedClasses.SignUpMenuSerialised;

public class SignUpMenu extends VBox implements java.io.Serializable{
	
	transient double heightCoeff = 20;
	transient double widthCoeff = 10;
	
	transient Label fullNameLabel = new Label("FULL NAME");
	transient Label userNameLabel = new Label("USERNAME");
	transient Label heightLabel = new Label("HEIGHT");
	transient Label passwordLabel = new Label("PASSWORD");
	transient Label emailLabel = new Label("EMAIL");
	transient Label dateOfBirthLabel = new Label("DATE OF BIRTH");
	transient Label weightLabel = new Label("WEIGHT");
	transient Label confirmPasswordLabel = new Label("CONFIRM PASSWORD");
	
	transient HBox fullNameLabelBox = new HBox();
	transient HBox userNameLabelBox = new HBox();
	transient HBox heightLabelBox = new HBox();
	transient HBox passwordLabelBox = new HBox();
	transient HBox emailLabelBox = new HBox();
	transient HBox dateOfBirthLabelBox = new HBox();
	transient HBox weightLabelBox = new HBox();
	transient HBox confirmPasswordLabelBox = new HBox();
	
	transient static TextField fullNameField = new TextField();
	transient static TextField userNameField = new TextField();
	transient static TextField heightField = new TextField();
	transient static TextField passwordField = new TextField();
	transient static TextField emailField = new TextField();
	transient static TextField dateOfBirthField = new TextField();
	transient static TextField weightField = new TextField();
	transient static TextField confirmPasswordField = new TextField();
	
	transient Button doneButton = new Button("DONE");
	
	transient HBox fullNameFieldBox = new HBox();
	transient HBox userNameFieldBox = new HBox();
	transient HBox heightFieldBox = new HBox();
	transient HBox passwordFieldBox = new HBox();
	transient HBox emailFieldBox = new HBox();
	transient HBox dateOfBirthFieldBox = new HBox();
	transient HBox weightFieldBox = new HBox();
	transient HBox confirmPasswordFieldBox = new HBox();
	
	transient VBox leftLabelsBox = new VBox();
	transient VBox leftFieldsBox = new VBox();
	transient VBox rightLabelsBox = new VBox();
	transient VBox rightFieldsBox = new VBox();
	
	transient HBox leftDataBox = new HBox();
	transient HBox rightDataBox = new HBox();
	
	transient HBox labelsFieldsBox = new HBox();
	transient HBox buttonBox = new HBox();
	
	public int i = 1;
	
	public SignUpMenu() {
		
	}
	
	public SignUpMenu (double screenWidth, double screenHeight, BorderPane root) {
		
		fullNameLabelBox.getChildren().addAll(fullNameLabel);
		fullNameLabelBox.setPrefSize(screenWidth/widthCoeff, screenHeight/heightCoeff);
		
		userNameLabelBox.getChildren().addAll(userNameLabel);
		userNameLabelBox.setPrefSize(screenWidth/widthCoeff, screenHeight/heightCoeff);
		
		heightLabelBox.getChildren().addAll(heightLabel);
		heightLabelBox.setPrefSize(screenWidth/widthCoeff, screenHeight/heightCoeff);
		
		passwordLabelBox.getChildren().addAll(passwordLabel);
		passwordLabelBox.setPrefSize(screenWidth/widthCoeff, screenHeight/heightCoeff);
		
		leftLabelsBox.getChildren().addAll(fullNameLabelBox, userNameLabelBox, heightLabelBox, passwordLabelBox);
		
		
		fullNameFieldBox.getChildren().addAll(fullNameField);
		fullNameFieldBox.setPrefSize(screenWidth/widthCoeff, screenHeight/heightCoeff);
		
		userNameFieldBox.getChildren().addAll(userNameField);
		userNameFieldBox.setPrefSize(screenWidth/widthCoeff, screenHeight/heightCoeff);
		
		heightFieldBox.getChildren().addAll(heightField);
		heightFieldBox.setPrefSize(screenWidth/widthCoeff, screenHeight/heightCoeff);
		
		passwordFieldBox.getChildren().addAll(passwordField);
		passwordFieldBox.setPrefSize(screenWidth/widthCoeff, screenHeight/heightCoeff);
		
		leftFieldsBox.getChildren().addAll(fullNameField, userNameField, heightField, passwordField);
		
		leftDataBox.getChildren().addAll(leftLabelsBox, leftFieldsBox);
		
		emailLabelBox.getChildren().addAll(emailLabel);
		emailLabelBox.setPrefSize(screenWidth/widthCoeff, screenHeight/heightCoeff);
		
		dateOfBirthLabelBox.getChildren().addAll(dateOfBirthLabel);
		dateOfBirthLabelBox.setPrefSize(screenWidth/widthCoeff, screenHeight/heightCoeff);
		
		weightLabelBox.getChildren().addAll(weightLabel);
		weightLabelBox.setPrefSize(screenWidth/widthCoeff, screenHeight/heightCoeff);
		
		confirmPasswordLabelBox.getChildren().addAll(confirmPasswordLabel);
		confirmPasswordLabelBox.setPrefSize(screenWidth/widthCoeff, screenHeight/heightCoeff);
		
		rightLabelsBox.getChildren().addAll(emailLabel, dateOfBirthLabel, weightLabel, confirmPasswordLabel);
		
		
		emailFieldBox.getChildren().addAll(emailField);
		emailFieldBox.setPrefSize(screenWidth/widthCoeff, screenHeight/heightCoeff);
		
		dateOfBirthFieldBox.getChildren().addAll(dateOfBirthField);
		dateOfBirthFieldBox.setPrefSize(screenWidth/widthCoeff, screenHeight/heightCoeff);
		
		weightFieldBox.getChildren().addAll(weightField);
		weightFieldBox.setPrefSize(screenWidth/widthCoeff, screenHeight/heightCoeff);
		
		confirmPasswordFieldBox.getChildren().addAll(confirmPasswordField);
		confirmPasswordField.setPrefSize(screenWidth/widthCoeff, screenHeight/heightCoeff);
		
		rightFieldsBox.getChildren().addAll(emailFieldBox, dateOfBirthFieldBox, weightFieldBox, confirmPasswordFieldBox);
		
		rightDataBox.getChildren().addAll(rightLabelsBox, rightFieldsBox);
		
		labelsFieldsBox.getChildren().addAll(leftDataBox, rightDataBox);
		labelsFieldsBox.setSpacing(screenWidth/6);
		labelsFieldsBox.setPadding(new Insets(0, screenWidth/5, 0, screenWidth/5));
		
		buttonBox.getChildren().addAll(doneButton);
		doneButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				// WorkoutMenu workoutMenu = new WorkoutMenu(screenWidth, screenHeight, root);
				UserProfileDataToXML userXML1 = new UserProfileDataToXML();
				//SignUpMenuSerialised serialised = new SignUpMenuSerialised();
				try{
					if (i < 6) {
						userXML1.returnData(new File("user" + i + ".xml"));
						i++;
						//serialised.serialise(i);
						System.out.println(i);
					}
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
		});
		buttonBox.setPadding(new Insets(0, screenWidth/2.2, 0, screenWidth/2.2));
		
		putImage(screenWidth, screenHeight);
		getChildren().addAll(labelsFieldsBox, buttonBox);
		//setSpacing(20);
	}
	
	public void putImage(double screenWidth, double screenHeight) {
		HBox imageBox = new HBox();
		imageBox.setAlignment(Pos.TOP_CENTER);
		imageBox.setId("image-box");
		
		Image prodLogo = new Image("product_logo.jpg");
		ImageView prodLogoView = new ImageView(prodLogo);
		prodLogoView.setImage(prodLogo);
		prodLogoView.setFitWidth(screenWidth*0.4);
		prodLogoView.setFitHeight(screenHeight*0.125);
		
		imageBox.setAlignment(Pos.TOP_CENTER);
		imageBox.getChildren().addAll(prodLogoView);
		getChildren().addAll(imageBox);
		
	}

	@XmlRootElement
	public static class UserProfileData {
		
		private String fullName;
		private String userName;
		private String height;
		private String password;
		private String email;
		private String dateOfBirth;
		private String weight;
		private String confirmedPassword;
		
		public String getFullName() {
			return fullName;
		}
		public void setFullName(String fullName) {
			this.fullName = fullName;
		}
		
		public String getUserName() {
			return userName;
		}
		public void setUserName(String userName) {
			this.userName = userName;
		}
		
		public String getHeight() {
			return height;
		}
		public void setHeight(String height) {
			this.height = height;
		}
		
		public String getPassword() {
			return password;
		}
		public void setPassword(String password) {
			this.password = password;
		}
		
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		
		public String getDateOfBirth() {
			return dateOfBirth;
		}
		public void setDateOfBirth(String dateOfBirth) {
			this.dateOfBirth = dateOfBirth;
		}
		
		public String getWeight() {
			return weight;
		}
		public void setWeight(String weight) {
			this.weight = weight;
		}
		
		public String getConfirmedPassword() {
			return confirmedPassword;
		}
		public void setConfirmedPassword(String confirmedPassword) {
			this.confirmedPassword = confirmedPassword;
		}
		
		/*UserProfileData() {
			fullName = fullNameField.getText();
			userName = userNameField.getText();
			height = heightField.getText();
			password = passwordField.getText();
			email = emailField.getText();
			dateOfBirth = 
		}*/
	}
	
	public static class UserProfileDataToXML {

		public void returnData(File userFile) {
				
			UserProfileData user = new UserProfileData();
					
			try {
				File sourceFile = new File("src/xmlFiles/" + userFile);
				JAXBContext jaxbContext = JAXBContext.newInstance(UserProfileData.class);
						
				Marshaller m = jaxbContext.createMarshaller();
				m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
						
				user.setFullName(fullNameField.getText());
				user.setUserName(userNameField.getText());
				user.setHeight(heightField.getText());
				user.setPassword(passwordField.getText());
				user.setEmail(emailField.getText());
				user.setWeight(weightField.getText());
				user.setDateOfBirth(dateOfBirthField.getText());
				user.setConfirmedPassword(confirmPasswordField.getText());
						
				m.marshal(user, sourceFile);
							
			} catch (JAXBException e) {
				e.printStackTrace();
			}
		}
	}
}