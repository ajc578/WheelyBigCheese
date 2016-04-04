package userInterface;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;

public class CharacterMenu extends VBox{
	
		private int gainz = 0;
		private int strength = 10;
		private int speed = 10;
		private int endurance = 7;
		private int agility = 6;
		private int unspentPoints = 0;
		
		/* Array of strings to be displayed in the challenges box. Only repeated so many times
		 * to text the scroll box works. This array will eventually be contained in an XML file.*/
		String[] challenges = {
				"Perform 200 squats",
				"Run a marathon",
				"Perform 50 push-ups in a single set",
				"Perform 200 squats",
				"Run a marathon",
				"Perform 50 push-ups in a single set",
				"Perform 200 squats",
				"Run a marathon",
				"Perform 50 push-ups in a single set",
				"Perform 200 squats",
				"Run a marathon",
				"Perform 50 push-ups in a single set",
				"Perform 200 squats",
				"Run a marathon",
				"Perform 50 push-ups in a single set",
				"Perform 200 squats",
				"Run a marathon",
				"Perform 50 push-ups in a single set",
				"Perform 200 squats",
				"Run a marathon",
				"Perform 50 push-ups in a single set"
		};
		
		
		Label challenge, strengthPoints, speedPoints, endurancePoints, agilityPoints, 
			  unspentGainz, attributePoints;
		ScrollPane challengeArea;
		HBox attributeBox, shopBox;
		VBox attributeLabels, challengesBox;
		
	public CharacterMenu (double screenWidth, double screenHeight, BorderPane root){		
		
		challengeArea = new ScrollPane();
		attributeLabels = new VBox();
		challengesBox = new VBox();
		attributeBox = new HBox();
		shopBox = new HBox();			
		strengthPoints = new Label("STRENGTH: " + strength);
		speedPoints = new Label("SPEED: " + speed);
		endurancePoints = new Label("ENDURANCE: " + endurance);
		agilityPoints = new Label("AGILITY: " + agility);		
		unspentGainz = new Label("GAINZ: " + gainz);
		attributePoints = new Label("UNSPENT POINTS: " + unspentPoints);
		
		Button openShop = new Button("OPEN SHOP");
		openShop.setPrefSize(screenWidth*0.07, screenHeight*0.05);
		
		//set the contents of the drop box above the challenges window
		ObservableList<String> refineChallenges = FXCollections.observableArrayList(
				"Weekly Challenges",
				"Completed Challenges",
				"Lifetime Goals",
				"Completed Lifetime Goals"
			);
		
		ComboBox<String> challengeSort = new ComboBox<String>(refineChallenges);
		challenge = new Label();
		VBox allChallenges = new VBox();
		
		//set the contents of the challenge text box
		for(int i=0; i<challenges.length; i++){
			challenge = new Label(challenges[i]);
			allChallenges.getChildren().addAll(challenge);
		}
		
		allChallenges.setSpacing(screenHeight*0.025);
		
		challengeArea.setContent(allChallenges);
		challengeArea.setHbarPolicy(ScrollBarPolicy.NEVER);
		challengeArea.setMinWidth(screenWidth*0.3);		
		challengeArea.setMinHeight(screenHeight*0.5);
		challengeArea.setMaxHeight(screenHeight*0.5);
		
		//add all the attribute labels to a box and set it's position on the screen
		attributeLabels.getChildren().addAll(strengthPoints, speedPoints, endurancePoints, agilityPoints, attributePoints);
		attributeLabels.setPadding(new Insets(screenHeight*0.1, 0, 0, screenWidth*0.1));
		attributeLabels.setSpacing(screenHeight*0.05);
		
		challengesBox.getChildren().addAll(challengeSort, challengeArea);
		challengesBox.setPadding(new Insets(screenHeight*0.025, 0, 0, screenWidth*0.2));
		
		attributeBox.getChildren().addAll(challengesBox, attributeLabels);
		
		/* set an event handler to perform an action when the open shop button is pressed and set that action
		 * to be the shop menu opening on the screen - this menu overwrites what's already on the screen*/
		openShop.setOnAction(new EventHandler<ActionEvent>(){			
			public void handle(ActionEvent event) {
				ShopMenu shop = new ShopMenu(screenWidth, screenHeight);
				try{
					root.setCenter(shop);
				} catch (Exception e){
					e.printStackTrace();
				}
			}	
		});
		
		shopBox.getChildren().addAll(unspentGainz, openShop);
		shopBox.setSpacing(20);
		shopBox.setMinWidth(screenWidth*0.3);
		shopBox.setPadding(new Insets(screenHeight*0.05, 0, 0, screenWidth*0.45));
		
		LevelBar bar = new LevelBar(screenWidth, screenHeight);
		
		getChildren().addAll(bar, attributeBox, shopBox);
		
		
	}

		
}


