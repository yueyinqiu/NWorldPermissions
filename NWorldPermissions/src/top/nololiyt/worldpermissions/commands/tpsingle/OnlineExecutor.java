package top.nololiyt.worldpermissions.commands.tpsingle;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import top.nololiyt.worldpermissions.MarksAPILinker;
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
    public List<String> getTabComplete(RootPlugin rootPlugin, int ordinal)
    {
        switch (ordinal)
        {
            case 0:
                List<String> result = new ArrayList<>();
                for (Player player : Bukkit.getOnlinePlayers())
                {
                    result.add(player.getName());
                }
                return result;
            case 1:
                MarksAPILinker marksAPI = rootPlugin.getMarksAPILinker();
                return marksAPI == null ?
                        new ArrayList<>(rootPlugin.getLocalMarksManager().allMarksName()) :
                        marksAPI.getMarksAPI().getAllMarksKey(() -> null);
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
            case 3:
                List<String> wResult = new ArrayList<>();
                for (World world : Bukkit.getWorlds())
                {
                    wResult.add(world.getName());
                }
                return wResult;
            default:
                return new ArrayList<>();
        }
    }
    
    @Override
    protected boolean run(int layer, RootPlugin rootPlugin,
                          DotDividedStringBuilder messageKey, CommandSender commandSender,
                          String[] args)
    {
        Arguments arguments = getArguments(args, layer);
        if (arguments == null)
            return false;
    
        String senderName = commandSender.getName();
    
        MessagesManager messagesManager = rootPlugin.getMessagesManager();
        MessagesSender messagesSender = new MessagesSender(
                messagesManager, commandSender, new StringPair[]{
                null, // for teleported times
                null, // for player name
                StringPair.markName(arguments.markName),
                StringPair.worldName(arguments.worldName),
                StringPair.senderName(senderName),
                StringPair.teleportationTimes(arguments.timesString)
        });
    
        Player player = Bukkit.getPlayer(arguments.playerName);
        if(player == null)
        {
            messagesSender.getArgs()[1] = StringPair.playerName(arguments.playerName);
            messagesSender.send(messageKey.append("no-such-player"));
            return true;
        }
        else
        {
            messagesSender.getArgs()[1] = StringPair.playerName(player.getDisplayName());
        }
        
        if(!arguments.worldName.isEmpty())
        {
            if (Bukkit.getWorld(arguments.worldName) == null)
            {
                messagesSender.send(messageKey.append("no-such-world"));
                return true;
            }
            if (!player.getWorld().getName().equals(arguments.worldName))
            {
                messagesSender.send(messageKey.append("player-not-in-world"));
                return true;
            }
        }
    
        MarksAPILinker marksAPI = rootPlugin.getMarksAPILinker();
        Location location = marksAPI == null ?
                rootPlugin.getLocalMarksManager().getMark(arguments.markName) :
                marksAPI.getMarksAPI().getMark(arguments.markName, () -> commandSender);
        if (location == null)
        {
            messagesSender.send(messageKey.append("no-such-mark"));
            return true;
        }
    
        teleport(player, location, arguments.timesInt,
                new DotDividedStringBuilder(messageKey).append("completed"),
                messageKey.append("failed"),
                messagesSender,
                new MessagesSender(messagesManager, player, new StringPair[]{
                        null, // for teleported times
                        StringPair.playerName(player.getDisplayName()),
                        StringPair.markName(arguments.markName),
                        StringPair.worldDisplayName(
                                rootPlugin.getWorldsManager()
                                        .getWorldInfo(arguments.worldName).getDisplay()
                        ),
                        StringPair.senderName(senderName),
                        StringPair.teleportationTimes(arguments.timesString)
                }));
        return true;
    }
    
    class Arguments
    {
        int timesInt = 1;
        String timesString = "1";
        String playerName;
        String worldName = "";
        String markName;
    }
    
    private Arguments getArguments(String[] args, int layer)
    {
        if (args.length - 1 < layer + 1 ||
                args.length - 1 > layer + 3)
        {
            return null;
        }
        Arguments arguments = new Arguments();
        arguments.playerName = args[layer];
        arguments.markName = args[layer + 1];
    
        if (args.length - 1 == layer + 1)
            return arguments;
    
        arguments.timesString = args[layer + 2];
        try
        {
            arguments.timesInt = Integer.parseInt(arguments.timesString);
        }
        catch (NumberFormatException ex)
        {
            return null;
        }
    
        if (args.length - 1 == layer + 2)
            return arguments;
    
        arguments.worldName = args[layer + 3];
        return arguments;
    }
    
    private boolean teleport(Player player, Location mark, int times,
                             DotDividedStringBuilder successMessageKey,
                             DotDividedStringBuilder failMessageKey,
                             MessagesSender toSender, MessagesSender toPlayer)
    {
        int realTimes = 0;
        for (int i = 0; i < times; i++)
        {
            realTimes += player.teleport(mark) ? 1 : 0;
        }
        toSender.getArgs()[0] = StringPair.teleportedTimes(String.valueOf(realTimes));
        toPlayer.getArgs()[0] = StringPair.teleportedTimes(String.valueOf(realTimes));
        
        DotDividedStringBuilder teleportMessage = new DotDividedStringBuilder(
                "messages.to-players.when-teleported-by-tpsingle-online");
        toPlayer.send(teleportMessage);
    
        if (realTimes < times)
        {
            toSender.send(failMessageKey);
            return false;
        }
        toSender.send(successMessageKey);
        return true;
    }
}