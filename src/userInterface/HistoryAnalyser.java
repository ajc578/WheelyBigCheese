package userInterface;

import account.Achievement;
import account.WorkoutEntry;
import javafx.collections.FXCollections;
import parser.WorkoutInfo;
import parser.XMLParser;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

/**
 * Created by Seb on 28/05/2016.
 */
public class HistoryAnalyser {
    static Locale eng = Locale.UK;
    static LocalDateTime today = LocalDateTime.now();
    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.ENGLISH);


    public static List<WorkoutEntry> getWorkoutHistoryFromCurrentAccount() {
        List<WorkoutEntry> history = Main.account.getHistory();
        return history;
    }

    public static ArrayList<WorkoutInfo> getWorkoutLibraryFromXMLCollection() {
        ArrayList<WorkoutInfo> library = XMLParser.retrieveAllWorkoutInfo();
        return library;
    }

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
        List<WorkoutEntry> workoutHistoryList = getWorkoutHistoryFromCurrentAccount();


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

            String lastDateString = changeDatePatternTo("yyyyMMddHHmm", "dd/MM/yy", Long.toString(lastDate));

            wLibPointer.setLastCompletedDate(lastDateString);


        }

        return workoutData;
    }

    public static String changeDatePatternTo(String inputPattern, String outputPattern, String dateString) {



        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern(inputPattern, eng);


        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern(outputPattern, eng);

        LocalDateTime date = LocalDateTime.parse(dateString, inputFormatter);

        String dateStringOutput = "";

        dateStringOutput = LocalDateTime.parse(dateString, inputFormatter).format(outputFormatter);
        return dateStringOutput;

    }

    public static ArrayList<String> searchForAchievementsInHistory() {

        List<WorkoutEntry> history = getWorkoutHistoryFromCurrentAccount();
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
                        allCompletedAchievementNames.add(achievement.getContent());
                    }
                }

            }
        }

        return allCompletedAchievementNames;
    }

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

    public static List<Achievement> buildCompletionAchievements() {
        List<Achievement> completionAchievements = new ArrayList();


        String[] achievementTitles = {
                "Completed a workout",
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
