package com.jaspervanmerle.aoc2020.day

import com.jaspervanmerle.aoc2020.Reflection
import com.jaspervanmerle.aoc2020.createDay
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.TestFactory
import java.util.stream.Stream
import kotlin.streams.asStream

class DayTests {
    @TestFactory
    fun generateDayTests(): Stream<DynamicTest> = sequence {
        Reflection.getAllDayClasses().forEach {
            val day = it.createDay()

            if (shouldCreateTest(day, 1)) {
                yield(createTest(day, 1))
            }

            if (shouldCreateTest(day, 2)) {
                yield(createTest(day, 2))
            }
        }
    }.asStream()

    private fun shouldCreateTest(day: Day, part: Int): Boolean =
        day.getAnswer(part) != null

    private fun createTest(day: Day, part: Int): DynamicTest =
        DynamicTest.dynamicTest("Day ${day.number} Part $part") {
            Assertions.assertEquals(day.getAnswer(part), day.solve(part))
        }
}
