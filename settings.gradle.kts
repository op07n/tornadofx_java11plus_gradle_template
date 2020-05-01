rootProject.name = "hello-tornadofx"

pluginManagement {
    
  plugins {
    repositories {
      gradlePluginPortal()
    }
    val kotlinVersion: String by extra
    val springBootVersion: String by extra
    val versionsPluginVersion: String by extra
    val dependencyManagementVersion: String by extra
    kotlin("jvm") version kotlinVersion
    kotlin("plugin.spring") version kotlinVersion
    id("org.springframework.boot") version springBootVersion
    id("com.github.ben-manes.versions") version versionsPluginVersion
    id("io.spring.dependency-management") version dependencyManagementVersion
  }    
    
  
    
    repositories {
        mavenLocal()
        mavenCentral()
        maven("https://plugins.gradle.org/m2/")
        maven("https://oss.sonatype.org/content/repositories/snapshots")
    }
}


val rootProjectName: String by extra
rootProject.name = rootProjectName
