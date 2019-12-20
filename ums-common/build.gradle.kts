plugins {
    id("com.commercehub.gradle.plugin.avro") version "0.16.0"
}

dependencies {
    compile("org.springframework.boot:spring-boot-starter-web")
    compile("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("cn.hutool:hutool-all:5.1.0")

    implementation("eu.bitwalker:UserAgentUtils")
//    compile("org.springframework.boot:spring-boot-starter-security")
    testImplementation("org.springframework.boot:spring-boot-starter-test")

}