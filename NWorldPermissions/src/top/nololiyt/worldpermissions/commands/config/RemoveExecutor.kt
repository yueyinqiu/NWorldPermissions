package top.nololiyt.worldpermissions.commands.config

import org.bukkit.command.CommandSender
import org.bukkit.configuration.Configuration
import top.nololiyt.worldpermissions.RootPlugin
import top.nololiyt.worldpermissions.entities.DotDividedStringBuilder
import top.nololiyt.worldpermissions.entities.StringPair
import top.nololiyt.worldpermissions.commands.Executor

class RemoveExecutor : Executor() {

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

        worlds.remove(args[layer])
        config.set("controlled-worlds", worlds)

        rootPlugin.saveConfig()

        rootPlugin.messagesManager.sendMessage(messageKey.append("completed"), cPairs,commandSender);
        return true
    }

    companion object {
        protected val layerName = "remove"
    }
}