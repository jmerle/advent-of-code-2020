package com.jaspervanmerle.aoc2020.day

class Day14 : Day("17765746710228", "4401465949086") {
    private abstract class Program {
        protected val memory = mutableMapOf<Long, Long>()

        protected var mask = ""

        protected abstract fun writeValue(address: Long, value: Long)

        fun processInstruction(instruction: String) {
            val (operation, argument) = instruction.split(" = ")

            if (operation == "mask") {
                mask = argument
            } else {
                writeValue(operation.split("[")[1].split("]")[0].toLong(), argument.toLong())
            }
        }

        fun getResult(): Long {
            return memory.values.sum()
        }
    }

    private class ProgramV1 : Program() {
        override fun writeValue(address: Long, value: Long) {
            val binaryValue = value
                .toString(2)
                .padStart(mask.length, '0')

            memory[address] = mask.indices
                .map { if (mask[it] != 'X') mask[it] else binaryValue[it] }
                .joinToString("")
                .toLong(2)
        }
    }

    private class ProgramV2 : Program() {
        override fun writeValue(address: Long, value: Long) {
            val binaryAddress = address
                .toString(2)
                .padStart(mask.length, '0')

            val resolvedAddress = mask.indices
                .map { if (mask[it] != '0') mask[it] else binaryAddress[it] }
                .joinToString("")

            if (!resolvedAddress.contains("X")) {
                memory[resolvedAddress.toLong(2)] = value
                return
            }

            forEachCombination(resolvedAddress.count { it == 'X' }) { combination ->
                var nextIndex = 0
                val actualAddress = mask.indices
                    .map {
                        if (resolvedAddress[it] == 'X') {
                            val combinationValue = combination[nextIndex].toString()
                            nextIndex++
                            combinationValue
                        } else {
                            resolvedAddress[it]
                        }
                    }
                    .joinToString("")
                    .toLong(2)

                memory[actualAddress] = value
            }
        }

        private fun forEachCombination(
            size: Int,
            current: IntArray = IntArray(size),
            nextIndex: Int = 0,
            action: (IntArray) -> Unit
        ) {
            if (nextIndex == size) {
                action(current)
            } else {
                for (i in 0..1) {
                    current[nextIndex] = i
                    forEachCombination(size, current, nextIndex + 1, action)
                }
            }
        }
    }

    private val instructions = getInput().lines()

    override fun solvePartOne(): Any {
        val program = ProgramV1()

        for (instruction in instructions) {
            program.processInstruction(instruction)
        }

        return program.getResult()
    }

    override fun solvePartTwo(): Any {
        val program = ProgramV2()

        for (instruction in instructions) {
            program.processInstruction(instruction)
        }

        return program.getResult()
    }
}
