package top.nololiyt.worldpermissions.commands.worlds;

import org.bukkit.command.CommandSender;
import top.nololiyt.worldpermissions.configurationmanagers.MessagesManager;
import top.nololiyt.worldpermissions.RootPlugin;
import top.nololiyt.worldpermissions.commands.Executor;
import top.nololiyt.worldpermissions.entitiesandtools.DotDividedStringBuilder;
import top.nololiyt.worldpermissions.entitiesandtools.MessagesSender;
import top.nololiyt.worldpermissions.entitiesandtools.StringPair;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ControlledExecutor extends Executor
{
    protected final static String layerName = "controlled";
    
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
        MessagesManager messagesManager = rootPlugin.getMessagesManager();
        MessagesSender messagesSender = new MessagesSender(messagesManager,
                commandSender, new StringPair[]{
                StringPair.senderName(commandSender.getName())
        });
        
        List<String> worldName = rootPlugin.getWorldsManager().allControlledWorldsName();
        if (worldName.isEmpty())
        {
            messagesSender.send(messageKey.append("no-controlled-world"));
            return true;
        }
        
        messageKey.append("list");
        String beginning = getMessageItem(messagesManager, messageKey, "beginning");
        String separator = getMessageItem(messagesManager, messageKey, "separator");
        String ending = getMessageItem(messagesManager, messageKey, "ending");
        
        messagesSender.sendJointed(beginning, ending,separator , worldName);
        return true;
    }
    
    private String getMessageItem(MessagesManager messagesManager,
                                  DotDividedStringBuilder messageKey, String nodeName)
    {
        return messagesManager.getItemAndShowError(
                new DotDividedStringBuilder(messageKey).append(nodeName));
    }
}