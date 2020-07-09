package top.nololiyt.worldpermissions.commands.reload;

import org.bukkit.command.CommandSender;
import top.nololiyt.worldpermissions.RootPlugin;
import top.nololiyt.worldpermissions.entitiesandtools.DotDividedStringBuilder;
import top.nololiyt.worldpermissions.commands.Executor;

import java.util.List;

public class AllExecutor extends Executor
{
    protected final static String layerName = "all";
    
    @Override
    public String permissionName()
    {
        return null;
    }
    
    @Override
    public String messageKey()
    {
        return null;
    }
    
    @Override
    public List<String> tabComplete(int layer, RootPlugin rootPlugin,
                                    DotDividedStringBuilder permission,
                                    CommandSender commandSender, String[] args)
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