package top.nololiyt.worldpermissions;

import jdk.internal.util.xml.impl.ReaderUTF8;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import java.util.Scanner;

public class UpdateChecker
{
    private Plugin plugin;
    
    public UpdateChecker(Plugin plugin)
    {
        this.plugin = plugin;
    }
    
    private static final int CURRENT_VERSION = 1;
    public void checkAndLog()
    {
        Bukkit.getScheduler().runTaskAsynchronously(this.plugin, () -> {
            try (InputStream inputStream = new URL(
                    "https://api.github.com/repos/yueyinqiu/NWorldPermissions/releases/latest")
                    .openStream(); Scanner scanner = new Scanner(inputStream))
            {
                if(scanner.hasNextInt())
                {
                    int ver = scanner.nextInt();
                    if(ver > CURRENT_VERSION)
                    {
                        plugin.getLogger().warning("A new version available.");
                    }
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        });
    }
    
    private String readToEnd(InputStream inputStream)
    {
        Scanner scanner = new Scanner(inputStream);
        StringBuilder stringBuilder = new StringBuilder();
        while(scanner.hasNextLine())
        {
            stringBuilder.append(scanner.nextLine());
        }
        return stringBuilder.toString();
    }
}
