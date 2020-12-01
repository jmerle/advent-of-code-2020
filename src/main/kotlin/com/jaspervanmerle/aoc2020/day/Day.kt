package com.jaspervanmerle.aoc2020.day

abstract class Day(private val answerPartOne: Any? = null, private val answerPartTwo: Any? = null) {
    val number = javaClass.simpleName.replace("Day", "").toInt()

    fun solve(part: Int): Any = when (part) {
        1 -> solvePartOne()
        2 -> solvePartTwo()
        else -> throw Error("Invalid part $part")
    }

    fun getAnswer(part: Int): Any? = when (part) {
        1 -> answerPartOne
        2 -> answerPartTwo
        else -> throw Error("Invalid part $part")
    }

    protected abstract fun solvePartOne(): Any
    protected abstract fun solvePartTwo(): Any

    protected fun getInput(): String {
        return this::class.java
            .getResourceAsStream("/input-${number.toString().padStart(2, '0')}.txt")
            .bufferedReader()
            .use { it.readText() }
            .trim()
    }
}
