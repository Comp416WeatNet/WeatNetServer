package auth;

import model.DataType;
import model.Result;

import java.io.*;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.*;

public class Authentication {
    private static final String DEFAULT_FILE_PATH = System.getProperty("user.dir") + "/SecurityAnswers";
    private static final String[] questions = new String[]{
            "What is your favorite color?",
            "In which city, you were born?",
            "What is your mother's name?",
            "What is your favorite pet?"
    };
    private BufferedReader fin;
    private static File answers;
    private final HashMap<String, String> answerMap;
    private final BufferedReader is;
    private final PrintWriter os;
    private final Socket s;
    private String username;
    private final Random rand = new Random();

    public Authentication(BufferedReader inputStream, PrintWriter outputStream, Socket s) {
        this.is = inputStream;
        this.os = outputStream;
        this.s = s;
        answers = new File(DEFAULT_FILE_PATH);
        answerMap = new HashMap<>();
    }

    public boolean checkAnswer(String question, String answer) {
        String correctAnswer = answerMap.get(question);
        return correctAnswer.equals(answer);
    }

    public boolean checkUsername(String username) {
        String line;
        String question;
        String answer;
        try {
            fin = new BufferedReader(new FileReader(answers));
            while ((line = fin.readLine()) != null) {
                if (line.equals(username)) {
                    while ((line = fin.readLine()) != null && !line.equals("Line end")) {
                        question = line;
                        if (question.equals("Line end"))
                            break;
                        answer = fin.readLine();
                        answerMap.put(question, answer);
                    }
                    return true;
                }
            }
            return false;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean authenticate() {

        ArrayList<String> questionList = this.toList(questions);
        Collections.shuffle(questionList);

        String resp;
        DataType data;
        try {
            resp = is.readLine();
            data = new DataType(resp);
            username = "<" + data.getPayload() + ">";
            boolean result = this.checkUsername(username);
            if (result == false) {
                this.disconnect("Username is not existing in the database. The connection will close now.", false);
            } else {
                return sendChallenges(questionList);
            }
        } catch (SocketTimeoutException e){
            disconnect("Server connection timed out. The connection will close now.", false);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean sendChallenges(ArrayList<String> questionList) {
        int qNum = rand.nextInt(questionList.size()) + 1;
        String answer;
        String question;
        DataType resp;
        try {
            for (int i = 0; i < qNum; i++) {
                question = questionList.get(i);
                DataType data = new DataType(DataType.AUTH_PHASE, DataType.AUTH_CHALLENGE, question);
                os.println(data.getData());
                answer = is.readLine();
                if (answer != null) {
                    resp = new DataType(answer);
                    answer = resp.getPayload();
//                    System.out.println("Client sent message:" + answer + "\nto the " + Thread.currentThread().getId() + "th thread.");
                    boolean check = checkAnswer(question, answer);
                    if (!check) {
                        this.disconnect("You gave the wrong answer. The connection will close now.", false);
                        return false;
                    }
                }
            }
            return true;
        } catch (SocketTimeoutException e) {
            disconnect("Server connection timed out. The connection will close now.", false);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void timeout(String s) {
        try {
            is.readLine();
            disconnect(s, false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void disconnect(String message, boolean res) {
        Result result = new Result(message, res);
        DataType fail = result.convertToDatatype();
        try {
            os.println(fail.getData());
            if (is != null) {
                is.close();
                System.err.println("The input stream of socket with the address: " + s.getRemoteSocketAddress() + " is closed");
            }

            if (os != null) {
                os.close();
                System.err.println("The output stream of socket with the address: " + s.getRemoteSocketAddress() + " is closed");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String createToken() {
        String token = String.valueOf(username.hashCode());
        token += String.valueOf(Math.abs(rand.nextInt()));
        return token;
    }

    public ArrayList<String> toList(String[] array) {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.addAll(Arrays.asList(array));
        return arrayList;
    }
}
