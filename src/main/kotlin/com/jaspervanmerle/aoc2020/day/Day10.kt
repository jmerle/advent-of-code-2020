package com.jaspervanmerle.aoc2020.day

class Day10 : Day("3000", "193434623148032") {
    private val adapters = getInput()
        .lines()
        .map { it.toInt() }
        .sorted()

    private val device = adapters.last() + 3

    override fun solvePartOne(): Any {
        val diffs = (listOf(0) + adapters + device)
            .windowed(2)
            .groupBy { window -> window[1] - window[0] }

        return diffs[1]!!.size * diffs[3]!!.size
    }

    override fun solvePartTwo(): Any {
        return adapters
            .fold(mapOf(0 to 1L)) { options, joltage ->
                options + (joltage to (1..3).sumOf { options[joltage - it] ?: 0 })
            }[adapters.last()]!!
    }
}
