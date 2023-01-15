package de.olivermakesco.kotsh.linux.x64

import de.olivermakesco.kotsh.common.mod
import kotlinx.cinterop.*
import platform.posix.*

val LFLAGS_TO_DISABLE = listOf(
    ICANON,
    ECHO,
).map { it.toUInt().inv() }.reduce { acc, i -> acc and i }

fun main() {
    if (isatty(fileno(stdin)) != 0) {
        println("stdin is a tty") // result when running directly from terminal
    } else {
        println("stdin is not a tty") // result when running via gradle
    }

    memScoped {
        val termios = alloc<termios>()
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

    val singleByteArray = malloc(1)!!
    val currentLine = mutableListOf<UByte>()
    val lines = mutableListOf<List<UByte>>()
    var currentItem = 0
    var mostRecentlyTabbed = false
    while (true) {
        read(fileno(stdin), singleByteArray, 1)
        val byte = singleByteArray.readBytes(1)[0].toUByte()
        when (byte.toInt().toChar()) {
            '\u0004' -> break // ctrl+d
            '\t' -> {
                if (mostRecentlyTabbed) {
                    val shiftPressed = false //TODO
                    val prevItem = exampleList[currentItem]
                    currentItem = (currentItem + if (shiftPressed) -1 else 1) mod exampleList.size
                    repeat(prevItem.length) {
                        currentLine.removeLast()
                        print("\b \b")
                    }
                } else {
                    currentItem = 0
                }
                mostRecentlyTabbed = true

                currentLine.addAll(exampleList[currentItem].map { it.code.toUByte() })
                print(exampleList[currentItem])
            }
            '\u0008', '\u007f' -> {
                mostRecentlyTabbed = false
                if (currentLine.isNotEmpty()) {
                    currentLine.removeLast()
                    print("\b \b")
                    print(currentLine.dropLast(1).map { it.toInt().toChar() }.joinToString("")) // print input
                }
            }
            '\n' -> {
                mostRecentlyTabbed = false
                lines.add(currentLine.map { it }) // copy
                currentLine.clear()
                println()
            }
            else -> {
                mostRecentlyTabbed = false
                currentLine.add(byte)
                print(byte.toInt().toChar())
            }
        }
    }
    lines.add(currentLine) // final line
    println(lines)
}
