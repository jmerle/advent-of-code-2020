package com.jaspervanmerle.aoc2020.day

class Day01 : Day(436404, 274879808) {
    private val entries = getInput()
        .lines()
        .map { it.toInt() }

    override fun solvePartOne(): Any {
        for (a in entries) {
            for (b in entries) {
                if (a + b == 2020) {
                    return a * b
                }
            }
        }

        throw Error("Problem impossible")
    }

    override fun solvePartTwo(): Any {
        for (a in entries) {
            for (b in entries) {
                for (c in entries) {
                    if (a + b + c == 2020) {
                        return a * b * c
                    }
                }
            }
        }

        throw Error("Problem impossible")
    }
}
