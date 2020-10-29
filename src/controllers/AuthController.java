package controllers;

import auth.Authentication;

import java.io.BufferedReader;
import java.io.PrintWriter;

public class AuthController {
    private Authentication auth;
    private static AuthController authController;

    public AuthController() {
        auth = new Authentication();
    }

    public static AuthController getAuthController() {
        if (authController == null)
            authController = new AuthController();
        return authController;
    }

//    public String GetQuestion(int number){
//        return auth.GetQuestion(number);
//    }
//    public boolean CheckAnswer(String username, int number, String answer) {
//        return auth.CheckAnswer(username, number, answer);
//    }
//
//    public String GetAnswer(String username, int number) {
//        return auth.GetAnswers(username, number);
//    }

    public boolean authenticate(BufferedReader is, PrintWriter os) {
        return auth.authenticate(is, os);
    }

    public String createToken() {
        return auth.createToken();
    }
}
