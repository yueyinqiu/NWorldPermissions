package top.nololiyt.worldpermissions.entities;

import org.bukkit.command.CommandSender;
import top.nololiyt.worldpermissions.configurationmanagers.MessagesManager;

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
    public void send(DotDividedStringBuilder messageKey)
    {
        messagesManager.sendMessage(target, messageKey, args);
    }
}
