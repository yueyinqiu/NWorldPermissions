package top.nololiyt.worldpermissions.commands.worlds;

import org.bukkit.command.CommandSender;
import top.nololiyt.worldpermissions.configurationmanagers.MessagesManager;
import top.nololiyt.worldpermissions.RootPlugin;
import top.nololiyt.worldpermissions.commands.Executor;
import top.nololiyt.worldpermissions.entitiesandtools.DotDividedStringBuilder;
import top.nololiyt.worldpermissions.entitiesandtools.MessagesSender;
import top.nololiyt.worldpermissions.entitiesandtools.StringPair;

import java.util.List;

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
        MessagesSender messagesSender = new MessagesSender(messagesManager,
                commandSender, new StringPair[]{
                StringPair.senderName(commandSender.getName())
        });
        
        List<String> worlds = rootPlugin.getConfig().getStringList("controlled-worlds");
        if (worlds.isEmpty())
        {
            messagesSender.send(messageKey.append("no-controlled-world"));
            return true;
        }
        
        messageKey.append("list");
    
        String beginning = getMessageItem(messagesManager, messageKey, "beginning");
        String separator = getMessageItem(messagesManager, messageKey, "separator");
        String ending = getMessageItem(messagesManager, messageKey, "separator");
        messagesSender.sendJointed(beginning, separator, ending, worlds);
        
        return true;
    }
    
    private String getMessageItem(MessagesManager messagesManager,
                                  DotDividedStringBuilder messageKey, String nodeName)
    {
        return messagesManager.getItemAndShowError(
                new DotDividedStringBuilder(messageKey).append(nodeName));
    }
}