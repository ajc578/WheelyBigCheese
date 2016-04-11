package userInterface.wkoutpage.model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Helper class to wrap a list of workouts. This is used for saving the
 * list of workouts to XML.
 * 
 * @author Marco Jakob
 */
// Define name of root element
@XmlRootElement(name = "workouts")
public class WorkoutListWrapper {

    private List<Workout> workouts;

    @XmlElement(name = "workout")
    public List<Workout> getWorkouts() {
        return workouts;
    }

    public void setWorkouts(List<Workout> workouts) {
        this.workouts = workouts;
    }
}
