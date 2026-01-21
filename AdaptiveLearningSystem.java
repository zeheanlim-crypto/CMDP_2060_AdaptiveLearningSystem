import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ArrayList;

public class AdaptiveLearningSystem
{
    private static JFrame mainFrame;
    private static List<PatternMatchingLesson> patternMatchingLessons;
    private static PatternMatchingLesson currentPatternLesson;
    private static Student currentStudent;
    
    
    public static void main(String[] args)
    {
        showStudentRegistration();
    }
    
    
    private static void showStudentRegistration()
    {
        mainFrame = new JFrame("Student Registration");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(500, 400);
        mainFrame.getContentPane().setBackground(Color.LIGHT_GRAY);
        mainFrame.setLocationRelativeTo(null);
        
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(Color.LIGHT_GRAY);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        
        JLabel titleLabel = new JLabel("Create Student Profile", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.DARK_GRAY);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBackground(Color.LIGHT_GRAY);
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        
        JPanel idPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        idPanel.setBackground(Color.LIGHT_GRAY);
        JLabel idLabel = new JLabel("Student ID:");
        idLabel.setFont(new Font("Arial", Font.BOLD, 14));
        JTextField idField = new JTextField(15);
        idField.setFont(new Font("Arial", Font.PLAIN, 14));
        idPanel.add(idLabel);
        idPanel.add(idField);
        
        JPanel namePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        namePanel.setBackground(Color.LIGHT_GRAY);
        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setFont(new Font("Arial", Font.BOLD, 14));
        JTextField nameField = new JTextField(15);
        nameField.setFont(new Font("Arial", Font.PLAIN, 14));
        namePanel.add(nameLabel);
        namePanel.add(nameField);
        
        JPanel agePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        agePanel.setBackground(Color.LIGHT_GRAY);
        JLabel ageLabel = new JLabel("Age:");
        ageLabel.setFont(new Font("Arial", Font.BOLD, 14));
        JTextField ageField = new JTextField(15);
        ageField.setFont(new Font("Arial", Font.PLAIN, 14));
        agePanel.add(ageLabel);
        agePanel.add(ageField);
        
        JLabel errorLabel = new JLabel("", SwingConstants.CENTER);
        errorLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        errorLabel.setForeground(Color.RED);
        errorLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        formPanel.add(idPanel);
        formPanel.add(Box.createVerticalStrut(15));
        formPanel.add(namePanel);
        formPanel.add(Box.createVerticalStrut(15));
        formPanel.add(agePanel);
        formPanel.add(Box.createVerticalStrut(15));
        formPanel.add(errorLabel);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        buttonPanel.setBackground(Color.LIGHT_GRAY);
        
        JButton saveButton = new JButton("Save Student");
        saveButton.setFont(new Font("Arial", Font.BOLD, 16));
        saveButton.setPreferredSize(new Dimension(150, 40));
        saveButton.setBackground(new Color(70, 130, 180));
        saveButton.setForeground(Color.WHITE);
        saveButton.setFocusPainted(false);
        
        JButton cancelButton = new JButton("Cancel");
        cancelButton.setFont(new Font("Arial", Font.PLAIN, 14));
        cancelButton.setPreferredSize(new Dimension(150, 40));
        
        saveButton.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseEntered(java.awt.event.MouseEvent mouseEvent)
            {
                saveButton.setBackground(new Color(50, 110, 160));
            }
            
            public void mouseExited(java.awt.event.MouseEvent mouseEvent)
            {
                saveButton.setBackground(new Color(70, 130, 180));
            }
        });
        
        saveButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent actionEvent)
            {
                String idText = idField.getText().trim();
                String name = nameField.getText().trim();
                String ageText = ageField.getText().trim();
                
                if (idText.isEmpty() || name.isEmpty() || ageText.isEmpty())
                {
                    errorLabel.setText("Please fill in all fields.");
                    return;
                }
                
                try
                {
                    int studentId = Integer.parseInt(idText);
                    int age = Integer.parseInt(ageText);
                    
                    if (age <= 0 || age > 120)
                    {
                        errorLabel.setText("Please enter a valid age (1-120).");
                        return;
                    }
                    
                    Student existingStudent = FileManager.loadStudent(idText);
                    if (existingStudent != null)
                    {
                        int option = JOptionPane.showConfirmDialog(
                            mainFrame,
                            "Student ID already exists. Load existing profile?",
                            "Existing Student",
                            JOptionPane.YES_NO_OPTION
                        );
                        
                        if (option == JOptionPane.YES_OPTION)
                        {
                            currentStudent = existingStudent;
                            mainFrame.dispose();
                            createMainDashboard();
                            return;
                        }
                        else
                        {
                            errorLabel.setText("Please use a different Student ID.");
                            return;
                        }
                    }
                    
                    currentStudent = new Student(studentId, name, age, 1, new ArrayList<Integer>(), 0);
                    
                    FileManager.saveStudent(currentStudent);
                    
                    JOptionPane.showMessageDialog(
                        mainFrame,
                        "Student profile created successfully!\nName: " + name + "\nID: " + studentId,
                        "Registration Successful",
                        JOptionPane.INFORMATION_MESSAGE
                    );
                    
                    mainFrame.dispose();
                    createMainDashboard();
                }
                catch (NumberFormatException exception)
                {
                    errorLabel.setText("Student ID and Age must be numbers.");
                }
            }
        });
        
        cancelButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent actionEvent)
            {
                int option = JOptionPane.showConfirmDialog(
                    mainFrame,
                    "Exit application?",
                    "Confirm Exit",
                    JOptionPane.YES_NO_OPTION
                );
                
                if (option == JOptionPane.YES_OPTION)
                {
                    System.exit(0);
                }
            }
        });
        
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        
        mainPanel.add(titleLabel);
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(formPanel);
        mainPanel.add(Box.createVerticalGlue());
        mainPanel.add(buttonPanel);
        
        mainFrame.add(mainPanel);
        mainFrame.setVisible(true);
    }
    
    
    public static void createMainDashboard()
    {
        patternMatchingLessons = FileManager.loadPatternLessons();
        
        if (patternMatchingLessons == null || patternMatchingLessons.isEmpty())
        {
            JOptionPane.showMessageDialog( 
                null, 
                "No pattern matching lessons found. Please check the lessons directory.", 
                "Lesson Error", 
                JOptionPane.WARNING_MESSAGE 
            );
        }
        
        if (mainFrame != null)
        {
            mainFrame.dispose();
        }
        
        mainFrame = new JFrame("Adaptive Learning System");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(900, 700);
        mainFrame.getContentPane().setBackground(Color.LIGHT_GRAY);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setLayout(new BorderLayout());
        
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.LIGHT_GRAY);
        
        JLabel titleLabel = new JLabel("Adaptive Learning System", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(Color.DARK_GRAY);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        
        if (currentStudent != null)
        {
            JLabel welcomeLabel = new JLabel(
                "Welcome, " + currentStudent.getName() + "! (Level: " + currentStudent.getlevel() + ")",
                SwingConstants.CENTER
            );
            welcomeLabel.setFont(new Font("Arial", Font.ITALIC, 16));
            welcomeLabel.setForeground(new Color(70, 130, 180));
            welcomeLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
            topPanel.add(welcomeLabel, BorderLayout.SOUTH);
        }
        
        topPanel.add(titleLabel, BorderLayout.CENTER);
        
        JPanel mainButtonPanel = new JPanel(new GridLayout(2, 2, 30, 30));
        mainButtonPanel.setBackground(Color.LIGHT_GRAY);
        mainButtonPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
        
        JButton patternMatchingButton = new JButton("Pattern Matching");
        JButton wordBuildingButton = new JButton("Word Building");
        JButton viewProgressButton = new JButton("View Progress");
        JButton exportReportButton = new JButton("Export Report");
        
        Font buttonFont = new Font("Arial", Font.BOLD, 18);
        Color buttonBackgroundColor = new Color(240, 240, 240);
        Color buttonBorderColor = new Color(70, 130, 180);
        
        JButton[] applicationButtons = { patternMatchingButton, wordBuildingButton, viewProgressButton, exportReportButton };
        
        for (JButton currentButton : applicationButtons)
        {
            currentButton.setFont(buttonFont);
            currentButton.setForeground(Color.DARK_GRAY);
            currentButton.setBackground(buttonBackgroundColor);
            currentButton.setFocusPainted(false);
            
            currentButton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(buttonBorderColor, 2),
                BorderFactory.createEmptyBorder(80, 40, 80, 40)
            ));
            
            currentButton.addMouseListener(new java.awt.event.MouseAdapter()
            {
                public void mouseEntered(java.awt.event.MouseEvent mouseEvent)
                {
                    currentButton.setBackground(new Color(220, 220, 220));
                }
                
                public void mouseExited(java.awt.event.MouseEvent mouseEvent)
                {
                    currentButton.setBackground(buttonBackgroundColor);
                }
            });
        }
        
        patternMatchingButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent actionEvent)
            {
                if (patternMatchingLessons == null || patternMatchingLessons.isEmpty())
                {
                    JOptionPane.showMessageDialog( 
                        mainFrame, 
                        "No pattern matching lessons available. Please check the lessons file.", 
                        "No Lessons", 
                        JOptionPane.WARNING_MESSAGE 
                    );
                    return;
                }
                
                selectPatternMatchingLesson();
                createPatternMatchingWindow();
            }
        });
        
        wordBuildingButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent actionEvent)
            {
                JOptionPane.showMessageDialog(mainFrame, "Word Building feature in progress, Lim");
            }
        });
        
        viewProgressButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent actionEvent)
            {
                if (currentStudent != null)
                {
                    String statistics = currentStudent.getStatistics();
                    JOptionPane.showMessageDialog(
                        mainFrame,
                        statistics,
                        "Progress Report",
                        JOptionPane.INFORMATION_MESSAGE
                    );
                }
                else
                {
                    JOptionPane.showMessageDialog(mainFrame, "No student profile loaded.");
                }
            }
        });
        
        exportReportButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent actionEvent)
            {
                if (currentStudent != null)
                {
                    FileManager.exportReport(currentStudent);
                    JOptionPane.showMessageDialog(
                        mainFrame,
                        "Report exported to reports/ directory!",
                        "Export Successful",
                        JOptionPane.INFORMATION_MESSAGE
                    );
                }
                else
                {
                    JOptionPane.showMessageDialog(mainFrame, "No student profile to export.");
                }
            }
        });
        
        mainButtonPanel.add(patternMatchingButton);
        mainButtonPanel.add(wordBuildingButton);
        mainButtonPanel.add(viewProgressButton);
        mainButtonPanel.add(exportReportButton);
        
        mainFrame.add(topPanel, BorderLayout.NORTH);
        mainFrame.add(mainButtonPanel, BorderLayout.CENTER);
        
        mainFrame.setVisible(true);
    }
    
    
    private static void selectPatternMatchingLesson()
    {
        if (patternMatchingLessons == null || patternMatchingLessons.isEmpty())
        {
            currentPatternLesson = null;
            return;
        }
        
        currentPatternLesson = patternMatchingLessons.get(0);
    }
    
    
    private static void createPatternMatchingWindow()
    {
        if (currentPatternLesson == null)
        {
            JOptionPane.showMessageDialog(mainFrame, "Error loading lesson.");
            return;
        }
        
        JDialog patternMatchingDialog = new JDialog(mainFrame, "Pattern Matching", true);
        patternMatchingDialog.setSize(500, 450);
        patternMatchingDialog.setLocationRelativeTo(mainFrame);
        patternMatchingDialog.setLayout(new BorderLayout());
        
        JPanel mainContentPanel = new JPanel();
        mainContentPanel.setLayout(new BoxLayout(mainContentPanel, BoxLayout.Y_AXIS));
        mainContentPanel.setBackground(new Color(250, 250, 250));
        mainContentPanel.setBorder(BorderFactory.createEmptyBorder(30, 20, 20, 20));
        
        JLabel instructionLabel = new JLabel("Memorize this pattern:", SwingConstants.CENTER);
        instructionLabel.setFont(new Font("Arial", Font.BOLD, 18));
        instructionLabel.setForeground(Color.DARK_GRAY);
        instructionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        instructionLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));
        
        JPanel patternPanel = new JPanel();
        patternPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 0));
        patternPanel.setBackground(new Color(250, 250, 250));
        patternPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        patternPanel.setMaximumSize(new Dimension(450, 150));
        
        String[] pattern = currentPatternLesson.getPattern();
        
        for (String shape : pattern)
        {
            JLabel shapeLabel = createShapeLabel(shape);
            patternPanel.add(shapeLabel);
        }
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BorderLayout());
        buttonPanel.setBackground(new Color(250, 250, 250));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(100, 20, 50, 20));
        
        JButton readyButton = new JButton("I'm Ready!");
        readyButton.setFont(new Font("Arial", Font.BOLD, 16));
        readyButton.setPreferredSize(new Dimension(0, 40));
        readyButton.setBackground(new Color(70, 130, 180));
        readyButton.setForeground(Color.WHITE);
        readyButton.setFocusPainted(false);
        
        readyButton.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseEntered(java.awt.event.MouseEvent mouseEvent)
            {
                readyButton.setBackground(new Color(50, 110, 160));
            }
            
            public void mouseExited(java.awt.event.MouseEvent mouseEvent)
            {
                readyButton.setBackground(new Color(70, 130, 180));
            }
        });
        
        readyButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent actionEvent)
            {
                patternMatchingDialog.dispose();
                showPatternMatchingTest();
            }
        });
        
        buttonPanel.add(readyButton, BorderLayout.CENTER);
        
        mainContentPanel.add(instructionLabel);
        mainContentPanel.add(patternPanel);
        mainContentPanel.add(Box.createVerticalGlue());
        mainContentPanel.add(buttonPanel);
        
        patternMatchingDialog.add(mainContentPanel, BorderLayout.CENTER);
        patternMatchingDialog.setVisible(true);
    }
    
    
    private static JLabel createShapeLabel(String shape)
    {
        JLabel shapeLabel = new JLabel(shape, SwingConstants.CENTER);
        shapeLabel.setFont(new Font("Arial", Font.BOLD, 18));
        shapeLabel.setPreferredSize(new Dimension(100, 80));
        shapeLabel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.BLACK, 1),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        shapeLabel.setOpaque(true);
        shapeLabel.setBackground(Color.WHITE);
        shapeLabel.setForeground(Color.DARK_GRAY);
        
        return shapeLabel;
    }
    
    
    private static void showPatternMatchingTest()
    {
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
        inputPanel.setBackground(new Color(250, 250, 250));
        
        JLabel validationLabel = new JLabel("Please enter the pattern sequence:");
        validationLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        validationLabel.setForeground(Color.RED);
        validationLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        validationLabel.setVisible(false);
        
        JLabel instructionLabel = new JLabel("Recreate the pattern (e.g., Circle, Square, Circle):");
        instructionLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        instructionLabel.setForeground(Color.DARK_GRAY);
        instructionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        instructionLabel.setBorder(BorderFactory.createEmptyBorder(5, 0, 10, 0));
        
        JTextField answerField = new JTextField();
        answerField.setFont(new Font("Arial", Font.PLAIN, 14));
        answerField.setMaximumSize(new Dimension(400, 30));
        answerField.setAlignmentX(Component.CENTER_ALIGNMENT);
        answerField.setBackground(Color.WHITE);
        
        inputPanel.add(validationLabel);
        inputPanel.add(instructionLabel);
        inputPanel.add(answerField);
        
        boolean validInput = false;
        
        while (!validInput)
        {
            int result = JOptionPane.showConfirmDialog(
                mainFrame,
                inputPanel,
                "Pattern Matching Lesson",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
            );
            
            if (result != JOptionPane.OK_OPTION)
            {
                return;
            }
            
            String userAnswer = answerField.getText().trim();
            
            if (userAnswer.isEmpty())
            {
                validationLabel.setVisible(true);
                answerField.setText("");
                inputPanel.revalidate();
                inputPanel.repaint();
            }
            else
            {
                validInput = true;
                int score = currentPatternLesson.evaluateAnswer(userAnswer);
                
                if (currentStudent != null)
                {
                    currentStudent.updateProgress(score == 1);
                    FileManager.saveStudent(currentStudent);
                }
                
                if (score == 1)
                {
                    JOptionPane.showMessageDialog( 
                        mainFrame, 
                        "Correct!", 
                        "Result", 
                        JOptionPane.INFORMATION_MESSAGE 
                    );
                }
                else
                {
                    JOptionPane.showMessageDialog( 
                        mainFrame, 
                        "Please try again, you are doing great!", 
                        "Result", 
                        JOptionPane.WARNING_MESSAGE 
                    );
                }
            }
        }
    }
}