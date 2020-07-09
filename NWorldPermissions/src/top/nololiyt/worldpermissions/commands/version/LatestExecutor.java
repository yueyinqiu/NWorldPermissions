package top.nololiyt.worldpermissions.commands.version;

import org.bukkit.command.CommandSender;
import top.nololiyt.worldpermissions.RootPlugin;
import top.nololiyt.worldpermissions.VersionManager;
import top.nololiyt.worldpermissions.entitiesandtools.DotDividedStringBuilder;
import top.nololiyt.worldpermissions.commands.Executor;
import top.nololiyt.worldpermissions.entitiesandtools.LatestVersion;
import top.nololiyt.worldpermissions.entitiesandtools.MessagesSender;
import top.nololiyt.worldpermissions.entitiesandtools.StringPair;

import java.util.ArrayList;
import java.util.List;


public class LatestExecutor extends Executor
{
    protected final static String layerName = "latest";
    
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
    public List<String> getTabComplete(RootPlugin rootPlugin,int ordinal)
    {
        switch (ordinal)
        {
            default:
                return new ArrayList<>();
        }
    }
    @Override
    protected boolean run(int layer, RootPlugin rootPlugin,
                          DotDividedStringBuilder messageKey, CommandSender commandSender,
                          String[] args)
    {
        MessagesSender messagesSender = new MessagesSender(rootPlugin.getMessagesManager(),
                commandSender, new StringPair[]{
                StringPair.senderName(commandSender.getName())
        });
        VersionManager versionManager = rootPlugin.getVersionManager();
        if (!versionManager.isUpdateCheckerEnabled())
        {
            messagesSender.send(messageKey.append("checker-not-enabled"));
            return true;
        }
        LatestVersion latestVersion = versionManager.getLatestVersion();
        if (latestVersion == null)
        {
            messagesSender.send(messageKey.append("failed-to-check"));
            return true;
        }
        messagesSender.setArgs(new StringPair[]{
                StringPair.senderName(commandSender.getName()),
                StringPair.version(latestVersion.getVersion().toString()),
                StringPair.time(latestVersion.getCheckTime().toString())
        });
        messagesSender.send(messageKey.append("message"));
        return true;
    }
}