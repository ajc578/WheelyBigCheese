package userInterface;

import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Screen;
import javafx.stage.Stage;


	public class Main extends Application {
		
		double screenWidth;
		double screenHeight;
		
		public void start(Stage primaryStage) {
			try {
				Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
				
				BorderPane root = new BorderPane();				
				Scene scene = new Scene(root,400,400);
				
				//temporary addition for my personal convenience when trying to run code
				//configure so that pressing escape at anytime closes the application
				//-Lexxy
				scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			        public void handle(KeyEvent ke) {
			            if (ke.getCode() == KeyCode.ESCAPE) {
			                primaryStage.close();
			            }
			        }
			    });
				
				scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
				primaryStage.setScene(scene);
				primaryStage.setFullScreen(true);
				primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
				primaryStage.show();
				screenWidth = primaryScreenBounds.getWidth();
				screenHeight = primaryScreenBounds.getHeight();
				LoginMenu loginMenu = new LoginMenu(screenWidth, screenHeight, root);
				root.setTop(loginMenu);
				//Menu menu = new Menu(screenWidth, screenHeight, root);
				//root.setTop(menu);
				
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		public static void main(String[] args) {
			launch(args);
		}
		

		
	}

