/**
 * SignUpMenu Class
 * @author - Kamil Sledziewski
 * @author - Jonathan Sullivan
 * @author - Henry Nash
 * @author - Sebastien Corrigan
 */

package userInterface;

import java.io.BufferedWriter;
import java.io.File;

import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import account.Protocol;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * SignUpMenu allows the user to create a new account within the application by putting
 * their profile data into 9 text fields. The data input into the text fields is retrieved by 
 * appropriate integration (with Account) methods, and stored in the application database.
 * <p>
 * The class extends VBox as this the most outer container into which all the sub-
 * containers and nodes are put
 * @author Kamil Sledziewski (Base GUI and error checking methods)
 * @author Jonathan Sullivan (error checking methods)
 * @author Oliver Rushton (GUI modifications, integration with the Account module and error checking methods)
 *
 */

public class SignUpMenu extends VBox implements Controllable {
	
	private static final String activeAccountPath = "src/res/clientAccounts/activeAccount.txt";
	
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
	
	Label header =  new Label("Sign Up to MegaFit");
	Label errorLabel = new Label("\n\n\n\n\n\n\n\n\n");
	
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

	HBox buttonBox = new HBox();
	
	GridPane grid = new GridPane();
	
	private StackPaneUpdater screenParent;
	private Main mainApp;
	private double screenWidth, screenHeight;

	public SignUpMenu() {
		
	}
	
	/**
	 * Constructs a SignUpMenu
	 * @param screenWidth parameter that adjusts the majority of the horizontal spacings and padding constraints according to the width of machine's screen
	 * @param screenHeight parameter that adjusts the majority of the vertical spacings and padding constraints according to the height of machine's screen
	 * <p>
	 * Adds ColumnConstraints to properly lay out all the nodes within the GridPane grid
	 * <p>
	 * The 9 text fields are used to receive the following data:
	 * First Name,
	 *  Last Name,
	 *  Username, 
	 *  Password,
	 *  Password Confirmation,
	 *  Height (in metres and centimetres),
	 *  Weight (in kilograms),
	 *  Date of Birth,
	 *  Email
	 * <p>
	 * Each text field is associated with a label that indicates the type of information
	 * the user is meant to input into the text field
	 * <p>
	 * Each text field also contains initial prompt text in order to help the user input the data correctly
	 * <p> 
	 * The erroneous input checking methods ensure that the collection of data input into text fields
	 * is correct at the final stage of sign up, that is just before storing the data in the Account
	 * <p>
	 * The constructor creates a GridPane that consists of 6 columns and 5 rows 
	 * <p>
	 * Columns 1 and 2 contain the first 5 labels and corresponding text fields (genericColumn in ColumnConstraints)
	 * <p>
	 * Columns 4 and 5 contain the other 4 labels and corresponding text fields (genericColumn in ColumnConstraints)
	 * <p> 
	 * Columns 0 and 3 are used as blank columns setting appropriate spacings between the two column
	 * sets (sideColumn in ColumnConstraints)
	 * <p>
	 * The "DONE" button will navigate the user to the main character menu, if no input errors are detected by the error checking methods
	 * <p>
	 * Once the user is navigated to the main character menu, this will mean that their data are appropriately passed and stored in the Account module
	 */
	
