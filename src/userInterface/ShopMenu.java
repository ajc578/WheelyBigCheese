package userInterface;


import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class ShopMenu extends VBox implements Controllable {

	private ScreenFlowController screenParent;
	private Main mainApp;


		Button backButton;
		
		String[] pictureStrings = {
				"res/images/apple.jpg",
				"res/images/cheese.jpg"
		};
		String[] descriptions = {
				"apple description",
				"cheese description"
		};
		String[] prices = {
				"apple price",
				"cheese price"
		};
		
		VBox itemList;
		ScrollPane scrollPane = new ScrollPane();
	
	public ShopMenu(double screenWidth, double screenHeight) {
		
		itemList = new VBox();
		
		for(int i=0; i<pictureStrings.length; i++){
			ItemLayout item = new ItemLayout(screenWidth, screenHeight, prices[i], descriptions[i], pictureStrings[i]);
			itemList.getChildren().add(item);
		}
		
		itemList.setSpacing(screenHeight*0.05);
		itemList.setPadding(new Insets(screenHeight*0.01, screenWidth*0.02, screenHeight*0.01, screenWidth*0.02));
		
		scrollPane.setContent(itemList);	
		
		scrollPane.setHbarPolicy(ScrollBarPolicy.NEVER);
				
		scrollPane.setMinWidth(screenWidth*0.85);		
		scrollPane.setMinHeight(screenHeight*0.7);
		scrollPane.setMaxWidth(screenWidth*0.85);		
		scrollPane.setMaxHeight(screenHeight*0.7);
		
		Image goBack = new Image("res/images/backButton.png");
		ImageView back = new ImageView(goBack);
		back.setFitHeight(screenHeight*0.1);
		back.setFitWidth(screenWidth*0.1);		
		backButton = new Button("", back);
		
		backButton.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent event){
				screenParent.setScreen(Main.characterMenuID);
			}
		});
		
		getChildren().addAll(scrollPane, backButton);
		
	}	
	
	
	public void setNodeCursor (Node node) {
		node.setOnMouseEntered(event -> setCursor(Cursor.HAND));
		node.setOnMouseExited(event -> setCursor(Cursor.DEFAULT));
	}

	@Override
	public void setScreenParent(ScreenFlowController screenParent) {
		this.screenParent = screenParent;
	}

	@Override
	public void setMainApp(Main mainApp) {
		this.mainApp = mainApp;
	}
	
}
	

