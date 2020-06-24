package top.nololiyt.worldpermissions.entities

class StringPair private constructor(val key: String, val value: String)
{
    companion object
    {
        fun senderName(value: String): StringPair
        {
            return StringPair("{senderName}", value);
        }
        
        fun playerName(value: String): StringPair
        {
            return StringPair("{playerName}", value);
        }
        
        fun worldName(value: String): StringPair
        {
            return StringPair("{worldName}", value);
        }
        
        fun teleportedCount(value: String): StringPair
        {
            return StringPair("{teleportedCount}", value);
        }
        
        fun unteleportedCount(value: String): StringPair
        {
            return StringPair("{unteleportedCount}", value);
        }
        
        fun markName(value: String): StringPair
        {
            return StringPair("{markName}", value);
        }
    }
}