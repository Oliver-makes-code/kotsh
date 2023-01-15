package de.olivermakesco.kotsh.linux.x64

import de.olivermakesco.kotsh.common.*
import de.olivermakesco.kotsh.common.desktop.*
import kotlinx.cinterop.*
import platform.posix.*

val LFLAGS_TO_DISABLE = listOf(
    ICANON,
    ECHO,
).map { it.toUInt().inv() }.reduce { acc, i -> acc and i }

fun disableFlags() {
    memScoped {
        val termios = alloc<termios>()
        tcgetattr(fileno(stdin), termios.ptr)
        termios.c_lflag = termios.c_lflag and LFLAGS_TO_DISABLE
        tcsetattr(fileno(stdin), TCSANOW, termios.ptr)
    }
}

fun enableFlags() {
    memScoped {
        val termios = alloc<termios>()
        tcgetattr(fileno(stdin), termios.ptr)
        termios.c_lflag = termios.c_lflag or LFLAGS_TO_DISABLE.inv()
        tcsetattr(fileno(stdin), TCSANOW, termios.ptr)
    }
}

val currentLine = mutableListOf<UByte>()

fun signalHandle(code: Int) {
    println()
    when (code) {
        2 -> {
            currentLine.clear()
        }
        else -> {
            println(
                Ansi.builder {
                    +bold
                    +brightRed
                    +"Received signal: $code"
                    +reset
                }
            )
            enableFlags()
            exit(127)
        }
    }
}

fun main() {
    signal(SIGINT, staticCFunction(::signalHandle))

    disableFlags()

    val exampleList = listOf(
        "hello",
        "world",
        "this",
        "is",
        "a",
        "test",
    )

    val singleByteArray = malloc(1)!!
    val lines = mutableListOf<List<UByte>>()
    var currentItem = 0
    var mostRecentlyTabbed = false
    var backslash = false
    var quoted = false
    var doubleQuoted = false
    while (true) {
        read(fileno(stdin), singleByteArray, 1)
        val byte = singleByteArray.readBytes(1)[0].toUByte()
        when (byte.toInt().toChar()) {
            '\u0004' -> break // ctrl+d
            '\t' -> {
                if (backslash) {
                    currentLine.removeLast() // remove backslash
                    currentLine.add(byte) // add tab
                    print('\n')
                    backslash = false
                    continue
                }
                if (mostRecentlyTabbed) {
                    val shiftPressed = false //TODO
                    val prevItem = exampleList[currentItem]
                    currentItem = (currentItem + if (shiftPressed) -1 else 1) posMod exampleList.size
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
            '\u0008', '\u007f' -> { // Backspace
                backslash = false
                mostRecentlyTabbed = false
                if (currentLine.isNotEmpty()) {
                    currentLine.removeLast()
                    print(Ansi.back(currentLine.size + 1)+currentLine.buildString() + " ${Ansi.back}") // print input
                }
            }
            '\\' -> {
                mostRecentlyTabbed = false
                if (backslash) {
                    backslash = false
                    currentLine.add(byte)
                } else {
                    backslash = true
                    currentLine.add(byte)
                    print(byte.toInt().toChar())
                }
            }
            '\n' -> {
                mostRecentlyTabbed = false
                if (!backslash && !quoted && !doubleQuoted) {
                    lines.add(currentLine.map { it }) // copy
                    currentLine.clear()
                    println()
                } else {
                    backslash = false
                    currentLine.add(byte)
                    // shell interprets backslash-newline as line continuation
                    println()
                    print("> ")
                }
            }
            '"' -> {
                print(byte.toInt().toChar())
                currentLine.add(byte)
                if (!quoted)
                    doubleQuoted = !doubleQuoted
            }
            '\'' -> {
                print(byte.toInt().toChar())
                currentLine.add(byte)
                if (!doubleQuoted)
                    quoted = !quoted
            }
            else -> {
                backslash = false
                mostRecentlyTabbed = false
                currentLine.add(byte)
                print(byte.toInt().toChar())
            }
        }
    }

    enableFlags()
}
