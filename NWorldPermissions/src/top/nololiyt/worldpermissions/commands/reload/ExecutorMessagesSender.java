package top.nololiyt.worldpermissions.commands.reload;

import org.bukkit.command.CommandSender;
import top.nololiyt.worldpermissions.RootPlugin;
import top.nololiyt.worldpermissions.entities.DotDividedStringBuilder;
import top.nololiyt.worldpermissions.entities.StringPair;

public class ExecutorMessagesSender
{
    public static void sendMessage(RootPlugin rootPlugin,
                            DotDividedStringBuilder messageKey, CommandSender commandSender)
    {
        messageKey.append("completed");
    
        StringPair[] pairs = new StringPair[]{
                StringPair.senderName(commandSender.getName())
        };
    
        rootPlugin.getMessagesManager().sendMessage(
                commandSender, messageKey, pairs);
    }
}