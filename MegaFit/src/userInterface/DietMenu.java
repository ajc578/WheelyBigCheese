package userInterface;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import userInterface.Meal;
import userInterface.Meals;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;

public class DietMenu extends HBox {
	
	private Boolean click = true;
	private Boolean clickThis = true;
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

	double screenWidth;
	double screenHeight;
	
	
	static Meals mealList = new Meals();
	
	public DietMenu(double screenWidth, double screenHeight) throws JAXBException{
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;
		displayMealList();

	}
	
	private void displayMealList() {
		
		recipeList = new VBox();
		
		ScrollPane mealListPane = new ScrollPane();
		GridPane mealDisplayPane = new GridPane();
		
		Label[] mealInstructionIndexLabel = new Label[5];
		Label[] mealInstructionContentLabel = new Label[5];
		HBox mealListPaneBox = new HBox();
		
		Label[] mealIngredientIndexLabel = new Label[5];
		Label[] mealIngredientContentLabel = new Label[5];
		
		BorderPane mealInfoPane = new BorderPane();
		
		final TextArea[] mealInfoArea = new TextArea[5];
		HBox [] mealInfoAreaBox = new HBox[5];
		
		HBox mealTabsBox = new HBox();
		Button mealIngredientsButton = new Button("Ingredients");
		Button mealInstructionsButton = new Button("Instructions");
		
		for(int i = 0; i<recipes.length; i++){
			Recipes recipeView = new Recipes(screenWidth, screenHeight, recipes[i], meals[i], 
					contains[i], pictureStrings[i]);
			recipeList.getChildren().add(recipeView);
		}
		
		recipeList.setSpacing(screenHeight*0.05);
		
		mealListPane.setContent(recipeList);
			
		mealListPane.setMinWidth(screenWidth*0.42);		
		mealListPane.setMinHeight(screenHeight*0.65);
		
		mealIngredientsButton.setPrefSize(screenWidth*0.1, screenHeight*0.07);
		mealInstructionsButton.setPrefSize(screenWidth*0.1, screenHeight*0.07);
		
		setNodeCursor (mealIngredientsButton);
		setNodeCursor (mealInstructionsButton);
		
		
		
		mealIngredientsButton.setOnAction(new EventHandler<ActionEvent>(){
			
			@Override
			public void handle(ActionEvent event) {
				Label testLabel1 = new Label("Ingredients loaded as a list from xml");
				
				if (clickThis != false) {
					clickThis = false;
					for (int k = 0; k < 5; k++) {
						
						mealIngredientIndexLabel[k] = new Label("Ingredient " + Integer.toString(k+1));
						mealDisplayPane.add(mealIngredientIndexLabel[k], 0, k);
						
						mealIngredientContentLabel[k] = new Label("Ingredient " +
						Integer.toString(k+1) + "amount");
						mealDisplayPane.add(mealIngredientContentLabel[k], 1, k);
						mealDisplayPane.setHgap(screenWidth*0.05);
					}
					mealInfoPane.setCenter(mealDisplayPane);
					
				}
			}
		});
		
		mealInstructionsButton.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent event) {
				Label testLabel2 = new Label("Instructions loaded as a list from xml");
				
				if (clickThis != true) {
					clickThis = true;	
					for (int i = 0; i < 5; i++) {
						mealInstructionIndexLabel[i] = new Label(Integer.toString(i+1));
						mealDisplayPane.add(mealInstructionIndexLabel[i], 0, i);
						mealDisplayPane.setVgap(screenHeight*0.05);
					}
					
					for (int j = 0; j < 5; j++) {
						mealInfoAreaBox[j] = new HBox();
						mealInfoArea[j] = new TextArea("Instruction no. " + Integer.toString(j+1));
						mealInfoArea[j].setWrapText(true);
						mealInfoArea[j].setEditable(false);
						mealInfoAreaBox[j].getChildren().add(mealInfoArea[j]);
						mealInfoAreaBox[j].setMaxSize(screenWidth*0.2, screenHeight*0.1);
						mealDisplayPane.add(mealInfoAreaBox[j], 1, j);
						mealDisplayPane.setHgap(screenWidth*0.05);
					}
					mealInfoPane.setCenter(mealDisplayPane);
				}
			}		
		});
		
		mealListPaneBox.getChildren().add(mealListPane);
		mealTabsBox.getChildren().addAll(mealIngredientsButton, mealInstructionsButton);
		mealTabsBox.setSpacing(screenWidth*0.1);
		
		mealInfoPane.setTop(mealTabsBox);
		//mealInfoPane.setVgap(screenHeight*0.1);
		getChildren().addAll(mealInfoPane, mealListPaneBox);
		setPadding(new Insets(screenHeight*0.05, screenWidth*0.05, screenHeight*0.05, screenWidth*0.05));
		setSpacing(screenWidth*0.1);
	}
	
	public void setNodeCursor (Node node) {
		
		node.setOnMouseEntered(event -> setCursor(Cursor.HAND));
		node.setOnMouseExited(event -> setCursor(Cursor.DEFAULT));
	}
};
