package parser;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


import java.util.ArrayList;


public class WorkoutInfo {

	private String name;
	private int duration;
	private String description;
	private String author;
	private int totalPoints;
	private String lastCompleted = "100001010000";
	private ArrayList<ExerciseInfo> exerciseList = new ArrayList<ExerciseInfo>();
	ArrayList<Presentation.Slide> slideList = new ArrayList<>();
	private String fileName;

	// Workout author
	private StringProperty authorProperty= new SimpleStringProperty();
	// Workout description
	private StringProperty descrProperty= new SimpleStringProperty();
	// Workout duration
	private StringProperty durProperty = new SimpleStringProperty();
	// Workout name
	private StringProperty nameProperty = new SimpleStringProperty();
	// Last completed date
	private StringProperty lastCompletedProperty = new SimpleStringProperty();


	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;

	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void addSlide(Presentation.Slide slide) {

		slideList.add(slide);
	}

	public ArrayList<Presentation.Slide> getSlideList() {
		return slideList;
	}

	public void addExercise(ExerciseInfo info) {
		exerciseList.add(info);
	}

	public ArrayList<ExerciseInfo> getExerciseList() {
		return exerciseList;
	}


	/**
	 * Properties needed for TableView
	 *
	 */

	// Author: the author of this workout

	public StringProperty authorProperty() {
		authorProperty.set(author);
		return authorProperty;
	}

	// Workout Name: the title of this workout

	public StringProperty nameProperty() {
		nameProperty.set(name);
		return nameProperty;
	}

	public StringProperty descriptionProperty() {
		descrProperty.set(description);
		return descrProperty;

	}

	public StringProperty durationProperty() {
		durProperty.set(Integer.toString(duration));
		return durProperty;
	}

	public void sumTotalPoints() {
		ExerciseInfo exercise;
		for (int i = 0; i < exerciseList.size(); i++) {
			exercise = exerciseList.get(i);
			totalPoints += exercise.getPoints()*exercise.getReps()*exercise.getSets();

		}
	}



	public int getTotalPoints() {
		return totalPoints;
	}


	public void setLastCompletedDate(String lastCompleted) {
		this.lastCompleted = lastCompleted;
	}

	public String getLastCompletedDate() {
		return lastCompleted;
	}

	// Property for last completed date is needed for
	// TableView displaying
	public StringProperty lastCompletedDateProperty() {
		if (lastCompleted == "100001010000") {	// last completed date is default value
			// workout hasn't been completed yet
			lastCompleted = ""; // show no date for incomplete workouts
		}

		lastCompletedProperty.set(lastCompleted);

		if (lastCompleted == "") { // reset for next search
			lastCompleted = "100001010000";
		}

		return lastCompletedProperty;
	}



	// Filename needed to launch presentation
	// Set by XMLParser
	public void setFileName(String absolutePath) {
		this.fileName = absolutePath;
	}

	public String getFileName() {
		return fileName;
	}


}
