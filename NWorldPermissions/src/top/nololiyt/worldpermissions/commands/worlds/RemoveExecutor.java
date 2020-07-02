package top.nololiyt.worldpermissions.commands.worlds;

import org.bukkit.command.CommandSender;
import org.bukkit.configuration.Configuration;
import top.nololiyt.worldpermissions.RootPlugin;
import top.nololiyt.worldpermissions.entities.DotDividedStringBuilder;
import top.nololiyt.worldpermissions.entities.StringPair;
import top.nololiyt.worldpermissions.commands.Executor;

import java.util.List;

public class RemoveExecutor extends Executor
{
    protected final static String layerName = "remove";
    
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
    
        if(!worlds.remove(args[layer]))
        {
            rootPlugin.getMessagesManager().sendMessage(
                    cPairs, commandSender, messageKey.append("no-such-controlled-world"));
        }
        
        config.set("controlled-worlds", worlds);
        
        rootPlugin.saveConfig();
    
        rootPlugin.getMessagesManager().sendMessage(
                cPairs, commandSender, messageKey.append("completed"));
        return true;
    }
}