package com.jaspervanmerle.aoc2020.day

class Day19 : Day("285", "412") {
    private val inputParts = getInput().split("\n\n")

    private val rules = inputParts[0]
        .lines()
        .map {
            val parts = it.split(": ")
            parts[0].toInt() to parts[1]
        }
        .toMap()
        .toMutableMap()

    private val messages = inputParts[1].lines()

    override fun solvePartOne(): Any {
        return countMatchingMessages(false)
    }

    override fun solvePartTwo(): Any {
        return countMatchingMessages(true)
    }

    private fun countMatchingMessages(advanced: Boolean): Int {
        val regex = "^${parseRule(0, advanced)}$".toRegex()
        return messages.count { regex.matches(it) }
    }

    private fun parseRule(ruleIndex: Int, advanced: Boolean): String {
        val rule = rules[ruleIndex]!!

        if (advanced) {
            if (ruleIndex == 8) {
                return "(${parseRule(42, true)})+"
            } else if (ruleIndex == 11) {
                val first = parseRule(42, true)
                val second = parseRule(31, true)

                val parts = (1..10).map { "(${first.repeat(it)}${second.repeat(it)})" }

                return "(${parts.joinToString("|")})"
            }
        }

        return if (rule.contains(" | ")) {
            val patterns = rule
                .split(" | ")
                .map { parseSingleRule(it, advanced) }

            "(${patterns.joinToString("|")})"
        } else {
            parseSingleRule(rule, advanced)
        }
    }

    private fun parseSingleRule(rule: String, advanced: Boolean): String {
        if (rule.startsWith("\"")) {
            return rule[1].toString()
        }

        val pattern = rule
            .split(" ")
            .joinToString("") { parseRule(it.toInt(), advanced) }

        return "($pattern)"
    }
}
