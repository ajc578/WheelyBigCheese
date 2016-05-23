package userInterface;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;


public class SocialMenu extends VBox implements Controllable{
	private ScreenFlowController screenParent;
	private Main mainApp;

	public SocialMenu(double width, double height){
		
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
