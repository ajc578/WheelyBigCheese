/* SOCIAL MENU */
/* In progress - Jon */

package userInterface;

import java.util.ArrayList;

import account.Account;
import account.CharacterParts;
import account.Protocol;
import javafx.beans.binding.NumberBinding;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Screen;
import presentationViewer.ExceptionFx;


public class SocialMenu extends VBox implements Controllable {


	private StackPaneUpdater screenParent;
	private Main mainApp;
	private ArrayList<Account> friendsList = new ArrayList<Account>();
	private ArrayList<Account> searchList = new ArrayList<Account>();
	
	/*Button myStats = new Button("My Stats");*/

	/*VBox containers for sub-menu selection*/
	VBox leaderboardContainer = new VBox();
	VBox statsContainer = new VBox();
	VBox buttonsContainer = new VBox();
	VBox searchContainer = new VBox();
	VBox friendsListContainer = new VBox();
	BorderPane editFriendsContainer = new BorderPane();
	VBox liveArea = new VBox();
	


	
	/*HBox container for overall content display - added to SocialMenu VBox*/
	HBox content = new HBox();
	
	/*Constructor*/
	public SocialMenu(double width, double height){
		
		/*Set up swappable areas*/
		liveArea.setPrefSize(400, 400);
		liveArea.getChildren().add(statsContainer);
		
		/*Set up grid for buttons */
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));
		
		Button addFriend = new Button("Add Friend");
		Button editFriends = new Button("Edit Friends");
		Button viewStats = new Button("View Stats");
		TextField searchTest = new TextField();
		//searchTest.setPromptText("Add a Friend...");
		
		grid.add(addFriend, 0, 0);
		grid.add(editFriends, 0, 1);
		grid.add(viewStats, 0, 2);
		//grid.add(searchTest, 2, 1);
		
		buttonsContainer.getChildren().add(grid);
		
		/*ActionListeners for buttons*/
		addFriend.setOnAction(new EventHandler<ActionEvent>(){
		@Override
		public void handle(ActionEvent e){
			liveArea.getChildren().clear();
			liveArea.getChildren().add(searchContainer);
		}
		});
		
		editFriends.setOnAction(new EventHandler<ActionEvent>(){
		@Override
		public void handle(ActionEvent e){
			liveArea.getChildren().clear();
			liveArea.getChildren().add(editFriendsContainer);
		}
		});
		
		viewStats.setOnAction(new EventHandler<ActionEvent>(){
		@Override
		public void handle(ActionEvent e){
			liveArea.getChildren().clear();
			liveArea.getChildren().add(statsContainer);
		}
		});
		
		
