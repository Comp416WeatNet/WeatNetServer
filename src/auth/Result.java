package auth;

public class Result {
    String message;
    boolean result;

    public Result(String message, boolean result) {
        this.message = message;
        this.result = result;
    }


    @Override
    public String toString() {
        return "Result{" +
                "message='" + message + '\'' +
                ", result=" + result +
                '}';
    }
}
