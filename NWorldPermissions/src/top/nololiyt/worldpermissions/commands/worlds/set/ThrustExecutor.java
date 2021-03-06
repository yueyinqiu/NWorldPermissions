package top.nololiyt.worldpermissions.commands.worlds.set;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import top.nololiyt.worldpermissions.RootPlugin;
import top.nololiyt.worldpermissions.commands.Executor;
import top.nololiyt.worldpermissions.configurationmanagers.WorldsManager;
import top.nololiyt.worldpermissions.entitiesandtools.WorldInfo;
import top.nololiyt.worldpermissions.entitiesandtools.DotDividedStringBuilder;
import top.nololiyt.worldpermissions.entitiesandtools.MessagesSender;
import top.nololiyt.worldpermissions.entitiesandtools.StringPair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ThrustExecutor extends Executor
{
    protected final static String layerName = "thrust";
    
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
                return rootPlugin.getWorldsManager().getAllWorldName();
            case 1:
                return new ArrayList<String>()
                {
                    {
                        add("0");
                        add("0.4");
                        add("0.7");
                        add("2.33");
                    }
                };
            default:
                return new ArrayList<>();
        }
    }
    
    @Override
    protected boolean run(int layer, RootPlugin rootPlugin,
                          DotDividedStringBuilder messageKey, CommandSender commandSender,
                          String[] args)
    {
        if (args.length - 1 != layer + 1)
            return false;
    
        String world = args[layer];
        String sThrust = args[layer + 1];
        double thrust;
        try
        {
            thrust = Double.parseDouble(sThrust);
        }
        catch (NumberFormatException e)
        {
            return false;
        }
        
        MessagesSender messagesSender = new MessagesSender(
                rootPlugin.getMessagesManager(), commandSender, new StringPair[]{
                StringPair.worldName(world),
                StringPair.thrust(sThrust),
                StringPair.senderName(commandSender.getName())
        });
        
        WorldsManager worldsManager = rootPlugin.getWorldsManager();
        WorldInfo worldInfo = worldsManager.getWorldInfo(world);
        try
        {
            worldsManager.set(world,
                    new WorldInfo(worldInfo.getDisplay(), thrust, worldInfo.isControlled()));
        }
        catch (IOException e)
        {
            e.printStackTrace();
            messagesSender.send(messageKey.append("failed"));
            return true;
        }
        
        if (worldsManager.existedInGame(world))
            messagesSender.send(messageKey.append("completed"));
        else
            messagesSender.send(messageKey.append("completed-but-no-such-world"));
        
        return true;
    }
}