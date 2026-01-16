public class PatternMatchingLesson
{
    private String lessonID;
    private String title;
    private int difficulty;
    private String[] pattern;


    public PatternMatchingLesson ( String lessonID, String title, int difficulty, String[] patterns )
    {
        this.lessonID = lessonID;
        this.title = title;
        this.difficulty = difficulty;
        this.pattern = patterns;
    }


    public boolean evaluateAnswer ( String userAnswer )
    {
        String correctPattern = String.join ( ",", pattern );
        return userAnswer.equals ( correctPattern );
    }


    public String getHint ()
    {
        if ( pattern.length > 0 )
        {
            return "The pattern starts with: " + pattern[0];
        }
        return "No pattern available.";
    }


    public void displayLesson ()
    {
        System.out.println ( "Lesson ID: " + lessonID );
        System.out.println ( "Title: " + title );
        System.out.println ( "Difficulty: " + difficulty );
        System.out.println ( "Pattern length: " + pattern.length );
    }


    public String[] getPattern ()
    {
        return pattern.clone ();
    }


    public String getlessonID ()
    {
        return lessonID;
    }


    public String getTitle ()
    {
        return title;
    }

    
    public int getdifficulty ()
    {
        return difficulty;
    }
}
