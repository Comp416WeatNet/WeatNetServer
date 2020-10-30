package auth;

import model.DataType;
import model.Result;

import java.io.*;
import java.util.*;

public class Authentication {
    private static final String DEFAULT_FILE_PATH = System.getProperty("user.dir") + "/SecurityAnswers";
    private static final String[] questions = new String[] {
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

    public boolean checkAnswer(String question, String answer) {
        String correctAnswer = answerMap.get(question);
        return correctAnswer.equals(answer);
    }

    public void getAnswers(String username) {
        String line;
        String question;
        String answer;
        try {
            fin = new BufferedReader(new FileReader(answers));
            while ((line = fin.readLine()) != null) {
               if(line.equals(username)) {
                   while((line = fin.readLine()) != null && !line.equals("Line end")) {
                      question = line;
                      if(question.equals("Line end"))
                          break;
                      answer = fin.readLine();
                      answerMap.put(question, answer);
                  }
                  return;
               }
            }
            this.disconnect("Username is not existing in the database.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean authenticate(BufferedReader inputStream, PrintWriter outputStream) {
        this.is = inputStream;
        this.os = outputStream;
        ArrayList<String> questionList = this.toList(questions);
        Collections.shuffle(questionList);
        String username;
        String answer;
        String question;
        DataType resp;
        try {
            username = is.readLine();
            resp = new DataType(username);
            username = "<" + resp.getPayload() + ">";
            this.getAnswers(username);
            for(int i=0; i<3; i++) {
                question = questionList.get(i);
                DataType data = new DataType(DataType.AUTH_PHASE ,DataType.AUTH_CHALLENGE ,question);
                os.println(data.getData());
                os.flush();
                answer = is.readLine();
                if(answer != null) {
                    resp = new DataType(answer);
                    answer = resp.getPayload();
                    System.out.println("Client sent message to the " + Thread.currentThread().getId() + "th thread.");
                    boolean check = checkAnswer(question, answer);
                    if (!check) {
                        return check;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    private void disconnect(String message) {
        Result result = new Result(message, false);
        DataType fail = result.convertToDatatype();
        try {
            os.println(fail.getData());
            os.println("Closing the connection");
            os.flush();

            if (is != null)
            {
                is.close();
                System.err.println(" Socket Input Stream Closed");
            }

            if (os != null)
            {
                os.close();
                System.err.println("Socket Out Closed");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String createToken() {
        return "token 123";
    }

    public ArrayList<String> toList(String[] array){
        ArrayList<String> arrayList = new ArrayList<>();
        for (String element : array){
            arrayList.add(element);
        }
        return arrayList;
    }
}
