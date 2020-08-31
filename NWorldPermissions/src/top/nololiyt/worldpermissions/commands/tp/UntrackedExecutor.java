package top.nololiyt.worldpermissions.commands.tp;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import top.nololiyt.worldpermissions.MarksAPILinker;
import top.nololiyt.worldpermissions.RootPlugin;
import top.nololiyt.worldpermissions.commands.Executor;
import top.nololiyt.worldpermissions.entitiesandtools.DotDividedStringBuilder;
import top.nololiyt.worldpermissions.entitiesandtools.MessagesSender;
import top.nololiyt.worldpermissions.entitiesandtools.OfflinePlayersPosition;
import top.nololiyt.worldpermissions.entitiesandtools.StringPair;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UntrackedExecutor extends Executor
{
    protected final static String layerName = "untracked";
    
    @Override
    public String permissionName()
    {
        return null;
    }
    
    @Override
    public String messageKey()
    {
        return layerName;
    }
    
    @Override
    public List<String> getTabComplete(RootPlugin rootPlugin, int ordinal)
    {
        switch (ordinal)
        {
            case 0:
                MarksAPILinker marksAPI = rootPlugin.getMarksAPILinker();
                return marksAPI == null ?
                        new ArrayList<>(rootPlugin.getLocalMarksManager().allMarksName()) :
                        marksAPI.getMarksAPI().getAllMarksKey(() -> null);
            default:
                return new ArrayList<>();
        }
    }
    
    @Override
    protected boolean run(int layer, RootPlugin rootPlugin,
                          DotDividedStringBuilder messageKey, CommandSender commandSender,
                          String[] args)
    {
        if (args.length - 1 != layer)
            return false;
    
        String markName = args[layer];
    
        MessagesSender messagesSender = new MessagesSender(rootPlugin.getMessagesManager(),
                commandSender, new StringPair[]{
                null, null,
                StringPair.markName(markName),
                StringPair.senderName(commandSender.getName())
        });
    
        if ((!rootPlugin.getConfig().getBoolean("offline-players-tracker.enabled")))
        {
            messagesSender.send(messageKey.append("tracker-not-enabled"));
            return true;
        }
    
        MarksAPILinker marksAPI = rootPlugin.getMarksAPILinker();
        Location location = marksAPI == null ?
                rootPlugin.getLocalMarksManager().getMark(markName) :
                marksAPI.getMarksAPI().getMark(markName, () -> commandSender);
        if (location == null)
        {
            messagesSender.send(messageKey.append("no-such-mark"));
            return true;
        }
    
        File dir = new File(rootPlugin.getDataFolder().getAbsolutePath(), "playersData");
        dir.mkdirs();
        String dirPath = dir.getAbsolutePath();
        
        long suc = 0;
        long fai = 0;
        DotDividedStringBuilder failureMessage = new DotDividedStringBuilder(messageKey).append("");
        for (OfflinePlayer player : Bukkit.getOfflinePlayers())
        {
            File file = new File(dirPath, player.getUniqueId().toString() + ".yml");
            if (file.exists())
                continue;
    
            messagesSender.getArgs()[0] = StringPair.playerName(player.getName());
            try
            {
                new OfflinePlayersPosition(location, true).saveTo(file);
                suc++;
            }
            catch (IOException ex)
            {
                fai++;
                messagesSender.send(failureMessage);
                ex.printStackTrace();
            }
        }
    
        messagesSender.getArgs()[0] = StringPair.teleportedCount(String.valueOf(suc));
        messagesSender.getArgs()[1] = StringPair.unteleportedCount(String.valueOf(fai));
        messagesSender.send(messageKey.append("completed"));
        return true;
    }
}