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

            for (part in 1..2) {
                val answer = day.getAnswer(part) ?: continue

                yield(DynamicTest.dynamicTest("Day ${day.number} Part $part") {
                    Assertions.assertEquals(answer, day.solve(part))
                })
            }
        }
    }.asStream()
}
