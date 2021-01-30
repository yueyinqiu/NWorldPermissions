package top.nololiyt.worldpermissions.playerlisteners;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.util.Vector;
import top.nololiyt.worldpermissions.RootPlugin;
import top.nololiyt.worldpermissions.configurationmanagers.WorldsManager;
import top.nololiyt.worldpermissions.entitiesandtools.DotDividedStringBuilder;
import top.nololiyt.worldpermissions.entitiesandtools.MessagesSender;
import top.nololiyt.worldpermissions.entitiesandtools.StringPair;
import top.nololiyt.worldpermissions.entitiesandtools.WorldInfo;

public class RespawnListener implements Listener
{
    private RootPlugin rootPlugin;
    
    public RespawnListener(RootPlugin rootPlugin)
    {
        this.rootPlugin = rootPlugin;
    }
    
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerRespawn(PlayerRespawnEvent e)
    {
        String mark = rootPlugin.getConfig().getString("teleport-when-respawn.mark");
        if (mark == null)
            return;
    
        Location location = e.getRespawnLocation();
    
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
                Player player = e.getPlayer();
                needTeleport = !player.hasPermission("nworldpermissions.forfreeto." + destWorldName);
            }
        }
        if (!needTeleport)
            return;
        location = rootPlugin.getLocalMarksManager().getMark(mark);
        if (location != null)
            e.setRespawnLocation(location);
    }
}