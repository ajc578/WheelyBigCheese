package userInterface;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.image.Image;
import javafx.scene.control.Label;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.ArrayList;

public class CreateCharacter extends VBox {
	StackPane characterStack;
	
	ImageView baseView;
	ImageView eyesView;
	ImageView torsoView;
	ImageView legsView;
	ImageView hairView;
	File hairFile;
	
	Label hairLabel;
	
	Button hairButtonF, hairButtonB;
	
	String basePath = "res/images/BaseCharacter.png";
	String eyesPath = "res/images/Eyes/DarkBlueEyes.png";
	String torsoPath;
	String legsPath;
	String hairPath = "res/images/Hair/GreenLongHair.png";
	String secondHairPath = "res/images/Hair/GreenCatEarHair.png";
	String path = "res/images/Hair/";
	File[] hairPaths;
	int currentHair = 0;
	
	public CreateCharacter(double screenWidth, double screenHeight){
		
		BorderPane root = new BorderPane();
		
		String filePath = new File("").getAbsolutePath();
		System.out.println(filePath);
		String newString = filePath.concat("\\src\\res\\images\\Hair");
		System.out.println(newString);
		
		hairFile = new File(newString);
		hairPaths = hairFile.listFiles();
		System.out.println(String.valueOf(hairPaths.length));
		List<String> hairList = new ArrayList<String>();
		for(int i = 0; i<hairPaths.length; i++){
			filePath = path.concat(hairPaths[i].getName());
			hairList.add(filePath);
			System.out.println(filePath);
		}
			
	
		characterStack = new StackPane();
		
		hairButtonF = new Button("Change Hair");
		setNodeCursor(hairButtonF);
		
		hairButtonF.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent event){
				if(currentHair < (hairList.size() -1)){
					currentHair++;
				}
				else{
					currentHair = 0;
				}				
				hairView.setImage(new Image(hairList.get(currentHair)));
				characterStack.getChildren().set(2, hairView);
				
			}
		});
		
		
		hairLabel = new Label();
		
		baseView = new ImageView();
		eyesView = new ImageView();
		hairView = new ImageView();
		
		baseView.setImage(new Image(basePath));
		eyesView.setImage(new Image(eyesPath));
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
		
		ImageView backButton = BackImageButton(screenWidth, screenHeight);
		setNodeCursor(backButton);
		HBox backButtonBox = new HBox();
		backButtonBox.getChildren().add(backButton);
		
		backButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			CharacterMenu menu = new CharacterMenu (screenWidth, screenHeight, root);
			@Override
			public void handle(MouseEvent event) {
				getChildren().add(menu);
				getChildren().removeAll(characterStack, hairButtonF, backButtonBox);
			}
			
		});
		
		getChildren().addAll(characterStack, hairButtonF, backButtonBox);
		setSpacing(screenHeight*0.05);
		
	}
	
	public void setNodeCursor (Node node) {
		
		node.setOnMouseEntered(event -> setCursor(Cursor.HAND));
		node.setOnMouseExited(event -> setCursor(Cursor.DEFAULT));
	}
	
	public ImageView BackImageButton (double screenWidth, double screenHeight) {
		
		HBox buttonImageBox = new HBox();
		buttonImageBox.setAlignment(Pos.BOTTOM_LEFT);
		
		Image backButton = new Image("res/images/back_arrow.jpg");
		ImageView buttonImageView = new ImageView(backButton);
		buttonImageView.setImage(backButton);
		buttonImageView.setFitWidth(screenWidth*0.05);
		buttonImageView.setFitHeight(screenHeight*0.05);
		
		return buttonImageView;
	
	}
	
}
