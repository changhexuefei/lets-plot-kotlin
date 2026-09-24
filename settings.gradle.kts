/*
 * Copyright (c) 2026. JetBrains s.r.o.
 * Use of this source code is governed by the MIT license that can be found in the LICENSE file.
 */

pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
        google()
    }

    plugins {
        val kotlinVersion = extra["kotlin.version"] as String
        val dokkaVersion = extra["dokka.version"] as String
        val nexusStagingVersion = extra["nexusStaging.version"] as String
        val nexusPublishVersion = extra["nexusPublish.version"] as String

//        val kspVersion = extra["ksp.version"] as String
        val jupyterApiVersion = extra["jupyterApi.version"] as String

        kotlin("multiplatform") version kotlinVersion
        kotlin("jvm").version(kotlinVersion)

        id("org.jetbrains.dokka") version dokkaVersion

        id("io.codearte.nexus-staging") version nexusStagingVersion
        id("io.github.gradle-nexus.publish-plugin") version nexusPublishVersion

//        id("com.google.devtools.ksp") version kspVersion
        kotlin("jupyter.api") version jupyterApiVersion
    }
}

// Optional source-level integration with a local lets-plot core checkout.
// Compatibility CI uses this to validate Kotlin API against the upgraded core
// without publishing unrelated Native artifacts.
System.getenv("LETS_PLOT_CORE_BUILD")
    ?.takeIf { it.isNotBlank() }
    ?.let { coreBuildPath ->
        includeBuild(coreBuildPath) {
            dependencySubstitution {
                substitute(module("org.jetbrains.lets-plot:lets-plot-common"))
                    .using(project(":jvm-package:jvm-publish-common"))
                // lets-plot-swing is a publication-only aggregator whose AWT dependency
                // is injected into its generated POM. Composite builds do not see that POM,
                // so map it directly to the implementation project.
                substitute(module("org.jetbrains.lets-plot:lets-plot-swing"))
                    .using(project(":platf-awt"))
                substitute(module("org.jetbrains.lets-plot:platf-batik"))
                    .using(project(":platf-batik"))
                substitute(module("org.jetbrains.lets-plot:platf-awt"))
                    .using(project(":platf-awt"))
            }
        }
    }

rootProject.name = "lets-plot-kotlin"

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

include("plot-api")
include("demo-common")
include("plot-swing")
include("browser")
include("js-frontend-app")
include("wasmjs-frontend-app")
include("geotools")
include("geotools-swing")
include("dokka")
include("jupyter")
include("geotools-jupyter")
include("json")

project(":demo-common").projectDir = File("./demo/demo-common")
project(":plot-swing").projectDir = File("./demo/plot-swing")
project(":browser").projectDir = File("./demo/browser")
project(":js-frontend-app").projectDir = File("./demo/js-frontend-app")
project(":wasmjs-frontend-app").projectDir = File("./demo/wasmjs-frontend-app")

project(":geotools").projectDir = File("./toolkit/geotools")
project(":geotools-swing").projectDir = File("./demo/geotools-swing")

project(":dokka").projectDir = File("./docs/dokka")

project(":jupyter").projectDir = File("./toolkit/jupyter")
project(":geotools-jupyter").projectDir = File("./toolkit/geotools-jupyter")

project(":json").projectDir = File("./toolkit/json")