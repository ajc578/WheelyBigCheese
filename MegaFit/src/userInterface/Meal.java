package userInterface;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "meal")
@XmlAccessorType(XmlAccessType.FIELD)
public class Meal {

	private int id;
	private String name;
	private String imgLoc;
	private int type;
	public int getMealId() {
		return id;
	}
	public void setMealId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getImgLoc() {
		return imgLoc;
	}
	public void setImgLoc(String imgLoc) {
		this.imgLoc = imgLoc;
	}
	
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	
}

