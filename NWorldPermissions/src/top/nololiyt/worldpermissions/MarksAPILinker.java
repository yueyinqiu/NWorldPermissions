package top.nololiyt.worldpermissions;

import org.bukkit.Location;
import top.nololiyt.yueyinqiu.bukkitplugins.marksapi.MarksManager;
import top.nololiyt.yueyinqiu.bukkitplugins.marksapi.MarksProvider;
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
        try
        {
            marksAPI = MarksManager.getInstance();
            marksAPI.marksProviders().add(new Provider(rootPlugin));
        }
        catch (NoClassDefFoundError e)
        {
            // This is likely due to MarksAPI isn't installed, so do nothing here.
            rootPlugin.getLogger().info("MarksAPI not found. The related features will be disabled.");
        }
        catch (Exception e)
        {
            // Incompatible version.
            e.printStackTrace();
        }
    }
    
    private class Provider implements MarksProvider
    {
        RootPlugin rootPlugin;
    
        private Provider(RootPlugin rootPlugin)
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