package handlers;

import java.io.PrintWriter;
import java.io.StringWriter;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

public class ExceptionFx {
	
	private Alert alert;
	private GridPane alertContent;
	
	public ExceptionFx(Exception e, AlertType type, String title, String header, String context) {
		alert = new Alert(type);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(context);

		// Create expandable Exception.
		StringWriter sw = new StringWriter();
		e.printStackTrace(new PrintWriter(sw));
		String exceptionStackTrace = sw.toString();

		Label label = new Label("The exception stacktrace was:");

		TextArea textArea = new TextArea(exceptionStackTrace);
		textArea.setEditable(false);
		textArea.setWrapText(true);

		textArea.setMaxWidth(Double.MAX_VALUE);
		textArea.setMaxHeight(Double.MAX_VALUE);
		GridPane.setVgrow(textArea, Priority.ALWAYS);
		GridPane.setHgrow(textArea, Priority.ALWAYS);

		alertContent = new GridPane();
		alertContent.setMaxWidth(Double.MAX_VALUE);
		alertContent.add(label, 0, 0);
		alertContent.add(textArea, 0, 1);
		
		// Set expandable Exception into the dialog pane.
		alert.getDialogPane().setExpandableContent(alertContent);
	}
	
	public ExceptionFx(AlertType type, String title, String header, String context) {
		alert = new Alert(type);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(context);
	}
	
	public void show() {
		alert.show();
	}
	
}
