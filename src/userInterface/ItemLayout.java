package userInterface;

import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class ItemLayout extends HBox {

	Label priceLabel, descriptionLabel;
	ImageView itemPicture;
	Image picture;
	Button purchaseButton, equipButton;
	
	public ItemLayout(double screenWidth, double screenHeight, String price, String description,
			String pictureString) {
	
		priceLabel = new Label(price);
		priceLabel.setMinWidth(screenWidth*0.05);
		descriptionLabel = new Label(description);
		descriptionLabel.setMinWidth(screenWidth*0.2);
		purchaseButton = new Button("BUY");
		setNodeCursor(purchaseButton);
		purchaseButton.setPrefSize(screenWidth*0.07, screenHeight*0.05);
		equipButton = new Button("EQUIP");
		setNodeCursor(equipButton);
		equipButton.setPrefSize(screenWidth*0.07, screenHeight*0.05);
		
		picture = new Image(pictureString);
		
		itemPicture = new ImageView(picture);
		itemPicture.setFitWidth(screenWidth*0.1);
		itemPicture.setFitHeight(screenWidth*0.1);
		
		getChildren().addAll(itemPicture, descriptionLabel, 
				priceLabel, purchaseButton, equipButton);
		setWidth(screenWidth*0.8);
		setHeight(screenHeight*0.2);
		setSpacing(screenWidth*0.05);
		
	}
	
	public void setNodeCursor (Node node) {
		
		node.setOnMouseEntered(event -> setCursor(Cursor.HAND));
		node.setOnMouseExited(event -> setCursor(Cursor.DEFAULT));
	}
}

