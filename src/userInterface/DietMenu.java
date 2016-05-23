package userInterface;

import java.io.File;
import java.util.ArrayList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import account.DayDiet;
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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;


public class DietMenu extends SplitPane implements Controllable {

	private static final String imageDir = "res/images/";
	private static final String recipeDir = "src/res/recipes/";

	private ArrayList<Recipe> allRecipes = new ArrayList<Recipe>();
	private BorderPane rightSide;

	private ScreenFlowController screenParent;
	private Main mainApp;

	protected static int day, type;

	private int mealIndex;
	private Button addButton;

	private Boolean click = true;
	private Boolean clickThis;
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

	public DietMenu(double screenWidth, double screenHeight){
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;
		this.setWidth(screenWidth);
		this.setHeight(screenHeight);
		this.setDividerPositions(0.5f,0.5f);
		//displayMealList();
		displayContent();

	}

	public void setDayAndType(int day, int type) {
		this.day = day;
		this.type = type;
	}

	private void displayContent() {

		BorderPane leftSide = new BorderPane();
		ScrollPane rightScroll = new ScrollPane();
		rightSide = new BorderPane();
		rightSide.setPadding(new Insets(10));

		NumberBinding sideWidthBind = this.widthProperty().multiply(0.5);
		leftSide.prefWidthProperty().bind(sideWidthBind);
		leftSide.prefHeightProperty().bind(this.heightProperty());

		rightSide.prefWidthProperty().bind(sideWidthBind);
		rightSide.prefWidthProperty().bind(this.heightProperty());

		leftSide.setTop(createTitleRow(leftSide));
		leftSide.setCenter(rightScroll);

		rightScroll.prefWidthProperty().bind(leftSide.widthProperty());
		NumberBinding scrollHeightBind = leftSide.heightProperty().multiply(0.8);
		rightScroll.prefHeightProperty().bind(leftSide.heightProperty());

		VBox scrollContent = new VBox();
		scrollContent.minHeightProperty().bind(scrollHeightBind);
		scrollContent.minWidthProperty().bind(leftSide.widthProperty());

		populateTable(scrollContent);
		System.out.println(rightScroll.getWidth()*0.05);
		scrollContent.setPadding(new Insets(0,rightScroll.getWidth()*0.05,0,rightScroll.getWidth()*0.05));

		rightScroll.setContent(scrollContent);

		addButton = new Button("Add");
		addButton.setMinSize(80, 50);
		addButton.setTextAlignment(TextAlignment.CENTER);

		addButton.setAlignment(Pos.CENTER_LEFT);
		addButton.setOnAction(event -> {
			setDayDiet();
			addButton.setText("Added");
		});

		rightSide.setBottom(addButton);

		createRightSide(rightSide,allRecipes.get(0).getIndex());

		this.getItems().addAll(leftSide,rightSide);
		this.setDividerPositions(0.5f,0.5f);

	}

	public ArrayList<Recipe> getAllRecipes() {
		return allRecipes;
	}

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

	private GridPane createTitleRow(BorderPane leftSide) {
		GridPane row = new GridPane();
		row.setHgap(20);
		row.setVgap(10);

		ColumnConstraints column1 = new ColumnConstraints();
		NumberBinding columnWidthBind = leftSide.widthProperty().multiply(0.3);
		column1.minWidthProperty().bind(columnWidthBind);
		row.getColumnConstraints().add(column1);
		row.getColumnConstraints().add(column1);
		row.getColumnConstraints().add(column1);

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

		GridPane.setHalignment(imageLabel, HPos.CENTER);
		GridPane.setHalignment(nameLabel, HPos.CENTER);
		GridPane.setHalignment(typeLabel, HPos.CENTER);

		row.add(imageLabel, 0, 1);
		row.add(nameLabel, 1, 1);
		row.add(typeLabel, 2, 1);

		return row;
	}

	private IndexGridPane createRow(VBox scrollContent, String imageSrc, String title, int type, int index) {
		IndexGridPane row = new IndexGridPane(index);
		row.setHgap(20);
		row.setVgap(10);
		ColumnConstraints column1 = new ColumnConstraints();
		column1.minWidthProperty().bind(scrollContent.widthProperty().multiply(0.3));
		row.getColumnConstraints().add(column1);
		row.getColumnConstraints().add(column1);
		row.getColumnConstraints().add(column1);

		System.out.println(imageSrc);
		Image im = new Image(imageDir.concat(imageSrc));
		ImageView imv = new ImageView(im);
		imv.setPreserveRatio(true);
		imv.setSmooth(true);

		imv.fitWidthProperty().bind(column1.minWidthProperty());

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

		GridPane.setHalignment(imv, HPos.CENTER);
		GridPane.setHalignment(lab1, HPos.CENTER);
		GridPane.setHalignment(lab2, HPos.CENTER);

		row.add(imv, 0, 0);
		row.add(lab1, 1, 0);
		row.add(lab2, 2, 0);

		addClickListener(row,rightSide);

		return row;
	}

