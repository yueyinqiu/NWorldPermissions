package top.nololiyt.worldpermissions.commands.worlds.get;

import top.nololiyt.worldpermissions.commands.CommandLayer;
import top.nololiyt.worldpermissions.commands.Router;

import java.util.LinkedHashMap;
import java.util.Map;

public class GetRouter extends Router
{
    protected final static String layerName = "get";
    
    @Override
    public String permissionName()
    {
        return null;
    }
    
    @Override
    public String messageKey()
    {
        return layerName;
    }
    
    private Map<String, CommandLayer> commandLayers = new LinkedHashMap<String, CommandLayer>()
    {
        {
            put("display", new DisplayExecutor());
            put("thrust", new ThrustExecutor());
        }
    };
    
    @Override
    protected Map<String, CommandLayer> nextLayers()
    {
        return commandLayers;
    }
}