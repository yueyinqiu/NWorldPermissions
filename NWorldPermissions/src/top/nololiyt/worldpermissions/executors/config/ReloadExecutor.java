package top.nololiyt.worldpermissions.executors.config;

import org.bukkit.command.CommandSender;
import top.nololiyt.worldpermissions.RootPlugin;
import top.nololiyt.worldpermissions.entities.DotDividedStringBuilder;
import top.nololiyt.worldpermissions.entities.StringPair;
import top.nololiyt.worldpermissions.executors.Executor;

public class ReloadExecutor extends Executor
{
    protected final static String layerName = "reload";
    
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
        rootPlugin.reloadConfig();
        rootPlugin.getMessagesManager().reloadConfiguration();
    
        messageKey.append("completed");
        StringPair[] pairs = new StringPair[]{
                StringPair.senderName(commandSender.getName())
        };
        
        commandSender.sendMessage(rootPlugin.getMessagesManager().getMessage(
                messageKey, pairs));
        return true;
    }
}
