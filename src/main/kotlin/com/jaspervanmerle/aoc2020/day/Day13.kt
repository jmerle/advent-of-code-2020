package com.jaspervanmerle.aoc2020.day

import kotlin.math.ceil

class Day13 : Day("3882", "867295486378319") {
    private val notes = getInput().lines()

    private val earliestTimestamp = notes[0].toInt()

    private val busIds = notes[1]
        .split(",")
        .map { if (it == "x") null else it.toLong() }

    override fun solvePartOne(): Any {
        return busIds
            .filterNotNull()
            .map {
                val waitTime = ceil(earliestTimestamp.toDouble() / it.toDouble()) * it - earliestTimestamp
                it to waitTime.toInt()
            }
            .minByOrNull { it.second }!!
            .let { it.first * it.second }
    }

    override fun solvePartTwo(): Any {
        return busIds
            .mapIndexed { index, id -> if (id == null) null else id to index.toLong() }
            .filterNotNull()
            .fold(0L to 0L) { (currentOffset, multiplier), (busId, busOffset) ->
                if (multiplier == 0L) {
                    return@fold 0L to busId
                }

                var newOffset = currentOffset
                while ((newOffset + busOffset) % busId != 0L) {
                    newOffset += multiplier
                }

                newOffset to multiplier * busId
            }.first
    }
}
