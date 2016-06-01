package game;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import account.CharacterAttributes;
import javafx.beans.binding.NumberBinding;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
/**
 * A class which constructs the buttons for the user to choose a move.
 * Also contains the game dialog to inform the user of the state in which
 * the game is in.
 * <p> <STRONG> WARNING: This class could not be implemented for the
 * first release.</STRONG>
 * 
 * <p> <STRONG> Developed by </STRONG> <p>
 * Oliver Rushton
 * <p> <STRONG> Tested by </STRONG> <p>
 * Oliver Rushton
 * <p> <STRONG> Developed for </STRONG> <p>
 * BOSS
 * @author Oliver Rushton
 */
public class UserInputUI extends GridPane {

	private static final String movePath = "src/res/moves/allMoves.xml";
	private static final String fontFamily = "Calibri";

	private static final int BUTTON_WIDTH = 100;
	private static final int BUTTON_HEIGHT = 40;

	private Button button1;
	private Button button2;
	private Button button3;
	private Button button4;
	private Button quit;

	private Move move1;
	private Move move2;
	private Move move3;
	private Move move4;

	private TextFlow gameDialog;
	private HBox dialogBox = new HBox();
	private Move output;
	private boolean selected = false;
	private CharacterAttributes attributes;
	private Scene gameScene;
	private boolean lock = true;
	private Text message;
	/**
	 * Builds the grid pane to contain all the buttons and game dialog.
	 * 
	 * @param scene the game scene.
	 * @param attributes the user's attributes containing the player's moves.
	 */
	public UserInputUI(Scene scene, CharacterAttributes attributes) {
		this.attributes = attributes;
		this.gameScene = scene;
		message = new Text("Game started");
		gameDialog = new TextFlow(message);
		loadMoves();
		setupButtons();
		buildInputUI();
	}
	/**
	 * Builds the user input interface and sets the actions for the buttons.
	 */
	private void buildInputUI() {
		GridPane buttonGrid = new GridPane();
		buttonGrid.setPadding(new Insets(0));
		buttonGrid.add(button1, 0, 0);
		buttonGrid.add(button2, 0, 1);
		buttonGrid.add(button3, 1, 0);
		buttonGrid.add(button4, 1, 1);
		gameDialog.setMinSize(gameScene.getWidth()*(1/3) -20, 60);
		dialogBox.getChildren().add(gameDialog);
		dialogBox.setMinWidth(gameScene.getWidth()*(1/3));

		ColumnConstraints column1 = new ColumnConstraints();
		ColumnConstraints column2 = new ColumnConstraints();
		ColumnConstraints column3 = new ColumnConstraints();

		NumberBinding col1Bind = gameScene.widthProperty().multiply(0.5);
		column1.prefWidthProperty().bind(col1Bind);
		NumberBinding col2Bind = gameScene.widthProperty().multiply(0.3);
		column2.prefWidthProperty().bind(col2Bind);
		NumberBinding col3Bind = gameScene.widthProperty().multiply(0.2);
		column3.prefWidthProperty().bind(col3Bind);

		column3.setHalignment(HPos.RIGHT);
		this.getColumnConstraints().addAll(column1,column2,column3);
		this.add(buttonGrid, 0, 0);
		this.add(dialogBox, 1, 0);
		this.add(quit, 2, 0);
		this.setHgap(10);

		this.prefWidthProperty().bind(gameScene.widthProperty());
		this.setPadding(new Insets(10,10,10,10));
		this.setPrefHeight(80);
	}

