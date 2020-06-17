package top.nololiyt.worldpermissions.executors.tp;

import top.nololiyt.worldpermissions.executors.CommandLayer;
import top.nololiyt.worldpermissions.executors.Router;

public class OfflineRouter extends Router
{
    protected final static String layerName = "offline";
    @Override
    protected String permissionName()
    {
        return null;
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
            case "offline":
                return new OfflineRouter();
            default:
                return null;
        }
    }
}
