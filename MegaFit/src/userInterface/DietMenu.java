package userInterface;

import javafx.scene.layout.VBox;

import java.io.File;
import java.util.ArrayList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import userInterface.Meal;
import userInterface.Meals;
import javafx.scene.control.ScrollPane;

public class DietMenu extends ScrollPane implements Controllable {

	private ScreenFlowController screenParent;
	private Main mainApp;
	
	Recipes recipeView;
	
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
	
	String mealNames[] = new String[5];
	String mealTypes[] = new String[5];
	String imgPaths[] = new String[5];
	int i = 0;
	
	static Meals mealList = new Meals();
	
	public DietMenu(double screenWidth, double screenHeight) throws JAXBException{
		
		recipeList = new VBox();
		
		for(int i=0; i<recipes.length; i++){
			Recipes recipeView = new Recipes(screenWidth, screenHeight, recipes[i], meals[i], 
					contains[i], pictureStrings[i]);
			recipeList.getChildren().add(recipeView);
		}
		//recipeView.displayMealInfo();
		
		recipeList.setSpacing(screenHeight*0.05);
		
		setContent(recipeList);
				
		setMinWidth(screenWidth*0.85);		
		setMinHeight(screenHeight*0.7);
		setMaxWidth(screenWidth*0.85);		
		setMaxHeight(screenHeight*0.7);

	}

	@Override
	public void setScreenParent(ScreenFlowController screenParent) {
		this.screenParent = screenParent;
	}

	@Override
	public void setMainApp(Main mainApp) {
		this.mainApp = mainApp;
	}

};
