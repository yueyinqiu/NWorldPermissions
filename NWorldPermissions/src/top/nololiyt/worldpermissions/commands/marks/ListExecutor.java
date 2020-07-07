package top.nololiyt.worldpermissions.commands.marks;

import org.bukkit.command.CommandSender;
import top.nololiyt.worldpermissions.configurationmanagers.MessagesManager;
import top.nololiyt.worldpermissions.RootPlugin;
import top.nololiyt.worldpermissions.commands.Executor;
import top.nololiyt.worldpermissions.entitiesandtools.DotDividedStringBuilder;
import top.nololiyt.worldpermissions.entitiesandtools.StringPair;

import java.util.Set;

public class ListExecutor extends Executor
{
    protected final static String layerName = "list";
    
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
        MessagesManager messagesManager = rootPlugin.getMessagesManager();
    
        Set<String> marks = rootPlugin.getMarksManager().allMarksName();
        if (marks.isEmpty())
        {
            messagesManager.sendMessage(commandSender, messageKey.append("no-mark"), new StringPair[]{
                    StringPair.senderName(commandSender.getName())
            });
            return true;
        }
        
        StringBuilder message = new StringBuilder();
        messageKey.append("list");
    
        String beginning = messagesManager.getItemAndShowError(
                new DotDividedStringBuilder(messageKey).append("beginning"));
        if (beginning == null)
            return true;
        message.append(beginning);
    
        String separator = messagesManager.getItemAndShowError(
                new DotDividedStringBuilder(messageKey).append("separator"));
        if (separator == null)
            return true;
        
        for (String name : marks)
        {
            message.append(name);
            message.append(separator);
        }
    
        int ml = message.length();
        message.delete(ml - separator.length(), ml);
        
        String ending = messagesManager.getItemAndShowError(messageKey.append("ending"));
        if (ending == null)
            return true;
        message.append(ending);
        
        messagesManager.sendMessage(commandSender, message.toString(), new StringPair[]{
                StringPair.senderName(commandSender.getName())
        });
        return true;
    }
    
}