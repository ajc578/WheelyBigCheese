//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.05.15 at 03:07:05 PM BST 
//


package account;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * A class used to store all the attributes associated with
 * a User's MegaFit account. This class was originally generated 
 * from the account xsd file via JAXB, but has since been manually 
 * edited and corrected.
 * <p> <STRONG> Developed by </STRONG> <p>
 * Oliver Rushton
 * <p> <STRONG> Tested by </STRONG> <p>
 * Oliver Rushton
 * <p> <STRONG> Developed for </STRONG> <p>
 * BOSS
 * @author Oliver Rushton
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "friends",
        "achievements",
        "items",
        "history",
        "dietPlanner",
        "characterAttributes",
        "mostRecentWorkouts"
})
@XmlRootElement(name = "Account")
public class Account implements java.io.Serializable {



    private static final long serialVersionUID = 1L;
    protected List<String> friends;
    protected List<Achievement> achievements;
    @XmlElement(type = Integer.class)
    protected List<Integer> items;
    @XmlElement(type = WorkoutEntry.class)
    protected List<WorkoutEntry> history;
    @XmlElement(required = true)
    protected DietCalender dietPlanner;
    @XmlElement(required = true)
    protected CharacterAttributes characterAttributes;
    @XmlElement(required = true)
    protected RecentWorkouts mostRecentWorkouts;
    @XmlAttribute(name = "loginStatus", required = true)
    protected String loginStatus;
    @XmlAttribute(name = "number", required = true)
    protected String number;
    @XmlAttribute(name = "username", required = true)
    protected String username;
    @XmlAttribute(name = "password", required = true)
    protected String password;
    @XmlAttribute(name = "lastSaved", required = true)
    protected long lastSaved;
    @XmlAttribute(name = "level", required = true)
    protected int level;
    @XmlAttribute(name = "xp", required = true)
    protected int xp;
    @XmlAttribute(name = "skillPoints", required = true)
    protected int skillPoints;
    @XmlAttribute(name = "gainz", required = true)
    protected int gainz;
    @XmlAttribute(name = "dailyChallengeID", required = true)
    protected String dailyChallengeID;
    @XmlAttribute(name = "firstName", required = true)
    protected String firstName;
    @XmlAttribute(name = "surname", required = true)
    protected String surname;
    @XmlAttribute(name = "height", required = true)
    protected double height;
    @XmlAttribute(name = "weight", required = true)
    protected double weight;
    @XmlAttribute(name = "DOB", required = true)
    protected String dob;
    @XmlAttribute(name = "email", required = true)
    protected String email;

    /**
     * Gets the value of the friends property. Each friend's
     * accountName is stored within a list of strings called 'friends'.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFriends().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * @return Friends List as a list of strings
     *
     *
     */
    public List<String> getFriends() {
        if (friends == null) {
            friends = new ArrayList<String>();
        }
        return this.friends;
    }
    
    /**
     * Sets the value of the friends property. Each friend's
     * accountName is stored within a list of strings called 'friends'.
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * @param friends The friends List as a list of strings
     *
     */
    public void setFriends(List<String> friends) {
        if (friends == null) {
            this.friends = new ArrayList<String>();
        } else {
            this.friends = friends;
        }
    }


    /**
     * Gets the value of the achievements property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAchievements().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Achievement }
     * 
     * @return The list of achievements for a particular user.
     *
     *
     */
    public List<Achievement> getAchievements() {
        if (achievements == null) {
            achievements = new ArrayList<Achievement>();
        }
        return this.achievements;
    }
    /**
     * Sets the value of the achievements property.
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Achievement }
     * 
     * @param achievements The list of achievements for a particular user
     *
     */
    public void setAchievements(List<Achievement> achievements) {
        if (achievements == null) {
            this.achievements = new ArrayList<Achievement>();
        } else {
            this.achievements = achievements;
        }
    }

    /**
     * Gets the value of the items property. Items are stored
     * as a list of Integers that index the item xmls, where the
     * Item data is stored.
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getItems().add(newItem);
     * </pre>
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Integer }
     *
     *
     */
    public List<Integer> getItems() {
        if (items == null) {
            items = new ArrayList<Integer>();
        }
        return this.items;
    }
    /**
     * Sets the value of the items property. Items are stored
     * as a list of Integers that index the item xmls, where the
     * Item data is stored.
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Integer }
     * 
     * @param items The list of item indexes
     *
     */
    public void setItems(List<Integer> items) {
        if (items == null) {
            this.items = new ArrayList<Integer>();
        } else {
            this.items = items;
        }
    }

    /**
     * Gets the value of the history property. This is a list of 
     * WorkoutEntry(s) each containing the workout name, duration
     * and date performed.
     * <p>
     * For example, to add a new WorkoutEntry, do as follows:
     * <pre>
     *    getHistory().add(newWorkoutEntry);
     * </pre>
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link WorkoutEntry }
     * 
     * @return The List of WorkoutEntry(s) for a particular user.
     */
    public List<WorkoutEntry> getHistory() {
        if (history == null) {
            history = new ArrayList<WorkoutEntry>();
            System.out.println("history null");
        }
        return history;
    }
    
