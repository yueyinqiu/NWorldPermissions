package top.nololiyt.worldpermissions.commands.version;

import org.bukkit.command.CommandSender;
import top.nololiyt.worldpermissions.RootPlugin;
import top.nololiyt.worldpermissions.commands.CommandLayer;
import top.nololiyt.worldpermissions.commands.Router;
import top.nololiyt.worldpermissions.commands.tp.OfflineExecutor;
import top.nololiyt.worldpermissions.commands.tp.OnlineExecutor;
import top.nololiyt.worldpermissions.entitiesandtools.DotDividedStringBuilder;

import java.util.HashMap;
import java.util.Map;

public class VersionRouter extends Router
{
    protected final static String layerName = "version";
    
    @Override
    public String permissionName()
    {
        return layerName;
    }
    
    @Override
    public String messageKey()
    {
        return layerName;
    }
    
    private Map<String, CommandLayer> commandLayers = new HashMap<String, CommandLayer>()
    {
        {
            put("latest", new LatestExecutor());
            put("current", new CurrentExecutor());
        }
    };
    
    @Override
    protected Map<String, CommandLayer> nextLayers()
    {
        return commandLayers;
    }
}