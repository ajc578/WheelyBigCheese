package presentationViewer;

/*
 * Author : 
 * Group: 4
 * Description: 
 */

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.media.MediaPlayer.Status;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
/**
 * This module creates a control panel to control a media player.
 * 				Functionality includes : play, pause, replay, mute, volume control
 * 										 and time control
 * <p> <STRONG> Developed by </STRONG> <p>
 * Oliver Rushton and Seb Pillon
 * 
 * @author Oliver Rushton and Seb Pillon
 * @version 2.2
 * @see MediaFx
 * @see MediaFxControls
 * 
 * 
 */
public class MediaFxControls extends GridPane {
	//DECLARE CONSTANTS
	private static final int BUTTONSIZE = 30;
	//corresponds to order of images in IMAGE_NAME
	private static final int PAUSE = 0, PLAY = 1, REPLAY = 2, VOLUME_ON = 3, MUTE = 4;
	private static final int PLAY_BUTTON = 0, VOLUME_BUTTON = 1; //control buttons in imageState
	private static final String[] IMAGE_NAME = {"pause.png","play.png","replay.png",
												"volume_on.png","mute.png"};
	//declare local variables
	private final MediaPlayer mediaPlayer;
	private boolean loop;
	//keeps track of image for each button
	private int[] imageState = {PAUSE,VOLUME_ON};
	private Duration mediaLength = Duration.ZERO;
	private Slider volumeSlider, mediaSlider;
	private Button playButton, volumeButton;
	private Label mediaTime;
	private int startTime;
	
	
	/**constructs the controls and adds them to the specified media element
	 * @param mediaPlayer
	 * @param loop
	 */
	public MediaFxControls(MediaPlayer mediaPlayer, boolean loop) {
		this.mediaPlayer = mediaPlayer;
		this.loop = loop;
		addControls();
	}
	
	
	/**
	 * Creates all the controls
	 */
	private void addControls() {
		//create play button
		playButton = new Button();
		playButton.setGraphic(new ImageView(loadImage(PLAY_BUTTON)));
		playButton.setShape(new Circle(0,0,BUTTONSIZE/2));
		playButton.setPrefSize(BUTTONSIZE, BUTTONSIZE);
		//shadow effect while button is being pressed
		playButton.setOnMousePressed(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				DropShadow shadow = new DropShadow();
				playButton.setEffect(shadow);
			}
		});
		//perform correct action when button is released
		playButton.setOnMouseReleased(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent e) {
                Status status = mediaPlayer.getStatus();
                playButton.setEffect(null);
                if (status == Status.PAUSED || status == Status.STOPPED ) {
                	if (mediaSlider.getValue() >= 99) {
                		mediaPlayer.stop();
                	}
                    mediaPlayer.play();
                    imageState[PLAY_BUTTON] = PAUSE;
                } else {
                    mediaPlayer.pause();
                    imageState[PLAY_BUTTON] = PLAY;
                }
                playButton.setGraphic(new ImageView(loadImage(PLAY_BUTTON))); //load next image
            }
        });
		
		mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE); //repeat as often as required
		mediaPlayer.setOnEndOfMedia(new Runnable() {
			@Override
			public void run() {
				//when media has finished, set replay button
				if (!loop) {
					imageState[PLAY_BUTTON] = REPLAY;
				} else {
					imageState[PLAY_BUTTON] = PAUSE;
					mediaPlayer.stop();
					mediaPlayer.play();
				}
				playButton.setGraphic(new ImageView(loadImage(PLAY_BUTTON)));
			}
		});
		//volume button for controlling mute/unmute
		volumeButton = new Button();
		volumeButton.setGraphic(new ImageView(loadImage(VOLUME_BUTTON)));
		volumeButton.setShape(new Circle(0,0,BUTTONSIZE/2));
		volumeButton.setPrefSize(BUTTONSIZE, BUTTONSIZE);
		volumeButton.setOnMousePressed(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				DropShadow shadow = new DropShadow();
				volumeButton.setEffect(shadow);
			}
		});
		volumeButton.setOnMouseReleased(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				volumeButton.setEffect(null);
				if (mediaPlayer.isMute()) {
					mediaPlayer.setMute(false);
					imageState[VOLUME_BUTTON] = VOLUME_ON;
				} else {
					mediaPlayer.setMute(true);
					imageState[VOLUME_BUTTON] = MUTE;
				}
				volumeButton.setGraphic(new ImageView(loadImage(VOLUME_BUTTON)));
			}
		});
		//slider to control volume
		volumeSlider = new Slider(0,100,100);
		volumeSlider.setBlockIncrement(10);
		volumeSlider.setOrientation(Orientation.HORIZONTAL);
		volumeSlider.setPrefSize(100, BUTTONSIZE);
		volumeSlider.valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
				mediaPlayer.setVolume(volumeSlider.getValue()/100); //takes a number between 0 and 1
				if (mediaPlayer.isMute()) {
					mediaPlayer.setMute(false);
					imageState[VOLUME_BUTTON] = VOLUME_ON;
				} 
				volumeButton.setGraphic(new ImageView(loadImage(VOLUME_BUTTON)));
			}
		});
		//mediaSlider controls the time in the mediaPlayer
		mediaSlider = new Slider(0,100,0);
		mediaSlider.setMinWidth(50);
		mediaSlider.setMaxWidth(Double.MAX_VALUE);
        mediaSlider.valueProperty().addListener(new InvalidationListener() {
        	@Override
        	public void invalidated(Observable ov) {
                if (mediaSlider.isValueChanging()) {
                	//if slider is dragged while media is finished, switch button graphic to play
                    if (mediaSlider.getValue() >= 99 && !loop) {
                    	mediaPlayer.pause();
                    	imageState[PLAY_BUTTON] = PLAY;
                    	playButton.setGraphic(new ImageView(loadImage(PLAY_BUTTON)));
                    }
                    // multiply duration by percentage calculated by slider position
                	Duration currentTime = mediaLength.multiply(mediaSlider.getValue() / 100);
                    mediaPlayer.seek(currentTime);
                    mediaTime.setText(createTimeLabel(currentTime) + "/" + createTimeLabel(mediaLength));
                }
            }
        });
        //as mediaPlayer time changes, we update the media slider position and time label
		mediaPlayer.currentTimeProperty().addListener(new InvalidationListener() {
			@Override
			public void invalidated(Observable ov) {
				Duration currentTime = mediaPlayer.getCurrentTime();
				if (currentTime.toSeconds() == 0.0 && loop) {
					mediaPlayer.play();
					System.out.println("played because time changed and loop");
				}
				mediaSlider.setValue(currentTime.toMillis()/mediaLength.toMillis()*100);
				mediaTime.setText(createTimeLabel(currentTime) + "/" + createTimeLabel(mediaLength));
			}
		});
		//gets the length of the media and positions media slider and creates time label
	 	mediaPlayer.setOnReady(new Runnable() {
			 	@Override
	            public void run() {
	                mediaLength = mediaPlayer.getMedia().getDuration();
	                mediaSlider.setDisable(mediaLength.isUnknown());
                    if (!mediaSlider.isDisabled()
                            && mediaLength.greaterThan(Duration.ZERO)
                            && !mediaSlider.isValueChanging()) {
                    	Duration currentTime = mediaPlayer.getCurrentTime();
        				mediaSlider.setValue(currentTime.toMillis()/mediaLength.toMillis()*100);
        				mediaTime.setText(createTimeLabel(currentTime) + "/" + createTimeLabel(mediaLength));
                    } 
                    //pause and wait to be played by PresentationFx
                    if (getStartTime() > 0) {
                    	mediaPlayer.pause();
                    }
                    
	            }
        });
	 	//sets style for mediaTime label
	 	mediaTime = new Label();
	 	mediaTime.setStyle("-fx-font: 12 arial;-fx-background-color: #e6e6e6; -fx-border-color: #b3b3b3;");
		//labels used as filler labels
	 	Label filler1 = new Label("");
 		GridPane.setHgrow(filler1, Priority.ALWAYS); //take up any excess space
 		Label filler2 = new Label(" ");
 		GridPane.setHgrow(filler2, Priority.ALWAYS);
 		GridPane.setHgrow(mediaSlider, Priority.ALWAYS);
		
 		//sets gap of rows and columns
 		this.setHgap(1);
 		this.setVgap(1);
 		//create column constraints for gridpane
 		ColumnConstraints growColumn = new ColumnConstraints(10,10,Double.MAX_VALUE);
 		growColumn.setFillWidth(true);
 		growColumn.setHgrow(Priority.ALWAYS);
		ColumnConstraints fixedButtonColumn = new ColumnConstraints(BUTTONSIZE+20,playButton.getWidth(),100);
		fixedButtonColumn.setHgrow(Priority.NEVER);
		ColumnConstraints fixedVolumeSliderColumn = new ColumnConstraints(BUTTONSIZE+15,volumeSlider.getWidth(),100);
		fixedVolumeSliderColumn.setHgrow(Priority.NEVER);
		ColumnConstraints fixedTimeLabelColumn = new ColumnConstraints(BUTTONSIZE+15,100,120);
		fixedTimeLabelColumn.setHgrow(Priority.NEVER);
		//add constraints to gridpane
		this.getColumnConstraints().addAll(growColumn,fixedButtonColumn,fixedButtonColumn,fixedVolumeSliderColumn,fixedTimeLabelColumn,growColumn);
		//add nodes to gridpane in corresponding columns
		this.add(mediaSlider, 0, 0, 6, 1);
		this.add(filler1,0,1);
		this.add(playButton,1,1);
		this.add(volumeButton,2,1);
		this.add(volumeSlider,3,1);
		this.add(mediaTime,4,1);
		this.add(filler2,5,1);
		
	}

	/**
	 * manual Play method
	 */
	public void play() {
		mediaPlayer.play();
		imageState[PLAY_BUTTON] = PAUSE;
    	playButton.setGraphic(new ImageView(loadImage(PLAY_BUTTON)));
	}
	
	/**manual stop method*/
	public void stop() {
		mediaPlayer.pause();
		mediaPlayer.stop();
		imageState[PLAY_BUTTON] = REPLAY;
    	playButton.setGraphic(new ImageView(loadImage(PLAY_BUTTON)));
	}
	
	/**disposes the media player object*/
	public void dispose() {
		mediaPlayer.dispose();
	}
	
	
	/**define how far into the media the playback should start
	 * @param startTime
	 */
	public void setStartTime(int startTime) {
		this.startTime = startTime;
	}
	
	/**return how far into the media the playback should start
	 * @return startTime
	 */
	private int getStartTime() {
		return startTime;
	}
	
	/**return how long the media file is
	 * @return duration (milliseconds)
	 */
	public Integer getDuration() {
		if (mediaLength != Duration.ZERO) {
			return (int) mediaLength.toMillis();
		} else {
			return PresentationFx.durationUnconfirmed;
		}
	}
	
	
	/**generates a timestamp for placing on the controls
	 * @param time
	 * @return timeLabel (String)
	 */
	private String createTimeLabel(Duration time) {
		int hours,minutes,seconds;
		//calculates a 'Duration' in hours/minutes/seconds
		hours = (int) Math.floor(time.toHours());
		minutes = (int) (Math.floor(time.toMinutes()) - (hours*60));
		seconds = (int) (Math.floor(time.toSeconds()) - (hours*60*60) - (minutes*60));
		
		return String.format("%02d:%02d:%02d",hours,minutes,seconds);
	}
	
	
	/** Loads the images required for the control buttons and places them
	 * @param button
	 * @return Image
	 */
	private Image loadImage(int button) {
		Image controlIcon = null;
		try {
			controlIcon = new Image(getClass().getResourceAsStream("/res/images/" + IMAGE_NAME[imageState[button]]),BUTTONSIZE,BUTTONSIZE,true,true);
		} catch (NullPointerException npe) {
			System.out.println("The image '" + IMAGE_NAME[imageState[button]] + "' could not be found using the relative path: /images/" + IMAGE_NAME[imageState[button]]);
		}
		return controlIcon;
	}
	
}
