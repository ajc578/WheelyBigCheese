package userInterface;

import java.io.File;
import java.util.ArrayList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

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
	
	/*static Meals meals = new Meals();
	static {
		
		meals.setMeals(new ArrayList<Meal>());
		
		Meal granola = new Meal();
		granola.setMealId(0);
		granola.setName("granola");
		granola.setImgLoc("res/images/granola.JPG");
		granola.setType(1);
		
		Meal bananaSmoothie = new Meal();
		bananaSmoothie.setMealId(1);
		bananaSmoothie.setName("Peanut Butter Banana Smoothie");
		bananaSmoothie.setImgLoc("res/images/pbsmoothie.jpg");
		bananaSmoothie.setType(0);
		
		Meal tortillaLessSoup = new Meal();
		tortillaLessSoup.setMealId(2);
		tortillaLessSoup.setName("Tortilla-less soup");
		tortillaLessSoup.setImgLoc("res/images/tlsoup.jpg");
		tortillaLessSoup.setType(1);
		
		Meal vegCouscous = new Meal();
		vegCouscous.setMealId(3);
		vegCouscous.setName("Vegetarian Couscous");
		vegCouscous.setImgLoc("res/images/vcouscous.jpg");
		vegCouscous.setType(2);
		
		Meal proteinCookies = new Meal();
		proteinCookies.setMealId(4);
		proteinCookies.setName("Peanut Butter Protein Cookies");
		proteinCookies.setImgLoc("res/images/pbcookies.jpg");
		proteinCookies.setType(1);
		
		meals.getMeals().add(granola);
		meals.getMeals().add(bananaSmoothie);
		meals.getMeals().add(tortillaLessSoup);
		meals.getMeals().add(vegCouscous);
		meals.getMeals().add(proteinCookies);
	}*/
	
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
	
	/*static void marshallMealInfo() throws JAXBException {
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(Meals.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			
			jaxbMarshaller.marshal(meals, new File("C:/Users/Kamil/workspace/XMLMealTest/meals.xml"));
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}
	
	public static void unmarshallMealInfo() throws JAXBException{
		try {
			JAXBContext jc = JAXBContext.newInstance(Meals.class);
			Unmarshaller u = jc.createUnmarshaller();
			
			Meals meals = (Meals) u.unmarshal(new File("res/xml/meals.xml"));
			
			for (Meal meal: meals.getMeals()) {
				System.out.println(meal.getType());
			}
				
		}
		catch (JAXBException e) {
			e.printStackTrace();
		}
	}*/
	
}
