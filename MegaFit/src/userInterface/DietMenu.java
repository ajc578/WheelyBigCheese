package userInterface;

import javafx.scene.layout.VBox;
import javafx.scene.control.ScrollPane;

public class DietMenu extends ScrollPane {
	
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
			"Tortilla-Less Soup",
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
	
	public DietMenu(double screenWidth, double screenHeight){
		
		recipeList = new VBox();
		
		for(int i=0; i<recipes.length; i++){
			Recipes recipeView = new Recipes(screenWidth, screenHeight, recipes[i], meals[i], 
					contains[i], pictureStrings[i]);
			recipeList.getChildren().add(recipeView);
		}
		
		recipeList.setSpacing(screenHeight*0.05);
		
		setContent(recipeList);
				
		setMinWidth(screenWidth*0.85);		
		setMinHeight(screenHeight*0.7);
		setMaxWidth(screenWidth*0.85);		
		setMaxHeight(screenHeight*0.7);

}

};
