package top.nololiyt.worldpermissions.configurationmanagers;

import org.bukkit.configuration.file.YamlConfiguration;
import top.nololiyt.worldpermissions.RootPlugin;

import java.io.File;
import java.io.IOException;

public abstract class ConfigurationManager
{
    abstract String getFileName();
    
    private RootPlugin rootPlugin;
    protected RootPlugin getRootPlugin()
    {
        return rootPlugin;
    }
    
    ConfigurationManager(RootPlugin rootPlugin)
    {
        this.rootPlugin = rootPlugin;
        createIfNotExist();
    }
    
    private File createIfNotExist()
    {
        File file = new File(rootPlugin.getDataFolder().getAbsolutePath(), getFileName());
        if (!file.exists())
            rootPlugin.saveResource(getFileName(), false);
        return file;
    }
    
    private YamlConfiguration configuration;
    public void reload()
    {
        File file = createIfNotExist();
        configuration = YamlConfiguration.loadConfiguration(file);
    }
    
    protected YamlConfiguration getConfiguration()
    {
        if(configuration == null)
            reload();
        return configuration;
    }
    
    protected void saveConfiguration() throws IOException
    {
        configuration.save(new File(
                getRootPlugin().getDataFolder().getAbsolutePath(), getFileName()));
    }
}
