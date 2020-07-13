package top.nololiyt.worldpermissions.configurationmanagers;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;
import top.nololiyt.worldpermissions.RootPlugin;
import top.nololiyt.worldpermissions.entitiesandtools.WorldInfo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WorldsManager extends ConfigurationManager
{
    @Override
    String getFileName()
    {
        return "worlds.yml";
    }
    
    public WorldsManager(RootPlugin rootPlugin)
    {
        super(rootPlugin);
    }
    
    public List<String> allControlledWorldsName()
    {
        List<String> result = new ArrayList<>();
    
        Configuration configuration = getConfiguration();
        for (String key : configuration.getKeys(false))
        {
            WorldInfo info = getWorldInfo(key);
            if(info.isControlled())
                result.add(key);
        }
        
        return result;
    }
    
    public WorldInfo getWorldInfo(String name)
    {
        ConfigurationSection section = getConfiguration().
                getConfigurationSection(name);
        
        if (section == null)
            return new WorldInfo(name, 0, false);
    
        return new WorldInfo(section.getString("display"),
                section.getDouble("thrust"),
                section.getBoolean("controlled"));
    }
    
    public void set(String world, WorldInfo worldInfo) throws IOException
    {
        ConfigurationSection section = getConfiguration()
                .createSection(world);
        section.set("display", worldInfo.getDisplay());
        section.set("thrust", worldInfo.getThrust());
        section.set("controlled", worldInfo.isControlled());
        saveConfiguration();
    }
    
    public boolean uncontrol(String world) throws IOException
    {
        ConfigurationSection section = getConfiguration().
                getConfigurationSection(world);
        if (section == null)
        {
            return false;
        }
        WorldInfo current = getWorldInfo(world);
        set(world,
                new WorldInfo(current.getDisplay(), current.getThrust(), false));
        return true;
    }
    
    public List<String> getAllWorldName()
    {
        List<String> result = new ArrayList<>();
        for (World world : Bukkit.getWorlds())
        {
            result.add(world.getName());
        }
        return result;
    }
    
    public boolean existedInGame(String worldName)
    {
        for (World world : Bukkit.getWorlds())
        {
            if (world.getName().equals(worldName))
            {
                return true;
            }
        }
        return false;
    }
}