/*		searchTest.textProperty().addListener(new ChangeListener() {

			@Override
			public void changed(ObservableValue arg0, Object arg1, Object arg2) {
				System.out.println(arg0);
			}});*/
		
		/*Stats area*/
		TextArea statsArea = new TextArea();
		statsArea.setEditable(false);
		statsContainer.getChildren().add(statsArea);
			
		
		/*Leaderboard*/
		
		TableView leaderboard = new TableView();
		leaderboard.setId("Leaderboard");
		
		TableColumn usernameColumn = new TableColumn("Name");
		TableColumn levelColumn = new TableColumn("Level");
		TableColumn xpColumn = new TableColumn("XP Points");
		TableColumn skillPointsColumn = new TableColumn("Skill Points");
		TableColumn gainzColumn = new TableColumn("Gainz");
		
		leaderboard.getColumns().addAll(usernameColumn, levelColumn, xpColumn, skillPointsColumn, gainzColumn);
		leaderboardContainer.getChildren().add(leaderboard);
		
		
		/*Search friends*/
		
		TextField searchFriend = new TextField();
		searchFriend.setPromptText("Add a Friend...");
		TextArea searchResults = new TextArea();
		searchResults.setEditable(false);

		
		searchFriend.textProperty().addListener(new ChangeListener() {

			@Override
			public void changed(ObservableValue  arg0, Object arg1, Object arg2) {
				System.out.println(arg0);
			}});
		
		searchFriend.setOnKeyPressed(new EventHandler<KeyEvent>(){
		@Override
		public void handle(KeyEvent e){
			if(e.getCode().equals(KeyCode.ENTER)){
			String searchString = new String(searchFriend.getText());
			System.out.println("Search text = " + searchString);}
		}
		});
		
		searchContainer.getChildren().addAll(searchFriend, searchResults);
		
		/*Friends list*/
		ScrollPane friendsList = new ScrollPane();
		friendsListContainer.getChildren().add(friendsList);
		
		/*Edit friends*/
		ScrollPane editList = new ScrollPane();
		GridPane editGrid = new GridPane();
		HBox row = new HBox();
		
		editList.setContent(row);
		editFriendsContainer.getChildren().add(editList);
		
		/*Add all content to screen*/
		content.getChildren().addAll(buttonsContainer, liveArea, leaderboardContainer);
		content.setPrefSize(width*0.7, height*0.7);
		content.setPadding(new Insets(10,10,10,10));
		content.setSpacing(0.1*width);
		getChildren().add(content);
		
	}
	
	private void obtainFriendAccounts() {
		try{
			if (Main.serverDetected && Main.client.isAccessible()) {
				Main.client.findFriends();
				while (true) {
					String output = Main.client.receive();
					if (output.equals(Protocol.SUCCESS)) {
						friendsList = Main.client.getFriendsList();
						break;
					} else if (output.startsWith(Protocol.ERROR)) {
						System.out.println("Error returned in SocialMenu: " + output);
						generateReminderAlert();
						break;
					}
				}
			} else {
				
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	
	private void generateReminderAlert() {
		ExceptionFx except = new ExceptionFx(AlertType.WARNING, "Offline Error",
											 "You are not connected to the server",
											 "You're session has been switched to offline. This means"
											 + " that all social features wil be inaccessible. "
											 + "You will need to restart the program to reconnect.");
		except.show();
	}
	
	private void displayEditFriendsList() {
		editFriendsContainer.setTop(createFriendsListTitleRow(editFriendsContainer));
		VBox scrollContent = new VBox();
		scrollContent.minWidthProperty().bind(editFriendsContainer.widthProperty());
		populateTable(scrollContent);
		
	}
	
	private StackPane createAvatarImage(CharacterParts source, ReadOnlyDoubleProperty width) {
		StackPane imageContent = new StackPane();
		//set all images from the image paths stored in the account
		ImageView bodyImage = new ImageView(new Image("res/images/BaseCharacter.png"));
		ImageView hairImage = new ImageView(new Image(source.getHairSource()));
		ImageView eyesImage = new ImageView(new Image(source.getEyesSource()));
		//Bind all Fit dimensions to the parameter width 
		bodyImage.fitWidthProperty().bind(width.multiply(0.25));
		hairImage.fitWidthProperty().bind(width.multiply(0.25));
		eyesImage.fitWidthProperty().bind(width.multiply(0.25));
		//set scaling smooth and preserve the aspect ratio of each image
		bodyImage.setPreserveRatio(true);
		hairImage.setPreserveRatio(true);
		eyesImage.setPreserveRatio(true);
		bodyImage.setSmooth(true);
		hairImage.setSmooth(true);
		eyesImage.setSmooth(true);
		
		imageContent.getChildren().add(bodyImage);
		imageContent.getChildren().add(eyesImage);
		imageContent.getChildren().add(hairImage);
		
		return imageContent;
	}
	
	private GridPane createFriendsListTitleRow(BorderPane leftSide) {
		GridPane row = new GridPane();
		row.setHgap(20);
		row.setVgap(10);

		ColumnConstraints column1 = new ColumnConstraints();
		NumberBinding columnWidthBind = leftSide.widthProperty().multiply(0.25);
		column1.minWidthProperty().bind(columnWidthBind);
		row.getColumnConstraints().add(column1);
		row.getColumnConstraints().add(column1);
		row.getColumnConstraints().add(column1);
		row.getColumnConstraints().add(column1);

		Label editTitle = new Label("Friends List");
		editTitle.setFont(Font.font("Calibri", FontWeight.BOLD, 16));
		editTitle.setId("EditTitle-SM");
		GridPane.setHalignment(editTitle, HPos.CENTER);
		row.add(editTitle, 0, 0, 4, 1);

		Label imageLabel = new Label("Image");
		Label userNameLabel = new Label("User Name");
		Label firstNameLabel = new Label("First Name");
		Label lastNameLabel = new Label("Last Name");

		imageLabel.setId("ImageLabel-SM");
		userNameLabel.setId("UserNameLabel-SM");
		firstNameLabel.setId("FirstNameLabel-SM");
		lastNameLabel.setId("LastNameLabel-SM");

		GridPane.setHalignment(imageLabel, HPos.CENTER);
		GridPane.setHalignment(userNameLabel, HPos.CENTER);
		GridPane.setHalignment(firstNameLabel, HPos.CENTER);
		GridPane.setHalignment(lastNameLabel, HPos.CENTER);

		row.add(imageLabel, 0, 1);
		row.add(userNameLabel, 1, 1);
		row.add(firstNameLabel, 2, 1);
		row.add(lastNameLabel, 3, 1);

		return row;
	}
	
	private IndexGridPane createRow(VBox scrollContent, Account account, int index) {
		IndexGridPane row = new IndexGridPane(index);
		row.setHgap(20);
		row.setVgap(10);
		ColumnConstraints column1 = new ColumnConstraints();
		column1.minWidthProperty().bind(scrollContent.widthProperty().multiply(0.25));
		row.getColumnConstraints().add(column1);
		row.getColumnConstraints().add(column1);
		row.getColumnConstraints().add(column1);
		row.getColumnConstraints().add(column1);

		StackPane avatar = createAvatarImage(account.getCharacterAttributes().getCharacterSource(), 
											 scrollContent.widthProperty());
		
		Label username = new Label(account.getUsername());
		Label firstname = new Label(account.getFirstName());
		Label lastname = new Label(account.getSurname());
		

		GridPane.setHalignment(avatar, HPos.CENTER);
		GridPane.setHalignment(username, HPos.CENTER);
		GridPane.setHalignment(firstname, HPos.CENTER);
		GridPane.setHalignment(lastname, HPos.CENTER);

		row.add(avatar, 0, 0);
		row.add(username, 1, 0);
		row.add(firstname, 2, 0);
		row.add(lastname, 3, 0);

		//addClickListener(row,rightSide);
		setNodeCursor(row);
		
		return row;
	}

	private void addClickListener(IndexGridPane row, BorderPane bp) {
		row.setOnMouseClicked(event -> {
			//add stuff to present the friends account stat card and avatar
			Account selectedAccount = friendsList.get(row.getIndex());
		});
	}

	private void populateTable(VBox scrollContent) {
		//when removing a friend, we need to use increment and decrement methods in IndexGridPane 
		//to update the new index of that GridPane to match the index of the account in the friendsList
		for (int i = 0; i < friendsList.size(); i++) {
			scrollContent.getChildren().add(createRow(scrollContent,friendsList.get(i),i));
		}

	}
	
	private GridPane generateStatCard(Account friend) {
		GridPane statCard = new GridPane();
		double screenWidth = Screen.getPrimary().getVisualBounds().getWidth();
		statCard.setMinWidth(screenWidth*0.25);
		
		Label cardTitle = new Label("Stat Card: " + friend.getUsername());
		cardTitle.setId("CardTitle-SM");
		cardTitle.setAlignment(Pos.CENTER_LEFT);
		statCard.setId("StatCard-SM");
		
		ColumnConstraints column1 = new ColumnConstraints();
		column1.setMinWidth(statCard.getWidth()*0.25);
		statCard.getColumnConstraints().add(column1);
		statCard.getColumnConstraints().add(column1);
		statCard.getColumnConstraints().add(column1);
		
		Label firstnameTitle = 	new Label("First name :");
		Label lastnameTitle = 	new Label("Last name  :");
		Label emailTitle = 		new Label("Email      :");
		Label dOBTitle = 		new Label("DOB        :");
		
		Label firstnameLabel = new Label(friend.getFirstName());
		Label lastnameLabel = new Label(friend.getSurname());
		Label emailLabel = new Label(friend.getEmail());
		Label dOBLabel = new Label(friend.getDOB());
		
		Label levelTitle = 	new Label("Level :");
		Label xpTitle = 	new Label("XP    :");
		Label gainzTitle =	new Label("Gainz :");
		
		Label levelLabel = new Label(Integer.toString(friend.getLevel()));
		Label xpLabel = new Label(Integer.toString(friend.getXp()));
		Label gainzLabel = new Label(Integer.toString(friend.getGainz()));
		
		statCard.add(cardTitle, 0, 0, 4, 1);
		statCard.add(firstnameTitle,0,1);
		statCard.add(lastnameTitle,0,2);
		statCard.add(emailTitle,0,3);
		statCard.add(dOBTitle,0,4);
		statCard.add(firstnameLabel,1,1);
		statCard.add(lastnameLabel,1,2);
		statCard.add(emailLabel,1,3);
		statCard.add(dOBLabel,1,4);
		statCard.add(levelTitle,2,1);
		statCard.add(xpTitle,2,2);
		statCard.add(gainzTitle,2,3);
		statCard.add(levelLabel,3,1);
		statCard.add(xpLabel,3,2);
		statCard.add(gainzLabel,3,3);
		
		GridPane.setHalignment(cardTitle, HPos.LEFT);
		GridPane.setHalignment(firstnameTitle, HPos.LEFT);
		GridPane.setHalignment(lastnameTitle, HPos.LEFT);
		GridPane.setHalignment(emailTitle, HPos.LEFT);
		GridPane.setHalignment(dOBTitle, HPos.LEFT);
		GridPane.setHalignment(firstnameLabel, HPos.RIGHT);
		GridPane.setHalignment(lastnameLabel, HPos.RIGHT);
		GridPane.setHalignment(emailLabel, HPos.RIGHT);
		GridPane.setHalignment(dOBLabel, HPos.RIGHT);
		GridPane.setHalignment(levelTitle, HPos.LEFT);
		GridPane.setHalignment(xpTitle, HPos.LEFT);
		GridPane.setHalignment(gainzTitle, HPos.LEFT);
		GridPane.setHalignment(levelLabel, HPos.RIGHT);
		GridPane.setHalignment(xpLabel, HPos.RIGHT);
		GridPane.setHalignment(gainzLabel, HPos.RIGHT);

		return statCard; 
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
