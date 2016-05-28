package userInterface;

import account.Account;
import account.CharacterAttributes;
import account.WorkoutEntry;
import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

import java.time.LocalDateTime;

import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
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
    private CategoryAxis xAxis;

    @FXML
    private NumberAxis yAxis;

    @FXML
    private LineChart<String, Number> lineChart;


    private List<WorkoutEntry> workoutHistoryLog;
    XYChart.Series series = new XYChart.Series();


    public void CharacterDashBoardController() {

    }

    @FXML
    private void initialize() {
        Account account = Main.account;
        attributes = account.getCharacterAttributes();
        username = account.getUsername();
        workoutHistoryLog = account.getHistory();

        CreateCharacter avatarContainer = new CreateCharacter(229.0, 263.0);
        avatarStackPane.getChildren().add(0, avatarContainer);


        makeSeriesFromAccountHistory();
        showAccountAttributes();

        lineChart.setTitle("Progress Chart");

        lineChart.getData().add(series);
    }

    private void makeSeriesFromAccountHistory() {



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

                series.getData().add(new XYChart.Data(formattedDateForAxis, timeToComplete));
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
