package top.nololiyt.worldpermissions.commands.tp;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import top.nololiyt.worldpermissions.RootPlugin;
import top.nololiyt.worldpermissions.commands.Executor;
import top.nololiyt.worldpermissions.entities.DotDividedStringBuilder;
import top.nololiyt.worldpermissions.entities.StringPair;

import java.io.File;
import java.io.IOException;
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
        if (args.length - 1 != layer + 1)
            return false;
    
        String worldName = args[layer];
        String markName = args[layer + 1];
        
        StringPair[] basePairs = new StringPair[]{
                StringPair.markName(markName),
                StringPair.worldName(worldName),
                StringPair.senderName(commandSender.getName())
        };
        
        World world = Bukkit.getWorld(args[layer]);
        if (world == null)
        {
            commandSender.sendMessage(rootPlugin.getMessagesManager().getMessage(
                    messageKey.append("no-such-world"), basePairs));
            return true;
        }
    
        Location location = rootPlugin.getMarksManager().getMark(markName);
        if (location == null)
        {
            commandSender.sendMessage(rootPlugin.getMessagesManager().getMessage(
                    messageKey.append("no-such-mark"), basePairs));
            return true;
        }
    
        List<Player> players = world.getPlayers();
    
        StringPair[] playersPairs = new StringPair[]{
                null,
                StringPair.markName(markName),
                StringPair.worldName(worldName),
                StringPair.senderName(commandSender.getName())
        };
        
        int sCount = 0,fCount = 0;
        for (Player player : players)
        {
            playersPairs[0] = StringPair.playerName(player.getDisplayName());
        
            player.sendMessage(rootPlugin.getMessagesManager().getMessage(
                    new DotDividedStringBuilder(
                            "messages.to-players.when-teleported-by-tp-online.before-teleport"),
                    playersPairs));
            if (!player.teleport(location))
            {
                fCount++;
                commandSender.sendMessage(rootPlugin.getMessagesManager().getMessage(
                        messageKey.append("failed-to-teleport-someone"), playersPairs));
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
                StringPair.senderName(commandSender.getName())
        };
        commandSender.sendMessage(rootPlugin.getMessagesManager().getMessage(
                messageKey.append("completed"), cPairs));
        return true;
    }
}
