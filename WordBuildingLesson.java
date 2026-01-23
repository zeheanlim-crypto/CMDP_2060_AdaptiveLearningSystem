import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class WordBuildingLesson extends Lesson {
    private String targetWord;
    private String[] scrambledLetters;

    public WordBuildingLesson(Integer lessonId, String title, Integer difficultyLevel, String targetWordFilePath) {
        super(lessonId, title, difficultyLevel);

        this.targetWord = null;
        this.scrambledLetters = new String[0];

        if (targetWordFilePath == null || targetWordFilePath.isEmpty()) {
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(targetWordFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String trimmed = line.trim();
                if (!trimmed.isEmpty()) {
                    this.targetWord = trimmed;
                    break;
                }
            }
        } catch (IOException e) {
            this.targetWord = null;
        }
    }

    private static String normalizeLetters(String s) {
        if (s == null) return "";
        return s.replaceAll("\\s+", "").toLowerCase();
    }

    public boolean evaluateAnswer(String userAnswer) {
        if (userAnswer == null || userAnswer.trim().isEmpty()) {
            return false;
        }

        String target = getTargetWord();
        if (target == null || target.trim().isEmpty()) {
            return false;
        }

        String normalizedAnswer = normalizeLetters(userAnswer);
        String normalizedTarget = normalizeLetters(target);

        if (normalizedAnswer.equals(normalizedTarget)) {
            return true;
        }

        if (normalizedAnswer.length() != normalizedTarget.length()) {
            return false;
        }

        Map<Character, Integer> answerCounts = new HashMap<>();
        Map<Character, Integer> targetCounts = new HashMap<>();

        for (char c : normalizedAnswer.toCharArray()) {
            answerCounts.put(c, answerCounts.getOrDefault(c, 0) + 1);
        }

        for (char c : normalizedTarget.toCharArray()) {
            targetCounts.put(c, targetCounts.getOrDefault(c, 0) + 1);
        }

        return answerCounts.equals(targetCounts);
    }

    public String[] scrambleLetters() {
        if (this.targetWord == null || this.targetWord.isEmpty()) {
            this.scrambledLetters = new String[0];
            return this.scrambledLetters;
        }

        int n = this.targetWord.length();
        String[] chars = new String[n];
        for (int i = 0; i < n; i++) {
            chars[i] = String.valueOf(this.targetWord.charAt(i));
        }

        for (int attempt = 0; attempt < 10; attempt++) {
            String[] shuffled = Arrays.copyOf(chars, n);

            for (int i = n - 1; i > 0; i--) {
                int j = (int) (Math.random() * (i + 1));
                String temp = shuffled[i];
                shuffled[i] = shuffled[j];
                shuffled[j] = temp;
            }

            if (!String.join("", shuffled).equalsIgnoreCase(this.targetWord)) {
                this.scrambledLetters = shuffled;
                return this.scrambledLetters;
            }
        }

        // Fallback: rotate letters
        if (n > 1) {
            String[] rotated = new String[n];
            for (int i = 0; i < n; i++) {
                rotated[i] = chars[(i + 1) % n];
            }
            this.scrambledLetters = rotated;
        } else {
            this.scrambledLetters = chars;
        }

        return this.scrambledLetters;
    }

    public String[] getScrambledLetters() {
        if (this.scrambledLetters == null || this.scrambledLetters.length == 0) {
            scrambleLetters();
        }
        return Arrays.copyOf(this.scrambledLetters, this.scrambledLetters.length);
    }

    public String getTargetWord() {
        return this.targetWord;
    }

    public String getHint() {
        if (this.targetWord == null || this.targetWord.isEmpty()) {
            return " ";
        }

        int len = this.targetWord.length();
        StringBuilder hidden = new StringBuilder();
        hidden.append(this.targetWord.charAt(0));

        for (int i = 1; i < len; i++) {
            hidden.append('_');
        }

        String extraHint = "";
        if (len > 2) {
            int randomIndex = 1 + new Random().nextInt(len - 1);
            extraHint = " Also, the letter in position " + (randomIndex + 1)
                      + " is '" + this.targetWord.charAt(randomIndex) + "'.";
        }

        return "Hint: " + hidden + " (Length: " + len + ")" + extraHint;
    }
}

