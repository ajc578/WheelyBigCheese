package parser;

import java.util.ArrayList;

public class WorkoutInfo {

	private String workoutName;
	private int workoutDuration;
	private String description;
	private String author;
	private ArrayList<ExerciseInfo> exerciseList = new ArrayList<ExerciseInfo>();

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public void setWorkoutName(String workoutName) {
		this.workoutName = workoutName;
	}

	public String getWorkoutName() {
		return workoutName;
	}

	public int getWorkoutDuration() {
		return workoutDuration;
	}

	public void setWorkoutDuration(int workoutDuration) {
		this.workoutDuration = workoutDuration;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void addExercise(ExerciseInfo info) {
		exerciseList.add(info);
	}

	public ArrayList<ExerciseInfo> getExerciseList() {
		return exerciseList;
	}

}
