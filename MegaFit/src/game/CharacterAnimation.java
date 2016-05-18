package game;

import javafx.animation.SequentialTransition;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.Scene;

public class CharacterAnimation {
	
	private Scene gameScene;
	private LayerCharacter opponentCharacter;
	private LayerCharacter localCharacter;
	private SimpleIntegerProperty oppHContainerYpos;
	private SimpleIntegerProperty locHContainerYpos;
	
	public CharacterAnimation(Scene scene, LayerCharacter opponentCharacter, LayerCharacter localCharacter, SimpleIntegerProperty oppHContainerYpos, SimpleIntegerProperty locHContainerYpos) {
		this.gameScene = scene;
		this.opponentCharacter = opponentCharacter;
		this.localCharacter = localCharacter;
				
	}
	
	private void opponentAttack() {
		
		
		
		
		
		
		
		SequentialTransition sequentialTransition = new SequentialTransition();
		
	}
	
	private void defend() {
		
	}
	
}
