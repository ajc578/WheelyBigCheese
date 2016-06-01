package game;

import javafx.animation.SequentialTransition;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.Scene;
/**
 * A class used to control the character animations for each player 
 * in the MegaFit Game session. The two animations consist of attacking
 * and defending.
 * <p> <STRONG> WARNING: This class could not be implemented for the
 * first release.</STRONG>
 * 
 * <p> <STRONG> Developed by </STRONG> <p>
 * Oliver Rushton
 * <p> <STRONG> Tested by </STRONG> <p>
 * Oliver Rushton
 * <p> <STRONG> Developed for </STRONG> <p>
 * BOSS
 * @author Oliver Rushton
 */
public class CharacterAnimation {
	
	private Scene gameScene;
	private LayerCharacter opponentCharacter;
	private LayerCharacter localCharacter;
	private SimpleIntegerProperty oppHContainerYpos;
	private SimpleIntegerProperty locHContainerYpos;
	/**
	 * Sets the avatars of the two players.
	 * 
	 * @param scene the game scene
	 * @param opponentCharacter the opponent's avatar
	 * @param localCharacter the local player's avatar
	 * @param oppHContainerYpos
	 * @param locHContainerYpos
	 */
	public CharacterAnimation(Scene scene, LayerCharacter opponentCharacter, LayerCharacter localCharacter, SimpleIntegerProperty oppHContainerYpos, SimpleIntegerProperty locHContainerYpos) {
		this.gameScene = scene;
		this.opponentCharacter = opponentCharacter;
		this.localCharacter = localCharacter;
		
	}
	/**
	 * Runs the attack animation transition.
	 */
	public void opponentAttack() {
		
		SequentialTransition sequentialTransition = new SequentialTransition();
		
	}
	/**
	 * Runs the defend animation transition.
	 */
	public void defend() {
		
	}
	
}
