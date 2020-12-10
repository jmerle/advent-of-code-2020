package com.jaspervanmerle.aoc2020.day

class Day10 : Day("3000", "193434623148032") {
    private val adapters = getInput()
        .lines()
        .map { it.toInt() }
        .sorted()

    private val device = adapters.last() + 3

    override fun solvePartOne(): Any {
        var currentJolts = 0
        var oneDiffs = 0
        var threeDiffs = 0

        for (adapter in adapters + device) {
            val difference = adapter - currentJolts

            when (difference) {
                1 -> oneDiffs++
                3 -> threeDiffs++
            }

            if (difference in 1..3) {
                currentJolts = adapter
            }
        }

        return oneDiffs * threeDiffs
    }

    override fun solvePartTwo(): Any {
        return (adapters + device)
            .fold(mapOf(0 to 1L)) { options, joltage ->
                options + (joltage to (1..3).sumOf { options[joltage - it] ?: 0 })
            }[device]!!
    }
}
