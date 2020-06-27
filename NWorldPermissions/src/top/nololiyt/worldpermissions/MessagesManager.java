package top.nololiyt.worldpermissions;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
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
        reloadConfiguration();
    }
    
    public void reloadConfiguration()
    {
        rootPlugin.saveResource("messages.yml", false);
        configuration = YamlConfiguration.loadConfiguration(new File(
                rootPlugin.getDataFolder().getAbsolutePath(), "messages.yml"));
    }
    
    public void sendMessage(DotDividedStringBuilder node, StringPair[] stringPairs, CommandSender target)
    {
        String key = node.toString();
        String result = configuration.getString(key);
        
        if (result == null)
        {
            rootPlugin.getLogger().severe(
                    "File 'messages.yml' is corrupted and '" + key
                            + "' is missing.");
            return;
        }
        
        result = result.trim();
        if (result.isEmpty())
            return;
        
        result = result.replace('&', '§');
        for (StringPair pair : stringPairs)
        {
            result = result.replace(pair.getKey(), pair.getValue());
        }
        target.sendMessage(result);
    }
}