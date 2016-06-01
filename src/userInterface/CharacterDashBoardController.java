package userInterface;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import account.Account;
import account.CharacterAttributes;
import account.WorkoutEntry;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
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
 * Controller class for characterDashBoard.fxml
 * <p>
 *     Displays a dashboard for the information pertaining to the authenticated user's virtual character.
 *     Implements the methods to display nodes defined in characterDashboard.fxml and handle user actions on the
 *     character dashboard screen.
 *     <ul>
 *         <li>Displays and allows customising of the user's avatar.</li>
 *         <li>Allows the user to toggle between a monthly/weekly view of their most up to date workout time.</li>
 *         <li>Shows the user's character attributes</li>
 *         <li>Shows a list of accomplished achievements defined in {@link HistoryAnalyser}</li>
 *     </ul>
 * <p> <STRONG> Developed by </STRONG><p>
 * Sebastien Corrigan
 *
 * <p> <STRONG> Developed for </STRONG><p>
 * BOSS
 *
 * @see HistoryAnalyser
 * @see account.Achievement
 * @see Account
 * @see CreateCharacter
 *
 * @author Sebastien Corrigan
 * @version 2
 */
public class CharacterDashBoardController implements Controllable {
    //================================================================================
    // Fields
    //================================================================================

    // References to main and screen parent
    Main mainApp;
    StackPaneUpdater screenParent; // stack pane that will hold the graphical elements loaded from this fxml/controller

    //----------------------------------------------------------//
    // Character attributes and avatar
    /**
     * a <code>CharacterAttributes</code> object
     * @see CharacterAttributes
     */
    CharacterAttributes attributes;
    // labels for account username and attributes
    // these are situated in the bottom left hand corner gridpane at column index 1
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
    // stack pane to hold nodes made by a CreateCharacter object
    @FXML
    StackPane avatarStackPane;

    //----------------------------------------------------------//
    // Progress charts
    /**
     * stores a collapsed workout history whereby entries on the same day are collapsed
     * to one entry with its workoutTime set to total sum of workoutTime on that day.
     */
    private List<WorkoutEntry> workoutHistoryLog;
    @FXML
    private BarChart<String, Number> weekBarChart;
    @FXML
    private LineChart<String, Number> monthLineChart;
    /**
     * a series of points representing this week's workout times (same day workouts are collapsed into one longer workout)
     */
    XYChart.Series seriesW = new XYChart.Series();
    /**
     * a series of points representing this month's workout times (same day workouts are collapsed into one longer workout)
     */
    XYChart.Series seriesM = new XYChart.Series();

    /**
     * radio button that toggles the progress chart display to the weekBarChart and is within the ToggleGroup
     * <code>radioGroup</code>: when this radio is selected by the user, all others are unselected
     */
    @FXML
    RadioButton weekRadio;
    /**
     * radio button that toggles the progress chart display to the monthLineChart and is within the ToggleGroup
     * <code>radioGroup</code>: when this radio is selected by the user, all others are unselected
     */
    @FXML
    RadioButton monthRadio;
    /**
     * group that manages the <code>weekRadio</code> and <code>monthRadio</code> radio buttons
     */
    ToggleGroup radioGroup;

    //----------------------------------------------------------//
    // Achievements
    /**
     * list view for the achievements completed by the authenticated user
     */
    @FXML
    ListView achievementListView;


    //================================================================================
    // Constructor and initialize
    //================================================================================
    /**
     * Constructor is empty for this class
     */
    public void CharacterDashBoardController() {
    }
    /**
     * Called after @FXML annotated fields are injected and so is used to update the view with the data built in the
     * constructor.
     *
     * Retrieves history and achievement information from the account to build the charts and set the text for attribute
     * and username labels.
     *
     * First displays the user's workout activity in the last seven days but the user is allowed to switch between
     * last 30 days/ last 7 days charts
     *
     *
     */
    @FXML
    private void initialize() {


        // build the series from the workout history information
        workoutHistoryLog = HistoryAnalyser.getDailyWorkoutHistoryFromCurrentAccount();
        makeWeekSeriesFromAccountHistory();
        makeMonthSeriesFromAccountHistory();

        //----------------------------------------------------------//
        // Character attributes and avatar
        // Get the authenticated and up to date account and get its attributes
        Account account = Main.account;
        attributes = account.getCharacterAttributes();

        //----------------------------------------------------------//
        // Progress charts

        // immediately remove the line chart that displays the last 30 days of workout activity from view
        monthLineChart.setVisible(false);

        // add the series to their charts
        weekBarChart.getData().add(seriesW);
        monthLineChart.getData().add(seriesM);

        // manage the toggling feature between month and weekRadios
        radioGroup = new ToggleGroup();
        monthRadio.setToggleGroup(radioGroup);
        weekRadio.setToggleGroup(radioGroup);
        // Initially set week radio to selected
        weekRadio.setSelected(true);

        showAccountAttributes();

        showAvatarInStackPane();

        showAchievementsInListView();


    }

    //================================================================================
    // Members
    //================================================================================
    //----------------------------------------------------------//
    // Progress charts
    /**
     * Uses <code>List workoutHistoryLog</code> to build the bar chart series:
     * <ul>
     *     <li>Selects the last 7 days of workout history</li>
     *     <li>retrieves history data and formats dates for <code>xDateAxis</code> and
     *     total workout time for that day for the <code>yWorkoutTimeAxis</code></li>
     *     <li>adds all data to the series</li>
     * </ul>
     */
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

