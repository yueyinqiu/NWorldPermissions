package top.nololiyt.worldpermissions.commands.reload

import top.nololiyt.worldpermissions.commands.CommandLayer
import top.nololiyt.worldpermissions.commands.Router

class ReloadRouter : Router() {
    override fun permissionName(): String? {
        return layerName
    }

    override fun messageKey(): String? {
        return layerName
    }

    /**
     * Return the next layer matching the arg.
     * If no layer match it, please return 'null' and the help list will be sent.
     *
     * @param arg
     * @return
     */
    override fun nextLayer(arg: String): CommandLayer? {
        when (arg) {
            "config" -> return ConfigExecutor()
            "messages" -> return MessagesExecutor()
            "marks" -> return MarksExecutor()
            else -> return null
        }
    }

    companion object {
        protected val layerName = "reload"
    }
}
