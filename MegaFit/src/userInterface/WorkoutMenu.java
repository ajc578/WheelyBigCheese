package userInterface;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;

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
		
		
		/* Begin Integration amendments (KS) */
		createWorkout.setOnAction (new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				CreateWorkout newWorkout = new CreateWorkout(screenWidth, screenHeight);
				try{
					root.setBottom(newWorkout);
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
					root.setBottom(library);
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
		
		
		
		getChildren().addAll(bar, menuOptions);
	
	}
	
	
	public void setNodeCursor (Node node) {
		
		node.setOnMouseEntered(event -> setCursor(Cursor.HAND));
		node.setOnMouseExited(event -> setCursor(Cursor.DEFAULT));
	}

}
