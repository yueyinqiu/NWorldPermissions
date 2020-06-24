package top.nololiyt.worldpermissions.commands.reload

import org.bukkit.command.CommandSender
import top.nololiyt.worldpermissions.RootPlugin
import top.nololiyt.worldpermissions.commands.Executor
import top.nololiyt.worldpermissions.entities.DotDividedStringBuilder
import top.nololiyt.worldpermissions.entities.StringPair

class MarksExecutor : Executor()
{
    
    override fun permissionName(): String?
    {
        return null
    }
    
    override fun messageKey(): String?
    {
        return layerName
    }
    
    override fun run(layer: Int, rootPlugin: RootPlugin, permission: DotDividedStringBuilder,
        messageKey: DotDividedStringBuilder, commandSender: CommandSender, args: Array<String>): Boolean
    {
        rootPlugin.marksManager!!.reloadConfiguration()
        
        val pairs = arrayOf<StringPair?>(StringPair.senderName(commandSender.name))
        
        rootPlugin.messagesManager.sendMessage(messageKey.append("completed"), pairs, commandSender);
        return true
    }
    
    companion object
    {
        private const val layerName = "marks";
    }
}
