package top.nololiyt.worldpermissions;

import org.bukkit.Location;
import top.nololiyt.yueyinqiu.bukkitplugins.marksapi.MarksManager;
import top.nololiyt.yueyinqiu.bukkitplugins.marksapi.entities.MarkRelatedValues;

import java.util.Collections;

public class MarksAPILinker
{
    private MarksManager marksAPI;
    
    public MarksManager getMarksAPI()
    {
        return marksAPI;
    }
    MarksAPILinker(RootPlugin rootPlugin)
    {
        marksAPI = MarksManager.getInstance();
        marksAPI.marksProviders().add(new MarksProvider(rootPlugin));
    }
    
    private class MarksProvider implements top.nololiyt.yueyinqiu.bukkitplugins.marksapi.MarksProvider
    {
        RootPlugin rootPlugin;
        
        MarksProvider(RootPlugin rootPlugin)
        {
            this.rootPlugin = rootPlugin;
        }
        
        @Override
        public String getPrefix()
        {
            return "nwope";
        }
        
        @Override
        public Location getMark(String s, MarkRelatedValues markRelatedValues)
        {
            if (markRelatedValues.getCommandSender() != null &&
                    !markRelatedValues.getCommandSender().hasPermission("nworldpermissions.marks.list"))
                return null;
            return rootPlugin.getLocalMarksManager().getMark(s);
        }
        
        @Override
        public Iterable<String> getAllMarksKey(MarkRelatedValues markRelatedValues)
        {
            if (markRelatedValues.getCommandSender() != null &&
                    !markRelatedValues.getCommandSender().hasPermission("nworldpermissions.marks.list"))
                return Collections.emptyList();
            return rootPlugin.getLocalMarksManager().allMarksName();
        }
    }
}