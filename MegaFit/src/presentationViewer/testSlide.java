package presentationViewer;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import  javax.swing.Timer;
import javax.swing.*;

class testSlide extends JPanel implements ActionListener, MouseListener{
	
	private ArrayList<VisualElement> displaying, elements;
	private JFrame frame;
	private Timer sequencerTimer;
	private int sequencerCounter;
	private boolean finished = false;
	private String destination = "";
	
	public static void main(String[] args) {
		new testSlide();
	}
	
	public testSlide() {
		makeFrame();
		
		this.setBackground(Color.PINK);
		elements = new ArrayList<VisualElement>();
		displaying = new ArrayList<VisualElement>();
		
		elements.add(new Circle(0,1,0,1, 30,30,30,30,30,30,Color.blue,60,60,Color.GREEN, Color.BLACK, "circle1"));
		elements.add(new Circle(1,3,1,3, 70,70,20,20,30,30,Color.blue,60,60,Color.GREEN, Color.BLACK, "circle2"));
		elements.add(new Circle(2,5,2,5, 10,10,15,15,30,30,Color.blue,60,60,Color.GREEN, Color.BLACK, "circle3"));
		elements.add(new Circle(4,6,4,6, 70,10,20,15,30,30,Color.blue,60,60,Color.GREEN, Color.BLACK, "circle4"));
		
		sequencerTimer = new Timer (1000, this);
		sequencerTimer.setInitialDelay(0);
		sequencerCounter = 0;
		sequencerTimer.start();
	}
	

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (VisualElement i : displaying) {
			i.display(g, this.getWidth(), this.getHeight());
		}
    }
    
	 // Create a window frame
    public void makeFrame() {

        // Instantiate a window frame using Swing class JFrame
        frame = new JFrame("Test Slide");

        // When the window is closed terminate the application
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        // set initial size of window
        frame.setSize(800, 600);

        // add the mainAreaPanel containing the control panels and the Panel for the current object (the drawing area)
        frame.add(this, BorderLayout.CENTER);
        frame.setVisible(true);
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == sequencerTimer){	
			if (sequencerCounter == 8){
				sequencerCounter = 0;
			}
	        for (VisualElement i : elements) {
				if(i.getStartTime() == sequencerCounter){
					displaying.add(i);
				}else if(i.getEndTime() == sequencerCounter){
					displaying.remove(i);
				}
			}
			sequencerCounter++;
		}
        this.repaint();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		 if (e.getButton() == 1){
			 for (VisualElement i : displaying) {
				 if (i.contains(e.getX(), e.getY(), this.getWidth(), this.getHeight())){
					 finished = true;
					 destination = i.getTargetLoc();
				 }
			 }
		}
		repaint();
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	} 
}
