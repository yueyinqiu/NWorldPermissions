package top.nololiyt.worldpermissions.entities;

import org.bukkit.command.CommandSender;
import top.nololiyt.worldpermissions.configurationmanagers.MessagesManager;

public class MessagesSender
{
    private CommandSender target;
    private StringPair[] args;
    private MessagesManager messagesManager;
    
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
