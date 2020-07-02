package top.nololiyt.worldpermissions.commands.worlds;

import top.nololiyt.worldpermissions.commands.CommandLayer;
import top.nololiyt.worldpermissions.commands.Router;

public class WorldsRouter extends Router
{
    protected final static String layerName = "worlds";
    
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
            case "list":
                return new ListExecutor();
            default:
                return null;
        }
    }
}
