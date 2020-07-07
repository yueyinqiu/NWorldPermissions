package top.nololiyt.worldpermissions.commands.reload;

import org.bukkit.command.CommandSender;
import top.nololiyt.worldpermissions.RootPlugin;
import top.nololiyt.worldpermissions.entitiesandtools.DotDividedStringBuilder;
import top.nololiyt.worldpermissions.commands.Executor;

import java.nio.channels.Pipe;

public class ConfigExecutor extends ReloadExecutor
{
    protected final static String layerName = "config";
    
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
     public void reload(RootPlugin rootPlugin)
    {
        rootPlugin.saveDefaultConfig();
        rootPlugin.reloadConfig();
    }
}
