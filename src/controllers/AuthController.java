package controllers;

import auth.Authentication;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;

public class AuthController {
    private final Authentication auth;
    private final BufferedReader is;
    private final PrintWriter os;
    private final Socket s;

    public AuthController(BufferedReader is, PrintWriter os, Socket s) {
        this.auth = new Authentication(is, os, s);
        this.is = is;
        this.os = os;
        this.s = s;
    }

    public boolean authenticate() {
        return auth.authenticate();
    }

    public String createToken() {
        return auth.createToken();
    }


    public String getToken() {
        return auth.getToken();
    }
}
