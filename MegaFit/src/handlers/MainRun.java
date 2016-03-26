package handlers;

/*
 * Author : Oliver Rushton and Alexander Chapman
 * Group: 4
 * Description: This main class is a demo of the parser and presentation viewer
 */

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import parser.XMLParser;

public class MainRun extends Application{
	
	PresentationFx testPresent;

	public static void main(String[] args) { launch(args); }
	
	@Override
	public void start(Stage frame) throws Exception {
		
		testPresent = new PresentationFx("testPresentation", "Lexxy", "v0.1", "for testing");
		//calls parser
		XMLParser parser = new XMLParser("test4.xml");
		//adds all slides to presentation
		testPresent.addAllSlides(parser.getAllSlides());
		//plays presentation
		Scene scene = testPresent.Play();
		testPresent.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("PresentationFinished");
				frame.close();
			}
			
		});
		//maintains window aspect ratio
		frame.setScene(scene);
	    frame.minWidthProperty().bind(scene.heightProperty().multiply(2));
	    frame.minHeightProperty().bind(scene.widthProperty().divide(2));
	    frame.show();
	    
	    
	}

}
