package userInterface;

import account.Achievement;
import account.WorkoutEntry;
import parser.WorkoutInfo;
import parser.XMLParser;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * A utility class that finds achievements and daily workout times from the active account's workout history.
 * <p> <STRONG> Developed by </STRONG> <p>
 * Sebastien Corrigan
 * <p> <STRONG> Developed for </STRONG> <p>
 * BOSS
 * @author Sebastien Corrigan
 *
 * @see XMLParser
 * @see WorkoutEntry
 * @see WorkoutInfo
 * @see Achievement a model class for a user achievement
 * @see CharacterDashBoardController
 * @see WorkoutOverviewController
 */
public class HistoryAnalyser {
    static Locale eng = Locale.UK;

    //================================================================================
    // public static members
    //================================================================================

    /**
     * Gets the authenticated user's workout history
     * @return history: a <code>List</code> of <code>workoutEntry</code>
     */
    public static List<WorkoutEntry> getWorkoutHistoryFromActiveAccount() {
        List<WorkoutEntry> history = Main.account.getHistory();
        return history;
    }

    /**
     * Collapses the workout history by day.
     * Uses {@link #getWorkoutHistoryFromActiveAccount() getWorkoutHistoryFromActiveAccount()} to retrieve the full
     * <code>workoutHistory</code>.
     * Finds <code> workoutEntry</code>s recorded on the same day and sums their <code>workoutTime</code>s to produce
     * a single <code>workoutEntry</code> for that day.
     * Used for charting progress charts in {@link CharacterDashBoardController}.
     * @return dailyWorkoutHistory a collapsed List of workouts where workout times on
     * the same day are collapsed to one <code>WorkoutEntry</code> whose <code>workoutTime</code> is the
     * sum of <code>workoutTime</code> for the day.
     */
    public static List<WorkoutEntry> getDailyWorkoutHistoryFromCurrentAccount() {
        List<WorkoutEntry> history = getWorkoutHistoryFromActiveAccount();
        List<WorkoutEntry> dailyWorkoutHistory = new ArrayList<>();

        // Sum workout time for same day
        for (WorkoutEntry entry :
                history) {

            int head = dailyWorkoutHistory.size();

            if (head != 0){
            String entryDaySubstring = entry.getWorkoutDate().substring(0,8);
            String collapsedEntryDaySubstring = dailyWorkoutHistory.get(head-1).getWorkoutDate().substring(0,8);
                /**
                 * Compares the day substrings
                 * same collapsing algorithm found in  history,
                 * @author Alex
                 */
                if(entryDaySubstring.contentEquals(collapsedEntryDaySubstring)){ //add workout times
                    dailyWorkoutHistory.get(head-1).setWorkoutTime(dailyWorkoutHistory.get(head-1).getWorkoutTime()+entry.getWorkoutTime());
                }else dailyWorkoutHistory.add(entry); // the next entry is not on the same day so add the entry
            }else dailyWorkoutHistory.add(entry); // first entry added
        }
        return dailyWorkoutHistory;
    }

    /**
     * Gets the presentation objects by calling {@link parser.XMLParser#retrieveAllWorkoutInfo() XMLParser.retrieveAllWorkoutInfo()}
     *
     * @return <code>ArrayList</code> of <code>WorkoutInfo</code>
     */
    public static ArrayList<WorkoutInfo> getWorkoutLibraryFromXMLCollection() {
        ArrayList<WorkoutInfo> library = XMLParser.retrieveAllWorkoutInfo();
        return library;
    }

    /**
     * @return List of workout presentation names available in the XML collection
     */
    private static List<String> getWorkoutNamesInWorkoutLibrary() {
        List<String>    names = new ArrayList<>();
        ArrayList<WorkoutInfo> workoutLibrary = getWorkoutLibraryFromXMLCollection();
        for (WorkoutInfo workout :
                workoutLibrary) {
            names.add(workout.getName());
        }
        return names;
    }

    /**
     * Builds an ArrayList of WorkoutInfos that have their lastCompletedDate fields set to the date last completed in the
     * active user's workout history.
     * @return workoutData with last completed dates
     */
    //For each WorkoutInfo (object representation of a workout presentation)
    // we look for matching Workout Entries in the active user's  history,
    //evaluate if this is the most recent completed entry for the WorkoutInfo in the history
    public static ArrayList<WorkoutInfo> getWorkoutLibraryWithLastCompletedDates() {
        /**
         * Updating the workout info data for last completed
         */
        String dataPointerName;
        String histPointerName;


        long histDate;
        long dataDate;
        long lastDate;

        ArrayList<WorkoutInfo> workoutData = getWorkoutLibraryFromXMLCollection();
        List<WorkoutEntry> workoutHistoryList = getWorkoutHistoryFromActiveAccount();


        for (WorkoutInfo wLibPointer : workoutData) {

            dataPointerName = wLibPointer.getName();
            lastDate = 0;

            for (WorkoutEntry wHistPointer : workoutHistoryList) {

                histPointerName = wHistPointer.getWorkoutName();

                // find match with workout in workout library
                if (dataPointerName.equals(histPointerName)) {

                    // get dates for both history and data to compare
                    histDate = Long.parseLong(wHistPointer.getWorkoutDate());
                    dataDate = Long.parseLong(wLibPointer.getLastCompletedDate());

                    // if history date is most recent, update lastDate
                    if (histDate > dataDate && histDate > lastDate) {
                        lastDate = histDate;
                    }
                }
            }



            if (lastDate == 0) { // no match
                // prevent exception that would occur in parsing a 0 date
                // in changeDatePatternTo() method
            }
            else {
                String lastDateString = changeDatePatternTo("yyyyMMddHHmm", "dd/MM/yy", Long.toString(lastDate));
                wLibPointer.setLastCompletedDate(lastDateString);
            }
        }

        return workoutData;
    }

