package userInterface;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class LevelBar extends VBox {

	private ProgressBar progBar;
	
	Label currentLevelLabel, xpLabel;

	public LevelBar(double width, double height, int startXP, int currentXP, int endXP, int currentLevel){
		
		progBar = new ProgressBar();
		
		currentLevelLabel = new Label("Level:  " + currentLevel);
		xpLabel = new Label(Integer.toString(currentXP) + "/" + Integer.toString(endXP));
		xpLabel.getStyleClass().add("levelBarText");
		currentLevelLabel.getStyleClass().add("levelBarText");
		double progress = ((double)currentXP-startXP)/((double)endXP-startXP);
		System.out.println("LevelBar: progress: " + progress);
		progBar.setProgress(progress);
		this.setPrefWidth(width*0.8);
		this.setMaxWidth(width*0.8);
		this.setPrefHeight(height);
		
		progBar.setPrefWidth(width*0.8);
		progBar.setMaxWidth(width*0.8);
		progBar.setPrefHeight(height*0.6);
		
		this.setAlignment(Pos.CENTER_LEFT);
		
		GridPane labelsGrid = new GridPane();
		ColumnConstraints column1 = new ColumnConstraints();
		column1.minWidthProperty().bind(progBar.widthProperty().multiply(0.5));
		column1.setHalignment(HPos.LEFT);
		ColumnConstraints column2 = new ColumnConstraints();
		column2.minWidthProperty().bind(progBar.widthProperty().multiply(0.5));
		column2.setHalignment(HPos.RIGHT);
		labelsGrid.getColumnConstraints().addAll(column1,column2);
		labelsGrid.setPrefHeight(height*0.4);
		
		labelsGrid.add(currentLevelLabel, 0, 0);
		labelsGrid.add(xpLabel, 1, 0);
		
		getChildren().addAll(labelsGrid, progBar);
		setSpacing(5);
		
	}
	
	/*Creates the bar and adds it to the screen 
	 * 
	 * @param width The width that the bar should be
	 * @param height The height that the bar should be 
	 */
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
