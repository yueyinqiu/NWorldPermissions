package top.nololiyt.worldpermissions.commands.reload;

import org.bukkit.command.CommandSender;
import top.nololiyt.worldpermissions.RootPlugin;
import top.nololiyt.worldpermissions.commands.Executor;
import top.nololiyt.worldpermissions.entitiesandtools.DotDividedStringBuilder;

public class MarksExecutor extends Executor
{
    protected final static String layerName = "marks";
    
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
        reload(rootPlugin, messageKey, commandSender);
        return true;
    }
    
    void reload(RootPlugin rootPlugin, DotDividedStringBuilder messageKey, CommandSender commandSender)
    {
        rootPlugin.getMarksManager().reload();
        ExecutorMessagesSender.sendMessage(rootPlugin, messageKey, commandSender);
    }
}