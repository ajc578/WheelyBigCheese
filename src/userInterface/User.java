package userInterface;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class User {
    private final SimpleStringProperty username;
    private final SimpleIntegerProperty level;
    private final SimpleIntegerProperty xpInt;
    private final SimpleIntegerProperty skillz;
    private final SimpleIntegerProperty gainz;
 
    public User(String uUsername, int uLevel, int uXPint, int uSkillz, int uGainz) {
        this.username = new SimpleStringProperty(uUsername);
        this.level = new SimpleIntegerProperty(uLevel);
        this.xpInt = new SimpleIntegerProperty(uXPint);
        this.skillz = new SimpleIntegerProperty(uSkillz);
        this.gainz = new SimpleIntegerProperty(uGainz);
    }
 
    public String getUsername() {
        return username.get();
    }
    public void setUsername(String uUsername) {
        username.set(uUsername);
    }
        
    public int getLevel() {
        return level.get();
    }
    public void setLevel(int uLevel) {
        level.set(uLevel);
    }
    
   public int getXpInt() {
        return xpInt.get();
   }
   public void setXP(int uXPint) {
        xpInt.set(uXPint);
   }
    
    public int getSkillz() {
    	return skillz.get();
    }
    public void setSkillz(int uSkillz) {
    	skillz.set(uSkillz);
    }
    
    public int getGainz() {
    	return gainz.get();
    }
    public void setGainz(int uGainz) {
    	gainz.set(uGainz);
    }
    

}

