package top.nololiyt.worldpermissions.commands.worlds;

import top.nololiyt.worldpermissions.commands.CommandLayer;
import top.nololiyt.worldpermissions.commands.Router;
import top.nololiyt.worldpermissions.commands.worlds.get.GetRouter;
import top.nololiyt.worldpermissions.commands.worlds.set.SetRouter;

import java.util.LinkedHashMap;
import java.util.Map;

public class WorldsRouter extends Router
{
    protected final static String layerName = "worlds";
    
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
            put("control", new ControlExecutor());
            put("uncontrol", new UncontrolExecutor());
            put("controlled", new ControlledExecutor());
            put("get", new GetRouter());
            put("set", new SetRouter());
        }
    };
    
    @Override
    protected Map<String, CommandLayer> nextLayers()
    {
        return commandLayers;
    }
}