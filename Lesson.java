abstract class Lesson{
    private int lessonId;
    private String title;
    private int difficultyLevel;
    private boolean completionStatus = false;

    //public Lesson()

    public abstract int evaluateAnswer(String userAnswer);
    public abstract String getHint();
    public abstract void displayLesson();

    public boolean markCompleted(int score){
        if (score <= 60) {
            completionStatus = true;
        } 
        return markCompleted(score);
    }

    public String getTitle(){
        return title;
    }

    public int getdifficultyLevel(){
        return difficultyLevel;
    }

    public int getLessonId(){
        return lessonId;
    }

    public boolean isCompleted(){
        return completionStatus;
    }
}
