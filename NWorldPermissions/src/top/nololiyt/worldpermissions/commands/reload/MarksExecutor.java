package top.nololiyt.worldpermissions.commands.reload;

import top.nololiyt.worldpermissions.RootPlugin;

public class MarksExecutor extends ReloadExecutor
{
    protected final static String layerName = "marks";
    
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
        rootPlugin.getLocalMarksManager().reload();
    }
}