	private void setupButtons() {
		button1 = new Button(move1.getName());
		button2 = new Button(move2.getName());
		button3 = new Button(move3.getName());
		button4 = new Button(move4.getName());

		button1.setTextAlignment(TextAlignment.CENTER);
		button2.setTextAlignment(TextAlignment.CENTER);
		button3.setTextAlignment(TextAlignment.CENTER);
		button4.setTextAlignment(TextAlignment.CENTER);

		button1.setPrefSize(BUTTON_WIDTH, BUTTON_HEIGHT);
		button2.setPrefSize(BUTTON_WIDTH, BUTTON_HEIGHT);
		button3.setPrefSize(BUTTON_WIDTH, BUTTON_HEIGHT);
		button4.setPrefSize(BUTTON_WIDTH, BUTTON_HEIGHT);

		button1.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				if (!lock) {
					output = move1;
					selected = true;
				}
			}
		});

		button1.setOnMouseEntered(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				DropShadow shadow = new DropShadow();
				button1.setEffect(shadow);
				setMoveDialog(move1);
			}
		});

		button1.setOnMouseExited(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				button1.setEffect(null);
				clearDialog();
				gameDialog.getChildren().add(message);
			}
		});

		button2.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				if (!lock) {
					output = move2;
					selected = true;
				}
			}
		});

		button2.setOnMouseEntered(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				DropShadow shadow = new DropShadow();
				button2.setEffect(shadow);
				setMoveDialog(move2);
			}
		});

		button2.setOnMouseExited(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				button2.setEffect(null);
				clearDialog();
				gameDialog.getChildren().add(message);
			}
		});

		button3.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				if (!lock) {
					output = move3;
					selected = true;
				}
			}
		});

		button3.setOnMouseEntered(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				DropShadow shadow = new DropShadow();
				button3.setEffect(shadow);
				setMoveDialog(move3);
			}
		});

		button3.setOnMouseExited(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				button3.setEffect(null);
				clearDialog();
				gameDialog.getChildren().add(message);
			}
		});

		button4.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				if (!lock) {
					output = move4;
					selected = true;
				}
			}
		});

		button4.setOnMouseEntered(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				DropShadow shadow = new DropShadow();
				button4.setEffect(shadow);
				setMoveDialog(move4);
			}
		});

		button4.setOnMouseExited(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				button4.setEffect(null);
				clearDialog();
				gameDialog.getChildren().add(message);
			}
		});

		//quit button
		quit = new Button("Forfeit\nGame");
		quit.setTextAlignment(TextAlignment.CENTER);
		quit.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				System.out.println("Attempting to close game");
				//TODO close the game
			}
		});

		quit.setOnMouseEntered(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				DropShadow shadow = new DropShadow();
				quit.setEffect(shadow);
			}
		});

		quit.setOnMouseExited(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				quit.setEffect(null);
			}
		});

	}
	/**
	 * Loads the move list to obtain the users moves from.
	 */
	private void loadMoves() {
		MoveList allMoves = new MoveList();
		File sourceFile = new File(movePath);
		JAXBContext jaxbContext;
		try {
			jaxbContext = JAXBContext.newInstance(MoveList.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			allMoves = (MoveList) jaxbUnmarshaller.unmarshal(sourceFile);
		} catch (JAXBException e) {
			//TODO handle exception
			e.printStackTrace();
		}

		//populate the moves
		//may have to test this as the moves might not be loaded in order
		move1 = allMoves.getMoves().get(attributes.getMove1());
		move2 = allMoves.getMoves().get(attributes.getMove2());
		move3 = allMoves.getMoves().get(attributes.getMove3());
		move4 = allMoves.getMoves().get(attributes.getMove4());

	}
	/**
	 * Gets the game dialog.
	 * @return the game dialog.
	 */
	public TextFlow getGameDialog() {
		return gameDialog;
	}
	/**
	 * Sets the game dialog.
	 * @param the new game dialog.
	 */
	public void setGameDialog(TextFlow gameDialog) {
		this.gameDialog = gameDialog;
	}
	/**
	 * Sets the game dialog to display the attributes of teh selected move.
	 * @param move
	 */
	private void setMoveDialog(Move move) {
		Text name = new Text(move.getName() + "\n");
		name.setFont(Font.font(fontFamily, FontWeight.BOLD, 14));
		Text type = new Text("Type: " + getTypeString(move.getType()) + "\n");
		Text value = new Text("Value: " + Double.toString(move.getValue()) + "\n");
		Text number = new Text("Number: " + Integer.toString(move.getNumber()) + "\n");

		gameDialog.getChildren().clear();
		gameDialog.getChildren().addAll(name,type,value,number);
	}
	/**
	 * Clears the game dialog.
	 */
	private void clearDialog() {
		gameDialog.getChildren().clear();
	}
	/**
	 * Converts the integer move type to a string of the move type.
	 * @param type the move type index.
	 * @return the name of the move type.
	 */
	private String getTypeString(int type) {
		String output = "";
		if (type == 0)
			output = "Damage";
		else if (type == 1)
			output = "Strengthen";
		else if (type == 2)
			output = "Weaken";

		return output;
	}
	/**
	 * Sets the message value for the game dialog.
	 * @param message the text to set the game dialog to.
	 */
	public void setMessage(String message) {
		this.message.setText(message);
	}
	/**
	 * checks if the move buttons are locked
	 * @return True if the move buttons are locked, false otherwise.
	 */
	public boolean isLock() {
		return lock;
	}
	/**
	 * Sets if the move buttons are locked or unlocked.
	 * @param the value to set lock to.
	 */
	public void setLock(boolean lock) {
		this.lock = lock;
	}
	
	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	
	public Move getChoice() {
		return output;
	}
	public Button getButton1() {
		return null;
	}

}
