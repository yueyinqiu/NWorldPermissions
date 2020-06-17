package top.nololiyt.worldpermissions.executors.config;

import top.nololiyt.worldpermissions.executors.CommandLayer;
import top.nololiyt.worldpermissions.executors.Router;

public class ConfigRouter extends Router
{
    protected final static String layerName = "config";
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
    /**
     * Return the next layer matching the arg.
     * If no layer match it, please return 'null' and the help list will be sent.
     *
     * @param arg
     * @return
     */
    @Override
    protected CommandLayer nextLayer(String arg)
    {
        switch (arg)
        {
            case "reload":
                return new ReloadExecutor();
            default:
                return null;
        }
    }
}
