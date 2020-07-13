package top.nololiyt.worldpermissions.commands.reload;

import top.nololiyt.worldpermissions.commands.CommandLayer;
import top.nololiyt.worldpermissions.commands.Router;

import java.util.LinkedHashMap;
import java.util.Map;

public class ReloadRouter extends Router
{
    protected final static String layerName = "reload";
    
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
            put("config", new ConfigExecutor());
            put("messages", new MessagesExecutor());
            put("marks", new MarksExecutor());
            put("worlds", new WorldsExecutor());
            put("all", new AllExecutor());
        }
    };
    
    @Override
    protected Map<String, CommandLayer> nextLayers()
    {
        return commandLayers;
    }
}