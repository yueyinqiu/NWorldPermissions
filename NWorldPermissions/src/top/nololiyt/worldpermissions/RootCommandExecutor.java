package top.nololiyt.worldpermissions;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import top.nololiyt.worldpermissions.entities.StringPair;
import top.nololiyt.worldpermissions.executors.CommandRouter;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class RootCommandExecutor implements CommandExecutor
{
    private RootPlugin rootPlugin;
    
    RootCommandExecutor(RootPlugin rootPlugin)
    {
        this.rootPlugin = rootPlugin;
        this.router = new CommandRouter(rootPlugin);
    }
    
    CommandRouter router;
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args)
    {
        return  router.RouteCommand(commandSender,command,label,args);
    }
    private boolean recordPosition(CommandSender commandSender, String[] args)
    {
        if (!commandSender.hasPermission("nworldpermissions.admin"))
            return true;
    
        if (!(commandSender instanceof Player))
        {
            StringPair[] pairs = new StringPair[]{
                    StringPair.senderName(commandSender.getName()),
            };
            commandSender.sendMessage(rootPlugin.getMessagesManager().getMessage(
                    "record-position.is-not-player-calling", pairs));
        }
        
        if (args.length != 2)
            return false;
        
        Player sender = ((Player) commandSender);
        try
        {
            File file = new File(
                    rootPlugin.getDataFolder().getAbsolutePath(), "positions.yml");
            if (!file.exists())
                file.createNewFile();
    
            YamlConfiguration configuration =
                    YamlConfiguration.loadConfiguration(file);
    
            configuration.set(args[1], sender.getLocation());
            configuration.save(file);
        }
        catch(IOException ex)
        {
            rootPlugin.getLogger().warning(ex.toString());
        }
        StringPair[] cPairs = new StringPair[]{
                StringPair.positionName(args[1]),
                StringPair.senderName(sender.getDisplayName())
        };
        commandSender.sendMessage(rootPlugin.getMessagesManager().getMessage(
                "record-position.completed", cPairs));
        return true;
    }
    private boolean setOfflineHere(CommandSender commandSender, String[] args)
    {
        if (!commandSender.hasPermission("nworldpermissions.admin"))
            return true;
    
        if (!(commandSender instanceof Player))
        {
            StringPair[] pairs = new StringPair[]{
                    StringPair.senderName(commandSender.getName()),
            };
            commandSender.sendMessage(rootPlugin.getMessagesManager().getMessage(
                    "set-offline-here.is-not-player-calling", pairs));
        }
        
        if (args.length != 2)
            return false;
        
        Player sender = (Player) commandSender;
    
        if ((!rootPlugin.getConfig().getBoolean("offline-players-controller.enabled")) ||
                rootPlugin.getConfig().getBoolean("offline-players-controller.record-only"))
        {
            StringPair[] pairs = new StringPair[]{
                    StringPair.senderName(sender.getDisplayName()),
            };
            commandSender.sendMessage(rootPlugin.getMessagesManager().getMessage(
                    "set-offline-here.not-enabled", pairs));
            return true;
        }
    
        World world = Bukkit.getWorld(args[1]);
        if (world == null)
        {
            StringPair[] pairs = new StringPair[]{
                    StringPair.worldName(args[1]),
                    StringPair.senderName(sender.getDisplayName())
            };
            commandSender.sendMessage(rootPlugin.getMessagesManager().getMessage(
                    "setofflinehere.no-such-world", pairs));
            return true;
        }
    
        Location location = sender.getLocation();
    
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
                try
                {
                    configuration.save(file);
                    count++;
                }
                catch (IOException ex)
                {
                    rootPlugin.getLogger().warning(ex.toString());
                }
            }
        }
        StringPair[] cPairs = new StringPair[]{
                StringPair.teleportedCount(String.valueOf(count)),
                StringPair.senderName(sender.getDisplayName())
        };
        commandSender.sendMessage(rootPlugin.getMessagesManager().getMessage(
                "set-offline-here.completed", cPairs));
        return true;
    }
    private boolean tpHere(CommandSender commandSender, String[] args)
    {
        if (!commandSender.hasPermission("nworldpermissions.admin"))
            return true;
    
        if (args.length != 2)
            return false;
    
        if (!(commandSender instanceof Player))
        {
            StringPair[] pairs = new StringPair[]{
                    StringPair.senderName(commandSender.getName()),
            };
            commandSender.sendMessage(rootPlugin.getMessagesManager().getMessage(
                    "tp-here.is-not-player-calling", pairs));
        }
        Player sender = (Player) commandSender;
        World world = Bukkit.getWorld(args[1]);
        if (world == null)
        {
            StringPair[] pairs = new StringPair[]{
                    StringPair.worldName(args[1]),
                    StringPair.senderName(sender.getDisplayName())
            };
            commandSender.sendMessage(rootPlugin.getMessagesManager().getMessage(
                    "tp-here.no-such-world", pairs));
            return true;
        }
    
        List<Player> players = world.getPlayers();
    
        StringPair[] playersPairs = new StringPair[]{
                StringPair.worldName(args[1]),
                StringPair.senderName(sender.getDisplayName()),
                null
        };
        Location location = sender.getLocation();
        
        int sCount = 0,fCount = 0;
        for (Player player : players)
        {
            playersPairs[2] = StringPair.playerName(player.getDisplayName());
        
            player.sendMessage(rootPlugin.getMessagesManager().getMessage(
                    "tp-here.before-tp", playersPairs));
            if (!player.teleport(location))
            {
                fCount++;
                commandSender.sendMessage(rootPlugin.getMessagesManager().getMessage(
                        "tp-here.fail-to-teleport-someone", playersPairs));
            }
            else
            {
                sCount++;
            }
        }
    
        StringPair[] cPairs = new StringPair[]{
                StringPair.teleportedCount(String.valueOf(sCount)),
                StringPair.unteleportedCount(String.valueOf(fCount)),
                StringPair.senderName(sender.getDisplayName())
        };
        commandSender.sendMessage(rootPlugin.getMessagesManager().getMessage(
                "tp-here.completed", cPairs));
        return true;
    }
    private boolean showHelp(CommandSender commandSender, String[] args)
    {
        if (commandSender.hasPermission("nworldpermissions.admin"))
        {
            try
            {
                commandSender.sendMessage(rootPlugin.getMessagesManager().getHelp(
                        Integer.parseUnsignedInt(args[1]) - 1, commandSender));
            }
            catch (NumberFormatException | ArrayIndexOutOfBoundsException e)
            {
                commandSender.sendMessage(rootPlugin.getMessagesManager().getHelp(
                        0, commandSender));
            }
        }
        return true;
    }
    
}