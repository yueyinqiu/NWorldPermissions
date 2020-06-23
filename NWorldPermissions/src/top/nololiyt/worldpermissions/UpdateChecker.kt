package top.nololiyt.worldpermissions

import jdk.internal.util.xml.impl.ReaderUTF8
import org.bukkit.Bukkit
import org.bukkit.plugin.Plugin
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

import java.io.IOException
import java.io.InputStream
import java.io.Reader
import java.net.URL
import java.util.Scanner

class UpdateChecker(private val plugin: Plugin) {

    fun checkAndLog() {
        Bukkit.getScheduler().runTaskAsynchronously(this.plugin, Runnable {
            try {
                val jsonObject = JSONObject(
                    URL("https://api.github.com/repos/yueyinqiu/NWorldPermissions/releases/latest").readText()
                );
                val tagName = jsonObject.getString("tag_name")

                if (!plugin.description.version.equals(tagName, true)) {
                    val browserDownloadUrl = jsonObject.getJSONArray("assets")
                        .getJSONObject(0)
                        .getString("browser_download_url");

                    plugin.logger.warning(
                        "A new version: '" + tagName + "' is available. " +
                                "It can be downloaded at '" + browserDownloadUrl + "'."
                    )
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }
        });
    }
}
