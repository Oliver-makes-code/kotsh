package de.olivermakesco.kotsh.common.command

import java.nio.file.InvalidPathException
import java.nio.file.Path

actual val String.isPath: Boolean get() {
    return try {
        Path.of(this)
        true
    } catch (_: InvalidPathException) {
        false
    }
}
