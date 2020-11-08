package model;

public class DataType {
    public static final byte AUTH_PHASE = 0x00;
    public static final byte QUERYING_PHASE = 0x01;

    public static final byte AUTH_REQUEST = 0x00;
    public static final byte AUTH_CHALLENGE = 0x01;
    public static final byte AUTH_FAIL = 0x02;
    public static final byte AUTH_SUCCESS = 0x03;


    private String data;
    private final byte phase;
    private final byte type;
    private final int size;
    private String payload;
    private String token;

    public DataType(String data) {
        int startIndex = findStartIndex(data);
        this.data = data;
        Byte phase = Byte.decode(data.substring(0, 1));
        this.phase = phase;
        Byte type = Byte.decode(data.substring(1, 2));
        this.type = type;
        int size = Integer.decode(data.substring(2, startIndex));
        this.size = size;
        this.payload = data.substring(startIndex + 1);
        if (this.phase == QUERYING_PHASE) {
            splitToken();
        }
    }

    public DataType(byte phase, byte type, String payload) {
        this.phase = phase;
        this.type = type;
        this.size = payload.length();
        this.payload = payload;
        this.data = Byte.toString(this.phase);
        this.data += Byte.toString(this.type);
        this.data += Integer.toString(this.size);
        this.data += "ø";
        this.data += this.payload;
    }

    public String getData() {
        return this.data;
    }

    public int getPhase() {
        return this.phase;
    }

    public int getType() {
        return this.type;
    }

    public String getPayload() {
        return this.payload;
    }

    private void splitToken() {
        int index = findTokenIndex(this.payload);
        token = payload.substring(0, index);
        payload = payload.substring(index + 1);
    }

    private int findStartIndex(String data) {
        char[] chArray = data.toCharArray();
        int index = 0;
        for (char ch : chArray) {
            if (String.valueOf(ch).equals("ø"))
                break;
            index++;
        }
        return index;
    }

    private int findTokenIndex(String data) {
        char[] chArray = data.toCharArray();
        int index = 0;
        for (char ch : chArray) {
            if (String.valueOf(ch).equals("@"))
                break;
            index++;
        }
        return index;
    }
}
