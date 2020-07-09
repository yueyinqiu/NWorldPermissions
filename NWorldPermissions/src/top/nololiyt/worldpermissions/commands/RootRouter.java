package top.nololiyt.worldpermissions.commands;

import org.bukkit.command.CommandSender;
import top.nololiyt.worldpermissions.RootPlugin;
import top.nololiyt.worldpermissions.commands.reload.ReloadRouter;
import top.nololiyt.worldpermissions.commands.version.VersionRouter;
import top.nololiyt.worldpermissions.entitiesandtools.DotDividedStringBuilder;
import top.nololiyt.worldpermissions.commands.worlds.WorldsRouter;
import top.nololiyt.worldpermissions.commands.marks.MarksRouter;
import top.nololiyt.worldpermissions.commands.tp.TpRouter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class RootRouter extends Router
{
    
    private RootPlugin rootPlugin;
    
    public RootRouter(RootPlugin rootPlugin)
    {
        this.rootPlugin = rootPlugin;
    }
    
    
    public void routeCommand(CommandSender commandSender, String[] args)
    {
        DotDividedStringBuilder messagesRoot = new DotDividedStringBuilder("messages");
        DotDividedStringBuilder permissionRoot = new DotDividedStringBuilder("nworldpermissions");
        
        execute(0, rootPlugin, permissionRoot, messagesRoot, commandSender, args);
    }
    public List<String> doTabComplete(CommandSender commandSender, String[] args)
    {
        DotDividedStringBuilder permissionRoot = new DotDividedStringBuilder("nworldpermissions");
    
        return tabComplete(0, rootPlugin, permissionRoot, commandSender, args);
    }
    
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
    
    private Map<String, CommandLayer> commandLayers = new HashMap<String, CommandLayer>()
    {
        {
            put("worlds", new WorldsRouter());
            put("tp", new TpRouter());
            put("marks", new MarksRouter());
            put("reload", new ReloadRouter());
            put("version", new VersionRouter());
        }
    };
    
    @Override
    protected Map<String, CommandLayer> nextLayers()
    {
        return commandLayers;
    }
}