
group = "com.mattworzala"
version = "1.0"

tasks.compileJava {
    options.compilerArgs.add("-parameters")
    options.isFork = true
    options.forkOptions.executable = "javac"
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "com.mattworzala.artifact"
            artifactId = "api"
            version = "1.0"

            from(project.components["java"])
        }
    }
}
