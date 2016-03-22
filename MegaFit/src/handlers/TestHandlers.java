package handlers;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class TestHandlers extends Application {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		// TODO Auto-generated method stub
		stage.setTitle("Test Handlers");
		
		Group layout = new Group();
		
		Scene scene = new Scene(layout, 800, 600);
		
		//MediaFx videoPlayer = new MediaFx(0,0,0.1,0.1,0.5,0.5,"test.mp4",true,0);
		MediaFx audioPlayer = new MediaFx(0, 0, "yummy2.aac", true);
		//ImageFx image = new ImageFx(0, 0, 0.65, 0.1, 0.4, 0.3, "replay.png", 0);
		//TextFx text = new TextFx(0, 0, 0.1, 0.8, "<b>Hello!</b> This <i>is</i> a <b><i>test</i></b>...", "Calibri", 14, Color.DARKSLATEBLUE, 0);
		//ShadingFx shading = new ShadingFx(0, 0, 1, 1, Color.DARKBLUE, Color.ALICEBLUE);
		//PolygonFx polygon = new PolygonFx("Pentagon.csv",shading,Color.PLUM,Color.BROWN,true);
		
		//layout.getChildren().add(videoPlayer.createContent(scene));
		//layout.getChildren().add(image.createContent(scene));
		//layout.getChildren().add(text.createContent(scene));
		layout.getChildren().add(audioPlayer.createContent(scene));
		//layout.getChildren().add(polygon.createContent(scene));
		
		stage.setScene(scene);
		stage.sizeToScene();
		stage.show();
	}

}
