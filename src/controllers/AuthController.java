package controllers;

import auth.Authentication;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;

public class AuthController {
    private Authentication auth;
    private BufferedReader is;
    private PrintWriter os;
    private Socket s;

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
}
