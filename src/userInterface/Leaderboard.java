package userInterface;

import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class Leaderboard extends TableView {
	
	public Leaderboard(ObservableList<User> data) {
		
		setEditable(false);
		setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		
		TableColumn usernameColumn = new TableColumn("Name");
		TableColumn levelColumn = new TableColumn("Level");
		TableColumn xpColumn = new TableColumn("XP Points");
		TableColumn skillPointsColumn = new TableColumn("Skill Points");
		TableColumn gainzColumn = new TableColumn("Gainz");
		
		usernameColumn.setCellValueFactory(new PropertyValueFactory<User, String>("username"));
		levelColumn.setCellValueFactory(new PropertyValueFactory<User, Integer>("level"));
		xpColumn.setCellValueFactory(new PropertyValueFactory<User, Integer>("xpInt"));
		skillPointsColumn.setCellValueFactory(new PropertyValueFactory<User, Integer>("skillz"));
		gainzColumn.setCellValueFactory(new PropertyValueFactory<User, Integer>("gainz"));
		
		setItems(data);
		getColumns().addAll(usernameColumn, levelColumn, xpColumn, skillPointsColumn, gainzColumn);
		
	}

	public void setData(ObservableList<User> data) {
		getItems().clear();
		setItems(data);
	}
	
}
