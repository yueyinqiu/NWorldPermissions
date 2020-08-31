package top.nololiyt.worldpermissions.commands.tpsingle;

import top.nololiyt.worldpermissions.commands.CommandLayer;
import top.nololiyt.worldpermissions.commands.Router;

import java.util.LinkedHashMap;
import java.util.Map;

public class TpSingleRouter extends Router
{
    protected final static String layerName = "tpsingle";
    
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
    
    private Map<String, CommandLayer> commandLayers = new LinkedHashMap<String, CommandLayer>()
    {
        {
            put("offline", new OfflineExecutor());
            put("online", new OnlineExecutor());
        }
    };
    
    @Override
    protected Map<String, CommandLayer> nextLayers()
    {
        return commandLayers;
    }
}