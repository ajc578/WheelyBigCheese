package userInterface;

import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class NewsFeed extends HBox {

	Label news, title, author;
	Button view;
	
	public NewsFeed(double screenWidth, double screenHeight, String newsType, String newsTitle, String addedBy){
		
		news = new Label(newsType);
		news.setMinWidth(screenWidth*0.11);
		
		title = new Label(newsTitle);
		title.setMinWidth(screenWidth*0.16);
		
		author = new Label(addedBy);
		author.setMinWidth(screenWidth*0.06);
		
		view = new Button("VIEW");
		setButtonCursor(view);
		
		getChildren().addAll(news, title, author, view);
		setWidth(screenWidth*0.4);
		setHeight(screenHeight*0.2);
		setSpacing(screenWidth*0.005);
	}
	
	public void setButtonCursor (Button button) {
		button.setOnMouseEntered(event -> setCursor(Cursor.HAND));
		button.setOnMouseExited(event -> setCursor(Cursor.DEFAULT));
	}
}
