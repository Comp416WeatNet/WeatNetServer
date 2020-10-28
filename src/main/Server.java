package main;

import controllers.AuthController;

public class Server {

    public static void main(String[] args) {
        AuthController authController = new AuthController();
        System.out.println(authController.GetQuestion(2));
        System.out.println(authController.GetAnswer("basakcortuk", 2));
        System.out.println(authController.CheckAnswer("basakcortuk", 2, "gaziantep"));
    }
}
