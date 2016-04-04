package userInterface;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.HBox;

public class LevelBar extends HBox{

	private ProgressBar progBar;
	private int currentLevel = 0;
	private int nextLevel = 1;
	private double currentXP = 340;
	private double requiredXP = 1000*nextLevel^2;
	private double progress = currentXP/requiredXP;
	
	Label currentLevelLabel, nextLevelLabel;

	public LevelBar(double screenWidth, double screenHeight){
		
		progBar = new ProgressBar();
		currentLevelLabel = new Label("Current:  " +currentLevel);
		currentLevelLabel.setMinWidth(screenWidth*0.05);
		currentLevelLabel.setPadding(new Insets(screenHeight*0.01, 0, 0, 0));
		nextLevelLabel = new Label("Next:  " +nextLevel);
		nextLevelLabel.setMinWidth(screenWidth*0.05);
		nextLevelLabel.setPadding(new Insets(screenHeight*0.01, 0, 0, 0));
		progBar.setProgress(progress);
		progBar.setPrefWidth(screenWidth*0.5);
		progBar.setPrefHeight(screenHeight*0.05);
		
		getChildren().addAll(currentLevelLabel, progBar, nextLevelLabel);
		setSpacing(screenWidth*0.01);
		
		setPadding(new Insets(0.05*screenHeight,screenWidth*0.25 , 0, screenWidth*0.25));
		
	}
}