	public SignUpMenu (double screenWidth, double screenHeight) {
		
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;
		
		ColumnConstraints sideColumn = new ColumnConstraints(100, 200, Double.MAX_VALUE);
		sideColumn.setHgrow(Priority.ALWAYS);
		ColumnConstraints dividerColumn = new ColumnConstraints(50, 100, 150);
		ColumnConstraints genericColumn = new ColumnConstraints();
		grid.getColumnConstraints().addAll(sideColumn, genericColumn, genericColumn, dividerColumn, genericColumn, genericColumn, sideColumn);
		
		firstNameField.setPromptText("Enter First Name");
		lastNameField.setPromptText("Enter Last Name");
		userNameField.setPromptText("Enter Username");
		heightField.setPromptText("Enter height (M.CM)");
		passwordField.setPromptText("Enter Password");
		emailField.setPromptText("Enter Email Address");
		weightField.setPromptText("Enter Weight (Kg)");
		dOBPicker.setPromptText("DD/MM/YYYY");
		confirmPasswordField.setPromptText("Retype Password");
		
		grid.setHgap(10);
		grid.setVgap(20);
		grid.setPadding(new Insets(10));
		
		GridPane.setHalignment(firstNameLabel, HPos.RIGHT);
		grid.add(firstNameLabel, 1, 0);
		GridPane.setHalignment(lastNameLabel, HPos.RIGHT);
		grid.add(lastNameLabel, 1, 1);
		GridPane.setHalignment(userNameLabel, HPos.RIGHT);
		grid.add(userNameLabel, 1, 2);
		GridPane.setHalignment(passwordLabel, HPos.RIGHT);
		grid.add(passwordLabel, 1, 3);
		GridPane.setHalignment(confirmPasswordLabel, HPos.RIGHT);
		grid.add(confirmPasswordLabel, 1, 4);
		
		grid.add(firstNameField, 2, 0);
		grid.add(lastNameField, 2, 1);
		grid.add(userNameField, 2, 2);
		grid.add(passwordField, 2, 3);
		grid.add(confirmPasswordField, 2, 4);
		
		GridPane.setHalignment(heightLabel, HPos.RIGHT);
		grid.add(heightLabel, 4, 0);
		GridPane.setHalignment(weightLabel, HPos.RIGHT);
		grid.add(weightLabel, 4, 1);
		GridPane.setHalignment(dateOfBirthLabel, HPos.RIGHT);
		grid.add(dateOfBirthLabel, 4, 2);
		GridPane.setHalignment(emailLabel, HPos.RIGHT);
		grid.add(emailLabel, 4, 3);
		
		grid.add(heightField, 5, 0);
		grid.add(weightField, 5, 1);
		grid.add(dOBPicker, 5, 2);
		grid.add(emailField, 5, 3);
		
		buttonBox.getChildren().addAll(doneButton);
		//TODO character
		doneButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				// TODO  name of this method is misleading in the if statement
				// because when it is true, then the input is correct.
				String errorMessage = erroneusInputCheck();
				if (errorMessage.equals("\n\n\n\n\n\n\n\n\n")) {
					System.out.println("No errors detected in sign up credentials - SignUpMenu");
					if (Main.serverDetected && Main.client.isAccessible()) {
						Main.client.createAccount(userNameField.getText(), passwordField.getText(), firstNameField.getText(), 
												  lastNameField.getText(), weightField.getText(), heightField.getText(), 
												  dOBPicker.getEditor().getText(), emailField.getText());
						while (true) {
							String output = Main.client.receive();
							if (output.equals(Protocol.SUCCESS)) {
								Main.account = Main.client.getAccount();
								System.out.println("Success in creating account in SignUpMenu.");
								File activeAccount = new File(activeAccountPath);
								if (activeAccount.exists() && activeAccount.isFile()) {
									try (
										BufferedWriter	writer= new BufferedWriter(new FileWriter(activeAccount));
									) {
										writer.write(Main.client.getAccount().getUsername());
									} catch (IOException ioe) {
										//TODO handle exception
										ioe.printStackTrace();
									}
								} else {
									System.out.println("Active Account text file does not exist. (SignUpMenu)");
								}
								
								try {
									Thread.sleep(200);
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								loadJavaScreens();
								screenParent.loadDietPlanner();
								screenParent.loadWorkoutLibrary();
								screenParent.loadCharacterDashboard();
								screenParent.setScreen(Main.characterDashID);
								System.out.println("SUM: ");
								break;
							} else if (output.startsWith(Protocol.ERROR)) {
								System.out.println("Error returned in SignUpMenu from ClientSide: " + output);
								//TODO add error label to 
								break;
							}
						}
					} else {
						errorLabel.setText("Cannot create an account at this time. Server not detected.\n\n\n\n\n\n\n\n");
						errorLabel.setTextFill(Color.RED);
					}
				} else {
					errorLabel.setText(errorMessage);
					errorLabel.setTextFill(Color.RED);
				}
			}
		});

		setNodeCursor(doneButton);

		buttonBox.setPadding(new Insets(0, screenWidth/2.2, 0, screenWidth/2.2));
		
		header.setFont(Font.font("Calibri", FontWeight.BOLD, 16));
		HBox headerBox = new HBox();
		headerBox.setAlignment(Pos.CENTER);
		headerBox.setSpacing(10);
		headerBox.setPadding(new Insets(20));
		headerBox.getChildren().add(header);
		
		getChildren().addAll(headerBox, grid, buttonBox, errorLabel);
		putBackImageButton(screenWidth, screenHeight);
		//setSpacing(20);
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

		mainApp.setLevelBar(levelCurve(Main.account.getLevel()),
									   Main.account.getXp(),
									   levelCurve(Main.account.getLevel()+1),
									   Main.account.getLevel());

