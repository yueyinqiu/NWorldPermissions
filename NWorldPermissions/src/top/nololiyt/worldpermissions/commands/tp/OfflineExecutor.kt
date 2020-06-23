package top.nololiyt.worldpermissions.commands.tp

import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.World
import org.bukkit.command.CommandSender
import org.bukkit.configuration.file.YamlConfiguration
import top.nololiyt.worldpermissions.RootPlugin
import top.nololiyt.worldpermissions.entities.DotDividedStringBuilder
import top.nololiyt.worldpermissions.entities.StringPair
import top.nololiyt.worldpermissions.commands.Executor

import java.io.File
import java.io.IOException

class OfflineExecutor : Executor() {

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

        if (!rootPlugin.config.getBoolean("offline-players-tracker.enabled") || rootPlugin.config.getBoolean("offline-players-tracker.record-only")) {
            commandSender.sendMessage(rootPlugin.messagesManager!!.getMessage(
                    messageKey.append("tracker-not-enabled"), basePairs))
            return true
        }

        val world = Bukkit.getWorld(args[layer])
        if (world == null) {
            commandSender.sendMessage(rootPlugin.messagesManager!!.getMessage(
                    messageKey.append("no-such-world"), basePairs))
            return true
        }

        val location = rootPlugin.marksManager!!.getMark(markName)
        if (location == null) {
            commandSender.sendMessage(rootPlugin.messagesManager!!.getMessage(
                    messageKey.append("no-such-mark"), basePairs))
            return true
        }

        val dir = File(
                rootPlugin.dataFolder.absolutePath, "playersData")
        dir.mkdirs()

        var count = 0
        for (file in dir.listFiles()!!) {
            val configuration = YamlConfiguration.loadConfiguration(file)
            if (configuration.getLocation("position")!!.world == world) {
                configuration.set("position", location)
                try {
                    configuration.save(file)
                    count++
                } catch (ex: IOException) {
                    ex.printStackTrace()
                }

            }
        }
        val cPairs = arrayOf<StringPair?>(StringPair.teleportedCount(count.toString()), StringPair.markName(markName), StringPair.worldName(worldName), StringPair.senderName(commandSender.name))
        commandSender.sendMessage(rootPlugin.messagesManager!!.getMessage(
                messageKey.append("completed"), cPairs))
        return true
    }

    companion object {
        protected val layerName = "offline"
    }
}
