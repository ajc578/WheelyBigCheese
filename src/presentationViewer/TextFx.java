package presentationViewer;


/*
 * Author : Oliver Rushton
 * Group: 4
 * Description: This module detects for bold and italic tags in the source string
 * 				and sperates that string into different javaFX Text objects with 
 * 				their separate font styles. Then the separate Text objects are added 
 * 				together in a TextFlow object.
 */

import java.util.ArrayList;

import javafx.beans.binding.NumberBinding;
import javafx.scene.Node;
import javafx.scene.SubScene;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class TextFx extends SlideContent {
	
	private static final int NORMAL = 0, BOLD = 1, ITALIC = 2, BOTH = 3;
	
	private int fontSize;
	private double xStart, yStart, width, height;
	private String text, font;
	private Color textColour;
	private TextFlow content;
	private ArrayList<String> sentenceStorage;
	private ArrayList<Integer> styleStorage;
	
	public TextFx(int startTime, int duration, double xStart, double yStart, double width, double height, 
				String text, String font, int fontSize, Color textColour, Integer targetLoc) {
		super(startTime, duration, targetLoc);
		this.xStart = xStart;
		this.yStart = yStart;
		this.width = width;
		this.height = height;
		this.text = text;
		this.font = font;
		this.fontSize = fontSize;
		this.textColour = textColour;
	}
	
	public Node createContent(SubScene parent) {

		content = new TextFlow();
		NumberBinding textX = parent.widthProperty().multiply(xStart);	
		NumberBinding textY = parent.heightProperty().multiply(yStart);
		NumberBinding textWidth = parent.widthProperty().multiply(width);
		NumberBinding textHeight = parent.heightProperty().multiply(height);
		content.layoutXProperty().bind(textX);
		content.layoutYProperty().bind(textY);
		
		if (height != -1)content.prefHeightProperty().bind(textHeight);
		if (width != -1)content.prefWidthProperty().bind(textWidth);
		
		detectTextStyle();
		content.setVisible(false);
		return content;
	}
	
	public Node getContent() {
		return content;
	}
	
	public String getType() {
		return "TextFx";
	}
		
	private int getLastChangeIndex(ArrayList<String> sentenceStorage) {
		int changeIndex = 0;
		if (sentenceStorage.size() == 0) {
			return 0;
		} else {
			for (int i = 0; i < sentenceStorage.size(); i++) {
				changeIndex += sentenceStorage.get(i).length();
			}
			return changeIndex;
		}
	}
	
	private void detectTextStyle() {
		sentenceStorage = new ArrayList<String>();
		styleStorage = new ArrayList<Integer>();
		int currentStyle = NORMAL;
		boolean bold = false, italic = false, change = false;
		for (int i = 0; i < text.length(); i++) {
			if (!(i+3 > text.length())) {
				if (text.substring(i, i+3).equals("%b%")) {
					text = text.replaceFirst("%b%", "");
					bold = true;
					change = true;
				} 
			}
			if (!(i+3 > text.length())) {
				if (text.substring(i,i+3).equals("%i%")) {
					text = text.replaceFirst("%i%", "");
					italic = true;
					change = true;
				}
			}
			if (!(i+4 > text.length())) {
				if (text.substring(i, i+4).equals("%/b%")) {
					text = text.replaceFirst("%/b%", "");
					bold= false;
					change = true;
				}
			}
			if (!(i+4 > text.length())) {
				if (text.substring(i,i+4).equals("%/i%")) {
					text = text.replaceFirst("%/i%", "");
					italic = false;
					change = true;
				}
			}
			if (i + 1 == text.length()) {
				change = true;
			}
			if (change) {
				if(i + 1 == text.length())  {
					sentenceStorage.add(text.substring(getLastChangeIndex(sentenceStorage), i+1));
					styleStorage.add(currentStyle);
				} else {
					if (i != getLastChangeIndex(sentenceStorage)) {
						sentenceStorage.add(text.substring(getLastChangeIndex(sentenceStorage), i));
						styleStorage.add(currentStyle);
					}
				}
				if (!bold && !italic) {
					currentStyle = NORMAL;
				} else if (bold && !italic) {
					currentStyle = BOLD;
				} else if (!bold && italic) {
					currentStyle = ITALIC;	
				} else {
					currentStyle = BOTH;
				}
				change = false;
				if (!(i == text.length() - 1) && i != 0) {
					i-=1;
				}
			}
			
		}
		System.out.println(sentenceStorage.size());
		for (int j = 0; j < sentenceStorage.size(); j++) {
			Text text = new Text(sentenceStorage.get(j));
			if (styleStorage.get(j) == NORMAL) {
				text.setFont(Font.font(font,FontWeight.NORMAL,FontPosture.REGULAR,fontSize));
			} else if (styleStorage.get(j) == BOLD) {
				text.setFont(Font.font(font,FontWeight.BOLD,FontPosture.REGULAR,fontSize));
			} else if (styleStorage.get(j) == ITALIC) {
				text.setFont(Font.font(font,FontWeight.NORMAL,FontPosture.ITALIC,fontSize));
			} else {
				text.setFont(Font.font(font,FontWeight.BOLD,FontPosture.ITALIC,fontSize));
			}
			text.setFill(textColour);
			content.getChildren().add(text);
		}
		
	}
	
	//For tests
	public String getText() {
		String text = "";
		for (int i = 0; i < sentenceStorage.size(); i++) {
			text += sentenceStorage.get(i);
		}
		return text;
	}
	
	public int getNumOfStyleChanges() {
		int num = sentenceStorage.size();
		return num;
	}
	
	public int getFontSize() {
		return fontSize;
	}
	
	public String getFont() {
		return font;
	}
	
	public Color getFontColour() {
		return textColour;
	}
	
	
}
