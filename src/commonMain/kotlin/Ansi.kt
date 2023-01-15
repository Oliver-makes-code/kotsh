@file:Suppress("unused", "MemberVisibilityCanBePrivate")

package de.olivermakesco.kotsh.common

class Ansi {

    operator fun String.unaryPlus() {
        if (!this.startsWith("$ESC[") || !this.endsWith("m")) {
            sb.append("m$this$ESC[")
        } else {
            if (sb.last() != '[') sb.append(';')
            sb.append(this.substring(2, this.length - 1))
        }
    }

    fun build() = sb.append('m').toString()

    val ESC = Ansi.ESC

    //region: Style
    val reset = Ansi.reset
    val bold = Ansi.bold
    val dim = Ansi.dim
    @LimitedSupport("Sometimes treated as inverse or blink")
    val italic = Ansi.italic
    val underline = Ansi.underline
    val blink = Ansi.blink
    @LimitedSupport(alternate = ReplaceWith("blink"))
    val fastBlink = Ansi.fastBlink
    val reverse = Ansi.reverse
    @LimitedSupport
    val hidden = Ansi.hidden
    @LimitedSupport
    val strikethrough = Ansi.strikethrough

    //region: Fonts
    val defaultFont = Ansi.defaultFont
    val altFont1 = Ansi.altFont1
    val altFont2 = Ansi.altFont2
    val altFont3 = Ansi.altFont3
    val altFont4 = Ansi.altFont4
    val altFont5 = Ansi.altFont5
    val altFont6 = Ansi.altFont6
    val altFont7 = Ansi.altFont7
    val altFont8 = Ansi.altFont8
    val altFont9 = Ansi.altFont9
    //endregion

    @LimitedSupport
    val fraktur = Ansi.fraktur
    @LimitedSupport
    val boldOff = Ansi.boldOff

    val normalColorOrIntensity = Ansi.normalColorOrIntensity
    val italicOff = Ansi.italicOff
    val underlineOff = Ansi.underlineOff
    val blinkOff = Ansi.blinkOff
    @LimitedSupport("No known use in terminals")
    val proportionalSpacing = Ansi.proportionalSpacing
    val inverseOff = Ansi.inverseOff
    val reveal = Ansi.reveal
    val strikethroughOff = Ansi.strikethroughOff
    // Colors, handled by Color object
    @LimitedSupport("No known use in terminals")
    val proportionalSpacingOff = Ansi.proportionalSpacingOff
    val framed = Ansi.framed
    val encircled = Ansi.encircled
    @LimitedSupport("No support in MacOS Terminal")
    val overlined = Ansi.overlined
    val framedOff = "$ESC[54m"
    val encircledOff = framedOff
    val overlinedOff = "$ESC[55m"
    // Underline colors, handled by Color object

    //region: Ideograms
    @LimitedSupport
    val ideogramUnderline = "$ESC[60m"
    @LimitedSupport
    val ideogramDoubleUnderline = "$ESC[61m"
    @LimitedSupport
    val ideogramOverline = "$ESC[62m"
    @LimitedSupport
    val ideogramDoubleOverline = "$ESC[63m"
    @LimitedSupport
    val ideogramStressMarking = Ansi.ideogramStressMarking
    val ideogramOff = Ansi.ideogramOff
    //endregion

    @LimitedSupport
    val superscript = Ansi.superscript
    @LimitedSupport
    val subscript = Ansi.subscript
    val superscriptOff = Ansi.superscriptOff
    val subscriptOff = Ansi.subscriptOff
    //endregion

    //region: Colors
    //region: Simple Colors
    val black = Ansi.black
    val red = Ansi.red
    val green = Ansi.green
    val yellow = Ansi.yellow
    val blue = Ansi.blue
    val magenta = Ansi.magenta
    val cyan = Ansi.cyan
    val white = Ansi.white

    val default = Ansi.default
    //endregion

    //region: Bright Colors
    @LimitedSupport
    val brightBlack = Ansi.brightBlack
    @LimitedSupport
    val brightRed = Ansi.brightRed
    @LimitedSupport
    val brightGreen = Ansi.brightGreen
    @LimitedSupport
    val brightYellow = Ansi.brightYellow
    @LimitedSupport
    val brightBlue = Ansi.brightBlue
    @LimitedSupport
    val brightMagenta = Ansi.brightMagenta
    @LimitedSupport
    val brightCyan = Ansi.brightCyan
    @LimitedSupport
    val brightWhite = Ansi.brightWhite
    //endregion

    //region: Other Stuff
    @LimitedSupport
    val grey = Ansi.grey
    val lightGrey = Ansi.lightGrey

    @LimitedSupport("Not all terminals support underline color changes")
    val defaultUnderline = Ansi.defaultUnderline
    //endregion
    //endreigon

    //region: Operations
    @LimitedSupport("Not all terminals support RGB colors")
    fun rgb(r: Int, g: Int, b: Int) = Ansi.rgb(r,g,b)
    @LimitedSupport("Not all terminals support 8-bit colors")
    fun byteColor(n: Int) = Ansi.byteColor(n)
    fun bg(color: String) = Ansi.bg(color)

    @LimitedSupport("Not all terminals support underline color changes")
    fun underlineRgb(r: Int, g: Int, b: Int) = "$ESC[58;2;$r;$g;${b}m"
    @LimitedSupport("Not all terminals support underline color changes")
    fun underlineByteColor(n: Int) = "$ESC[58;5;${n}m"
    //endregion

    //region: Cursor
    val up = Ansi.up
    val down = Ansi.down
    val forward = Ansi.forward
    val back = Ansi.back
    val nextLine = Ansi.nextLine
    val previousLine = Ansi.previousLine
    val horizontalAbsolute = Ansi.horizontalAbsolute
    val position = Ansi.position
    val eraseAfterCursorInDisplay = Ansi.eraseAfterCursorInDisplay
    val eraseBeforeCursorInDisplay = Ansi.eraseBeforeCursorInDisplay
    val eraseEntireDisplay = Ansi.eraseEntireDisplay
    val eraseAfterCursorInLine = Ansi.eraseAfterCursorInLine
    val eraseBeforeCursorInLine = Ansi.eraseBeforeCursorInLine
    val eraseEntireLine = Ansi.eraseEntireLine
    val scrollUp = Ansi.scrollUp
    val scrollDown = Ansi.scrollDown
    //endregion

    private val sb = StringBuilder("$ESC[")

    companion object {
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

        fun builder(block: Ansi.() -> Unit) = Ansi().apply(block).build()
    }
}
