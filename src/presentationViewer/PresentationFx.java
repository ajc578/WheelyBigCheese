package presentationViewer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.SubScene;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;
import parser.ExerciseInfo;
import parser.XMLParser;
/**
 * A class to allow building presentation data structures 
 * and then playing through them, accepting and responding
 * to user interacting
 * <p> <STRONG> Developed by </STRONG> <p>
 * Alexander Chapman
 * <p> <STRONG> Tested by </STRONG> <p>
 * Alexander Chapman and Oliver Rushton
 * <p> <STRONG> Developed for </STRONG> <p>
 * BOSS
 * @author Alexander Chapman
 */
public class PresentationFx{
	private String title, author, version, comment;
	private ArrayList<SlideFx> slides;
	private ArrayList<ExerciseInfo> exerciseDetails, completedExercises;
	private SlideFx currentSlide, previousSlide;
	private int sequencerCounter;
	private boolean playing, automode, visiblityUpdate;
	private int destination;
	private SubScene presentationPane;
	private Group contentPane;
	private ArrayList<ActionListener> actionListenerList;
	private Timeline timer;
	private ArrayList<Integer> mediaID = new ArrayList<Integer>();
	private static final int updatetime = 100;
	static final int quitDestination = -1;
	static final int reverseDestination = -2;
	static final int nonValidDestination = -3;
	static final int persistTimeStamp = -1;
	public static final int durationUnconfirmed = 1000000;
	/*---Fields ---
	 * title - the title of the presentation.
	 * author - the author property of the presentation
	 * version - the version number of the presentation
	 * comment - a description or other comment about the presentation
	 * slides - a list of all the slides in the presentation
	 * exercise - lists the exercise details of all slides.
	 * completedExercises - lists the exercise details of each completed exercise.
	 * currentSlide - a pointer to the slide that is currently playing
	 * previousSlide - a pointer to the last slide played.
	 * sequencerCounter - count how many milliseconds the presentation 
	 *                    currently is into the current slide
	 * playing - holds whether the presentation is currently being played or not
	 * automode - will be true when the presentation is playing on a timer,
	 *            or false when the presentation is advanced by user input
	 * visiblityUpdate - a flag used when the presentation is being played in
	 *                   manual mode, which indicates when a change has occurred
	 *                   in the sequencer
	 * destination - holds the id of the slide (or special escape character) that
	 *              will be advanced to
	 * presentationPane - the visual object returned by the viewer in which it 
	 *                    is displayed
	 * contentPane - the pane to which the content is added
	 * actionListenerList - actionlisteners which will be triggered when the 
	 *                      presentation finishes will have pointers in this list
	 * timer - pointer to the timer that triggers advancing the presentation in auto play
	 * mediaID - a list which holds the subset of slide content that is audio/video in order
	 *           to control it stopping
	 * ---Constants ---
	 * updatetime - how often (in milliseconds) the objects on the slide are checked
	 *              to see if they should be on the screen.
	 * quitDestination - escape character that signifies this destination will cause
	 *                   this presentation to end.
	 * reverseDestination - escape destination that means the presentation will proceed back
	 *                      to the previously played slide.
	 * nonValidDestination - a value that if taken, means the progression from this element
	 *                       should be ignored.
	 * durationUnconfirmed - a marker used to indicated that the duration an element should last for
	 * 						 should be set to match the media's play time
	 * persistTimeStamp - special value that means any slide or element with this as its
	 *                    duration will not disappear
	 */
	
	/** Load all the initial details for the presentation 
	 * @param title
	 * @param author
	 * @param version
	 * @param comment
	 */
	public PresentationFx(String title, String author, String version, String comment) {
		this.title = title;
		this.author = author;
		this.version = version;
		this.comment = comment;
		slides = new ArrayList<SlideFx>();
		completedExercises = new ArrayList<ExerciseInfo>();
		automode = true;
	}
	
	/**Build a Presentation object from a source file by invoking the parser
	 * 
	 * @param sourceFile - the name of the xml to be played
	 */
	public PresentationFx(String sourceFile) {
		
		XMLParser parser = new XMLParser(sourceFile);
		this.title = parser.getDocumentInfo().getTitle();
		System.out.println("pfx title:" + title);
		this.author = parser.getDocumentInfo().getAuthor();
		this.version =  parser.getDocumentInfo().getVersion();
		this.comment = parser.getDocumentInfo().getComment();
		slides = new ArrayList<SlideFx>();
		completedExercises = new ArrayList<ExerciseInfo>();
		this.addAllSlides(parser.getAllSlides());
		this.addExerciseDetails(XMLParser.retrieveWorkoutInfo(sourceFile).getExerciseList());
		automode = true;
	}
	
	/**Add multiple slides into the list of slides in this presentation
	 * @param newSlides - array list of slides to be added
	 */
	public void addAllSlides(ArrayList<SlideFx> allSlides) {
		slides = allSlides;
	}
	
