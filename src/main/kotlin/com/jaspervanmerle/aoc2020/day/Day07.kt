package com.jaspervanmerle.aoc2020.day

class Day07 : Day("289", "30055") {
    private data class Bag(val color: String, val contents: MutableMap<String, Int> = mutableMapOf()) {
        fun contains(targetColor: String, bags: Map<String, Bag>): Boolean {
            return contents.keys.any { it == targetColor || bags.getValue(it).contains(targetColor, bags) }
        }

        fun depth(bags: Map<String, Bag>): Int {
            return contents.entries.sumBy { it.value + it.value * bags.getValue(it.key).depth(bags) }
        }
    }

    private val bags = getInput()
        .lines()
        .fold(mutableMapOf<String, Bag>(), { acc, line ->
            parseBag(line, acc)
            acc
        })

    override fun solvePartOne(): Any {
        return bags.values.count { it.contains("shiny gold", bags) }
    }

    override fun solvePartTwo(): Any {
        return bags.getValue("shiny gold").depth(bags)
    }

    private fun parseBag(line: String, bags: MutableMap<String, Bag>) {
        val color = line.substringBefore(" bags")
        val bag = bags.getOrPut(color) { Bag(color) }

        line
            .substringAfter("contain ")
            .substringBefore(".")
            .split(", ")
            .forEach {
                if (it == "no other bags") {
                    return@forEach
                }

                val parts = it.split(" ")
                val entryAmount = parts[0].toInt()
                val entryColor = "${parts[1]} ${parts[2]}"

                bag.contents[entryColor] = entryAmount
            }
    }
}
