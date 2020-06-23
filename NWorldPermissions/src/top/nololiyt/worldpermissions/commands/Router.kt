package top.nololiyt.worldpermissions.commands

import org.bukkit.command.CommandSender
import top.nololiyt.worldpermissions.RootPlugin
import top.nololiyt.worldpermissions.entities.DotDividedStringBuilder
import top.nololiyt.worldpermissions.entities.StringPair

abstract class Router : CommandLayer {
    protected abstract fun permissionName(): String?
    protected abstract fun messageKey(): String?
    /**
     * Return the next layer matching the arg.
     * If no layer match it, please return 'null' and the help list will be sent.
     * @param arg
     * @return
     */
    protected abstract fun nextLayer(arg: String): CommandLayer?

    override fun execute(layer: Int,
                         rootPlugin: RootPlugin,
                         permission: DotDividedStringBuilder,
                         messageKey: DotDividedStringBuilder,
                         commandSender: CommandSender,
                         args: Array<String>) {
        if (permissionName() != null) {
            permission.append(permissionName()!!)
            if (!commandSender.hasPermission(permission.toString())) {
                return
            }
        }
        if (messageKey() != null)
            messageKey.append(messageKey()!!)

        if (args.size <= layer) {
            sendHelp(messageKey, rootPlugin, commandSender)
            return
        }

        val nextLayer = nextLayer(args[layer].toLowerCase())
        if (nextLayer == null) {
            sendHelp(messageKey, rootPlugin, commandSender)
            return
        }
        nextLayer.execute(
                layer + 1,
                rootPlugin,
                permission,
                messageKey,
                commandSender,
                args
        )
    }


    private fun sendHelp(messageKey: DotDividedStringBuilder,
                         rootPlugin: RootPlugin, commandSender: CommandSender): Boolean {
        messageKey.append("help")
        val pairs = arrayOf<StringPair?>(StringPair.senderName(commandSender.name))

        rootPlugin.messagesManager.sendMessage(messageKey, pairs,commandSender);
        return true
    }
}