	/**Add multiple slides into the list of slides in this presentation
	 * @param newSlides - array list of slides to be added
	 */
	public void addExerciseDetails(ArrayList<ExerciseInfo> exerciseDetails) {
		this.exerciseDetails = exerciseDetails;
	}
	
	/**Add a slide into the list of slides in this presentation
	 * @param newSlide
	 */
	public void addSlide(SlideFx newSlide){
		slides.add(newSlide);
	}
	
	/**
	 * @return the title of the presentation
	 */
	public String getTitle() {
		return title;
	}
	
	/**
	 * @return the author of the presentation
	 */
	public String getAuthor() {
		return author;
	}

	/**
	 * @return the version number of this presentation
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * @return the comment associated with this presentation
	 */
	public String getComment() {
		return comment;
	}
	
	/**
	 * @return the details of completed exercises, for use in achievements and points
	 */
	public ArrayList<ExerciseInfo> getCompletedExercises() {
		return completedExercises;
	}
	
	/**Method to be called when the presentation should be played, is not blocking,
	 * and will quickly return an object that can be embedded to view the presentation.
	 * @return presentationPane - the visual object upon which the presentation will be seen.
	 */
	public SubScene Play(double width,double height){
		
		contentPane = new Group();
		presentationPane = new SubScene(contentPane, width, height);
		//create the presentationPane  
		    
	    timer = new Timeline(new KeyFrame(
	            Duration.millis(updatetime),
	            ae -> sequenceContentVisibility()));
	    timer.setCycleCount(Animation.INDEFINITE);
	    //setup the timer to call the sequencer
	    
	    playing = true;
	    
	    destination = 0;

	    slideFinished();
	    //set the current slide to slide 0 (the first slide)
		//currentSlide = slides.get(0);
	    
	    sequenceContentVisibility();
	    //call the sequencer so as to make objects active at 0 time
	    if (automode)
	    	timer.play();
	    //start the timer if in autoplay mode
	    
	    return presentationPane;
	}
	
	
	/**
	 * Called to set the presentation to be advanced by the timer
	 */
	public void setAutoPlay(){
		automode = true;
		if (playing) timer.play();
	}
	
	/**
	 * Called to set the presentation to be advanced by external input
	 */
	public void setManualPlay(){
		automode = false;
		if (playing) timer.stop();
	}
	
	/**
	 * Called to advance through events in the presentation when in manual mode
	 * ignore calls to this function in auto mode
	 */
	public void advanceManualEvents(){
		if (!automode){
			visiblityUpdate = false;
			while (!visiblityUpdate){
				sequenceContentVisibility();
			};
			//call the sequencer repeatedly until a visible change occurs.
		}

	}

	/**Called by the timer, checks the timings of the slide and elements
	 * to determine whether to move on, or change whether an element is active.
	 */
	private void sequenceContentVisibility() {	
		if (playing){



			if (sequencerCounter >= currentSlide.getDuration() && currentSlide.getDuration() != persistTimeStamp){
				if (currentSlide.getDestination() != nonValidDestination){
					//if the slide has reached the end of its life span...					
					//specifies a new slide to move onto (and set a flag)
					visiblityUpdate = true;
					destination = currentSlide.getDestination();
					slideFinished();
				}
			}
			if(currentSlide != null){
				for(SlideContent i : currentSlide.getElements()){
					boolean prevVisibility = i.getContent().isVisible();
					
					//if the element should currently be visible
					//make sure it is, and if it is media make 
					//sure it is playing
					if(checkContentTimeRange(i)){
						i.getContent().setVisible(true);
						for (int j = 0; j < mediaID.size(); j++) {
							if (i.getElementID() == mediaID.get(j)) {
								if (((MediaFx) i).getPlayed() == false) {
									((MediaFx) i).play();
								}
							}
						}
					} 
					
					//if the element shouldn't currently be visible
					//make sure it is not, and if it is media make 
					//sure it isn't playing
					else {
						i.getContent().setVisible(false);
						for (int j = 0; j < mediaID.size(); j++) {
							if (i.getElementID() == mediaID.get(j)) {
								if (((MediaFx) i).getPlayed() == true) {
									((MediaFx) i).stop();
								}
							}
						} 
					}
					//set a flag when any elements state has been updated
					if (i.getContent().isVisible() != prevVisibility) visiblityUpdate = true;
				}
			}
			sequencerCounter+= updatetime;
		}
	}
	
	/**Checks whether an element is with the time it should be being displayed
	 * @param element - the slide content element too check
	 * @return boolean - is the current time within this elements active range?
	 */
	private boolean checkContentTimeRange(SlideContent element){
		
		updateMediaDuration(element);
		
		if ((sequencerCounter >= element.getStartTime() && 
				(sequencerCounter <= element.getEndTime() || element.getEndTime() == persistTimeStamp))){
			return true;
		}else return false;
	}
	
