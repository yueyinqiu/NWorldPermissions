package top.nololiyt.worldpermissions.commands.tp;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import top.nololiyt.worldpermissions.RootPlugin;
import top.nololiyt.worldpermissions.commands.Executor;
import top.nololiyt.worldpermissions.configurationmanagers.MessagesManager;
import top.nololiyt.worldpermissions.entitiesandtools.DotDividedStringBuilder;
import top.nololiyt.worldpermissions.entitiesandtools.MessagesSender;
import top.nololiyt.worldpermissions.entitiesandtools.StringPair;

import java.util.ArrayList;
import java.util.List;

public class OnlineExecutor extends Executor
{
    protected final static String layerName = "online";
    
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
                List<String> result = new ArrayList<>();
                for (World world : Bukkit.getWorlds())
                {
                    result.add(world.getName());
                }
                return result;
            case 1:
                return new ArrayList<>(rootPlugin.getMarksManager().allMarksName());
            case 2:
                return new ArrayList<String>()
                {
                    {
                        add("1");
                        add("2");
                        add("3");
                        add("4");
                        add("5");
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
        Times times = getTimes(args, layer);
        if (times == null)
            return false;
    
        String worldName = args[layer];
        String markName = args[layer + 1];
    
        String senderName = commandSender.getName();
    
        MessagesManager messagesManager = rootPlugin.getMessagesManager();
        MessagesSender messagesSender = new MessagesSender(
                messagesManager, commandSender, new StringPair[]{
                StringPair.markName(markName),
                StringPair.worldName(worldName),
                StringPair.senderName(senderName),
                StringPair.teleportationTimes(times.stringValue)
        });
    
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
    
        List<Player> players = world.getPlayers();
    
        messagesSender.setArgs(new StringPair[]{
                null, null,
                StringPair.markName(markName),
                StringPair.worldName(worldName),
                StringPair.senderName(senderName),
                StringPair.teleportationTimes(times.stringValue)
        });
    
        DotDividedStringBuilder messageKeyCopy = new DotDividedStringBuilder(messageKey);
        messageKey.append("failed-to-teleport-someone");
    
        TeleportCount count =
                teleport(players, location, times.intValue, messageKey, messagesSender,
                        new MessagesSender(messagesManager, null, new StringPair[]{
                                null,
                                StringPair.markName(markName),
                                StringPair.worldDisplayName(
                                        rootPlugin.getWorldsManager()
                                                .getWorldInfo(worldName).getDisplay()
                                ),
                                StringPair.senderName(senderName),
                                StringPair.teleportationTimes(times.stringValue)
                        }));
    
        messagesSender.setArgs(new StringPair[]{
                StringPair.teleportedCount(String.valueOf(count.success)),
                StringPair.unteleportedCount(String.valueOf(count.failure)),
                StringPair.markName(markName),
                StringPair.worldName(worldName),
                StringPair.senderName(senderName),
                StringPair.teleportationTimes(times.stringValue)
        });
        messagesSender.send(messageKeyCopy.append("completed"));
        return true;
    }
    
    class Times
    {
        int intValue;
        String stringValue;
    }
    
    private Times getTimes(String[] args, int layer)
    {
        Times times = new Times();
        
        if (args.length - 1 != layer + 1)
        {
            if (args.length - 1 != layer + 2)
                return null;
            
            times.stringValue = args[layer + 2];
            try
            {
                times.intValue = Integer.parseInt(times.stringValue);
            }
            catch (NumberFormatException ex)
            {
                return null;
            }
        }
        else
        {
            times.intValue = 1;
            times.stringValue = "1";
        }
        return times;
    }
    
    class TeleportCount
    {
        int success;
        int failure;
    }
    
    private TeleportCount teleport(List<Player> players, Location mark,
                                   int times, DotDividedStringBuilder failMessageKey,
                                   MessagesSender toSender, MessagesSender toPlayers)
    {
        TeleportCount count = new TeleportCount();
        count.success = 0;
        count.failure = 0;
        DotDividedStringBuilder teleportMessage = new DotDividedStringBuilder(
                "messages.to-players.when-teleported-by-tp-online.before-teleport");
        for (Player player : players)
        {
            toPlayers.getArgs()[0] = toSender.getArgs()[0]
                    = StringPair.playerName(player.getDisplayName());
            toPlayers.setTarget(player);
            toPlayers.send(teleportMessage);
    
            int realTimes = 0;
            for (int i = 0; i < times; i++)
            {
                realTimes += player.teleport(mark) ? 1 : 0;
            }
            if (realTimes < times)
            {
                count.failure++;
                toSender.getArgs()[1] = StringPair.teleportedTimes(String.valueOf(realTimes));
                toSender.send(failMessageKey);
                continue;
            }
            count.success++;
        }
        return count;
    }
}