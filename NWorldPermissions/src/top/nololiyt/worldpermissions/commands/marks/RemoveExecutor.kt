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

class RemoveExecutor : Executor()
{
    
    override fun permissionName(): String?
    {
        return null;
    }
    
    override fun messageKey(): String?
    {
        return layerName;
    }
    
    override fun run(layer: Int, rootPlugin: RootPlugin, permission: DotDividedStringBuilder,
        messageKey: DotDividedStringBuilder, commandSender: CommandSender, args: Array<String>): Boolean
    {
        if (args.size - 1 != layer) return false;
        
        val cPairs = arrayOf<StringPair?>(StringPair.markName(args[layer]), StringPair.senderName(commandSender.name))
        try
        {
            val marksManager = rootPlugin.marksManager;
            
            if (marksManager.getMark(args[layer]) == null)
            {
                rootPlugin.messagesManager.sendMessage(messageKey.append("no-such-mark"), cPairs, commandSender);
                return true;
            }
            
            marksManager.setMark(args[layer], null);
            
            rootPlugin.messagesManager.sendMessage(messageKey.append("completed"), cPairs, commandSender);
            return true;
            
        }
        catch (ex: IOException)
        {
            ex.printStackTrace();
            
            rootPlugin.messagesManager.sendMessage(messageKey.append("failed"), cPairs, commandSender);
            return true;
        }
    }
    
    companion object
    {
        private const val layerName= "remove";
    }
}