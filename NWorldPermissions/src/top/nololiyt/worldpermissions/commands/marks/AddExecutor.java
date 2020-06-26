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
    
            rootPlugin.getMessagesManager().sendMessage(
                    messageKey.append("without-a-position"), pairs,commandSender);
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
                rootPlugin.getMessagesManager().sendMessage(
                        messageKey.append("with-occupied-name"), cPairs,sender);
                return true;
            }
            marksManager.setMark(args[layer],sender.getLocation());
    
    
            rootPlugin.getMessagesManager().sendMessage(
                    messageKey.append("completed"), cPairs,sender);
            return true;
        }
        catch(IOException ex)
        {
            ex.printStackTrace();
    
            rootPlugin.getMessagesManager().sendMessage(
                    messageKey.append("failed"), cPairs,sender);
            return true;
        }
    }
}