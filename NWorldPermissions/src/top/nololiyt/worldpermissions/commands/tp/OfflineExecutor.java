package top.nololiyt.worldpermissions.commands.tp;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import top.nololiyt.worldpermissions.RootPlugin;
import top.nololiyt.worldpermissions.entities.DotDividedStringBuilder;
import top.nololiyt.worldpermissions.entities.StringPair;
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
        
        StringPair[] basePairs = new StringPair[]{
                StringPair.markName(markName),
                StringPair.worldName(worldName),
                StringPair.senderName(commandSender.getName())
        };
        
        if ((!rootPlugin.getConfig().getBoolean("offline-players-tracker.enabled")) ||
                rootPlugin.getConfig().getBoolean("offline-players-tracker.record-only"))
        {
            rootPlugin.getMessagesManager().sendMessage(
                    messageKey.append("tracker-not-enabled"), basePairs,commandSender);
            return true;
        }
        
        World world = Bukkit.getWorld(args[layer]);
        if (world == null)
        {
            rootPlugin.getMessagesManager().sendMessage(
                    messageKey.append("no-such-world"), basePairs,commandSender);
            return true;
        }
    
        Location location = rootPlugin.getMarksManager().getMark(markName);
        if (location == null)
        {
          rootPlugin.getMessagesManager().sendMessage(
                    messageKey.append("no-such-mark"), basePairs,commandSender);
            return true;
        }
    
        File dir = new File(
                rootPlugin.getDataFolder().getAbsolutePath(), "playersData");
        dir.mkdirs();
    
        int count = 0;
        for (File file : dir.listFiles())
        {
            YamlConfiguration configuration =
                    YamlConfiguration.loadConfiguration(file);
            if (configuration.getLocation("position").getWorld().equals(world))
            {
                configuration.set("position", location);
                configuration.set("changed", true);
                try
                {
                    configuration.save(file);
                    count++;
                }
                catch (IOException ex)
                {
                    ex.printStackTrace();
                }
            }
        }
        StringPair[] cPairs = new StringPair[]{
                StringPair.teleportedCount(String.valueOf(count)),
                StringPair.markName(markName),
                StringPair.worldName(worldName),
                StringPair.senderName(commandSender.getName())
        };
        rootPlugin.getMessagesManager().sendMessage(
                messageKey.append("completed"), cPairs,commandSender);
        return true;
    }
}
