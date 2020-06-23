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
                URL("https://api.github.com/repos/yueyinqiu/NWorldPermissions/releases/latest")
                        .openStream().use { inputStream ->
                            val jsonObject = JSONObject(
                                    readToEnd(inputStream)
                            )
                            val tag_name = jsonObject.getString("tag_name")

                            if (!plugin.description.version.equals(tag_name, ignoreCase = true)) {
                                val assets = jsonObject.getJSONArray("assets")
                                val asset = assets.getJSONObject(0)
                                val browser_download_url = asset.getString("browser_download_url")

                                plugin.logger.warning("A new version: '" + tag_name + "' is available. " +
                                        "It can be downloaded at '" + browser_download_url + "'.")
                            }
                        }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        });
    }

    private fun readToEnd(inputStream: InputStream): String {
        val scanner = Scanner(inputStream)
        val stringBuilder = StringBuilder()
        while (scanner.hasNextLine()) {
            stringBuilder.append(scanner.nextLine())
        }
        return stringBuilder.toString()
    }
}
