package top.nololiyt.worldpermissions.executors.tp;

import top.nololiyt.worldpermissions.executors.CommandLayer;
import top.nololiyt.worldpermissions.executors.Router;

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
