package userInterface;


import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

public class ShopMenu extends ScrollPane {

	
		String[] pictureStrings = {
				"apple.jpg",
				"cheese.jpg"
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
	
	public ShopMenu(double screenWidth, double screenHeight) {
		
		itemList = new VBox();
		
		
		for(int i=0; i<pictureStrings.length; i++){
			ItemLayout item = new ItemLayout(screenWidth, screenHeight, prices[i], descriptions[i], pictureStrings[i]);
			itemList.getChildren().add(item);
		}
		
		itemList.setSpacing(screenHeight*0.05);
		itemList.setPadding(new Insets(screenHeight*0.01, screenWidth*0.02, screenHeight*0.01, screenWidth*0.02));
		
		setContent(itemList);	
		
		setHbarPolicy(ScrollBarPolicy.NEVER);
				
		setMinWidth(screenWidth*0.85);		
		setMinHeight(screenHeight*0.7);
		setMaxWidth(screenWidth*0.85);		
		setMaxHeight(screenHeight*0.7);
		
		
	}	
	
}
	

