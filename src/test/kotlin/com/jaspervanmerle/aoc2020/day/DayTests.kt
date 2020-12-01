package com.jaspervanmerle.aoc2020.day

import com.jaspervanmerle.aoc2020.dayClasses
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.TestFactory
import java.util.stream.Stream
import kotlin.streams.asStream

class DayTests {
    @TestFactory
    fun generateDayTests(): Stream<DynamicTest> = sequence {
        dayClasses.forEach {
            val day = it.newInstance()

            if (day.answerPartOne != null) {
                yield(createTest(day, 1, day.answerPartOne) { day.solvePartOne() })
            }

            if (day.answerPartTwo != null) {
                yield(createTest(day, 2, day.answerPartTwo) { day.solvePartTwo() })
            }
        }
    }.asStream()

    private fun createTest(day: Day, partNumber: Int, answer: Any?, solver: () -> Any): DynamicTest =
        DynamicTest.dynamicTest("Day ${day.number} Part $partNumber") {
            Assertions.assertEquals(answer, solver())
        }
}
