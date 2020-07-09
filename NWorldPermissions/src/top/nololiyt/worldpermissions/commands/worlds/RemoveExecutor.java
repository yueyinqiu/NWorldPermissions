package top.nololiyt.worldpermissions.commands.worlds;

import org.bukkit.command.CommandSender;
import org.bukkit.configuration.Configuration;
import top.nololiyt.worldpermissions.RootPlugin;
import top.nololiyt.worldpermissions.entitiesandtools.DotDividedStringBuilder;
import top.nololiyt.worldpermissions.entitiesandtools.MessagesSender;
import top.nololiyt.worldpermissions.entitiesandtools.StringPair;
import top.nololiyt.worldpermissions.commands.Executor;

import java.util.ArrayList;
import java.util.List;

public class RemoveExecutor extends Executor
{
    protected final static String layerName = "remove";
    
    @Override
    public String permissionName()
    {
        return null;
    }
    
    @Override
    public String messageKey()
    {
        return layerName;
    }
    
    @Override
    public List<String> getTabComplete(RootPlugin rootPlugin,int ordinal)
    {
        switch (ordinal)
        {
            case 0:
                return rootPlugin.getConfig().getStringList("controlled-worlds");
            default:
                return new ArrayList<>();
        }
    }
    
    @Override
    protected boolean run(int layer, RootPlugin rootPlugin,
                          DotDividedStringBuilder messageKey, CommandSender commandSender,
                          String[] args)
    {
        if (args.length - 1 != layer)
            return false;
        
        String worldName = args[layer];
    
        MessagesSender messagesSender = new MessagesSender(rootPlugin.getMessagesManager(),
                commandSender, new StringPair[]{
                StringPair.worldName(worldName),
                StringPair.senderName(commandSender.getName())
        });
    
        Configuration config = rootPlugin.getConfig();
        List<String> worlds = config.getStringList("controlled-worlds");
    
        if (!worlds.remove(worldName))
        {
            messagesSender.send(messageKey.append("no-such-controlled-world"));
            return true;
        }
        
        config.set("controlled-worlds", worlds);
        
        rootPlugin.saveConfig();
    
        messagesSender.send(messageKey.append("completed"));
        return true;
    }
}