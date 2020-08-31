package top.nololiyt.worldpermissions.commands.tp;

import top.nololiyt.worldpermissions.commands.CommandLayer;
import top.nololiyt.worldpermissions.commands.Router;
import top.nololiyt.worldpermissions.commands.reload.AllExecutor;
import top.nololiyt.worldpermissions.commands.reload.ConfigExecutor;
import top.nololiyt.worldpermissions.commands.reload.MarksExecutor;
import top.nololiyt.worldpermissions.commands.reload.MessagesExecutor;

import java.util.LinkedHashMap;
import java.util.Map;

public class TpRouter extends Router
{
    protected final static String layerName = "tp";
    
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
            put("untracked", new UntrackedExecutor());
        }
    };
    
    @Override
    protected Map<String, CommandLayer> nextLayers()
    {
        return commandLayers;
    }
}