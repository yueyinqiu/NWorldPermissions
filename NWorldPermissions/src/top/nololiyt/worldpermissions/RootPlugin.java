package top.nololiyt.worldpermissions;


import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import top.nololiyt.worldpermissions.playerlisteners.JoinAndQuitListener;
import top.nololiyt.worldpermissions.playerlisteners.TeleportListener;

public class RootPlugin extends JavaPlugin {
    private MessagesManager messagesManager;
    
    public MessagesManager getMessagesManager()
    {
        return messagesManager;
    }
    
    
    private MarksManager marksManager;
    public MarksManager getMarksManager()
    {
        return marksManager;
    }
    @Override
    public void onEnable() {
        saveDefaultConfig();
        messagesManager = new MessagesManager(this);
        marksManager = new MarksManager(this);
        
        // messagesManager.reloadConfiguration();
        // marksManager.reloadConfiguration();
    
        getCommand("nworldpermissions").setExecutor(new RootCommandExecutor(this));
    
        Bukkit.getPluginManager().registerEvents(
                new JoinAndQuitListener(this), this);
        Bukkit.getPluginManager().registerEvents(
                new TeleportListener(this), this);
    
        new UpdateChecker(this).checkAndLog();
    }
}
