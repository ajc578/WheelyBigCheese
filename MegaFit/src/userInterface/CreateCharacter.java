package userInterface;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.StackPane;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.control.Label;
import javafx.scene.control.Button;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.ArrayList;

public class CreateCharacter extends HBox {
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
	String eyesPath = "res/images/Eyes/DarkBlueEyes.png";
	String torsoPath;
	String legsPath;
	String hairPath = "res/images/hair/GreenLonghair.png";
	String secondhairPath = "res/images/hair/GreenCatEarhair.png";
	String hair = "res/images/hair/";
	String eyes = "res/images/eyes/";
	File[] hairPaths, eyePaths;
	int currenthair = 0;
	int currentEyes = 0;
	
	VBox selectionChoices;
	
	public CreateCharacter(){
		
		
		String hairPath = new File("").getAbsolutePath();
		System.out.println(hairPath);
		String hairString = hairPath.concat("\\src\\res\\images\\Hair");
		System.out.println(hairString);
		
		hairFile = new File(hairString);
		hairPaths = hairFile.listFiles();
		System.out.println(String.valueOf(hairPaths.length));
		List<String> hairList = new ArrayList<String>();
		for(int i = 0; i<hairPaths.length; i++){
			hairPath = hair.concat(hairPaths[i].getName());
			hairList.add(hairPath);
			System.out.println(hairPath);
		}
		
		String eyesPath = new File("").getAbsolutePath();
		System.out.println(eyesPath);
		String eyeString = eyesPath.concat("\\src\\res\\images\\Eyes");
		System.out.println(eyeString);
			
		eyeFile = new File(eyeString);
		eyePaths = eyeFile.listFiles();
		System.out.println(String.valueOf(eyePaths.length));
		List<String> eyeList = new ArrayList<String>();
		for(int i = 0; i<eyePaths.length; i++){
			eyesPath = eyes.concat(eyePaths[i].getName());
			eyeList.add(eyesPath);
			System.out.println(eyesPath);
		}
	
		
		
		
		characterStack = new StackPane();
		
		hairButtonF = new Button("Change hair");
		
		
		hairButtonF.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent event){
				if(currenthair < (hairList.size() -1)){
					currenthair++;
				}
				else{
					currenthair = 0;
				}				
				hairView.setImage(new Image(hairList.get(currenthair)));
				characterStack.getChildren().set(2, hairView);
				
			}
		});
		
		eyeButton = new Button("Change eye colour");
		
		
		eyeButton.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent event){
				if(currentEyes < (eyeList.size() -1)){
					currentEyes++;
				}
				else{
					currentEyes = 0;
				}				
				eyesView.setImage(new Image(eyeList.get(currentEyes)));
				characterStack.getChildren().set(1, eyesView);
				
			}
		});
		
		
		hairLabel = new Label();
		
		baseView = new ImageView();
		eyesView = new ImageView();
		hairView = new ImageView();
		
		baseView.setImage(new Image(basePath));
		eyesView.setImage(new Image(eyeList.get(0)));
		hairView.setImage(new Image(hairList.get(0)));
		
		eyesView.setPreserveRatio(true);
		hairView.setPreserveRatio(true);
		baseView.setPreserveRatio(true);
		
		baseView.setFitHeight(500);
		eyesView.setFitHeight(500);
		hairView.setFitHeight(500);
		
		
		characterStack.getChildren().add(baseView);
		characterStack.getChildren().add(eyesView);
		characterStack.getChildren().add(hairView);
		
		selectionChoices = new VBox();
		
		selectionChoices.getChildren().addAll(hairButtonF, eyeButton);
		
		getChildren().addAll(characterStack, selectionChoices);
		
	}
	
}
