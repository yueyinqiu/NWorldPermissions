package top.nololiyt.worldpermissions;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import top.nololiyt.worldpermissions.entities.DotDividedStringBuilder;
import top.nololiyt.worldpermissions.entities.StringPair;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class PlayerListener implements Listener
{
    private RootPlugin rootPlugin;
    
    PlayerListener(RootPlugin rootPlugin)
    {
        this.rootPlugin = rootPlugin;
    }
    
    @EventHandler
    public void onPlayerLogin(PlayerJoinEvent e)
    {
        if ((!rootPlugin.getConfig().getBoolean("offline-players-tracker.enabled")) ||
                rootPlugin.getConfig().getBoolean("offline-players-tracker.record-only"))
        {
            return;
        }
        
        Player player = e.getPlayer();
        File file = new File(
                rootPlugin.getDataFolder().getAbsolutePath(), "playersData");
        file.mkdirs();
        file = new File(file.getAbsolutePath(), player.getUniqueId().toString() + ".yml");
        if (!file.exists())
            return;
    
        YamlConfiguration yamlConfiguration = YamlConfiguration
                .loadConfiguration(file);
        Object location = yamlConfiguration.get("position");
        if (location != null)
            player.teleport((Location) location);
    }
    @EventHandler
    public void onPlayerQuitEvent(PlayerQuitEvent e)
    {
        if(!rootPlugin.getConfig().getBoolean("offline-players-tracker.enabled"))
        {
            return;
        }
        Player player = e.getPlayer();
        try
        {
            File file = new File(
                    rootPlugin.getDataFolder().getAbsolutePath(), "playersData");
            file.mkdirs();
            file = new File(file.getAbsolutePath(), player.getUniqueId().toString() + ".yml");
            if (!file.exists())
                file.createNewFile();
    
            YamlConfiguration yamlConfiguration = YamlConfiguration
                    .loadConfiguration(file);
            yamlConfiguration.set("position", player.getLocation());
            yamlConfiguration.save(file);
        }
        catch (IOException ex)
        {
            rootPlugin.getLogger().warning(ex.toString());
        }
    }
    
    @EventHandler
    public void onPlayerTeleport(PlayerTeleportEvent e)
    {
        World dest = e.getTo().getWorld();
        if(dest.equals(e.getFrom().getWorld()))
        {
            return;
        }
        
        String destName = dest.getName();
        
        Player player = e.getPlayer();
        
        if (!worldIsControlled(dest))
        {
            return;
        }
    
        StringPair[] pairs = new StringPair[]{
                StringPair.playerName(player.getDisplayName())
        };
        
        if (player.hasPermission("nworldpermissions.forfreeto." + destName))
        {
            player.sendMessage(rootPlugin.getMessagesManager().getMessage(
                    new DotDividedStringBuilder(
                            "messages.to-players.when-teleport-to-controlled-worlds.teleported"),
                    pairs));
            return;
        }
        
        e.setCancelled(true);
        player.sendMessage(rootPlugin.getMessagesManager().getMessage(
                new DotDividedStringBuilder(
                        "messages.to-players.when-teleport-to-controlled-worlds.denied"),
                pairs));
    }
    
    private boolean worldIsControlled(World world)
    {
        List<String> worlds = rootPlugin.getConfig().getStringList("controlled-worlds");
        String destName = world.getName();
        return worlds.contains(destName);
    }
}
