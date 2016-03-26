package handlers;

/*
 * Author: Oliver Rushton
 * Group: 4
 * Date finished: 03/03/2016
 * Description: This module instantiates a video player with video control functionality
 * 				and resizes the MediaView to fit its Pane, whilst maintaining aspect ratio 
 * 				and centring the MediaView
 */

import java.io.File;

import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.beans.binding.NumberBinding;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaException;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.util.Duration;

/**
 * MediaFx creates a media player capable of playing both audio (.wav) or video (.mp4) files.
 * <p>
 * A media control panel is returned from both constructors, but the constructor for video also 
 * return a media view in a pane. The media view maintains the aspect ratio of the video whilst
 * keeping it centred in the middle of the pane. NOTE: The audio player object does not have a 
 * position or dimensions unlike the video player. These attributes need to be handled by a 
 * <tt>layout manager</tt>.
 * <p>
 * This class extends <tt>SlideContent</tt> as with all classes in the <tt>handlers</tt> package.
 * As with the other classes, it's parent's parameters are overridden.
 * <p>
 * The source file parameter must be a String of the file name and it's extension. 
 * <p>
 * e.g. "video.mp4" or "audio.wav"
 * <p>
 * The source file must be in the video resources folder in the src directory of your project. 
 * <p>
 * i.e. Under src/res/videos/fileNameHere.extension
 * <p> <STRONG> Developed by </STRONG> <p>
 * Oliver Rushton
 * <p> <STRONG> Tested by </STRONG> <p>
 * Oliver Rushton
 * 
 * @author Oliver Rushton
 * @version 2.2
 * @see MediaView
 * @see MediaPlayer
 * @see MediaFxControls
 * 
 * 
 */

public class MediaFx extends SlideContent{
	
	private static final boolean VIDEO = true, AUDIO = false;
	
	private double x, y, width, height;
	private String sourceFile;
	private boolean loop, mediaType;
	private Pane content;
	
	private MediaPlayer mediaPlayer;
	private MediaView mediaView;
	private boolean endTransitionFinished = true;
	private MediaFxControls vc;
	private boolean played = false;
	
	/**
	 * Constructs a video player. The object however is not created until 
	 * the <tt>createNode</tt> method is called. Also <tt>mediaType</tt> is set to the final 
	 * static boolean <tt>VIDEO</tt>.
	 * 
	 * @param startTime time at which the video player appears on screen.
	 * @param duration duration of the video player on screen.
	 * @param x x position of the video player as a fraction of the screen's total width.
	 * @param y y position of the video player as a fraction of the screen's total height.
	 * @param width width of the video player as a fraction of the screen's total width.
	 * @param height height of the video player as a fraction of the screen's total height.
	 * @param sourceFile name of the .mp4 file to be played.
	 * @param loop boolean to control auto-replay of the media.
	 * @param targetLoc If the object is an intractable, this will contain the slideID to skip to. 
	 * Otherwise this will be null.
	 */
	public MediaFx(int startTime, int duration, double x, double y, double width, double height, String sourceFile, boolean loop, Integer targetLoc) {
		super(startTime, duration, targetLoc);
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.sourceFile = sourceFile;
		this.loop = loop;
		this.mediaType = VIDEO;
	}
	
