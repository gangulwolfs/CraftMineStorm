plugins {
    id("java")
}

group = "gyeoul.kr"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    implementation("net.minestom:minestom:2026.05.17c-1.26.1.1")
    // If you want to use the integration testing library.
    testImplementation("net.minestom:testing:2026.05.17c-1.26.1.1")

}

tasks.test {
    useJUnitPlatform()
}