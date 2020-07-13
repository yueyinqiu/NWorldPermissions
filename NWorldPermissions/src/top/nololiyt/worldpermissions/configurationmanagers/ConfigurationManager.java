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
        reload();
    }
    
    public void reload()
    {
        File file = new File(rootPlugin.getDataFolder().getAbsolutePath(), getFileName());
        if (!file.exists())
            rootPlugin.saveResource(getFileName(), false);
        configuration = YamlConfiguration.loadConfiguration(file);
    }
    
    
    private YamlConfiguration configuration;
    protected YamlConfiguration getConfiguration()
    {
        return configuration;
    }
    protected void saveConfiguration() throws IOException
    {
        configuration.save(new File(
                getRootPlugin().getDataFolder().getAbsolutePath(), "marks.yml"));
    }
}
