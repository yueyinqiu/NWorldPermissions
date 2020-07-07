package top.nololiyt.worldpermissions.commands.reload;

import org.bukkit.command.CommandSender;
import top.nololiyt.worldpermissions.RootPlugin;
import top.nololiyt.worldpermissions.entities.DotDividedStringBuilder;
import top.nololiyt.worldpermissions.commands.Executor;

public class AllExecutor extends Executor
{
    protected final static String layerName = "all";
    
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
        new ConfigExecutor().reload(rootPlugin,messageKey,commandSender);
        new MarksExecutor().reload(rootPlugin,messageKey,commandSender);
        new MessagesExecutor().reload(rootPlugin,messageKey,commandSender);
        return true;
    }
}
