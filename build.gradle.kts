import com.palantir.gradle.gitversion.VersionDetails

allprojects {
    ext {
        set("company", "castlery")
        set("toPublishMaven", false)
    }

    repositories {
        jcenter()
        mavenCentral()
        maven {
            name = "azure"
            url = uri("https://pkgs.dev.azure.com/castlery/_packaging/artifact/maven/v1")
            credentials {
                username = "AZURE_ARTIFACTS"
                password = System.getenv("AZURE_ARTIFACTS_ENV_ACCESS_TOKEN")
                        ?: project.properties["azureArtifactsGradleAccessToken"].toString()
            }
        }
    }

    apply(plugin = "com.palantir.git-version")
    val gitVersion: groovy.lang.Closure<String> by extra
    group = "com.castlery.ums"
    version = gitVersion.call()
}

subprojects {
    apply(plugin = "java")
    apply(plugin = "idea")
    apply(plugin = "com.gorylenko.gradle-git-properties")
    apply(plugin = "io.spring.dependency-management")
    apply(plugin = "io.franzbecker.gradle-lombok")
    apply(plugin = "net.ltgt.apt")
    apply(plugin = "checkstyle")
    apply(plugin = "jacoco")
    apply(plugin = "maven-publish")

    configure<JavaPluginConvention> {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    tasks.withType<Checkstyle>().configureEach {
        reports {
            xml.isEnabled = true
            html.isEnabled = true
        }
        exclude("**/generated/**")
    }
    tasks.withType<Test>().configureEach {
        reports {
            html.isEnabled = true
            junitXml.isEnabled = true
        }
        testLogging.showExceptions = true
        testLogging.showStandardStreams = false
        val springProfiles: String = System.getenv("SPRING_PROFILES_ACTIVE") ?: "test,noSecurity"
        environment("SPRING_PROFILES_ACTIVE", springProfiles)
    }
    configure<JacocoPluginExtension> {
        toolVersion = "0.8.4"
    }
    tasks.withType<JacocoReport>().configureEach {
        reports {
            xml.isEnabled = false
            csv.isEnabled = false
            html.isEnabled = true
        }
    }
    tasks.withType<JacocoCoverageVerification>().configureEach {
        violationRules {
            rule {
                limit {
                    minimum = "0.0".toBigDecimal()
                }
            }
            rule {
                element = "CLASS"
                includes = listOf("com.castlery.*.service.*")

                limit {
                    minimum = "0.0".toBigDecimal()
                }
            }
        }
    }

    configure<PublishingExtension> {
        publications {
            create<MavenPublication>("mavenJava") {
                val sourcesJar by tasks.creating(Jar::class) {
                    val sourceSets: SourceSetContainer by project
                    from(sourceSets["main"].allJava)
                    archiveClassifier.set("sources")
                }
                from(components["java"])
                artifact(sourcesJar)
                versionMapping {
                    usage("java-api") {
                        fromResolutionOf("runtimeClasspath")
                    }
                    usage("java-runtime") {
                        fromResolutionResult()
                    }
                }
            }
        }

        repositories {
            maven {
                url = uri("https://pkgs.dev.azure.com/castlery/_packaging/artifact/maven/v1")
                credentials {
                    username = "AZURE_ARTIFACTS"
                    //The Azure DevOps Services build system will use the "SYSTEM_ACCESSTOKEN" to authenticate to Azure DevOps Services feeds
                    password = System.getenv("AZURE_ARTIFACTS_ENV_ACCESS_TOKEN")
                            ?: project.properties["azureArtifactsGradleAccessToken"].toString()
                }
            }
        }
    }

    gradle.taskGraph.whenReady {
        tasks.findByName("publishMavenJavaPublicationToMavenRepository")?.onlyIf {
            val versionDetails: groovy.lang.Closure<VersionDetails> by extra
            val toPublishMaven = extra["toPublishMaven"] as Boolean
            versionDetails.call().isCleanTag && toPublishMaven
        }
    }

    dependencyManagement {
        val springBootVersion = "2.2.1.RELEASE"
//        val springCloudStreamVersion = "Fishtown.SR3"
        val mapstructVersion = "1.3.0.Final"
        val swaggerVersion = "2.9.2"


        imports {
            mavenBom("org.springframework.boot:spring-boot-dependencies:$springBootVersion")
        }
        dependencies {
            // explicitly define kotlin version here to fix class loading issue in CircleCI
            dependency("org.jetbrains.kotlin:kotlin-stdlib:1.3.10")
            dependency("org.jetbrains.kotlin:kotlin-stdlib-common:1.3.10")

            dependencySet("org.apache.zookeeper:3.4.10") {
                entry("zookeeper") {
                    exclude("log4j:log4j")
                    exclude("org.slf4j:slf4j-log4j12")
                }
            }
            dependency("org.postgresql:postgresql:42.2.5")
            dependency("org.projectlombok:lombok:1.18.0")
            dependency("com.google.guava:guava:25.1-jre")
            dependency("log4j:log4j:1.2.17")
            dependency("org.mapstruct:mapstruct:$mapstructVersion")
            dependency("org.mapstruct:mapstruct-processor:$mapstructVersion")

            //opentracing
            dependencySet("io.opentracing.contrib:2.0.3") {
                entry("opentracing-spring-jaeger-cloud-starter")
                entry("opentracing-spring-jaeger-web-starter")
            }

            //prometheus
            dependency("io.micrometer:micrometer-registry-prometheus:1.1.3")

            // sentry
            dependency("io.sentry:sentry-spring-boot-starter:1.7.23")
            dependency("io.sentry:sentry:1.7.23")

            // user-managerment
            dependency("org.springframework.boot:spring-boot-starter-data-jpa:2.1.6.RELEASE")
            dependency("eu.bitwalker:UserAgentUtils:1.20")
            dependency("com.alibaba:fastjson:1.2.54")
            dependency("com.alibaba:druid-spring-boot-starter:1.1.10")
            dependency("cn.hutool:hutool-all:5.1.0")
            dependency("org.lionsoul:ip2region:1.7.2")
            dependency("org.apache.poi:poi:3.17")
            dependency("org.apache.poi:poi-ooxml:3.17")
            dependency("xerces:xercesImpl:2.11.0")
            dependency("javax.inject:javax.inject:1")
            dependency("org.zalando:problem-spring-web:0.24.0")
            dependency("org.zalando:problem:0.23.0")
            dependency("io.springfox:springfox-swagger2:$swaggerVersion")
            dependency("io.springfox:springfox-swagger-ui:$swaggerVersion")
            dependency("com.github.ory:hydra-client-resttemplate:1.0.0")
            // castlery-commons
            dependencySet("com.castlery.commons:1.0.2") {
                entry("commons-sentry")
                entry("commons-tracing")
                entry("commons-test-support")
                entry("commons-xxl-job-spring-boot-starter")
            }
        }
    }
}

plugins {
    id("org.springframework.boot") version "2.1.2.RELEASE"
    id("io.spring.dependency-management") version "1.0.6.RELEASE"
    id("com.gorylenko.gradle-git-properties") version "2.0.0"
    id("com.palantir.git-version") version "0.11.0"

    id("io.franzbecker.gradle-lombok") version "2.0" apply false
    id("net.ltgt.apt") version "0.20" apply false
    id("org.flywaydb.flyway") version "5.2.4" apply false
}

tasks.register("resolveDependencies") {
    description = "Download all dependencies to the Gradle cache"
    doLast {
        val nonResolveableConfigurations = setOf("api", "apiElements", "implementation",
                "runtimeElements", "runtimeOnly",
                "testImplementation", "testRuntimeOnly",
                "generatedImplementation", "generatedRuntimeOnly")

        project.rootProject.allprojects.forEach { subProject ->
            val configs = subProject.buildscript.configurations.union(subProject.configurations)
            configs.filterNot { c -> nonResolveableConfigurations.contains(c.name) }
                    .forEach { c -> c.resolve() }
        }
    }
}

tasks.register<TestReport>("aggregateTestReport") {
    destinationDir = file("$buildDir/reports/allTests")
    // Include the results from the `test` task in all subprojects
    reportOn(subprojects.map { it.tasks["test"] })
}