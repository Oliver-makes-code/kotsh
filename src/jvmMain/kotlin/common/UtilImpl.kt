package de.olivermakesco.kotsh.common

actual fun pathSeparator(): String = System.getProperty("path.separator")
actual fun getEnvironmentVariable(name: String): String? = System.getenv(name)
