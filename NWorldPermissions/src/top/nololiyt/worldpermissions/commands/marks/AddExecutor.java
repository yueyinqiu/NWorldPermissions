package top.nololiyt.worldpermissions.commands.marks;

import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import top.nololiyt.worldpermissions.configurationmanagers.LocalMarksManager;
import top.nololiyt.worldpermissions.RootPlugin;
import top.nololiyt.worldpermissions.entitiesandtools.DotDividedStringBuilder;
import top.nololiyt.worldpermissions.entitiesandtools.MessagesSender;
import top.nololiyt.worldpermissions.entitiesandtools.StringPair;
import top.nololiyt.worldpermissions.commands.Executor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AddExecutor extends Executor
{
    protected final static String layerName = "add";
    
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
            addMark(rootPlugin.getLocalMarksManager(), markName,
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
    
    private void addMark(LocalMarksManager localMarksManager, String markName, Location location,
                         MessagesSender messagesSender, DotDividedStringBuilder messageKey)
            throws IOException
    {
        if (localMarksManager.getMark(markName) != null)
        {
            messagesSender.send(messageKey.append("with-occupied-name"));
            return;
        }
    
        localMarksManager.setMark(markName, location);
        messagesSender.send(messageKey.append("completed"));
    }
}