import com.github.artfable.gradle.npm.repository.GradleNpmRepositoryExtension

group = "org.artfable"
version = "1.0-SNAPSHOT"

buildscript {

    val npm_version = "0.0.3"

    repositories {
        mavenLocal()
        jcenter()
        mavenCentral()
    }

    dependencies {
        classpath("com.github.artfable.gradle:gradle-npm-repository-plugin:$npm_version")
    }
}

apply(plugin = "base")
apply(plugin = "artfable.npm")

configure<GradleNpmRepositoryExtension> {
    output = "$projectDir/src/libs/"
    dependencies = mapOf(
            Pair("@polymer/polymer", "3.0.5"),
            Pair("@polymer/app-layout", "3.0.1"),
            Pair("@polymer/paper-toolbar", "3.0.1"),
            Pair("@polymer/paper-icon-button", "3.0.1"),
            Pair("@polymer/iron-icons", "3.0.1"),
            Pair("@webcomponents/webcomponentsjs", "2.1.3")
    )
}

tasks["npmLoad"].group = "frontend"
