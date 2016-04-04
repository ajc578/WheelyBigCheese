package presentationViewer;

/*
 * Author : Oliver Rushton and Seb Pillon
 * Group: 4
 * Description: This module creates a imageview and maintains its relative position
 * 				and height to that of the scene using bindings. Also the apsect
 * 				ratio is maintained.
 */


import javafx.beans.binding.NumberBinding;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ImageFx extends SlideContent {
	
	private double xPos,yPos,width,height;
	private String sourceFile;
	private ImageView content;
	
	public ImageFx(int startTime, int duration, double xPos, double yPos, 
					double width, double height,String sourceFile, Integer targetLoc) {
		super(startTime,duration,targetLoc);
		this.xPos = xPos;
		this.yPos = yPos;
		this.width = width;
		this.height = height;
		this.sourceFile = sourceFile;

	}
	
	public Node createContent(Scene parent) {
		Image image = null;
		try {
			image = new Image(getClass().getResourceAsStream("/res/images/" + sourceFile));
		} catch (NullPointerException npe) {
			System.out.println("The image '" + sourceFile + "' could not be found using the relative path: /res/images/" + sourceFile);
		}
		content = new ImageView(image);
		
		NumberBinding imageWidth = parent.widthProperty().multiply(width);
		content.fitWidthProperty().bind(imageWidth);
		NumberBinding imageHeight = parent.heightProperty().multiply(height);
		content.fitHeightProperty().bind(imageHeight);
		NumberBinding imageX = parent.widthProperty().multiply(xPos);
		content.xProperty().bind(imageX);
		NumberBinding imageY = parent.heightProperty().multiply(yPos);
		content.yProperty().bind(imageY);
	
		content.setPreserveRatio(true);
		content.setSmooth(true);
		content.setVisible(false);
		return content;
	}
	
	public Node getContent() {
		return content;
	}
	
	public String getType() {
		return "ImageFx";
	}
		
}
