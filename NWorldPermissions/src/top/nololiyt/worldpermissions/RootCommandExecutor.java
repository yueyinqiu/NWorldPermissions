package top.nololiyt.worldpermissions;

import com.sun.org.apache.regexp.internal.REUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Statistic;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import javax.swing.text.Position;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

public class RootCommandExecutor implements CommandExecutor
{
    
    private RootPlugin rootPlugin;
    
    RootCommandExecutor(RootPlugin rootPlugin)
    {
        this.rootPlugin = rootPlugin;
    }
    
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args)
    {
        if (args.length == 0)
        {
            StringPair[] pairs = new StringPair[]{
                    StringPair.senderName(commandSender.getName())
            };
            commandSender.sendMessage(rootPlugin.getLanguageManager()
                    .getMessage("introduce", pairs));
    
            return true;
        }
        boolean result;
        switch (args[0].toLowerCase())
        {
            case "reload":
                result = reload(commandSender, args);
                break;
            case "help":
                result = showHelp(commandSender, args);
                break;
            case "tphere":
                result = tpHere(commandSender, args);
                break;
            case "setofflinehere":
                result = setOfflineHere(commandSender, args);
                break;
            default:
                result = false;
                break;
        }
        if (!result)
        {
            StringPair[] seeHelpPairs = new StringPair[]{
                    StringPair.senderName(commandSender.getName())
            };
            commandSender.sendMessage(rootPlugin.getLanguageManager().getMessage(
                    "see-help", seeHelpPairs));
        }
        return true;
    }
    private boolean setOfflineHere(CommandSender commandSender, String[] args)
    {
        if (!commandSender.hasPermission("nworldpermissions.admin"))
            return true;
    
        if (!rootPlugin.getConfig().getBoolean("enable-offline-controller"))
        {
            StringPair[] pairs = new StringPair[]{
                    StringPair.senderName(commandSender.getName()),
            };
            commandSender.sendMessage(rootPlugin.getLanguageManager().getMessage(
                    "setofflinehere.not-enabled", pairs));
            return true;
        }
    
        if (args.length != 2)
            return false;
    
        if (!(commandSender instanceof Player))
        {
            StringPair[] pairs = new StringPair[]{
                    StringPair.senderName(commandSender.getName()),
            };
            commandSender.sendMessage(rootPlugin.getLanguageManager().getMessage(
                    "setofflinehere.is-not-player-calling", pairs));
        }
    
        World world = Bukkit.getWorld(args[1]);
        if (world == null)
        {
            StringPair[] pairs = new StringPair[]{
                    StringPair.worldName(args[1]),
                    StringPair.senderName(commandSender.getName())
            };
            commandSender.sendMessage(rootPlugin.getLanguageManager().getMessage(
                    "setofflinehere.no-such-world", pairs));
            return true;
        }
    
        Location location = ((Player) commandSender).getLocation();
    
        File dir = new File(
                rootPlugin.getDataFolder().getAbsolutePath(), "playersData");
        dir.mkdirs();
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
                }
                catch (IOException ex)
                {
                    rootPlugin.getLogger().warning(ex.toString());
                }
            }
        }
    
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
            commandSender.sendMessage(rootPlugin.getLanguageManager().getMessage(
                    "tphere.is-not-player-calling", pairs));
        }
    
        World world = Bukkit.getWorld(args[1]);
        if (world == null)
        {
            StringPair[] pairs = new StringPair[]{
                    StringPair.worldName(args[1]),
                    StringPair.senderName(commandSender.getName())
            };
            commandSender.sendMessage(rootPlugin.getLanguageManager().getMessage(
                    "tphere.no-such-world", pairs));
            return true;
        }
    
        List<Player> players = world.getPlayers();
    
        StringPair[] playersPairs = new StringPair[]{
                StringPair.worldName(args[1]),
                StringPair.senderName(commandSender.getName()),
                null
        };
        Location location = ((Player) commandSender).getLocation();
        for (Player player : players)
        {
            playersPairs[2] = StringPair.playerName(player.getName());
        
            player.sendMessage(rootPlugin.getLanguageManager().getMessage(
                    "tphere.before-tp", playersPairs));
            if (!player.teleport(location))
            {
                commandSender.sendMessage(rootPlugin.getLanguageManager().getMessage(
                        "tphere.fail-to-teleport-someone", playersPairs));
            }
        }
        return true;
    }
    private boolean showHelp(CommandSender commandSender, String[] args)
    {
        if (commandSender.hasPermission("nworldpermissions.admin"))
        {
            try
            {
                commandSender.sendMessage(rootPlugin.getLanguageManager().getHelp(
                        Integer.parseUnsignedInt(args[1]) - 1, commandSender));
            }
            catch (NumberFormatException | ArrayIndexOutOfBoundsException e)
            {
                commandSender.sendMessage(rootPlugin.getLanguageManager().getHelp(
                        0, commandSender));
            }
        }
        return true;
    }
    private boolean reload(CommandSender commandSender, String[] args)
    {
        rootPlugin.reloadConfig();
        rootPlugin.getLanguageManager().reloadConfiguration();
        if (commandSender.hasPermission("nworldpermissions.admin"))
        {
            StringPair[] pairs = new StringPair[]{
                    StringPair.senderName(commandSender.getName())
            };
            commandSender.sendMessage(rootPlugin.getLanguageManager().getMessage(
                    "reload.reloaded", pairs));
        }
        return true;
    }
}