package top.nololiyt.worldpermissions.commands.reload;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import top.nololiyt.worldpermissions.RootPlugin;
import top.nololiyt.worldpermissions.entitiesandtools.DotDividedStringBuilder;
import top.nololiyt.worldpermissions.commands.Executor;

import java.util.ArrayList;
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
    public List<String> getTabComplete(RootPlugin rootPlugin,int ordinal)
    {
        switch (ordinal)
        {
            default:
                return new ArrayList<>();
        }
    }
    @Override
    protected boolean run(int layer, RootPlugin rootPlugin,
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