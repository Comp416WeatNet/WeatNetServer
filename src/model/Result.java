package model;

public class Result {
    String message;
    boolean result;

    public Result(String message, boolean result) {
        this.message = message;
        this.result = result;
    }

    public DataType convertToDatatype() {
        DataType dataType = result ? new DataType(DataType.AUTH_PHASE, DataType.AUTH_FAIL, message) : new DataType(DataType.AUTH_PHASE, DataType.AUTH_SUCCESS, message);
        return dataType;
    }
}
