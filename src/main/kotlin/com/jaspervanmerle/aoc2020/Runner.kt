package com.jaspervanmerle.aoc2020

import com.jaspervanmerle.aoc2020.day.Day

object Runner {
    fun runDay(dayNumber: Int) {
        val dayPackage = Day::class.java.`package`.name
        val clsName = "Day${dayNumber.toString().padStart(2, '0')}"
        val dayCls = Class.forName("$dayPackage.$clsName")

        run(dayCls.getConstructor().newInstance() as Day)
    }

    fun runLatestDay() {
        val dayCls = Reflection.dayClasses.maxByOrNull { it.simpleName }!!

        run(dayCls.getConstructor().newInstance())
    }

    private fun run(day: Day) {
        println("Running day ${day.number}")

        runPart(1, day.answerPartOne) { day.solvePartOne() }
        runPart(2, day.answerPartTwo) { day.solvePartTwo() }
    }

    private fun runPart(number: Int, answer: Any?, solver: () -> Any) {
        val result = try {
            solver()
        } catch (err: NotImplementedError) {
            "TODO"
        }

        val status = if (answer != null) {
            if (result == answer) {
                "correct"
            } else {
                "incorrect, expected $answer"
            }
        } else {
            null
        }

        val resultWithStatus = if (status != null) "$result ($status)" else "$result"
        println("Part $number: $resultWithStatus")
    }
}

fun main(args: Array<String>) {
    if (args.isNotEmpty()) {
        Runner.runDay(args[0].toInt())
    } else {
        Runner.runLatestDay()
    }
}
