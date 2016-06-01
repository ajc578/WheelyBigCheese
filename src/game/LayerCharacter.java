package game;

import account.CharacterParts;
import javafx.beans.binding.NumberBinding;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
/**
 * A class used to create the avatar from the provided character parts.
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
public class LayerCharacter extends Canvas {

	private static final String bodyDirectory = "res/images/";
	private static final String hairDirectory = "res/images/Hair/";
	private static final String eyesDirectory = "res/images/Eyes/";

	private GraphicsContext gc;

	private Image bodyImage;
	private Image hairImage;
	private Image eyesImage;

	/**
	 * Creates the images of the body parts from the paths provided.
	 * @param characterSource contains the paths to the images of the avatar.
	 */
	public LayerCharacter(CharacterParts characterSource) {
		bodyImage = new Image(bodyDirectory + characterSource.getBodySource());
		hairImage = new Image(hairDirectory + characterSource.getHairSource());
		eyesImage = new Image(eyesDirectory + characterSource.getEyesSource());
		createCharacter();
	}
	/**
	 * Draws the images on the canvas.
	 */
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
