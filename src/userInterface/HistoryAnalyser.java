package userInterface;

import account.Achievement;
import account.WorkoutEntry;
import javafx.collections.FXCollections;
import parser.WorkoutInfo;
import parser.XMLParser;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.ENGLISH);
        String dataPointerName;
        String histPointerName;

        Date histPointerDate;
        Date dataPointerDate;
        Date histLastCompleted;
        String initDate = "2015/05/10 01:01";

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
                    System.out.println("match with " + histPointerName);
                    System.out.println("workout time " + wHistPointer.getWorkoutTime());

                    // get dates for both history and data to compare
                    histDate = Integer.parseInt(wHistPointer.getWorkoutDate());
                    dataDate = Integer.parseInt(wLibPointer.getLastCompletedDate());


                    System.out.println("data date is: " + dataDate);

                    // if history date is most recent, update lastDate
                    if (histDate > dataDate && histDate > lastDate) {
                        lastDate = histDate;
                    }
                }
            }

            wLibPointer.setLastCompletedDate(Long.toString(lastDate));
            System.out.println("last date for " + dataPointerName + wLibPointer.getLastCompletedDate());

        }

        return workoutData;
    }

    public static String changeDatePatternTo(String inputPattern, String outputPattern, String dateString) {



        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern(inputPattern, eng);


        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern(outputPattern, eng);

        String dateStringOutput = "";

        dateStringOutput = LocalDateTime.parse(dateString, inputFormatter).format(outputFormatter);
        return dateStringOutput;

    }

//    public static List<WorkoutEntry> getWorkoutEntriesWithinWeek() {
//
////        List<WorkoutEntry> allEntries;
////        List<WorkoutEntry> selectedEntries = null;
////
////        allEntries = getWorkoutHistoryFromCurrentAccount();
////
////        String inputDate;
////        String formattedDate;
////
////        LocalDateTime dateOfCompletion;
////        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern(inputPattern, eng);
////
////        for (WorkoutEntry entry:
////             allEntries) {
////
////            inputDate = entry.getWorkoutDate();
////
////            dateOfCompletion = LocalDateTime.parse(inputDate, inputFormatter);
////
////            // Check if with in the last 7 days
////            if (dateOfCompletion.until(today, ChronoUnit.WEEKS) == 0) {
////                formattedDate = changeDatePatternTo("yyyyMMddHHmm", "EEEE", inputDate);
////                entry.setWorkoutDate(formattedDate);
////                selectedEntries.add(entry);
////            }
////        }
////        return selectedEntries;
//    }

    public static List<Achievement> findAchievementsInWorkoutHistory() {

        List<WorkoutEntry> history = getWorkoutHistoryFromCurrentAccount();
        List<WorkoutInfo> library = getWorkoutLibraryFromXMLCollection();

        List<Achievement> allCompletedAchievements = new ArrayList<>();
        List<Achievement> workoutAchievements = buildCompletionAchievements();

        int numberOfCompletions = 0;

        for (WorkoutInfo workout:
             library) {
            numberOfCompletions = findNumberOfCompletionsInHistory(workout, history);
            for (Achievement achievement:
                 workoutAchievements) {
                if (numberOfCompletions > achievement.getThreshold()) {
                    allCompletedAchievements.add(achievement);
                }

            }
        }

        return allCompletedAchievements;
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

    private static void findStreaks(WorkoutEntry history, List<Achievement> streakAchievements) {

        getDaysOfTheYear();


    }

    private static long[] getDaysOfTheYear() {

        long[] dates = new long[365];

        Calendar calendar = new GregorianCalendar();
        SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMddHHmm");
        fmt.setCalendar(calendar);
        // Get today's date formatted
        String dateFormatted = fmt.format(calendar.getTime());
        long today = Long.parseLong(dateFormatted);

        long lastYear = today - 365;
        long date = lastYear;

        int i = 0;

        while (date < today) {
            dates[i] = date;
            i++;
        }

        return dates;


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
