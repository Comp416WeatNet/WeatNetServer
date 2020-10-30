package controllers;

import auth.Authentication;

import java.io.BufferedReader;
import java.io.PrintWriter;

public class AuthController {
    private Authentication auth;

    public AuthController() {
        auth = new Authentication();
    }

    public boolean authenticate(BufferedReader is, PrintWriter os) {
        return auth.authenticate(is, os);
    }

    public String createToken() {
        return auth.createToken();
    }
}
