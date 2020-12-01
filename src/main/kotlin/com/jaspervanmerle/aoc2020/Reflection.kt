package com.jaspervanmerle.aoc2020

import com.jaspervanmerle.aoc2020.day.Day
import io.github.classgraph.ClassGraph

object Reflection {
    val dayClasses: List<Class<Day>> = ClassGraph()
        .enableClassInfo()
        .acceptPackages(Day::class.java.`package`.name)
        .scan()
        .use {
            val dayClasses = mutableListOf<Class<Day>>()

            for (cls in it.allClasses) {
                if (cls.extendsSuperclass(Day::class.java.name)) {
                    @Suppress("UNCHECKED_CAST")
                    dayClasses.add(cls.loadClass(false) as Class<Day>)
                }
            }

            dayClasses
        }
}
