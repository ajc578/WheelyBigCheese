package account;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import javafx.scene.control.Alert.AlertType;
import presentationViewer.ExceptionFx;
import userInterface.Main;
/**
 * A class providing the necessary methods to handle and manage
 * an {@link Account}.
 * <p>
 * Some of these methods are to be used by the server side only,
 * and some are for client use.
 *
 * <p>
 * The key functionality includes:
 * <ul>
 * Creating an account. <br>
 * Loading an account from xml. <br>
 * Saving an account to xml. <br>
 * Account login and logout. <br>
 * Friends list manipulation.
 * </ul>
 * <p> <STRONG> Developed by </STRONG> <p>
 * Oliver Rushton
 * <p> <STRONG> Tested by </STRONG> <p>
 * Oliver Rushton
 * <p> <STRONG> Developed for </STRONG> <p>
 * BOSS
 * @author Oliver Rushton
 */
public class AccountHandler {

	public static final int LOGIN_INDEX = 0, NUM_INDEX = 1, NAME_INDEX = 2, PASSWORD_INDEX = 3;
	private static final String clientDirectory = "src/res/clientAccounts/";
	private static final String serverDirectory = "src/res/serverAccounts/";
	private static final String activeAccountPath = "src/res/clientAccounts/activeAccount.txt";
	private static final String defaultBodyImagePath = "res/images/BaseCharacter.png";
	private static final String defaultHairImagePath = "res/images/Hair/BlackSpikeHair.png";
	private static final String defaultEyesImagePath = "res/images/Eyes/BrownEyes.png";

	private Account account;
	/**
	 * Gets the account {@link Account}
	 *
	 * @return The account associated with this AccountHandler.
	 */
	public Account getAccount() {
		return account;
	}
	/**
	 * Sets the account {@link Account}
	 *
	 * @param account The account to be associated with this AccountHandler.
	 */
	public void setAccount(Account account) {
		this.account = account;
	}
	/**
	 * Loads the name of the most recent workout into the 1st 
	 * position of {@link RecentWorkout} in the account<br>
	 * The remaining workout names are shifted down the order and 
	 * the last workout name is overwritten by the 3rd.
	 *
	 * @param workoutName The name of the most recent workout completed.
	 */
	public void updateRecentWorkouts(String workoutName) {
		account.getMostRecentWorkouts().setWorkout4(account.getMostRecentWorkouts().getWorkout3());
		account.getMostRecentWorkouts().setWorkout3(account.getMostRecentWorkouts().getWorkout2());
		account.getMostRecentWorkouts().setWorkout2(account.getMostRecentWorkouts().getWorkout1());
		account.getMostRecentWorkouts().setWorkout4(workoutName);
	}
	/**
	 * <h2>Server Side Only</h2>
	 * Gets the list of accounts associated with each friend in the
	 * primary accounts friendList.
	 *
	 *
	 * @return A list of friend accounts or an empty list if the friends accounts
	 * could not be loaded.
	 *
	 * @see Account
	 */
	public ArrayList<Account> getAllFriendAccounts() {
		ArrayList<Account> friends = new ArrayList<Account>();
		for (String i : account.getFriends()) {
			if (i != null) {
				Account friend = new Account();
				try {
					friend = accountLoad(serverDirectory,generateAccountNum(i));
				} catch (JAXBException e) {
					e.printStackTrace();
				}
				if (friend != null) {
					friend.setPassword("invisible"); //sets friend password invisible
					friends.add(friend);
				}
				System.out.println("Friend account loaded in account handler. Name: " + friend.getUsername());
			} else {
				return null;
			}
		}
		return friends;
	}
	/**
	 * Checks whether the account number exists in the specified
	 * directory. Account names are converted to account numbers
	 * for user anonymity when browsing the server file repository.
	 *
	 * @param directory The path to the account xml directory
	 * @param accountNum The account number generated from the account name.
	 *
	 * @return True if the account name is unique, false otherwise.
	 *
	 */
	public boolean checkUnique(String directory, String accountNum) {
		boolean unique = true;
		File dir = new File(directory);
		if (dir.isDirectory()) {
			File[] allFiles = dir.listFiles();
			for (File i : allFiles) {
				if (i.getName().equals(accountNum + ".xml")) {
					unique = false;
				}
			}
		}
		return unique;
	}
	/**
	 * Generates an account number for a particular account name.
	 * The account name is used as a seed to produce the account number
	 * so that converting from the account name to the account number
	 * is possible, but not the other way around.
	 *
	 * @param name The account name to be converted to a number
	 *
	 * @return The account number generated from the account name.
	 *
	 * @see FixedGenerator
	 */
	public static String generateAccountNum(String name) {
		String accountNum;
		Long seed = new Long(name.hashCode());
		FixedGenerator generator = new FixedGenerator(seed);
		accountNum = Integer.toString(generator.nextPositiveInt());

		return accountNum;
	}

