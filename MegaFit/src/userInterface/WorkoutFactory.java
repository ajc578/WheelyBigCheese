package userInterface;

import java.util.List;
import presentationViewer.PresentationFx;
import parser.ExerciseInfo;

public class WorkoutFactory {
	
	List<ExerciseInfo> exerciseList;
	PresentationFx workout;

	public WorkoutFactory(List<ExerciseInfo> exerciseList) {
		this.exerciseList = exerciseList;
		workout = new PresentationFx("Workout 1", null, null, null);
	}

	public List<ExerciseInfo> getExerciseList() {
		// TODO Auto-generated method stub
		return exerciseList;
	}

	public PresentationFx getWorkoutPresentation() {
		// TODO Auto-generated method stub
		return workout;
	}

}
