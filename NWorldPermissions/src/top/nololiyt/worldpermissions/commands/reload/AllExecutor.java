package top.nololiyt.worldpermissions.commands.reload;

import org.bukkit.command.CommandSender;
import top.nololiyt.worldpermissions.RootPlugin;
import top.nololiyt.worldpermissions.entitiesandtools.DotDividedStringBuilder;
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
        return null;
    }
    
    @Override
    protected boolean run(int layer, RootPlugin rootPlugin, DotDividedStringBuilder permission,
                          DotDividedStringBuilder messageKey, CommandSender commandSender,
                          String[] args)
    {
        ReloadExecutor[] executors = new ReloadExecutor[]{
                new ConfigExecutor(), new MarksExecutor(), new MessagesExecutor()
        };
        
        for (ReloadExecutor executor : executors)
        {
            executor.run(rootPlugin, messageKey, commandSender);
        }
        return true;
    }
}