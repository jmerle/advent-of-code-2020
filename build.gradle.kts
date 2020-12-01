plugins {
    application
    kotlin("jvm") version "1.4.20"
}

group = "com.jaspervanmerle.aoc2020"
version = "1.0.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("org.reflections:reflections:0.9.12")
}

application {
    mainClassName = "$group.RunnerKt"
}
