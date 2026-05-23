plugins {
    id("fabric-loom") version "1.6.13"
    id("maven-publish")
    kotlin("jvm") version "1.9.23"
}

version = "1.0.0"
group = "com.secondchance"

repositories {
    mavenCentral()
    maven("https://maven.fabricmc.net/")
    maven("https://maven.architectury.dev/")
}

dependencies {
    minecraft("com.mojang:minecraft:1.21.1")
    mappings(loom.layered {
        officialMojangMappings()
    })
    modImplementation("net.fabricmc:fabric-loader:0.16.9")
    modImplementation("net.fabricmc.fabric-api:fabric-api:0.108.0+1.21.1")
}

tasks.withType<JavaCompile>().configureEach {
    options.release.set(21)
}

java {
    withSourcesJar()
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}
