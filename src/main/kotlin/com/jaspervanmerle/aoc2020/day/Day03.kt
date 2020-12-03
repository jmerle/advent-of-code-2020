package com.jaspervanmerle.aoc2020.day

class Day03 : Day("265", "3154761400") {
    private val grid = getInput()
        .lines()
        .map { it.toCharArray() }

    override fun solvePartOne(): Any {
        return getPassedTrees(3, 1)
    }

    override fun solvePartTwo(): Any {
        return arrayOf(1 to 1, 3 to 1, 5 to 1, 7 to 1, 1 to 2)
            .map { getPassedTrees(it.first, it.second) }
            .reduce { acc, i -> acc * i }
    }

    private fun getPassedTrees(right: Int, down: Int): Long {
        var trees = 0L

        var x = 0
        var y = 0

        while (y < grid.size) {
            if (grid[y][x % grid[y].size] == '#') {
                trees++
            }

            x += right
            y += down
        }

        return trees
    }
}
