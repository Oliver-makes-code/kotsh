@file:Suppress("unused", "MemberVisibilityCanBePrivate")

package de.olivermakesco.kotsh.common

object Ansi {
    const val ESC = "\u001b"

    //region: Style
    const val reset = "$ESC[0m"
    const val bold = "$ESC[1m"
    const val dim = "$ESC[2m"
    @LimitedSupport("Sometimes treated as inverse or blink")
    const val italic = "$ESC[3m"
    const val underline = "$ESC[4m"
    const val blink = "$ESC[5m"
    @LimitedSupport(alternate = ReplaceWith("blink"))
    const val fastBlink = "$ESC[6m"
    const val reverse = "$ESC[7m"
    @LimitedSupport
    const val hidden = "$ESC[8m"
    @LimitedSupport
    const val strikethrough = "$ESC[9m"

    //region: Fonts
    const val defaultFont = "$ESC[10m"
    const val altFont1 = "$ESC[11m"
    const val altFont2 = "$ESC[12m"
    const val altFont3 = "$ESC[13m"
    const val altFont4 = "$ESC[14m"
    const val altFont5 = "$ESC[15m"
    const val altFont6 = "$ESC[16m"
    const val altFont7 = "$ESC[17m"
    const val altFont8 = "$ESC[18m"
    const val altFont9 = "$ESC[19m"
    //endregion

    @LimitedSupport
    const val fraktur = "$ESC[20m"
    @LimitedSupport
    const val boldOff = "$ESC[21m"

    const val normalColorOrIntensity = "$ESC[22m"
    const val italicOff = "$ESC[23m"
    const val underlineOff = "$ESC[24m"
    const val blinkOff = "$ESC[25m"
    @LimitedSupport("No known use in terminals")
    const val proportionalSpacing = "$ESC[26m"
    const val inverseOff = "$ESC[27m"
    const val reveal = "$ESC[28m"
    const val strikethroughOff = "$ESC[29m"
    // Colors, handled by Color object
    @LimitedSupport("No known use in terminals")
    const val proportionalSpacingOff = "$ESC[50m"
    const val framed = "$ESC[51m"
    const val encircled = "$ESC[52m"
    @LimitedSupport("No support in MacOS Terminal")
    const val overlined = "$ESC[53m"
    const val framedOff = "$ESC[54m"
    const val encircledOff = framedOff
    const val overlinedOff = "$ESC[55m"
    // Underline colors, handled by Color object

    //region: Ideograms
    @LimitedSupport
    const val ideogramUnderline = "$ESC[60m"
    @LimitedSupport
    const val ideogramDoubleUnderline = "$ESC[61m"
    @LimitedSupport
    const val ideogramOverline = "$ESC[62m"
    @LimitedSupport
    const val ideogramDoubleOverline = "$ESC[63m"
    @LimitedSupport
    const val ideogramStressMarking = "$ESC[64m"
    const val ideogramOff = "$ESC[65m"
    //endregion

    @LimitedSupport
    const val superscript = "$ESC[73m"
    @LimitedSupport
    const val subscript = "$ESC[74m"
    const val superscriptOff = "$ESC[75m"
    const val subscriptOff = superscriptOff
    //endregion

    //region: Colors
    //region: Simple Colors
    const val black = "$ESC[30m"
    const val red = "$ESC[31m"
    const val green = "$ESC[32m"
    const val yellow = "$ESC[33m"
    const val blue = "$ESC[34m"
    const val magenta = "$ESC[35m"
    const val cyan = "$ESC[36m"
    const val white = "$ESC[37m"

    const val default = "$ESC[39m"
    //endregion

    //region: Bright Colors
    @LimitedSupport
    const val brightBlack = "$ESC[90m"
    @LimitedSupport
    const val brightRed = "$ESC[91m"
    @LimitedSupport
    const val brightGreen = "$ESC[92m"
    @LimitedSupport
    const val brightYellow = "$ESC[93m"
    @LimitedSupport
    const val brightBlue = "$ESC[94m"
    @LimitedSupport
    const val brightMagenta = "$ESC[95m"
    @LimitedSupport
    const val brightCyan = "$ESC[96m"
    @LimitedSupport
    const val brightWhite = "$ESC[97m"
    //endregion

    //region: Other Stuff
    @LimitedSupport
    const val grey = brightBlack
    const val lightGrey = white

    @LimitedSupport("Not all terminals support underline color changes")
    const val defaultUnderline = "$ESC[59m"
    //endregion

    //region: Operations
    @LimitedSupport("Not all terminals support RGB colors")
    fun rgb(r: Int, g: Int, b: Int) = "$ESC[38;2;$r;$g;${b}m" // braces for `b` are required to not interpret as `bm`
    @LimitedSupport("Not all terminals support 8-bit colors")
    fun byteColor(n: Int) = "$ESC[38;5;${n}m"
    fun bg(color: String) = "$ESC[${color.substring(2, 4).toInt() + 10}${color.substring(4)}"

    @LimitedSupport("Not all terminals support underline color changes")
    fun underlineRgb(r: Int, g: Int, b: Int) = "$ESC[58;2;$r;$g;${b}m"
    @LimitedSupport("Not all terminals support underline color changes")
    fun underlineByteColor(n: Int) = "$ESC[58;5;${n}m"
    //endregion
    //endregion

    //region: Cursor
    const val up = "$ESC[A"
    const val down = "$ESC[B"
    const val forward = "$ESC[C"
    const val back = "$ESC[D"
    const val nextLine = "$ESC[E"
    const val previousLine = "$ESC[F"
    const val horizontalAbsolute = "$ESC[G"
    const val position = "$ESC[H"
    const val eraseAfterCursorInDisplay = "$ESC[J"
    const val eraseBeforeCursorInDisplay = "$ESC[1J"
    const val eraseEntireDisplay = "$ESC[2J"
    const val eraseAfterCursorInLine = "$ESC[K"
    const val eraseBeforeCursorInLine = "$ESC[1K"
    const val eraseEntireLine = "$ESC[2K"
    const val scrollUp = "$ESC[S"
    const val scrollDown = "$ESC[T"

    fun up(n: Int) = "$ESC[${n}A"
    fun down(n: Int) = "$ESC[${n}B"
    fun forward(n: Int) = "$ESC[${n}C"
    fun back(n: Int) = "$ESC[${n}D"
    fun nextLine(n: Int) = "$ESC[${n}E"
    fun previousLine(n: Int) = "$ESC[${n}F"
    fun horizontalAbsolute(n: Int) = "$ESC[${n}G"
    fun position(x: Int, y: Int) = "$ESC[${y};${x}H"
    //endregion

    class GraphicBuilder {
        private val sb = StringBuilder("$ESC[")

        operator fun String.unaryPlus() {
            if (!this.startsWith("$ESC[") || !this.endsWith("m")) throw IllegalArgumentException("Invalid standalone ANSI code: $this")
            if (sb.last() != '[') sb.append(';')
            sb.append(this.substring(2, this.length - 1))
        }

        fun build() = sb.append('m').toString()
    }

    fun builder(block: GraphicBuilder.() -> Unit) = GraphicBuilder().apply(block).build()
}

operator fun Ansi.invoke(block: StringBuilder.() -> Unit): String {
    val builder = StringBuilder()
    builder.block()
    return builder.toString()
}
