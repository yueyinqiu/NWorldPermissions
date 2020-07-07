package top.nololiyt.worldpermissions.commands.reload;

import com.sun.org.apache.bcel.internal.generic.NEW;
import top.nololiyt.worldpermissions.commands.CommandLayer;
import top.nololiyt.worldpermissions.commands.Router;

import java.util.*;

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
            case "all":
                return new AllExecutor();
            default:
                return null;
        }
    }
}