package handlers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.MediaPlayer.Status;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class PresentationFx{
	private String title, author, version, comment;
	private ArrayList<SlideFx> slides;
	private SlideFx currentSlide;
	private int sequencerCounter;
	private boolean playing;
	private int destination;
	private Scene presentationPane;
	private Group contentPane;
	private ArrayList<ActionListener> actionListenerList;
	private ArrayList<Integer> mediaID = new ArrayList<Integer>();
	private static final int updatetime = 100;
	static final int quitDestination = -1;
	static final Integer nonValidDestination = null;
	static final int persistTimeStamp = -1;
	
	public PresentationFx(String title, String author, String version, String comment) {
		this.title = title;
		this.author = author;
		this.version = version;
		this.comment = comment;
		slides = new ArrayList<SlideFx>();
	}
	
	public void addAllSlides(ArrayList<SlideFx> allSlides) {
		for (int i = 0; i < allSlides.size(); i++) {
			slides.add(allSlides.get(i));
		}
	}
	
	public void addSlide(SlideFx newSlide){
		slides.add(newSlide);
	}
	
	public String getTitle() {
		return title;
	}

	public String getAuthor() {
		return author;
	}

	public String getVersion() {
		return version;
	}

	public String getComment() {
		return comment;
	}
	
	public Scene Play(){
		
		contentPane = new Group();
		presentationPane = new Scene(contentPane, 300, 250);
		presentationPane.setFill(Color.WHEAT);
		    
		
		
	    Timeline timeline = new Timeline(new KeyFrame(
	            Duration.millis(updatetime),
	            ae -> sequenceContentVisibility()));
	    timeline.setCycleCount(Animation.INDEFINITE);
	    
	    playing = true;
	    destination = 0;
	    slideFinished();
	    
	    sequenceContentVisibility();
	    timeline.play();
	    
	    return presentationPane;
	}
	

	private void sequenceContentVisibility() {	
		if (playing){	
			if (sequencerCounter >= currentSlide.getDuration() && currentSlide.getDuration() != persistTimeStamp){
				if (currentSlide.getDestination() != nonValidDestination){
					destination = currentSlide.getDestination();
					slideFinished();
				}
			}
			for(SlideContent i : currentSlide.getElements()){
				if(checkContentTimeRange(i)){
					i.getContent().setVisible(true);
					for (int j = 0; j < mediaID.size(); j++) {
						if (i.getElementID() == mediaID.get(j)) {
							if (((MediaFx) i).getPlayed() == false) {
								((MediaFx) i).play();
								((MediaFx) i).setPlayed(true);
							}
						}
					}
				} else {
					i.getContent().setVisible(false);
					for (int j = 0; j < mediaID.size(); j++) {
						if (i.getElementID() == mediaID.get(j)) {
							if (((MediaFx) i).getPlayed() == true) {
								((MediaFx) i).stop();
							}
						}
					}
				}
			}
			sequencerCounter+= updatetime;
		}
	}
	
	private boolean checkContentTimeRange(SlideContent element){
		if ((sequencerCounter >= element.getStartTime() && 
				(sequencerCounter <= element.getEndTime() || element.getEndTime() == persistTimeStamp))){
			return true;
		}else return false;
	}
	
	EventHandler<MouseEvent> mouseHandler = new EventHandler<MouseEvent>() {

		@Override
		public void handle(MouseEvent me) {
			if (playing){
				for(SlideContent i : currentSlide.getElements()){
					if (me.getSource().equals(i.getContent())){
						if (i.getTargetLoc() != nonValidDestination){
							destination = i.getTargetLoc();
							slideFinished();
						}
					}
				}
			}
		}
	};
	
	private void slideFinished(){
		if (destination != quitDestination){
			for (SlideFx i : slides) {
				if (i.getID() == destination){
					currentSlide = i;
					break;
				}
			}
			contentPane.getChildren().clear();
			int j = 0;
			for (SlideContent i : currentSlide.getElements()) {
				contentPane.getChildren().add(i.createContent(presentationPane));
				i.getContent().setOnMouseClicked(mouseHandler);
				i.setElementID(j);
				if (i.getType() == "VideoFx" || i.getType() == "AudioFx") {
					mediaID.add(j);
					((MediaFx) i).setPlayed(false);
				}
				j++;
			}
			sequencerCounter = 0;

		}
		else{
			processEvent(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null));
			playing = false;
		}
	}

	public synchronized void addActionListener(ActionListener listener) {
		if(actionListenerList == null) {
			actionListenerList = new ArrayList<ActionListener>(2);
		}
		if(!actionListenerList.contains(listener)) {
			actionListenerList.add(listener);
		}
	}

	public synchronized void removeActionListener(ActionListener listener) {
		if(actionListenerList != null && actionListenerList.contains(listener)) {
			actionListenerList.remove(listener);
		}
	}

	@SuppressWarnings("unchecked")
	private void processEvent(ActionEvent e) {
		ArrayList<ActionListener> list;

		synchronized(this) {
			if(actionListenerList == null) return;
			list = (ArrayList<ActionListener>)actionListenerList.clone();
		}

		for(ActionListener listener: list) {
			listener.actionPerformed(e);
		}
	}

	
}
