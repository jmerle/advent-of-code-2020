package com.jaspervanmerle.aoc2020.day

class Day15 : Day("981", "164878") {
    private val startNumbers = getInput()
        .split(",")
        .map { it.toInt() }

    override fun solvePartOne(): Any {
        return getNthNumberSpoken(2020)
    }

    override fun solvePartTwo(): Any {
        return getNthNumberSpoken(30_000_000)
    }

    private fun getNthNumberSpoken(n: Int): Int {
        val lastTurn = mutableMapOf<Int, Int>()

        for (i in startNumbers.indices) {
            lastTurn[startNumbers[i]] = i + 1
        }

        var currentNumber = startNumbers.last()

        for (turn in startNumbers.size until n) {
            val nextNumber = if (currentNumber in lastTurn) {
                turn - lastTurn[currentNumber]!!
            } else {
                0
            }

            lastTurn[currentNumber] = turn
            currentNumber = nextNumber
        }

        return currentNumber
    }
}
