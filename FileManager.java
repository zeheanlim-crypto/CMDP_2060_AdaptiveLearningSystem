import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileManager
{
    private static final String STUDENT_DIR = "students/";
    private static final String LESSON_DIR = "lessons/";
    private static final String REPORT_DIR = "reports/";

    static
    {
        createDirectory ( STUDENT_DIR );
        createDirectory ( LESSON_DIR );
        createDirectory ( REPORT_DIR );
    }

    private static void createDirectory ( String path )
    {
        File dir = new File ( path );
        if ( !dir.exists () )
        {
            dir.mkdirs ();
        }
    }


    // Save student
    public static void saveStudent( Student student )
    {
        String fileName = "STU_" + student.getStudentId() + ".txt";
        String filePath = STUDENT_DIR + fileName;

        try ( BufferedWriter writer = new BufferedWriter(new FileWriter( filePath ) ) )
        {
            writer.write( fileName );
            writer.newLine();

            writer.write( String.valueOf( student.getStudentId() ) );
            writer.newLine();

            writer.write( student.getName() );
            writer.newLine();

            writer.write( String.valueOf( student.getAge() ) );
            writer.newLine();

            writer.write( String.valueOf( student.getlevel() ) );
            writer.newLine();

            // Write all progress scores line by line
            for ( int score : student.getProgressScore() )
            {
                writer.write( String.valueOf( score ) );
                writer.newLine();
            }

            System.out.println( "Student profile saved successfully." );
        }
        catch ( IOException e )
        {
            e.printStackTrace();
        }
    }


    // Load student
    public static Student loadStudent ( String studentId )
    {
        String filePath = STUDENT_DIR + "STU_" + studentId + ".txt";

        try ( BufferedReader reader = new BufferedReader ( new FileReader ( filePath ) ) )
        {
            reader.readLine (); // This is to skip file name

            int id = Integer.parseInt ( reader.readLine () );
            String name = reader.readLine ();
            int age = Integer.parseInt ( reader.readLine () );
            int level = Integer.parseInt ( reader.readLine () );

            ArrayList<Integer> progressScores = new ArrayList<> ();
            String line;

            while ( ( line = reader.readLine () ) != null )
            {
                progressScores.add ( Integer.parseInt ( line ) );
            }

            return new Student ( id, name, age, level, progressScores );
        }
        catch ( IOException e )
        {
            System.out.println ( "Failed to load student: " + studentId );
            return null;
        }
    }


    // List all students
    public static String[] listStudents ()
    {
        File folder = new File ( STUDENT_DIR );
        File[] files = folder.listFiles ();

        if ( files == null || files.length == 0 )
        {
            return new String[0];
        }

        List<String> students = new ArrayList<> ();
        int count = 1;

        for ( File file : files )
        {
            if ( file.isFile () && file.getName ().startsWith ( "STU_" ) )
            {
                String studentId = file.getName ()
                        .replace ( "STU_", "" )
                        .replace ( ".txt", "" );

                System.out.println ( count + ". Student ID: " + studentId );
                count++;

                students.add ( studentId );
            }
        }

        return students.toArray ( new String[0] );
    }


    // Save and export reports
    public static void exportReport ( Student student ) 
    {
        String studentId = String.valueOf ( student.getStudentId () );
        String studentName = student.getName ();

        File studentFile = new File ( STUDENT_DIR + "STU_" + studentId + ".txt" );

        if ( !studentFile.exists () )
        {
            System.out.println ( "Student file not found." );
            return;
        }

        String reportFilePath = REPORT_DIR + studentId + "_report.txt";

        try ( BufferedWriter writer = new BufferedWriter ( new FileWriter ( reportFilePath ) ) )
        {
            writer.write ( "Progress Report" );
            writer.newLine ();

            writer.write ( "Student Name: " + studentName );
            writer.newLine ();

            writer.write ( "Student ID: " + studentId );
            writer.newLine ();

            writer.write ( "Level: " + student.getlevel () );
            writer.newLine ();

            double avg = student.getAverageScore ();
            int avgPercent = ( int ) Math.round ( avg );

            writer.write ( "Average Score: " + avgPercent + "%" );
            writer.newLine ();

            int totalLessons = student.getProgressScore ().size ();

            writer.write ( "Total Lessons Completed: " + totalLessons );
            writer.newLine ();
        }
        catch ( IOException e )
        {
            e.printStackTrace ();
        }

        System.out.println ( "Report created for student ID: " + studentId );
    }


    // Load Pattern Matching Lessons
    public static List<PatternMatchingLesson> loadPatternLessons ()
    {
        List<PatternMatchingLesson> lessons = new ArrayList<> ();
        String filePath = LESSON_DIR + "pattern_lessons.txt";

        try ( BufferedReader reader = new BufferedReader ( new FileReader ( filePath ) ) )
        {
            String line;

            while ( ( line = reader.readLine () ) != null )
            {
                if ( line.trim ().isEmpty () || line.startsWith ( "#" ) )
                {
                    continue;
                }

                String[] parts = line.split ( "\\|" );

                if ( parts.length != 4 )
                {
                    continue;
                }

                String lessonId = parts[0];
                String title = parts[1];
                int difficulty = Integer.parseInt ( parts[2] );
                String[] patterns = parts[3].split ( "," );

                if ( !lessonId.startsWith ( "PM" ) || difficulty < 1 || difficulty > 3 )
                {
                    continue;
                }

                lessons.add (
                        new PatternMatchingLesson ( lessonId, title, difficulty, patterns )
                );
            }
        }
        catch ( IOException e )
        {
            System.out.println ( "Pattern lessons file does not exist." );
        }

        return lessons;
    }


    /*
    -------Still waiting for Lim's code-------

    public static ArrayList<WordBuildingLesson> loadWordLessons ( String filename )
    {
        ArrayList<WordBuildingLesson> lessons = new ArrayList<> ();
        return lessons;
    }
    */
}