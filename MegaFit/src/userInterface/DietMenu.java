package userInterface;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.Cursor;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class DietMenu extends VBox {
	
	String[] pictureStrings = {
		"res/images/granola.JPG",
		"res/images/pbsmoothie.jpg",
		"res/images/tlsoup.jpg",
		"res/images/vcouscous.jpg",
		"res/images/pbcookies.jpg"
	};
	
	String[] recipes = {
			"Cruncy Granola Wedges",
			"Peanut Butter Banana Smoothie",
			"Tortilla Less Soup",
			"Vegetarian Couscous",
			"Peanut Butter Protein Cookies"
	};
	
	String[] meals = {
			"Snack/Breakfast",
			"Breakfast",
			"Lunch",
			"Dinner/Lunch",
			"Snack"
	};
	
	String[] contains = {
			"Vegetarian, contains gluten",
			"Vegetarian, gliten-free, dairy-free",
			"gluten-free",
			"Vegetarian, dairy-free, contains gluten",
			"Vegetarian, gluten-free, dairy-free"
	};
	
	VBox recipeList;
	
	ScrollPane scrollPane = new ScrollPane();
	BorderPane root = new BorderPane();
	
	public DietMenu(double screenWidth, double screenHeight){
		
		recipeList = new VBox();
		
		for(int i=0; i<recipes.length; i++){
			Recipes recipeView = new Recipes(screenWidth, screenHeight, recipes[i], meals[i], 
					contains[i], pictureStrings[i]);
			recipeList.getChildren().add(recipeView);
		}
		
		recipeList.setSpacing(screenHeight*0.05);
		recipeList.setPadding(new Insets(screenHeight*0.01, screenWidth*0.02, screenHeight*0.01, screenWidth*0.02));
		
		scrollPane.setContent(recipeList);
		
		scrollPane.setHbarPolicy(ScrollBarPolicy.NEVER);
		
		scrollPane.setMinWidth(screenWidth*0.85);		
		scrollPane.setMinHeight(screenHeight*0.7);
		scrollPane.setMaxWidth(screenWidth*0.85);		
		scrollPane.setMaxHeight(screenHeight*0.7);
		
		getChildren().add(scrollPane);
		putBackImageButton(screenWidth, screenHeight, root);
		
	}
	
public void putBackImageButton (double screenWidth, double screenHeight, BorderPane root) {
		
		Menu menu = new Menu (screenWidth, screenHeight, root);
		
		HBox buttonImageBox = new HBox();
		buttonImageBox.setAlignment(Pos.BOTTOM_LEFT);
		
		Image backButton = new Image("res/images/back_arrow.jpg");
		ImageView buttonImageView = new ImageView(backButton);
		buttonImageView.setImage(backButton);
		buttonImageView.setFitWidth(screenWidth*0.05);
		buttonImageView.setFitHeight(screenHeight*0.05);
		
		buttonImageView.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				//getChildren().add(menu);
				getChildren().removeAll(scrollPane, buttonImageBox);
				setPadding(new Insets(0, 0, 0, 0));
			}
			
		});
		
		buttonImageView.setOnMouseEntered(event -> setCursor(Cursor.HAND));
		
		buttonImageBox.getChildren().add(buttonImageView);
		buttonImageBox.setPadding(new Insets(screenHeight*0.1, screenWidth*0.9, 0, 0));
		getChildren().add(buttonImageBox);
		
	}

}
