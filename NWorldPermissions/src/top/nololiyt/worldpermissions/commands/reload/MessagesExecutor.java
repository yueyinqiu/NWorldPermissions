package top.nololiyt.worldpermissions.commands.reload;

import org.bukkit.command.CommandSender;
import top.nololiyt.worldpermissions.RootPlugin;
import top.nololiyt.worldpermissions.entities.DotDividedStringBuilder;
import top.nololiyt.worldpermissions.entities.StringPair;
import top.nololiyt.worldpermissions.commands.Executor;

public class MessagesExecutor extends Executor
{
    protected final static String layerName = "messages";
    
    @Override
    protected String permissionName()
    {
        return null;
    }
    
    @Override
    protected String messageKey()
    {
        return layerName;
    }
    
    @Override
    protected boolean run(int layer, RootPlugin rootPlugin, DotDividedStringBuilder permission,
                          DotDividedStringBuilder messageKey, CommandSender commandSender,
                          String[] args)
    {
        rootPlugin.getMessagesManager().reloadConfiguration();
    
        messageKey.append("completed");
        
        StringPair[] pairs = new StringPair[]{
                StringPair.senderName(commandSender.getName())
        };
        
        rootPlugin.getMessagesManager().sendMessage(
                messageKey, pairs,commandSender);
        return true;
    }
}