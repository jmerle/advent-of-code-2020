package com.jaspervanmerle.aoc2020.day

class Day01 : Day(1, 436404, 274879808) {
    private val input = getInput()
        .lines()
        .map { it.toInt() }

    override fun solvePartOne(): Any {
        for (a in input) {
            for (b in input) {
                if (a + b == 2020) {
                    return a * b
                }
            }
        }

        throw Error("Problem impossible")
    }

    override fun solvePartTwo(): Any {
        for (a in input) {
            for (b in input) {
                for (c in input) {
                    if (a + b + c == 2020) {
                        return a * b * c
                    }
                }
            }
        }

        throw Error("Problem impossible")
    }
}
