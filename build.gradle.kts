// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath  (BuildPlugins.androidGradlePlugin)
        classpath (BuildPlugins.kotlinGradlePlugin)
        classpath (BuildPlugins.hiltGradlePlugin)
        classpath(BuildPlugins.googleServices)

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
     // Warning: this repository is going to shut down soon
    }
}

tasks.register(name = "type",type = Delete::class){
    delete(rootProject.buildDir)
}