package userInterface;

import account.Account;
import account.CharacterAttributes;
import account.WorkoutEntry;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.StackPane;

import java.time.LocalDateTime;

import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Seb on 25/05/2016.
 */
public class CharacterDashBoardController implements Controllable {

    Main mainApp;
    StackPaneUpdater screenParent;
    String username;
    CharacterAttributes attributes;

    @FXML
    StackPane avatarStackPane;

    @FXML
    ListView achievementListView;



    @FXML
    Label usernameLabel;
    @FXML
    Label strengthLabel;
    @FXML
    Label speedLabel;
    @FXML
    Label enduranceLabel;
    @FXML
    Label agilityLabel;

    @FXML
    RadioButton weekRadio;
    @FXML
    RadioButton monthRadio;

    ToggleGroup radioGroup;

    @FXML
    private CategoryAxis xAxis;

    @FXML
    private NumberAxis yAxis;

    @FXML
    private BarChart<String, Number> barChart;
    @FXML
    private LineChart<String, Number> lineChart;

    private ObservableList<String> achievementsList = FXCollections.observableArrayList();


    private List<WorkoutEntry> workoutHistoryLog;
    XYChart.Series seriesW = new XYChart.Series();
    XYChart.Series seriesM = new XYChart.Series();


    public void CharacterDashBoardController() {

    }

    @FXML
    private void initialize() {
        lineChart.setVisible(false);
        Account account = Main.account;

        attributes = account.getCharacterAttributes();
        username = account.getUsername();
        showAccountAttributes();

        CreateCharacter avatarContainer = new CreateCharacter(229.0, 263.0);
        avatarStackPane.getChildren().add(0, avatarContainer);

        ArrayList<String> achievementNames = HistoryAnalyser.searchForAchievementsInHistory();

        achievementsList = FXCollections.observableList(achievementNames);

        achievementListView.setItems(achievementsList);

        workoutHistoryLog = HistoryAnalyser.getDailyWorkoutHistoryFromCurrentAccount();

        radioGroup = new ToggleGroup();
        monthRadio.setToggleGroup(radioGroup);
        weekRadio.setToggleGroup(radioGroup);

        barChart.getData().add(seriesW);
        lineChart.getData().add(seriesM);

        // Initially set week radio to selected
        makeWeekSeriesFromAccountHistory();
        makeMonthSeriesFromAccountHistory();
        weekRadio.setSelected(true);

    }

    @FXML
    private void handleChartRadioActions() {
        if (weekRadio.isSelected()) {
            // remove month line chart from view
            lineChart.setVisible(false);
            // set week bar chart visible
            barChart.setVisible(true);
        }

        if (monthRadio.isSelected()) {
            // remove month line chart from view
            barChart.setVisible(false);
            // set week bar chart visible
            lineChart.setVisible(true);
        }

    }
    private void makeWeekSeriesFromAccountHistory() {

        Locale eng = Locale.UK;
        LocalDateTime today = LocalDateTime.now();

        String inputPattern     = "yyyyMMddHHmm";
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern(inputPattern, eng);

        String outputPattern    = "EEEE"; // day with text
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern(outputPattern, eng);

        // Variables for history entries
        String inputDate;
        long   timeToComplete;

        // LocalDateTime object for input date
        LocalDateTime dateOfCompletion;
        // String output for axis
        String formattedDateForAxis;

        long lastDate;


        // Search all workout entries in account's workout history
        // parse the input date string into a LocalDateTime for pattern formatting
        // find entries that are within the last week
        // add date against workout time to series
        for (WorkoutEntry entry:
                workoutHistoryLog) {

            // Get workout date from history entry
            inputDate = entry.getWorkoutDate();
            dateOfCompletion = LocalDateTime.parse(inputDate, inputFormatter);

            formattedDateForAxis = dateOfCompletion.format(outputFormatter);

            // Check if within the last 7 days
            if (dateOfCompletion.until(today, ChronoUnit.WEEKS) == 0) {
                timeToComplete  = entry.getWorkoutTime();
                seriesW.getData().add(new XYChart.Data(formattedDateForAxis, timeToComplete));
            }

        }


    }

    private void makeMonthSeriesFromAccountHistory() {

        Locale eng = Locale.UK;
        LocalDateTime today = LocalDateTime.now();

        String inputPattern     = "yyyyMMddHHmm";
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern(inputPattern, eng);

        String outputPattern    = "dd/MM"; // day with text
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern(outputPattern, eng);

        // Variables for history entries
        String inputDate;
        long   timeToComplete;

        // LocalDateTime object for input date
        LocalDateTime dateOfCompletion;
        // String output for axis
        String formattedDateForAxis;


        // Search all workout entries in account's workout history
        // parse the input date string into a LocalDateTime for pattern formatting
        // find entries that are within the last week
        // add date against workout time to series
        for (WorkoutEntry entry:
                workoutHistoryLog) {

            // Get workout date from history entry
            inputDate = entry.getWorkoutDate();
            dateOfCompletion = LocalDateTime.parse(inputDate, inputFormatter);


            formattedDateForAxis = dateOfCompletion.format(outputFormatter);


            // Check if within the last month
            if (dateOfCompletion.until(today, ChronoUnit.MONTHS) == 0) {
                timeToComplete  = entry.getWorkoutTime();

                seriesM.getData().add(new XYChart.Data(formattedDateForAxis, timeToComplete));
            }
        }

    }

    private void showAccountAttributes() {
        usernameLabel.setText(username);

        strengthLabel.setText(Integer.toString(attributes.getStrength()));
        speedLabel.setText(Integer.toString(attributes.getSpeed()));
        enduranceLabel.setText(Integer.toString(attributes.getEndurance()));
        agilityLabel.setText(Integer.toString(attributes.getAgility()));

    }



    @Override
    public void setScreenParent(StackPaneUpdater screenParent) {
        this.screenParent = screenParent;
    }

    @Override
    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }

}
