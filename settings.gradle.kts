rootProject.name = "user-management"

include(
        "ums-common",
        "ums-logging",
        "ums-rest",
        "ums-web"
)

pluginManagement {
    repositories {
        gradlePluginPortal()
        maven {
            name = "JCenter Gradle Plugins"
            url = uri("https://dl.bintray.com/gradle/gradle-plugins")
        }
        jcenter()
    }
}
