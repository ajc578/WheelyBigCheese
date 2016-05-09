/**
 * SignUpMenu Class
 * @author - Kamil Sledziewski
 * @author - Jonathan Sullivan
 * @author - Henry Nash
 * @author - Sebastien Corrigan
 */

package userInterface;

import java.io.File;


import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import javafx.event.ActionEvent;


import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.Window;

public class SignUpMenu extends VBox {
	
	double heightCoeff = 20;
	double widthCoeff = 10;
	
	Label firstNameLabel = new Label("FIRST NAME");
	Label lastNameLabel = new Label ("LAST NAME");
	Label userNameLabel = new Label("USERNAME");
	Label heightLabel = new Label("HEIGHT");
	Label passwordLabel = new Label("PASSWORD");
	Label emailLabel = new Label("EMAIL");
	Label dateOfBirthLabel = new Label("DATE OF BIRTH");
	Label weightLabel = new Label("WEIGHT");
	Label confirmPasswordLabel = new Label("CONFIRM PASSWORD");
	
	HBox firstNameLabelBox = new HBox();
	HBox lastNameLabelBox = new HBox();
	HBox userNameLabelBox = new HBox();
	HBox heightLabelBox = new HBox();
	HBox passwordLabelBox = new HBox();
	HBox emailLabelBox = new HBox();
	HBox dateOfBirthLabelBox = new HBox();
	HBox weightLabelBox = new HBox();
	HBox confirmPasswordLabelBox = new HBox();
	
	public static TextField firstNameField = new TextField();
	public static TextField lastNameField = new TextField();
	public static TextField userNameField = new TextField();
	public static TextField heightField = new TextField();
	public static TextField passwordField = new TextField();
	public static TextField emailField = new TextField();
	public static DatePicker dOBPicker = new DatePicker();
	public static TextField weightField = new TextField();
	public static TextField confirmPasswordField = new TextField();
	
	Button doneButton = new Button("DONE");
	
	HBox firstNameFieldBox = new HBox();
	HBox lastNameFieldBox = new HBox();
	HBox userNameFieldBox = new HBox();
	HBox heightFieldBox = new HBox();
	HBox passwordFieldBox = new HBox();
	HBox emailFieldBox = new HBox();
	HBox dateOfBirthFieldBox = new HBox();
	HBox weightFieldBox = new HBox();
	HBox confirmPasswordFieldBox = new HBox();
	
	VBox leftLabelsBox = new VBox();
	VBox leftFieldsBox = new VBox();
	VBox rightLabelsBox = new VBox();
	VBox rightFieldsBox = new VBox();
	
	HBox leftDataBox = new HBox();
	HBox rightDataBox = new HBox();
	
	HBox labelsFieldsBox = new HBox();
	HBox buttonBox = new HBox();
	
	public int i = 1;
	
	public SignUpMenu() {
		
	}
	
