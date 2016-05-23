package game;

import javax.xml.bind.JAXBException;

import account.Account;
import account.AccountHandler;
import account.CharacterAttributes;
import account.Protocol;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainGame {

	private Account localAccount = new Account();
	private Account opponentAccount = new Account();

	private GameGraphics gg;

	public MainGame() {
		loadAccounts();

		constructGameInterface();
	}

	public MainGame(Account localAccount, Account opponentAccount) {
		loadAccounts();

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
		gg = new GameGraphics(gameScene, opponentAccount, localAccount);
		root.setCenter(gg);
		gameStage.setScene(gameScene);
		gameStage.sizeToScene();
		gameStage.setResizable(false);
		gameStage.show();

		gg.healthBarLowAnimation();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		gg.reduceHealth(50, 0);
		gg.healthBarBubbleAnimation();

	}

	private void loadAccounts() {
		String opponentName = "IncredibleBulk";
		String localName = "Roidacious";

		String opponentNumber = AccountHandler.generateAccountNum(opponentName);
		String localNumber = AccountHandler.generateAccountNum(localName);

		try {
			localAccount = AccountHandler.accountLoad("src/res/clientAccounts/", opponentNumber);
			opponentAccount = AccountHandler.accountLoad("src/res/clientAccounts/", localNumber);
		} catch (JAXBException j) {
			j.printStackTrace();
		}

		System.out.println("Local account retrieved proof in MainGame: " + localAccount.getCharacterAttributes());

	}

}
