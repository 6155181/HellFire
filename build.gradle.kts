buildscript {
    val agp_version by extra("8.1.1")
    repositories {
        mavenLocal()
        mavenCentral()
        google()
        jcenter()
    }
    dependencies {
        classpath("com.google.gms:google-services:4.4.0")
        classpath("com.android.tools.build:gradle:$agp_version")
        //classpath("com.android.tools.build:gradle:7.0.2")
        //classpath ("com.google.gms:google-services:4.3.10")
    }
}
// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.1.1" apply false
    id("com.android.library") version "7.4.2" apply false
    id("org.jetbrains.kotlin.android") version "1.8.10" apply false
}

