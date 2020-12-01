package com.jaspervanmerle.aoc2020

import com.jaspervanmerle.aoc2020.day.Day
import org.reflections.Reflections
import java.text.DecimalFormat
import kotlin.system.measureNanoTime

class Runner {
    fun runDay(dayNumber: Int) {
        val pkgName = Day::class.java.packageName
        val clsName = "Day${dayNumber.toString().padStart(2, '0')}"
        val dayCls = Class.forName("$pkgName.$clsName")

        run(dayCls.getConstructor().newInstance() as Day)
    }

    fun runLatestDay() {
        val dayCls = Reflections(Day::class.java.packageName)
            .getSubTypesOf(Day::class.java)
            .maxByOrNull { it.javaClass.simpleName }!!

        run(dayCls.getConstructor().newInstance())
    }

    private fun run(day: Day) {
        println("Running day ${day.number}")

        runPart(1) { day.solvePartOne() }
        runPart(2) { day.solvePartTwo() }
    }

    private fun runPart(number: Int, solver: () -> Any) {
        lateinit var result: Any

        val solvingTime = measureNanoTime {
            result = try {
                solver()
            } catch (err: NotImplementedError) {
                "TODO"
            }
        }

        println("Part $number: $result")

        val df = DecimalFormat("#.#")
        df.minimumFractionDigits = 2
        df.maximumFractionDigits = 2

        println("Time taken for part $number: ${df.format(solvingTime / 1e6)}ms")
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
