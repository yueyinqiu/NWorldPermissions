package top.nololiyt.worldpermissions;

import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class MarksManager
{
    private RootPlugin rootPlugin;
    
    private YamlConfiguration configuration;
    
    MarksManager(RootPlugin rootPlugin)
    {
        this.rootPlugin = rootPlugin;
        reloadConfiguration();
    }
    
    public void reloadConfiguration()
    {
        rootPlugin.saveResource("marks.yml", false);
        configuration = YamlConfiguration.loadConfiguration(new File(
                rootPlugin.getDataFolder().getAbsolutePath(), "marks.yml"));
    }
    
    public void setMark(String name, Location mark) throws IOException
    {
        configuration.set(name, mark);
        configuration.save(new File(
                rootPlugin.getDataFolder().getAbsolutePath(), "marks.yml"));
    }
    
    public Location getMark(String name)
    {
        Object oLocation = configuration.get(name);
        if (oLocation == null)
        {
            return null;
        }
        return (Location) oLocation;
    }
}