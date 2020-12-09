package com.jaspervanmerle.aoc2020.day

import com.jaspervanmerle.aoc2020.ProblemImpossibleError

class Day09 : Day("1309761972", "177989832") {
    private val numbers = getInput()
        .lines()
        .map { it.toLong() }

    private val preambleSize = 25

    override fun solvePartOne(): Any {
        return getFirstInvalidNumber()
    }

    override fun solvePartTwo(): Any {
        val target = getFirstInvalidNumber()

        for (i in numbers.indices) {
            var runningTotal = 0L
            var currentIndex = i

            var minValue = Long.MAX_VALUE
            var maxValue = Long.MIN_VALUE

            while (runningTotal < target) {
                val currentNumber = numbers[currentIndex]

                runningTotal += currentNumber

                if (currentNumber < minValue) {
                    minValue = currentNumber
                }

                if (currentNumber > maxValue) {
                    maxValue = currentNumber
                }

                currentIndex++
            }

            if (runningTotal == target) {
                return minValue + maxValue
            }
        }

        throw ProblemImpossibleError()
    }

    private fun getFirstInvalidNumber(): Long {
        outer@
        for (window in numbers.windowed(preambleSize + 1)) {
            val preamble = window.subList(0, preambleSize)
            val target = window[preambleSize]

            for (a in preamble) {
                for (b in preamble) {
                    if (a != b && a + b == target) {
                        continue@outer
                    }
                }
            }

            return target
        }

        throw ProblemImpossibleError()
    }
}
