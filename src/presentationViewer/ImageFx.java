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
import javafx.scene.SubScene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;



public class ImageFx extends SlideContent {
	
	private double xPos,yPos,width,height;
	private String sourceFile;
	private ImageView content;
	
	/*Data Fields:
	 * x_pos - (integer between 0 and 1) proportion across the slide the left edge of the image should be
	 * y_pos - (integer between 0 and 1) proportion across the slide the top edge of the image should be
	 * width - (integer between 0 and 1) proportion across the slide the image should span
	 * height- (integer between 0 and 1) proportion down the slide the image should span
	 * sourceFile - the name of the file that contains the actual image 
	 * content- the ImageView content that will be placed in the slide
	 * */
	
	/**
	 * The constructor sets all the parameters which will be then read from the XML by the interpreter
	 * 
	 * @param startTime - the time in milliseconds into the slide before an element will present itself
	 * @param duration - the time in milliseconds that the slide will last
	 * @param x_pos - (integer between 0 and 1) proportion across the slide the left edge of the image should be
	 * @param y_pos - (integer between 0 and 1) proportion across the slide the top edge of the image should be
	 * @param width - (integer between 0 and 1) proportion across the slide the image should span
	 * @param height- (integer between 0 and 1) proportion down the slide the image should span
	 * @param sourceFile - the file that contains the actual image
	 * @param targetLoc - the destination to be navigated to when the element is clicked (a value of Presentation.quitDestination
	 *			  		  means the presentation will end, and a value of Presentation.nonValidDestination will cause the click to
	 *					  be ignored.)
	 **/
	
	public ImageFx(int startTime, int duration, double xPos, double yPos, 
					double width, double height,String sourceFile, Integer targetLoc) {
		super(startTime,duration,targetLoc);
		this.xPos = xPos;
		this.yPos = yPos;
		this.width = width;
		this.height = height;
		this.sourceFile = sourceFile;

	}
	
	/** The following method creates an Imageview object for display within
	 *  the specified scene.
	 * @param parent - the scene in which this image will be displayed
	 * @return An imageView object (which is a sub-type of Node)
	 * **/
	
	public Node createContent(SubScene parent) {
		Image image = null;
		try {
			image = new Image(getClass().getResourceAsStream("/res/images/" + sourceFile));
		} catch (NullPointerException npe) {
			System.out.println("The image '" + sourceFile + "' could not be found using the relative path: /res/images/" + sourceFile);
		}
		content = new ImageView(image);
		
		/*
		 * Binding is used to automatically adjust the x,y,width and height properties 
		 * of the image according to any changes
		 * */
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
	
	/**Obtains the content of the node
	 * @return content (node)
	 **/
	
	public Node getContent() {
		return content;
	}
	
	
	
	/**Obtains the type of the object
	 * @return "ImageFX" (String)
	 * **/
	public String getType() {
		return "ImageFx";
	}
		
}


