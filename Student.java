import java.util.*;

public class Student {
    // CLASS FIELDS

    private int studentId;
    private String name;
    private int age;
    private int level;
    private int currentPoints;  // points tracking
    private ArrayList<Integer> progressScores;

    // PARAMETER CONSTRUCTOR
    public Student(int studentId, String name, int age, int level, ArrayList<Integer> progressScores) {
        this.studentId = studentId;
        this.name = name;
        this.age = age;
        this.level = level;
        this.progressScores = progressScores;
        this.currentPoints = 0;
    }

    public int getStudentId() {
        return studentId;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    // METHOD: updateProgress, +1 true, -1 false
    public void updateProgress(boolean isCorrect) 
    {
        if (isCorrect) {
            progressScores.add(1);
        } else {
            progressScores.add(0);
        }
    }


    // METHOD: getStatistics
    public String getStatistics() 
    {
        double avgScore = getAverageScore();

        int totalLessons = progressScores.size();

        String statisticsReport =
            "Student: " + name +
            ", Level: " + level +
            ", Average Score: " + Math.round(avgScore) + "%" + ", Total Lessons: " + totalLessons;

        return statisticsReport;
    }


    // METHOD: getlevel
    public int getlevel() {
        return level;
    }

    // METHOD: getProgressScore
    public ArrayList<Integer> getProgressScore() {
        return progressScores;
    }

    // METHOD: getAverageScore
    public double getAverageScore() 
    {
        if (progressScores.isEmpty()) {
            return 0.0;
        }

        int total = 0;
        for (int score : progressScores) {
            total += score;
        }

        return ((double) total / progressScores.size()) * 100;
    }
}
