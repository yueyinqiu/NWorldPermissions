package top.nololiyt.worldpermissions.executors;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import top.nololiyt.worldpermissions.RootPlugin;
import top.nololiyt.worldpermissions.entities.StringPair;

public class CommandRouter
{
    
    private RootPlugin rootPlugin;
    
    public CommandRouter(RootPlugin rootPlugin)
    {
        this.rootPlugin = rootPlugin;
        administer = new ConfigExecutor(rootPlugin);
    }
    
    ConfigExecutor administer;
    public boolean RouteCommand(CommandSender commandSender, Command command,
                                String label, String[] args)
    {
        if (args.length == 0)
        {
            StringPair[] pairs = new StringPair[]{
                    StringPair.senderName(commandSender.getName())
            };
            commandSender.sendMessage(rootPlugin.getMessagesManager()
                    .getMessage("introduce", pairs));
            return true;
        }
        
        boolean result;
        switch (args[0].toLowerCase())
        {
            case "reload":
                result = administer.reload(commandSender, args);
                break;
            case "help":
                result = showHelp(commandSender, args);
                break;
            case "tphere":
                result = tpHere(commandSender, args);
                break;
            case "setofflinehere":
                result = setOfflineHere(commandSender, args);
                break;
            case "recordposition":
                result = recordPosition(commandSender, args);
                break;
            default:
                result = false;
                break;
        }
        
        if (!result)
        {
            StringPair[] seeHelpPairs = new StringPair[]{
                    StringPair.senderName(commandSender.getName())
            };
            commandSender.sendMessage(rootPlugin.getMessagesManager().getMessage(
                    "see-help", seeHelpPairs));
        }
        return true;
    }
    
}
