package controllers;

import auth.Authentication;

public class AuthController {
    private Authentication auth;

    public AuthController() {
        auth = new Authentication();
    }

    public String GetQuestion(int number){
        return auth.GetQuestion(number);
    }
    public boolean CheckAnswer(String username, int number, String answer) {
        return auth.CheckAnswer(username, number, answer);
    }

    public String GetAnswer(String username, int number) {
        return auth.getAnswer(username, number);
    }
}
