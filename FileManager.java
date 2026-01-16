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


    // Save Student method
    public static void saveStudent ( String studentName, String studentID, int age, int scores ) 
    {
        String fileName = "STU_" + studentName + ".txt";
        String filePath = STUDENT_DIR + fileName;

        try ( BufferedWriter writer = new BufferedWriter ( new FileWriter ( filePath ) ) )
        {
            writer.write ( fileName );
            writer.newLine ();

            writer.write ( studentID );
            writer.newLine ();

            writer.write ( studentName );
            writer.newLine ();

            // BufferedWriter writes the ASCII character, use .valueOf to turn into string
            writer.write ( String.valueOf ( age ) );
            writer.newLine ();

            writer.write ( String.valueOf ( scores ) );
            writer.newLine ();

            System.out.println ( "Student profile saved successfully." );

        } catch ( IOException e ) 
        {
            System.out.println ( "Error saving student profile." );
            e.printStackTrace ();
        }
    }


    // Load student file and return contents as String
    public static String loadStudent( String studentName ) 
    {
        String filePath = STUDENT_DIR + "STU_" + studentName + ".txt";
        StringBuilder studentProfile = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) 
        {
            String line;

            while ((line = reader.readLine()) != null) 
            {
                studentProfile.append(line).append(System.lineSeparator());
            }

            return studentProfile.toString();

        } catch (IOException e) 
        {
            System.out.println("Error loading student profile.");
            e.printStackTrace();
            return "";
        }
    }



    // List out all the students registered
    public static String[] listStudents ()
    {
        File studentFolder = new File( STUDENT_DIR );
        File[] files = studentFolder.listFiles();
        List<String> studentList = new ArrayList<>();

        if (files == null || files.length == 0)
        {
            return new String[0];
        }

        int count = 1;

        for (File file : files)
        {
            if ( file.isFile() && file.getName().startsWith( "STU_" ) )
            {
                String fileName = file.getName();

                String studentName = fileName
                        .replace("STU_", "")
                        .replace(".txt", "");

                System.out.println(count + ". " + studentName);
                count++;

                studentList.add(studentName);
            }
        }

        return studentList.toArray(new String[0]);
    }


    // Make a student report
    public static void exportReport ( String studentID, String studentName, int totalLessons, double averageScore, int level )
    {
        // Search for the student file in STUDENT_DIR first
        String studentFileName = "STU_" + studentName + ".txt";
        File studentFile = new File ( STUDENT_DIR + studentFileName );

        if ( !studentFile.exists ( ) )
        {
            System.out.println ( "Student file not found: " + studentFileName );
            return;
        }

        // Starts creating report
        String reportFileName = studentID + "_report.txt";
        File reportFile = new File ( REPORT_DIR + reportFileName );

        try ( BufferedWriter writer = new BufferedWriter ( new FileWriter ( reportFile ) ) )
        {
            writer.write ( "Progress Report" );
            writer.newLine ( );

            writer.write ( "Student: " + studentName );
            writer.newLine ( );

            writer.write ( "Level: " + level );
            writer.newLine ( );

            writer.write ( "Average Score: " + averageScore );
            writer.newLine ( );

            writer.write ( "Total Lessons: " + totalLessons );
            writer.newLine ( );
        }
        catch ( IOException e )
        {
            e.printStackTrace ( );
            return;
        }

        System.out.println ( "Created report for: " + reportFileName );
    }


    // Load pattern_lessons.txt
    public static List<PatternMatchingLesson> loadPatternLessons ()
    {
        List<PatternMatchingLesson> lessons = new ArrayList<> ();
        String filePath = LESSON_DIR + "pattern_lessons.txt";

        try ( BufferedReader reader = new BufferedReader ( new FileReader ( filePath ) ) )
        {
            String line;

            while ( ( line = reader.readLine () ) != null )
            {
                // Skip comments and empty lines
                if ( line.startsWith ( "#" ) || line.trim ().isEmpty () )
                {
                    continue;
                }

                // Split lesson data
                String[] parts = line.split ( "\\|" );

                if ( parts.length != 4 )
                {
                    System.out.println ( "Invalid lesson format: " + line );
                    continue;
                }

                String lessonID = parts[0];
                String title = parts[1];

                int difficulty;
                try
                {
                    difficulty = Integer.parseInt ( parts[2] );
                }
                catch ( NumberFormatException e )
                {
                    System.out.println ( "Invalid difficulty: " + parts[2] );
                    continue;
                }

                String[] patterns = parts[3].split ( "," );

                // Basic validation
                if ( !lessonID.startsWith ( "PM" ) || difficulty < 1 || difficulty > 3 )
                {
                    System.out.println ( "Invalid lesson data: " + line );
                    continue;
                }

                if ( patterns.length < 3 )
                {
                    System.out.println ( "Not enough patterns: " + line );
                    continue;
                }

                // Create lesson object
                PatternMatchingLesson lesson = new PatternMatchingLesson ( lessonID, title, difficulty, patterns );

                lessons.add ( lesson );
            }
        }
        catch ( IOException e )
        {
            System.out.println ( "Error loading pattern lessons." );
            e.printStackTrace ();
        }

        // Holy shit I actually did it wtf
        return lessons;
    }


    // Save report into the reports directory
    public static void saveReport ( String studentId, String reportContent ) 
    {
        String fileName = REPORT_DIR + studentId + "_report.txt";

        try ( FileWriter writer = new FileWriter ( fileName ) ) 
        {
            writer.write ( reportContent );
        } catch ( IOException e ) 
        {
            System.out.println ( "Error saving report." );
            e.printStackTrace ();
        }
    }
}

