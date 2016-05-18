package game;

import account.Account;
import account.CharacterAttributes;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainGame {
	
	private Account localAccount = new Account();
	private Account opponentAccount = new Account();
	
	public MainGame() {
		constructGameInterface();
	}
	
	public MainGame(Account localAccount, Account opponentAccount) {
		this.localAccount = localAccount;
		this.opponentAccount = opponentAccount;		
		
		constructGameInterface();
	}
	
	private void constructGameInterface() {
		Stage gameStage = new Stage();
		gameStage.setTitle("MegaFit - Game");
		
		BorderPane root = new BorderPane();
		Scene gameScene = new Scene(root,400,300);
		//for testing
		CharacterAttributes attributes = new CharacterAttributes();
		attributes.setAgility(10);
		attributes.setEndurance(10);
		attributes.setStrength(10);
		attributes.setSpeed(10);
		attributes.setBaseAttack(0.15);
		attributes.setBaseDefense(0.15);
		attributes.setHealth(100);
		attributes.setEquippedItem(0);
		attributes.setMove1(0);
		attributes.setMove2(3);
		attributes.setMove3(5);
		attributes.setMove4(8);
		
		root.setBottom(new UserInputUI(gameScene, attributes));
		gameStage.setScene(gameScene);
		gameStage.sizeToScene();
		gameStage.setResizable(false);
		gameStage.show();
	}
	
}
