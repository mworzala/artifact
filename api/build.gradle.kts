
group = "com.mattworzala"
version = "1.0"

tasks.compileJava {
    options.compilerArgs.add("-parameters")
    options.isFork = true
    options.forkOptions.executable = "javac"
}
