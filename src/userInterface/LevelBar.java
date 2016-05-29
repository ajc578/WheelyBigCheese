package userInterface;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class LevelBar extends VBox {

	private ProgressBar progBar;
	
	Label currentLevelLabel, xpLabel;

	public LevelBar(double width, double height, int startXP, int currentXP, int endXP, int currentLevel){
		
		progBar = new ProgressBar();
		
		currentLevelLabel = new Label("Level:  " + currentLevel);
		xpLabel = new Label(Integer.toString(currentXP) + "/" + Integer.toString(endXP));
		double progress = ((double)currentXP-startXP)/((double)endXP-startXP);
		System.out.println("LevelBar: progress: " + progress);
		progBar.setProgress(progress);
		this.setMinWidth(width);
		this.setMinHeight(height);
		this.setMaxWidth(width);
		this.setMaxHeight(height);
		
		progBar.setMinWidth(width*0.8);
		progBar.setMinHeight(height*0.6);
		
		this.setAlignment(Pos.CENTER_LEFT);
		
		HBox progressBarBox = new HBox();
		progressBarBox.setSpacing(5);
		progressBarBox.getChildren().addAll(progBar, xpLabel);
		
		getChildren().addAll(currentLevelLabel, progressBarBox);
		setSpacing(5);
		
	}
	
	public LevelBar(double width, double height) {
		progBar = new ProgressBar();
		
		currentLevelLabel = new Label("Level:  " + 0);
		xpLabel = new Label(Integer.toString(0) + "/" + Integer.toString(0));
		progBar.setProgress(0);
		this.setMinWidth(width);
		this.setMinHeight(height);
		this.setMaxWidth(width);
		this.setMaxHeight(height);
		
		this.setAlignment(Pos.CENTER_LEFT);
		
		HBox progressBarBox = new HBox();
		progressBarBox.setSpacing(5);
		progressBarBox.getChildren().addAll(progBar, xpLabel);
		
		getChildren().addAll(currentLevelLabel, progressBarBox);
		setSpacing(5);
	}
}
