package top.nololiyt.worldpermissions.commands.config

import org.bukkit.command.CommandSender
import org.bukkit.configuration.Configuration
import top.nololiyt.worldpermissions.RootPlugin
import top.nololiyt.worldpermissions.entities.DotDividedStringBuilder
import top.nololiyt.worldpermissions.entities.StringPair
import top.nololiyt.worldpermissions.commands.Executor

class AddExecutor : Executor() {

    override fun permissionName(): String? {
        return null
    }

    override fun messageKey(): String? {
        return layerName
    }

    override fun run(layer: Int, rootPlugin: RootPlugin, permission: DotDividedStringBuilder,
                     messageKey: DotDividedStringBuilder, commandSender: CommandSender,
                     args: Array<String>): Boolean {
        if (args.size - 1 != layer)
            return false

        val cPairs = arrayOf<StringPair?>(StringPair.worldName(args[layer]), StringPair.senderName(commandSender.name))

        val config = rootPlugin.config
        val worlds = config.getStringList("controlled-worlds")
        if (!worlds.contains(args[layer])) {
            worlds.add(args[layer])
        }
        config.set("controlled-worlds", worlds)
        rootPlugin.saveConfig()

        val message = rootPlugin.messagesManager.getMessage(
                messageKey.append("completed"), cPairs)
        if (!message.isBlank())
            commandSender.sendMessage(message)
        return true
    }

    companion object {
        protected val layerName = "add"
    }
}