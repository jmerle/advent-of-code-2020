package com.jaspervanmerle.aoc2020.day

class Day15 : Day("981", "164878") {
    private val startingNumbers = getInput()
        .split(",")
        .map { it.toInt() }

    override fun solvePartOne(): Any {
        return getSpokenNumber(2020)
    }

    override fun solvePartTwo(): Any {
        return getSpokenNumber(30_000_000)
    }

    private fun getSpokenNumber(n: Int): Int {
        val lastTurn = mutableMapOf<Int, Int>()

        for (i in startingNumbers.indices) {
            lastTurn[startingNumbers[i]] = i + 1
        }

        var currentNumber = startingNumbers.last()

        for (turn in startingNumbers.size until n) {
            val nextNumber = when (currentNumber) {
                in lastTurn -> turn - lastTurn[currentNumber]!!
                else -> 0
            }

            lastTurn[currentNumber] = turn
            currentNumber = nextNumber
        }

        return currentNumber
    }
}
