package auth;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Authentication {
    private static String[] questions = new String[] {
       "What is your favorite color?",
       "In which city, you were born?",
       "What is your mother's name?"
    };

    public String GetQuestion(int number) {
        if (number < 1 || number > questions.length){
            return "Please enter a number within the question range.";
        }
        return questions[number - 1];
    }
    public boolean CheckAnswer(String username, int number, String answer) {
        String correctAnswer = getAnswer(username,number);
        return correctAnswer.equals(answer);
    }
    public String getAnswer(String username, int number) {
        File answers = new File(System.getProperty("user.dir") + "/SecurityAnswers");
        String line;
        try {
            BufferedReader br = new BufferedReader(new FileReader(answers));
            while (true) {
                line = br.readLine();
                if(line == null) {
                    return "Not found";
                }
               if(line.contains(username)) {
                  while(line != null) {
                      if (line.contains(Integer.toString(number))){
                          String[] correctLine = line.split(" ");
                          line = correctLine[1];
                          return line;
                      }
                      line=br.readLine();
                  }
               }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "Failed";
        }

    }
}