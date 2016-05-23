package game;

import java.util.ArrayList;
import java.util.Random;

import account.Account;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Bounds;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.Lighting;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;

public class GameGraphics extends AnchorPane {

	private static final String defaultFont = "Calibri";
	private static final int OPPONENT = 0, LOCAL = 1;

	private Label opponentName;
	private Label localName;
	private Label opponentLevel;
	private Label localLevel;
	private Label opponentHealth;
	private Label localHealth;
	private Rectangle opponentHealthBar;
	private Rectangle localHealthBar;
	private LayerCharacter opponentCharacter;
	private LayerCharacter localCharacter;
	private CharacterAnimation animationControls;

	private SimpleDoubleProperty oppHContainerYpos = new SimpleDoubleProperty();
	private SimpleDoubleProperty locHContainerYpos = new SimpleDoubleProperty();
	private GridPane oppStatGrid;
	private GridPane locStatGrid;

	private ArrayList<ParallelTransition> locBubbleAnim = new ArrayList<ParallelTransition>();
	private ArrayList<ParallelTransition> oppBubbleAnim = new ArrayList<ParallelTransition>();
	private int oldOppHealth;
	private int oldLocHealth;

	private Scene gameScene;

	public GameGraphics(Scene scene, Account opponent, Account local) {
		this.gameScene = scene;
		this.opponentName = new Label(opponent.getUsername());
		this.localName = new Label(local.getUsername());
		this.opponentLevel = new Label(Integer.toString(opponent.getLevel()));
		this.localLevel = new Label(Integer.toString(local.getLevel()));
		this.opponentHealth = new Label(Integer.toString(opponent.getCharacterAttributes().getHealth()));
		this.localHealth = new Label(Integer.toString(local.getCharacterAttributes().getHealth()));

		oppHContainerYpos.set(0.0);
		locHContainerYpos.set(0.0);

		oldOppHealth = opponent.getCharacterAttributes().getHealth();
		oldLocHealth = local.getCharacterAttributes().getHealth();
		//this.opponentCharacter = new LayerCharacter(opponent.getCharacterAttributes().getCharacterSource());
		//this.localCharacter = new LayerCharacter(local.getCharacterAttributes().getCharacterSource());
		//animationControls = new CharacterAnimation(scene,opponentCharacter,localCharacter, oppHContainerYpos, locHContainerYpos);
		createGraphics();

	}

