plugins {
    id("com.github.johnrengelman.shadow") version "6.1.0" apply false
}

group = "com.mattworzala"
version = "1.0"

subprojects {
    apply(plugin = "java")
    apply(plugin = "maven-publish")

    repositories {
        mavenCentral()
        maven(url = "https://jitpack.io")

        // Minestom
        maven(url = "https://repo.spongepowered.org/maven")
        maven(url = "https://libraries.minecraft.net")
    }

    dependencies {
        "implementation"("org.jetbrains", "annotations", "20.1.0")
        "implementation"("com.google.code.gson", "gson", "2.8.6")

        "implementation"("net.kyori", "adventure-api", "4.3.0")
        "implementation"("com.github.mworzala", "resource", "d350d0df19")

        "implementation"("com.github.Minestom", "Minestom", "ada1e49d3c")
    }
}