//		WorkoutEndCard workoutEndCardInstance = new WorkoutEndCard(screenWidth, screenHeight, completedExercises);
//		controllableCenterScreen.loadJavaWrittenScreen(workoutEndCardID, workoutEndCardInstance);


	}
	
	private int levelCurve(int n){
		int levelBoundary;

		levelBoundary = Math.round(Math.round((Math.exp(n/7)*150) + ((n-1)*75)-174));

		return levelBoundary;
	}
	
	public void putBackImageButton (double screenWidth, double screenHeight) {
		
		HBox buttonImageBox = new HBox();
		buttonImageBox.setAlignment(Pos.BOTTOM_LEFT);
		
		Image backButton = new Image("res/images/backButton.png");
		ImageView buttonImageView = new ImageView(backButton);
		buttonImageView.setImage(backButton);
		buttonImageView.setFitWidth(screenWidth*0.05);
		buttonImageView.setFitHeight(screenHeight*0.05);
		
		buttonImageView.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

			// TODO login
			@Override
			public void handle(MouseEvent event) {
				screenParent.setScreen(Main.loginID);
			}
			
		});
		
		buttonImageView.setOnMouseEntered(event -> setCursor(Cursor.HAND));
		
		buttonImageBox.getChildren().add(buttonImageView);
		getChildren().add(buttonImageBox);
		
	}
	
	public void setNodeCursor (Node node) {
		
		node.setOnMouseEntered(event -> setCursor(Cursor.HAND));
		node.setOnMouseExited(event -> setCursor(Cursor.DEFAULT));
	}
	
	
	 /**
	 * Checks whether any of the text fields is blank when the user presses the done button
	 * and collects the outputs of each of the error checking methods corresponding to each text field
	 * in order to display the resulting error message as a label at the top of the sign up screen
	 * in the event of data being input unsuccessfully
	 * 
	 * @return errorMessage a string that collects all the error messages resulting from 
	 * erroneous data input into text fields
	 */
	public static String erroneusInputCheck() {
		String errorMessage = "";
		if((firstNameField.getText() == null || firstNameField.getText().length() == 0) ||
			(lastNameField.getText() == null || lastNameField.getText().length() == 0) ||
			(userNameField.getText() == null || userNameField.getText().length() == 0) ||
			(heightField.getText() == null || heightField.getText().length() == 0) ||
			(passwordField.getText() == null || passwordField.getText().length() == 0) ||
			(emailField.getText() == null || emailField.getText().length() == 0) ||
			(weightField.getText() == null || weightField.getText().length() == 0) ||
			(confirmPasswordField.getText() == null || confirmPasswordField.getText().length() == 0)) {
				errorMessage += "Fill in all the fields!\n";
		} 
		
		if (!invalidFirstNameCheck(firstNameField.getText())) {
			errorMessage += "Please provide valid first name!\n";
		} 
		
		if (!invalidLastNameCheck(lastNameField.getText())) {
			errorMessage += "Please provide valid last name!\n";
		} 
		
		if (!invalidUserNameCheck(userNameField.getText())) {
			errorMessage += "Please provide valid username!\n";
		} 
		
		if(!invalidPasswordCheck(passwordField.getText())) {
			errorMessage += "Your password must have at least 8 characters,"
					+ "including one upper-case letter, a digit and no special characters!\n";
		} 
		
		if(!invalidConfirmedPasswordCheck(confirmPasswordField.getText())) {
			errorMessage += "Confirmed password doesn't match the password!\n";
		} 
		
		if (!invalidHeightCheck(heightField.getText())) {
			errorMessage += "Please provide valid height!\n";
		} 
		
		if(!invalidWeightCheck(weightField.getText())) {
			errorMessage += "Please provide valid weight!\n";
		} 
		
		if (!invalidDOBCheck()) {
			errorMessage += "Please provide valid date of birth!\n";
		} 
		
		if(!invalidEmailCheck(emailField.getText())) {
			errorMessage += "Please provide valid e-mail address!\n";
		} 
		
		if (errorMessage.equals(""))
			errorMessage = "\n\n\n\n\n\n\n\n\n";
		System.out.println("ErrorMessage is: " + errorMessage);
		return errorMessage;
		
	}
	
	private static boolean invalidDOBCheck() {
		boolean dobCheck = true;
		String dob = dOBPicker.getEditor().getText();
		Date date = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            date = sdf.parse(dob);
            if (!dob.equals(sdf.format(date))) {
                dobCheck = false;
            }
        } catch (ParseException ex) {
        	dobCheck = false;
        }
        return dobCheck;
	}
	
	/**
	 * Boolean method that checks whether the first name input by the user starts with a captial letter
	 * and then contains only lower case letter
	 * <p>
	 * The above checking procedure is conducted through converting the string input into the textfield
	 * into a character array, which enables comparing each character of the string according
	 * to the method specification
	 * @return true if the above conditions are met
	 * @return false otherwise
	 */
	private static boolean invalidFirstNameCheck(String firstName) {
		boolean fNCheck = true;
		//String firstName = firstNameField.getText();
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
	private static boolean invalidLastNameCheck(String lastName) {
		//String lastName = lastNameField.getText();
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

	 /**
	 * Boolean method that checks whether the user name input by the user is of correct format
	 * <p>
	 * It checks if the user name consists of upper/lower case letters, digits and underscores only.
	 * @return true if the above conditions are met
	 * @return false otherwise
	 */
	private static boolean invalidUserNameCheck(String userName) {
		//String userName = userNameField.getText();
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
	
		 /**
		 * Boolean method that checks whether the height input by the user into the text field
		 * is of correct format - X.XX (m.cm)
		 * <p>
		 * It checks whether the height contains a "." sign and that all the other characters are digits
		 * <p>
		 * By converting the input into a double variable, it all sets the bottom and upper constraints for the input
		 * @return true if the above conditions are met
		 * @return false otherwise
		 */
	private static boolean invalidHeightCheck(String height){
		boolean heightCheck = true;
		//String height = heightField.getText();
		if (!height.contains(".")) {
			heightCheck = false;
		} else {
			if (height.matches(".*[0123456789.].*+")) {
				double heightTemp = Double.parseDouble(height);
				if (!(heightTemp > 0.50 && heightTemp < 3.00)) {
					heightCheck = false;
				}
			} else {
				heightCheck = false;
			}
			
		}
		return heightCheck;
	}
	
	/**
	 * Boolean method that checks whether the password input by the user is of correct format
	 * <p>
	 * It requires that the password is at least 6 characters long
	 * <p>
	 * It checks if the input password contains an upper/lower case letter and a number
	 * @return true if the above conditions are met
	 * @return false otherwise
	 */
	public static boolean invalidPasswordCheck(String password) {
		//String password = passwordField.getText();
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
	
	/**
	 * This method checks whether the email input by the user into the text field is of correct format
	 * <p>
	 * It ensures that the email doesn't start nor end with a period or an "@" sign
	 * <p>
	 * It ensures that the email does contain the two characters elsewhere
	 * @return true if the above conditions are met
	 * @return false otherwise
	 */
	private static boolean invalidEmailCheck(String email) {
		boolean emailCheck = false;
		//String email = emailField.getText();
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
		
	/**
	 * This method checks whether the weight input by the user into the text field is of correct format
	 * <p>
	 * It makes sure if the input consists of not less than 2, and not more than 3 characters
	 * <p>
	 * It ensures that the weight input by the user should be not less than 45 kg and not greater than 299 kg
	 * <p>
	 * 
	 * @return true if the above conditions are met
	 * @return false otherwise
	 */
	private static boolean invalidWeightCheck(String weight) {
		//String weight = weightField.getText();
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
	
	/**
	 * This method ensures that the confirmed password input by the user matches the input password
	 * @return true if the above condition is met
	 * @return false otherwise
	 */
	private static boolean invalidConfirmedPasswordCheck(String confirmedPassword) {
		boolean cPcheck = true;
		//String confirmedPassword = confirmPasswordField.getText();
		if (confirmedPassword.equals(passwordField.getText()))
			cPcheck = true;
		else cPcheck = false;
		
		return cPcheck;
		
	}

	@Override
	public void setScreenParent(StackPaneUpdater screenParent) {
		this.screenParent = screenParent;
		
	}

	@Override
	public void setMainApp(Main mainApp) {
		this.mainApp = mainApp;
		
	}
}