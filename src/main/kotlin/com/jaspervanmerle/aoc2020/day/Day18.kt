package com.jaspervanmerle.aoc2020.day

class Day18 : Day("654686398176", "8952864356993") {
    private val parensRegex = "\\(([^()]*)\\)".toRegex()
    private val addRegex = "(\\d+) \\+ (\\d+)".toRegex()
    private val multiplyRegex = "(\\d+) \\* (\\d+)".toRegex()

    override fun solvePartOne(): Any {
        return calculateOutput(this::calculateSimpleMath)
    }

    override fun solvePartTwo(): Any {
        return calculateOutput(this::calculateAdvancedMath)
    }

    private fun calculateOutput(calculator: (String) -> Long): Long {
        return getInput()
            .lines()
            .map { expression ->
                val simplifiedExpression = replaceAll(expression, parensRegex) {
                    calculator(it.groups[1]!!.value).toString()
                }

                calculator(simplifiedExpression)
            }
            .sum()
    }

    private fun calculateSimpleMath(expression: String): Long {
        val parts = expression.split(" ")

        var value = parts[0].toLong()

        for (i in 2 until parts.size step 2) {
            when (parts[i - 1]) {
                "*" -> value *= parts[i].toLong()
                "+" -> value += parts[i].toLong()
            }
        }

        return value
    }

    private fun calculateAdvancedMath(expression: String): Long {
        val addParsed = replaceAll(expression, addRegex) {
            (it.groups[1]!!.value.toLong() + it.groups[2]!!.value.toLong()).toString()
        }

        val multiplyParsed = replaceAll(addParsed, multiplyRegex) {
            (it.groups[1]!!.value.toLong() * it.groups[2]!!.value.toLong()).toString()
        }

        return multiplyParsed.toLong()
    }

    private fun replaceAll(original: String, regex: Regex, replacer: (MatchResult) -> String): String {
        var current = original

        while (true) {
            val oldCurrent = current

            current = current.replace(regex, replacer)

            if (current == oldCurrent) {
                return current
            }
        }
    }
}
