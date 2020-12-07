package com.jaspervanmerle.aoc2020.day

class Day07 : Day("289", "30055") {
    private data class Bag(val color: String, val contents: Map<String, Int>) {
        fun contains(targetColor: String, bags: Map<String, Bag>): Boolean {
            return contents.keys.any { it == targetColor || bags.getValue(it).contains(targetColor, bags) }
        }

        fun depth(bags: Map<String, Bag>): Int {
            return contents.entries.sumBy { it.value + it.value * bags.getValue(it.key).depth(bags) }
        }
    }

    private val bags = getInput()
        .lines()
        .map { parseBag(it) }
        .associateBy { it.color }

    override fun solvePartOne(): Any {
        return bags.values.count { it.contains("shiny gold", bags) }
    }

    override fun solvePartTwo(): Any {
        return bags.getValue("shiny gold").depth(bags)
    }

    private fun parseBag(line: String): Bag {
        val color = line.substringBefore(" bags")
        val contents = line
            .substringAfter("contain ")
            .substringBefore(".")
            .split(", ")
            .filter { it != "no other bags" }
            .map {
                val parts = it.split(" ")
                "${parts[1]} ${parts[2]}" to parts[0].toInt()
            }
            .toMap()

        return Bag(color, contents)
    }
}
