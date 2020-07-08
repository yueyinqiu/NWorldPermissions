package top.nololiyt.worldpermissions.commands.version;

import org.bukkit.command.CommandSender;
import top.nololiyt.worldpermissions.RootPlugin;
import top.nololiyt.worldpermissions.VersionManager;
import top.nololiyt.worldpermissions.entitiesandtools.DotDividedStringBuilder;
import top.nololiyt.worldpermissions.commands.Executor;
import top.nololiyt.worldpermissions.entitiesandtools.LatestVersion;
import top.nololiyt.worldpermissions.entitiesandtools.MessagesSender;
import top.nololiyt.worldpermissions.entitiesandtools.StringPair;


public class CurrentExecutor extends Executor
{
    protected final static String layerName = "current";
    
    @Override
    protected String permissionName()
    {
        return null;
    }
    
    @Override
    protected String messageKey()
    {
        return layerName;
    }
    
    @Override
    protected boolean run(int layer, RootPlugin rootPlugin, DotDividedStringBuilder permission,
                          DotDividedStringBuilder messageKey, CommandSender commandSender,
                          String[] args)
    {
        MessagesSender messagesSender = new MessagesSender(rootPlugin.getMessagesManager(),
                commandSender, new StringPair[]{
                StringPair.senderName(commandSender.getName()),
                StringPair.version(rootPlugin.getVersionManager().getCurrentVersion().toString())
        });
        messagesSender.send(messageKey.append("message"));
        return true;
    }
}