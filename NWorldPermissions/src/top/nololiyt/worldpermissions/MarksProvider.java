package top.nololiyt.worldpermissions;

import org.bukkit.Location;
import top.nololiyt.yueyinqiu.bukkitplugins.marksapi.MarksManager;
import top.nololiyt.yueyinqiu.bukkitplugins.marksapi.entities.MarkRelatedValues;

import java.util.Collections;

public class MarksProvider implements top.nololiyt.yueyinqiu.bukkitplugins.marksapi.MarksProvider
{
    RootPlugin rootPlugin;
    
    public MarksProvider(RootPlugin rootPlugin)
    {
        MarksManager.getInstance().marksProviders().add(this);
        this.rootPlugin = rootPlugin;
    }
    
    @Override
    public String getPrefix()
    {
        return "nworpe";
    }
    
    @Override
    public Location getMark(String s, MarkRelatedValues markRelatedValues)
    {
        if (markRelatedValues == null ||
                !markRelatedValues.getCommandSender().hasPermission("nworldpermissions.marks.list"))
            return null;
        return rootPlugin.getMarksManager().getMark(s);
    }
    
    @Override
    public Iterable<String> getAllMarksKey(MarkRelatedValues markRelatedValues)
    {
        if (markRelatedValues == null ||
                !markRelatedValues.getCommandSender().hasPermission("nworldpermissions.marks.list"))
            return Collections.emptyList();
        return rootPlugin.getMarksManager().allMarksName();
    }
}