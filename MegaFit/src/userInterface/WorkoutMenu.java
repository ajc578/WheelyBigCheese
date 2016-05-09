package userInterface;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class WorkoutMenu extends VBox{
	
	/* create arrays of type String for the exercises and descriptions, these
	 * will eventually be in an XML file that will be parsed into the code.*/
	String[] news = {
			"New workout added!",
			"New workout added!",
			"New recipe added!",
			"New workout added!"
	};
	String[] title = {
			"Bicep Blaster!",
			"6 Minute Abs",
			"Peanut Butter Protein Cookies",
			"Insane Cardio"
	};
	String[] author = {
			"MegaFit",
			"Emily Skye",
			"Emily Skye",
			"Shaun T"
	};
	
	Button createWorkout, openLibrary;
	VBox buttons, newsFeed;
	HBox menuOptions;
	LevelBar bar;
	ScrollPane newsScroll;
	GridPane recentWorkouts;
	
	public WorkoutMenu (double screenWidth, double screenHeight, BorderPane root){
		
		createWorkout = new Button("Create New Workout");
		createWorkout.setPrefSize(screenWidth*0.25, screenHeight*0.125);	
		
		bar = new LevelBar(screenWidth, screenHeight);
		menuOptions = new HBox();
		buttons = new VBox();
		newsFeed = new VBox();
		newsScroll = new ScrollPane();
		
		ImageView backButton = BackImageButton (screenWidth, screenHeight);
		setNodeCursor(backButton);
		HBox backButtonBox = new HBox();
		backButtonBox.getChildren().add(backButton);
		/* Begin Integration amendments (KS) */
		createWorkout.setOnAction (new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				CreateWorkout newWorkout = new CreateWorkout(screenWidth, screenHeight);
				try{
					getChildren().add(newWorkout);
					getChildren().removeAll(bar, menuOptions, backButtonBox);
				} catch (Exception e){
					e.printStackTrace();
				}
			
			}
		
		});
		
		setNodeCursor(createWorkout);
		/* End Integration Amendments (KS) */
		
		openLibrary = new Button("Open Workout Library");
		openLibrary.setPrefSize(screenWidth*0.25, screenHeight*0.125);
		
		/* Begin integration amendments (KS) */
		openLibrary.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				WorkoutLibrary library = new WorkoutLibrary(screenWidth, screenHeight, root);
				try{
					getChildren().add(library);
					getChildren().removeAll(bar, menuOptions, backButtonBox);
				} catch (Exception e){
					e.printStackTrace();
				}
				
			}
			
		});
		
		setNodeCursor(openLibrary);
		
		buttons.getChildren().addAll(createWorkout, openLibrary);
		buttons.setSpacing(screenHeight*0.05);
		buttons.setPadding(new Insets(screenHeight*0.1, 0, 0, 0));
		
		
		for(int i=0; i<news.length; i++){
			NewsFeed newsContents = new NewsFeed(screenWidth, screenHeight, news[i], title[i], author[i]);
			newsFeed.getChildren().add(newsContents);
		}
		
		newsFeed.setSpacing(screenHeight*0.05);
		
		newsScroll.setContent(newsFeed);		
		newsScroll.setHbarPolicy(ScrollBarPolicy.NEVER);		
		newsScroll.setMinWidth(screenWidth*0.4);		
		newsScroll.setMinHeight(screenHeight*0.6);
		newsScroll.setPadding(new Insets(screenHeight*0.01, screenWidth*0.01, screenHeight*0.01, screenWidth*0.01));
		
		
		Button workout1 = new Button("Recent Workout 1");
		workout1.setPrefSize(screenWidth*0.125, screenWidth*0.125);
		setNodeCursor(workout1);
		Button workout2 = new Button("Recent Workout 2");
		workout2.setPrefSize(screenWidth*0.125, screenWidth*0.125);
		setNodeCursor(workout2);
		Button workout3 = new Button("Recent Workout 3");
		workout3.setPrefSize(screenWidth*0.125, screenWidth*0.125);
		setNodeCursor(workout3);
		Button workout4 = new Button("Recent Workout 4");
		workout4.setPrefSize(screenWidth*0.125, screenWidth*0.125);
		setNodeCursor(workout4);

		recentWorkouts = new GridPane();
		
		recentWorkouts.setVgap(0);
		recentWorkouts.setHgap(0);
		recentWorkouts.add(workout1, 1, 1);
		recentWorkouts.add(workout3, 1, 2);
		recentWorkouts.add(workout2, 2, 1);
		recentWorkouts.add(workout4, 2, 2);
		recentWorkouts.setMinWidth(screenWidth*0.25);
		recentWorkouts.setPadding(new Insets(screenHeight*0.025, 0, 0, 0));
		
		menuOptions.getChildren().addAll(recentWorkouts, buttons, newsScroll);
		menuOptions.setSpacing(screenWidth*0.05);
		menuOptions.setPadding(new Insets(screenHeight*0.1, screenWidth*0.025, screenHeight*0.01, screenWidth*0.025));
		
		backButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			Menu menu = new Menu (screenWidth, screenHeight, root);
			@Override
			public void handle(MouseEvent event) {
				root.setTop(menu);
				getChildren().removeAll(bar, menuOptions, backButtonBox);
			}
			
		});
		
		getChildren().addAll(bar, menuOptions, backButtonBox);
	
	}
	
	public ImageView BackImageButton (double screenWidth, double screenHeight) {
		
		HBox buttonImageBox = new HBox();
		buttonImageBox.setAlignment(Pos.BOTTOM_LEFT);
		
		Image backButton = new Image("res/images/back_arrow.jpg");
		ImageView buttonImageView = new ImageView(backButton);
		buttonImageView.setImage(backButton);
		buttonImageView.setFitWidth(screenWidth*0.05);
		buttonImageView.setFitHeight(screenHeight*0.05);
		
		return buttonImageView;
	
	}
	
	public void setNodeCursor (Node node) {
		
		node.setOnMouseEntered(event -> setCursor(Cursor.HAND));
		node.setOnMouseExited(event -> setCursor(Cursor.DEFAULT));
	}

}
