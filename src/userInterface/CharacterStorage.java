package userInterface;

public class CharacterStorage {

	private String eyesPath;
	private String hairPath;

	public CharacterStorage(){
		
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
