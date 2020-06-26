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
    
    private File getMarksFile()
    {
        return new File(
                rootPlugin.getDataFolder().getAbsolutePath(), "marks.yml");
    }
    
    private void saveDefaultFile(File file)
    {
        InputStream res = rootPlugin.getResource("marks.yml");
        try
        {
            file.getParentFile().mkdirs();
            file.delete();
            file.createNewFile();
            FileOutputStream fileOutputStream = new FileOutputStream(file);
    
            byte[] buffer = new byte[4096];
            for (; ; )
            {
                int count = res.read(buffer, 0, buffer.length);
                fileOutputStream.write(buffer, 0, count);
                if (count < buffer.length)
                {
                    break;
                }
            }
    
            fileOutputStream.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    
    public void reloadConfiguration()
    {
        File file = getMarksFile();
        if (!file.exists())
        {
            saveDefaultFile(file);
        }
        configuration = YamlConfiguration.loadConfiguration(file);
    }
    
    public void setMark(String name, Location mark) throws IOException
    {
        configuration.set(name, mark);
        configuration.save(getMarksFile());
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