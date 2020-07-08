package top.nololiyt.worldpermissions.commands.version;

import top.nololiyt.worldpermissions.commands.CommandLayer;
import top.nololiyt.worldpermissions.commands.Router;

public class VersionRouter extends Router
{
    protected final static String layerName = "version";
    
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
            case "latest":
                return new LatestExecutor();
            case "current":
                return new CurrentExecutor();
            default:
                return null;
        }
    }
}