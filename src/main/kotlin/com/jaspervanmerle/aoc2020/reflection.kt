package com.jaspervanmerle.aoc2020

import com.jaspervanmerle.aoc2020.day.Day
import io.github.classgraph.ClassGraph

@Suppress("UNCHECKED_CAST")
val dayClasses: List<Class<Day>> = ClassGraph()
    .enableClassInfo()
    .acceptPackages(Day::class.java.`package`.name)
    .scan()
    .use {
        it.allClasses
            .filter { cls -> cls.extendsSuperclass(Day::class.java.name) }
            .map { cls -> cls.loadClass() as Class<Day> }
    }
