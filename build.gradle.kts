import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    application
    kotlin("jvm") version "1.4.21"
    id("org.jlleitschuh.gradle.ktlint") version "9.4.1"
}

repositories {
    mavenCentral()
}

group = "com.kylemayes.aoc2020"
version = "1.0.0"

dependencies {
    implementation(kotlin("reflect"))
    implementation(kotlin("stdlib"))
    implementation("org.apache.commons:commons-lang3:3.11")
    implementation("org.apache.commons:commons-math3:3.6.1")
    implementation("com.google.guava:guava:30.1-jre")
    testImplementation(platform("org.junit:junit-bom:5.7.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "9"
}

tasks.test {
    useJUnitPlatform()
}
