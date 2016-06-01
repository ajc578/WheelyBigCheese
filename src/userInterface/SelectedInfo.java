package userInterface;

/**
 * A class to hold the info required for the selected exercises 
 * <p> <STRONG> Developed by </STRONG> <p>
 * Alexander Chapman
 * <p> <STRONG> Developed for </STRONG> <p>
 * BOSS
 * @author Alexander Chapman
 */
public class SelectedInfo {
	String filename, name;
	int sets, reps;
	/*Fields -
	 *filename - the name of the exercise xml selected
	 *name - the title of the exercise
	 *sets - how many sets of this exercise the user has requested
	 *reps - how many reps of this exercise the user has requested*/
	
	/**Simply create a new  instance of selected info
	 * 
	 * @param filename - the name of the exercise xml selected
	 * @param name - the title of the exercise
	 * @param sets - how many sets of this exercise the user has requested
	 * @param reps - how many reps of this exercise the user has requested
	 */
	public SelectedInfo(String filename, String name, int sets, int reps) {
		this.filename = filename;
		this.sets = sets;
		this.reps = reps;
		this.name = name;
	}
}
