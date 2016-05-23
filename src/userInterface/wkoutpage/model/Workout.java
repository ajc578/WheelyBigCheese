package userInterface.wkoutpage.model;


import java.time.LocalDate;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;

/**
 * Model class for a Workout
 *
 */
public class Workout {

    private final StringProperty title; // Title
    private final StringProperty creator; // Creator
    private final StringProperty time; // Time
    private final IntegerProperty totalWorkoutGainz; // Points
    private final ObjectProperty<Image> workoutIcon;

    public Workout() {
    	this(null, null);
    }
    
    /**
     * Constructor with some initial data.
     * 
     * @param firstName
     * @param lastName
     */
    public Workout(String title, String creator) {
        this.title = new SimpleStringProperty(title);
        this.creator = new SimpleStringProperty(creator);

        // Some initial dummy data, just for convenient testing.
        this.time = new SimpleStringProperty();
        this.totalWorkoutGainz = new SimpleIntegerProperty();
        this.workoutIcon = new SimpleObjectProperty<Image>();
    }
    
    public String getTitle() {
        return title.get();
    }

    public void setTitle(String firstName) {
        this.title.set(firstName);
    }

    public StringProperty titleProperty() {
        return title;
    }
    
    public String getCreator() {
        return creator.get();
    }

    public void setCreator(String creator) {
        this.creator.set(creator);
    }

    public StringProperty creatorProperty() {
        return creator;
    }
    
    public String getTime() {
        return time.get();
    }

    public void setTime(String time) {
        this.time.set(time);
    }

    public StringProperty timeProperty() {
        return time;
    }
    
    public int getTotalWorkoutGainz() {
        return totalWorkoutGainz.get();
    }

    public void setTotalWorkoutGainz(int totalWorkoutGainz) {
        this.totalWorkoutGainz.set(totalWorkoutGainz);
    }

    public IntegerProperty totalWorkoutGainzProperty() {
        return totalWorkoutGainz;
    }
    
    public Image getWorkoutIcon() {
        return workoutIcon.get();
    }

    public void setWorkoutIcon(Image workoutIcon) {
        this.workoutIcon.set(workoutIcon);
    }

    public ObjectProperty<Image> workoutIconProperty() {
        return workoutIcon;
    }
    
    
    
    
    
    
}
    