package userInterface;

import java.io.File;
import java.util.ArrayList;
import java.util.Optional;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import diet.Ingredient;
import diet.Instruction;
import diet.Recipe;
import javafx.beans.binding.NumberBinding;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import javafx.stage.Screen;
import javafx.stage.Window;

/**
 * DietMenu presents the user with a filtered list of meals according to their type,
 * displayed on the left hand side of the screen, and the information about the meal 
 * already selected by the user and placed in the weekly diet plan, 
 * displayed on the right hand side of the screen.
 * <p>
 * The filtering is triggered by user's action of pressing a button
 * in the calendar array in DietPlanner.
 * <p>
 * According to the column index of the pressed button, appropriate filtering takes place
 * <p> The class extends SplitPane to separate the recipe list from the selected recipes 
 * instruction and ingredient list.
 * @author Kamil Sledziewski (Initial GUI)
 * @author Oliver Rushton(GUI modifications, integration with Account, background functionality)
 *
 */

public class DietMenu extends SplitPane implements Controllable {

	private static final String imageDir = "res/images/";
	private static final String recipeDir = "src/res/recipes/";
	private static Window theStage;

	private ArrayList<Recipe> allRecipes = new ArrayList<Recipe>();
	private BorderPane rightSide;

	private StackPaneUpdater screenParent;
	private Main mainApp;

	protected static int day = -1, type = -1;

	private int mealIndex;
	private Button addButton;

	private double screenWidth;
	private double screenHeight;


	public DietMenu(double screenWidth, double screenHeight){
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;
		this.setWidth(screenWidth);
		this.setHeight(screenHeight);
		this.setDividerPositions(0.5f,0.5f);
		displayContent();
	}

	public void setDayAndType(int day, int type) {
		DietMenu.day = day;
		DietMenu.type = type;
	}
	/**
	 * Sets the title String to the selected day and meal type.
	 * 
	 * @return the title string for the recipe list
	 */
	private String determineDay() {
		String selectedDay = null;
		String selectedType = null;
		int dayIndex = 0;
		//set dayIndex to be between 0 -> 6
		if (day == -1) {
			dayIndex = -1;
		} else if (day < 7) {
			dayIndex = day;
		} else if (day < 14) {
			dayIndex = day-7;
		} else if (day < 21) {
			dayIndex = day-14;
		}
		//set the meal type String
		if (type == 0) {
			selectedType = "Breakfast";
		} else if (type == 1) {
			selectedType = "Lunch";
		} else if (type == 2) {
			selectedType = "Dinner";
		} 
		//determine the day string
		switch(dayIndex) {
		case 0:
			selectedDay = "Monday's " + selectedType;
			break;
		case 1:
			selectedDay = "Tuesday's " + selectedType;
			break;
		case 2:
			selectedDay = "Wednesday's " + selectedType;
			break;
		case 3:
			selectedDay = "Thursday's " + selectedType;
			break;
		case 4:
			selectedDay = "Friday's " + selectedType;
			break;
		case 5:
			selectedDay = "Saturday's " + selectedType;
			break;
		case 6:
			selectedDay = "Sunday's " + selectedType;
			break;
		case -1:
			//if no specific day selected, set to All Recipes
			selectedDay = "All Recipes";
			break;
		}
		return selectedDay;
	}

