package presentationViewer;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

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
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class TextFx extends SlideContent {
	
	private static final int NORMAL = 0, BOLD = 1, ITALIC = 2, BOTH = 3;
	
	private int fontSize;
	private double xStart, yStart;
	private String text = "", sourceFile, font;
	private Color textColour;
	private TextFlow content;
	private ArrayList<String> sentenceStorage;
	private ArrayList<Integer> styleStorage;
	
	public TextFx(int startTime, int duration, double xStart, double yStart, 
				String sourceFile, String font, int fontSize, Color textColour, Integer targetLoc) {
		super(startTime, duration, targetLoc);
		this.xStart = xStart;
		this.yStart = yStart;
		this.sourceFile = sourceFile;
		this.font = font;
		this.fontSize = fontSize;
		this.textColour = textColour;
	}
	
	public Node createContent(Scene parent) {
		/*NumberBinding textWidth = scene.widthProperty().multiply(width);
		this.prefWidthProperty().bind(textWidth);
		NumberBinding textHeight = scene.heightProperty().multiply(height);
		this.prefHeightProperty().bind(textHeight);*/
		content = new TextFlow();
		NumberBinding textX = parent.widthProperty().multiply(xStart);
		content.layoutXProperty().bind(textX);
		NumberBinding textY = parent.heightProperty().multiply(yStart);
		content.layoutYProperty().bind(textY);
		
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
	
	private void loadTextFile() {
		BufferedReader br = null;
		String line = "";
		try {
			br = new BufferedReader(new FileReader("src/res/text/" + sourceFile));
			while ((line = br.readLine()) != null) {
				text += line;
			}
		} catch (FileNotFoundException e2) {
			e2.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
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
		loadTextFile();
		for (int i = 0; i < text.length(); i++) {
			if (!(i+3 > text.length())) {
				if (text.substring(i, i+3).matches("<b>")) {
					text = text.replaceFirst("<b>", "");
					bold = true;
					change = true;
				} 
			}
			if (!(i+3 > text.length())) {
				if (text.substring(i,i+3).matches("<i>")) {
					text = text.replaceFirst("<i>", "");
					italic = true;
					change = true;
				}
			}
			if (!(i+4 > text.length())) {
				if (text.substring(i, i+4).matches("</b>")) {
					text = text.replaceFirst("</b>", "");
					bold= false;
					change = true;
				}
			}
			if (!(i+4 > text.length())) {
				if (text.substring(i,i+4).matches("</i>")) {
					text = text.replaceFirst("</i>", "");
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
