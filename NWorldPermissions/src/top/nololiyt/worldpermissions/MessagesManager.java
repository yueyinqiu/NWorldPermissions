package top.nololiyt.worldpermissions;

import org.bukkit.configuration.file.YamlConfiguration;
import top.nololiyt.worldpermissions.entities.DotDividedStringBuilder;
import top.nololiyt.worldpermissions.entities.StringPair;

import java.io.*;

public class MessagesManager
{
    private RootPlugin rootPlugin;
    
    private YamlConfiguration configuration;
    
    MessagesManager(RootPlugin rootPlugin)
    {
        this.rootPlugin = rootPlugin;
        File file = getMessagesFile();
        if (!file.exists())
        {
            saveDefaultFile(file);
        }
        reloadConfiguration();
    }
    
    private File getMessagesFile()
    {
        return new File(
                rootPlugin.getDataFolder().getAbsolutePath(), "messages.yml");
    }
    
    private void saveDefaultFile(File file)
    {
        InputStream res = rootPlugin.getResource("messages.yml");
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
        configuration = YamlConfiguration.loadConfiguration(
                getMessagesFile());
    }
    
    public String getMessage(DotDividedStringBuilder node, StringPair[] stringPairs)
    {
        String key = node.toString();
        String result = configuration.getString(key);
        
        if(result == null)
        {
            rootPlugin.getLogger().severe(
                    "&cFile 'messages.yml' is corrupted and '" + key
                            + "' is missing. You may contact the operators.");
            return "";
        }
        
        result.trim().replace('&', '§');
        for (StringPair pair : stringPairs)
        {
            result = result.replace(pair.getKey(), pair.getValue());
        }
        return result;
    }
}