package de.olivermakesco.kotsh.common.desktop

fun List<UByte>.buildString() = map { it.toInt().toChar() }.joinToString("")
