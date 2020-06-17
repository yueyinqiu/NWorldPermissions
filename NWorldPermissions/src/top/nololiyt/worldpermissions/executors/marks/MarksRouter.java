package top.nololiyt.worldpermissions.executors.marks;

import top.nololiyt.worldpermissions.executors.CommandLayer;
import top.nololiyt.worldpermissions.executors.Router;
import top.nololiyt.worldpermissions.executors.config.ReloadExecutor;

public class MarksRouter extends Router
{
    protected final static String layerName = "marks";
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
            case "add":
                return new AddExecutor();
            case "remove":
                return new RemoveExecutor();
            default:
                return null;
        }
    }
}