package top.nololiyt.worldpermissions.executors;

import org.bukkit.command.CommandSender;
import top.nololiyt.worldpermissions.RootPlugin;
import top.nololiyt.worldpermissions.entities.StringPair;

public class ConfigExecutor
{
    private RootPlugin rootPlugin;
    
    ConfigExecutor(RootPlugin rootPlugin)
    {
        this.rootPlugin = rootPlugin;
    }
    
    private boolean executeCommand(CommandSender commandSender, String[] args)
    {
        // config
        if(args < 0)
        {
        
        }
    }
    
    private boolean reload(CommandSender commandSender, String[] args)
    {
        // config reload
        rootPlugin.reloadConfig();
        rootPlugin.getMessagesManager().reloadConfiguration();
        
        if (commandSender.hasPermission("nworldpermissions.admin"))
        {
            StringPair[] pairs = new StringPair[]{
                    StringPair.senderName(commandSender.getName())
            };
            commandSender.sendMessage(rootPlugin.getMessagesManager().getMessage(
                    "reload.reloaded", pairs));
        }
        return true;
    }
}
