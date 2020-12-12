package com.jaspervanmerle.aoc2020.day

import kotlin.math.abs

class Day12 : Day("1589", "23960") {
    private data class Instruction(val action: Char, val value: Int)

    private enum class Direction(val xModifier: Int, val yModifier: Int) {
        NORTH(0, 1),
        EAST(1, 0),
        SOUTH(0, -1),
        WEST(-1, 0);
    }

    private abstract class Ship(var x: Int, var y: Int) {
        abstract fun applyInstruction(instruction: Instruction)

        fun getManhattanDistance(): Int {
            return abs(x) + abs(y)
        }
    }

    private class SimpleShip(x: Int, y: Int, var direction: Direction) : Ship(x, y) {
        override fun applyInstruction(instruction: Instruction) {
            when (instruction.action) {
                'N' -> y += instruction.value
                'S' -> y -= instruction.value
                'E' -> x += instruction.value
                'W' -> x -= instruction.value
                'L' -> rotate(-1 * (instruction.value / 90))
                'R' -> rotate(instruction.value / 90)
                'F' -> {
                    x += direction.xModifier * instruction.value
                    y += direction.yModifier * instruction.value
                }
            }
        }

        private fun rotate(steps: Int) {
            val directions = Direction.values()
            direction = directions[Math.floorMod(direction.ordinal + steps, directions.size)]
        }

        override fun toString(): String {
            return "SimpleShip(x=$x, y=$y, direction=$direction)"
        }
    }

    private class AdvancedShip(x: Int, y: Int, var waypointX: Int, var waypointY: Int) : Ship(x, y) {
        override fun applyInstruction(instruction: Instruction) {
            when (instruction.action) {
                'N' -> waypointY += instruction.value
                'S' -> waypointY -= instruction.value
                'E' -> waypointX += instruction.value
                'W' -> waypointX -= instruction.value
                'L' -> rotate(-instruction.value)
                'R' -> rotate(instruction.value)
                'F' -> {
                    x += waypointX * instruction.value
                    y += waypointY * instruction.value
                }
            }
        }

        fun rotate(degrees: Int) {
            val rotations = mapOf(
                0 to (waypointX to waypointY),
                90 to (waypointY to -waypointX),
                180 to (-waypointX to -waypointY),
                270 to (-waypointY to waypointX)
            )

            val newWaypoint = rotations[(degrees + 360) % 360]!!
            waypointX = newWaypoint.first
            waypointY = newWaypoint.second
        }

        override fun toString(): String {
            return "AdvancedShip(x=$x, y=$y, waypointX=$waypointX, waypointY=$waypointY)"
        }
    }

    private val instructions = getInput()
        .lines()
        .map { Instruction(it[0], it.substring(1).toInt()) }

    override fun solvePartOne(): Any {
        val ship = SimpleShip(0, 0, Direction.EAST)

        for (instruction in instructions) {
            ship.applyInstruction(instruction)
        }

        return ship.getManhattanDistance()
    }

    override fun solvePartTwo(): Any {
        val ship = AdvancedShip(0, 0, 10, 1)

        for (instruction in instructions) {
            ship.applyInstruction(instruction)
        }

        return ship.getManhattanDistance()
    }
}
