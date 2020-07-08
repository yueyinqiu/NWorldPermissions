package top.nololiyt.worldpermissions.commands;

import org.bukkit.command.CommandSender;
import top.nololiyt.worldpermissions.RootPlugin;
import top.nololiyt.worldpermissions.commands.reload.ReloadRouter;
import top.nololiyt.worldpermissions.commands.version.VersionRouter;
import top.nololiyt.worldpermissions.entitiesandtools.DotDividedStringBuilder;
import top.nololiyt.worldpermissions.commands.worlds.WorldsRouter;
import top.nololiyt.worldpermissions.commands.marks.MarksRouter;
import top.nololiyt.worldpermissions.commands.tp.TpRouter;

public class RootRouter extends Router
{
    
    private RootPlugin rootPlugin;
    
    public RootRouter(RootPlugin rootPlugin)
    {
        this.rootPlugin = rootPlugin;
    }
    
    
    public void RouteCommand(CommandSender commandSender, String[] args)
    {
        DotDividedStringBuilder messagesRoot = new DotDividedStringBuilder("messages");
        DotDividedStringBuilder permissionRoot = new DotDividedStringBuilder("nworldpermissions");
    
        execute(0, rootPlugin, permissionRoot, messagesRoot, commandSender, args);
    }
    
    
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
    protected CommandLayer nextLayer(String arg)
    {
        switch (arg)
        {
            case "worlds":
                return new WorldsRouter();
            case "tp":
                return new TpRouter();
            case "marks":
                return new MarksRouter();
            case "reload":
                return new ReloadRouter();
            case "version":
                return new VersionRouter();
            default:
                return null;
        }
    }
}