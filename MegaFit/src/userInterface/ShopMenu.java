package userInterface;


import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ShopMenu extends VBox {

	
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
		
		BorderPane root = new BorderPane();
		
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
		
		ImageView backButton = BackImageButton(screenWidth, screenHeight);
		setNodeCursor(backButton);
		HBox backButtonBox = new HBox();
		backButtonBox.getChildren().add(backButton);
		
		backButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			CharacterMenu menu = new CharacterMenu (screenWidth, screenHeight, root);
			@Override
			public void handle(MouseEvent event) {
				getChildren().add(menu);
				getChildren().removeAll(scrollPane, backButtonBox);
			}
			
		});
		
		getChildren().addAll(scrollPane, backButtonBox);
		
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
	
	
	public void setNodeCursor (Node node) {
		node.setOnMouseEntered(event -> setCursor(Cursor.HAND));
		node.setOnMouseExited(event -> setCursor(Cursor.DEFAULT));
	}
	
}
	

