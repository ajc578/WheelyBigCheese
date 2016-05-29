package presentationViewer;

import java.io.PrintWriter;
import java.io.StringWriter;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;

public class ExceptionFx {

	private Alert alert;
	private GridPane alertContent;
	/**
	 * Constructor creates an {@link Alert} with the given parameters
	 * with the option to view the <tt>stack trace</tt>.
	 *
	 * @param e - The {@link Exception}
	 * @param type - The description of the alert
	 * @param title - The Title of the alert
	 * @param header - The header of the alert
	 * @param context - The context of the alert
	 *
	 * @see Alert
	 */
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
	/**
	 * Alternate Constructor to create an {@link Alert} without the
	 * exception <tt>stack trace</tt>.
	 *
	 * @param type - The description of the alert
	 * @param title - The Title of the alert
	 * @param header - The header of the alert
	 * @param context - The context of the alert
	 */
	public ExceptionFx(AlertType type, String title, String header, String context) {
		alert = new Alert(type);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(context);
	}
	/**
	 *
	 * @param type
	 * @param title
	 * @param header
	 * @param context
	 * @param stage
	 */
	public ExceptionFx(AlertType type, String title, String header, String context, Stage stage) {
		alert = new Alert(type);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(context);
		alert.initOwner(stage);
	}
	/**
	 * Shows the alert
	 *
	 * @see Alert#show()
	 */
	public void show() {
		alert.show();
	}

}