    /**
     * Uses <code>List workoutHistoryLog</code> to build the bar chart series:
     * <ul>
     *     <li>Selects the last 30 days of workout history</li>
     *     <li>retrieves history data and formats dates for <code>xDateAxis</code> and
     *     total workout time for that day for the <code>yWorkoutTimeAxis</code></li>
     *     <li>adds all data to the series</li>
     * </ul>
     */
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
        long input;

        // LocalDateTime object for input date
        LocalDateTime dateOfCompletion;
        //LocalDateTime newDate = LocalDateTime.of(1900, 05, 16, 03);

        // String output for axis
        String formattedDateForAxis;


        // Search all workout entries in account's workout history
        // parse the input date string into a LocalDateTime for pattern formatting
        // find entries that are within the last week
        // add date against workout time to series
        for(int i = 0; i <workoutHistoryLog.size(); i++) {

            // Get workout date from history entry
            inputDate = workoutHistoryLog.get(i).getWorkoutDate();
            //input = Long.parseLong(inputDate);

            dateOfCompletion = LocalDateTime.parse(inputDate, inputFormatter);

            formattedDateForAxis = dateOfCompletion.format(outputFormatter);


            // Check if within the last month
            if (dateOfCompletion.until(today, ChronoUnit.MONTHS) == 0) {

                timeToComplete  = workoutHistoryLog.get(i).getWorkoutTime();

                seriesM.getData().add(new XYChart.Data(formattedDateForAxis, timeToComplete));

                if (i != workoutHistoryLog.size()-1) {
                    int diff = getDifference(inputDate, workoutHistoryLog.get(i+1).getWorkoutDate());
                    LocalDateTime noWorkoutDate = dateOfCompletion;


                    if (diff > 1) {
                        for (int j = 0; j<diff-1; j++) {
                            noWorkoutDate.plusDays(j+1);
                            System.out.println(noWorkoutDate.plusDays(j+1).format(outputFormatter));
                            seriesM.getData().add(new XYChart.Data(noWorkoutDate.plusDays(j+1).format(outputFormatter), 0));
                        }
                        System.out.println("out");
                    }
                }
            }
        }
    }

    //----------------------------------------------------------//
    // Actions

    /**
     * Handle user actions on the toggle group to switch between weekly bar chart and monthly line chart
     */
    @FXML
    private void handleChartRadioActions() {
        if (weekRadio.isSelected()) {
            // remove month line chart from view
            monthLineChart.setVisible(false);
            // set week bar chart visible
            weekBarChart.setVisible(true);
        }

        if (monthRadio.isSelected()) {
            // remove month line chart from view
            weekBarChart.setVisible(false);
            // set week bar chart visible
            monthLineChart.setVisible(true);
        }

    }


    //----------------------------------------------------------//
    // Show methods

    /**
     * Calls a {@link HistoryAnalyser#searchForAchievementsInHistory() searchForAchievementsInHistory} method in
     * <code>HistoryAnalyser</code> and populates the <code>achievementListView</code> with the <code>ArrayList</code>
     * of <code>String</code> returned.
     */
    private void showAchievementsInListView() {
        ArrayList<String> achievementNames = HistoryAnalyser.searchForAchievementsInHistory();
        achievementListView.setItems(FXCollections.observableList(achievementNames));
    }


    /**
     * Instatiates a new CreateCharacter and adds its nodes <code>avatarStackPane</code>
     * Allows the user to edit the character from the character dashboard
     */
    private void showAvatarInStackPane() {
        // Add the editable avatar to the stackpane (left hand side)
        CreateCharacter avatarContainer = new CreateCharacter(229.0, 263.0);
        avatarStackPane.getChildren().add(0, avatarContainer);
    }

    /**
     * Sets the text for the labels that shows the character fields pertaining to the user's character and account:
     * <ul>
     *     <li>Username</li>
     *     <li>Character attributes: strength, speed, endurance, agility</li>
     * </ul>
     */
    private void showAccountAttributes() {
        usernameLabel.setText(Main.account.getUsername());
        strengthLabel.setText(Integer.toString(attributes.getStrength()));
        speedLabel.setText(Integer.toString(attributes.getSpeed()));
        enduranceLabel.setText(Integer.toString(attributes.getEndurance()));
        agilityLabel.setText(Integer.toString(attributes.getAgility()));

    }



    /**
     * Finds difference between two dates that obey the format yyyyMMddHHmm and that are within the same month
     * @param firstDate date string that is chronologically before the second date
     * @param secondDate date string that represents a more recent date
     * @return diff the difference between the second date and the first date
     *
     */
    private int getDifference(String firstDate, String secondDate) {
        int days1 = 0;
        int days2 = 0;

        days1 = Integer.parseInt(firstDate.substring(6, 8));
        days2 = Integer.parseInt(secondDate.substring(6, 8));

        int diff = days2-days1;
        return diff;
    }

    /**
     * This method is implemented for the Controllable interface. Gives the instance of the app's
     * {@link StackPaneUpdater} so that the {@link StackPaneUpdater#setScreen(String)}  setScreen(screenID)} method
     * can be called from within this screen.
     * <b>Note:</b> this screen does not currently need this reference because the other screens are accessed by the
     * tab menu options.
     * however it implements controllable for a potential
     * future need to change the screen using a button in this screen eg. for a new shop menu
     * @param screenParent the parent node for this screen
     */
    @Override
    public void setScreenParent(StackPaneUpdater screenParent) {
        this.screenParent = screenParent;
    }

    /**
    * This method is implemented for the Controllable interface.
    */
    @Override
    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }

}