    /**
     * Reformats a date string from the specified input format pattern to the output format pattern.
     * @param inputPattern
     * @param outputPattern
     * @param dateString
     * @return dateStringOutput a string
     */
    public static String changeDatePatternTo(String inputPattern, String outputPattern, String dateString) {
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern(inputPattern, eng);

        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern(outputPattern, eng);

        LocalDateTime date = LocalDateTime.parse(dateString, inputFormatter);

        String dateStringOutput = "";

        dateStringOutput = LocalDateTime.parse(dateString, inputFormatter).format(outputFormatter);
        return dateStringOutput;

    }

    /**
     * Builds a list of completed achievements by looking at the active account's workout history
     * <b>Note:</b> for this current release it just looks for arbitrary workout achievements based on number of completions
     * these are workout milestones.
     * In future release we could use this method to also search for discipline achievements such as "exercised 3 days in a row"
     * @return <code>ArrayList</code> of String that indicates which {@link account.Achievement Achievement} the user
     * has completed.
     *
     */
    public static ArrayList<String> searchForAchievementsInHistory() {

        List<WorkoutEntry> history = getWorkoutHistoryFromActiveAccount();
        List<WorkoutInfo> library = getWorkoutLibraryFromXMLCollection();

        ArrayList<String> allCompletedAchievementNames = new ArrayList<>();
        List<Achievement> workoutAchievements = buildCompletionAchievements();

        int numberOfCompletions = 0;

        for (WorkoutInfo workout:
             library) {
            numberOfCompletions = findNumberOfCompletionsInHistory(workout, history);
            for (Achievement achievement:
                 workoutAchievements) {

                // Based on number of completions
                if (numberOfCompletions > achievement.getThreshold()) {
                    if (achievement.isComplete()){

                    } else {
                        achievement.setComplete(true);
                        allCompletedAchievementNames.add(achievement.getContent());

                    }
                }

            }
        }

        return allCompletedAchievementNames;
    }

    /**
     * Finds how many times a workout was completed by the user
     * @param workout
     * @param history
     * @return number of times completed
     */
    private static int findNumberOfCompletionsInHistory(WorkoutInfo workout, List<WorkoutEntry> history) {
        int numberOfCompletions = 0;

        for (WorkoutEntry entry :
                history) {
            if (workout.getName().equals(entry.getWorkoutName())) {
                numberOfCompletions++;
            }
        }

        return numberOfCompletions;
    }

    /**
     * Makes new achievements for informing the user of number of workouts completed achievements.
     * @return
     */
    public static List<Achievement> buildCompletionAchievements() {
        List<Achievement> completionAchievements = new ArrayList();


        String[] achievementTitles = {
                "Completed a workout!",
                "Completed a workout 3 times",
        };


        int[]   thresholds          = {
                1,
                3,
        };

        int i = 0;
        Achievement achievement;
        for (String achievementTitle:
                achievementTitles) {

            achievement = new Achievement();

            achievement.setContent(achievementTitle);
            achievement.setThreshold(thresholds[i]);

            completionAchievements.add(achievement);

            i++;
        }

        return completionAchievements;

    }

    /**
     * Builds discipline achievements
     * <b>Note:</b> not searched for in this release.
     * @return
     */
    public static List<Achievement> buildDisciplineAchievements() {
        List<Achievement> disciplineAchievements = new ArrayList();


        String[] achievementTitles = {
                "3 day streak",
                "1 week streak",
                "2 week streak",
                "3 week streak",
                "50 day streak",
                "100 day streak"
        };


        int[]   thresholds          = {
                3,
                7,
                14,
                21,
                50,
                100
        };

        int i = 0;
        Achievement achievement;
        for (String achievementTitle:
             achievementTitles) {

            achievement = new Achievement();

            achievement.setContent(achievementTitle);
            achievement.setThreshold(thresholds[i]);

            disciplineAchievements.add(achievement);

            i++;
        }

        return disciplineAchievements;

    }

}
