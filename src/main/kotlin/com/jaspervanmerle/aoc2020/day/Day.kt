package com.jaspervanmerle.aoc2020.day

abstract class Day(val number: Int) {
    abstract fun solvePartOne(): Any
    abstract fun solvePartTwo(): Any

    protected fun getInput(): String {
        return readResource("input-${number.toString().padStart(2, '0')}.txt")
    }

    protected fun readResource(name: String): String {
        return this::class.java
            .getResourceAsStream("/$name")
            .bufferedReader()
            .use { it.readText() }
            .trim()
    }
}
