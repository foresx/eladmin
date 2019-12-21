plugins {
    id("com.commercehub.gradle.plugin.avro") version "0.16.0"
}

dependencies {
    annotationProcessor("org.mapstruct:mapstruct-processor")
    compile(project(":ums-common"))

}