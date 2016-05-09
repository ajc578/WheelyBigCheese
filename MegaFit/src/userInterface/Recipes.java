package userInterface;

import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class Recipes extends HBox {

	Label recipeName, mealName, recipeContains;
	Button viewRecipe;
	Image picture;
	ImageView recipePicture;
	
	
	public Recipes (double screenWidth, double screenHeight, String recipe, String meal,
			String contents, String pictureString){
		
		recipeName = new Label(recipe);
		recipeName.setMinWidth(screenWidth*0.2);
		
		mealName = new Label(meal);
		mealName.setMinWidth(screenWidth*0.1);
		
		recipeContains = new Label (contents);
		recipeContains.setMinWidth(screenWidth*0.2);
		
		picture = new Image(pictureString);
		
		viewRecipe = new Button("VIEW");
		viewRecipe.setPrefSize(screenWidth*0.07, screenHeight*0.05);
		setButtonCursor(viewRecipe);
		
		recipePicture = new ImageView(picture);
		recipePicture.setFitWidth(screenWidth*0.1);
		recipePicture.setFitHeight(screenWidth*0.1);
		
		getChildren().addAll(recipePicture, recipeName, mealName, recipeContains, 
				viewRecipe);
		setWidth(screenWidth*0.8);
		setHeight(screenHeight*0.2);
		setSpacing(screenWidth*0.025);
	}
	
	public void setButtonCursor (Button button) {
		button.setOnMouseEntered(event -> setCursor(Cursor.HAND));
		button.setOnMouseExited(event -> setCursor(Cursor.DEFAULT));
	}
	
}
