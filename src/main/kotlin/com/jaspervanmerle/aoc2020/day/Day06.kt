package com.jaspervanmerle.aoc2020.day

class Day06 : Day("6457", "3260") {
    private val groups = getInput()
        .split("\n\n")
        .map {
            it
                .lines()
                .map { line -> line.toCharArray().toList() }
        }

    override fun solvePartOne(): Any {
        return groups.sumBy { it.flatten().toSet().size }
    }

    override fun solvePartTwo(): Any {
        return groups.sumBy { it.fold(it[0].toSet()) { acc, list -> acc.intersect(list) }.size }
    }
}
