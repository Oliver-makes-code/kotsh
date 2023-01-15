package de.olivermakesco.kotsh.linux.x64

import kotlinx.cinterop.alloc
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.ptr
import kotlinx.cinterop.readBytes
import platform.posix.*

// kt/n is a mess ngl
fun main() {
    if (isatty(fileno(stdin)) != 0) {
        println("stdin is a tty") // result when running directly from terminal
    } else {
        println("stdin is not a tty") // result when running via gradle
    }

    // what the fuck is going on
    memScoped {
        val termios = alloc<termios>()
        // trading card game extra terrestrial attribute
        // :iea:
        tcgetattr(fileno(stdin), termios.ptr)
        termios.c_lflag = termios.c_lflag and ICANON.toUInt().inv()
        tcsetattr(fileno(stdin), TCSANOW, termios.ptr)
    }

    // oh god unsafe code
    val singleByteArray = malloc(1)!!
    var bytesRead = read(fileno(stdin), singleByteArray, 1)
    val input = mutableListOf<UByte>()
    while (bytesRead > 0) {
        bytesRead = read(fileno(stdin), singleByteArray, 1)
        val byte = singleByteArray.readBytes(1)[0].toUByte()
        if (byte == 4.toUByte()) break
        input.add(byte)
    }
    println(input)
}
