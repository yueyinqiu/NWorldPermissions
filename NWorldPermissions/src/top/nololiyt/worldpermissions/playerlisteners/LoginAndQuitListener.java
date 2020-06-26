package top.nololiyt.worldpermissions.playerlisteners;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import top.nololiyt.worldpermissions.RootPlugin;
import top.nololiyt.worldpermissions.entities.DotDividedStringBuilder;
import top.nololiyt.worldpermissions.entities.StringPair;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class LoginAndQuitListener implements Listener
{
    private RootPlugin rootPlugin;
    
    public LoginAndQuitListener(RootPlugin rootPlugin)
    {
        this.rootPlugin = rootPlugin;
    }
    
    @EventHandler
    public void onPlayerLogin(PlayerJoinEvent e)
    {
        if ((!rootPlugin.getConfig().getBoolean("offline-players-tracker.enabled")))
            return;
    
    
        Player player = e.getPlayer();
        File file = new File(
                rootPlugin.getDataFolder().getAbsolutePath(), "playersData");
        file.mkdirs();
        file = new File(file.getAbsolutePath(), player.getUniqueId().toString() + ".yml");
        if (!file.exists())
            return;
    
        YamlConfiguration yamlConfiguration = YamlConfiguration
                .loadConfiguration(file);
        Object position = yamlConfiguration.get("position");
        if (position == null) return;
        Location location = (Location) position;
    
        int times = yamlConfiguration.getBoolean("changed", false) ?
                rootPlugin.getConfig().getInt("offline-players-tracker.teleport-times.position-changed") :
                rootPlugin.getConfig().getInt("offline-players-tracker.teleport-times.position-unchanged");
    
        for (int i = 0; i < times; i++)
            player.teleport(location);
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
            yamlConfiguration.set("changed", false);
            yamlConfiguration.save(file);
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
    }
}