	/**
	 * Creates a new account class with the users sign up details and
	 * checks to see if the account number is the only instance by comparing
	 * it with the list lift of account xmls in the client/server accounts directory.
	 *
	 * @param directory The server/client accounts directory.
	 * @param protocol The Users sign up details concatenated into a String - The tagged with the the Protocol.createAccount() method
	 *
	 * @return True if the account was created successfully, false otherwise.
	 *
	 * @see
	 * {@link #checkUnique(String, String) checkUnique}
	 * {@link #saveAccount(String) saveAccount}
	 */
	public boolean createNewAccount(String directory, String protocol) {
		boolean saveSuccess = false;
		//removes the protocol type tag from the message
		String line = protocol.substring(protocol.lastIndexOf(" : ") + 3);
		//split the message arguments into a list
		List<String> accountDetails = Arrays.asList(line.split("\\s*,\\s*"));
		String accountNum = generateAccountNum(accountDetails.get(0));

		//checks if the account Number is a new instance
		if (checkUnique(directory,accountNum)) {
			//Initialises the account
			account = new Account();
			account.setNumber(accountNum);
			account.setUsername(accountDetails.get(0));
			account.setPassword(accountDetails.get(1));
			account.setFirstName(accountDetails.get(2));
			account.setSurname(accountDetails.get(3));
			account.setWeight(Double.parseDouble(accountDetails.get(4)));
			account.setHeight(Double.parseDouble(accountDetails.get(5)));
			account.setDOB(accountDetails.get(6));
			account.setEmail(accountDetails.get(7));
			account.setFriends(new ArrayList<String>());
			account.setAchievements(loadAchievements());
			account.setItems(new ArrayList<Integer>());
			account.setHistory(new ArrayList<WorkoutEntry>());
			//TODO set daily challenge index ???????
			account.setDailyChallengeID("blahblah");
			CharacterAttributes charAtr = new CharacterAttributes();
			charAtr.setAgility(0);
			charAtr.setEndurance(0);
			charAtr.setSpeed(0);
			charAtr.setStrength(0);
			charAtr.setBaseAttack(0.1);
			charAtr.setBaseDefense(0.1);
			charAtr.setMove1(-1);
			charAtr.setMove2(-1);
			charAtr.setMove3(-1);
			charAtr.setMove4(-1);
			charAtr.setEquippedItem(-1);
			charAtr.setHealth(100);
			CharacterParts character = new CharacterParts();
			character.setBodySource(defaultBodyImagePath);
			character.setHairSource(defaultHairImagePath);
			character.setEyesSource(defaultEyesImagePath);
			charAtr.setCharacterSource(character);
			account.setCharacterAttributes(charAtr);
			DietCalender calender = new DietCalender();
			DayDiet day = new DayDiet();
			day.setBreakfast(-1);
			day.setLunch(-1);
			day.setDinner(-1);
			calender.setMonday(day);
			calender.setTuesday(day);
			calender.setWednesday(day);
			calender.setThursday(day);
			calender.setFriday(day);
			calender.setSaturday(day);
			calender.setSunday(day);
			account.setDietPlanner(calender);
			account.setLoginStatus(LoginStatus.LOGGED_IN);
			account.setLastSaved(System.currentTimeMillis());
			account.setLevel(0);
			account.setGainz(0);
			account.setXp(0);
			account.setSkillPoints(0);

			//save the account in the workouts directory
			if (saveAccount(directory))
				saveSuccess = true;
		}

		return saveSuccess;
	}
	/**
	 * Parses the achievement data from the text files (in the
	 * achievement directory) into an {@link ArrayList} of {@link Achievement}s.
	 *
	 * @return The <tt>List</tt> of <tt>Achievement</tt>s loaded from the clients
	 * 		   <tt>achievements</tt> directory.
	 *
	 *  @see Achievement
	 *
	 *  @see {@link #loadAchieveSequence(int, String, Achievement) loadAchieveSequence}
	 *
	 */
	private List<Achievement> loadAchievements() {
		List<Achievement> achievements = new ArrayList<Achievement>();
		//retrieves all achievement text files from the achievement directory
		File dir = new File("src/res/achievements/");
		//convert to a file array
		File[] allChieves = dir.listFiles();
		//continue if the previous two steps were successful
		if (allChieves != null) {
			//iterate through each file
			for (File i : allChieves) {
				Achievement temp = new Achievement();
				boolean loadSuccess = false;
				try (
						BufferedReader br = new BufferedReader(new FileReader(i));
				) {
					String line = null;
					int j = 0;
					//Read each line out of the achievement text file
					while ((line = br.readLine()) != null) {
						//
						temp = loadAchieveSequence(j,line,temp);
						j++;
					}
					//sets the progress of achievement to 0
					temp.setCurrentValue(0);
					temp.setComplete(false);
					loadSuccess = true;
				} catch (FileNotFoundException e) {
					loadSuccess = false;
					e.printStackTrace();
				} catch (IOException e) {
					loadSuccess = false;
					e.printStackTrace();
				}
				//If the try-resource statement was successful, add to achievement list
				if (loadSuccess) {
					achievements.add(temp);
				}
			}
		}
		return achievements;
	}
	/**
	 * Takes an <tt>Achievement</tt> and sets the fields corresponding
	 * to the given <tt>index</tt> with the contents of <tt>line</tt>.
	 * <p>
	 * The <tt>line</tt> argument provided contains a <tt>Integer</tt>
	 * value as a <tt>String</tt>.
	 *
	 * @param index referencing which {@link Achievement} setter to load the data into.
	 * @param line the String data to pass to the correct setter.
	 * @param temp the temporary <tt>Achievement</tt> to set.
	 * @return the modified <tt>Achievement</tt>.
	 *
	 * @see {@link #loadAchievements() loadAchievements}
	 */
	private Achievement loadAchieveSequence(int index, String line, Achievement temp) {
		switch (index) {
			case 0:
				temp.setIndex(Integer.parseInt(line));
				break;
			case 1:
				temp.setContent(line);
				break;
			case 2:
				temp.setPoints(Integer.parseInt(line));
				break;
			case 3:
				temp.setGainz(Integer.parseInt(line));
				break;
			case 4:
				temp.setThreshold(Integer.parseInt(line));
				break;
		}

		return temp;
	}

