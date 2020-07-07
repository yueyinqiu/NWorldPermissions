package top.nololiyt.worldpermissions.entitiesandtools;

import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

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
    
    public static OfflinePlayersPosition fromFile(File file)
    {
        if (!file.exists())
            return null;
        
        YamlConfiguration yamlConfiguration = YamlConfiguration
                .loadConfiguration(file);
    
        Object position = yamlConfiguration.get("position");
        if (position == null)
            return null;
    
        boolean changed = yamlConfiguration.getBoolean("changed");
        return new OfflinePlayersPosition((Location) position, changed);
    }
    
    public void saveTo(File file) throws IOException
    {
        YamlConfiguration yamlConfiguration = new YamlConfiguration();
        yamlConfiguration.set("position", position);
        yamlConfiguration.set("changed", hasChanged);
    
        yamlConfiguration.save(file);
    }
}