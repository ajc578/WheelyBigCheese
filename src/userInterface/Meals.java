package userInterface;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "meals")
@XmlAccessorType(XmlAccessType.FIELD)
public class Meals {
	@XmlElement(name = "meal")
	private List<Meal> meals = null;
	
	public List<Meal> getMeals() {
		return meals;
	}
	
	public void setMeals(ArrayList<Meal> meals) {
		this.meals = meals;
	}

}

