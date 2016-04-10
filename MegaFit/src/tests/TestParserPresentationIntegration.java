package tests;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import parser.XMLParser;
import presentationViewer.PresentationFx;
/**
 * A test application that builds a presentation from file
 * using the parser and plays it to allow visual testing
 * and demostration of the presentation and parser integration.
 * <p> <STRONG> Developed by </STRONG> <p>
 * Alexander Chapman and Oliver Rushton
 * <p> <STRONG> Tested by </STRONG> <p>
 * Alexander Chapman and Oliver Rushton
 * <p> <STRONG> Developed for </STRONG> <p>
 * BOSS
 * @author Alexander Chapman
 */
public class TestParserPresentationIntegration extends Application{
	
	PresentationFx testPresent;
	//the test presentation object

	public static void main(String[] args) { launch(args); }
	
	@Override
	public void start(Stage frame) throws Exception {
		
		//calls parser
		XMLParser parser = new XMLParser("test4.xml");
		//create and add all slides to presentation
		testPresent = new PresentationFx(parser.getDocumentInfo().getTitle(),
				parser.getDocumentInfo().getAuthor(), parser.getDocumentInfo().getVersion(),
				parser.getDocumentInfo().getComment());
		testPresent.addAllSlides(parser.getAllSlides());
		
		//when the presentation finishes, close the application
		testPresent.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("PresentationFinished");
				frame.close();
			}
			
		});
		
		
		//plays presentation
		Scene scene = testPresent.Play();
		
		//maintains window aspect ratio
		frame.setScene(scene);
	    frame.minWidthProperty().bind(scene.heightProperty().multiply(2));
	    frame.minHeightProperty().bind(scene.widthProperty().divide(2));
	    frame.setTitle(testPresent.getTitle());
	    frame.show();
	    
	    scene.setOnKeyTyped(keyHandler);
	    
	}
	
	/**A handler to allow keys to be pressed to test some of the user controllability
	 * of the presentation class.
	 */
	EventHandler<KeyEvent> keyHandler = new EventHandler<KeyEvent>() {

		@Override
		public void handle(KeyEvent ke) {
			switch(ke.getCharacter()){
				case "q":
					testPresent.quit();
				case "m":
					testPresent.setManualPlay();
				case "a":
					testPresent.setAutoPlay();
				case "p":
					testPresent.advanceManualEvents();
			}
			
		}
	};

}