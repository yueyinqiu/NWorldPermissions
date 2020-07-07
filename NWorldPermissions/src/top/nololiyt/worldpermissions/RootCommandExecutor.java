package top.nololiyt.worldpermissions;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import top.nololiyt.worldpermissions.commands.RootRouter;

public class RootCommandExecutor implements CommandExecutor
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
        router.RouteCommand(commandSender, command, label, args);
        return true;
    }
}