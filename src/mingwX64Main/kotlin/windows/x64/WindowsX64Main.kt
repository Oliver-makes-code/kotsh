package de.olivermakesco.kotsh.windows.x64

import platform.posix.fileno
import platform.posix.isatty
import platform.posix.stdin

/*
 * hours wasted waiting for linker:
 * 2
 */
fun main() {
    if (isatty(fileno(stdin)) != 0) {
        println("stdin is a tty") // result when running directly from terminal
    } else {
        println("stdin is not a tty") // result when running via gradle
    }
}
