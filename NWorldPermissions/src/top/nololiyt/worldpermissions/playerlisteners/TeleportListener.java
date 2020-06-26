package top.nololiyt.worldpermissions.playerlisteners;

import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;
import top.nololiyt.worldpermissions.RootPlugin;
import top.nololiyt.worldpermissions.entities.DotDividedStringBuilder;
import top.nololiyt.worldpermissions.entities.StringPair;

import java.util.List;

public class TeleportListener implements Listener
{
    private RootPlugin rootPlugin;
    
    public TeleportListener(RootPlugin rootPlugin)
    {
        this.rootPlugin = rootPlugin;
    }
    
    @EventHandler
    public void onPlayerTeleport(PlayerTeleportEvent e)
    {
        World dest = e.getTo().getWorld();
        if (dest.equals(e.getFrom().getWorld()))
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
            rootPlugin.getMessagesManager().sendMessage(
                    new DotDividedStringBuilder(
                            "messages.to-players.when-teleport-to-controlled-worlds.teleported"),
                    pairs,player);
            return;
        }
        
        e.setCancelled(true);
        rootPlugin.getMessagesManager().sendMessage(
                new DotDividedStringBuilder(
                        "messages.to-players.when-teleport-to-controlled-worlds.denied"
                ), pairs,player);
    }
    
    private boolean worldIsControlled(World world)
    {
        List<String> worlds = rootPlugin.getConfig().getStringList("controlled-worlds");
        String destName = world.getName();
        return worlds.contains(destName);
    }
}