	/** For media elements without a confirmed duration entered this method will
	 * set the elements duration to be the length of the medias file, and, if necessary
	 * change the length of the slide to accommodate this.
	 * @param mediaElement
	 */
	private void updateMediaDuration(SlideContent element) {
		for (int i : mediaID) {
			if (element.getElementID() == i) {
				if(element.getDuration() == durationUnconfirmed) {
						element.setDuration(((MediaFx) element).getMediaDuration());
						if ((currentSlide.getDuration() >= durationUnconfirmed && element.getDuration() != durationUnconfirmed) || 
									currentSlide.getDuration() < element.getEndTime()) {
							currentSlide.setDuration(element.getEndTime());
							System.out.println("slide duration set to: " + element.getEndTime());
						}
				}
			} 
		}
	}
	
	/**The handler for the on click events that occur when a slide 
	 * content element is clicked. Proceeds the presentation to the
	 * destination of the clicked object if appropriate
	 * 
	 */
	EventHandler<MouseEvent> mouseHandler = new EventHandler<MouseEvent>() {
		@Override
		public void handle(MouseEvent me) {
			if (playing){
				for(SlideContent i : currentSlide.getElements()){
					if (me.getSource().equals(i.getContent())){
						//check all elements to see if they were the one that fired the click
						if (i.getTargetLoc() != nonValidDestination){
							destination = i.getTargetLoc();
							slideFinished();
						}
					}
				}
			}
		}
	};
	
	/**Whenever a slide has finished, the slide finished method
	 * sets up the presentation viewer to move onto the next 
	 * slide, or end the presentation as appropriate.
	 */
	private void slideFinished(){
		
		SlideFx tempSlide = currentSlide;
		
		//stop all old slide's media
		if (currentSlide != null){
			for (SlideContent i : currentSlide.getElements()) {
				for (int j = 0; j < mediaID.size(); j++) {
					if (i.getElementID() == mediaID.get(j)) {
						if (((MediaFx) i).getPlayed() == true) {
							((MediaFx) i).stop();
						}
					}
				} 
			}
			//and add data of finished exercise to the completed exercise list
			System.out.println("PerentationFx" + currentSlide);
			System.out.println("PerentationFx" + slides.indexOf(currentSlide));
			ExerciseInfo tempInfo = exerciseDetails.get(slides.indexOf(currentSlide));
			if (tempInfo.getName() != null){
				completedExercises.add(tempInfo);
			}
		}
		
		switch(destination){
			case quitDestination:
				//if the presentation has ended fire an action
				processEvent(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null));
				playing = false;
				currentSlide = null;
				break;
			case reverseDestination:
				currentSlide = previousSlide;
				break;
			default:
				//set the currentSlide pointer to the
				//destination slide.
				for (SlideFx i : slides) {
					if (i.getID() == destination){
						currentSlide = i;
						System.out.println("ID" + i.getID());
						break;
					}
				}					
		} 
		
		previousSlide = tempSlide;
		
		if (currentSlide != null){
			//set the background colour
			presentationPane.setFill(currentSlide.getbackgroundColour());
	
			//change the all the content pane elements to
			//those of the new slide instead of the old one
			contentPane.getChildren().clear();
			mediaID.clear();
			int j = 0;
			for (SlideContent i : currentSlide.getElements()) {
				contentPane.getChildren().add(i.createContent(presentationPane));
				i.getContent().setOnMouseClicked(mouseHandler);
				i.setElementID(j);
				if (i.getType() == "VideoFx" || i.getType() == "AudioFx") {
					mediaID.add(j);
				}
				j++;
			}
		}
		//reset the counter for the new slide
		sequencerCounter = 0;
		
	}
	
	/**
	 * The quit method can be called to stop the presentation 
	 */
	public void quit(){
		//stop all old slide's media
		if (currentSlide != null){
			for (SlideContent i : currentSlide.getElements()) {
				for (int j = 0; j < mediaID.size(); j++) {
					if (i.getElementID() == mediaID.get(j)) {
						if (((MediaFx) i).getPlayed() == true) {
							((MediaFx) i).stop();
						}
					}
				} 
			}
		}
		processEvent(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null));
		playing = false;
	}

	/**allows adding action listeners
	 * @param listener
	 */
	public synchronized void addActionListener(ActionListener listener) {
		if(actionListenerList == null) {
			actionListenerList = new ArrayList<ActionListener>(2);
		}
		if(!actionListenerList.contains(listener)) {
			actionListenerList.add(listener);
		}
	}

	/**allows removing action listeners.
	 * @param listener
	 */
	public synchronized void removeActionListener(ActionListener listener) {
		if(actionListenerList != null && actionListenerList.contains(listener)) {
			actionListenerList.remove(listener);
		}
	}

	/**Is required in order to fire the events on the added action listeners.
	 * @param e
	 */
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
