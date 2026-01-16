import java.util.List;

public class AdaptiveLearningSystem 
{
    public static void main ( String[] args ) 
    {
        String studentName = "Kelven";
        String studentID = "700052260";
        int totalLessons = 5;
        int age = 20;
        int scores = 90;
        int level = 2;
        double averageScore = 33.33;

        String[] students = FileManager.listStudents();
        System.out.println(students);


        /*
        FileManager.saveStudent ( studentName, studentID, age, scores );
        FileManager.loadStudent ( studentName );
        FileManager.listStudents();
        FileManager.exportReport ( studentID, studentName, totalLessons, averageScore, level );
        FileManager.loadPatternLessons();


        List<PatternMatchingLesson> lessons = FileManager.loadPatternLessons ();

        System.out.println ( "Total lessons loaded: " + lessons.size () );

        for (PatternMatchingLesson lesson : lessons)
        {
            lesson.displayLesson();
            System.out.println();
        }

        
        String studentProfile = FileManager.loadStudent("Kelven");
        System.out.println(studentProfile);


        */
    }
}