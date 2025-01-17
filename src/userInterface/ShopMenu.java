package userInterface;

import java.io.File;
import java.util.ArrayList;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
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
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * Class that displays the interface for buying items with Gainz
 * 
 * <p> <STRONG> Developed by </STRONG> <p>
 * Jennifer Thorpe, Henry Nash, Oliver Rushton
 * <p> <STRONG> Developed for </STRONG> <p>
 * BOSS
 * @author - company - B.O.S.S
 * @author - coders - Jennifer Thorpe, Henry Nash, Oliver Rushton
 */

public class ShopMenu extends BorderPane implements Controllable {

	private static final String imageDir = "res/images/";
	private static final String recipeDir = "src/res/recipes/";
	private ArrayList<Recipe> allRecipes = new ArrayList<Recipe>();
	private StackPaneUpdater screenParent;
	private Main mainApp;
	private int gainz = 69;
	private Button buyButton, equipButton;
	
	VBox recipeList;

	double screenWidth;
	double screenHeight;

/**
 * Main method
 * @param screenWidth current width of the screen in pixels
 * @param screenHeight current height of the screen in pixels
 */
	public ShopMenu(double screenWidth, double screenHeight){
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;
		this.setWidth(screenWidth);
		this.setHeight(screenHeight);
		displayContent();

	}

	/**
	 * Makes the back button for returning to the Character Menu
	 * 
	 * @param screenWidth The width in pixels of the screen
	 * @param screenHeight The height in pixels of the screen
	 * 
	 * @return backBox returns the event handler so it can be recognised by the main method
	 */
	public HBox putBackImageButton (double screenWidth, double screenHeight) {
		
		HBox backBox = new HBox();
		backBox.setAlignment(Pos.BOTTOM_LEFT);
		
		Image backButton = new Image("res/images/backButton.png");
		ImageView buttonImageView = new ImageView(backButton);
		buttonImageView.setImage(backButton);
		buttonImageView.setFitWidth(screenWidth*0.05);
		buttonImageView.setFitHeight(screenHeight*0.05);
		
		buttonImageView.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				screenParent.setScreen(Main.loginID); //Currently sends user to incorrect screen
			}
			
		});
		
		buttonImageView.setOnMouseEntered(event -> setCursor(Cursor.HAND));
		
		backBox.getChildren().add(buttonImageView);
		return backBox;
		
	}

	/**
	 * Method for displaying everything on the screen
	 */
	private void displayContent() {

		BorderPane shopBox = new BorderPane();
		ScrollPane itemScroll = new ScrollPane();
		BorderPane container = new BorderPane();
		Label title = new Label("Shop");
		title.setFont(Font.font("Calibri", FontWeight.BOLD, 16));

		NumberBinding sideWidthBind = this.widthProperty().multiply(0.5);
		shopBox.prefWidthProperty().bind(sideWidthBind);
		shopBox.prefHeightProperty().bind(this.heightProperty());

		itemScroll.setMinWidth(screenWidth*0.5);		
		itemScroll.setMinHeight(screenHeight*0.7);
		itemScroll.setMaxWidth(screenWidth*0.5);		
		itemScroll.setMaxHeight(screenHeight*0.7);
		itemScroll.setHbarPolicy(ScrollBarPolicy.NEVER);
		
		shopBox.setTop(createTitleRow(shopBox));
		shopBox.setCenter(itemScroll);

		itemScroll.prefWidthProperty().bind(shopBox.widthProperty());
		NumberBinding scrollHeightBind = shopBox.heightProperty().multiply(0.8);
		itemScroll.prefHeightProperty().bind(shopBox.heightProperty());

		VBox scrollContent = new VBox();
		scrollContent.minHeightProperty().bind(scrollHeightBind);
		scrollContent.minWidthProperty().bind(shopBox.widthProperty());

		populateTable(scrollContent);
		System.out.println(itemScroll.getWidth()*0.05);
		scrollContent.setPadding(new Insets(0,itemScroll.getWidth()*0.05,0,itemScroll.getWidth()*0.05));

		itemScroll.setContent(scrollContent);
		
		container.setCenter(shopBox);
		container.setAlignment(shopBox, Pos.CENTER);
	    container.setMargin(shopBox, new Insets(12,12,12,12));
		container.setBottom(buttons(gainz));
		container.setLeft(putBackImageButton(screenWidth, screenHeight));
//		container.setTop(title);
		
		getChildren().addAll(container);

	}

	//Gets and returns the list of all recipes
	public ArrayList<Recipe> getAllRecipes() {
		return allRecipes;
	}

	/**
	 * Creates the titles for each column in the recipe list.
	 * @return the title row for the recipe list.
	 */
	private GridPane createTitleRow(BorderPane shopBox) {
		GridPane row = new GridPane();
		row.setHgap(20);
		row.setVgap(10);

		ColumnConstraints column1 = new ColumnConstraints();
		NumberBinding columnWidthBind = shopBox.widthProperty().multiply(0.3);
		column1.minWidthProperty().bind(columnWidthBind);
		row.getColumnConstraints().add(column1);
		row.getColumnConstraints().add(column1);
		row.getColumnConstraints().add(column1);

		Label Title = new Label("Shop");
		Title.setFont(Font.font("Calibri", FontWeight.BOLD, 24));
		Title.setId("TitleTitle-DM");
		GridPane.setHalignment(Title, HPos.CENTER);
		row.add(Title, 0, 0, 3, 1);

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

	/**
	 * Creates the buttons for Buying and equipping items, and a readout of how many gainz the user has
	 * @param Gainz how many gainz the user currently has
	 * @return buttons this returns the event handlers for the buttons
	 */
	private HBox buttons(int Gainz){
		buyButton = new Button("Buy");
		buyButton.setPrefSize(100, 20);
		equipButton = new Button("Equip");
		equipButton.setPrefSize(100, 20);
		Label gainzAmount = new Label("Total Gainz: " + gainz);
		gainzAmount.setFont(Font.font("Calibri", FontWeight.BOLD, 16));
		
		HBox buttons = new HBox();
		buttons.setPadding(new Insets(15, 12, 15, 12));
		buttons.setSpacing(10);
		
		buttons.getChildren().addAll(buyButton, equipButton, gainzAmount);
		
		buyButton.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent event) {
				System.out.println("Buy");
			}	
		});
		
		equipButton.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent event) {
				System.out.println("Equip");
			}	
		});
		
		
		
		return buttons;
	}
	
	/**
	 * Creates the row for an item in the list with the image name and item provided.
	 * 
	 * @param scrollContent the container to add the row to.
	 * @param imageSrc the path to the image file for this particular item.
	 * @param title the name of the item.
	 * @param type the type of recipe (Breakfast, Lunch or Dinner). (needed as items had not yet been created to fill this menu)
	 * @param index the index of the item in the item list which the row points to.
	 * @return The constructed item row.
	 * @see IndexGridPane
	 */
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

		return row;
	}

	//Fills the table with the correct images and descriptions.
	private void populateTable(VBox scrollContent) {
		allRecipes = createRecipeList();

		for (Recipe i : allRecipes) {
			scrollContent.getChildren().add(createRow(scrollContent,i.getImageFile(),i.getMealName(),i.getMealType(),i.getIndex()));
		}

	}

	//obsolete code required to fill the table (used for filtering in the Diet Menu)
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

	/**
	 * Loads XML recipe data from the source file provided into a Recipe object.
	 * Again, items had not been created so i used the recipes as a placeholder
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
			System.out.println("The file could not be parsed - DM");
			e.printStackTrace();
		}
		return rec;
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