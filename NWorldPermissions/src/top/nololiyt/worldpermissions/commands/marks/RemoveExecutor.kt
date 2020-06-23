package top.nololiyt.worldpermissions.commands.marks

import com.sun.xml.internal.ws.client.SenderException
import org.bukkit.command.CommandSender
import org.bukkit.configuration.file.YamlConfiguration
import top.nololiyt.worldpermissions.MarksManager
import top.nololiyt.worldpermissions.RootPlugin
import top.nololiyt.worldpermissions.entities.DotDividedStringBuilder
import top.nololiyt.worldpermissions.entities.StringPair
import top.nololiyt.worldpermissions.commands.Executor

import java.io.File
import java.io.IOException

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

        val cPairs = arrayOf<StringPair?>(StringPair.markName(args[layer]), StringPair.senderName(commandSender.name))
        try {
            val marksManager = rootPlugin.marksManager

            if (marksManager!!.getMark(args[layer]) == null) {
                val message = rootPlugin.messagesManager.getMessage(
                        messageKey.append("no-such-mark"), cPairs)
                if (!message.isBlank())
                    commandSender.sendMessage(message)
                return true
            }

            marksManager.setMark(args[layer], null);

            val message = rootPlugin.messagesManager.getMessage(
                    messageKey.append("completed"), cPairs)
            if (!message.isBlank())
                commandSender.sendMessage(message)
            return true

        } catch (ex: IOException) {
            ex.printStackTrace()

            val message = rootPlugin.messagesManager.getMessage(
                    messageKey.append("failed"), cPairs)
            if (!message.isBlank())
                commandSender.sendMessage(message)
            return true
        }
    }

    companion object {
        protected val layerName = "remove"
    }
}