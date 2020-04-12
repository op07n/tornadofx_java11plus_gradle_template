plugins {
    kotlin("jvm") version "1.3.71"
    id("application")
    id("org.openjfx.javafxplugin") version "0.0.9-SNAPSHOT"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    maven("https://oss.sonatype.org/content/repositories/snapshots")
    mavenCentral()
    mavenLocal()
}
dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("no.tornado:tornadofx:2.0.0-SNAPSHOT") {
        exclude(group = "org.openjfx")
    }

    implementation(fileTree("libs") { include("*.jar") })

}
tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }

    javafx {
        version = "14"
        modules("javafx.controls")

    }

    application {
        mainClassName = "com.example.demo.app.MyApp"
    }
}

