package userInterface;

import account.Account;
import account.CharacterParts;

public class CharacterStorage {

	private String eyesPath;
	private String hairPath;

	public CharacterStorage(){
		CharacterParts characterParts = Main.account.getCharacterAttributes().getCharacterSource();
		eyesPath = characterParts.getEyesSource();
		hairPath = characterParts.getHairSource();
		
	}
	
	public String getEyesPath(){
		return eyesPath;
	}
	
	public String getHairPath(){
		return hairPath;
	}
	
	public void setEyesPath(String path){
		eyesPath = path;

	}
	
	public void setHairPath(String path){
		hairPath = path;
	}
	
}
