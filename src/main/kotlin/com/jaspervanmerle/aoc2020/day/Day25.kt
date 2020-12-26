package com.jaspervanmerle.aoc2020.day

class Day25 : Day("17032383", "17032383") {
    override fun solvePartOne(): Any {
        val (publicCardKey, publicDoorKey) = getInput()
            .lines()
            .map { it.toLong() }

        val subjectNumber = 7L
        val remainder = 20201227L

        var cardKey = 1L
        var doorKey = 1L

        while (cardKey != publicCardKey) {
            cardKey = (cardKey * subjectNumber) % remainder
            doorKey = (doorKey * publicDoorKey) % remainder
        }

        return doorKey
    }

    override fun solvePartTwo(): Any {
        return solvePartOne()
    }
}
