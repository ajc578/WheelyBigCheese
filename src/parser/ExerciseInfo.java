package parser;


public class ExerciseInfo {
	
	private String nameVar;
	private int sets, reps, points;
	private double speed, strength, endurance, agility;
	
	public ExerciseInfo(String nameVar, int sets, int reps, int points, 
						double speed, double strength, double endurance, double agility) {
		this.nameVar = nameVar;
		this.sets = sets;
		this.reps = reps;
		this.points = points;
		this.speed = speed;
		this.strength = strength;
		this.endurance = endurance;
		this.agility = agility;
	}
	
	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

	public double getStrength() {
		return strength;
	}

	public void setStrength(double strength) {
		this.strength = strength;
	}

	public double getEndurance() {
		return endurance;
	}

	public void setEndurance(double endurance) {
		this.endurance = endurance;
	}

	public double getAgility() {
		return agility;
	}

	public void setAgility(double agility) {
		this.agility = agility;
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
