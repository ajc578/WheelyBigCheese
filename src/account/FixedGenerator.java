package account;

import java.util.Random;
/**
 * A Class used primary to generate the account numbers for each account. This class
 * extends the {@link Random} class, but uses an account's user name as a seed to generate 
 * the account number. This means that, given an account's user name, you can get
 * the corresponding account number, but you cannot convert an account number to a user name.
 * This keeps the accounts anonymous when browsing the file server directory.
 * 
 * <p> <STRONG> Developed by </STRONG> <p>
 * Oliver Rushton
 * <p> <STRONG> Tested by </STRONG> <p>
 * Oliver Rushton
 * <p> <STRONG> Developed for </STRONG> <p>
 * BOSS
 * @author Oliver Rushton
 */
public class FixedGenerator extends Random {
	private static final long serialVersionUID = 1L;
	/**
	 * No arg constructor to access normal {@link Random} object functionality.
	 */
	public FixedGenerator() {}
	/**
	 * This constructor takes a seed to generate a number.
	 * 
	 * @param seed the user name of the account to retrieve the account number for.
	 */
	public FixedGenerator(long seed) {
		super(seed);
	}
	/**
	 * Generates an integer of a long length needed for the account numbers.
	 * @return the account number generated from the seed.
	 */
	public int nextPositiveInt() {
		return next(Integer.SIZE - 1);
	}
}
