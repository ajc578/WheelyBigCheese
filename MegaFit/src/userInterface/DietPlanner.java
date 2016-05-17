package userInterface;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class DietPlanner extends VBox implements Controllable{
	private ScreenFlowController screenParent;
	private Main mainApp;
	
	//Recipes mealView = new Recipes(_height, _height, null, null, null, null);

	private Button[] btns = new Button[21];
	private Label [] dayLabels = new Label[7]; 
	private Label[] mealTypeLabels = new Label[3];
	
	DietPlanner (double screenWidth, double screenHeight) {
		
		int xCoor = 0;
		int yCoor = 0;
		int k = 0;
	
		Label introLabel = new Label("Click on one of the buttons to select the day and type of a meal");
		
		GridPane calendarPane = new GridPane();
		
		addButtons();
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
				btns[k].setPrefSize(screenWidth*0.15, screenHeight*0.06);
				setNodeCursor(btns[k]);
				btns[k].setOnAction(new EventHandler<ActionEvent>(){
					
					public void handle (ActionEvent event) {
						BorderPane root = new BorderPane();


						screenParent.setScreen(Main.dietMenuID);
					}
				});
				k++;
			}
		}
		
		
		setPadding(new Insets(screenHeight*0.15, screenWidth*0.15, screenHeight*0.15, screenWidth*0.3));
		setSpacing(10);
		getChildren().addAll(introLabel, calendarPane);
	}
	
	public void addButtons() {
		for (int i = 0; i < btns.length; i++) {
			btns[i] = new Button(Integer.toString(i));
		}
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
		}

	}
	
	public void addMealTypeLabels() {
		
		String[] mealTypes = {
				"Breakfast",
				"Lunch",
				"Dinner"
		};
		for (int i = 0; i < mealTypeLabels.length; i++) {
			mealTypeLabels[i] = new Label(mealTypes[i]);
		}

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
 }

