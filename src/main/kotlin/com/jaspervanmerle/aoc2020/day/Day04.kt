package com.jaspervanmerle.aoc2020.day

class Day04 : Day("170", "103") {
    private val passports = getInput()
        .split("\n\n")
        .map { parsePassword(it) }

    private val requiredFields = arrayOf("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid")

    override fun solvePartOne(): Any {
        return passports.count { hasRequiredFields(it) }
    }

    override fun solvePartTwo(): Any {
        val hclRegex = "#[0-9a-f]{6}".toRegex()
        val eclValues = setOf("amb", "blu", "brn", "gry", "grn", "hzl", "oth")

        return passports.count {
            if (!hasRequiredFields(it)) {
                return@count false
            }

            val byr = it.getValue("byr").toInt()
            if (byr < 1920 || byr > 2002) {
                return@count false
            }

            val iyr = it.getValue("iyr").toInt()
            if (iyr < 2010 || iyr > 2020) {
                return@count false
            }

            val eyr = it.getValue("eyr").toInt()
            if (eyr < 2020 || eyr > 2030) {
                return@count false
            }

            val hgt = it.getValue("hgt")
            if (hgt.endsWith("cm")) {
                val cm = hgt.replace("cm", "").toInt()
                if (cm < 150 || cm > 193) {
                    return@count false
                }
            } else if (hgt.endsWith("in")) {
                val inch = hgt.replace("in", "").toInt()
                if (inch < 59 || inch > 76) {
                    return@count false
                }
            } else {
                return@count false
            }

            val hcl = it.getValue("hcl")
            if (!hclRegex.matches(hcl)) {
                return@count false
            }

            val ecl = it.getValue("ecl")
            if (ecl !in eclValues) {
                return@count false
            }

            val pid = it.getValue("pid")
            if (pid.length != 9 || pid.any { ch -> !ch.isDigit() }) {
                return@count false
            }

            return@count true
        }
    }

    private fun parsePassword(passport: String): Map<String, String> {
        return passport
            .lines()
            .joinToString(" ")
            .split(" ")
            .map {
                val parts = it.split(":")
                parts[0] to parts[1]
            }
            .toMap()
    }

    private fun hasRequiredFields(fields: Map<String, String>): Boolean {
        return requiredFields.all { it in fields.keys }
    }
}
