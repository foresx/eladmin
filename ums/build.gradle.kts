import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    id("org.springframework.boot")
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
    implementation("org.mapstruct:mapstruct")

    runtimeOnly("org.flywaydb:flyway-core")
    runtimeOnly("org.postgresql:postgresql")

    implementation(project(":ums-logging"))
    implementation("com.castlery.commons:commons-sentry")

    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-xml")

    implementation("org.springframework.boot:spring-boot-starter-web")

    implementation("org.springframework.boot:spring-boot-starter-data-redis")
    compile("org.apache.commons:commons-pool2:2.5.0")
    compile("org.apache.commons:commons-lang3")
    compile("org.bgee.log4jdbc-log4j2:log4jdbc-log4j2-jdbc4.1:1.16")

    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-cache")
    implementation("com.sun.mail:javax.mail")
    implementation("io.opentracing.contrib:opentracing-spring-jaeger-cloud-starter")
    implementation("io.micrometer:micrometer-registry-prometheus")

    //swagger
    implementation("io.springfox:springfox-swagger2")
    implementation("io.springfox:springfox-swagger-ui")

    testImplementation("com.castlery.commons:commons-test-support")
    testImplementation("com.github.javafaker:javafaker")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}
