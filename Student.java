import java.util.*;

public class Student {
    // CLASS FIELDS

    private int studentId;
    private String name;
    private int age;
    private int level;
    private ArrayList<Integer> progressScores;
    private int pointsUsedForLevel = 0;

    // PARAMETER CONSTRUCTOR

    public Student ( int studentId, String name, int age, int level, ArrayList<Integer> progressScores, int pointsUsedForLevel ) {
        this.studentId = studentId;
        this.name = name;
        this.age = age;
        this.level = level;
        this.progressScores = progressScores;
        this.pointsUsedForLevel = pointsUsedForLevel;
    }

    public int getStudentId ( ) {
        return studentId;
    }

    public String getName ( ) {
        return name;
    }

    public int getAge ( ) {
        return age;
    }

    public int getPointsUsedForLevel ( ) {
        return pointsUsedForLevel;
    }

    public void setPointsUsedForLevel ( int pointsUsedForLevel ) {
        this.pointsUsedForLevel = pointsUsedForLevel;
    }

    // METHOD: updateProgress, +1 for correct, -1 for incorrect
    public void updateProgress ( boolean isCorrect ) {
        if ( isCorrect ) {
            progressScores.add ( 1 );
        } else {
            progressScores.add ( -1 );
        }

        checkLevelUp ( );
    }

    // Check if student can level up
    public void checkLevelUp ( ) {
        int totalPoints = 0;

        for ( int score : progressScores ) {
            totalPoints += score;
        }

        int newLevels = ( totalPoints - pointsUsedForLevel ) / 3;

        if ( newLevels > 0 ) {
            level += newLevels;
            pointsUsedForLevel += newLevels * 3;
        }
    }

    // METHOD: getStatistics
    public String getStatistics ( ) {
        double avgScore = getAverageScore ( );
        int totalLessons = progressScores.size ( );

        String statisticsReport = 
            "Student: " + name  + "\n" + 
            "Level: " + level + "\n" + 
            "Average Score: " + Math.round ( avgScore ) + "%" + "\n" + 
            "Total Lessons: " + totalLessons;

        return statisticsReport;
    }

    // METHOD: getlevel
    public int getlevel ( ) {
        return level;
    }

    // METHOD: getProgressScore
    public ArrayList<Integer> getProgressScore ( ) {
        return progressScores;
    }

    // METHOD: getAverageScore
    public double getAverageScore ( ) {
        if ( progressScores.isEmpty ( ) ) {
            return 0.0;
        }

        int total = 0;
        int countCorrect = 0;

        for ( int score : progressScores ) {
            if ( score == 1 ) {
                countCorrect++;
            }
            total += Math.abs ( score ); // total answered questions count
        }

        // average = (correct answers / total answered) * 100
        return ( ( double ) countCorrect / total ) * 100;
    }
}