package top.nololiyt.worldpermissions.commands.reload;

import top.nololiyt.worldpermissions.RootPlugin;

public class WorldsExecutor extends ReloadExecutor
{
    protected final static String layerName = "worlds";
    
    @Override
    public String permissionName()
    {
        return null;
    }
    
    @Override
    public String messageKey()
    {
        return layerName;
    }
    
    @Override
    public void reload(RootPlugin rootPlugin)
    {
        rootPlugin.getWorldsManager().reload();
    }
}