package top.nololiyt.worldpermissions.commands.tp;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import top.nololiyt.worldpermissions.RootPlugin;
import top.nololiyt.worldpermissions.entitiesandtools.DotDividedStringBuilder;
import top.nololiyt.worldpermissions.entitiesandtools.MessagesSender;
import top.nololiyt.worldpermissions.entitiesandtools.OfflinePlayersPosition;
import top.nololiyt.worldpermissions.entitiesandtools.StringPair;
import top.nololiyt.worldpermissions.commands.Executor;

import java.io.File;
import java.io.IOException;

public class OfflineExecutor extends Executor
{
    protected final static String layerName = "offline";
    
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
        if (args.length - 1 != layer + 1)
            return false;
    
        String worldName = args[layer];
        String markName = args[layer + 1];
    
        MessagesSender messagesSender = new MessagesSender(rootPlugin.getMessagesManager(),
                commandSender, new StringPair[]{
                StringPair.markName(markName),
                StringPair.worldName(worldName),
                StringPair.senderName(commandSender.getName())
        });
    
        if ((!rootPlugin.getConfig().getBoolean("offline-players-tracker.enabled")) ||
                rootPlugin.getConfig().getBoolean("offline-players-tracker.record-only"))
        {
            messagesSender.send("tracker-not-enabled");
            return true;
        }
    
        World world = Bukkit.getWorld(args[layer]);
        if (world == null)
        {
            messagesSender.send("no-such-world");
            return true;
        }
    
        Location location = rootPlugin.getMarksManager().getMark(markName);
        if (location == null)
        {
            messagesSender.send("no-such-mark");
            return true;
        }
        
        long count = setPosition(
                new File(rootPlugin.getDataFolder().getAbsolutePath(), "playersData"),
                world, location);
        
        messagesSender.setArgs(new StringPair[]{
                StringPair.teleportedCount(String.valueOf(count)),
                StringPair.markName(markName),
                StringPair.worldName(worldName),
                StringPair.senderName(commandSender.getName())
        });
        messagesSender.send(messageKey.append("completed"));
        return true;
    }
    
    private long setPosition(File dir, World world, Location mark)
    {
        dir.mkdirs();
        long count = 0;
        for (File file : dir.listFiles())
        {
            OfflinePlayersPosition position = OfflinePlayersPosition.fromFile(file);
            if (!position.getPosition().getWorld().equals(world))
            {
                continue;
            }
            position = new OfflinePlayersPosition(mark, true);
            try
            {
                position.saveTo(file);
                count++;
            }
            catch (IOException ex)
            {
                ex.printStackTrace();
            }
        }
        return count;
    }
}