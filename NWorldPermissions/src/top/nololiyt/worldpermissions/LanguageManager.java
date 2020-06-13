package top.nololiyt.worldpermissions;

import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.*;
import java.util.List;

class LanguageManager
{
    
    private RootPlugin rootPlugin;
    
    private YamlConfiguration configuration;
    
    LanguageManager(RootPlugin rootPlugin)
    {
        this.rootPlugin = rootPlugin;
        File file = getLanguageFile();
        if (!file.exists())
        {
            saveDefaultFile(file);
        }
        reloadConfiguration();
    }
    
    private File getLanguageFile()
    {
        return new File(
                rootPlugin.getDataFolder().getAbsolutePath(), "language.yml");
    }
    
    private void saveDefaultFile(File file)
    {
        InputStream res = rootPlugin.getResource("language.yml");
        try
        {
            file.getParentFile().mkdirs();
            file.delete();
            file.createNewFile();
            FileOutputStream fileOutputStream = new FileOutputStream(file);
    
            byte[] buffer = new byte[4096];
            for (; ; )
            {
                int count = res.read(buffer, 0, buffer.length);
                fileOutputStream.write(buffer, 0, count);
                if (count < buffer.length)
                {
                    break;
                }
            }
    
            fileOutputStream.close();
        }
        catch (IOException e)
        {
            rootPlugin.getLogger().warning(e.toString());
        }
    }
    
    void reloadConfiguration()
    {
        configuration = YamlConfiguration.loadConfiguration(
                getLanguageFile());
    }
    
    private String getMessage(String id)
    {
        return configuration.getString("messages." + id,
                "File 'language.yml' is corrupted that 'messages." + id +"' is missing. You may contact the operators.")
                .trim();
    }
    
    String getMessage(String id, StringPair[] stringPairs)
    {
        String str = getMessage(id);
        for (StringPair pair : stringPairs)
        {
            str = str.replace(pair.getKey(), pair.getValue());
        }
        return str;
    }
    
    String getHelp(int page, CommandSender commandSender)
    {
        List<String> helpList = configuration.getStringList("messages.help");
        int size = helpList.size();
        
        String result;
        if (size > page)
        {
            result = helpList.get(page);
        }
        else
        {
            result = helpList.get(size - 1);
        }
        
        StringPair senderPair = StringPair.senderName(commandSender.getName());
        return result.trim().replace(senderPair.getKey(), senderPair.getValue());
    }
}