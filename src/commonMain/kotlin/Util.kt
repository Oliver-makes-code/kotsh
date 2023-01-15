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
