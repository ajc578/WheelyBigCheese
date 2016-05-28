package game;

import javax.xml.bind.JAXBException;

import account.Account;
import account.AccountHandler;
import account.CharacterAttributes;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainGame {
	
	//game states
	private static final int START = 0, WAIT = 1, SELECT = 2, PERFORM_MOVE = 3, RECEIVE_MOVE = 4, END = 5;
	//message tags
	private static final String START_LOC = "strtLoc", START_OPP = "strtOpp", GAME_STARTED = "started";
	
	private int gameState = 0;

	private Account localAccount = new Account();
	private Account opponentAccount = new Account();

	private GameGraphics gg;
	private UserInputUI userIn;

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
		
		userIn = new UserInputUI(gameScene, attributes);
		root.setBottom(userIn);
		gg = new GameGraphics(gameScene, opponentAccount, localAccount);
		root.setCenter(gg);
		gameStage.setScene(gameScene);
		gameStage.sizeToScene();
		gameStage.setResizable(false);
		gameStage.show();

	
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		if ((START_LOC).equals(GAME_STARTED)) {
			
			Object output = processInput(null);
			
			System.out.println("The choosen move recognised in main game is: " + ((Move) output).getName());
		}

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
	
	public Object processInput(Object input) {
		Object output = null;
		String inputLine = "";
		if (input instanceof String) {
			inputLine = (String) input;
		}
		if (gameState == START) {
			if (input.equals(START_LOC)) {
				gameState = SELECT;
				userIn.setMessage("The Game has started.\n Please choose your first move");
				userIn.setLock(false);
			} else if (input.equals(START_OPP)) {
				gameState = RECEIVE_MOVE;
				userIn.setLock(true);
			}
			output = GAME_STARTED;
		} else if (gameState == WAIT) {
			if (input instanceof Move) {
				//set dialog text
				Move move = (Move) input;
				int source = 1;
				if (move.getType() == 1) 
					source = 0;
				
				gg.performMove(move, source);
				gameState = SELECT;
			}
		} else if (gameState == SELECT) {
			userIn.setLock(false);
			Thread checkThread = new Thread(new Runnable() {

				@Override
				public void run() {
					while (!userIn.isSelected()) {
						try {
							Thread.sleep(200);
						} catch (InterruptedException e) {}
					} 
					Move move = userIn.getChoice();
					int source = 0;
					if (move.getType() == 1)
						source = 1;

					gg.performMove(move, source);
					output = move;
					gameState = WAIT;
					userIn.setLock(true);
				}
				
			});
			while (!userIn.isSelected()) {
				
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {}
					
				
			}
			
			Move move = userIn.getChoice();
			int source = 0;
			if (move.getType() == 1)
				source = 1;

			gg.performMove(move, source);
			output = move;
			gameState = WAIT;
			userIn.setLock(true);
		}  else if (gameState == RECEIVE_MOVE) {
			
		} else if (gameState == END) {
			
		}
		
		return output;
	}

}