	/**
	 * Attempts to login to the account specified in the protocol
	 * message. First the account number is used to load the account data
	 * to check no existing user is logged in to this account. <br>
	 * Then the given password is checked against the loaded accounts
	 * password. If successful, the account is set as the <tt>AccountHandler</tt>s
	 * <tt>account> field and the method returns <tt>LoginStatus.LOGGED_IN</tt>.
	 *
	 * @param directory of the server/client account files
	 * @param protocol A message containing the login credentials provided by the user.
	 *
	 * @return The success of the login attempt as a <tt>String</tt>.
	 *
	 * @see LoginStatus
	 * @see {@link #accountLoad(String, String) accountLoad}
	 */
	public String login(String directory, String protocol) {
		String loginSuccess = LoginStatus.LOGGED_OUT;
		String line = protocol.substring(protocol.lastIndexOf(" : ") + 3);
		List<String> nameAndPassword = Arrays.asList(line.split("\\s*,\\s*"));
		String accountNum = generateAccountNum(nameAndPassword.get(0));
		//loads Xml file with account Number
		File temp = new File(directory + accountNum + ".xml");
		String actualPassword = null;
		//checks if the file exists in the given directory
		if (temp.exists() && temp.isFile()) {
			Account accTemp = new Account();
			try {
				//loads the account specified by the account number
				accTemp = accountLoad(directory,accountNum);
			} catch (JAXBException e) {
				//if the account cannot be loaded, return unsuccessful
				return LoginStatus.ACCOUNT_NOT_FOUND;
			}
			//check if account already logged in. proceed if false
			if (!accTemp.getLoginStatus().equals(LoginStatus.LOGGED_IN)) {
				actualPassword = accTemp.getPassword();
				//compare two passwords, return successful if true
				if (actualPassword.equals(nameAndPassword.get(1))) {
					loginSuccess = LoginStatus.LOGGED_IN;
					account = new Account();
					//set field account
					account = accTemp;
				}
			} else {
				loginSuccess = LoginStatus.IN_USE;
			}
		}else {
			loginSuccess = LoginStatus.ACCOUNT_NOT_FOUND;
		}

		return loginSuccess;
	}
	/**
	 * Sets the <tt>LoginStatus</tt> of the active account to
	 * logged out and saves the active account.
	 *
	 * @param directory to save active account to.
	 * @return True if logout successful, false otherwise.
	 *
	 * @see LoginStatus
	 */
	public boolean logout(String directory) {
		boolean logoutSuccess = false;
		account.setLoginStatus(LoginStatus.LOGGED_OUT);
		if (saveAccount(directory)) {
			logoutSuccess = true;
		}
		return logoutSuccess;
	}

