package top.nololiyt.worldpermissions.commands.tp

import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.World
import org.bukkit.command.CommandSender
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.entity.Player
import top.nololiyt.worldpermissions.RootPlugin
import top.nololiyt.worldpermissions.commands.Executor
import top.nololiyt.worldpermissions.entities.DotDividedStringBuilder
import top.nololiyt.worldpermissions.entities.StringPair

import java.io.File
import java.io.IOException

class OnlineExecutor : Executor() {

    override fun permissionName(): String? {
        return null
    }

    override fun messageKey(): String? {
        return layerName
    }

    override fun run(layer: Int, rootPlugin: RootPlugin, permission: DotDividedStringBuilder,
                     messageKey: DotDividedStringBuilder, commandSender: CommandSender,
                     args: Array<String>): Boolean {
        if (args.size - 1 != layer + 1)
            return false

        val worldName = args[layer]
        val markName = args[layer + 1]

        val basePairs = arrayOf<StringPair?>(StringPair.markName(markName), StringPair.worldName(worldName), StringPair.senderName(commandSender.name))

        val world = Bukkit.getWorld(args[layer])
        if (world == null) {
            commandSender.sendMessage(rootPlugin.messagesManager.getMessage(
                    messageKey.append("no-such-world"), basePairs))
            return true
        }

        val location = rootPlugin.marksManager!!.getMark(markName)
        if (location == null) {
            commandSender.sendMessage(rootPlugin.messagesManager.getMessage(
                    messageKey.append("no-such-mark"), basePairs))
            return true
        }

        val players = world.players

        val playersPairs = arrayOf(null, StringPair.markName(markName), StringPair.worldName(worldName), StringPair.senderName(commandSender.name))

        var sCount = 0
        var fCount = 0
        for (player in players) {
            playersPairs[0] = StringPair.playerName(player.displayName)

            player.sendMessage(rootPlugin.messagesManager.getMessage(
                    DotDividedStringBuilder(
                            "messages.to-players.when-teleported-by-tp-online.before-teleport"),
                    playersPairs))
            if (!player.teleport(location)) {
                fCount++
                commandSender.sendMessage(rootPlugin.messagesManager.getMessage(
                        messageKey.append("failed-to-teleport-someone"), playersPairs))
            } else {
                sCount++
            }
        }

        val cPairs = arrayOf<StringPair?>(StringPair.teleportedCount(sCount.toString()), StringPair.unteleportedCount(fCount.toString()), StringPair.markName(markName), StringPair.worldName(worldName), StringPair.senderName(commandSender.name))
        commandSender.sendMessage(rootPlugin.messagesManager.getMessage(
                messageKey.append("completed"), cPairs))
        return true
    }

    companion object {
        protected val layerName = "online"
    }
}
