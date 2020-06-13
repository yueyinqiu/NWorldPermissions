package top.nololiyt.worldpermissions;

class StringPair {
    private String key;

    String getKey() {
        return key;
    }

    private String value;

    String getValue() {
        return value;
    }

    private StringPair(String key, String value) {
        this.key = key;
        this.value = value;
    }

    static StringPair senderName(String value) {
        return new StringPair("{senderName}", value);
    }
    static StringPair playerName(String value) {
        return new StringPair("{playerName}", value);
    }
    static StringPair worldName(String value) {
        return new StringPair("{worldName}", value);
    }
}