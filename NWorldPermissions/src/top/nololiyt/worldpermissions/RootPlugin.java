package top.nololiyt.worldpermissions;


import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class RootPlugin extends JavaPlugin {
    private MessagesManager messagesManager;
    
    public MessagesManager getMessagesManager()
    {
        return messagesManager;
    }
    @Override
    public void onEnable() {
        saveDefaultConfig();
        messagesManager = new MessagesManager(this);
        
        getCommand("worldpermissions").setExecutor(new RootCommandExecutor(this));
        Bukkit.getPluginManager().registerEvents(
                new PlayerListener(this), this);
    }
}
