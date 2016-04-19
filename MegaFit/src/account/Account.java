package account;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Account {
	
	public static final int LOGIN_INDEX = 0, NUM_INDEX = 1, NAME_INDEX = 2, PASSWORD_INDEX = 3, DATE_INDEX = 4, LEVEL_INDEX = 5, XP_INDEX  = 6, GAINZ_INDEX = 7,
							DAILYCHALLENGEID_INDEX = 8, FRIENDS_INDEX = 9;
	
	private String accountNum;
	private String accountName;
	private String password;
	private String saveDate;
	private String level;
	private String xp;
	private String gainz;
	private String loginStatus;
	private String dailyChallengeID;
	private String friendsList;
	
	private Long updateTime;
	
	public Account() {}
	
	public Account(String accountName, String password, String accountNum) {
		this.accountNum = accountNum;
		this.accountName = accountName;
		this.password = password;
		this.saveDate = Long.toString(System.currentTimeMillis());
		this.level = Integer.toString(0);
		this.xp = Integer.toString(0);
		this.gainz = Integer.toString(0);
	}
	
	public void setFriendsList(String friendsList) {
		this.friendsList = friendsList;
	}
	
	public String getFriendsList() {
		return friendsList;
	}
	
	public void setUpdateTime(Long newTime) {
		updateTime = newTime;
	}
	
	public Long getUpdateTime() {
		return updateTime;
	}
	
	public String getLoginStatus() {
		return loginStatus;
	}
	
	public void setLoginStatus(String loggedIn) {
		this.loginStatus = loggedIn;
	}
	
	public void setNumber(String accountNum) {
		this.accountNum = accountNum;
	}
	
	public String getNumber() {
		return accountNum;
	}
	
	public void setName(String accountName) {
		this.accountName = accountName;
	}
	
	public String getName() {
		return accountName;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setSaveDate(String date) {
		this.saveDate = date;
	}
	
	public String getSaveDate() {
		return saveDate;
	}
	
	public void setLevel(String level) {
		this.level = level;
	}
	
	public String getLevel() {
		return level;
	}
	
	public void setXP(String xp) {
		this.xp = xp;
	}
	
	public String getXP() {
		return xp;
	}
	
	public void setGainz(String gainz) {
		this.gainz = gainz;
	}
	
	public String getGainz() {
		return gainz;
	}
	
	public void setdailyChallengeID(String dailyChallengeID) {
		this.dailyChallengeID = dailyChallengeID;
	}
	
	public String getdailyChallengeID() {
		return dailyChallengeID;
	}
	
	public void addFriend(String friend) {
		if (friendsList.equals(null)) {
			friendsList = "#" + friend + "#";
		} else {
			friendsList = friendsList.replaceAll("#", "");
			friendsList = "#" + friendsList + "," + friend + "#";
		}
	}
	
	public void delFriend(String friend) {
		if (friendsList.contains("," + friend)) {
			friendsList.replace("," + friend, "");
		} else if (friendsList.contains(friend + ",")) {
			friendsList.replace(friend + ",", "");
		}
	}
	
	public void loadSequence(int count, String data) {
		switch (count) {
		case LOGIN_INDEX:
			setLoginStatus(data);
			break;
		case NUM_INDEX:
			setNumber(data);
			break;
		case NAME_INDEX:
			setName(data);
			break;
		case PASSWORD_INDEX:
			setPassword(data);
			break;
		case DATE_INDEX:
			setSaveDate(data);
			break;
		case LEVEL_INDEX:
			setLevel(data);
			break;
		case XP_INDEX: 
			setXP(data);
			break;
		case GAINZ_INDEX:
			setGainz(data);
			break;
		case DAILYCHALLENGEID_INDEX:
			setdailyChallengeID(data);
			break;
		case FRIENDS_INDEX:
			setFriendsList(data);
			break;
		default:
			//error in write sequence
			System.out.println("Error in write sequence - index i is not in case range");
			break;
			
		}
	}
	
	public String[] saveSequence() {
		final String[] sequence = {loginStatus, accountNum, accountName, password, saveDate = Long.toString(System.currentTimeMillis()),
								   level, xp, gainz, dailyChallengeID, friendsList};
		return sequence;
	}
	
	public boolean saveNewAccount(String directory) {
		boolean saveSuccess = false;
		File newFile = new File(directory + accountNum + ".txt");
		if (!newFile.exists()) {
			try (
				BufferedWriter bw = new BufferedWriter(new FileWriter(newFile));
			) {	
				int i = 0;
				String[] metadata = saveSequence(); 
				while (i < metadata.length) {
					bw.write(metadata[i]);
					bw.newLine();
					i++;
				}
				saveSuccess = true;
				
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				saveSuccess = false;
				e.printStackTrace();
				return saveSuccess;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				saveSuccess = false;
				e.printStackTrace();
				return saveSuccess;
			}
		}

		return saveSuccess;
	}
	
	public boolean saveAccount(String directory) {
		boolean saveSuccess = false;
		File oldFile = new File(directory + accountNum + ".txt");
		File tempFile = new File(directory + "tempFile.txt");
		if (oldFile.exists() && !oldFile.isDirectory()) {
			try (
				BufferedWriter bw = new BufferedWriter(new FileWriter(tempFile));
				BufferedReader br = new BufferedReader(new FileReader(oldFile));
			) {	
				String line = "";
				int i = 0;
				String[] metadata = saveSequence(); 
				while ((line = br.readLine()) != null) {
					bw.write(metadata[i]);
					bw.newLine();
					i++;
				}
				saveSuccess = true;
				
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				saveSuccess = false;
				e.printStackTrace();
				return saveSuccess;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				saveSuccess = false;
				e.printStackTrace();
				return saveSuccess;
			}
		}
		oldFile.delete();
		tempFile.renameTo(oldFile);

		return saveSuccess;
	}
	
	public void editAccount(int count, String input) {
		if (input.contains(" : ")) {
			loadSequence(count, input.substring(input.lastIndexOf(" : ") + 3));
		} else {
			loadSequence(count, input);
		}
	}
	
	public String getAttribute(int index) {
		String[] toSave = saveSequence();
		return toSave[index];
	}
	
	public boolean loadAccount(String directory, String filename) {
		boolean loadSuccess = false;
		File file = new File(directory + filename + ".txt");
		System.out.println(file.exists());
		if (file.exists() && !file.isDirectory()) {
			try (
				BufferedReader br = new BufferedReader(new FileReader(file));
			) {
				String line = null;
				int i = 0;
				while ((line = br.readLine()) != null) {
					loadSequence(i, line);
					i++;
				}
				
				loadSuccess = true;
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				loadSuccess = false;
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				loadSuccess = false;
				e.printStackTrace();
			}
		} else {
			loadSuccess = false;
		}
		
		return loadSuccess;
	}
	
	public static Account accountLoad(String directory, String filename) {
		File file = new File(directory + filename + ".txt");
		Account temp = new Account();
		System.out.println(file.exists());
		if (file.exists() && !file.isDirectory()) {
			try (
				BufferedReader br = new BufferedReader(new FileReader(file));
			) {
				String line = null;
				int i = 0;
				while ((line = br.readLine()) != null) {
					temp.loadSequence(i, line);
					i++;
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} 
		return temp;
	}

}
