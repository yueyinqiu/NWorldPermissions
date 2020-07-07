package top.nololiyt.worldpermissions.commands.marks;

import top.nololiyt.worldpermissions.commands.CommandLayer;
import top.nololiyt.worldpermissions.commands.Router;

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