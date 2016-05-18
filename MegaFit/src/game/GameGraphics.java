package game;

import account.Account;
import account.CharacterParts;
import javafx.beans.binding.NumberBinding;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class GameGraphics extends AnchorPane {
	
	private static final String defaultFont = "Calibri";
	
	private Label opponentName;
	private Label localName;
	private Label opponentLevel;
	private Label localLevel;
	private Label opponentHealth;
	private Label localHealth;
	private Rectangle opponentHealthContainer;
	private Rectangle localHealthContainer;
	private Rectangle opponentHealthBar;
	private Rectangle localHealthBar;
	private LayerCharacter opponentCharacter;
	private LayerCharacter localCharacter;
	private CharacterAnimation animationControls;
	
	private SimpleIntegerProperty oppHContainerYpos;
	private SimpleIntegerProperty locHContainerYpos;
	
	
	private Scene gameScene;
	
	public GameGraphics(Scene scene, Account opponent, Account local, CharacterParts opponentSource, CharacterParts localSource) {
		this.gameScene = scene;
		this.opponentName = new Label(opponent.getUsername());
		this.localName = new Label(local.getUsername());
		this.opponentLevel = new Label(Integer.toString(opponent.getLevel()));
		this.localLevel = new Label(Integer.toString(local.getLevel()));
		this.opponentHealth = new Label(Integer.toString(opponent.getCharacterAttributes().getHealth()));
		this.localHealth = new Label(Integer.toString(local.getCharacterAttributes().getHealth()));
		
		this.opponentCharacter = new LayerCharacter(opponentSource);
		this.localCharacter = new LayerCharacter(localSource);
		animationControls = new CharacterAnimation(scene,opponentCharacter,localCharacter, oppHContainerYpos, locHContainerYpos);
		
	}
	
	private void createGraphics() {
		GridPane oppStatGrid = new GridPane();
		ColumnConstraints column1 = new ColumnConstraints();
		NumberBinding column1Bind = gameScene.widthProperty().multiply(0.3);
		column1.prefWidthProperty().bind(column1Bind);
		ColumnConstraints column2 = new ColumnConstraints();
		NumberBinding column2Bind = gameScene.widthProperty().multiply(0.1);
		column1.prefWidthProperty().bind(column2Bind);
		oppStatGrid.getColumnConstraints().addAll(column1,column2);
		
		NumberBinding oppHealthLayout = opponentHealth.layoutXProperty().add(oppHContainerYpos);
		opponentHealth.layoutXProperty().bind(oppHealthLayout);
		opponentHealthBar.layoutXProperty().bind(oppHealthLayout);
		
		NumberBinding locHealthLayout = localHealth.layoutXProperty().add(locHContainerYpos);
		localHealth.layoutXProperty().bind(locHealthLayout);
		localHealthBar.layoutXProperty().bind(locHealthLayout);
		
		//+0.05
		
		opponentName.setFont(Font.font(defaultFont, FontWeight.BOLD, 16));
		localName.setFont(Font.font(defaultFont, FontWeight.BOLD, 16));
		opponentLevel.setFont(Font.font(defaultFont, 14));
		localLevel.setFont(Font.font(defaultFont, 14));
		opponentHealth.setFont(Font.font);
		
	}
	
	public void updateGraphics() {
		
	}
	
}
