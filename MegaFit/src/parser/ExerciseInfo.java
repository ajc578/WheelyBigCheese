package parser;


public class ExerciseInfo {
	
	private String nameVar;
	private int sets, reps, points;
	
	public ExerciseInfo(String nameVar, int sets, int reps, int points) {
		this.nameVar = nameVar;
		this.sets = sets;
		this.reps = reps;
		this.points = points;
	}
	
	public String getName() {
		return nameVar;
	}
	
	public int getSets() {
		return sets;
	}
	
	public int getReps() {
		return reps;
	}
	
	public int getPoints() {
		return points;
	}
	
}
