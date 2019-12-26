import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    id("org.springframework.boot")
    id("net.ltgt.apt")
}

val copyJar = task("copyJar", type = Copy::class) {
    val bootJar: BootJar by tasks
    from(file(bootJar.destinationDirectory))
    into(bootJar.destinationDirectory)
    rename {
        "${bootJar.archiveBaseName.get()}.${bootJar.archiveExtension.get()}"
    }
}

tasks {

    build {
        dependsOn(copyJar)
    }
}

dependencies {
    annotationProcessor("org.projectlombok:lombok")
    annotationProcessor("org.mapstruct:mapstruct-processor")

    compileOnly("org.projectlombok:lombok")

    runtimeOnly("org.flywaydb:flyway-core")
    runtimeOnly("org.postgresql:postgresql")

    implementation(project(":ums-logging"))
    implementation("com.castlery.commons:commons-sentry")
    implementation("org.zalando:problem-spring-web")

    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-xml")

    implementation("org.springframework.boot:spring-boot-starter-web")



    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("io.opentracing.contrib:opentracing-spring-jaeger-cloud-starter")
    implementation("io.micrometer:micrometer-registry-prometheus")

    testImplementation("com.castlery.commons:commons-test-support")
    testImplementation("com.github.javafaker:javafaker")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}
