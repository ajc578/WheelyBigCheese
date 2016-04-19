package parser;

import java.util.ArrayList;

public class WorkoutInfo {
	
	private String workoutName;
	private ArrayList<ExerciseInfo> exerciseList = new ArrayList<ExerciseInfo>();
	
	public void setWorkoutName(String workoutName) {
		this.workoutName = workoutName;
	}
	
	public String getWorkoutName() {
		return workoutName;
	}
	
	public void addExercise(ExerciseInfo info) {
		exerciseList.add(info);
	}
	
	public ArrayList<ExerciseInfo> getExerciseList() {
		return exerciseList;
	}
	
}
