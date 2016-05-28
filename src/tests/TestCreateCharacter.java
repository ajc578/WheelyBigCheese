package tests;



import static org.junit.Assert.assertEquals;
import org.junit.Rule;
import org.junit.Test;






import userInterface.CharacterStorage;
import userInterface.CreateCharacter;

public class TestCreateCharacter {
	

	
	@Rule public JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();

	
	@Test
	public void testDefaultImages(){
		CharacterStorage storage = new CharacterStorage();
		CreateCharacter createChar = new CreateCharacter(0,0);
		assertEquals("res/images/Eyes/BrownEyes.png", createChar.getCurrentEyesPath());
		assertEquals("res/images/Hair/BlackCatEarHair.png", createChar.getCurrentHairPath());
		assertEquals("res/images/Eyes/BrownEyes.png", storage.getEyesPath());
		assertEquals("res/images/Hair/BlackCatEarHair.png",  storage.getHairPath());
		
	}
	
	@Test public void testSettingImagesWithCharacterStorage(){
		CharacterStorage storage = new CharacterStorage();
		storage.setEyesPath("res/images/Eyes/GreenEyes.png");
		storage.setHairPath("res/images/Hair/RedFlame.png");
		CreateCharacter createChar = new CreateCharacter(0,0);
		assertEquals("res/images/Eyes/GreenEyes.png", createChar.getCurrentEyesPath());
		assertEquals("res/images/Hair/RedFlame.png", createChar.getCurrentHairPath());
	}
		
	

}