	private void addClickListener(IndexGridPane row, BorderPane bp) {
		row.setOnMouseClicked(event -> {

			for (Recipe i : allRecipes) {
				if (row.getIndex() == i.getIndex()) {
					bp.getChildren().clear();
					bp.setBottom(addButton);
					//TODO set recipe index here
					addButton.setText("Add");
					mealIndex = row.getIndex();
					createRightSide(bp,row.getIndex());
				}
			}
		});
	}

	private void populateTable(VBox scrollContent) {
		allRecipes = createRecipeList();

		for (Recipe i : allRecipes) {
			scrollContent.getChildren().add(createRow(scrollContent,i.getImageFile(),i.getMealName(),i.getMealType(),i.getIndex()));
		}

	}

	private ArrayList<Recipe> createRecipeList() {
		ArrayList<Recipe> recipeList = new ArrayList<Recipe>();
		File dir = new File(recipeDir);
		File[] fileList = dir.listFiles();
		for (File i : fileList) {
			if (i.exists() && i.isFile()) {
				recipeList.add(loadRecipe(i));
			}
		}
		return recipeList;
	}

	private Recipe loadRecipe(File sourceFile) {
		Recipe rec = null;
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(Recipe.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			rec = (Recipe) jaxbUnmarshaller.unmarshal(sourceFile);
		} catch (JAXBException e) {
			System.out.println("The file could not be parsed - DM");
			e.printStackTrace();
		}
		return rec;
	}

	/**
	 * Creates a Tab populated with the recipe information indexed by recipeIndex.
	 * @param recipeIndex
	 */
	private void createRightSide(BorderPane rightSide, int recipeIndex) {

		TabPane recipeTab = new TabPane();
		recipeTab.maxWidthProperty().bind(rightSide.widthProperty().multiply(0.8));
		recipeTab.maxHeightProperty().bind(rightSide.heightProperty().multiply(0.8));

		Tab instructionTab = new Tab();
		Tab ingredientTab = new Tab();

		ScrollPane instructScroll = new ScrollPane();
		ScrollPane ingredScroll = new ScrollPane();

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

		Recipe selectedRecipe = new Recipe();
		for (Recipe i : allRecipes) {
			if (i.getIndex() == recipeIndex) {
				selectedRecipe = i;
				break;
			}
		}

		for (Instruction j : selectedRecipe.getInstructions()) {
			instructContent.getChildren().add(createInstruction(instructContent,j));
		}

		System.out.println(instructContent.getChildren().size());

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

		rightSide.setCenter(recipeTab);
	}

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
		Label ingredQuant = new Label(ingred.getQuantity());

		GridPane.setHalignment(ingredQuant,HPos.CENTER);
		GridPane.setHalignment(ingredName, HPos.CENTER);

		gp.add(ingredName, 0, 0);
		gp.add(ingredQuant, 1, 0);

		return gp;
	}

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
		instructText.setText(instruct.getStep());
		TextFlow instructContain = new TextFlow(instructText);

		GridPane.setHalignment(stepNumber, HPos.CENTER);
		GridPane.setHalignment(instructContain, HPos.LEFT);

		gp.add(stepNumber, 0, 0);
		gp.add(instructContain, 1, 0);

		return gp;
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
				clickThis = true;
				if (clickThis != false) {
					clickThis = false;
					mealDisplayPane.getChildren().clear();
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
				clickThis = false;
				if (clickThis != true) {
					clickThis = true;
					mealDisplayPane.getChildren().clear();
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
		mealDisplayPane.setPadding(new Insets(screenHeight*0.05, 0, screenHeight*0.05, 0));
		mealListPaneBox.getChildren().add(mealListPane);
		mealTabsBox.getChildren().addAll(mealIngredientsButton, mealInstructionsButton);
		mealTabsBox.setSpacing(screenWidth*0.1);

		mealInfoPane.setTop(mealTabsBox);
		//mealInfoPane.setVgap(screenHeight*0.1);
		getChildren().addAll(mealInfoPane, mealListPaneBox);
		setPadding(new Insets(screenHeight*0.05, screenWidth*0.05, screenHeight*0.05, screenWidth*0.05));
		//setSpacing(screenWidth*0.1);
	}

	public void setNodeCursor (Node node) {

		node.setOnMouseEntered(event -> setCursor(Cursor.HAND));
		node.setOnMouseExited(event -> setCursor(Cursor.DEFAULT));
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
