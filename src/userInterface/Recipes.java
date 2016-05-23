package userInterface;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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
	
	//Label nameLabel, typeLabel;
	
	
	//String name = meal.getName();
	//String imagePath = meal.getImgLoc();
	//String type = meal.getType();
	//int id = meal.getMealId();
	
	static Meals meals = new Meals();
	static {
		
		meals.setMeals(new ArrayList<Meal>());
		
		Meal granola = new Meal();
		granola.setMealId(0);
		granola.setName("Granola");
		granola.setImgLoc("granola.JPG");
		granola.setType("Lunch");
		
		Meal bananaSmoothie = new Meal();
		bananaSmoothie.setMealId(1);
		bananaSmoothie.setName("Peanut Butter Banana Smoothie");
		bananaSmoothie.setImgLoc("pbsmoothie.jpg");
		bananaSmoothie.setType("Breakfast");
		
		Meal tortillaLessSoup = new Meal();
		tortillaLessSoup.setMealId(2);
		tortillaLessSoup.setName("Tortilla-less soup");
		tortillaLessSoup.setImgLoc("tlsoup.jpg");
		tortillaLessSoup.setType("Lunch");
		
		Meal vegCouscous = new Meal();
		vegCouscous.setMealId(3);
		vegCouscous.setName("Vegetarian Couscous");
		vegCouscous.setImgLoc("vcouscous.jpg");
		vegCouscous.setType("Dinner");
		
		Meal proteinCookies = new Meal();
		proteinCookies.setMealId(4);
		proteinCookies.setName("Peanut Butter Protein Cookies");
		proteinCookies.setImgLoc("pbcookies.jpg");
		proteinCookies.setType("Lunch");
		
		meals.getMeals().add(granola);
		meals.getMeals().add(bananaSmoothie);
		meals.getMeals().add(tortillaLessSoup);
		meals.getMeals().add(vegCouscous);
		meals.getMeals().add(proteinCookies);
		
	}
	
		

	
	
	/*public Recipes(double screenWidth, double screenHeight) throws JAXBException {
		
		unmarshallMealInfo();
		
		Meal meal = new Meal();
		String name = meal.getName();
		//String imagePath = meal.getImgLoc();
		String type = meal.getType();
		//int id = meal.getMealId();*/
	public Recipes (double screenWidth, double screenHeight, String recipe, String meal,
			String contents, String pictureString){
		//nameLabel = new Label(name);
		//nameLabel.setMinWidth(screenWidth*0.1);
		recipeName = new Label(recipe);
		recipeName.setMinWidth(screenWidth*0.2);
		
		/*mealName = new Label(name);
		mealName.setMinWidth(screenWidth*0.1);*/
		
		/*typeLabel = new Label(type);
		typeLabel.setMinWidth(screenWidth*0.2);*/
		
		
		recipeContains = new Label (contents);
		recipeContains.setMinWidth(screenWidth*0.2);
		
		//picture = new Image(imagePath);
		
		picture = new Image(pictureString);
		
		viewRecipe = new Button("VIEW");
		viewRecipe.setPrefSize(screenWidth*0.07, screenHeight*0.05);
		setButtonCursor(viewRecipe);
		
		recipePicture = new ImageView(picture);
		recipePicture.setFitWidth(screenWidth*0.1);
		recipePicture.setFitHeight(screenWidth*0.1);
		
		
		//getChildren().addAll(nameLabel, typeLabel, viewRecipe);
		getChildren().addAll(recipePicture, recipeName, recipeContains, 
				viewRecipe);
		setWidth(screenWidth*0.8);
		setHeight(screenHeight*0.2);
		setSpacing(screenWidth*0.025);
	}
	
	public void setButtonCursor (Button button) {
		button.setOnMouseEntered(event -> setCursor(Cursor.HAND));
		button.setOnMouseExited(event -> setCursor(Cursor.DEFAULT));
	}
	
	/*public void displayMealInfo() throws JAXBException {
		unmarshallMealInfo();
		System.out.println(name);
		System.out.println(imagePath);
		System.out.println(type);
	}*/
	
	static void marshallMealInfo() throws JAXBException {
		
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(Meals.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			jaxbMarshaller.marshal(meals, new File("src/res/xml/meals.xml"));
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}
	
	/*public static void unmarshallMealInfo(String[] names, String types[]) throws JAXBException{
		try {
			int i = 0;
			JAXBContext jc = JAXBContext.newInstance(Meals.class);
			Unmarshaller u = jc.createUnmarshaller();
			
			Meals meals = (Meals) u.unmarshal(new File("src/res/xml/meals.xml"));
			
			for (Meal meal: meals.getMeals()) {
				names[i] = meal.getName();
				types[i] = meal.getType();
				//System.out.println(meal.getName());
				//System.out.println(meal.getType());
				
				System.out.println(names[i]);
				System.out.println(types[i]);
				
				System.out.println(i);
				i++;
			}
				
		}
		catch (JAXBException e) {
			e.printStackTrace();
		}
	}*/
	
}
