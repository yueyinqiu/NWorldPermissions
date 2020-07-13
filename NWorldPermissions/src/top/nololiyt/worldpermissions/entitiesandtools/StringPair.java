package top.nololiyt.worldpermissions.entitiesandtools;

public class StringPair
{
    private String key;
    
    public String getKey()
    {
        return key;
    }
    
    private String value;
    
    public String getValue()
    {
        return value;
    }
    
    private StringPair(String key, String value)
    {
        this.key = key;
        this.value = value;
    }
    
    public static StringPair senderName(String value)
    {
        return new StringPair("{senderName}", value);
    }
    
    public static StringPair playerName(String value)
    {
        return new StringPair("{playerName}", value);
    }
    
    public static StringPair worldName(String value)
    {
        return new StringPair("{worldName}", value);
    }
    
    public static StringPair teleportedCount(String value)
    {
        return new StringPair("{teleportedCount}", value);
    }
    
    public static StringPair unteleportedCount(String value)
    {
        return new StringPair("{unteleportedCount}", value);
    }
    
    public static StringPair markName(String value)
    {
        return new StringPair("{markName}", value);
    }
    
    public static StringPair teleportationTimes(String value)
    {
        return new StringPair("{teleportationTimes}", value);
    }
    public static StringPair teleportedTimes(String value)
    {
        return new StringPair("{teleportedTimes}", value);
    }
    
    public static StringPair time(String value)
    {
        return new StringPair("{time}", value);
    }
    public static StringPair version(String value)
    {
        return new StringPair("{version}", value);
    }
    public static StringPair fromWorld(String value)
    {
        return new StringPair("{fromWorld}", value);
    }
    public static StringPair toWorld(String value)
    {
        return new StringPair("{toWorld}", value);
    }
    public static StringPair worldDisplayName(String value)
    {
        return new StringPair("{worldDisplayName}", value);
    }
    public static StringPair thrust(String value)
    {
        return new StringPair("{thrust}", value);
    }
}