	private void createGraphics() {
		oppStatGrid = new GridPane();
		oppStatGrid.setHgap(5);
		ColumnConstraints columnOpp1 = new ColumnConstraints();
		columnOpp1.setMinWidth(gameScene.getWidth()*0.3);
		//column1.setPrefWidth(gameScene.getWidth()*0.3);
		ColumnConstraints columnOpp2 = new ColumnConstraints();
		columnOpp2.setMinWidth(gameScene.getWidth()*0.3);
		oppStatGrid.getColumnConstraints().addAll(columnOpp1,columnOpp2);
		RowConstraints rowOpp = new RowConstraints();
		rowOpp.setMinHeight(gameScene.getHeight()*0.1);
		oppStatGrid.getRowConstraints().add(rowOpp);
		oppStatGrid.getRowConstraints().add(rowOpp);

		locStatGrid = new GridPane();
		locStatGrid.setHgap(5);
		ColumnConstraints columnLoc1 = new ColumnConstraints();
		columnLoc1.setMinWidth(gameScene.getWidth()*0.1);
		//column1.setPrefWidth(gameScene.getWidth()*0.3);
		ColumnConstraints columnLoc2 = new ColumnConstraints();
		columnLoc2.setMinWidth(gameScene.getWidth()*0.3);
		locStatGrid.getColumnConstraints().addAll(columnLoc1,columnLoc2);
		RowConstraints rowLoc2 = new RowConstraints();
		rowLoc2.setMinHeight(gameScene.getHeight()*0.1);
		locStatGrid.getRowConstraints().add(rowLoc2);
		locStatGrid.getRowConstraints().add(rowLoc2);

		opponentName.setFont(Font.font(defaultFont, FontWeight.BOLD, 16));
		localName.setFont(Font.font(defaultFont, FontWeight.BOLD, 16));
		opponentLevel.setFont(Font.font(defaultFont, 14));
		localLevel.setFont(Font.font(defaultFont, 14));
		opponentHealth.setFont(Font.font(defaultFont, 14));
		localHealth.setFont(Font.font(defaultFont, 14));

		opponentName.setAlignment(Pos.CENTER_LEFT);
		GridPane.setHalignment(localName, HPos.RIGHT);
		opponentLevel.setAlignment(Pos.CENTER_RIGHT);
		localLevel.setAlignment(Pos.CENTER_LEFT);
		GridPane.setHalignment(localLevel, HPos.RIGHT);
		opponentHealth.setAlignment(Pos.CENTER_RIGHT);
		localHealth.setAlignment(Pos.CENTER_LEFT);
		GridPane.setHalignment(localHealth, HPos.RIGHT);

		opponentHealthBar = new Rectangle();
		localHealthBar = new Rectangle();
		opponentHealthBar.setWidth(gameScene.getWidth()*0.3);
		localHealthBar.setWidth(gameScene.getWidth()*0.3);
		opponentHealthBar.setHeight(gameScene.getHeight()*0.1);
		localHealthBar.setHeight(gameScene.getHeight()*0.1);
		opponentHealthBar.setArcWidth(5);
		opponentHealthBar.setArcHeight(5);
		localHealthBar.setArcWidth(5);
		localHealthBar.setArcHeight(5);
		/*NumberBinding oppHealthBind = gameScene.property
		opponentHealthBar.prop*/

		opponentHealthBar.setEffect(new Lighting());
		localHealthBar.setEffect(new Lighting());

		opponentHealthBar.setFill(Color.DARKRED);
		localHealthBar.setFill(Color.DARKRED);

		oppStatGrid.add(opponentName, 0, 0);
		oppStatGrid.add(opponentLevel, 1, 0);
		oppStatGrid.add(opponentHealthBar, 0, 1);
		oppStatGrid.add(opponentHealth, 1, 1);

		locStatGrid.add(localLevel, 0, 0);
		locStatGrid.add(localName, 1, 0);
		locStatGrid.add(localHealthBar, 1, 1);
		locStatGrid.add(localHealth, 0, 1);

		AnchorPane.setTopAnchor(oppStatGrid, 10.0);
		AnchorPane.setLeftAnchor(oppStatGrid, 10.0);

		AnchorPane.setBottomAnchor(locStatGrid, 10.0);
		AnchorPane.setRightAnchor(locStatGrid, 10.0);

		this.getChildren().addAll(oppStatGrid,locStatGrid);

	}

	public void updateGraphics() {

	}

	public void healthBarBubbleAnimation() {

		int oppHealth = Integer.parseInt(opponentHealth.getText());
		int locHealth = Integer.parseInt(opponentHealth.getText());

		if (oppHealth != oldOppHealth) {
			Bounds boundsOpp = opponentHealthBar.getBoundsInLocal();
			double yPosOppTop = boundsOpp.getMinY();
			double yPosOppBottom = boundsOpp.getMinY() + boundsOpp.getWidth();
			double widthOpp = boundsOpp.getWidth();
			double xPosOpp = boundsOpp.getMinX();

			//if (widthOpp == )

			Random rand = new Random();
			int randXposOpp = rand.nextInt((int)Math.round(widthOpp));
			double randYposOpp = rand.nextInt(2)+1==1 ? yPosOppTop : yPosOppBottom;

			ParallelTransition bubbleOpp = healthBarBubbleTransition(xPosOpp + randXposOpp,randYposOpp);
		}


		Bounds boundsOpp = opponentHealthBar.getBoundsInLocal();
		Bounds boundsLoc = localHealthBar.getBoundsInLocal();
		double yPosOppTop = boundsOpp.getMinY();
		double yPosOppBottom = boundsOpp.getMinY() + boundsOpp.getWidth();
		double yPoslocTop = boundsLoc.getMinY();
		double yPosLocBottom = boundsLoc.getMinY() + boundsLoc.getWidth();
		double widthOpp = boundsOpp.getWidth();
		double xPosOpp = boundsOpp.getMinX();
		double widthLoc = boundsLoc.getWidth();
		double xPosLoc = boundsLoc.getMinX();

		Random rand = new Random();
		int randXposOpp = rand.nextInt((int)Math.round(widthOpp));
		int randXposLoc = rand.nextInt((int) Math.round(widthLoc));
		double randYposOpp = rand.nextInt(2)+1==1 ? yPosOppTop : yPosOppBottom;
		double randYposLoc = rand.nextInt(2)+1==1 ? yPoslocTop : yPosLocBottom;

		ParallelTransition bubbleOpp = healthBarBubbleTransition(xPosOpp + randXposOpp,randYposOpp);
		ParallelTransition bubbleLoc = healthBarBubbleTransition(xPosLoc + randXposLoc,randYposLoc);



		bubbleOpp.play();
		bubbleLoc.play();



	}

