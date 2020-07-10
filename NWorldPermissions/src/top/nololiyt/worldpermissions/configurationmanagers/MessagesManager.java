package top.nololiyt.worldpermissions.configurationmanagers;

import com.sun.istack.internal.NotNull;
import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import top.nololiyt.worldpermissions.RootPlugin;
import top.nololiyt.worldpermissions.entitiesandtools.DotDividedStringBuilder;
import top.nololiyt.worldpermissions.entitiesandtools.StringPair;


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
    
    public @NotNull
    String getItemAndShowError(DotDividedStringBuilder node)
    {
        String key = node.toString();
        String result = getConfiguration().getString(key);
        
        if (result == null)
        {
            getRootPlugin().getLogger().severe(
                    "File 'messages.yml' is corrupted and '" + key
                            + "' is missing.");
            return key;
        }
        return result;
    }
    
    public void sendMessage(CommandSender target, DotDividedStringBuilder messageKey, StringPair[] args)
    {
        String message = getItemAndShowError(messageKey);
        sendMessage(target, message, args);
    }
    
    public void sendMessage(CommandSender target, @NotNull String message, StringPair[] args)
    {
        String result = message.trim();
        if (result.isEmpty())
            return;
        
        result = ChatColor.translateAlternateColorCodes('&', result);
        for (StringPair pair : args)
        {
            result = result.replace(pair.getKey(), pair.getValue());
        }
        
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null)
        {
            if (target instanceof Player)
                result = PlaceholderAPI.setPlaceholders((Player) target, result);
            else if (target instanceof OfflinePlayer)
                result = PlaceholderAPI.setPlaceholders((OfflinePlayer) target, result);
        }
        
        target.sendMessage(result);
    }
}