package top.nololiyt.worldpermissions.configurationmanagers;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import top.nololiyt.worldpermissions.RootPlugin;
import top.nololiyt.worldpermissions.entities.DotDividedStringBuilder;
import top.nololiyt.worldpermissions.entities.StringPair;


public class MessagesManager extends ConfigurationManager
{
    public MessagesManager(RootPlugin rootPlugin)
    {
        super(rootPlugin);
    }
    
    @Override
    String getFileName()
    {
        return "messages.yml";
    }
    
    public String getItemAndShowError(DotDividedStringBuilder node)
    {
        String key = node.toString();
        String result = getConfiguration().getString(key);
        
        if (result == null)
        {
            getRootPlugin().getLogger().severe(
                    "File 'messages.yml' is corrupted and '" + key
                            + "' is missing.");
        }
        return result;
    }
    public void sendMessage(StringPair[] stringPairs, CommandSender target, DotDividedStringBuilder node)
    {
        String message = getItemAndShowError(node);
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
}