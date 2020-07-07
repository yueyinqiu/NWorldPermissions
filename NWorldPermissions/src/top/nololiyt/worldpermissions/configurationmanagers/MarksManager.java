package top.nololiyt.worldpermissions.configurationmanagers;

import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import top.nololiyt.worldpermissions.RootPlugin;

import javax.security.auth.login.Configuration;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Set;

public class MarksManager extends ConfigurationManager
{
    @Override
    String getFileName()
    {
        return "marks.yml";
    }
    
    public MarksManager(RootPlugin rootPlugin)
    {
        super(rootPlugin);
    }
    
    public void setMark(String name, Location mark) throws IOException
    {
        getConfiguration().set(name, mark);
        getConfiguration().save(new File(
                getRootPlugin().getDataFolder().getAbsolutePath(), "marks.yml"));
    }
    
    public Location getMark(String name)
    {
        Object oLocation = getConfiguration().get(name);
        if (oLocation == null)
        {
            return null;
        }
        return (Location) oLocation;
    }
    public Set<String> allMarksName()
    {
        return getConfiguration().getKeys(false);
    }
}