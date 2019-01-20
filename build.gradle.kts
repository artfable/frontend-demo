import com.github.artfable.gradle.js.importfix.GradleJsImportFixExtension
import com.github.artfable.gradle.npm.repository.GradleNpmRepositoryExtension

group = "org.artfable"
version = "1.0-SNAPSHOT"

buildscript {

    val npm_version = "0.0.3"
    val jsImport_version = "0.0.1"

    repositories {
        mavenLocal()
        jcenter()
        mavenCentral()
    }

    dependencies {
        classpath("com.github.artfable.gradle:gradle-npm-repository-plugin:$npm_version")
        classpath("com.github.artfable.gradle:gradle-js-import-fix-plugin:$jsImport_version")
    }
}

apply(plugin = "base")
apply(plugin = "artfable.npm")
apply(plugin = "artfable.js.import.fix")

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

configure<GradleJsImportFixExtension> {
    directory = "$projectDir/src/libs"
}

tasks["jsImportFix"].mustRunAfter("npmLoad")

listOf(tasks["npmLoad"], tasks["jsImportFix"]).forEach { task ->
    task.group = "frontend"
}
