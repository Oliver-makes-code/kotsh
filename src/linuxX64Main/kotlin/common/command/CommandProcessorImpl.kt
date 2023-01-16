package de.olivermakesco.kotsh.common.command

actual val String.isPath: Boolean
    get() = startsWith("./") || startsWith("~/") || startsWith("/")
