package top.nololiyt.worldpermissions.commands.marks

import top.nololiyt.worldpermissions.commands.CommandLayer
import top.nololiyt.worldpermissions.commands.Router

class MarksRouter : Router()
{
    override fun permissionName(): String?
    {
        return layerName
    }
    
    override fun messageKey(): String?
    {
        return layerName
    }
    
    /**
     * Return the next layer matching the arg.
     * If no layer match it, please return 'null' and the help list will be sent.
     *
     * @param arg
     * @return
     */
    override fun nextLayer(arg: String): CommandLayer?
    {
        when (arg)
        {
            "add"    -> return AddExecutor()
            "remove" -> return RemoveExecutor()
            else     -> return null
        }
    }
    
    companion object
    {
        protected val layerName = "marks"
    }
}