import com.github.artfable.gradle.GradleLibsassPluginExtension
import com.github.artfable.gradle.GradleLibsassPluginGroup
import com.github.artfable.gradle.js.importfix.GradleJsImportFixExtension
import com.github.artfable.gradle.npm.repository.GradleNpmRepositoryExtension
import groovy.lang.Closure

group = "org.artfable"
version = "1.0-SNAPSHOT"

buildscript {

    val npm_version = "0.0.3"
    val jsImport_version = "0.0.1"
    val sass_version = "0.0.1"

    repositories {
        mavenLocal()
        jcenter()
        mavenCentral()
    }

    dependencies {
        classpath("com.github.artfable.gradle:gradle-npm-repository-plugin:$npm_version")
        classpath("com.github.artfable.gradle:gradle-js-import-fix-plugin:$jsImport_version")
        classpath("com.github.artfable.gradle:gradle-sass-plugin:$sass_version")
    }
}

apply(plugin = "base")
apply(plugin = "artfable.npm")
apply(plugin = "artfable.js.import.fix")
apply(plugin = "artfable.sass")

configure<GradleNpmRepositoryExtension> {
    val libDir = "$projectDir/src/libs/"
    output = libDir
    if(!file(libDir).exists()) {
        file(libDir).mkdir()
    }
    dependencies = mapOf(
            Pair("@polymer/polymer", "3.0.5"),
            Pair("@polymer/app-layout", "3.0.1"),
            Pair("@polymer/paper-toolbar", "3.0.1"),
            Pair("@polymer/paper-icon-button", "3.0.1"),
            Pair("@polymer/iron-icons", "3.0.1"),
            Pair("@webcomponents/webcomponentsjs", "2.1.3"),
            Pair("bootstrap", "4.1.3")
    )
}

tasks.create<Copy>("copyResources") {
    mustRunAfter("npmLoad")

    from("src") {
        exclude(
                "sass",
                "libs/bootstrap",
                "libs/**/test",
                "**/demo",
                "**/*.ts"
        )
    }

    into("$buildDir/dist")
}

configure<GradleJsImportFixExtension> {
    directory = "$buildDir/dist/libs"
}

configure<GradleLibsassPluginExtension> {
    group(delegateClosureOf<GradleLibsassPluginGroup> {
        sourceDir = "src/sass"
        outputDir = "$buildDir/dist/css"
    } as Closure<Any>)
}

tasks.create("processResources") // to make sass plugin works. Will fix it later

tasks["jsImportFix"].mustRunAfter("npmLoad", "copyResources")
tasks["compileSass"].mustRunAfter("npmLoad", "copyResources")

listOf(tasks["npmLoad"], tasks["jsImportFix"], tasks["compileSass"]).forEach { task ->
    task.group = "frontend"
}

tasks["build"].dependsOn("compileSass", "jsImportFix", "npmLoad", "copyResources")