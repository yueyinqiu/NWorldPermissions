package top.nololiyt.worldpermissions.playerlisteners;

import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import top.nololiyt.worldpermissions.RootPlugin;
import top.nololiyt.worldpermissions.entitiesandtools.OfflinePlayersPosition;
import top.nololiyt.worldpermissions.entitiesandtools.WorldInfo;

import java.io.File;
import java.io.IOException;

public class JoinAndQuitListener implements Listener
{
    private RootPlugin rootPlugin;
    private OfflinePlayersPositionManager positionManager;
    
    public JoinAndQuitListener(RootPlugin rootPlugin)
    {
        this.rootPlugin = rootPlugin;
        this.positionManager = new OfflinePlayersPositionManager(rootPlugin);
    }
    
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerJoin( PlayerJoinEvent e)
    {
        if ((!rootPlugin.getConfig().getBoolean("offline-players-tracker.enabled")))
            return;
        
        Player player = e.getPlayer();
        OfflinePlayersPosition playersPosition = positionManager.readPlayersPosition(player);
    
        if (playersPosition == null)
        {
            String mark = rootPlugin.getConfig().getString("teleport-when-respawn.mark");
            if (mark == null)
                return;
    
            Location location = player.getLocation();
    
            World destWorld = location.getWorld();
            String destWorldName = destWorld.getName();
    
            boolean needTeleport;
            boolean noPermissionOnly = rootPlugin.getConfig().getBoolean(
                    "teleport-when-respawn.no-permission-only");
            if (!noPermissionOnly)
                needTeleport = true;
            else
            {
                WorldInfo worldInfo = rootPlugin.getWorldsManager()
                        .getWorldInfo(destWorldName);
    
                if (!worldInfo.isControlled())
                    needTeleport = false;
                else
                {
                    needTeleport = !player.hasPermission("nworldpermissions.forfreeto." + destWorldName);
                }
            }
            if (!needTeleport)
                return;
            location = rootPlugin.getLocalMarksManager().getMark(mark);
            if (location != null)
                player.teleport(location);
            return;
        }
    
        int times = playersPosition.hasChanged() ?
                rootPlugin.getConfig().getInt("offline-players-tracker.teleport-times.position-changed") :
                rootPlugin.getConfig().getInt("offline-players-tracker.teleport-times.position-unchanged");
    
        for (int i = 0; i < times; i++)
        {
            player.teleport(playersPosition.getPosition());
        }
    }
    
    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerQuitEvent(PlayerQuitEvent e)
    {
        if (!rootPlugin.getConfig().getBoolean("offline-players-tracker.enabled"))
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
    
        return OfflinePlayersPosition.fromFile(file);
    }
    
    void savePlayersPosition(OfflinePlayer player, OfflinePlayersPosition position) throws IOException
    {
        File file = new File(rootPlugin.getDataFolder().getAbsolutePath(), "playersData");
        file = new File(file.getAbsolutePath(), player.getUniqueId().toString() + ".yml");
        
        position.saveTo(file);
    }
}
