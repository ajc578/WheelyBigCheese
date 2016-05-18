package userInterface;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class DietMenuController implements Controllable{
	
	@FXML
	private GridPane recipeList; 
	
	Label recipeLabel;
	
	private ScreenFlowController screenParent;
	private Main mainApp;

	@FXML
	public void initialize() {
		recipeLabel = new Label("Wadup");
		Label balls = new Label("Balls");
		
		recipeList.add(recipeLabel, 0, 0);
		recipeList.add(balls, 3, 3);
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