    /**
     * Sets the value of the history property. This is a list of 
     * WorkoutEntry(s) each containing the workout name, duration
     * and date performed.
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link WorkoutEntry }
     * 
     * @param history The List of WorkoutEntry(s) for a particular user.
     */
    public void setHistory(List<WorkoutEntry> history) {
        if (items == null) {
            this.history = new ArrayList<WorkoutEntry>();
        } else {
            this.history = history;
        }


    }

    public void addWorkoutEntry(WorkoutEntry workout) {
        this.history.add(workout);
    }

    /**
     * Gets the value of the dietPlanner property.
     *
     * @return
     *     possible object is
     *     {@link DietCalender }
     *
     */
    public DietCalender getDietPlanner() {
        return dietPlanner;
    }

    /**
     * Sets the value of the dietPlanner property.
     *
     * @param value
     *     allowed object is
     *     {@link DietCalender }
     *
     */
    public void setDietPlanner(DietCalender value) {
        this.dietPlanner = value;
    }

    /**
     * Gets the value of the characterAttributes property.
     *
     * @return
     *     possible object is
     *     {@link CharacterAttributes }
     *
     */
    public CharacterAttributes getCharacterAttributes() {
        return characterAttributes;
    }

    /**
     * Sets the value of the characterAttributes property.
     *
     * @param value
     *     allowed object is
     *     {@link CharacterAttributes }
     *
     */
    public void setCharacterAttributes(CharacterAttributes value) {
        this.characterAttributes = value;
    }

    /**
     * Gets the value of the mostRecentWorkouts property.
     *
     * @return
     *     possible object is
     *     {@link RecentWorkouts }
     *
     */
    public RecentWorkouts getMostRecentWorkouts() {
        return mostRecentWorkouts;
    }

    /**
     * Sets the value of the mostRecentWorkouts property.
     *
     * @param value
     *     allowed object is
     *     {@link RecentWorkouts }
     *
     */
    public void setMostRecentWorkouts(RecentWorkouts value) {
        this.mostRecentWorkouts = value;
    }

    /**
     * Gets the value of the loginStatus property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getLoginStatus() {
        return loginStatus;
    }

    /**
     * Sets the value of the loginStatus property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setLoginStatus(String value) {
        this.loginStatus = value;
    }

    /**
     * Gets the value of the number property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getNumber() {
        return number;
    }

    /**
     * Sets the value of the number property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setNumber(String value) {
        this.number = value;
    }

    /**
     * Gets the value of the username property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the value of the username property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setUsername(String value) {
        this.username = value;
    }

    /**
     * Gets the value of the password property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the value of the password property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setPassword(String value) {
        this.password = value;
    }

    /**
     * Gets the value of the lastSaved property.
     *
     */
    public long getLastSaved() {
        return lastSaved;
    }

    /**
     * Sets the value of the lastSaved property.
     *
     */
    public void setLastSaved(long value) {
        this.lastSaved = value;
    }

    /**
     * Gets the value of the level property.
     *
     */
    public int getLevel() {
        return level;
    }

    /**
     * Sets the value of the level property.
     *
     */
    public void setLevel(int value) {
        this.level = value;
    }

    /**
     * Gets the value of the xp property.
     *
     */
    public int getXp() {
        return xp;
    }

    /**
     * Sets the value of the xp property.
     *
     */
    public void setXp(int value) {
        this.xp = value;
    }

    /**
     * Gets the value of the skillPoints property.
     *
     */
    public int getSkillPoints() {
        return skillPoints;
    }

    /**
     * Sets the value of the skillPoints property.
     *
     */
    public void setSkillPoints(int value) {
        this.skillPoints = value;
    }

    /**
     * Gets the value of the gainz property.
     *
     */
    public int getGainz() {
        return gainz;
    }

    /**
     * Sets the value of the gainz property.
     *
     */
    public void setGainz(int value) {
        this.gainz = value;
    }

    /**
     * Gets the value of the dailyChallengeID property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getDailyChallengeID() {
        return dailyChallengeID;
    }

    /**
     * Sets the value of the dailyChallengeID property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setDailyChallengeID(String value) {
        this.dailyChallengeID = value;
    }

    /**
     * Gets the value of the firstName property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the value of the firstName property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setFirstName(String value) {
        this.firstName = value;
    }

    /**
     * Gets the value of the surname property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getSurname() {
        return surname;
    }

    /**
     * Sets the value of the surname property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setSurname(String value) {
        this.surname = value;
    }

    /**
     * Gets the value of the height property.
     *
     */
    public double getHeight() {
        return height;
    }

    /**
     * Sets the value of the height property.
     *
     */
    public void setHeight(double value) {
        this.height = value;
    }

    /**
     * Gets the value of the weight property.
     *
     */
    public double getWeight() {
        return weight;
    }

    /**
     * Sets the value of the weight property.
     *
     */
    public void setWeight(double value) {
        this.weight = value;
    }

    /**
     * Gets the value of the dob property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getDOB() {
        return dob;
    }

    /**
     * Sets the value of the dob property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setDOB(String value) {
        this.dob = value;
    }

    /**
     * Gets the value of the email property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the value of the email property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setEmail(String value) {
        this.email = value;
    }

}
