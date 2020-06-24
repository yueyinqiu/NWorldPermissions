package top.nololiyt.worldpermissions.commands

import org.bukkit.command.CommandSender
import top.nololiyt.worldpermissions.RootPlugin
import top.nololiyt.worldpermissions.entities.DotDividedStringBuilder
import top.nololiyt.worldpermissions.entities.StringPair


abstract class Executor : CommandLayer
{
    protected abstract fun permissionName(): String?
    protected abstract fun messageKey(): String?
    
    protected abstract fun run(layer: Int, rootPlugin: RootPlugin, permission: DotDividedStringBuilder,
        messageKey: DotDividedStringBuilder, commandSender: CommandSender, args: Array<String>): Boolean
    
    override fun execute(layer: Int, rootPlugin: RootPlugin, permission: DotDividedStringBuilder,
        messageKey: DotDividedStringBuilder, commandSender: CommandSender, args: Array<String>)
    {
        if (permissionName() != null)
        {
            permission.append(permissionName());
            if (!commandSender.hasPermission(permission.toString())) return;
        }
        val messageKeyCopy = DotDividedStringBuilder(messageKey);
    
        if (messageKey() != null) messageKey.append(messageKey());
    
        if (!run(layer, rootPlugin, permission, messageKey, commandSender, args))
        {
            sendHelp(messageKeyCopy, rootPlugin, commandSender);
        }
    }
    
    private fun sendHelp(messageKey: DotDividedStringBuilder, rootPlugin: RootPlugin,
        commandSender: CommandSender): Boolean
    {
        messageKey.append("help");
        val pairs = arrayOf<StringPair?>(StringPair.senderName(commandSender.name));
        
        rootPlugin.messagesManager.sendMessage(messageKey, pairs, commandSender);
        return true;
    }
}