	private ParallelTransition healthBarBubbleTransition(double xPos, double yPos) {
		Circle bubble = new Circle();
		bubble.setCenterX(xPos);
		bubble.setCenterY(yPos);
		bubble.setRadius(3);
		Random rand = new Random();
		int colorSelect = rand.nextInt(4);
		Color bubbleCol = null;
		switch (colorSelect) {
			case 0:
				bubbleCol = Color.ORANGERED;
				break;
			case 1:
				bubbleCol = Color.RED;
				break;
			case 2:
				bubbleCol = Color.INDIANRED;
				break;
			case 3:
				bubbleCol = Color.DARKRED;
				break;
		}

		bubble.setStroke(bubbleCol);
		bubble.setStrokeDashOffset(0.5);
		bubble.setStrokeWidth(3);
		bubble.setFill(Color.TRANSPARENT);

		ScaleTransition scaleTrans = new ScaleTransition(Duration.millis(500), bubble);
		scaleTrans.setFromX(1);
		scaleTrans.setFromY(1);
		scaleTrans.setToX(2);
		scaleTrans.setToY(2);
		scaleTrans.setCycleCount(1);

		FadeTransition fadeTrans = new FadeTransition(Duration.millis(600), bubble);
		fadeTrans.setFromValue(1.0f);
		fadeTrans.setToValue(0.2f);
		fadeTrans.setCycleCount(1);

		ParallelTransition parallel = new ParallelTransition();
		parallel.getChildren().addAll(scaleTrans,fadeTrans);
		parallel.setCycleCount(1);

		return parallel;

	}

	public void healthBarLowAnimation() {
		Timeline timeline = new Timeline();
		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.setAutoReverse(true);
		KeyValue kvOpp = new KeyValue(opponentHealthBar.heightProperty(),opponentHealthBar.getHeight()-5);
		KeyValue kvLoc = new KeyValue(localHealthBar.heightProperty(),localHealthBar.getHeight()-5);
		KeyFrame kfOpp = new KeyFrame(Duration.millis(500), kvOpp);
		KeyFrame kfLoc = new KeyFrame(Duration.millis(500), kvLoc);
		timeline.getKeyFrames().addAll(kfOpp,kfLoc);
		timeline.play();
	}

	public void reduceHealth(int damage, int player) {
		if (player == OPPONENT) {
			int currentHealth = Integer.parseInt(opponentHealth.getText());
			int newHealth = currentHealth - damage;
			opponentHealth.setText(Integer.toString(newHealth));
			double reduction = opponentHealthBar.getWidth()*(((double)newHealth)/((double) currentHealth));
			healthBarDamageAnimation(0,reduction);
		} else if (player == LOCAL) {
			int currentHealth = Integer.parseInt(localHealth.getText());
			int newHealth = currentHealth - damage;
			localHealth.setText(Integer.toString(newHealth));
			double reduction = localHealthBar.getWidth()*(((double)newHealth)/((double) currentHealth));
			healthBarDamageAnimation(1,reduction);
		}
	}

	private void healthBarDamageAnimation(int player, double reduction) {
		Timeline timeline = new Timeline();
		timeline.setCycleCount(1);
		timeline.setAutoReverse(false);
		KeyValue kv = null;
		if (player == OPPONENT) {
			kv = new KeyValue(opponentHealthBar.widthProperty(),reduction);
		} else if (player == LOCAL) {
			kv = new KeyValue(localHealthBar.widthProperty(),reduction);
		}
		KeyFrame kf = new KeyFrame(Duration.millis(500), kv);
		timeline.getKeyFrames().add(kf);
		timeline.play();
	}



}
