package top.nololiyt.worldpermissions.playerlisteners;

import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
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
import top.nololiyt.worldpermissions.entities.OfflinePlayersPosition;
import top.nololiyt.worldpermissions.entities.StringPair;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class JoinAndQuitListener implements Listener
{
    private RootPlugin rootPlugin;
    private OfflinePlayersPositionManager positionManager;
    
    public JoinAndQuitListener(RootPlugin rootPlugin)
    {
        this.rootPlugin = rootPlugin;
        this.positionManager = new OfflinePlayersPositionManager(rootPlugin);
    }
    
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e)
    {
        if ((!rootPlugin.getConfig().getBoolean("offline-players-tracker.enabled")))
            return;
    
    
        Player player = e.getPlayer();
        OfflinePlayersPosition playersPosition = positionManager.readPlayersPosition(player);
    
        if (playersPosition == null)
            return;
    
        int times = playersPosition.hasChanged() ?
                rootPlugin.getConfig().getInt("offline-players-tracker.teleport-times.position-changed") :
                rootPlugin.getConfig().getInt("offline-players-tracker.teleport-times.position-unchanged");
    
        for (int i = 0; i < times; i++)
        {
            player.teleport(playersPosition.getPosition());
        }
    }
    @EventHandler
    public void onPlayerQuitEvent(PlayerQuitEvent e)
    {
        if(!rootPlugin.getConfig().getBoolean("offline-players-tracker.enabled"))
            return;
        
        Player player = e.getPlayer();
        try
        {
            positionManager.savePlayersPosition(player,
                    new OfflinePlayersPosition(player.getLocation(), false));
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
    }
}

class OfflinePlayersPositionManager
{
    private RootPlugin rootPlugin;
    OfflinePlayersPositionManager(RootPlugin rootPlugin)
    {
        this.rootPlugin = rootPlugin;
    }
    
    OfflinePlayersPosition readPlayersPosition(OfflinePlayer player)
    {
        File file = new File(rootPlugin.getDataFolder().getAbsolutePath(), "playersData");
        file = new File(file.getAbsolutePath(), player.getUniqueId().toString() + ".yml");
        if (!file.exists())
            return null;
        
        YamlConfiguration yamlConfiguration = YamlConfiguration
                .loadConfiguration(file);
        Object position = yamlConfiguration.get("position");
        if(position == null)
            return null;
        
        boolean changed = yamlConfiguration.getBoolean("changed");
        return new OfflinePlayersPosition((Location) position, changed);
    }
    void savePlayersPosition(OfflinePlayer player, OfflinePlayersPosition position) throws IOException
    {
        File file = new File(rootPlugin.getDataFolder().getAbsolutePath(), "playersData");
        file = new File(file.getAbsolutePath(), player.getUniqueId().toString() + ".yml");
        
        YamlConfiguration yamlConfiguration = new YamlConfiguration();
        yamlConfiguration.set("position", position.getPosition());
        yamlConfiguration.set("changed", position.hasChanged());
        
        yamlConfiguration.save(file);
    }
}
