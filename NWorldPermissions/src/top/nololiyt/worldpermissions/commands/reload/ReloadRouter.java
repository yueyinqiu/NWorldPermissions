package top.nololiyt.worldpermissions.commands.reload;

import top.nololiyt.worldpermissions.commands.CommandLayer;
import top.nololiyt.worldpermissions.commands.Router;

public class ReloadRouter extends Router
{
    protected final static String layerName = "reload";
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
            case "config":
                return new ConfigExecutor();
            case "messages":
                return new MessagesExecutor();
            case "marks":
                return new MarksExecutor();
            default:
                return null;
        }
    }
}

