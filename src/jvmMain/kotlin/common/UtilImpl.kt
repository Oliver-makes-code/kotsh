package de.olivermakesco.kotsh.common

actual val PATH_SEPARATOR: String = System.getProperty("path.separator")
actual fun getEnvironmentVariable(name: String): String? = System.getenv(name)
