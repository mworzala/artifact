import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

apply(plugin = "com.github.johnrengelman.shadow")

group = "com.mattworzala"
version = "1.0"

repositories {
    // MiniMessage
    maven(url = "https://oss.sonatype.org/content/repositories/snapshots/");
}

dependencies {
    implementation(project(":api"))

    implementation("net.kyori", "adventure-text-minimessage", "4.1.0-SNAPSHOT")
}

tasks {

    compileJava {
        options.compilerArgs.add("-parameters")
        options.isFork = true
        options.forkOptions.executable = "javac"
    }

    java {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    named<ShadowJar>("shadowJar") {
        archiveBaseName.set("artifact")

        dependencies {
            include(project(":api"))
        }
    }

    build {
        dependsOn(named("shadowJar"))
    }


}
