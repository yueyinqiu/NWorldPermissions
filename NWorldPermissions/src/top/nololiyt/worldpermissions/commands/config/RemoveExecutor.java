package top.nololiyt.worldpermissions.commands.config;

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
    
        worlds.remove(args[layer]);
        config.set("controlled-worlds", worlds);
        
        rootPlugin.saveConfig();
        
        commandSender.sendMessage(rootPlugin.getMessagesManager().getMessage(
                messageKey.append("completed"), cPairs));
        return true;
    }
}