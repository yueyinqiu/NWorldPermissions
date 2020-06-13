package top.nololiyt.worldpermissions;


import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class RootPlugin extends JavaPlugin {
    private LanguageManager languageManager;
    LanguageManager getLanguageManager()
    {
        return languageManager;
    }
    @Override
    public void onEnable() {
        saveDefaultConfig();
        languageManager = new LanguageManager(this);
        
        getCommand("worldpermissions").setExecutor(new RootCommandExecutor(this));
        Bukkit.getPluginManager().registerEvents(
                new PlayerListener(this), this);
    }
}
