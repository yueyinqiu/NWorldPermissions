package top.nololiyt.worldpermissions.playerlisteners;

import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.util.Vector;
import top.nololiyt.worldpermissions.RootPlugin;
import top.nololiyt.worldpermissions.configurationmanagers.WorldsManager;
import top.nololiyt.worldpermissions.entitiesandtools.WorldInfo;
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
        World fromWorld = e.getFrom().getWorld();
        if (destWorld.equals(fromWorld))
            return;
    
        String destWorldName = destWorld.getName();
    
        WorldInfo worldInfo = rootPlugin.getWorldsManager()
                .getWorldInfo(destWorldName);
        if (!worldInfo.isControlled())
            return;
    
        Player player = e.getPlayer();
        MessagesSender messagesSender = createMessagesSender(player, fromWorld.getName(),
                worldInfo.getDisplay());
    
        if (player.hasPermission("nworldpermissions.forfreeto." + destWorldName))
        {
            messagesSender.send(new DotDividedStringBuilder(
                    "messages.to-players.when-teleport-to-controlled-worlds.teleported"));
            return;
        }
    
        e.setCancelled(true);
        messagesSender.send(new DotDividedStringBuilder(
                "messages.to-players.when-teleport-to-controlled-worlds.denied"));
    
        if (e.getCause() == PlayerTeleportEvent.TeleportCause.NETHER_PORTAL ||
                e.getCause() == PlayerTeleportEvent.TeleportCause.END_PORTAL)
        {
            double thrust = worldInfo.getThrust();
            Vector random = Vector.getRandom().multiply(thrust);
            player.setVelocity(player.getVelocity().add(random));
        }
    }
    
    private MessagesSender createMessagesSender(Player player, String fromWorld, String toWorldDisplay)
    {
        WorldsManager worldsManager = rootPlugin.getWorldsManager();
        StringPair[] pairs = new StringPair[]{
                StringPair.playerName(player.getDisplayName()),
                StringPair.fromWorld(
                        worldsManager.getWorldInfo(fromWorld).getDisplay()),
                StringPair.toWorld(toWorldDisplay)
        };
        return new MessagesSender(rootPlugin.getMessagesManager(), player, pairs);
    }
}