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
    
    public void checkAndLog()
    {
        Bukkit.getScheduler().runTaskAsynchronously(this.plugin, () -> {
            try (InputStream inputStream = new URL(
                    "https://api.github.com/repos/yueyinqiu/NWorldPermissions/releases/latest")
                    .openStream())
            {
                JSONObject jsonObject = new JSONObject(
                        readToEnd(inputStream)
                );
                String tag_name = jsonObject.getString("tag_name");
    
                if (!plugin.getDescription().getVersion().equalsIgnoreCase(tag_name))
                {
                    JSONArray assets = jsonObject.getJSONArray("assets");
                    JSONObject asset = assets.getJSONObject(0);
                    String browser_download_url = asset.getString("browser_download_url");
    
                    plugin.getLogger().warning("A new version: '" + tag_name + "' is available." +
                            "It can be downloaded at '" + browser_download_url + "'.");
                }
            }
            catch (IOException | JSONException e)
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
