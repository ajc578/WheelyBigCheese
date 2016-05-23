package game;

import account.CharacterParts;
import javafx.beans.binding.NumberBinding;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class LayerCharacter extends Canvas {
	
	private static final String bodyDirectory = "src/res/images/";
	private static final String hairDirectory = "src/res/images/Hair/";
	private static final String eyesDirectory = "src/res/images/Eyes/";
	
	private GraphicsContext gc;
	
	private Image bodyImage;
	private Image hairImage;
	private Image eyesImage;
	
	
	
	public LayerCharacter(CharacterParts characterSource) {
		bodyImage = new Image(bodyDirectory + characterSource.getBodySource());
		hairImage = new Image(hairDirectory + characterSource.getHairSource());
		eyesImage = new Image(eyesDirectory + characterSource.getEyesSource());
		createCharacter();
	}
	
	private void createCharacter() {
		
		NumberBinding canvasWidth = this.widthProperty().multiply(0.4);
		this.heightProperty().bind(canvasWidth);
		NumberBinding canvasHeight = this.heightProperty().multiply(0.5);
		this.heightProperty().bind(canvasHeight);
		
		
		
		gc = this.getGraphicsContext2D();
		gc.setFill(Color.TRANSPARENT);
		gc.drawImage(bodyImage, 0, 0);
		gc.drawImage(hairImage, 0, 0);
		gc.drawImage(eyesImage, 0, 0);
	}
	
}
