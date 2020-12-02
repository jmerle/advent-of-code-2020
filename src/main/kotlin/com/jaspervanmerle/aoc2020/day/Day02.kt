package com.jaspervanmerle.aoc2020.day

class Day02 : Day("396", "428") {
    private data class Entry(val left: Int, val right: Int, val character: Char, val password: String)

    private val entries = getInput()
        .lines()
        .map { parseEntry(it) }

    override fun solvePartOne(): Any {
        return entries
            .count { it.password.count { ch -> it.character == ch } in it.left..it.right }
    }

    override fun solvePartTwo(): Any {
        return entries
            .count {
                var occurrences = 0

                if (it.password[it.left - 1] == it.character) {
                    occurrences++
                }

                if (it.password[it.right - 1] == it.character) {
                    occurrences++
                }

                occurrences == 1
            }
    }

    private fun parseEntry(line: String): Entry {
        val mainParts = line.split(" ")

        val firstPart = mainParts[0].split("-")
        val left = firstPart[0].toInt()
        val right = firstPart[1].toInt()

        val character = mainParts[1][0]
        val password = mainParts[2]

        return Entry(left, right, character, password)
    }
}
