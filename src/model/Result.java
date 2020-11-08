package model;

public class Result {
    String message;
    boolean result;

    public Result(String message, boolean result) {
        this.message = message;
        this.result = result;
    }

    public DataType convertToDatatype() {
        DataType dataType = result ? new DataType(DataType.AUTH_PHASE, DataType.AUTH_SUCCESS, message) : new DataType(DataType.AUTH_PHASE, DataType.AUTH_FAIL, message);
        return dataType;
    }
    public DataType convertToQueryDataType(){
        DataType dataType = result ? new DataType(DataType.QUERYING_PHASE, DataType.QUERYING_SUCCESS, message) : new DataType(DataType.QUERYING_PHASE, DataType.QUERYING_FAIL, message);
        return dataType;
    }
}
