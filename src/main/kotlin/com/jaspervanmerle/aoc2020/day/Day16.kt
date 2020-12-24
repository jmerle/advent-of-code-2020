package com.jaspervanmerle.aoc2020.day

class Day16 : Day("30869", "4381476149273") {
    private val inputParts = getInput().split("\n\n")

    private val rangesByField = inputParts[0]
        .lines()
        .map { it.split(": ") }
        .fold(mutableMapOf<String, List<IntRange>>()) { acc, lineParts ->
            acc[lineParts[0]] = lineParts[1]
                .split(" or ")
                .map {
                    val (left, right) = it.split("-").map { it.toInt() }
                    left..right
                }

            acc
        }

    private val myTicket = inputParts[1]
        .lines()[1]
        .split(",")
        .map { it.toInt() }

    private val nearbyTickets = inputParts[2]
        .lines()
        .drop(1)
        .map { line -> line.split(",").map { it.toInt() } }

    private val allRanges = rangesByField.values.flatten()

    override fun solvePartOne(): Any {
        return nearbyTickets.sumBy { ticket ->
            ticket.filter { field ->
                allRanges.none { field in it }
            }.sum()
        }
    }

    override fun solvePartTwo(): Any {
        val validTickets = (nearbyTickets + listOf(myTicket))
            .filter { ticket ->
                ticket.none { field ->
                    allRanges.none { field in it }
                }
            }

        val options = rangesByField.keys
            .map { field ->
                val possibleColumns = rangesByField.keys.indices
                    .filter { currentIndex ->
                        validTickets.all { ticket ->
                            rangesByField[field]!!.any { ticket[currentIndex] in it }
                        }
                    }

                field to possibleColumns.toMutableList()
            }
            .toMutableList()

        while (options.any { it.second.size > 1 }) {
            val singles = options.filter { it.second.size == 1 }.flatMap { it.second }
            for ((_, possibleColumns) in options.filter { it.second.size > 1 }) {
                possibleColumns.removeAll(singles)
            }
        }

        return options
            .filter { it.first.startsWith("departure") }
            .map { myTicket[it.second[0]].toLong() }
            .reduce { a, b -> a * b }
    }
}
