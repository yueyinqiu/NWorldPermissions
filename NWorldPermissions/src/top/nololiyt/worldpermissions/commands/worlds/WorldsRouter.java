package top.nololiyt.worldpermissions.commands.worlds;

import top.nololiyt.worldpermissions.commands.CommandLayer;
import top.nololiyt.worldpermissions.commands.Router;
import top.nololiyt.worldpermissions.commands.marks.MarksRouter;
import top.nololiyt.worldpermissions.commands.reload.ReloadRouter;
import top.nololiyt.worldpermissions.commands.tp.TpRouter;
import top.nololiyt.worldpermissions.commands.version.VersionRouter;

import java.util.HashMap;
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