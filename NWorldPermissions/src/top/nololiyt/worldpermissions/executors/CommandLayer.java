package top.nololiyt.worldpermissions.executors;

import org.bukkit.command.CommandSender;
import top.nololiyt.worldpermissions.RootPlugin;
import top.nololiyt.worldpermissions.entities.DotDividedStringBuilder;

public interface CommandLayer
{
    void execute(int layer,
                 RootPlugin rootPlugin,
                 DotDividedStringBuilder permission,
                 DotDividedStringBuilder messageKey,
                 CommandSender commandSender,
                 String[] args);
}
