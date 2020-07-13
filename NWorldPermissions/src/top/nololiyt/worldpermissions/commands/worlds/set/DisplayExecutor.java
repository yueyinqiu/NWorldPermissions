package top.nololiyt.worldpermissions.commands.worlds.set;

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

public class DisplayExecutor extends Executor
{
    protected final static String layerName = "display";
    
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
                        add("A WORLD");
                        add("B WORLD");
                        add("C WORLD");
                        add("D WORLD");
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
        if (args.length - 1 < layer)
            return false;
    
        String world = args[layer];
        String display = getDisplay(layer,args);
        
        MessagesSender messagesSender = new MessagesSender(
                rootPlugin.getMessagesManager(), commandSender, new StringPair[]{
                StringPair.worldName(world),
                StringPair.worldDisplayName(display),
                StringPair.senderName(commandSender.getName())
        });
        
        WorldsManager worldsManager = rootPlugin.getWorldsManager();
        WorldInfo worldInfo = worldsManager.getWorldInfo(world);
        
        try
        {
            worldsManager.set(world,
                    new WorldInfo(display, worldInfo.getThrust(), worldInfo.isControlled()));
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
    private String getDisplay(int layer, String[] args)
    {
        StringBuilder builder = new StringBuilder();
        for (int i = layer + 1; i < args.length; i++)
        {
            builder.append(args[i]);
            builder.append(' ');
        }
        builder.deleteCharAt(builder.length() - 1);
        return builder.toString();
    }
}