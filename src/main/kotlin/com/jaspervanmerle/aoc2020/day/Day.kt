package com.jaspervanmerle.aoc2020.day

abstract class Day(private val answerPartOne: String? = null, private val answerPartTwo: String? = null) {
    val number = javaClass.simpleName.replace("Day", "").toInt()

    fun solve(part: Int): String = when (part) {
        1 -> solvePartOne().toString()
        2 -> solvePartTwo().toString()
        else -> throw Error("Invalid part $part")
    }

    fun getAnswer(part: Int): String? = when (part) {
        1 -> answerPartOne
        2 -> answerPartTwo
        else -> throw Error("Invalid part $part")
    }

    protected abstract fun solvePartOne(): Any
    protected abstract fun solvePartTwo(): Any

    protected fun getInput(): String {
        return javaClass
            .getResource("/input-${number.toString().padStart(2, '0')}.txt")
            .readText()
            .trim()
    }
}
