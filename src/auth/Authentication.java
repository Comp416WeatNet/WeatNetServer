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
                  while(line != null && !line.equals("Line end")) {
                      question = fin.readLine();
                      if(question.equals("Line end"))
                          break;
                      answer = fin.readLine();
                      answerMap.put(question, answer);
                  }
                  System.out.println("True username");
                  return;
               }
            }
            System.out.println("False username...");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean Authenticate(BufferedReader is, PrintWriter os) {
        List<String> questionList = Arrays.asList(questions);
        Collections.shuffle(questionList);
        String username;
        String answer;
        String question;
        try {
            username = is.readLine();
            this.GetAnswers(username);
            for(int i=0; i<3; i++) {
                question = questionList.get(i);
                os.println(question);
                os.flush();
                answer = is.readLine();
                boolean check = CheckAnswer(question, answer);
                if (!check) {
                    return check;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    public String createToken() {
        return "token 123";
    }
}
