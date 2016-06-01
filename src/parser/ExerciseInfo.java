package parser;

/**
 * This class is used to store and retrieve all the attributes associated with an
 * exercise slide in a workout. 
 * 
 * <p> <STRONG> Developed by </STRONG> <p>
 * Oliver Rushton
 * <p> <STRONG> Tested by </STRONG> <p>
 * Oliver Rushton
 * <p> <STRONG> Developed for </STRONG> <p>
 * BOSS
 * @author Oliver Rushton
 * 
 */
public class ExerciseInfo {
	
	private String nameVar;
	private int sets, reps, points;
	private double speed, strength, endurance, agility;
	/**
	 * Sets all the attributes associated with an exercise slide in a workout.
	 * 
	 * @param nameVar the name of the exercise.
	 * @param sets the number of sets for this exercise.
	 * @param reps the number of repetions for this exercise.
	 * @param points the number of awarded skill points for this exercise.
	 * @param speed the proportion this exercise that works the speed attribute of a user.
	 * @param strength the proportion this exercise that works the strength attribute of a user.
	 * @param endurance the proportion this exercise that works the endurance attribute of a user.
	 * @param agility the proportion this exercise that works the agility attribute of a user.
	 */
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
	/**
	 * gets the value of the speed field.
	 * 
	 * @return the value of the speed field
	 */
	public double getSpeed() {
		return speed;
	}
	/**
	 * Sets the value of the speed field.
	 * @param speed the new value of the speed field
	 */
	public void setSpeed(double speed) {
		this.speed = speed;
	}
	/**
	 * gets the value of the strength field.
	 * 
	 * @return the value of the strength field
	 */
	public double getStrength() {
		return strength;
	}
	/**
	 * Sets the value of the strength field.
	 * @param strength the new value of the strength field
	 */
	public void setStrength(double strength) {
		this.strength = strength;
	}
	/**
	 * gets the value of the endurance field.
	 * 
	 * @return the value of the endurance field
	 */
	public double getEndurance() {
		return endurance;
	}
	/**
	 * Sets the value of the endurance field.
	 * @param endurance the new value of the endurance field
	 */
	public void setEndurance(double endurance) {
		this.endurance = endurance;
	}
	/**
	 * gets the value of the agility field.
	 * 
	 * @return the value of the agility field
	 */
	public double getAgility() {
		return agility;
	}
	/**
	 * Sets the value of the agility field.
	 * @param agility the new value of the agility field
	 */
	public void setAgility(double agility) {
		this.agility = agility;
	}
	/**
	 * Gets the value of the <code>nameVar</code> field.
	 * @return The exercise name.
	 */
	public String getName() {
		return nameVar;
	}
	/**
	 * Gets the  value of the <code>sets</code> field.
	 * @return The number of sets in the exercise.
	 */
	public int getSets() {
		return sets;
	}
	/**
	 * Sets the value of the <code>sets</code> field.
	 * @param sets the number of sets in the exercise.
	 */
	public void setSets(int sets) {
		this.sets = sets;
	}
	/**
	 * Gets the value of the <code>reps</code> field.
	 * @return The number of repetitions in each set of the exercise
	 */
	public int getReps() {
		return reps;
	}
	/**
	 * Sets the value of the <code>points</code> field.
	 * @return The number of skill points awarded for completion of the exercise.
	 */
	public int getPoints() {
		return points;
	}
	
}
