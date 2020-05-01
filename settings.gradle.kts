rootProject.name = "hello-tornadofx"

pluginManagement {
    
  plugins {
    repositories {
      gradlePluginPortal()
    }
  }    
    
  
    
    repositories {
        mavenLocal()
        mavenCentral()
        maven("https://plugins.gradle.org/m2/")
        maven("https://oss.sonatype.org/content/repositories/snapshots")
    }
}

