package top.nololiyt.worldpermissions.commands.marks;

import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import top.nololiyt.worldpermissions.configurationmanagers.MarksManager;
import top.nololiyt.worldpermissions.RootPlugin;
import top.nololiyt.worldpermissions.entities.DotDividedStringBuilder;
import top.nololiyt.worldpermissions.entities.MessagesSender;
import top.nololiyt.worldpermissions.entities.StringPair;
import top.nololiyt.worldpermissions.commands.Executor;

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
        MessagesSender messagesSender = new MessagesSender(rootPlugin.getMessagesManager(),
                commandSender, null);
    
        if (!(commandSender instanceof Player))
        {
            messagesSender.setArgs(new StringPair[]{
                    StringPair.senderName(commandSender.getName()),
            });
            messagesSender.send(messageKey.append("without-a-position"));
            return true;
        }
    
        if (args.length - 1 != layer)
            return false;
    
        Player player = (Player) commandSender;
    
        String markName = args[layer];
    
        messagesSender.setArgs(new StringPair[]{
                StringPair.markName(markName),
                StringPair.senderName(player.getDisplayName())
        });
        try
        {
            addMark(rootPlugin.getMarksManager(), markName,
                    player.getLocation(), messagesSender,
                    messageKey);
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
            messagesSender.send(messageKey.append("failed"));
        }
        return true;
    }
    
    private void addMark(MarksManager marksManager, String markName, Location location,
                         MessagesSender messagesSender, DotDividedStringBuilder messageKey)
            throws IOException
    {
        if (marksManager.getMark(markName) != null)
        {
            messagesSender.send(messageKey.append("with-occupied-name"));
            return;
        }
    
        marksManager.setMark(markName, location);
        messagesSender.send(messageKey.append("completed"));
    }
}