package model;

public class DataType {
    private String data;
    private int phase;
    private int type;
    private int size;
    private String payload;

    public DataType(String data) {
        this.data = data;
        Byte phase = Byte.decode(data.substring(0,1));
        this.phase = phase.intValue();
        Byte type = Byte.decode(data.substring(1,2));
        this.type = type.intValue();
        Byte size = Byte.decode(data.substring(2,6));
        this.size = size.intValue();
        this.payload = data.substring(6);
    }

    public DataType(int phase, int type, String payload){
        this.phase = phase;
        this.type = type;
        this.size = payload.length();
        this.payload = payload;
        this.data += Integer.toString(this.phase);
        this.data += Integer.toString(this.type);
        this.data += Integer.toString(this.size);
        this.data += this.payload;
    }

    public String getData(){
        return this.data;
    }

    public int getPhase(){
        return this.phase;
    }

    public int getType(){
        return this.type;
    }

    public String getPayload(){
        return this.payload;
    }
}
