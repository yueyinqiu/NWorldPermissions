package top.nololiyt.worldpermissions.commands.marks;

import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import top.nololiyt.worldpermissions.MarksManager;
import top.nololiyt.worldpermissions.RootPlugin;
import top.nololiyt.worldpermissions.entities.DotDividedStringBuilder;
import top.nololiyt.worldpermissions.entities.StringPair;
import top.nololiyt.worldpermissions.commands.Executor;

import java.io.File;
import java.io.IOException;

public class AddExecutor extends Executor
{
    protected final static String layerName = "add";
    
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
        if (!(commandSender instanceof Player))
        {
            StringPair[] pairs = new StringPair[]{
                    StringPair.senderName(commandSender.getName()),
            };
    
            String message = rootPlugin.getMessagesManager().getMessage(
                    messageKey.append("without-a-position"), pairs);
            if (message != "")
                commandSender.sendMessage(message);
            return true;
        }
    
        if (args.length - 1 != layer)
            return false;
    
        Player sender = ((Player) commandSender);
        StringPair[] cPairs = new StringPair[]{
                StringPair.markName(args[layer]),
                StringPair.senderName(sender.getDisplayName())
        };
        try
        {
            MarksManager marksManager = rootPlugin.getMarksManager();
            if(marksManager.getMark(args[layer]) != null)
            {
                String message = rootPlugin.getMessagesManager().getMessage(
                        messageKey.append("with-occupied-name"), cPairs);
                if (message != "")
                    sender.sendMessage(message);
                
                return true;
            }
            marksManager.setMark(args[layer],sender.getLocation());
    
    
            String message = rootPlugin.getMessagesManager().getMessage(
                    messageKey.append("completed"), cPairs);
            if (message != "")
                sender.sendMessage(message);
            return true;
        }
        catch(IOException ex)
        {
            ex.printStackTrace();
    
            String message = rootPlugin.getMessagesManager().getMessage(
                    messageKey.append("failed"), cPairs);
            if (message != "")
                sender.sendMessage(message);
            return true;
        }
    }
}