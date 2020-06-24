package top.nololiyt.worldpermissions

import org.bukkit.Bukkit
import org.bukkit.plugin.Plugin
import org.json.JSONObject

import java.net.URL

class UpdateChecker(private val plugin: Plugin)
{
    fun checkAndLog()
    {
        Bukkit.getScheduler().runTaskAsynchronously(this.plugin, Runnable {
            try
            {
                val jsonObject = JSONObject(
                    URL("https://api.github.com/repos/yueyinqiu/NWorldPermissions/releases/latest").readText());
                val tagName = jsonObject.getString("tag_name");
                
                if (!plugin.description.version.equals(tagName, true))
                {
                    val browserDownloadUrl =
                        jsonObject.getJSONArray("assets").getJSONObject(0).getString("browser_download_url");
                    
                    plugin.logger.warning(
                        "A new version: '$tagName' is available. It can be downloaded at '$browserDownloadUrl'.");
                }
                
            }
            catch (e: Exception)
            {
                e.printStackTrace();
            }
        });
    }
}