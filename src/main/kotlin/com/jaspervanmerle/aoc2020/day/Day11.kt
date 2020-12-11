package com.jaspervanmerle.aoc2020.day

class Day11 : Day("2321", "2102") {
    private val initialLayout = getInput()
        .lines()
        .map { it.toCharArray() }
        .toTypedArray()

    private val width = initialLayout[0].size
    private val height = initialLayout.size

    private val adjacentDirections = arrayOf(-1 to -1, 0 to -1, 1 to -1, -1 to 0, 1 to 0, -1 to 1, 0 to 1, 1 to 1)

    override fun solvePartOne(): Any {
        return countOccupiedSeatsAfterMutations(4) { layout, x, y ->
            adjacentDirections.count {
                val newX = x + it.first
                val newY = y + it.second
                newX in 0 until width && newY in 0 until height && layout[newY][newX] == '#'
            }
        }
    }

    override fun solvePartTwo(): Any {
        return countOccupiedSeatsAfterMutations(5) { layout, x, y ->
            adjacentDirections.count {
                var newX = x + it.first
                var newY = y + it.second

                while (newX in 0 until width && newY in 0 until height) {
                    when (layout[newY][newX]) {
                        '#' -> return@count true
                        'L' -> return@count false
                    }

                    newX += it.first
                    newY += it.second
                }

                return@count false
            }
        }
    }

    private fun countOccupiedSeatsAfterMutations(
        occupiedToEmptyThreshold: Int,
        neighborCounter: (layout: Array<CharArray>, x: Int, y: Int) -> Int
    ): Int {
        var currentLayout = duplicateLayout(initialLayout)

        while (true) {
            val newLayout = duplicateLayout(currentLayout)
            var layoutChanged = false

            for (y in 0 until height) {
                for (x in 0 until width) {
                    if (currentLayout[y][x] == '.') {
                        continue
                    }

                    val occupiedNeighbors = neighborCounter(currentLayout, x, y)

                    if (currentLayout[y][x] == 'L') {
                        if (occupiedNeighbors == 0) {
                            newLayout[y][x] = '#'
                            layoutChanged = true
                        }
                    } else {
                        if (occupiedNeighbors >= occupiedToEmptyThreshold) {
                            newLayout[y][x] = 'L'
                            layoutChanged = true
                        }
                    }
                }
            }

            if (!layoutChanged) {
                break
            }

            currentLayout = newLayout
        }

        return currentLayout.sumBy { it.count { char -> char == '#' } }
    }

    private fun duplicateLayout(layout: Array<CharArray>): Array<CharArray> {
        return layout.map { it.clone() }.toTypedArray()
    }
}
