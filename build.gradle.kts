plugins {
    kotlin("multiplatform") version "1.8.0"
}

group = "olivermakesco.de"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
}

kotlin {
    jvm()
    linuxX64 {
        binaries {
            executable {
                entryPoint = "de.olivermakesco.kotsh.linux.x64.main"
            }
        }
    }
}
