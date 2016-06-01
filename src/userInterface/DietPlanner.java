package userInterface;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import account.DietCalender;
import diet.Recipe;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;

public class DietPlanner extends VBox implements Controllable{
	private StackPaneUpdater screenParent;
	private Main mainApp;

	private static final String clientDir = "src/res/clientAccounts/";
	private static final String recipeDir = "src/res/recipes/";
	//Recipes mealView = new Recipes(_height, _height, null, null, null, null);

	private IndexButton[] btns = new IndexButton[21];
	private Label [] dayLabels = new Label[7];
	private Label[] mealTypeLabels = new Label[3];

	DietPlanner (double screenWidth, double screenHeight) {

		int xCoor = 0;
		int yCoor = 0;
		int k = 0;

		Label introLabel = new Label("Click on one of the buttons to select the day and type of a meal");

		GridPane calendarPane = new GridPane();

		initializeButtons();
		addDayLabels();
		addMealTypeLabels();

		for (yCoor = 1; yCoor < 8; yCoor++) {
			calendarPane.add(dayLabels[yCoor-1], xCoor, yCoor);
			calendarPane.setVgap(10);
		}

		yCoor = 0;

		for (xCoor = 1; xCoor < 4; xCoor++) {
			calendarPane.add(mealTypeLabels[xCoor-1], xCoor, yCoor);
		}

		for (xCoor = 1; xCoor < 4; xCoor++) {
			for(yCoor = 1; yCoor < 8; yCoor++) {
				calendarPane.add(btns[k], xCoor, yCoor);
				btns[k].setMinSize(screenWidth*0.15, screenHeight*0.07);
				btns[k].setMaxSize(screenWidth*0.15, screenHeight*0.07);
				btns[k].setTextAlignment(TextAlignment.CENTER);
				//btns[k].getStyleClass().add("calenderButton");
				setNodeCursor(btns[k]);
				btns[k].setOnAction(new EventHandler<ActionEvent>(){

					public void handle (ActionEvent event) {
						int i = ((IndexButton) event.getSource()).getIndex();
						if (i >= 0 && i <=6) {
							DietMenu.type = 0;
							DietMenu.day = i;
						} else if (i >= 7 && i <= 13) {
							DietMenu.type = 1;
							DietMenu.day = i-7;
						} else if (i >=14 && i <= 20) {
							DietMenu.type = 2;
							DietMenu.day = i-14;
						}
						System.out.println(DietMenu.type);
						System.out.println(DietMenu.day);

						screenParent.loadDietMenu();
						screenParent.setScreen(Main.dietMenuID);
					}
				});
				k++;
			}
		}


		setPadding(new Insets(screenHeight*0.05, screenWidth*0.15, screenHeight*0.05, screenWidth*0.3));
		setSpacing(10);
		getChildren().addAll(introLabel, calendarPane);
		
		Button viewAllRecipes = new Button("View All Recipes");
		viewAllRecipes.setMinSize(150, 40);
		viewAllRecipes.setMaxSize(150, 40);
		viewAllRecipes.setAlignment(Pos.CENTER);
		viewAllRecipes.setOnAction(event -> {
			DietMenu.type = -1;
			screenParent.loadDietMenu();
			screenParent.setScreen(Main.dietMenuID);
		});
		getChildren().add(viewAllRecipes);
	}

