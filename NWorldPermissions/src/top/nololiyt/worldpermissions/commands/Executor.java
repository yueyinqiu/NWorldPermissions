package top.nololiyt.worldpermissions.commands;

import org.bukkit.command.CommandSender;
import top.nololiyt.worldpermissions.RootPlugin;
import top.nololiyt.worldpermissions.entities.DotDividedStringBuilder;
import top.nololiyt.worldpermissions.entities.StringPair;


public abstract class Executor implements CommandLayer
{
    protected abstract String permissionName();
    protected abstract String messageKey();
    
    protected abstract boolean run(int layer,
                                   RootPlugin rootPlugin,
                                   DotDividedStringBuilder permission,
                                   DotDividedStringBuilder messageKey,
                                   CommandSender commandSender,
                                   String[] args);
    
    public void execute(int layer,
                        RootPlugin rootPlugin,
                        DotDividedStringBuilder permission,
                        DotDividedStringBuilder messageKey,
                        CommandSender commandSender,
                        String[] args)
    {
        if (permissionName() != null)
        {
            permission.append(permissionName());
            if (!commandSender.hasPermission(permission.toString()))
            {
                return;
            }
        }
        DotDividedStringBuilder messageKeyCopy = new DotDividedStringBuilder(messageKey);
        
        if (messageKey() != null)
            messageKey.append(messageKey());
    
        if (!run(layer,rootPlugin, permission, messageKey, commandSender, args))
        {
            sendHelp(messageKeyCopy, rootPlugin, commandSender);
        }
    }
    
    protected boolean sendHelp(DotDividedStringBuilder messageKey,
                               RootPlugin rootPlugin, CommandSender commandSender)
    {
        messageKey.append("help");
        StringPair[] pairs = new StringPair[]{
                StringPair.senderName(commandSender.getName())
        };
        
        String message = rootPlugin.getMessagesManager().getMessage(
                messageKey, pairs);
        if (!message.isEmpty())
            commandSender.sendMessage(message);
        return true;
    }
}
