package de.olivermakesco.kotsh.common

expect fun pathSeparator(): String

expect fun getEnvironmentVariable(name: String): String?

//expect fun readSingleChar(): String

val PATH = getEnvironmentVariable("PATH")?.split(pathSeparator()) ?: emptyList()
