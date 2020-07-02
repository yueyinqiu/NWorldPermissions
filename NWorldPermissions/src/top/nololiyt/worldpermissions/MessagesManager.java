package top.nololiyt.worldpermissions;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.omg.CORBA.NVList;
import sun.rmi.runtime.Log;
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
        File file = new File(rootPlugin.getDataFolder().getAbsolutePath(), "messages.yml");
        if (!file.exists())
            rootPlugin.saveResource("messages.yml", false);
        configuration = YamlConfiguration.loadConfiguration(file);
    }
    
    public void sendMessage(StringPair[] stringPairs, CommandSender target, DotDividedStringBuilder node)
    {
        String message = getItem(node);
        if (message != null)
            sendMessage(stringPairs, target, message);
    }
    
    public void sendMessage(StringPair[] stringPairs, CommandSender target, String message)
    {
        String result = message.trim();
        if (result.isEmpty())
            return;
        result = ChatColor.translateAlternateColorCodes('&', result);
        for (StringPair pair : stringPairs)
        {
            result = result.replace(pair.getKey(), pair.getValue());
        }
        target.sendMessage(result);
    }
    
    public String getItem(DotDividedStringBuilder node)
    {
        String key = node.toString();
        String result = configuration.getString(key);
    
        if (result == null)
        {
            rootPlugin.getLogger().severe(
                    "File 'messages.yml' is corrupted and '" + key
                            + "' is missing.");
        }
        return result;
    }
}