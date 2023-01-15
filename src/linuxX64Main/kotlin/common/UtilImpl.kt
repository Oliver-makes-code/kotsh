package de.olivermakesco.kotsh.common

import kotlinx.cinterop.toKString
import platform.posix.getenv

actual val PATH_SEPARATOR: String = ":"
actual fun getEnvironmentVariable(name: String): String? {
    return getenv(name)?.toKString()
}
