package com.jaspervanmerle.aoc2020.day

import com.jaspervanmerle.aoc2020.ProblemImpossibleError

class Day08 : Day("1723", "846") {
    private data class Instruction(var operation: String, val argument: Int)

    private data class RunResult(val accumulator: Int, val hasLoop: Boolean)

    private data class Program(val instructions: List<Instruction>) {
        fun run(): RunResult {
            var accumulator = 0
            var currentInstruction = 0

            val completedInstructions = mutableSetOf<Int>()

            while (currentInstruction < instructions.size) {
                val instruction = instructions[currentInstruction]

                if (!completedInstructions.add(currentInstruction)) {
                    return RunResult(accumulator, true)
                }

                when (instruction.operation) {
                    "acc" -> {
                        accumulator += instruction.argument
                        currentInstruction += 1
                    }
                    "jmp" -> {
                        currentInstruction += instruction.argument
                    }
                    "nop" -> {
                        currentInstruction += 1
                    }
                }
            }

            return RunResult(accumulator, false)
        }
    }

    private val program = Program(
        getInput()
            .lines()
            .map {
                val parts = it.split(" ")
                Instruction(parts[0], parts[1].toInt())
            }
    )

    override fun solvePartOne(): Any {
        return program.run().accumulator
    }

    override fun solvePartTwo(): Any {
        val initialResult = program.run()
        if (!initialResult.hasLoop) {
            return initialResult.accumulator
        }

        for (instruction in program.instructions) {
            if (instruction.operation == "acc") {
                continue
            }

            val originalOperation = instruction.operation
            instruction.operation = if (instruction.operation == "jmp") "nop" else "jmp"

            val result = program.run()
            if (!result.hasLoop) {
                return result.accumulator
            }

            instruction.operation = originalOperation
        }

        throw ProblemImpossibleError()
    }
}
