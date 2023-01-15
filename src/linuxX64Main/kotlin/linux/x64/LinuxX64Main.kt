package de.olivermakesco.kotsh.linux.x64

import kotlinx.cinterop.alloc
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.ptr
import kotlinx.cinterop.readBytes
import platform.posix.*

val LFLAGS_TO_DISABLE = listOf(
    ICANON,
    ECHO,
).map { it.toUInt().inv() }.reduce { acc, i -> acc and i }

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
        termios.c_lflag = termios.c_lflag and LFLAGS_TO_DISABLE
        tcsetattr(fileno(stdin), TCSANOW, termios.ptr)
    }

    val exampleList = listOf(
        "hello",
        "world",
        "this",
        "is",
        "a",
        "test",
    )

    // oh god unsafe code
    val singleByteArray = malloc(1)!!
    val input = mutableListOf<UByte>()
    var currentItem = 0
    while (true) {
        read(fileno(stdin), singleByteArray, 1)
        val byte = singleByteArray.readBytes(1)[0].toUByte()
        when (byte.toInt().toChar()) {
            '\u0004' -> break // ctrl+d
            '\t' -> {
                print("\u001b[2K\r") // clear line
                val item = exampleList[currentItem]
                print(exampleList[currentItem++]) // print item
                currentItem %= exampleList.size // wrap around
                input.clear() // clear input
                input.addAll(item.map { it.code.toUByte() }) // add item to input
            }
            '\u0008', '\u007f' -> {
                if (input.isNotEmpty()) {
                    print("\u001b[1D") // move cursor left
                    print("\u001b[2K\r") // clear line
                    print(input.dropLast(1).map { it.toInt().toChar() }.joinToString("")) // print input
                    input.removeLast() // remove last char
                }
            }
            else -> {
                input.add(byte)
                print(byte.toInt().toChar())
            }
        }
    }
    println(input)
    println("(the input was: ${input.map { it.toInt().toChar() }.joinToString("").trimEnd()})")
}
