package game;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class GameTest extends Application {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

	@Override
	public void start(Stage mainStage) throws Exception {
		// TODO Auto-generated method stub
		mainStage = new Stage();
		mainStage.setTitle("Game Test");
		
		BorderPane root = new BorderPane();
		
		Scene scene = new Scene(root,800,600);
		
		Button startGame = new Button("Start Game!");
		startGame.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				MainGame game = new MainGame();
			}
		});
		
		root.setCenter(startGame);
		
		mainStage.setScene(scene);
		mainStage.sizeToScene();
		mainStage.show();
		
	}

}
