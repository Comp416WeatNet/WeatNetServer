package auth;

import model.DataType;
import model.Result;

import java.io.*;
import java.util.*;

public class Authentication {
    private static final String DEFAULT_FILE_PATH = System.getProperty("user.dir") + "/SecurityAnswers";
    private static String[] questions = new String[] {
            "What is your favorite color?",
            "In which city, you were born?",
            "What is your mother's name?",
            "What is your favorite pet?"
    };
    private BufferedReader fin;
    private static File answers;
    private HashMap<String, String> answerMap;
    private BufferedReader is;
    private PrintWriter os;

    public Authentication (){
        answers = new File(this.DEFAULT_FILE_PATH);
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
                  os.println("True username");
                  return;
               }
            }
            this.Disconnect("Username is not existing in the database.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean Authenticate(BufferedReader inputStream, PrintWriter outputStream) {
        this.is = inputStream;
        this.os = outputStream;
        List<String> questionList = Arrays.asList(questions);
        Collections.shuffle(questionList);
        String username;
        String answer;
        String question;
        DataType resp;
        try {
            username = is.readLine();
            resp = new DataType(username);
            this.GetAnswers(resp.getPayload());
            for(int i=0; i<3; i++) {
                question = questionList.get(i);
                os.println(new DataType(DataType.AUTH_PHASE ,DataType.AUTH_CHALLENGE ,question));
                os.flush();
                answer = is.readLine();
                resp = new DataType(answer);
                answer = resp.getPayload();
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

    private void Disconnect(String message) {
        Result result = new Result(message, false);
        DataType fail = result.convertToDatatype();
        try {
            os.println(fail.getData());
            os.flush();
        //disconnects the connections..
            is.close();
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String createToken() {
        return "token 123";
    }
}
