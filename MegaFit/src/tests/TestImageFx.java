package tests;

/*
 * Author : Oliver Rushton
 * Group: 4
 * Description: Test ImageFx
 */

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import presentationViewer.ImageFx;

public class TestImageFx {

	Group layout = new Group();
	ImageFx image;
	Scene scene;
	ImageView imageView;
	
	@Rule public JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();
	
	@Before
	public void setUp() {
		scene = new Scene(layout,800,600);
		image = new ImageFx(0,0,0.1,0.2,0.4,0.4,"play.png",0);
		imageView = (ImageView) image.createContent(scene);
		layout.getChildren().add(imageView);
	}
	
	@Test
	public void testPositionAndDim() {
		assertEquals(imageView.getX(),80.0,0);
		assertEquals(imageView.getY(), 120.0,0);
		assertEquals(imageView.getFitWidth(), 320.0,0);
		assertEquals(imageView.getFitHeight(), 240.0,0);
	}
	
	@Test
	public void testContent() {
		assertNotNull(imageView);
	}
	

}
