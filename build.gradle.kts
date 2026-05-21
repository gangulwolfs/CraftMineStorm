plugins {
    id("java")
    id("com.gradleup.shadow") version "8.3.0"
}


group = "gyeoul.kr"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    //mineStom spark fork lib.
    maven("https://repo.hypera.dev/snapshots/") // spark-minestom
    maven("https://repo.lucko.me/") // spark-common
    maven("https://oss.sonatype.org/content/repositories/snapshots/") // spark-common's dependencies

}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    implementation("net.minestom:minestom:2026.05.17c-26.1.1")
    // If you want to use the integration testing library.
    testImplementation("net.minestom:testing:2026.05.17c-26.1.1")
    //mineStom spark. impl.
    implementation("dev.lu15:spark-minestom:1.10-SNAPSHOT")
    //world Format. better. Polar with minestom.
    implementation("dev.hollowcube:polar:1.15.1")
    //Jline -> Console lib.
    implementation("org.jline:jline:3.30.0")
    //Gson lib -> json.
    implementation("com.google.code.gson:gson:2.14.0")
    //slf4j-api -> adventure text logger.
    implementation("org.slf4j:slf4j-api:2.0.18")
    //cache data lib
    implementation("com.github.ben-manes.caffeine:caffeine:3.2.4")


}

tasks.test {
    useJUnitPlatform()
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(25)) // Minestom has a minimum Java version of 25
    }
}

tasks {
    jar {
        manifest {
            attributes["Main-Class"] = "kr.gyeoul.Main" // Change this to your main class
        }
    }

    build {
        dependsOn(shadowJar)
    }
    shadowJar {
        mergeServiceFiles()
        archiveClassifier.set("") // Prevent the -all suffix on the shadowjar file.
    }
}