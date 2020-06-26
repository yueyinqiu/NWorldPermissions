package top.nololiyt.worldpermissions.commands.marks;

import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import top.nololiyt.worldpermissions.MarksManager;
import top.nololiyt.worldpermissions.RootPlugin;
import top.nololiyt.worldpermissions.entities.DotDividedStringBuilder;
import top.nololiyt.worldpermissions.entities.StringPair;
import top.nololiyt.worldpermissions.commands.Executor;

import java.io.File;
import java.io.IOException;

public class RemoveExecutor extends Executor
{
    protected final static String layerName = "remove";
    
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
        if (args.length - 1 != layer)
            return false;
        
        StringPair[] cPairs = new StringPair[]{
                StringPair.markName(args[layer]),
                StringPair.senderName(commandSender.getName())
        };
        try
        {
            MarksManager marksManager = rootPlugin.getMarksManager();
            
            if(marksManager.getMark(args[layer]) == null)
            {
                rootPlugin.getMessagesManager().sendMessage(
                        messageKey.append("no-such-mark"), cPairs,commandSender);
                return true;
            }
    
            marksManager.setMark(args[layer], null);
        }
        catch(IOException ex)
        {
            ex.printStackTrace();
    
            rootPlugin.getMessagesManager().sendMessage(
                    messageKey.append("failed"), cPairs,commandSender);
            return true;
        }
    
        rootPlugin.getMessagesManager().sendMessage(
                messageKey.append("completed"), cPairs,commandSender);
        return true;
    }
}