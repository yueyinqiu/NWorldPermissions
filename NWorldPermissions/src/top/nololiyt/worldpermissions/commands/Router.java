package top.nololiyt.worldpermissions.commands;

import org.bukkit.command.CommandSender;
import top.nololiyt.worldpermissions.RootPlugin;
import top.nololiyt.worldpermissions.entities.DotDividedStringBuilder;
import top.nololiyt.worldpermissions.entities.StringPair;

public abstract class Router implements CommandLayer
{
    protected abstract String permissionName();
    
    protected abstract String messageKey();
    
    /**
     * Return the next layer matching the arg.
     * If no layer match it, please return 'null' and the help list will be sent.
     *
     * @param arg
     * @return
     */
    protected abstract CommandLayer nextLayer(String arg);
    
    public void execute(int layer,
                        RootPlugin rootPlugin,
                        DotDividedStringBuilder permission,
                        DotDividedStringBuilder messageKey,
                        CommandSender commandSender,
                        String[] args)
    {
        if (permissionName() != null)
        {
            permission.append(permissionName());
            if (!commandSender.hasPermission(permission.toString()))
            {
                return;
            }
        }
        if (messageKey() != null)
            messageKey.append(messageKey());
    
        if (args.length <= layer)
        {
            sendHelp(messageKey, rootPlugin, commandSender);
            return;
        }
        
        CommandLayer nextLayer = nextLayer(args[layer].toLowerCase());
        if (nextLayer == null)
        {
            sendHelp(messageKey, rootPlugin, commandSender);
            return;
        }
        nextLayer.execute(
                layer + 1,
                rootPlugin,
                permission,
                messageKey,
                commandSender,
                args
        );
    }
    
    
    private boolean sendHelp(DotDividedStringBuilder messageKey,
                             RootPlugin rootPlugin, CommandSender commandSender)
    {
        messageKey.append("help");
        StringPair[] pairs = new StringPair[]{
                StringPair.senderName(commandSender.getName())
        };
        
        rootPlugin.getMessagesManager().sendMessage(
                messageKey, pairs, commandSender);
        return true;
    }
}