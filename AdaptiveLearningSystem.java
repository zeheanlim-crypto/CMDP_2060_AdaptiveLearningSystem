public class AdaptiveLearningSystem 
{
    public static void main ( String[] args ) 
    {
        // Create a student
        Student s = new Student(700052260, "kelven", 21, 1, new java.util.ArrayList<>());

        // Save student
        FileManager.saveStudent(s);

        /*// List students
        System.out.println("Students:");
        String[] students = FileManager.listStudents();
        for (String name : students) {
            System.out.println(name);
        }*/


        // Load student
        Student loaded = FileManager.loadStudent("700052260");
        if (loaded != null) {
            System.out.println("Loaded student successfully");
            System.out.println(loaded.getStatistics());

            // Simulate answering questions
            loaded.updateProgress(true);
            loaded.updateProgress(true);  
            loaded.updateProgress(false);
            loaded.updateProgress(true);
            loaded.updateProgress(true);  
            loaded.updateProgress(false);
            loaded.updateProgress(true);
            loaded.updateProgress(true);  
            loaded.updateProgress(false);
            loaded.updateProgress(true);



            System.out.println("After answering questions:");
            System.out.println(loaded.getStatistics());

            // Save updated student
            FileManager.saveStudent(loaded);
        }

        FileManager.exportReport(loaded);
    }
}