	/**
	 * Adds a friend name to the active accounts friend list.
	 * @param friend name.
	 *
	 * @see Account.friends
	 */
	public void addFriend(String friend) {
		account.getFriends().add(friend);
	}
	/**
	 * Searches the server directory for accounts similar to the
	 * information provided by <tt>friendUserName</tt>. Then, returns a
	 * list of accounts as the response to the search results.
	 *
	 * @param search the search String to compare.
	 * @return A List of accounts similar to the search String.
	 *
	 *
	 */
	public ArrayList<Account> searchFriend(String search) {
		ArrayList<Account> searchResult = new ArrayList<Account>();
		System.out.println("AccountHandler: temp before load in search friends.");
		File dir = new File(serverDirectory);
		File[] allAccounts = dir.listFiles();
		for (File i : allAccounts) {
			if (i.exists() && i.isFile()) {
				JAXBContext jaxbContext = null;
				Account temp = null;
				try {

					jaxbContext = JAXBContext.newInstance(Account.class);
					Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
					temp = (Account) jaxbUnmarshaller.unmarshal(i);

					System.out.println("AccountHandler: temp account loaded in search friends.");

					if (temp.getUsername().contains(search) ||
							search.contains(temp.getUsername()) ||
							temp.getFirstName().contains(search) ||
							search.contains(temp.getFirstName()) ||
							temp.getSurname().contains(search) ||
							search.contains(temp.getSurname()) ||
							temp.getEmail().contains(search) ||
							search.contains(temp.getEmail())) {
						//if search matches this account,
						//add the account to the search result list
						if (!temp.getUsername().equals(account.getUsername()))
							searchResult.add(temp);
						System.out.println("AccountHandler: temp account added to search Results.");
					}

				} catch (JAXBException e) {} //TODO

			}
		}

		return searchResult;
	}