		 /**
		 * Adds to and displays the content of the DietMenu screen
		 * Divides the screen into an RHS part, organised within a BorderPane, and
		 * LHS organised within a ScrollPane.
		 * <p>
		 * The BorderPane is used for displaying the information about a meal.
		 * It contains a TabPane that displays either the ingredients or instructions
		 * of selected meal as a response to clicking on one of the two tabs
		 * <p>
		 * The ScrollPane is used for displaying the filtered list of meals
		 */
	private void displayContent() {

		BorderPane leftSide = new BorderPane();
		ScrollPane leftScroll = new ScrollPane();
		leftScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
		rightSide = new BorderPane();
		rightSide.setPadding(new Insets(10));

		NumberBinding sideWidthBind = this.widthProperty().multiply(0.5);
		leftSide.prefWidthProperty().bind(sideWidthBind);
		leftSide.prefHeightProperty().bind(this.heightProperty());

		rightSide.prefWidthProperty().bind(sideWidthBind);
		rightSide.prefHeightProperty().bind(this.heightProperty());
		//to store the day label and title row
		VBox titleBox = new VBox();
		titleBox.setAlignment(Pos.CENTER);
		titleBox.setSpacing(10);
		//titleBox.minHeightProperty().bind(this.heightProperty().multiply(0.1));
		
		addButton = new Button("Add");
		addButton.setMinSize(80, 50);
		addButton.setTextAlignment(TextAlignment.CENTER);

		addButton.setAlignment(Pos.CENTER_LEFT);
		addButton.setOnAction(event -> {

			theStage = screenParent.getScene().getWindow();

			showMealAddedPopup();
		});

		rightSide.setBottom(addButton);
		
		Label dayLabel = null;
		if (!determineDay().contains("null")) {
			dayLabel = new Label(determineDay());
			addButton.setVisible(true);
		} else {
			dayLabel = new Label("All Recipes");
			addButton.setVisible(false);
		}
		
		dayLabel.getStyleClass().add("featureTitle");
		
		titleBox.getChildren().addAll(dayLabel,createTitleRow(leftSide));

		leftSide.setTop(titleBox);
		leftSide.setCenter(leftScroll);

		leftScroll.prefWidthProperty().bind(leftSide.widthProperty());
		//leftScroll.prefHeightProperty().bind(leftSide.heightProperty());

		VBox scrollContent = new VBox();
		populateTable(scrollContent);
		leftScroll.setContent(scrollContent);

		createRightSide(rightSide,allRecipes.get(0).getIndex());

		this.getItems().addAll(leftSide,rightSide);
		this.setDividerPositions(0.5f,0.5f);
		//prevent split pane from resizing
		double screenWidth = Screen.getPrimary().getVisualBounds().getWidth();
		rightSide.setMinWidth((screenWidth-4)/2);
		leftSide.setMinWidth((screenWidth-4)/2);
		rightSide.setMaxWidth((screenWidth-4)/2);
		leftSide.setMaxWidth((screenWidth-4)/2);
		//prevent gridPanes from overshooting
		scrollContent.setMaxWidth((screenWidth-4)/2);
		scrollContent.setMinWidth((screenWidth-10)/2);
		scrollContent.setPadding(new Insets(0,leftScroll.getWidth()*0.05,0,leftScroll.getWidth()*0.05));
		
		//Build back button
		Image goBack = new Image("res/images/backButton.png");
		ImageView back = new ImageView(goBack);
		back.setFitHeight(screenHeight*0.05);
		back.setFitWidth(screenWidth*0.05);		
		Button backButton = new Button("", back);
		setNodeCursor(backButton);
		backButton.setAlignment(Pos.BOTTOM_LEFT);
		backButton.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent event){
				screenParent.setScreen(Main.dietPlannerID);
			}
		});
		leftSide.setBottom(backButton);
	}
	/**
	 * Gets the list of all the recipes.
	 * @return The list of all the recipes.
	 */
	public ArrayList<Recipe> getAllRecipes() {
		return allRecipes;
	}
	
	 /**
	 * Adds the information about the meal added to user's diet plan by referencing the index of a meal
	 * from the Account 
	 */
	private void setDayDiet() {
		System.out.println("Set day is: " + day);
		System.out.println("Set type is: " + type);
		switch (day) {
			case 0:
				if (type == 0)
					Main.account.getDietPlanner().getMonday().setBreakfast(mealIndex);
				else if (type == 1)
					Main.account.getDietPlanner().getMonday().setLunch(mealIndex);
				else if (type == 2) {
					System.out.println("Monday dinner set to: " + mealIndex);
					Main.account.getDietPlanner().getMonday().setDinner(mealIndex);
				}
				break;
			case 1:
				if (type == 0)
					Main.account.getDietPlanner().getTuesday().setBreakfast(mealIndex);
				else if (type == 1)
					Main.account.getDietPlanner().getTuesday().setLunch(mealIndex);
				else if (type == 2)
					Main.account.getDietPlanner().getTuesday().setDinner(mealIndex);
				break;
			case 2:
				if (type == 0)
					Main.account.getDietPlanner().getWednesday().setBreakfast(mealIndex);
				else if (type == 1)
					Main.account.getDietPlanner().getWednesday().setLunch(mealIndex);
				else if (type == 2)
					Main.account.getDietPlanner().getWednesday().setDinner(mealIndex);
				break;
			case 3:
				if (type == 0)
					Main.account.getDietPlanner().getThursday().setBreakfast(mealIndex);
				else if (type == 1)
					Main.account.getDietPlanner().getThursday().setLunch(mealIndex);
				else if (type == 2)
					Main.account.getDietPlanner().getThursday().setDinner(mealIndex);
				break;
			case 4:
				if (type == 0)
					Main.account.getDietPlanner().getFriday().setBreakfast(mealIndex);
				else if (type == 1)
					Main.account.getDietPlanner().getFriday().setLunch(mealIndex);
				else if (type == 2)
					Main.account.getDietPlanner().getFriday().setDinner(mealIndex);
				break;
			case 5:
				if (type == 0)
					Main.account.getDietPlanner().getSaturday().setBreakfast(mealIndex);
				else if (type == 1)
					Main.account.getDietPlanner().getSaturday().setLunch(mealIndex);
				else if (type == 2)
					Main.account.getDietPlanner().getSaturday().setDinner(mealIndex);
				break;
			case 6:
				if (type == 0)
					Main.account.getDietPlanner().getSunday().setBreakfast(mealIndex);
				else if (type == 1)
					Main.account.getDietPlanner().getSunday().setLunch(mealIndex);
				else if (type == 2)
					Main.account.getDietPlanner().getSunday().setDinner(mealIndex);
				break;
		}
	}
	/**
	 * Creates the titles for each column in the recipe list.
	 * 
	 * @param leftSide the container on the left hand side of this classes <code>SplitPane</code>.
	 * @return the title row for the recipe list.
	 */
	private GridPane createTitleRow(BorderPane leftSide) {
		GridPane row = new GridPane();
		row.setHgap(20);
		row.setVgap(10);
		//sets the widths of each column
		ColumnConstraints column1 = new ColumnConstraints();
		NumberBinding columnWidthBind = leftSide.widthProperty().multiply(0.3);
		column1.minWidthProperty().bind(columnWidthBind);
		row.getColumnConstraints().add(column1);
		row.getColumnConstraints().add(column1);
		row.getColumnConstraints().add(column1);
		//creates the labels for each title column
		Label leftTitle = new Label("Recipe List");
		leftTitle.setFont(Font.font("Calibri", FontWeight.BOLD, 16));
		leftTitle.setId("TitleTitle-DM");
		GridPane.setHalignment(leftTitle, HPos.CENTER);
		row.add(leftTitle, 0, 0, 3, 1);

		Label imageLabel = new Label("Image");
		Label nameLabel = new Label("Name");
		Label typeLabel = new Label("Type");

		imageLabel.setId("ImageTitle-DM");
		nameLabel.setId("NameTitle-DM");
		typeLabel.setId("TypeTitle-DM");
		//positions the titles in the center of the column
		GridPane.setHalignment(imageLabel, HPos.CENTER);
		GridPane.setHalignment(nameLabel, HPos.CENTER);
		GridPane.setHalignment(typeLabel, HPos.CENTER);

		row.add(imageLabel, 0, 1);
		row.add(nameLabel, 1, 1);
		row.add(typeLabel, 2, 1);

		return row;
	}
	/**
	 * Creates the row for a recipe in the recipe list with the image name and meal type provided.
	 * 
	 * @param scrollContent the container to add the row to.
	 * @param imageSrc the path to the image file for this particular recipe.
	 * @param title the name of the recipe.
	 * @param type the type of recipe (Breakfast, Lunch or Dinner).
	 * @param index the index of the recipe in the recipe list which the row points to.
	 * @return The constructed recipe row.
	 * @see IndexGridPane
	 */
	private IndexGridPane createRow(VBox scrollContent, String imageSrc, String title, int type, int index) {
		IndexGridPane row = new IndexGridPane(index);
		row.setHgap(20);
		row.setVgap(10);
		//fix the widths of each column
		ColumnConstraints column1 = new ColumnConstraints();
		column1.minWidthProperty().bind(scrollContent.widthProperty().multiply(0.3));
		row.getColumnConstraints().add(column1);
		row.getColumnConstraints().add(column1);
		row.getColumnConstraints().add(column1);

		Image im = new Image(imageDir.concat(imageSrc));
		ImageView imv = new ImageView(im);
		imv.setPreserveRatio(true);
		imv.setSmooth(true);
		//scale the image
		imv.fitWidthProperty().bind(column1.minWidthProperty());
		//identify which meal type label to use
		Label lab1 = new Label(title);
		Label lab2 = null;
		switch (type) {
			case 0:
				lab2 = new Label("Breakfast");
				break;
			case 1:
				lab2 = new Label("Lunch");
				break;
			case 2:
				lab2 = new Label("Dinner");
				break;
		}
		//center the column elements
		GridPane.setHalignment(imv, HPos.CENTER);
		GridPane.setHalignment(lab1, HPos.CENTER);
		GridPane.setHalignment(lab2, HPos.CENTER);

		row.add(imv, 0, 0);
		row.add(lab1, 1, 0);
		row.add(lab2, 2, 0);
		//add the action listener for when this row is clicked.
		addClickListener(row,rightSide);
		
		setNodeCursor(row);

		return row;
	}
	/**
	 * Sets the ingredient/instruction list to display on the right side of the <code>SplitPane</code>
	 * for the selected recipe row from the left hand side.
	 * 
	 * @param row the selected row to add the click action for.
	 * @param bp the container to use to create the right side ingredient/instruction list.
	 */
	private void addClickListener(IndexGridPane row, BorderPane bp) {
		row.setOnMouseClicked(event -> {

			for (Recipe i : allRecipes) {
				if (row.getIndex() == i.getIndex()) {
					bp.getChildren().clear();
					bp.setBottom(addButton);
					addButton.setText("Add");
					mealIndex = row.getIndex();
					//create the ingredient/instruction list
					createRightSide(bp,row.getIndex());
				}
			}
		});
	}
	/**
	 * Fills the recipe list with recipes.
	 * 
	 * @param scrollContent the container to add the recipe information to.
	 */
	private void populateTable(VBox scrollContent) {
		//obtains the selected meal type recipes only, unless all recipes are to be displayed.
		allRecipes = createRecipeList();

		for (Recipe i : allRecipes) {
			scrollContent.getChildren().add(createRow(scrollContent,i.getImageFile(),i.getMealName(),i.getMealType(),i.getIndex()));
		}

	}
	/**
	 * Filters meals from the recipe directory based on the selected meal type.
	 * If the type has not been selected, then displays all the recipes in the list.
	 * 
	 * @return A list of the filtered recipes.
	 */
	private ArrayList<Recipe> createRecipeList() {
		ArrayList<Recipe> recipeList = new ArrayList<Recipe>();
		File dir = new File(recipeDir);
		File[] fileList = dir.listFiles();
		//iterate through each file in the recipe directory
		for (File i : fileList) {
			if (i.exists() && i.isFile()) {
				//load each recipe XML into a Recipe object
				Recipe tempRecipe = loadRecipe(i);
				if (tempRecipe != null) {
				//determine whether to add to the list based on the selected meal type.
					if (DietMenu.type == -1) {
						recipeList.add(tempRecipe);
					} else if (tempRecipe.getMealType() == DietMenu.type) {
						recipeList.add(tempRecipe);
					}
				}
			}
		}
		return recipeList;
	}
	/**
	 * Loads XML recipe data from the source file provided into a Recipe object.
	 * 
	 * @param sourceFile the XML recipe file to load.
	 * @return The object representation of the recipe XML.
	 */
	private Recipe loadRecipe(File sourceFile) {
		Recipe rec = null;
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(Recipe.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			rec = (Recipe) jaxbUnmarshaller.unmarshal(sourceFile);
		} catch (JAXBException e) {
			return null;
		}
		return rec;
	}
	/**
	 * Creates a confirmation alert to ask the user if they would like to add the
	 * selected recipe to their diet planner. 
	 */
	public void showMealAddedPopup() {

		Alert alert = new Alert(Alert.AlertType.CONFIRMATION) ;
		alert.initOwner(theStage);
		alert.setTitle("Add this meal?");
		alert.setHeaderText("Do you want to add this meal to your diet planner?");

		Optional<ButtonType> result = alert.showAndWait();

		if (result.get() == ButtonType.OK) {
			addButton.setText("Added");
			setDayDiet();
			screenParent.loadDietPlanner();
			screenParent.setScreen(Main.dietPlannerID);
		}
		if (result.get() == ButtonType.CANCEL) {
			addButton.setText("Add");
			alert.close();
		}
	}

	/**
	 * Creates a Tab populated with the recipe information indexed by recipeIndex.
	 * @param recipeIndex the index of the recipe in the recipe list.
	 */
	private void createRightSide(BorderPane rightSide, int recipeIndex) {

		TabPane recipeTab = new TabPane();
		//prevents tabs from being closable.
		recipeTab.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
		//set the dimensions of the tab pane
		recipeTab.maxWidthProperty().bind(rightSide.widthProperty().multiply(0.8));
		recipeTab.maxHeightProperty().bind(rightSide.heightProperty().multiply(0.8));

		Tab instructionTab = new Tab();
		Tab ingredientTab = new Tab();

		ScrollPane instructScroll = new ScrollPane();
		ScrollPane ingredScroll = new ScrollPane();
		//sets the dimensions of the insctruction and ingredient content
		VBox instructContent = new VBox();
		instructContent.setPadding(new Insets(10));
		instructContent.setSpacing(20);
		instructContent.prefWidthProperty().bind(recipeTab.widthProperty().multiply(0.9));
		instructContent.maxWidthProperty().bind(recipeTab.widthProperty().multiply(0.9));

		VBox ingredContent = new VBox();
		ingredContent.setPadding(new Insets(10));
		ingredContent.setSpacing(20);
		ingredContent.prefWidthProperty().bind(recipeTab.widthProperty().multiply(0.9));
		ingredContent.maxWidthProperty().bind(recipeTab.widthProperty().multiply(0.9));
		//finds the selected recipe object from the recipe list
		Recipe selectedRecipe = new Recipe();
		for (Recipe i : allRecipes) {
			if (i.getIndex() == recipeIndex) {
				selectedRecipe = i;
				break;
			}
		}
		//adds the instructions to the instruction list
		for (Instruction j : selectedRecipe.getInstructions()) {
			instructContent.getChildren().add(createInstruction(instructContent,j));
		}
		//adds the ingredients to the ingredient list
		for (Ingredient k : selectedRecipe.getIngredients()) {
			ingredContent.getChildren().add(createIngredient(ingredContent,k));
		}

		instructScroll.setContent(instructContent);
		ingredScroll.setContent(ingredContent);

		instructionTab.setText("Method");
		ingredientTab.setText("Ingredients");

		instructionTab.setContent(instructScroll);
		ingredientTab.setContent(ingredScroll);

		recipeTab.getTabs().addAll(instructionTab,ingredientTab);
		Label mealTitle = new Label(selectedRecipe.getMealName());
		mealTitle.getStyleClass().add("featureTitle");
		BorderPane.setAlignment(mealTitle, Pos.CENTER);
		rightSide.setTop(mealTitle);
		rightSide.setCenter(recipeTab);
	}
	/**
	 * Creates the Ingredient row to display in the ingredients tab.
	 * 
	 * @param ingredContent the container to add the ingredient information to - used for dimensions.
	 * @param ingred the ingredient to add to the container.
	 * @return the constructed ingredient row to add to the container.
	 */
	private GridPane createIngredient(VBox ingredContent, Ingredient ingred) {
		GridPane gp = new GridPane();
		gp.setHgap(10);
		gp.setVgap(10);

		ColumnConstraints column1 = new ColumnConstraints();
		column1.minWidthProperty().bind(ingredContent.widthProperty().multiply(0.7));
		ColumnConstraints column2 = new ColumnConstraints();
		column2.minWidthProperty().bind(ingredContent.widthProperty().multiply(0.2));
		gp.getColumnConstraints().addAll(column1,column2);

		Label ingredName = new Label(ingred.getIngredientName());
		ingredName.getStyleClass().add("mediumText");
		Label ingredQuant = new Label(ingred.getQuantity());
		ingredQuant.getStyleClass().add("mediumText");

		GridPane.setHalignment(ingredQuant,HPos.CENTER);
		GridPane.setHalignment(ingredName, HPos.CENTER);

		gp.add(ingredName, 0, 0);
		gp.add(ingredQuant, 1, 0);

		return gp;
	}
	/**
	 * Creates the instruction row to display in the instructions tab.
	 * 
	 * @param instructContent the container to which the instruction will be added
	 * @param instruct the instruction to add to the instructions tab list
	 * @return The constructed instruction row to add to the container.
	 */
	private GridPane createInstruction(VBox instructContent, Instruction instruct) {
		GridPane gp = new GridPane();
		gp.setHgap(10);
		gp.setVgap(10);

		ColumnConstraints column1 = new ColumnConstraints();
		column1.minWidthProperty().bind(instructContent.widthProperty().multiply(0.1));
		ColumnConstraints column2 = new ColumnConstraints();
		column2.minWidthProperty().bind(instructContent.widthProperty().multiply(0.9));
		gp.getColumnConstraints().addAll(column1,column2);

		Label stepNumber = new Label(Integer.toString(instruct.getIndex()));
		Text instructText = new Text();
		instructText.getStyleClass().add("mediumText");
		instructText.setText(instruct.getStep());
		TextFlow instructContain = new TextFlow(instructText);

		GridPane.setHalignment(stepNumber, HPos.CENTER);
		GridPane.setHalignment(instructContain, HPos.LEFT);

		gp.add(stepNumber, 0, 0);
		gp.add(instructContain, 1, 0);

		return gp;
	}
	/**
	 * Sets mouse icon to be the click icon when a mouse is hovered over the graphical element.
	 * @param node the graphical element to add this functionality to.
	 */
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

};
