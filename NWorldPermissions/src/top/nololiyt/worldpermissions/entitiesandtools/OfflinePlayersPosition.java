package top.nololiyt.worldpermissions.entitiesandtools;

import org.bukkit.Location;

public class OfflinePlayersPosition
{
    private Location position;
    public Location getPosition()
    {
        return position;
    }
    
    private boolean hasChanged;
    public boolean hasChanged()
    {
        return hasChanged;
    }
    
    public OfflinePlayersPosition(Location position, boolean hasChanged)
    {
        this.position = position;
        this.hasChanged = hasChanged;
    }
}