	/**
	 * Removes the friend given by the <tt>enemy</tt> argument from
	 * the accounts friendList.
	 *
	 * @param enemy the user-name of the friend you want to
	 * 		  remove from the account field friend list.
	 */
	public void delFriend(String enemy) {

		List<String> temp = account.getFriends();
		List<String> reducedList = new ArrayList<String>();
		for (int i = 0; i < temp.size(); i++) {
			if (!temp.get(i).equals(enemy)) {
				reducedList.add(temp.get(i));
			}
		}

		account.setFriends(reducedList);
	}

	/**
	 * Loads the account given by the <tt>filename String</tt> from the
	 * directory and into the <tt>account</tt> field.
	 *
	 * @param directory The directory path to load from.
	 * @param filename The account number to reference the account xml.
	 * @return True if the account is parsed successfully, false otherwise.
	 *
	 * @see JAXBContext
	 * @see Unmarshaller
	 * @see JAXBException
	 */
	public boolean loadAccount(String directory, String filename) {
		boolean loadSuccess = true;
		File sourceFile = new File(directory + filename + ".xml");
		JAXBContext jaxbContext;
		try {
			jaxbContext = JAXBContext.newInstance(Account.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			account = (Account) jaxbUnmarshaller.unmarshal(sourceFile);
		} catch (JAXBException e) {
			e.printStackTrace();
			loadSuccess = false;
		}
		return loadSuccess;
	}


	/**
	 * Saves the account field in the given directory by the process 
	 * of the JAXB unmarshaller.
	 *
	 * @param directory the directory to save the account field to.
	 * @return True is the save was successful, false otehrwise.
	 *
	 * @see Marshaller
	 */
	public boolean saveAccount(String directory) {
		boolean saveSuccess = true;
		try {
			account.setLastSaved(System.currentTimeMillis());
			File file = new File(directory + account.getNumber() + ".xml");
			JAXBContext jaxbContext = JAXBContext.newInstance(Account.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			jaxbMarshaller.marshal(account, file);
		} catch (JAXBException e) {
			saveSuccess = false;
		}
		return saveSuccess;
	}
	/**
	 * Static alternative to loading an account Object specified by the 
	 * <tt>filename String</tt>.
	 *
	 * @param directory The path to the directory to load from.
	 * @param filename The account number to address the account xml file.
	 * @return the loaded account or null if the load was unsuccessful.
	 * @throws JAXBException
	 * @see Unmarshaller
	 */
	public static Account accountLoad(String directory, String filename) throws JAXBException {
		Account temp = null;
		File sourceFile = new File(directory + filename + ".xml");
		JAXBContext jaxbContext;

		jaxbContext = JAXBContext.newInstance(Account.class);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		temp = (Account) jaxbUnmarshaller.unmarshal(sourceFile);

		return temp;
	}

	/**
	 * Gets the active account user-name from the <tt>active account</tt> 
	 * text file.
	 *
	 * @return The stored user-name 
	 */
	public static String getActiveAccount() {
		File temp = new File(activeAccountPath);
		String activeAccount = null;
		if (temp.exists() && temp.isFile()) {
			try (
					BufferedReader reader = new BufferedReader(new FileReader(temp));
			) {
				activeAccount = reader.readLine();
			} catch (FileNotFoundException e) {
				ExceptionFx exFx = new ExceptionFx(e, AlertType.ERROR, "Active Account Exception", "Cannot read the active account text file.",
						"Either the active account file has been deleted, renamed or it has been placed in the incorrect directory.\n"
								+ "Please place the activeAccount.txt file in the clientAccounts resource folder and restart MegaFit.");
				exFx.show();
				return null;
			} catch (IOException e) {
				ExceptionFx exFx = new ExceptionFx(e, AlertType.ERROR, "Active Account Exception", "Cannot read the active account text file.",
						"Either the active account file has been deleted, renamed or it has been placed in the incorrect directory.\n"
								+ "Please place the activeAccount.txt file in the clientAccounts resource folder and restart MegaFit.");
				exFx.show();
				return null;
			}

		}
		return generateAccountNum(activeAccount);
	}

}
