package de.olivermakesco.kotsh.common.command

interface BuiltinCommand {
    fun execute(vararg args: String)
}
