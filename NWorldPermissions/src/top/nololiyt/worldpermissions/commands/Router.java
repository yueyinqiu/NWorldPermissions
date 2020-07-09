package top.nololiyt.worldpermissions.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import top.nololiyt.worldpermissions.RootPlugin;
import top.nololiyt.worldpermissions.entitiesandtools.DotDividedStringBuilder;
import top.nololiyt.worldpermissions.entitiesandtools.StringPair;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;

public abstract class Router implements CommandLayer
{
    protected abstract Map<String, CommandLayer> nextLayers();
    
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
        
        CommandLayer nextLayer = nextLayers().get(args[layer]);
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
    
    
    private void sendHelp(DotDividedStringBuilder messageKey,
                          RootPlugin rootPlugin, CommandSender commandSender)
    {
        messageKey.append("help");
        StringPair[] pairs = new StringPair[]{
                StringPair.senderName(commandSender.getName())
        };
        
        rootPlugin.getMessagesManager().sendMessage(
                commandSender, messageKey, pairs);
    }
    
    @Override
    public List<String> tabComplete(int layer,
                                    RootPlugin rootPlugin,
                                    DotDividedStringBuilder permission,
                                    CommandSender commandSender,
                                    String[] args)
    {
        if (permissionName() != null)
        {
            permission.append(permissionName());
            if (!commandSender.hasPermission(permission.toString()))
            {
                return null;
            }
        }
        
        if (args.length == layer + 1)
        {
            List<String> result = new ArrayList<>();
            for (Map.Entry<String, CommandLayer> entry : nextLayers().entrySet())
            {
                String key = entry.getKey();
                if (!key.startsWith(args[layer]))
                {
                    continue;
                }
                String newNode = entry.getValue().permissionName();
                if (newNode == null)
                {
                    result.add(entry.getKey());
                    continue;
                }
    
                DotDividedStringBuilder permissionCopy = new DotDividedStringBuilder(permission);
                permissionCopy.append(newNode);
                if (commandSender.hasPermission(permissionCopy.toString()))
                {
                    result.add(key);
                }
            }
            return result;
        }
    
        CommandLayer nextLayer = nextLayers().get(args[layer]);
        if (nextLayer != null)
        {
            return nextLayer.tabComplete(
                    layer + 1,
                    rootPlugin,
                    permission,
                    commandSender,
                    args);
        }
    
        return null;
    }
}