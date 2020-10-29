package model;

public class Result {
    String message;
    boolean result;

    public Result(String message, boolean result) {
        this.message = message;
        this.result = result;
    }

    public DataType convertToDatatype() {
        DataType dataType = result ? new DataType(0x00, 0x02, message) : new DataType(0x00, 0x03, message);
        return dataType;
    }

    @Override
    public String toString() {
        return "Result{" +
                "message='" + message + '\'' +
                ", result=" + result +
                '}';
    }
}
