package userInterface;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import account.CharacterParts;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class CreateCharacter extends VBox implements Controllable {
	private StackPaneUpdater screenParent;
	private Main mainApp;

	private static final String defaultBodyImagePath = "res/images/BaseCharacter.png";
	private static final String defaultHairImagePath = "res/images/Hair/BlackSpikeHair.png";
	private static final String defaultEyesImagePath = "res/images/Eyes/BrownEyes.png";

	StackPane characterStack;

	ImageView baseView;
	ImageView eyesView;
	ImageView torsoView;
	ImageView legsView;
	ImageView hairView;
	File hairFile, eyeFile, torsoFile, legsFile;

	Label hairLabel;

	Button hairButtonF, hairButtonB, eyeButton;

	String basePath = "res/images/BaseCharacter.png";
	String torsoPath;
	String legsPath;
	String hair = "res/images/Hair/";
	String eyes = "res/images/Eyes/";
	File[] hairPaths, eyePaths;
	List<String> eyeList;
	List<String> hairList;
	String currentEyesPath;
	String currentHairPath;
	int currenthair = 0;
	int currentEyes = 0;

	VBox selectionChoices;
	private CharacterParts character;

	public CreateCharacter(double screenWidth, double screenHeight){
		CharacterParts source = null;
		if (Main.account != null) {
			source = Main.account.getCharacterAttributes().getCharacterSource();
		} else {
			source = new CharacterParts();
			source.setBodySource(defaultBodyImagePath);
			source.setEyesSource(defaultEyesImagePath);
			source.setHairSource(defaultHairImagePath);
		}
		this.character = source;


		String hairPath = new File("").getAbsolutePath();
		String hairString = hairPath.concat("/src/res/images/Hair");

		hairFile = new File(hairString);
		hairPaths = hairFile.listFiles();
		hairList = new ArrayList<String>();
		for(int i = 0; i<hairPaths.length; i++){
			hairPath = hair.concat(hairPaths[i].getName());
			hairList.add(hairPath);
		}

		String eyesPath = new File("").getAbsolutePath();
		String eyeString = eyesPath.concat("/src/res/images/Eyes");

		eyeFile = new File(eyeString);
		eyePaths = eyeFile.listFiles();
		eyeList = new ArrayList<String>();
		for(int i = 0; i<eyePaths.length; i++){
			eyesPath = eyes.concat(eyePaths[i].getName());
			eyeList.add(eyesPath);
		}

		characterStack = new StackPane();

		hairButtonF = new Button("Change Hair");
		setNodeCursor(hairButtonF);


		hairButtonF.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent event){
				if(currenthair < (hairList.size() -1)){
					currenthair++;
				}
				else{
					currenthair = 0;
				}
				currentHairPath = hairList.get(currenthair);
				hairView.setImage(new Image(currentHairPath));
				characterStack.getChildren().set(2, hairView);
				setCharacterStoragePaths();

			}
		});

		eyeButton = new Button("Change eye colour");
		setNodeCursor(eyeButton);


		eyeButton.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent event){
				if(currentEyes < (eyeList.size() -1)){
					currentEyes++;
				}
				else{
					currentEyes = 0;
				}
				currentEyesPath = eyeList.get(currentEyes);
				eyesView.setImage(new Image(currentEyesPath));
				characterStack.getChildren().set(1, eyesView);
				setCharacterStoragePaths();

			}
		});


		hairLabel = new Label();

		baseView = new ImageView();
		eyesView = new ImageView();
		hairView = new ImageView();

		baseView.setImage(new Image(basePath));

		if(character.getEyesSource() != null){
			currentEyesPath = character.getEyesSource();

		}
		else{
			currentEyesPath = eyeList.get(0);
		}

		if(character.getHairSource() != null){
			currentHairPath = character.getHairSource();

		}
		else{
			currentHairPath = hairList.get(0);
		}

		setCharacterStoragePaths();

		eyesView.setImage(new Image(currentEyesPath));
		hairView.setImage(new Image(currentHairPath));

		eyesView.setPreserveRatio(true);
		hairView.setPreserveRatio(true);
		baseView.setPreserveRatio(true);

		baseView.setFitHeight(screenHeight*0.8);
		eyesView.setFitHeight(screenHeight*0.8);
		hairView.setFitHeight(screenHeight*0.8);


		characterStack.getChildren().add(baseView);
		characterStack.getChildren().add(eyesView);
		characterStack.getChildren().add(hairView);


		selectionChoices = new VBox();

		selectionChoices.getChildren().addAll(hairButtonF, eyeButton);
		selectionChoices.setSpacing(screenHeight*0.05);
		//selectionChoices.setPadding(new Insets(screenHeight*0.05, screenWidth*0.05, screenHeight*0.05, screenWidth*0.05));

		getChildren().addAll(characterStack, selectionChoices);
		setSpacing(screenHeight*0.02);
		setAlignment(Pos.BOTTOM_CENTER);


	}

	private void setCharacterStoragePaths(){
		character.setEyesSource(currentEyesPath);
		character.setHairSource(currentHairPath);
		if (Main.account != null) {
			Main.account.getCharacterAttributes().getCharacterSource().setEyesSource(currentEyesPath);
			Main.account.getCharacterAttributes().getCharacterSource().setHairSource(currentHairPath);
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

	public String getCurrentEyesPath() {
		// TODO Auto-generated method stub
		return currentEyesPath;
	}

	public String getCurrentHairPath() {
		// TODO Auto-generated method stub
		return currentHairPath;
	}



}
