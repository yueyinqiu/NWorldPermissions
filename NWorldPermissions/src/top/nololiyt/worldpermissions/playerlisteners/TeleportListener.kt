package top.nololiyt.worldpermissions.playerlisteners

import org.bukkit.World
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerTeleportEvent
import top.nololiyt.worldpermissions.RootPlugin
import top.nololiyt.worldpermissions.entities.DotDividedStringBuilder
import top.nololiyt.worldpermissions.entities.StringPair

class TeleportListener(private val rootPlugin: RootPlugin) : Listener {

    @EventHandler
    fun onPlayerTeleport(e: PlayerTeleportEvent) {
        val dest = e.to!!.world
        if (dest == e.from.world) {
            return
        }

        val destName = dest!!.name

        val player = e.player

        if (!worldIsControlled(dest)) {
            return
        }

        val pairs = arrayOf<StringPair?>(StringPair.playerName(player.displayName))

        if (player.hasPermission("nworldpermissions.forfreeto.$destName")) {

            rootPlugin.messagesManager.sendMessage(DotDividedStringBuilder(
                "messages.to-players.when-teleport-to-controlled-worlds.teleported"), pairs, player);
            return
        }

        e.isCancelled = true

        rootPlugin.messagesManager.sendMessage(DotDividedStringBuilder(
            "messages.to-players.when-teleport-to-controlled-worlds.denied"), pairs, player);
    }

    private fun worldIsControlled(world: World): Boolean {
        val worlds = rootPlugin.config.getStringList("controlled-worlds")
        val destName = world.name
        return worlds.contains(destName)
    }
}