	public SignUpMenu (double screenWidth, double screenHeight, BorderPane root) {
		
		firstNameLabelBox.getChildren().addAll(firstNameLabel);
		firstNameLabelBox.setPrefSize(screenWidth/widthCoeff, screenHeight/heightCoeff);
		
		lastNameLabelBox.getChildren().addAll(lastNameLabel);
		lastNameLabelBox.setPrefSize(screenWidth/widthCoeff, screenHeight/heightCoeff);
		
		userNameLabelBox.getChildren().addAll(userNameLabel);
		userNameLabelBox.setPrefSize(screenWidth/widthCoeff, screenHeight/heightCoeff);
		
		heightLabelBox.getChildren().addAll(heightLabel);
		heightLabelBox.setPrefSize(screenWidth/widthCoeff, screenHeight/heightCoeff);
		
		passwordLabelBox.getChildren().addAll(passwordLabel);
		passwordLabelBox.setPrefSize(screenWidth/widthCoeff, screenHeight/heightCoeff);
		
		leftLabelsBox.getChildren().addAll(firstNameLabelBox, lastNameLabelBox, userNameLabelBox, heightLabelBox, passwordLabelBox);
		
		
		firstNameFieldBox.getChildren().addAll(firstNameField);
		firstNameFieldBox.setPrefSize(screenWidth/widthCoeff, screenHeight/heightCoeff);
		
		lastNameFieldBox.getChildren().addAll(lastNameField);
		lastNameFieldBox.setPrefSize(screenWidth/widthCoeff, screenHeight/heightCoeff);
		
		userNameFieldBox.getChildren().addAll(userNameField);
		userNameFieldBox.setPrefSize(screenWidth/widthCoeff, screenHeight/heightCoeff);
		
		heightFieldBox.getChildren().addAll(heightField);
		heightFieldBox.setPrefSize(screenWidth/widthCoeff, screenHeight/heightCoeff);
		
		passwordFieldBox.getChildren().addAll(passwordField);
		passwordFieldBox.setPrefSize(screenWidth/widthCoeff, screenHeight/heightCoeff);
		
		leftFieldsBox.getChildren().addAll(firstNameField, lastNameField, userNameField, heightField, passwordField);
		
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
		
		dateOfBirthFieldBox.getChildren().addAll(dOBPicker);
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
				CreateCharacter createChar = new CreateCharacter();
				//UserProfileDataToXML userXML1 = new UserProfileDataToXML();
				//SignUpMenuSerialised serialised = new SignUpMenuSerialised();
				try{
					//if (i < 6) {
						//userXML1.returnData(new File("user" + i + ".xml"));
						//System.out.println(i);
						//i++;
						//serialised.serialise(i);
						/* Methods that look for erroneous inputs */ 
						/*if (erroneusInputCheck()) 
							root.setTop(createChar);*/
					
						erroneusInputCheck();
						
					//}
				} catch (Exception e){
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
		
		Image prodLogo = new Image("res/images/product_logo.jpg");
		ImageView prodLogoView = new ImageView(prodLogo);
		prodLogoView.setImage(prodLogo);
		prodLogoView.setFitWidth(screenWidth*0.4);
		prodLogoView.setFitHeight(screenHeight*0.125);
		
		imageBox.setAlignment(Pos.TOP_CENTER);
		imageBox.getChildren().addAll(prodLogoView);
		getChildren().addAll(imageBox);
		
	}
	/*KS*/
	public static boolean erroneusInputCheck() {
		String errorMessage = "";
		if((firstNameField.getText() == null || firstNameField.getText().length() == 0) ||
			(lastNameField.getText() == null || lastNameField.getText().length() == 0) ||
			(userNameField.getText() == null || userNameField.getText().length() == 0) ||
			(heightField.getText() == null || heightField.getText().length() == 0) ||
			(passwordField.getText() == null || passwordField.getText().length() == 0) ||
			(emailField.getText() == null || emailField.getText().length() == 0) ||
			(weightField.getText() == null || weightField.getText().length() == 0) ||
			(confirmPasswordField.getText() == null || confirmPasswordField.getText().length() == 0))
				errorMessage += "Fill in all the fields!\n";
		
		if (!invalidFirstNameCheck()) {
			errorMessage += "Please provide valid first name!\n";
		}
		
		if (!invalidLastNameCheck()) {
			errorMessage += "Please provide valid last name!\n";
		}
		
		if (!invalidUserNameCheck()) {
			errorMessage += "Please provide valid username!\n";
		}
		
		if (!invalidHeightCheck()) {
			errorMessage += "Please provide valid height!\n";
		}
		
		if(!invalidPasswordCheck()) {
			errorMessage += "Your password must have at least 8 characters,"
					+ "including one upper-case letter, a digit and no special characters!\n";
		}
		
		if(!invalidEmailCheck()) {
			errorMessage += "Please provide valid e-mail address!\n";
		
		}
		
		if(!invalidWeightCheck()) {
			errorMessage += "Please provide valid weight!\n";
		}
		
		if(!invalidConfirmedPasswordCheck()) {
			errorMessage += "Confirmed password doesn't match the password!\n";
		}
		
		if(errorMessage.length() == 0) return true;
		else {
			Alert alertWindow = new Alert(AlertType.ERROR);
			alertWindow.setHeaderText("Error");
			alertWindow.setContentText(errorMessage);
			alertWindow.showAndWait();
			
			return false;
		}
		
	}
	/*KS*/
	public static boolean invalidFirstNameCheck() {
		boolean fNCheck = true;
		String firstName = firstNameField.getText();
		char[] fNFormat = firstName.toCharArray();
		
		if(fNFormat.length < 1) {
			fNCheck = false;
			return fNCheck;
		}
		if(fNFormat[0] < 65 || fNFormat[0] > 90) {
			fNCheck = false;
			return fNCheck;
		}
		
		if (fNFormat.length < 2) {
			fNCheck = false;
			return fNCheck;
		}
		
		if (firstName.matches(".*\\d.*")){
			fNCheck = false;
			return fNCheck;
		}
		if ((firstName.matches(".*[abcdefghijklmnopqrstuvwxyz].*+"))) {
			fNCheck = true;
		} 
		
		for(int i = 0; i < fNFormat.length; i++) {
			if((fNFormat[i] > 31 && fNFormat[i] < 48) ||
				(fNFormat[i] > 57 && fNFormat[i] < 65) ||
				(fNFormat[i] > 90 && fNFormat[i] < 97) ||
				(fNFormat[i] > 122 && fNFormat[i] <= 255)) {
				
				fNCheck = false;
				return fNCheck;
			}
		}
		
		return fNCheck;
	}
	/*KS*/
	public static boolean invalidLastNameCheck() {
		String lastName = lastNameField.getText();
		char[] lNFormat = lastName.toCharArray();
		boolean lNCheck = true;
		
		if(lNFormat.length < 1) {
			lNCheck = false;
			return lNCheck;
		}
		
		if(lNFormat[0] < 65 || lNFormat[0] > 90) {
			lNCheck = false;
			return lNCheck;
		}
		
		if (lNFormat.length < 2) {
			lNCheck = false;
			return lNCheck;
		}
		if (lastName.matches(".*\\d.*")){
			lNCheck = false;
			return lNCheck;
		}
		else if ((lastName.matches(".*[abcdefghijklmnopqrstuvwxyz].*+"))) {
			lNCheck = true;
		}
		
		for(int i = 0; i < lNFormat.length; i++) {
			if((lNFormat[i] > 31 && lNFormat[i] < 48) ||
				(lNFormat[i] > 57 && lNFormat[i] < 65) ||
				(lNFormat[i] > 90 && lNFormat[i] < 97) ||
				(lNFormat[i] > 122 && lNFormat[i] <= 255)) {
				
				lNCheck = false;
				return lNCheck;
			}
		}
		
		return lNCheck;
	}
	/*KS*/
	public static boolean invalidUserNameCheck() {
		String userName = userNameField.getText();
		char uNFormat[] = userName.toCharArray();
		boolean uNCheck = false;
		
		if(uNFormat.length < 1) {
			uNCheck = false;
			return uNCheck;
		}
		if (((uNFormat[0] > 64 && uNFormat[0] < 91) || (uNFormat[0] > 96 && uNFormat[0] < 123))
			&& (userName.matches(".*\\d.*") || userName.matches(".*[abcdefghijklmnopqrstuvwxyz].*+")
			|| userName.matches(".*[ABCDEFGHIJKLMNOPQRSTUVWXYZ].*+")
			|| userName.matches(".*[_]*."))){
			uNCheck = true;
		}
		for(int i = 0; i < uNFormat.length; i++) {
			if((uNFormat[i] > 31 && uNFormat[i] < 48) ||
				(uNFormat[i] > 57 && uNFormat[i] < 65) ||
				(uNFormat[i] > 90 && uNFormat[i] < 97) ||
				(uNFormat[i] > 122 && uNFormat[i] <= 255)) {
				
				uNCheck = false;
				return uNCheck;
			}
		}
	//	System.out.println("unFormat[0] = " + uNFormat[0]);
		return uNCheck;
		
	}
	/*JS*/
	public static boolean invalidHeightCheck(){
		String height = heightField.getText();
		char[] hFormat = height.toCharArray();
		
		
		//char hFormat[] = height.toCharArray();
		boolean heightCheck = true;
		
		if(hFormat.length < 2 || hFormat.length > 3){
			heightCheck = false;
			return heightCheck;
		} 
		for(int i = 0; i < hFormat.length; i++) {
			if(hFormat[i] < 49 || hFormat[i]  > 57) {
				heightCheck = false;
				return heightCheck;
			}
		}
		
		int heightValue = Integer.parseInt(height);
			
		if(heightValue < 54 || heightValue > 247){
			heightCheck = false;
			System.out.println("test");
			return heightCheck;
		}
		else {
			return true;
		}
	}
	/*KS*/
	public static boolean invalidPasswordCheck() {
		String password = passwordField.getText();
		char[] pFormat = password.toCharArray();
		boolean passwordCheck = false;
		
		if(password.matches(".*\\d.*+") && password.matches
			(".*[abcdefghijklmnopqrstuvwxyz].*") && password.matches
			(".*[ABCDEFGHIJKLMNOPQRSTUVWXYZ].*") && password.length() >= 6)
			passwordCheck = true;
			
		for(int i = 0; i < pFormat.length; i++) {
			if((pFormat[i] > 31 && pFormat[i] < 48) ||
				(pFormat[i] > 57 && pFormat[i] < 65) ||
				(pFormat[i] > 90 && pFormat[i] < 97) ||
				(pFormat[i] > 122 && pFormat[i] <= 255)) {
				
				passwordCheck = false;
				return passwordCheck;
			}
		}		
		return passwordCheck;
	}
	
	/*KS*/
	public static boolean invalidEmailCheck() {
		boolean emailCheck = false;
		String email = emailField.getText();
		char[] eFormat = new char[300];
		eFormat = email.toCharArray();
		
		if(eFormat.length < 1) {
			emailCheck = false;
			return emailCheck;
		}
		
		if (!(eFormat[0] == 64 || eFormat[eFormat.length - 1] == 64)){
			emailCheck = true;
			System.out.println("7. " + emailCheck);
		}
		
		if(!(email.matches(".*[@].*+") || email.matches(".*\\..*"))){
			emailCheck = false;
			System.out.println("8. " + emailCheck);
			return emailCheck;
		}
		System.out.println("9. " + emailCheck);
		return emailCheck;	
	}	
		
	/*JS*/
	public static boolean invalidWeightCheck() {
		String weight = weightField.getText();
		char[] wFormat = weight.toCharArray();
		boolean weightCheck = true;
		
		// 6. If length is less than 2 or greater than 3.
		if(wFormat.length < 2 || wFormat.length > 3){
			weightCheck = false;
			System.out.println("6");
			return weightCheck;
		}
		
		if(wFormat[0] < 49 || wFormat[0] > 57 || wFormat[0] ==51){
			weightCheck = false;
			System.out.println("1");
			return weightCheck;
		}
		// 2. If less than 45
		else if(wFormat[0] == 52 && wFormat[1] <53){
			weightCheck = false;
			System.out.println("2");
			System.out.println("Length = " + wFormat.length);
			return weightCheck;
		}
		// 3. If 1st ASCII = (1 or 2) & length is less than 3
		else if((wFormat[0] == 49 || wFormat[0] == 50) && wFormat.length < 3){
			weightCheck = false;
			System.out.println("3");
			return weightCheck;
		}
		
		// 4. If 2nd ASCII is less than 0 or greater than 99
		if(wFormat[1] < 48 || wFormat[1] > 57){
			weightCheck = false;
			System.out.println("4");
			return weightCheck;
		}
		// 5. If greater than 299
		if(wFormat.length > 2 && wFormat[0] > 50){
			weightCheck = false;
			System.out.println("5");
			return weightCheck;
		}
		

		
		// 7. If 3rd ASCII is less than 0 or greater than 9
		if(wFormat.length == 3){
		if(wFormat[2] < 48 || wFormat[2] > 57){
			weightCheck = false;
			System.out.println("7");
			return weightCheck;
		}
		}
		else weightCheck = true;
		return weightCheck;
	}
	/*KS*/
	public static boolean invalidConfirmedPasswordCheck() {
		boolean cPcheck = true;
		String confirmedPassword = confirmPasswordField.getText();
		if (confirmedPassword.equals(passwordField.getText()))
			cPcheck = true;
		else cPcheck = false;
		
		return cPcheck;
		
	}
	/*KS*/
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
			System.out.println(dateOfBirth);
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
	
	/*public static class UserProfileDataToXML {

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
				user.setDateOfBirth(dOBPicker.getValue().toString());
				user.setConfirmedPassword(confirmPasswordField.getText());
						
				m.marshal(user, sourceFile);
							
			} catch (JAXBException e) {
				e.printStackTrace();
			}
		}
	}*/
	
	/*public class SignUpMenuSerialised {
		public void serialise(int userNumber) {
			SignUpMenuLoc userIndex = new SignUpMenuLoc();
			userIndex.i = userNumber;
			
			try {
				FileOutputStream userIndexOut = new FileOutputStream("src/serialisedClasses/userIndex.ser");
				ObjectOutputStream out = new ObjectOutputStream(userIndexOut);
				out.writeObject(userIndex);
				out.close();
				userIndexOut.close();
			} catch (IOException i) {
				i.printStackTrace();
			}
		}
		
	}*/
}