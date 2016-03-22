package handlers;
//test git
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import parser.XMLParser;

public class TestBed extends Application{
	
	PresentationFx testPresent;

	public static void main(String[] args) { launch(args); }
	
	@Override
	public void start(Stage frame) throws Exception {
		
		testPresent = new PresentationFx("testPresentation", "Lexxy", "v0.1", "for testing");
		
		XMLParser parser = new XMLParser("test2.xml");
		testPresent.addAllSlides(parser.getAllSlides());
		
		Scene scene = testPresent.Play();
		testPresent.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("PresentationFinished");
				frame.close();
			}
			
		});
		
		frame.setScene(scene);
	    frame.minWidthProperty().bind(scene.heightProperty().multiply(2));
	    frame.minHeightProperty().bind(scene.widthProperty().divide(2));
	    frame.show();
	    
	    
	}

}
