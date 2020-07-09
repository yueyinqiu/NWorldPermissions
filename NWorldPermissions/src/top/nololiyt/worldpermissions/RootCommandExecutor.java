package top.nololiyt.worldpermissions;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import top.nololiyt.worldpermissions.commands.RootRouter;

import java.util.ArrayList;
import java.util.List;

public class RootCommandExecutor implements CommandExecutor, TabCompleter
{
    // private RootPlugin rootPlugin;
    
    RootCommandExecutor(RootPlugin rootPlugin)
    {
        // this.rootPlugin = rootPlugin;
        this.router = new RootRouter(rootPlugin);
    }
    
    RootRouter router;
    
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args)
    {
        router.routeCommand(commandSender, args);
        return true;
    }
    
    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command,
                                      String label, String[] args)
    {
        return router.doTabComplete(commandSender, args);
    }
}