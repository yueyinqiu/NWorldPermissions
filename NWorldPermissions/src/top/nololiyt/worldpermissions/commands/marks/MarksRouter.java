package top.nololiyt.worldpermissions.commands.marks;

import top.nololiyt.worldpermissions.commands.CommandLayer;
import top.nololiyt.worldpermissions.commands.Router;

import java.util.HashMap;
import java.util.Map;

public class MarksRouter extends Router
{
    protected final static String layerName = "marks";
    
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
            put("add", new AddExecutor());
            put("remove", new RemoveExecutor());
            put("list", new ListExecutor());
        }
    };
    
    @Override
    protected Map<String, CommandLayer> nextLayers()
    {
        return commandLayers;
    }
}