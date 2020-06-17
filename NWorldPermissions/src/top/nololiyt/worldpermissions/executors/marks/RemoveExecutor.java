package top.nololiyt.worldpermissions.executors.marks;

import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import top.nololiyt.worldpermissions.RootPlugin;
import top.nololiyt.worldpermissions.entities.DotDividedStringBuilder;
import top.nololiyt.worldpermissions.entities.StringPair;
import top.nololiyt.worldpermissions.executors.Executor;

import java.io.File;
import java.io.IOException;

public class RemoveExecutor extends Executor
{
    protected final static String layerName = "remove";
    
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
        if (args.length - 1 != layer)
            return false;
        
        StringPair[] cPairs = new StringPair[]{
                StringPair.markName(args[1]),
                StringPair.senderName(commandSender.getName())
        };
        try
        {
            File file = new File(
                    rootPlugin.getDataFolder().getAbsolutePath(), "marks.yml");
            if (!file.exists())
                file.createNewFile();
            
            YamlConfiguration configuration =
                    YamlConfiguration.loadConfiguration(file);
            
            if(configuration.get(args[layer]) == null)
            {
                commandSender.sendMessage(rootPlugin.getMessagesManager().getMessage(
                        messageKey.append("no-such-mark"), cPairs));
                return true;
            }
            
            configuration.set(args[layer], null);
            configuration.save(file);
        }
        catch(IOException ex)
        {
            ex.printStackTrace();
            commandSender.sendMessage(rootPlugin.getMessagesManager().getMessage(
                    messageKey.append("failed"), cPairs));
            return true;
        }
        commandSender.sendMessage(rootPlugin.getMessagesManager().getMessage(
                messageKey.append("completed"), cPairs));
        return true;
    }
}