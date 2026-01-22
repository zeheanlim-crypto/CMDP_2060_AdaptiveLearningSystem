import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

public class WordBuildingLessonTwo extends LessonClass {
    private String targetWord;
    private String[] scrambledLetters;

    public WordBuildingLessonTwo(Integer lessonId, String title, Integer difficultyLevel, String targetWordFilePath) {
        super(lessonId, title, difficultyLevel);

        this.targetWord = null;
        this.scrambledLetters = new String[0];

        if (targetWordFilePath == null || targetWordFilePath.isEmpty()) {
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(targetWordFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String trimmed = (line == null) ? "" : line.trim();
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
        if (userAnswer == null) {
            return false;
        }

        String answerTrimmedRaw = userAnswer.trim();
        if (answerTrimmedRaw.isEmpty()) {
            return false;
        }

        String target = getTargetWord();
        if (target == null || target.trim().isEmpty()) {
            return false;
        }

        String normalizedAnswer = normalizeLetters(answerTrimmedRaw);
        String normalizedTarget = normalizeLetters(target);

        if (normalizedAnswer.equals(normalizedTarget)) {
            return true;
        }

        if (normalizedAnswer.length() != normalizedTarget.length()) {
            return false;
        }

        java.util.Map<Character, Integer> answerCounts = new java.util.HashMap<>();
        java.util.Map<Character, Integer> targetCounts = new java.util.HashMap<>();

        for (int i = 0; i < normalizedAnswer.length(); i++) {
            char aChar = normalizedAnswer.charAt(i);
            answerCounts.put(aChar, answerCounts.getOrDefault(aChar, 0) + 1);
        }

        for (int i = 0; i < normalizedTarget.length(); i++) {
            char tChar = normalizedTarget.charAt(i);
            targetCounts.put(tChar, targetCounts.getOrDefault(tChar, 0) + 1);
        }

        for (java.util.Map.Entry<Character, Integer> e : targetCounts.entrySet()) {
            Character key = e.getKey();
            Integer count = e.getValue();
            if (!count.equals(answerCounts.getOrDefault(key, 0))) {
                return false;
            }
        }

        return true;
    }

    public String[] scrambleLetters() {
        if (this.targetWord == null || this.targetWord.length() == 0) {
            this.scrambledLetters = new String[0];
            return this.scrambledLetters;
        }

        int n = this.targetWord.length();
        String[] chars = new String[n];
        for (int i = 0; i < n; i++) {
            chars[i] = String.valueOf(this.targetWord.charAt(i));
        }

        int maxAttempts = 10;
        for (int attempt = 0; attempt < maxAttempts; attempt++) {
            String[] shuffled = java.util.Arrays.copyOf(chars, n);

            for (int i = n - 1; i > 0; i--) {
                int j = (int) (Math.random() * (i + 1));
                String temp = shuffled[i];
                shuffled[i] = shuffled[j];
                shuffled[j] = temp;
            }

            String joined = String.join("", shuffled);
            if (!joined.equalsIgnoreCase(this.targetWord)) {
                this.scrambledLetters = java.util.Arrays.copyOf(shuffled, n);
                return this.scrambledLetters;
            }
        }

        if (n > 1) {
            String[] rotated = new String[n];
            for (int i = 0; i < n; i++) {
                rotated[i] = chars[(i + 1) % n];
            }
            String joinedRotated = String.join("", rotated);
            if (!joinedRotated.equalsIgnoreCase(this.targetWord)) {
                this.scrambledLetters = rotated;
            } else {
                this.scrambledLetters = java.util.Arrays.copyOf(chars, n);
            }
            return this.scrambledLetters;
        } else {
            this.scrambledLetters = java.util.Arrays.copyOf(chars, n);
            return this.scrambledLetters;
        }
    }

    public String[] getScrambledLetters() {
        if (this.scrambledLetters == null || this.scrambledLetters.length == 0) {
            scrambleLetters();
        }
        if (this.scrambledLetters == null) {
            return new String[0];
        }
        return java.util.Arrays.copyOf(this.scrambledLetters, this.scrambledLetters.length);
    }

    public String getTargetWord() {
        return this.targetWord;
    }

    public String getHint() {
        if (this.targetWord == null || this.targetWord.length() == 0) {
            return " ";
        }

        int len = this.targetWord.length();
        char firstChar = this.targetWord.charAt(0);

        StringBuilder hidden = new StringBuilder();
        hidden.append(firstChar);
        for (int i = 1; i < len; i++) {
            hidden.append('_');
        }

        String extraHint = " ";
        if (len > 2) {
            int randomIndex = 1 + new Random().nextInt(len - 1);
            char extraChar = this.targetWord.charAt(randomIndex);
            extraHint = " Also, the letter in " + (randomIndex + 1) + " place is '" + extraChar + "'.";
        }

        return "Hint: " + hidden.toString() + " (Length: " + len + ")" + extraHint;
    }
}
