package com.jaspervanmerle.aoc2020.day

class Day17 : Day("346", "1632") {
    private data class Point(val coordinates: List<Int>)

    private abstract class Dimension(val dimensions: Int, input: String) {
        private val cubes = mutableSetOf<Point>()

        private val adjacentDirections = getPoints(-1..1)
            .filter { point -> point.coordinates.any { it != 0 } }

        init {
            input.lines().forEachIndexed { y, line ->
                line.toCharArray().forEachIndexed { x, ch ->
                    if (ch == '#') {
                        addActiveCube(parseInitialLocation(x, y))
                    }
                }
            }
        }

        protected abstract fun parseInitialLocation(x: Int, y: Int): Point

        fun addActiveCube(location: Point) {
            cubes.add(location)
        }

        fun countActiveCubes(): Int {
            return cubes.size
        }

        fun isActive(location: Point): Boolean {
            return location in cubes
        }

        fun countActiveNeighbors(location: Point): Int {
            return adjacentDirections
                .map { direction ->
                    Point(location.coordinates.mapIndexed { index, i -> i + direction.coordinates[index] })
                }
                .count { isActive(it) }
        }

        fun getPoints(range: IntRange): List<Point> {
            val columns = (0 until dimensions).map { range.toList() }

            val cartesianProduct = columns.fold(listOf(listOf<Int>())) { acc, value ->
                acc.flatMap { list ->
                    value.map { element -> list + element }
                }
            }

            return cartesianProduct.map { Point(it) }
        }

        fun getMinCoordinate(): Int {
            return cubes.flatMap { it.coordinates }.minOrNull()!!
        }

        fun getMaxCoordinate(): Int {
            return cubes.flatMap { it.coordinates }.maxOrNull()!!
        }
    }

    private class Dimension3d(input: String = "") : Dimension(3, input) {
        override fun parseInitialLocation(x: Int, y: Int): Point {
            return Point(listOf(x, y, 0))
        }
    }

    private class Dimension4d(input: String = "") : Dimension(4, input) {
        override fun parseInitialLocation(x: Int, y: Int): Point {
            return Point(listOf(x, y, 0, 0))
        }
    }

    override fun solvePartOne(): Any {
        return getActiveCubesAfterBoot(Dimension3d(getInput())) { Dimension3d() }
    }

    override fun solvePartTwo(): Any {
        return getActiveCubesAfterBoot(Dimension4d(getInput())) { Dimension4d() }
    }

    private fun getActiveCubesAfterBoot(initialDimension: Dimension, dimensionCreator: () -> Dimension): Int {
        var currentDimension = initialDimension

        for (i in 1..6) {
            val newDimension = dimensionCreator()

            val minCoord = currentDimension.getMinCoordinate() - 1
            val maxCoord = currentDimension.getMaxCoordinate() + 1
            val pointsToCheck = currentDimension.getPoints(minCoord..maxCoord)

            for (point in pointsToCheck) {
                if (currentDimension.isActive(point)) {
                    if (currentDimension.countActiveNeighbors(point) in 2..3) {
                        newDimension.addActiveCube(point)
                    }
                } else {
                    if (currentDimension.countActiveNeighbors(point) == 3) {
                        newDimension.addActiveCube(point)
                    }
                }
            }

            currentDimension = newDimension
        }

        return currentDimension.countActiveCubes()
    }
}
