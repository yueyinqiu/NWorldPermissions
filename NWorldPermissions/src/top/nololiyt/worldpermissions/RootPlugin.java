package top.nololiyt.worldpermissions;


import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

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
        
        getCommand("nworldpermissions").setExecutor(new RootCommandExecutor(this));
        Bukkit.getPluginManager().registerEvents(
                new PlayerListener(this), this);
    
        new UpdateCheckerSpigotMC(this).checkAndLog();
    }
}
