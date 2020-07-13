package top.nololiyt.worldpermissions.commands.worlds;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import top.nololiyt.worldpermissions.RootPlugin;
import top.nololiyt.worldpermissions.configurationmanagers.WorldsManager;
import top.nololiyt.worldpermissions.entitiesandtools.DotDividedStringBuilder;
import top.nololiyt.worldpermissions.entitiesandtools.MessagesSender;
import top.nololiyt.worldpermissions.entitiesandtools.StringPair;
import top.nololiyt.worldpermissions.commands.Executor;
import top.nololiyt.worldpermissions.entitiesandtools.WorldInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ControlExecutor extends Executor
{
    protected final static String layerName = "control";
    
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
    public List<String> getTabComplete(RootPlugin rootPlugin, int ordinal)
    {
        switch (ordinal)
        {
            case 0:
                WorldsManager worldsManager = rootPlugin.getWorldsManager();
                List<String> result = worldsManager.getAllWorldName();
                result.removeAll(worldsManager.allControlledWorldsName());
                return result;
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
        MessagesSender messagesSender = new MessagesSender(
                rootPlugin.getMessagesManager(), commandSender, new StringPair[]{
                StringPair.worldName(worldName),
                StringPair.senderName(commandSender.getName())
        });
    
        WorldsManager worldsManager = rootPlugin.getWorldsManager();
    
        WorldInfo world = worldsManager.getWorldInfo(worldName);
        if (world.isControlled())
        {
            messagesSender.send(messageKey.append("already-controlled"));
            return true;
        }
        
        worldsManager.set(worldName,
                new WorldInfo(world.getDisplay(), world.getThrust(), true));
    
        if (worldsManager.existedInGame(worldName))
            messagesSender.send(messageKey.append("completed"));
        else
            messagesSender.send(messageKey.append("completed-but-no-such-world"));
        return true;
    }
}