import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileManager {
    public static void main( String[] args )
    {
        // Gets variables from lesson.java
        saveStudent("700052260", "Kelven Tan", "Completed Pattern Matching 1");
        loadStudent("700052260", "Kelven Tan", "Completed Pattern Matching 1", "test");
    }  

    // Save Student method
    public static String[] saveStudent(String studentID, String studentDetails, String progressRecords)
    {
        String studentFileName = studentID + "_student.txt";
        String contents = studentID + "\n" + studentDetails + "\n" + progressRecords;
        File file = new File(studentFileName);
        
        if ( file.exists() )
        {
            System.out.println( "File exists." );

            try ( FileWriter write = new FileWriter(file) )
            {
                write.write( contents );
            } catch ( IOException e )
            {
                e.printStackTrace();
            }
        } else 
        {
            System.out.println( "File doesn't exist, creating file..." );

            // Create File
            try ( FileWriter writer = new FileWriter( file ) ) 
            {
                writer.write( contents );
            } catch ( IOException e ) 
            {
                e.printStackTrace();
            }
        }
        System.out.println("Saved student " + studentID + " to " + studentID + "_student.txt");
        


        return new String[] {studentFileName, contents};
    }

    // Load Student method
    public static String loadStudent(String studentID, String studentDetails, String progressRecords, String contents)
    {
        String studentFileName = studentID + "_student.txt";
        String studentProfile = contents;

        try ( BufferedReader reader = new BufferedReader(new FileReader(studentFileName)) )
        {
            String line;

            while ( ( line = reader.readLine()) != null )
            {
                System.out.println(line);
            }
        } catch ( IOException e )
        {
            e.printStackTrace();
        }

        System.out.println(studentProfile);
        return studentProfile;
    }
}