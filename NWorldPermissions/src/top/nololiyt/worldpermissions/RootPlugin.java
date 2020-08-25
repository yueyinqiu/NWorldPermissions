package top.nololiyt.worldpermissions;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import top.nololiyt.worldpermissions.configurationmanagers.WorldsManager;
import top.nololiyt.worldpermissions.configurationmanagers.MarksManager;
import top.nololiyt.worldpermissions.configurationmanagers.MessagesManager;
import top.nololiyt.worldpermissions.playerlisteners.JoinAndQuitListener;
import top.nololiyt.worldpermissions.playerlisteners.TeleportListener;

public class RootPlugin extends JavaPlugin
{
    
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
    
    private VersionManager versionManager;
    
    public VersionManager getVersionManager()
    {
        return versionManager;
    }
    private WorldsManager worldsManager;
    
    public WorldsManager getWorldsManager()
    {
        return worldsManager;
    }
    
    
    @Override
    public void onEnable()
    {
        saveDefaultConfig();
    
        worldsManager = new WorldsManager(this);
        messagesManager = new MessagesManager(this);
        marksManager = new MarksManager(this);
        versionManager = new VersionManager(this);
        
        new MarksProvider(this);
        
        getCommand("nworldpermissions").setExecutor(new RootCommandExecutor(this));
    
        Bukkit.getPluginManager().registerEvents(
                new JoinAndQuitListener(this), this);
        Bukkit.getPluginManager().registerEvents(
                new TeleportListener(this), this);
    }
}