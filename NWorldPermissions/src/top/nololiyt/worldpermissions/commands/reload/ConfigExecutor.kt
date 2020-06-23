package top.nololiyt.worldpermissions.commands.reload

import org.bukkit.command.CommandSender
import top.nololiyt.worldpermissions.RootPlugin
import top.nololiyt.worldpermissions.entities.DotDividedStringBuilder
import top.nololiyt.worldpermissions.entities.StringPair
import top.nololiyt.worldpermissions.commands.Executor

class ConfigExecutor : Executor() {

    override fun permissionName(): String? {
        return null
    }

    override fun messageKey(): String? {
        return layerName
    }

    override fun run(layer: Int, rootPlugin: RootPlugin, permission: DotDividedStringBuilder,
                     messageKey: DotDividedStringBuilder, commandSender: CommandSender,
                     args: Array<String>): Boolean {
        rootPlugin.reloadConfig()

        messageKey.append("completed")

        val pairs = arrayOf<StringPair?>(StringPair.senderName(commandSender.name))

        val message = rootPlugin.messagesManager.getMessage(
                messageKey, pairs)
        if (!message.isBlank())
            commandSender.sendMessage(message)
        return true
    }

    companion object {
        protected val layerName = "config"
    }
}
