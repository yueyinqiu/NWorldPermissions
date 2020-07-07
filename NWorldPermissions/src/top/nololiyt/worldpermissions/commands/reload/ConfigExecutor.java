package top.nololiyt.worldpermissions.commands.reload;

import org.bukkit.command.CommandSender;
import top.nololiyt.worldpermissions.RootPlugin;
import top.nololiyt.worldpermissions.entities.DotDividedStringBuilder;
import top.nololiyt.worldpermissions.entities.StringPair;
import top.nololiyt.worldpermissions.commands.Executor;

public class ConfigExecutor extends Executor
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
    protected boolean run(int layer, RootPlugin rootPlugin, DotDividedStringBuilder permission,
                          DotDividedStringBuilder messageKey, CommandSender commandSender,
                          String[] args)
    {
        reload(rootPlugin, messageKey, commandSender);
        return true;
    }
    
    void reload(RootPlugin rootPlugin, DotDividedStringBuilder messageKey, CommandSender commandSender)
    {
        rootPlugin.saveDefaultConfig();
        rootPlugin.reloadConfig();
        
        ExecutorMessagesSender.sendMessage(rootPlugin, messageKey, commandSender);
    }
}
