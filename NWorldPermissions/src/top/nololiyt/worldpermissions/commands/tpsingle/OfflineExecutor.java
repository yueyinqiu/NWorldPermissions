package top.nololiyt.worldpermissions.commands.tpsingle;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import top.nololiyt.worldpermissions.MarksAPILinker;
import top.nololiyt.worldpermissions.RootPlugin;
import top.nololiyt.worldpermissions.commands.Executor;
import top.nololiyt.worldpermissions.entitiesandtools.DotDividedStringBuilder;
import top.nololiyt.worldpermissions.entitiesandtools.MessagesSender;
import top.nololiyt.worldpermissions.entitiesandtools.OfflinePlayersPosition;
import top.nololiyt.worldpermissions.entitiesandtools.StringPair;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class OfflineExecutor extends Executor
{
    protected final static String layerName = "offline";
    
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
                for (OfflinePlayer player : Bukkit.getOfflinePlayers())
                {
                    if (!player.isOnline())
                        result.add(player.getName());
                }
                return result;
            case 1:
                MarksAPILinker marksAPI = rootPlugin.getMarksAPILinker();
                return marksAPI == null ?
                        new ArrayList<>(rootPlugin.getLocalMarksManager().allMarksName()) :
                        marksAPI.getMarksAPI().getAllMarksKey(() -> null);
            case 2:
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
    
        MessagesSender messagesSender = new MessagesSender(rootPlugin.getMessagesManager(),
                commandSender, new StringPair[]{
                StringPair.playerName(arguments.playerName),
                StringPair.markName(arguments.markName),
                StringPair.worldName(arguments.worldName),
                StringPair.senderName(commandSender.getName())
        });
    
        if ((!rootPlugin.getConfig().getBoolean("offline-players-tracker.enabled")) ||
                rootPlugin.getConfig().getBoolean("offline-players-tracker.record-only"))
        {
            messagesSender.send(messageKey.append("tracker-not-enabled"));
            return true;
        }
    
        if (Bukkit.getPlayer(arguments.playerName) != null)
        {
            messagesSender.send(messageKey.append("player-is-online"));
            return true;
        }
        OfflinePlayer player = null;
        for (OfflinePlayer playerInArray : Bukkit.getOfflinePlayers())
        {
            if (arguments.playerName.equals(playerInArray.getName()))
            {
                player = playerInArray;
                break;
            }
        }
        if (player == null)
        {
            messagesSender.send(messageKey.append("no-such-player"));
            return true;
        }
    
        World world = null;
        if (!arguments.worldName.isEmpty())
        {
            world = Bukkit.getWorld(arguments.worldName);
            if (world == null)
            {
                messagesSender.send(messageKey.append("no-such-world"));
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
    
        try
        {
            boolean suc = setPosition(
                    new File(rootPlugin.getDataFolder().getAbsolutePath(), "playersData"),
                    player, world, location);
            if (suc)
                messagesSender.send(messageKey.append("completed"));
            else
                messagesSender.send(messageKey.append("player-not-in-world"));
        }
        catch (IOException e)
        {
            e.printStackTrace();
            messagesSender.send(messageKey.append("failed"));
        }
        return true;
    }
    
    class Arguments
    {
        String playerName;
        String worldName = "";
        String markName;
    }
    
    private Arguments getArguments(String[] args, int layer)
    {
        if (args.length - 1 < layer + 1 ||
                args.length - 1 > layer + 2)
        {
            return null;
        }
        
        Arguments arguments = new Arguments();
        arguments.playerName = args[layer];
        arguments.markName = args[layer + 1];
        
        if (args.length - 1 == layer + 1)
            return arguments;
        
        arguments.worldName = args[layer + 2];
        return arguments;
    }
    
    private boolean setPosition(File dir, OfflinePlayer player, World world, Location mark)
            throws IOException
    {
        dir.mkdirs();
        File file = new File(dir.getAbsolutePath(), player.getUniqueId().toString() + ".yml");
        if (!file.exists())
            return false;
        
        OfflinePlayersPosition position = OfflinePlayersPosition.fromFile(file);
        if (world != null && (!position.getPosition().getWorld().equals(world)))
        {
            return false;
        }
        new OfflinePlayersPosition(mark, true).saveTo(file);
        return true;
    }
}