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
    jvm {
        compilations.all {
            kotlinOptions.languageVersion = "1.8"
        }
    }
    linuxX64 {
        binaries {
            executable {
                entryPoint = "de.olivermakesco.kotsh.linux.x64.main"
                runTask!!.standardInput = System.`in`
            }
        }
        compilations.all {
            kotlinOptions.languageVersion = "1.8"
        }
    }
    mingwX64 {
        binaries {
            executable {
                entryPoint = "de.olivermakesco.kotsh.windows.x64.main"
                runTask!!.standardInput = System.`in`
            }
        }
        compilations.all {
            kotlinOptions.languageVersion = "1.8"
        }
    }
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
            }
        }
        val desktopMain by creating {
            dependsOn(commonMain)
        }
        val linuxX64Main by getting {
            dependsOn(desktopMain)
        }
        val mingwX64Main by getting {
            dependsOn(desktopMain)
        }
    }
}
