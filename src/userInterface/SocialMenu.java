/* SOCIAL MENU */
/* In progress - Jon */

package userInterface;

import java.util.ArrayList;

import account.Account;
import account.CharacterParts;
import account.Protocol;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.binding.NumberBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import presentationViewer.ExceptionFx;


public class SocialMenu extends AnchorPane implements Controllable {


	private StackPaneUpdater screenParent;
	private Main mainApp;
	private ArrayList<Account> friendsList = new ArrayList<Account>();
	private ArrayList<Account> searchList = new ArrayList<Account>();
	
	/*Button myStats = new Button("My Stats");*/

	/*VBox containers for sub-menu selection*/
	private VBox leaderboardContainer = new VBox();
	private VBox buttonsContainer = new VBox();
	private VBox searchContainer = new VBox();
	private VBox friendsListContainer = new VBox();
	private BorderPane editFriendsContainer = new BorderPane();
	private VBox liveArea = new VBox();
	private BorderPane searchFriendContainer = new BorderPane();
	
	//contains the Avatar and Stats of the friend clicked on in the friend list
	private BorderPane avatarAndStats = new BorderPane();
	
	/*HBox container for overall content display - added to SocialMenu VBox*/
	private GridPane content = new GridPane();
	
	private TextField searchFriend;
	
	/*Target account set from clicked on row*/
	private Account targetAccount;
	
	/*Add and Delete Buttons for friendsList*/
	private Button friendButton = new Button();
	
	/*Leaderboard field*/
	private Leaderboard leaderboard;
	
