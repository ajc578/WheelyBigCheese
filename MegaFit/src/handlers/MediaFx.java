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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaException;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.util.Duration;

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
	
	public MediaFx(int startTime, int duration, String sourceFile, boolean loop) {
		super(startTime, duration, null);
		this.sourceFile = sourceFile; 
		this.mediaType = AUDIO;
	}
	
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
	    	mediaPlayer.pause();
	    } catch (MediaException m) {
	    	System.out.println("The source File '" + sourceFile + "' does not exist in the relative path: src/videos/" + sourceFile);
	    }
		if (mediaType == VIDEO) {
			setupPlayer();
			content.setVisible(false);
			return content;
		} else {
			
			
			vc = new MediaFxControls(mediaPlayer,loop);
			vc.prefWidthProperty().bind(parent.widthProperty());
			vc.setVisible(false);
			return vc;
		}
	}
	
	public Node getContent() {
		if (mediaType == VIDEO) {
			return content;
		} else {
			return vc;
		}
	}
	
	public String getType() {
		if (mediaType == VIDEO)
			return "VideoFx";
		else 
			return "AudioFx";
	}
	
	private void setupPlayer() {
		//set style of media player - needs to be re-written by each group
		content.setStyle("-fx-background-color: black; -fx-border-color: red; -fx-padding: 20");
		
		if (mediaType == VIDEO) {
			mediaView = new MediaView(mediaPlayer);
		    
			//bind mediaView width to that of its containers width
			mediaView.fitWidthProperty().bind(content.widthProperty());
			mediaView.fitHeightProperty().bind(content.heightProperty());
			//preserve aspect ratio and set scaling to smooth	
		    mediaView.setPreserveRatio(true);
		    mediaView.setSmooth(true);
		    createSizeListeners(); //adjusts mediaView dimensions to fit container
		    content.getChildren().add(mediaView);
		    
		    //instantiate video controls
			vc = new MediaFxControls(mediaPlayer,loop);
			vc.prefWidthProperty().bind(content.widthProperty());
			vc.setVisible(false);
			content.getChildren().add(vc);
		} 
	    
		
	}
	//returns Pane object
	private Pane getPane() {
		return content;
	}
	
	private void createSizeListeners() {
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
	//getter methods required by contract
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	//method required for tests
	public boolean getLoop() {
		return loop;
	}
	
	public MediaPlayer getMediaPlayer() {
		return mediaPlayer;
	}
	
	public void setPlayed(boolean played) {
		this.played = played;
	}
	
	public boolean getPlayed() {
		return played;
	}
	
	//media control methods required by contract
	public void play() {
		vc.play();
	}
	
	public void stop() {
		vc.stop();
	}
	
}
