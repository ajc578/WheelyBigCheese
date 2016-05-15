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

import account.FixedGenerator;
import account.LoginStatus;
import account.Protocol;

public class AccountHandler {
	
	public static final int LOGIN_INDEX = 0, NUM_INDEX = 1, NAME_INDEX = 2, PASSWORD_INDEX = 3;
	private static final String clientDirectory = "src/res/clientAccounts/";
	private static final String serverDirectory = "src/res/serverAccounts/";
	private static final String defaultImageFile = "src/res/images/defaultSkin.jpg";
	
	private Account account;
	
	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}
	
	public ArrayList<Account> getAllFriendAccounts() {
		ArrayList<Account> friends = new ArrayList<Account>();
		for (String i : account.getFriends()) {
			Account friend = accountLoad(serverDirectory,generateAccountNum(i));
			if (friend != null) {
				friend.setPassword("invisible"); //sets friend password invisible
				friends.add(friend);
			}
		}
		return friends;
	}
	
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
	
	public static String generateAccountNum(String name) {
		String accountNum;
		Long seed = new Long(name.hashCode());
		FixedGenerator generator = new FixedGenerator(seed);
		accountNum = Integer.toString(generator.nextPositiveInt());
		
		return accountNum;
	}
	
	public boolean createNewAccount(String directory, String protocol) {
		boolean saveSuccess = false;
		String line = protocol.substring(protocol.lastIndexOf(" : ") + 3);
		List<String> accountDetails = Arrays.asList(line.split("\\s*,\\s*"));
		String accountNum = generateAccountNum(accountDetails.get(0));
		
		if (checkUnique(directory,accountNum)) {
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
			//TODO set daily challenge index ???????
			account.setDailyChallengeID("blahblah");
			CharacterAttributes charAtr = new CharacterAttributes();
			charAtr.setAgility(0);
			charAtr.setEndurance(0);
			charAtr.setSpeed(0);
			charAtr.setStrength(0);
			account.setCharacterAttributes(charAtr);
			account.setCompletedWorkouts(new ArrayList<String>());
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
			account.setCharacterImage(defaultImageFile);
			account.setLevel(0);
			account.setGainz(0);
			account.setXp(0);
			account.setSkillPoints(0);
			account.setItems(new ArrayList<Integer>());
			
			if (saveAccount(directory))
				saveSuccess = true;
		}
		
		return saveSuccess;
	}
	
	private List<Achievement> loadAchievements() {
		List<Achievement> achievements = new ArrayList<Achievement>();
		File dir = new File("src/res/achievements/");
		File[] allChieves = dir.listFiles();
		if (allChieves != null) {
			for (File i : allChieves) {
				Achievement temp = new Achievement();
				boolean loadSuccess = false;
				try (
						BufferedReader br = new BufferedReader(new FileReader(i));
					) {
						String line = null;
						int j = 0;
						while ((line = br.readLine()) != null) {
							temp = loadAchieveSequence(j,line,temp);
							j++;
						}
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
				if (loadSuccess) {
					achievements.add(temp);
				}
			}
		}
		return achievements;
	}
	
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
	
	public String login(String directory, String protocol) {
		String loginSuccess = LoginStatus.LOGGED_OUT;
		String line = protocol.substring(protocol.lastIndexOf(" : ") + 3);
		List<String> nameAndPassword = Arrays.asList(line.split("\\s*,\\s*"));
		String accountNum = generateAccountNum(nameAndPassword.get(0));
		
		File temp = new File(directory + accountNum + ".xml");
		String actualPassword = null;
		if (temp.exists() && temp.isFile()) {
			Account accTemp = accountLoad(directory,accountNum);
			if (!accTemp.getLoginStatus().equals(LoginStatus.LOGGED_IN)) {
				actualPassword = accTemp.getPassword();
				if (actualPassword.equals(nameAndPassword.get(1))) {
					loginSuccess = LoginStatus.LOGGED_IN;
					account = new Account();
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
	
	public boolean logout(String directory) {
		boolean logoutSuccess = false;
		account.setLoginStatus(LoginStatus.LOGGED_OUT);
		if (saveAccount(directory)) {
			logoutSuccess = true;
		}
		return logoutSuccess;
	}

	public void addFriend(String friend) {
		account.getFriends().add(friend);
	}
	
	public Account searchFriend(String friendUserName) {
		Account searchResult = new Account();
		searchResult = accountLoad(serverDirectory,generateAccountNum(friendUserName));
		return searchResult;
	}
	
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
	
	public static String readLine(String directory, String filename, int index) {
		Account temp = accountLoad(directory, filename);
		String output = null;
		switch (index) {
		case LOGIN_INDEX: 
			output = temp.getLoginStatus();
			break;
		case NUM_INDEX:
			output = temp.getNumber();
			break;
		case NAME_INDEX:
			output = temp.getUsername();
			break;
		case PASSWORD_INDEX:
			output = temp.getPassword();
		}
		return output;
	}
	
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
			// TODO Auto-generated catch block
			e.printStackTrace();
			saveSuccess = false;
		}
		return saveSuccess;
	}
	
	public static Account accountLoad(String directory, String filename) {
		Account temp = null;
		File sourceFile = new File(directory + filename + ".xml");
		JAXBContext jaxbContext;
		try {
			jaxbContext = JAXBContext.newInstance(Account.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			temp = (Account) jaxbUnmarshaller.unmarshal(sourceFile);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return temp;
	}
	
}
