package top.nololiyt.worldpermissions.commands.marks;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import top.nololiyt.worldpermissions.MarksManager;
import top.nololiyt.worldpermissions.MessagesManager;
import top.nololiyt.worldpermissions.RootPlugin;
import top.nololiyt.worldpermissions.commands.Executor;
import top.nololiyt.worldpermissions.entities.DotDividedStringBuilder;
import top.nololiyt.worldpermissions.entities.StringPair;

import java.io.IOException;

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
    
        StringBuilder message = new StringBuilder();
        message.append(messagesManager.getItem(
                new DotDividedStringBuilder(messageKey).append("beginning")));
    
        String separator = messagesManager.getItem(
                new DotDividedStringBuilder(messageKey).append("separator"));
        for (String name : rootPlugin.getMarksManager().allMarksName())
        {
            message.append(name);
            message.append(separator);
        }
    
        int ml = message.length();
        message.delete(ml - separator.length(), ml);
        message.append(messagesManager.getItem(
                new DotDividedStringBuilder(messageKey).append("ending")));
    
        messagesManager.sendMessage(new StringPair[]{
                StringPair.senderName(commandSender.getName())
        }, commandSender, message.toString());
        return true;
    }
}