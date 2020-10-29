package auth;

import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.*;

public class Authentication {
    private static String[] questions = new String[] {
            "What is your favorite color?",
            "In which city, you were born?",
            "What is your mother's name?",
            "What is your favorite pet?"
    };
    private BufferedReader fin;
    private File answers;
    private HashMap<String, String> answerMap;

    public Authentication (){
        answers = new File(System.getProperty("user.dir") + "/SecurityAnswers");
        answerMap = new HashMap<>();
    }

    public boolean CheckAnswer(String question, String answer) {
        String correctAnswer = answerMap.get(question);
        return correctAnswer.equals(answer);
    }

    public void GetAnswers(String username) {
        String line;
        String question;
        String answer;
        try {
            fin = new BufferedReader(new FileReader(answers));
            while ((line = fin.readLine()) != null) {
               if(line.contains(username)) {
                  while(line != null || !line.equals("Line end")) {
                      question = fin.readLine();
                      answer = fin.readLine();
                      answerMap.put(question, answer);
                  }
               }
            }
            System.out.println("False username...");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean Authenticate(@NotNull BufferedReader is, PrintWriter os) {
        List<String> questionList = Arrays.asList(questions);
        Collections.shuffle(questionList);
        String username;
        String answer;
        try {
            username = is.readLine();
            this.GetAnswers(username);
            for(int i=0; i<3; i++) {
                answer = is.readLine();
                boolean check = CheckAnswer(questionList.get(0), answer);
                questionList.remove(0);
                if (!check) {
                    return check;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }
}
