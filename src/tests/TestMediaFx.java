package tests;
/*
 * Author : Oliver Rushton
 * Group  : 4
 * Date Finished : 07/03/2016
 * COMMENTS
 * I tested the buttons and slider's functionality with visual tests.
 * e.g. If I pressed pause, would the video pause.
 * e.g. If I set loop to true, would the video player automatically replay itself.
 * 
 */
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import presentationViewer.MediaFx;

public class TestMediaFx {

	Group layout = new Group();
	MediaFx video;
	MediaFx audio;
	SubScene scene;
	Pane videoPane;
	GridPane audioGridPane;

	@Rule public JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();

	@Before
	public void setUp() {
		scene = new SubScene(layout,800,600);
		video = new MediaFx(0,0,0.1,0.2,0.4,0.4,"test.mp4",true,0);
		audio = new MediaFx(0,0,"yummy.mp3",false);
		videoPane = (Pane) video.createContent(scene);
		audioGridPane = (GridPane) audio.createContent(scene);
		layout.getChildren().add(videoPane);
		layout.getChildren().add(audioGridPane);
	}

	@Test
	public void testPositionAndDim() {
		assertEquals(videoPane.getLayoutX(),80.0,0);
		assertEquals(videoPane.getLayoutY(), 120.0,0);
		assertEquals(videoPane.getPrefWidth(), 320.0,0);
		assertEquals(videoPane.getPrefHeight(), 240.0,0);
	}

	@Test
	public void testLoop() {
		assertTrue(video.getLoop());
		assertFalse(audio.getLoop());
	}

	@Test
	public void testContent() {
		assertNotNull(videoPane);
		assertNotNull(audioGridPane);
	}



}
