package top.nololiyt.worldpermissions.commands.marks

import org.bukkit.command.CommandSender
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.entity.Player
import top.nololiyt.worldpermissions.MarksManager
import top.nololiyt.worldpermissions.RootPlugin
import top.nololiyt.worldpermissions.entities.DotDividedStringBuilder
import top.nololiyt.worldpermissions.entities.StringPair
import top.nololiyt.worldpermissions.commands.Executor

import java.io.File
import java.io.IOException

class AddExecutor : Executor()
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
        if (commandSender !is Player)
        {
            val pairs = arrayOf<StringPair?>(StringPair.senderName(commandSender.name))
            
            rootPlugin.messagesManager.sendMessage(messageKey.append("without-a-position"), pairs, commandSender);
            return true
        }
        
        if (args.size - 1 != layer) return false
        
        val cPairs =
            arrayOf<StringPair?>(StringPair.markName(args[layer]), StringPair.senderName(commandSender.displayName))
        try
        {
            val marksManager = rootPlugin.marksManager
            if (marksManager!!.getMark(args[layer]) != null)
            {
                rootPlugin.messagesManager.sendMessage(messageKey.append("with-occupied-name"), cPairs, commandSender);
                return true
            }
            marksManager.setMark(args[layer], commandSender.location)
            
            rootPlugin.messagesManager.sendMessage(messageKey.append("completed"), cPairs, commandSender);
            return true
        }
        catch (ex: IOException)
        {
            ex.printStackTrace()
            
            rootPlugin.messagesManager.sendMessage(messageKey.append("failed"), cPairs, commandSender);
            return true
        }
        
    }
    
    companion object
    {
        protected val layerName = "add"
    }
}