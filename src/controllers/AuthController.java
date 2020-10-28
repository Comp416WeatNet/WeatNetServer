package controllers;

import Auth.Authentication;

public class AuthController {
    private Authentication auth;
    private String GetQuestion(int number){
        return auth.GetQuestion(number);
    }
}
