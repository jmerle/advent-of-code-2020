package com.jaspervanmerle.aoc2020

import com.jaspervanmerle.aoc2020.day.Day
import org.reflections.Reflections

class Runner {
    private val dayPackage = Day::class.java.packageName

    fun runDay(dayNumber: Int) {
        val clsName = "Day${dayNumber.toString().padStart(2, '0')}"
        val dayCls = Class.forName("$dayPackage.$clsName")

        run(dayCls.getConstructor().newInstance() as Day)
    }

    fun runLatestDay() {
        val dayCls = Reflections(dayPackage)
            .getSubTypesOf(Day::class.java)
            .maxByOrNull { it.simpleName }!!

        run(dayCls.getConstructor().newInstance())
    }

    private fun run(day: Day) {
        println("Running day ${day.number}")

        runPart(1) { day.solvePartOne() }
        runPart(2) { day.solvePartTwo() }
    }

    private fun runPart(number: Int, solver: () -> Any) {
        val result = try {
            solver()
        } catch (err: NotImplementedError) {
            "TODO"
        }

        println("Part $number: $result")
    }
}

fun main(args: Array<String>) {
    val runner = Runner()

    if (args.isNotEmpty()) {
        runner.runDay(args[0].toInt())
    } else {
        runner.runLatestDay()
    }
}
