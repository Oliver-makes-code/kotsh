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

fun signalHandle(code: Int) {
    println()
//    println("${Ansi.bold}${Ansi.brightRed}Received signal: $code${Ansi.reset}")
    println(with(Ansi) { this {
        append(builder {
            +bold
            +brightRed
            +"Received signal: $code"
            +reset
        })
    }})
    enableFlags()
    exit(127)
}

fun main() {
    signal(SIGINT, staticCFunction(::signalHandle))

    if (isatty(fileno(stdin)) != 0) {
        println("stdin is a tty") // result when running directly from terminal
    } else {
        println("stdin is not a tty") // result when running via gradle
    }

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
    val currentLine = mutableListOf<UByte>()
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
            '\u0008', '\u007f' -> {
                backslash = false
                mostRecentlyTabbed = false
                if (currentLine.isNotEmpty()) {
                    currentLine.removeLast()
                    print("\b \b")
                    print(currentLine.dropLast(1).buildString()) // print input
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
                    val line = currentLine.buildString()
                    currentLine.clear()
                    println()
                    println(tokenize(line))
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
                if (!quoted)
                    doubleQuoted = !doubleQuoted
                else
                    currentLine.add(byte)
            }
            '\'' -> {
                print(byte.toInt().toChar())
                if (!doubleQuoted)
                    quoted = !quoted
                else
                    currentLine.add(byte)
            }
            else -> {
                backslash = false
                mostRecentlyTabbed = false
                currentLine.add(byte)
                print(byte.toInt().toChar())
            }
        }
    }
    lines.add(currentLine) // final line
    println(lines)

    print("Would you like to print the lines as readable text? [y/${Ansi.bold}N${Ansi.boldOff}] ")
    while (true) {
        read(fileno(stdin), singleByteArray, 1)
        val byte = singleByteArray.readBytes(1)[0].toUByte()
        when (byte.toInt().toChar()) {
            'y', 'Y' -> {
                println(with(Ansi) { "${brightBlue}Yes${reset}" })
                lines.forEach { line ->
                    println(line.buildString())
                }
                break
            }
            'n', 'N', '\n' -> {
                println(with(Ansi) { "${brightBlue}No${reset}" })
                break
            }
            '\u0004' -> {
                println(with(Ansi) { "${brightRed}${bold}Aborted${reset}" })
                break
            }
        }
    }

    enableFlags()
}
