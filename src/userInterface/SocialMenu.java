package userInterface;

import javafx.scene.layout.VBox;


public class SocialMenu extends VBox implements Controllable{
	private TheScreen screenParent;
	private Main mainApp;

	public SocialMenu(double width, double height){
		
	}

	@Override
	public void setScreenParent(TheScreen screenParent) {
		this.screenParent = screenParent;
	}

	@Override
	public void setMainApp(Main mainApp) {
		this.mainApp = mainApp;
	}
	
}
