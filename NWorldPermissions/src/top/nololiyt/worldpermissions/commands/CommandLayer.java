package top.nololiyt.worldpermissions.commands;

import org.bukkit.command.CommandSender;
import top.nololiyt.worldpermissions.RootPlugin;
import top.nololiyt.worldpermissions.entitiesandtools.DotDividedStringBuilder;

import java.util.List;

public interface CommandLayer
{
    String permissionName();
    
    String messageKey();
    
    void execute(int layer,
                 RootPlugin rootPlugin,
                 DotDividedStringBuilder permission,
                 DotDividedStringBuilder messageKey,
                 CommandSender commandSender,
                 String[] args);
    
    List<String> tabComplete(int layer,
                             RootPlugin rootPlugin,
                             DotDividedStringBuilder permission,
                             CommandSender commandSender,
                             String[] args);
}