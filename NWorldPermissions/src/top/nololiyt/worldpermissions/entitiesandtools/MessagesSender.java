package top.nololiyt.worldpermissions.entitiesandtools;

import org.bukkit.command.CommandSender;
import top.nololiyt.worldpermissions.configurationmanagers.MessagesManager;

import java.util.Collection;
import java.util.Set;

public class MessagesSender
{
    private CommandSender target;
    
    public CommandSender getTarget()
    {
        return target;
    }
    
    public void setTarget(CommandSender target)
    {
        this.target = target;
    }
    
    private StringPair[] args;
    public StringPair[] getArgs()
    {
        return args;
    }
    
    public void setArgs(StringPair[] args)
    {
        this.args = args;
    }
    
    private MessagesManager messagesManager;
    public MessagesManager getMessagesManager()
    {
        return messagesManager;
    }
    
    public void setMessagesManager(MessagesManager messagesManager)
    {
        this.messagesManager = messagesManager;
    }
    
    
    public MessagesSender(MessagesManager messagesManager, CommandSender target, StringPair[] args)
    {
        this.messagesManager = messagesManager;
        this.target = target;
        this.args = args;
    }
    public void send(String message)
    {
        messagesManager.sendMessage(target, message, args);
    }
    
    /**
     * items 若长度为 0 ，或者存在参数为 null ， 则不会有任何输出。
     * @param beginning
     * @param ending
     * @param separator
     * @param items
     */
    public void sendJointed(String beginning, String ending, String separator, Iterable<String> items)
    {
        if (beginning == null || separator == null || ending == null || items == null)
            return;
    
        StringBuilder builder = new StringBuilder();
        builder.append(beginning);
        for (String item : items)
        {
            builder.append(item);
            builder.append(separator);
        }
        int builderLength = builder.length();
        
        if (builderLength == beginning.length())
            return;
    
        builder.delete(builderLength - separator.length(), builderLength);
        send(builder.toString());
    }
    
    public void send(DotDividedStringBuilder messageKey)
    {
        messagesManager.sendMessage(target, messageKey, args);
    }
}
