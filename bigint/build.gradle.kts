import tech.antibytes.gradle.dependency.Dependency
import io.cryptopunks.gradle.client.dependency.Dependency as LocalDependency
import org.jetbrains.kotlin.gradle.dsl.KotlinCompile
import io.cryptopunks.gradle.client.dependency.Npm

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.multiplatform")

    id("kotlin-parcelize")

    id("tech.antibytes.gradle.configuration")
    id("tech.antibytes.gradle.coverage")
    id("tech.antibytes.kmock.kmock-gradle")

    id("org.mozilla.rust-android-gradle.rust-android")
}

val rustDir = "${projectDir.absolutePath.trimEnd('/')}/rsrc"

kotlin {
    android()

    jvm()

    js(IR) {
        compilations {
            this.forEach {
                it.compileKotlinTask.kotlinOptions.sourceMap = true
                it.compileKotlinTask.kotlinOptions.metaInfo = true

                if (it.name == "main") {
                    it.compileKotlinTask.kotlinOptions.main = "call"
                }
            }
        }

        browser {
            testTask {
                useKarma {
                    useChromeHeadlessNoSandbox()
                }
            }
        }
    }

    sourceSets {
        all {
            languageSettings.apply {
                optIn("kotlin.ExperimentalUnsignedTypes")
                optIn("kotlin.RequiresOptIn")
            }
        }

        val commonMain by getting {
            dependencies {
                implementation(Dependency.multiplatform.kotlin.common)
                implementation(project(":util"))
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(Dependency.multiplatform.test.common)
                implementation(Dependency.multiplatform.test.annotations)

                implementation(LocalDependency.antibytes.test.kmp.core)
                implementation(LocalDependency.antibytes.test.kmp.annotations)
                implementation(LocalDependency.antibytes.test.kmp.fixture)
                implementation(LocalDependency.antibytes.test.kmp.kmock)
            }
        }

        val androidMain by getting {
            dependencies {
                implementation(Dependency.multiplatform.kotlin.android)
            }
        }
        val androidAndroidTestRelease by getting
        val androidTestFixtures by getting
        val androidTestFixturesDebug by getting
        val androidTestFixturesRelease by getting
        val androidTest by getting {
            dependsOn(androidTestFixtures)
            dependsOn(androidTestFixturesDebug)
            dependsOn(androidTestFixturesRelease)
            dependencies {
                implementation(Dependency.multiplatform.test.jvm)
                implementation(Dependency.multiplatform.test.junit)
                implementation(Dependency.android.test.robolectric)
            }
        }

        val androidAndroidTest by getting {
            dependsOn(androidAndroidTestRelease)
            dependencies {
                implementation(Dependency.jvm.test.junit)
                implementation(Dependency.android.test.junit)
                implementation(Dependency.android.test.espressoCore)
                implementation(Dependency.android.test.uiAutomator)
            }
        }

        val jvmMain by getting {
            resources.srcDir("$buildDir/generated/rust/")
            dependencies {
                implementation(Dependency.multiplatform.kotlin.jdk8)
                implementation(LocalDependency.jarNativeBundler)
            }
        }
        val jvmTest by getting {
            dependencies {
                implementation(Dependency.multiplatform.test.jvm)
                implementation(Dependency.multiplatform.test.junit)
            }
        }

        val jsMain by getting {
            dependencies {
                implementation(Dependency.multiplatform.kotlin.js)
                implementation(npm(Npm.dependency.bigInteger.first, Npm.dependency.bigInteger.second))
                implementation(Dependency.js.nodejs)
                implementation(LocalDependency.bigNumbers)

            }
        }

        val jsTest by getting {
            dependencies {
                implementation(Dependency.multiplatform.test.js)
            }
        }
    }
}

android {
    ndkVersion = "24.0.8215888"

    defaultConfig {
        ndk.abiFilters.addAll(
            listOf("armeabi-v7a", "arm64-v8a", "x86", "x86_64")
        )
    }
}

cargo {
    prebuiltToolchains = true
    module  = rustDir
    libname = "bigint_arithmetic"
    targets = listOf(
        "arm",
        "arm64",
        "x86",
        "x86_64",
    )
    targetIncludes = arrayOf("*.*")
    pythonCommand = "python3"
}


kmock {
    rootPackage = "io.cryptopunks.client.bignumber"
    freezeOnDefault = false
}

val cargoJvmAssemble by tasks.creating(Exec::class.java) {
    workingDir = File(rustDir)
    commandLine("cargo", "build", "--release")
}

val jvmRustLib = "$buildDir/generated/rust/"
val cargoJvmBuild by tasks.creating(Sync::class.java) {
    group = "Rust"
    dependsOn(cargoJvmAssemble)

    from("$rustDir/target/release")
    include("*.dylib","*.so","*.dll")
    into( "$buildDir/generated/rust/natives/osx_64")
}

tasks.withType(KotlinCompile::class.java) {
    dependsOn("cargoBuild", cargoJvmBuild)
}

tasks.named("jvmProcessResources") {
    dependsOn(cargoJvmBuild)
}

tasks.named("jvmTest", Test::class.java) {
    systemProperty("java.library.path", jvmRustLib)
}

tasks.named("clean") {
    doLast {
        File("$rustDir/target").deleteRecursively()
    }
}
