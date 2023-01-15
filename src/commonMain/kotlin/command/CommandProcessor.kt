package de.olivermakesco.kotsh.common.command

fun tokenize(input: String): List<String> {
    val output = arrayListOf<String>()

    var currString = ""
    var backslash = false
    var quoted = false
    var doubleQuoted = false
    for (char in input) {
        if (backslash) {
            if (char != '\n') currString += char
            continue
        }
        when (char) {
            ' ' -> {
                if (quoted || doubleQuoted) {
                    currString += char
                } else {
                    output += currString
                    currString = ""
                }
            }
            '"' -> {
                if (!quoted)
                    doubleQuoted = !doubleQuoted
                else
                    output += currString
            }
            '\'' -> {
                if (!doubleQuoted)
                    quoted = !quoted
                else
                    output += currString
            }
            '\\' -> {
                backslash = true
            }
            else -> {
                currString += char
            }
        }
    }
    output += currString

    return output
}