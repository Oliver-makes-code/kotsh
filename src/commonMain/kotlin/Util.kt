package de.olivermakesco.kotsh.common

annotation class LimitedSupport(
    val message: String = "",
    val alternate: ReplaceWith = ReplaceWith(""),
    val unsupportedOs: Array<RuntimeLocation> = [],
    val includeJvmOnUnsupportedOs: Boolean = true,
)

enum class RuntimeLocation {
    Windows,
    Linux,
    MacOS,
    JVM,
}

expect fun pathSeparator(): String

expect fun getEnvironmentVariable(name: String): String?

//expect fun readSingleChar(): String

val PATH = getEnvironmentVariable("PATH")?.split(pathSeparator()) ?: emptyList()

/**
 * Run a modulo operation on a number, but return a positive result.
 */
infix fun Int.posMod(other: Int) = (this % other + other) % other

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
