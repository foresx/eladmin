plugins {
    id("com.commercehub.gradle.plugin.avro") version "0.16.0"
}

dependencies {
    annotationProcessor("org.projectlombok:lombok")
    annotationProcessor("org.mapstruct:mapstruct-processor")

    compileOnly("org.projectlombok:lombok")
    compile("org.mapstruct:mapstruct")
    compile("org.springframework.boot:spring-boot-starter-web")
    compile("org.springframework.boot:spring-boot-starter-data-jpa")
    compile("cn.hutool:hutool-all:5.1.0")
    //swagger
    compile("io.springfox:springfox-swagger2")
    compile("io.springfox:springfox-swagger-ui")
    compile("org.springframework.boot:spring-boot-starter-data-redis")
    compile("org.apache.commons:commons-pool2:2.5.0")
    compile("org.apache.commons:commons-lang3")
    compile("org.springframework.boot:spring-boot-starter-security")
    compile("org.springframework.boot:spring-boot-starter-cache")
    compile("com.alibaba:fastjson")
    compile("org.lionsoul:ip2region")
    compile ("commons-codec:commons-codec:1.9")
    compile("org.bgee.log4jdbc-log4j2:log4jdbc-log4j2-jdbc4.1:1.16")
    implementation("eu.bitwalker:UserAgentUtils")
    //    compile("org.springframework.boot:spring-boot-starter-security")
    testImplementation("org.springframework.boot:spring-boot-starter-test")

}