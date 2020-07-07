package top.nololiyt.worldpermissions.commands.tp;

import top.nololiyt.worldpermissions.commands.CommandLayer;
import top.nololiyt.worldpermissions.commands.Router;

public class TpRouter extends Router
{
    protected final static String layerName = "tp";
    
    @Override
    protected String permissionName()
    {
        return layerName;
    }
    
    @Override
    protected String messageKey()
    {
        return layerName;
    }
    
    @Override
    protected CommandLayer nextLayer(String arg)
    {
        switch (arg)
        {
            case "offline":
                return new OfflineExecutor();
            case "online":
                return new OnlineExecutor();
            default:
                return null;
        }
    }
}