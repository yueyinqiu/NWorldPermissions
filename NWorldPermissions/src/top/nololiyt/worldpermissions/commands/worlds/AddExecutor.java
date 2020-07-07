package top.nololiyt.worldpermissions.commands.worlds;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.Configuration;
import top.nololiyt.worldpermissions.RootPlugin;
import top.nololiyt.worldpermissions.entitiesandtools.DotDividedStringBuilder;
import top.nololiyt.worldpermissions.entitiesandtools.MessagesSender;
import top.nololiyt.worldpermissions.entitiesandtools.StringPair;
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
    
        String worldName = args[layer];
        MessagesSender messagesSender = new MessagesSender(
                rootPlugin.getMessagesManager(), commandSender, new StringPair[]{
                StringPair.worldName(worldName),
                StringPair.senderName(commandSender.getName())
        });
    
        Configuration config = rootPlugin.getConfig();
        List<String> worlds = config.getStringList("controlled-worlds");
    
        if (worlds.contains(worldName))
        {
            messagesSender.send(messageKey.append("already-controlled"));
            return true;
        }
    
        worlds.add(worldName);
        config.set("controlled-worlds", worlds);
        rootPlugin.saveConfig();
    
        if (existed(worldName))
            messagesSender.send(messageKey.append("completed"));
        
        messagesSender.send(messageKey.append("completed-but-no-such-world"));
        return true;
    }
    
    /**
     * 世界是否存在（指的是是否存在，而不是是否受控）
     * @return
     */
    private boolean existed(String worldName)
    {
        for (World world : Bukkit.getWorlds())
        {
            if (world.getName().equals(worldName))
            {
                return true;
            }
        }
        return false;
    }
}