	/**
	 * Constructs an audio player. The object however is not created until 
	 * the <tt>createNode</tt> method is called. Also <tt>mediaType</tt> is set to the final 
	 * static boolean <tt>AUDIO</tt>.
	 * 
	 * @param startTime time at which the audio player appears on screen.
	 * @param duration duration of the audio player on screen.
	 * @param sourceFile name of the .wav file to be played.
	 * @param loop boolean to control auto-replay of the media.
	 */
	public MediaFx(int startTime, int duration, String sourceFile, boolean loop) {
		super(startTime, duration, null);
		this.sourceFile = sourceFile; 
		this.mediaType = AUDIO;
	}
	/**
	 * This method creates the audio/video player depending on the boolean <tt>mediaType</tt>.
	 * The appropriate directory is used to retrieve the source file. If the <tt>mediaType</tt>
	 * is an instance of <tt>VIDEO</tt> then the <tt>content</tt> <tt>Pane</tt> is constructed
	 * and it's position and dimensions are bound to portions of that of the <tt>parent Scene</tt>.
	 * <p>
	 * A <tt>MediaPlayer</tt> is then constructed with the corresponding <tt>Media</tt>.
	 * <p>
	 * Again, if <tt>mediaType</tt> is an instance of <tt>VIDEO</tt>, the <tt>setupMediaView</tt> method
	 * is called and the <tt> parent Scene</tt> is returned as a <tt>Node</tt>.
	 * <p>
	 * If <tt>mediaType</tt> is an instance of <tt>AUDIO</tt>, a <tt>MediaFxControls</tt> object
	 * is constructed and it's width is bound to that of the <tt>parent Scene</tt>. This object is
	 * then returned as a <tt>Node</tt>.
	 * 
	 * @param parent the parent <tt>Scene</tt> in which the object will be shown.
	 * @return An audio/video player object cast as a <tt>Node</tt>.
	 * @throws MediaExecption if <tt>Media</tt> can't be constructed.
	 * @throws NullPointerException if source file cannot be found.
	 * @see Scene
	 * @see Node
	 * @see Alert
	 */
	public Node createContent(Scene parent) {
		//retrieves video file from source folder
		File f = null;
		if (mediaType == VIDEO) {
			f = new File("src/res/videos/"+ sourceFile);
			
			content = new Pane();
			//bind width and height of pane
			NumberBinding videoWidth = parent.widthProperty().multiply(width);
			content.prefWidthProperty().bind(videoWidth);
			NumberBinding videoHeight = parent.heightProperty().multiply(height);
			content.prefHeightProperty().bind(videoHeight);
			//bind x and y position of pane
			NumberBinding videoX = parent.widthProperty().multiply(x);
			content.layoutXProperty().bind(videoX);
			NumberBinding videoY = parent.heightProperty().multiply(y);
			content.layoutYProperty().bind(videoY);
		} else {
			f = new File("src/res/audio/"+ sourceFile);
		}
	    try {
	    	System.out.println(f.toURI().toString());
	    	Media media = new Media(f.toURI().toString()); //writes file as URI string to pass to media
	    	mediaPlayer = new MediaPlayer(media);
	    	mediaPlayer.setAutoPlay(true);
	    } catch (MediaException m) {
	    	System.out.println("The source File '" + sourceFile + "' does not exist in the relative path: src/videos/" + sourceFile);
	    	ExceptionFx ex = new ExceptionFx(m,AlertType.ERROR,"File Exception","File Not Found",
	    										"The file with name: " + sourceFile + " was not found in the source directory");
	    	ex.show();
	    }
		if (mediaType == VIDEO) {
			setupVideoPlayer();
			content.setVisible(false);
			return content;
		} else {
			vc = new MediaFxControls(mediaPlayer,loop);
			vc.setStartTime(this.getStartTime());
			vc.prefWidthProperty().bind(parent.widthProperty());
			vc.setVisible(false);
			return vc;
		}
	}
	/**
	 * Used to retrieve the object. If <tt>mediaType</tt> is an instance of <tt>VIDEO</tt>,
	 * the <tt>content Pane</tt> is returned. Otherwise the <tt>MediaFxControls</tt> object is
	 * returned.
	 * @return Node
	 */
	public Node getContent() {
		if (mediaType == VIDEO) {
			return content;
		} else {
			return vc;
		}
	}
	/**
	 * Used to retrieve the name of the type of <tt>MediaFx</tt> object created.
	 * @return String - "VideoFx" for a video player, "AudioFx" for an audio player.
	 */
	public String getType() {
		if (mediaType == VIDEO)
			return "VideoFx";
		else 
			return "AudioFx";
	}
	/**
	 * Constructs a <tt>MediaView</tt> with the media player and binds it's width and height
	 * attributes to that of the <tt>content Pane</tt>. The aspect ratio of the media is maintained and
	 * the <tt>MediaView</tt> is added to the <tt>content Pane</tt>.
	 * <p>
	 * A <tt>MediaFxControls</tt> object is instantiated for the control panel of the video player and
	 * is added to the <tt>content Pane</tt>.
	 * @see createListeners
	 */
	private void setupVideoPlayer() {
		//set style of media player - needs to be re-written by each group
		content.setStyle("-fx-background-color: black; -fx-border-color: red; -fx-padding: 20");
		
		mediaView = new MediaView(mediaPlayer);
	    
		//bind mediaView width to that of its containers width
		mediaView.fitWidthProperty().bind(content.widthProperty());
		mediaView.fitHeightProperty().bind(content.heightProperty());
		//preserve aspect ratio and set scaling to smooth	
	    mediaView.setPreserveRatio(true);
	    mediaView.setSmooth(true);
	    createListeners(); //adjusts mediaView dimensions to fit container
	    content.getChildren().add(mediaView);
	    
	    //instantiate video controls
		vc = new MediaFxControls(mediaPlayer,loop);
		vc.setStartTime(this.getStartTime());
		vc.prefWidthProperty().bind(content.widthProperty());
		vc.setVisible(false);
		content.getChildren().add(vc);
	}
	/**
	 * Retrieves the <tt>Pane</tt> on which the video player sits. This pane is used to get the
	 * dimensions of the video player.
	 * @return Pane - <tt>content</tt>
	 */
	private Pane getPane() {
		return content;
	}
	/**
	 * Adds listeners to the width and height properties of the <tt>content Pane</tt> which call
	 * the <tt>adjustMediaDim</tt> method to resize the <tt>MediaView</tt>.
	 * <p>
	 * Also sets up mouse listeners on the <tt>content Pane</tt> to control the fade in/out transitions
	 * of the video control panel.
	 * @see adjustMediaDim
	 */
	private void createListeners() {
		//container width listener
		content.widthProperty().addListener(new ChangeListener<Number>() {
			@Override public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneWidth, Number newSceneWidth) {
				adjustMediaDim();
			}
		});
		//container height listener
		content.heightProperty().addListener(new ChangeListener<Number>() {
			@Override public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneHeight, Number newSceneHeight) {
				adjustMediaDim();
			}
		});
		
		//instantiate event handlers to show/hide video controls
		content.setOnMouseMoved(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				if (endTransitionFinished) {
					vc.setVisible(true);
					vc.setLayoutY(getPane().getHeight() - 70); //always appear off from off bottom of screen
					//appear transition
					FadeTransition appear = new FadeTransition(Duration.millis(350), vc);
					appear.setCycleCount(1);
					appear.setFromValue(0.0);
					appear.setToValue(1.0);
					appear.play();
					endTransitionFinished = false;
				}
				
			}
		});
		
		content.setOnMouseExited(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				//disappear transition
				FadeTransition fade = new FadeTransition(Duration.millis(350), vc);
				fade.setCycleCount(1);
				fade.setFromValue(1.0);
				fade.setToValue(0.0);
				fade.play();
				//at end of transition sets video controls invisible
				fade.setOnFinished(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent e2) {
						vc.setVisible(false);
						endTransitionFinished = true;	
					}
				});
			}
		});
		//adjusts the MediaView to be in centre of screen
		mediaPlayer.setOnPlaying(new Runnable() {
			@Override
			public void run() {
				adjustMediaDim();
			}
		});
		
	}
	/**
	 * Since the aspect ratio of the media is locked, the <tt>content Pane</tt> might be bigger than
	 * the <tt>MediaView</tt>. So this maintains the position of the <tt>MediaView</tt> in the middle 
	 * of the <tt>content Pane</tt>.
	 */
	private void adjustMediaDim() {
		Platform.runLater(new Runnable() {
            public void run() {
            	//if container width or height is larger than media view port, position media in middle of screen
	           	if (getPane().getWidth() > mediaView.getBoundsInParent().getWidth()) {
	           		mediaView.setLayoutX((getPane().getWidth() - mediaView.getBoundsInParent().getWidth())/2);
	           		mediaView.setLayoutY(0);
	           	} else if (getPane().getHeight() > mediaView.getBoundsInParent().getHeight()) {
	           		mediaView.setLayoutY((getPane().getHeight() - mediaView.getBoundsInParent().getHeight())/2);
	           		mediaView.setLayoutX(0);
	           	}  
            }
		 });
	}
	/**
	 * Retrieves x position of the media player. Can only be used if the object is a video player.
	 * @return x position of the media player as a fraction of the screen's total width.
	 */
	//getter methods required by contract
	public double getX() {
		return x;
	}
	/**
	 * Retrieves y position of the media player. Can only be used if the object is a video player.
	 * @return y position of the media player as a fraction of the screen's total height.
	 */
	public double getY() {
		return y;
	}
	/**
	 * Retrieves the loop property of the media player.
	 * @return boolean <tt>loop</tt> controlling media auto-replay.
	 */
	//method required for tests
	public boolean getLoop() {
		return loop;
	}
	/**
	 * Retrieves the value of the media's length
	 * @return duration of media
	 */
	public Integer getMediaDuration() {
		return vc.getDuration();
	}
	/**
	 * Retrieves the media player of the object.
	 * @return the javaFX <tt>MediaPlayer</tt> object.
	 */
	public MediaPlayer getMediaPlayer() {
		return mediaPlayer;
	}
	/**
	 * Retrieves the value of <tt>played</tt>.
	 * @return boolean representing if the player has been played already by the sequencer 
	 * in <tt>PresentationFx</tt>.
	 * @see PresentationFx
	 */
	public boolean getPlayed() {
		return played;
	}
	/**
	 * Plays the media in the player.
	 */
	//media control methods required by contract
	public void play() {
		vc.play();
		played = true;
	}
	/**
	 * Stops the media in the player.
	 */
	public void stop() {
		//if (mediaType == VIDEO) 
			//vc.stop();
		//else
			vc.dispose();
	}
	/**
	 * Disposes of the media player object
	 */
	public void dispose() {
		vc.dispose();
	}
	
}
