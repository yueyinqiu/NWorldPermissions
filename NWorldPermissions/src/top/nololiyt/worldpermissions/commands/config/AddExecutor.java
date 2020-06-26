package top.nololiyt.worldpermissions.commands.config;

import org.bukkit.command.CommandSender;
import org.bukkit.configuration.Configuration;
import top.nololiyt.worldpermissions.RootPlugin;
import top.nololiyt.worldpermissions.entities.DotDividedStringBuilder;
import top.nololiyt.worldpermissions.entities.StringPair;
import top.nololiyt.worldpermissions.commands.Executor;

import java.util.List;

public class AddExecutor extends Executor
{
    protected final static String layerName = "add";
    
    @Override
    protected String permissionName()
    {
        return null;
    }
    
    @Override
    protected String messageKey()
    {
        return layerName;
    }
    
    @Override
    protected boolean run(int layer, RootPlugin rootPlugin, DotDividedStringBuilder permission,
                          DotDividedStringBuilder messageKey, CommandSender commandSender,
                          String[] args)
    {
        if (args.length - 1 != layer)
            return false;
        
        StringPair[] cPairs = new StringPair[]{
                StringPair.worldName(args[layer]),
                StringPair.senderName(commandSender.getName())
        };
    
        Configuration config = rootPlugin.getConfig();
        List<String> worlds = config.getStringList("controlled-worlds");
        if (!worlds.contains(args[layer]))
        {
            worlds.add(args[layer]);
        }
        config.set("controlled-worlds", worlds);
        rootPlugin.saveConfig();
    
        rootPlugin.getMessagesManager().sendMessage(
                messageKey.append("completed"), cPairs,commandSender);
        return true;
    }
}