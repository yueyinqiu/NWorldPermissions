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
                String message = rootPlugin.getMessagesManager().getMessage(
                        messageKey.append("no-such-mark"), cPairs);
                if (!message.isEmpty())
                    commandSender.sendMessage(message);
                return true;
            }
    
            marksManager.setMark(args[layer], null);
        }
        catch(IOException ex)
        {
            ex.printStackTrace();
    
            String message = rootPlugin.getMessagesManager().getMessage(
                    messageKey.append("failed"), cPairs);
            if (!message.isEmpty())
                commandSender.sendMessage(message);
            return true;
        }
    
        String message = rootPlugin.getMessagesManager().getMessage(
                messageKey.append("completed"), cPairs);
        if (!message.isEmpty())
            commandSender.sendMessage(message);
        return true;
    }
}