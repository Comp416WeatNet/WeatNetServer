package controllers;

import auth.Authentication;

import java.io.BufferedReader;
import java.io.PrintWriter;

public class QueryingController {
    private AuthController authController;

    public QueryingController(AuthController authController) {
        this.authController = authController;
    }

    public boolean authenticate(BufferedReader is, PrintWriter os) {
        return authController.authenticate(is, os);
    }

    public String createToken() {
        return authController.createToken();
    }
}
