package top.nololiyt.worldpermissions.entities

class DotDividedStringBuilder {
    private var stringBuilder: StringBuilder? = null

    override fun toString(): String {
        return stringBuilder!!.toString()
    }

    fun append(name: String): DotDividedStringBuilder {
        this.stringBuilder!!.append('.')
                .append(name)
        return this
    }

    constructor(root: String) {
        this.stringBuilder = StringBuilder()
        this.stringBuilder!!.append(root)
    }

    constructor(builder: DotDividedStringBuilder) {
        this.stringBuilder = StringBuilder()
        this.stringBuilder!!.append(builder.stringBuilder!!.toString())
    }
}