	public void addButtons() {
		DietCalender calender = Main.account.getDietPlanner();
		System.out.println(calender.getFriday());
		//Set Breakfasts
		setButtonName(0,calender.getMonday().getBreakfast());
		setButtonName(1,calender.getTuesday().getBreakfast());
		setButtonName(2,calender.getWednesday().getBreakfast());
		setButtonName(3,calender.getThursday().getBreakfast());
		setButtonName(4,calender.getFriday().getBreakfast());
		setButtonName(5,calender.getSaturday().getBreakfast());
		setButtonName(6,calender.getSunday().getBreakfast());
		//Set Lunches
		setButtonName(7,calender.getMonday().getLunch());
		setButtonName(8,calender.getTuesday().getLunch());
		setButtonName(9,calender.getWednesday().getLunch());
		setButtonName(10,calender.getThursday().getLunch());
		setButtonName(11,calender.getFriday().getLunch());
		setButtonName(12,calender.getSaturday().getLunch());
		setButtonName(13,calender.getSunday().getLunch());
		//Set Dinners
		setButtonName(14,calender.getMonday().getDinner());
		setButtonName(15,calender.getTuesday().getDinner());
		setButtonName(16,calender.getWednesday().getDinner());
		setButtonName(17,calender.getThursday().getDinner());
		setButtonName(18,calender.getFriday().getDinner());
		setButtonName(19,calender.getSaturday().getDinner());
		setButtonName(20,calender.getSunday().getDinner());

	}

	private void initializeButtons() {
		for (int i = 0; i < btns.length; i++) {
			setButtonName(i,-2);
		}
	}

	private void setButtonName(int index, int meal) {
		if (meal == -2) {
			btns[index] = new IndexButton("Empty", index);
		} else if (meal != -1) {
			String temp = loadRecipe(meal);
			if (temp != null) {
				System.out.println("Name of recipe is at index " + index + ", is: " + temp);
				btns[index].setText(temp);
			} else {
				System.out.println("Could not find a recipe xml with name index: " + meal);
				btns[index] = new IndexButton("Empty", index);
			}
		} else {
			System.out.println("Meal is empty");
			btns[index] = new IndexButton("Empty", index);
		}
		
		if(index >= 0 && index <= 6) {
			//btns[index].setId("BreakfastButtons");
			btns[index].getStyleClass().add("calenderButton0");
		}
		
		if(index >= 7 && index <= 13) {
			//btns[index].setId("LunchButtons");
			btns[index].getStyleClass().add("calenderButton1");
		}
		
		if(index >= 14 && index <= 20) {
			//btns[index].setId("DinnerButtons");
			btns[index].getStyleClass().add("calenderButton2");
		}
		btns[index].wrapTextProperty().setValue(true);
	}

	private String loadRecipe(int index) {
		String recipeName = null;
		File dir = new File(recipeDir);
		if (dir.exists() && dir.isDirectory()) {
			for (File i : dir.listFiles()) {
				try {
					JAXBContext jaxbContext = JAXBContext.newInstance(Recipe.class);
					Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
					Recipe temp = (Recipe) jaxbUnmarshaller.unmarshal(i);
					if (temp.getIndex() == index)
						recipeName = temp.getMealName();
				} catch (JAXBException jaxbe) {
					jaxbe.printStackTrace();
				}

			}
		}
		return recipeName;
	}

	public void addDayLabels() {

		String [] days = {
				"Mon",
				"Tue",
				"Wed",
				"Thu",
				"Fri",
				"Sat",
				"Sun"
		};

		for(int i = 0; i < dayLabels.length; i++) {
			dayLabels[i] = new Label(days[i]);
			dayLabels[i].setId("DayLabel");
		}

	}
	
	String[] mealTypeLabelIDs = {
		"BreakfastLabel",
		"LunchLabel",
		"DinnerLabel"
	};

	public void addMealTypeLabels() {

		String[] mealTypes = {
				"Breakfast",
				"Lunch",
				"Dinner"
		};
		for (int i = 0; i < mealTypeLabels.length; i++) {
			mealTypeLabels[i] = new Label(mealTypes[i]);
			mealTypeLabels[i].setId(mealTypeLabelIDs[i]);
		}

	}

	public void setNodeCursor (Node node) {
		node.setOnMouseEntered(event -> setCursor(Cursor.HAND));
		node.setOnMouseExited(event -> setCursor(Cursor.DEFAULT));
	}

	@Override
	public void setScreenParent(StackPaneUpdater screenParent) {
		this.screenParent = screenParent;
	}

	@Override
	public void setMainApp(Main mainApp) {
		this.mainApp = mainApp;

	}

}

