package top.nololiyt.worldpermissions.commands;

import org.bukkit.command.CommandSender;
import top.nololiyt.worldpermissions.RootPlugin;
import top.nololiyt.worldpermissions.entitiesandtools.DotDividedStringBuilder;

public interface CommandLayer
{
    void execute(int layer,
                 RootPlugin rootPlugin,
                 DotDividedStringBuilder permission,
                 DotDividedStringBuilder messageKey,
                 CommandSender commandSender,
                 String[] args);
}
