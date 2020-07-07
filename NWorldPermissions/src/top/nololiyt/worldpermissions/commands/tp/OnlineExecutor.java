package top.nololiyt.worldpermissions.commands.tp;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import top.nololiyt.worldpermissions.RootPlugin;
import top.nololiyt.worldpermissions.commands.Executor;
import top.nololiyt.worldpermissions.entities.DotDividedStringBuilder;
import top.nololiyt.worldpermissions.entities.StringPair;

import java.util.List;

public class OnlineExecutor extends Executor
{
    protected final static String layerName = "online";
    
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
        int times;
        String sTimes;
        if (args.length - 1 != layer + 1)
        {
            if (args.length - 1 != layer + 2)
            {
                return false;
            }
            sTimes = args[layer + 2];
            try
            {
                times = Integer.parseInt(sTimes);
            }
            catch (NumberFormatException ex)
            {
                return false;
            }
        }
        else
        {
            times = 1;
            sTimes = "1";
        }
    
        String worldName = args[layer];
        String markName = args[layer + 1];
        
        String senderName = commandSender.getName();
        
        StringPair[] basePairs = new StringPair[]{
                StringPair.markName(markName),
                StringPair.worldName(worldName),
                StringPair.senderName(senderName),
                StringPair.teleportationTimes(sTimes)
        };
    
        World world = Bukkit.getWorld(args[layer]);
        if (world == null)
        {
            rootPlugin.getMessagesManager().sendMessage(
                    commandSender, messageKey.append("no-such-world"), basePairs);
            return true;
        }
    
        Location location = rootPlugin.getMarksManager().getMark(markName);
        if (location == null)
        {
            rootPlugin.getMessagesManager().sendMessage(
                    commandSender, messageKey.append("no-such-mark"), basePairs);
            return true;
        }
        
        List<Player> players = world.getPlayers();
    
        StringPair[] playersPairs = new StringPair[]{
                null,
                StringPair.markName(markName),
                StringPair.worldName(worldName),
                StringPair.senderName(senderName),
                StringPair.teleportationTimes(sTimes)
        };
    
        StringPair[] playersFailPairs = new StringPair[]{
                null, null,
                StringPair.markName(markName),
                StringPair.worldName(worldName),
                StringPair.senderName(senderName),
                StringPair.teleportationTimes(sTimes)
        };
        
        int sCount = 0, fCount = 0;
        DotDividedStringBuilder cKey = new DotDividedStringBuilder(messageKey);
        messageKey.append("failed-to-teleport-someone");
        for (Player player : players)
        {
            playersFailPairs[0] = playersPairs[0]
                    = StringPair.playerName(player.getDisplayName());
    
    
            rootPlugin.getMessagesManager().sendMessage(
                    player, new DotDividedStringBuilder(
                            "messages.to-players.when-teleported-by-tp-online.before-teleport"), playersPairs
            );
    
            int realTimes = 0;
            for (int i = 0; i < times; i++)
            {
                realTimes += player.teleport(location) ? 1 : 0;
            }
    
            if (realTimes < times)
            {
                fCount++;
                playersFailPairs[1] = StringPair.teleportedTimes(String.valueOf(realTimes));
                rootPlugin.getMessagesManager().sendMessage(commandSender, messageKey, playersFailPairs);
            }
            else
            {
                sCount++;
            }
        }
    
        StringPair[] cPairs = new StringPair[]{
                StringPair.teleportedCount(String.valueOf(sCount)),
                StringPair.unteleportedCount(String.valueOf(fCount)),
                StringPair.markName(markName),
                StringPair.worldName(worldName),
                StringPair.senderName(senderName),
                StringPair.teleportationTimes(sTimes)
        };
        rootPlugin.getMessagesManager().sendMessage(
                commandSender, cKey.append("completed"), cPairs);
        return true;
    }
}
