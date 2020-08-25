package top.nololiyt.worldpermissions.commands.marks;

import org.bukkit.command.CommandSender;
import top.nololiyt.worldpermissions.configurationmanagers.LocalMarksManager;
import top.nololiyt.worldpermissions.RootPlugin;
import top.nololiyt.worldpermissions.entitiesandtools.DotDividedStringBuilder;
import top.nololiyt.worldpermissions.entitiesandtools.MessagesSender;
import top.nololiyt.worldpermissions.entitiesandtools.StringPair;
import top.nololiyt.worldpermissions.commands.Executor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RemoveExecutor extends Executor
{
    protected final static String layerName = "remove";
    
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
            case 0:
                return new ArrayList<>(rootPlugin.getLocalMarksManager().allMarksName());
            default:
                return new ArrayList<>();
        }
    }
    
    @Override
    protected boolean run(int layer, RootPlugin rootPlugin,
                          DotDividedStringBuilder messageKey, CommandSender commandSender,
                          String[] args)
    {
        if (args.length - 1 != layer)
            return false;
    
        MessagesSender messagesSender = new MessagesSender(rootPlugin.getMessagesManager(),
                commandSender,new StringPair[]{
                StringPair.markName(args[layer]),
                StringPair.senderName(commandSender.getName())
        });
        
        try
        {
            LocalMarksManager localMarksManager = rootPlugin.getLocalMarksManager();
            if(localMarksManager.getMark(args[layer]) == null)
            {
                messagesSender.send(messageKey.append("no-such-mark"));
                return true;
            }
            localMarksManager.setMark(args[layer], null);
            messagesSender.send(messageKey.append("completed"));
            return true;
        }
        catch(IOException ex)
        {
            ex.printStackTrace();
            messagesSender.send(messageKey.append("failed"));
            return true;
        }
    }
}