	/*Constructor*/
	public SocialMenu(double width, double height){
		
		obtainFriendAccounts();
		
		/*Set up swappable areas*/
		liveArea.getChildren().add(editFriendsContainer);
		/*Set default dimensions of the liveArea and its children*/
		double screenWidth = Screen.getPrimary().getVisualBounds().getWidth();
		double screenHeight = Screen.getPrimary().getVisualBounds().getHeight();
		
		liveArea.setMinWidth(screenWidth*0.35);
		liveArea.setMinHeight(screenHeight*0.35);
		liveArea.setMaxWidth(screenWidth*0.35);
		liveArea.setMaxHeight(screenHeight*0.35);
		
		editFriendsContainer.setMinWidth(screenWidth*0.3);
		editFriendsContainer.setMinHeight(screenHeight*0.3);
		editFriendsContainer.setMaxWidth(screenWidth*0.35);
		editFriendsContainer.setMaxHeight(screenHeight*0.35);
		
		searchFriendContainer.setMinWidth(screenWidth*0.3);
		searchFriendContainer.setMinHeight(screenHeight*0.3);
		searchFriendContainer.setMaxWidth(screenWidth*0.35);
		searchFriendContainer.setMaxHeight(screenHeight*0.35);
		
		leaderboardContainer.setMinWidth(screenWidth*0.35);
		leaderboardContainer.setMinHeight(screenHeight*0.35);
		leaderboardContainer.setMaxWidth(screenWidth*0.35);
		leaderboardContainer.setMaxHeight(screenHeight*0.35);
		
		/*Set up grid for buttons */
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));
		
		Button addFriend = new Button("Search");
		Button editFriends = new Button("View Friends");
		addFriend.minWidthProperty().bind(editFriends.widthProperty());
		
		grid.add(addFriend, 0, 0);
		grid.add(editFriends, 0, 1);
		
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
		
		/*Leaderboard*/
		
		leaderboard = new Leaderboard(generateLeaderboardContent());
		leaderboard.setId("Leaderboard-SM");
		
		Label leaderboardTitle = new Label("Leaderboard");
		leaderboardTitle.getStyleClass().add("featureTitle");
		leaderboardContainer.setAlignment(Pos.CENTER);
		leaderboardContainer.getChildren().addAll(leaderboardTitle,leaderboard);
		
		
		/*Search friends*/
		
		searchFriend = new TextField();
		searchFriend.setPromptText("Search for a friend username, name or email...");
		TextArea searchResults = new TextArea();
		searchResults.setEditable(false);
		
		searchFriend.setOnKeyPressed(new EventHandler<KeyEvent>()
	    {
	        @Override
	        public void handle(KeyEvent ke)
	        {
	            if (ke.getCode().equals(KeyCode.ENTER))
	            {
	            	obtainSearchResult(searchFriend.getText());
	            	displaySearchList();
	            }
	        }
	    });
		
		/*Set Event Handler for the friendButton*/
		friendButton.setOnAction(event -> {
			if (friendButton.getText().startsWith("Add")) {
				System.out.println("SocialMenu: Add friend button pressed!");
				friendsList.add(targetAccount);
				Main.account.getFriends().add(targetAccount.getUsername());
				updateServerAccount(targetAccount.getUsername(),true);
				displayEditFriendsList();
				friendButton.setText("Remove from\nFriend List");
				leaderboard.setData(generateLeaderboardContent());
			} else if (friendButton.getText().startsWith("Remove")) {
				System.out.println("SocialMenu: Remove friend button pressed!");
				ArrayList<Account> tempAccList = new ArrayList<Account>();
				ArrayList<String> tempStrList = new ArrayList<String>();
				for (Account i : friendsList) {
					if (!i.getUsername().equals(targetAccount.getUsername())) {
						tempAccList.add(i);
						tempStrList.add(i.getUsername());
					}
				}
				friendsList.clear();
				friendsList = tempAccList;
				Main.account.setFriends(tempStrList);
				updateServerAccount(targetAccount.getUsername(),false);
				displayEditFriendsList();
				friendButton.setText("Add to\nFriend List");
				leaderboard.setData(generateLeaderboardContent());
			}
		});
		
		//create title label for search container
		Label searchTitle = new Label("Search for a friend");
		searchTitle.setId("SearchTitle-SM");
		searchContainer.getChildren().addAll(searchTitle,searchFriend, searchFriendContainer);
		
		/*Friends list*/
		ScrollPane friendsList = new ScrollPane();
		friendsListContainer.getChildren().add(friendsList);
		
		/*Edit friends*/
		displayEditFriendsList();
		
		/*Add all content to screen*/
		AnchorPane.setBottomAnchor(avatarAndStats, 10.0);
		AnchorPane.setLeftAnchor(avatarAndStats, 10.0);
		this.getChildren().add(avatarAndStats);
		
		ColumnConstraints genericColumn = new ColumnConstraints();
		ColumnConstraints fillerColumn = new ColumnConstraints();
		fillerColumn.setHgrow(Priority.ALWAYS);
		content.getColumnConstraints().add(genericColumn);
		content.getColumnConstraints().add(fillerColumn);
		content.getColumnConstraints().add(genericColumn);
		content.getColumnConstraints().add(fillerColumn);
		content.getColumnConstraints().add(genericColumn);
		
		content.add(buttonsContainer, 0, 0);
		content.add(liveArea, 2, 0);
		content.add(leaderboardContainer, 4, 0);
		
		this.getChildren().add(content);
		
		AnchorPane.setTopAnchor(content, 5.0);
		AnchorPane.setLeftAnchor(content, 5.0);
		AnchorPane.setRightAnchor(content, 5.0);
			
	}
	
	private ObservableList<User> generateLeaderboardContent() {
		ArrayList<User> temp = new ArrayList<User>();
		for (Account i : friendsList) {
			temp.add(createUser(i));
		}
		ObservableList<User> data = FXCollections.observableArrayList(temp);
		return data;
	}
	
	private User createUser(Account userAcc) {
		int totalSkillz = userAcc.getSkillPoints() +
						  userAcc.getCharacterAttributes().getStrength() +
						  userAcc.getCharacterAttributes().getSpeed() +
						  userAcc.getCharacterAttributes().getAgility() +
						  userAcc.getCharacterAttributes().getEndurance();
		
		User user = new User(userAcc.getUsername(), userAcc.getLevel(), userAcc.getXp(), 
							 totalSkillz, userAcc.getGainz());
		
		return user;
	}
	
	private void updateServerAccount(String username, boolean add) {
		try{
			if (Main.serverDetected && Main.client.isAccessible()) {
				if (add)
					Main.client.addFriend(username);
				else
					Main.client.removeFriend(username);
				while (true) {
					String output = Main.client.receive();
					if (output.equals(Protocol.SUCCESS)) {
						break;
					} else if (output.startsWith(Protocol.ERROR)) {
						ExceptionFx except = new ExceptionFx(AlertType.WARNING, "Save Error",
								 "You're friend list wasn't updated in the server server.",
								 "Try to restart the application to reconnect.",
								 (Stage) this.getScene().getWindow());
						except.show();
						break;
					}
				}
			} 
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	
	private void obtainSearchResult(String search) {
		try{
			if (Main.serverDetected && Main.client.isAccessible()) {
				Main.client.searchFriend(search);
				while (true) {
					String output = Main.client.receive();
					if (output.equals(Protocol.SUCCESS)) {
						searchList = Main.client.getFriendsList();
						break;
					} else if (output.startsWith(Protocol.ERROR)) {
						ExceptionFx except = new ExceptionFx(AlertType.WARNING, "Search Result",
								 "There were no similar accounts found to the text input.",
								 "Either the target account doesn't exist, or you have made"
								 + "spelling mistakes in the text input.\nPlease try again.",
								 (Stage) this.getScene().getWindow());
						except.show();
						break;
					}
				}
			} 
		} catch (Exception e){
			e.printStackTrace();
		}
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
						break;
					}
				}
			} 
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	
	private void displayEditFriendsList() {
		editFriendsContainer.getChildren().clear();
		editFriendsContainer.setTop(createFriendsListTitleRow(editFriendsContainer,0));
		ScrollPane friendScroll = new ScrollPane();
		friendScroll.minWidthProperty().bind(editFriendsContainer.widthProperty());
		friendScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
		editFriendsContainer.setCenter(friendScroll);
		VBox scrollContent = new VBox();
		scrollContent.minWidthProperty().bind(editFriendsContainer.widthProperty());
		populateFriendTable(scrollContent);
		friendScroll.setContent(scrollContent);
	}
	
	private void displaySearchList() {
		searchFriendContainer.setTop(createFriendsListTitleRow(searchFriendContainer,1));
		ScrollPane searchScroll = new ScrollPane();
		searchScroll.minWidthProperty().bind(searchFriendContainer.widthProperty());
		searchScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
		searchFriendContainer.setCenter(searchScroll);
		VBox scrollContent = new VBox();
		scrollContent.minWidthProperty().bind(searchFriendContainer.widthProperty());
		populateSearchTable(scrollContent);
		searchScroll.setContent(scrollContent);
	}
	
	private StackPane createAvatarImage(CharacterParts source, DoubleBinding sizeBinding, boolean inTable) {
		StackPane imageContent = new StackPane();
		//set all images from the image paths stored in the account
		ImageView bodyImage = new ImageView(new Image("res/images/BaseCharacter.png"));
		System.out.println("SocialMenu: createAvatarImage: hair source: " + source.getHairSource());
		ImageView hairImage = new ImageView(new Image(source.getHairSource()));
		ImageView eyesImage = new ImageView(new Image(source.getEyesSource()));
		//Bind all Fit dimensions to the parameter width 
		if (inTable) {
			bodyImage.fitWidthProperty().bind(sizeBinding);
			hairImage.fitWidthProperty().bind(sizeBinding);
			eyesImage.fitWidthProperty().bind(sizeBinding);
		} else {
			bodyImage.fitHeightProperty().bind(sizeBinding);
			hairImage.fitHeightProperty().bind(sizeBinding);
			eyesImage.fitHeightProperty().bind(sizeBinding);
		}
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
	
	private GridPane createFriendsListTitleRow(BorderPane leftSide, int titleIndex) {
		GridPane row = new GridPane();

		ColumnConstraints column1 = new ColumnConstraints();
		NumberBinding columnWidthBind = leftSide.widthProperty().multiply(0.25);
		column1.minWidthProperty().bind(columnWidthBind);
		row.getColumnConstraints().add(column1);
		row.getColumnConstraints().add(column1);
		row.getColumnConstraints().add(column1);
		row.getColumnConstraints().add(column1);
		Label editTitle = null;
		if (titleIndex == 0)
			editTitle = new Label("Friends List");
		else if (titleIndex == 1)
			editTitle = new Label("Search Results");
		editTitle.getStyleClass().add("featureTitle");
		GridPane.setHalignment(editTitle, HPos.CENTER);
		row.add(editTitle, 0, 0, 4, 1);

		Label imageLabel = new Label("Image");
		Label userNameLabel = new Label("User Name");
		Label firstNameLabel = new Label("First Name");
		Label lastNameLabel = new Label("Last Name");
		imageLabel.getStyleClass().add("columnTitle");
		userNameLabel.getStyleClass().add("columnTitle");
		firstNameLabel.getStyleClass().add("columnTitle");
		lastNameLabel.getStyleClass().add("columnTitle");

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
		ColumnConstraints column1 = new ColumnConstraints();
		column1.minWidthProperty().bind(scrollContent.widthProperty().multiply(0.25));
		row.getColumnConstraints().add(column1);
		row.getColumnConstraints().add(column1);
		row.getColumnConstraints().add(column1);
		row.getColumnConstraints().add(column1);

		StackPane avatar = createAvatarImage(account.getCharacterAttributes().getCharacterSource(), 
											 scrollContent.widthProperty().multiply(0.1),true);
	
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

	private void addClickListener(IndexGridPane row, int listChoice) {
		if (listChoice == 0) {
			row.setOnMouseClicked(event -> {
				//add stuff to present the friends account stat card and avatar
				avatarAndStats.getChildren().clear();
				targetAccount = friendsList.get(row.getIndex());
				avatarAndStats.setCenter(createMoreFriendDetails(targetAccount,false));
				friendButton.setText("Remove from\nFriend List");
			});
		} else if (listChoice == 1) {
			row.setOnMouseClicked(event -> {
				//add stuff to present the friends account stat card and avatar
				avatarAndStats.getChildren().clear();
				targetAccount = searchList.get(row.getIndex());
				friendButton.setText("Add to\nFriend List");
				for (Account i : friendsList) {
					if (i.getUsername().equals(targetAccount.getUsername())) {
						friendButton.setText("Remove from\nFriend List");
						break;
					}
				}
				avatarAndStats.setCenter(createMoreFriendDetails(searchList.get(row.getIndex()),true));
			});
		}
	}

	private void populateFriendTable(VBox scrollContent) {
		//when removing a friend, we need to use increment and decrement methods in IndexGridPane 
		//to update the new index of that GridPane to match the index of the account in the friendsList
		for (int i = 0; i < friendsList.size(); i++) {
			IndexGridPane row = createRow(scrollContent,friendsList.get(i),i);
			scrollContent.getChildren().add(row);
			addClickListener(row,0);
		}

	}
	
	private void populateSearchTable(VBox scrollContent) {
		System.out.println("SocialMenu: populateSearchTable: search list size: " + searchList.size());
		for (int i = 0; i < searchList.size(); i++) {
			System.out.println("SocialMenu: populateSearchTable: search list content: " + searchList.get(i));
			IndexGridPane row = createRow(scrollContent,searchList.get(i),i);
			scrollContent.getChildren().add(row);
			addClickListener(row,1);
		}
	}
	
	private GridPane createMoreFriendDetails(Account friend, boolean isNewFriend) {
		GridPane friendPane = new GridPane();
		double screenWidth = Screen.getPrimary().getVisualBounds().getWidth();
		double screenHeight = Screen.getPrimary().getVisualBounds().getHeight();
		friendPane.setMaxWidth(screenWidth*0.4);
		friendPane.setMaxHeight(screenHeight*0.4);
		friendPane.setMinWidth(screenWidth*0.4);
		friendPane.setMinHeight(screenHeight*0.4);
		
		ColumnConstraints column1 = new ColumnConstraints();
		column1.setMaxWidth(screenWidth*0.15);
		column1.setMinWidth(screenWidth*0.15);
		friendPane.getColumnConstraints().add(column1);
		ColumnConstraints column2 = new ColumnConstraints();
		column2.setMaxWidth(screenWidth*0.25);
		column2.setMinWidth(screenWidth*0.25);
		friendPane.getColumnConstraints().add(column2);
		
		RowConstraints row = new RowConstraints();
		row.setMinHeight(screenHeight*0.2);
		row.setMaxHeight(screenHeight*0.2);
		friendPane.getRowConstraints().add(row);
		friendPane.getRowConstraints().add(row);
		
		friendPane.add(createAvatarImage(friend.getCharacterAttributes().getCharacterSource(), 
						friendPane.heightProperty().multiply(1.0),false), 0, 0, 1, 2);
		friendPane.add(generateStatCard(friend), 1, 0);
		
		//Determine whether this account is from your friends list, or the search list
		//Place addFriend button if from searchList
		
		GridPane.setHalignment(friendButton, HPos.CENTER);
		friendPane.add(friendButton, 1, 1);
		
		return friendPane;
	}
	
	private GridPane generateStatCard(Account friend) {
		GridPane statCard = new GridPane();
		statCard.setHgap(5);
		statCard.setVgap(5);
		double screenWidth = Screen.getPrimary().getVisualBounds().getWidth();
		statCard.setMinWidth(screenWidth*0.25);
		
		Label cardTitle = new Label("Stat Card: " + friend.getUsername());
		cardTitle.getStyleClass().add("featureTitle");
		cardTitle.setAlignment(Pos.CENTER_LEFT);
		statCard.setId("StatCard-SM");
		
		ColumnConstraints column1 = new ColumnConstraints();
		column1.setMinWidth(statCard.getMinWidth()*0.25);
		ColumnConstraints column2 = new ColumnConstraints();
		column1.setMinWidth(statCard.getMinWidth()*0.35);
		ColumnConstraints column4 = new ColumnConstraints();
		column1.setMinWidth(statCard.getMinWidth()*0.15);
		statCard.getColumnConstraints().add(column1);
		statCard.getColumnConstraints().add(column2);
		statCard.getColumnConstraints().add(column1);
		statCard.getColumnConstraints().add(column4);
		
		Label firstnameTitle = new Label("First name:");
		firstnameTitle.getStyleClass().add("columnTitle");
		Label lastnameTitle = new Label("Last name:");
		lastnameTitle.getStyleClass().add("columnTitle");
		Label emailTitle = new Label("Email:");
		emailTitle.getStyleClass().add("columnTitle");
		Label dOBTitle = new Label("DOB:");
		dOBTitle.getStyleClass().add("columnTitle");
		
		Label firstnameLabel = new Label(friend.getFirstName());
		Label lastnameLabel = new Label(friend.getSurname());
		Label emailLabel = new Label(friend.getEmail());
		Label dOBLabel = new Label(friend.getDOB());
		
		Label levelTitle = new Label("Level:");
		levelTitle.getStyleClass().add("columnTitle");
		Label xpTitle = new Label("XP:");
		xpTitle.getStyleClass().add("columnTitle");
		Label gainzTitle = new Label("Gainz:");
		gainzTitle.getStyleClass().add("columnTitle");
		
		Label levelLabel = new Label(Integer.toString(friend.getLevel()));
		Label xpLabel = new Label(Integer.toString(friend.getXp()));
		Label gainzLabel = new Label(Integer.toString(friend.getGainz()));
		
		Label strengthTitle = new Label("Strength:");
		strengthTitle.getStyleClass().add("columnTitle");
		Label speedTitle = new Label("Speed:");
		speedTitle.getStyleClass().add("columnTitle");
		Label agilityTitle = new Label("Agility:");
		agilityTitle.getStyleClass().add("columnTitle");
		Label enduranceTitle = new Label("Endurance:");
		enduranceTitle.getStyleClass().add("columnTitle");
		
		Label strengthLabel = new Label(Integer.toString(friend.getCharacterAttributes().getStrength()));
		Label speedLabel = new Label(Integer.toString(friend.getCharacterAttributes().getSpeed()));
		Label agilityLabel = new Label(Integer.toString(friend.getCharacterAttributes().getAgility()));
		Label enduranceLabel = new Label(Integer.toString(friend.getCharacterAttributes().getEndurance()));
		
		statCard.add(cardTitle, 0, 0, 4, 1);
		statCard.add(firstnameTitle,0,1);
		statCard.add(lastnameTitle,0,2);
		statCard.add(emailTitle,0,3);
		statCard.add(dOBTitle,0,4);
		statCard.add(firstnameLabel,1,1);
		statCard.add(lastnameLabel,1,2);
		statCard.add(emailLabel,1,3);
		statCard.add(dOBLabel,1,4);
		
		statCard.add(strengthTitle, 2, 1);
		statCard.add(speedTitle, 2, 2);
		statCard.add(agilityTitle, 2, 3);
		statCard.add(enduranceTitle, 2, 4);
		statCard.add(strengthLabel, 3, 1);
		statCard.add(speedLabel, 3, 2);
		statCard.add(agilityLabel, 3, 3);
		statCard.add(enduranceLabel, 3, 4);
		//sets an empty row
		Label filler = new Label(" ");
		statCard.add(filler, 0, 5, 4, 1);
		
		statCard.add(levelTitle,0,6);
		statCard.add(xpTitle,2,6);
		statCard.add(levelLabel,1,6);
		statCard.add(xpLabel,3,6);
		
		HBox gainzBox = new HBox();
		gainzBox.setSpacing(10.0);
		gainzBox.setAlignment(Pos.CENTER);
		gainzBox.getChildren().addAll(gainzTitle, gainzLabel);
		
		statCard.add(gainzBox, 0, 7, 4, 1);
		
		GridPane.setHalignment(cardTitle, HPos.CENTER);
		GridPane.setHalignment(firstnameTitle, HPos.LEFT);
		GridPane.setHalignment(lastnameTitle, HPos.LEFT);
		GridPane.setHalignment(emailTitle, HPos.LEFT);
		GridPane.setHalignment(dOBTitle, HPos.LEFT);
		GridPane.setHalignment(firstnameLabel, HPos.CENTER);
		GridPane.setHalignment(lastnameLabel, HPos.CENTER);
		GridPane.setHalignment(emailLabel, HPos.CENTER);
		GridPane.setHalignment(dOBLabel, HPos.CENTER);
		GridPane.setHalignment(levelTitle, HPos.CENTER);
		GridPane.setHalignment(xpTitle, HPos.CENTER);
		GridPane.setHalignment(levelLabel, HPos.CENTER);
		GridPane.setHalignment(xpLabel, HPos.CENTER);
		
		GridPane.setHalignment(strengthTitle, HPos.LEFT);
		GridPane.setHalignment(speedTitle, HPos.LEFT);
		GridPane.setHalignment(agilityTitle, HPos.LEFT);
		GridPane.setHalignment(enduranceTitle, HPos.LEFT);
		
		GridPane.setHalignment(strengthLabel, HPos.CENTER);
		GridPane.setHalignment(speedLabel, HPos.CENTER);
		GridPane.setHalignment(agilityLabel, HPos.CENTER);
		GridPane.setHalignment(enduranceLabel, HPos.CENTER);

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
