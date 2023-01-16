package de.olivermakesco.kotsh.common.command

interface BuiltinCommand {
    operator fun invoke(vararg args: String)
}
