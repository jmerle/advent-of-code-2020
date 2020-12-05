package com.jaspervanmerle.aoc2020.day

import com.jaspervanmerle.aoc2020.ProblemImpossibleError

class Day05 : Day("965", "524") {
    private data class Range(val left: Int, val right: Int) {
        fun getLowerHalf(): Range {
            return Range(left, left + ((right - left) / 2))
        }

        fun getUpperHalf(): Range {
            return Range(left + ((right - left) / 2), right)
        }
    }

    private val boardingPassIds = getInput()
        .lines()
        .map {
            var rowRange = Range(0, 128)
            var columnRange = Range(0, 8)

            for (ch in it) {
                when (ch) {
                    'F' -> rowRange = rowRange.getLowerHalf()
                    'B' -> rowRange = rowRange.getUpperHalf()
                    'L' -> columnRange = columnRange.getLowerHalf()
                    'R' -> columnRange = columnRange.getUpperHalf()
                }
            }

            val row = rowRange.left
            val column = columnRange.left

            row * 8 + column
        }
        .sorted()

    override fun solvePartOne(): Any {
        return boardingPassIds.last()
    }

    override fun solvePartTwo(): Any {
        var previous = -1

        for (id in boardingPassIds) {
            if (previous == id - 2) {
                return id - 1
            }

            previous = id
        }

        throw ProblemImpossibleError()
    }
}
