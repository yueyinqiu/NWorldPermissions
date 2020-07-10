package top.nololiyt.worldpermissions.playerlisteners;

import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;
import top.nololiyt.worldpermissions.RootPlugin;
import top.nololiyt.worldpermissions.entitiesandtools.DotDividedStringBuilder;
import top.nololiyt.worldpermissions.entitiesandtools.MessagesSender;
import top.nololiyt.worldpermissions.entitiesandtools.StringPair;

public class TeleportListener implements Listener
{
    private RootPlugin rootPlugin;
    
    public TeleportListener(RootPlugin rootPlugin)
    {
        this.rootPlugin = rootPlugin;
    }
    
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerTeleport(PlayerTeleportEvent e)
    {
        if (e.isCancelled())
            return;
    
        World destWorld = e.getTo().getWorld();
    
        if (destWorld.equals(e.getFrom().getWorld()))
            return;
    
        String destWorldName = destWorld.getName();
    
        if (!worldIsControlled(destWorldName))
            return;
    
        Player player = e.getPlayer();
        MessagesSender messagesSender = createMessagesSender(player, destWorldName);
    
        if (player.hasPermission("nworldpermissions.forfreeto." + destWorldName))
        {
            messagesSender.send(new DotDividedStringBuilder(
                    "messages.to-players.when-teleport-to-controlled-worlds.teleported"));
            return;
        }
    
        e.setCancelled(true);
        messagesSender.send(new DotDividedStringBuilder(
                "messages.to-players.when-teleport-to-controlled-worlds.denied"));
    }
    
    private MessagesSender createMessagesSender(Player player, String destWorldName)
    {
        StringPair[] pairs = new StringPair[]{
                StringPair.playerName(player.getDisplayName()),
                StringPair.worldName(destWorldName)
        };
        return new MessagesSender(rootPlugin.getMessagesManager(), player, pairs);
    }
    
    private boolean worldIsControlled(String worldName)
    {
        return rootPlugin.getConfig().getStringList("controlled-worlds")
                .contains(worldName);
    }
}
