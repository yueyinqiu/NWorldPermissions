package top.nololiyt.worldpermissions.commands.reload;

import org.bukkit.command.CommandSender;
import top.nololiyt.worldpermissions.RootPlugin;
import top.nololiyt.worldpermissions.commands.Executor;
import top.nololiyt.worldpermissions.entitiesandtools.DotDividedStringBuilder;
import top.nololiyt.worldpermissions.entitiesandtools.StringPair;

public abstract class ReloadExecutor extends Executor
{
    void run(RootPlugin rootPlugin,DotDividedStringBuilder messageKey,CommandSender commandSender)
    {
        run(-1, rootPlugin, null,
                new DotDividedStringBuilder(messageKey).append(messageKey()),
                commandSender, null);
    }
    
    @Override
    protected boolean run(int layer, RootPlugin rootPlugin, DotDividedStringBuilder permission, DotDividedStringBuilder messageKey, CommandSender commandSender, String[] args)
    {
        reload(rootPlugin);
        messageKey.append("completed");
    
        StringPair[] pairs = new StringPair[]{
                StringPair.senderName(commandSender.getName())
        };
    
        rootPlugin.getMessagesManager().sendMessage(
                commandSender, messageKey, pairs);
        return true;
    }
    
    protected abstract void reload(RootPlugin rootPlugin);
}
