package de.olivermakesco.kotsh.common

expect val PATH_SEPARATOR: String

expect fun getEnvironmentVariable(name: String): String?
