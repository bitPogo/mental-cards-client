import android.databinding.tool.ext.capitalizeUS
import tech.antibytes.gradle.dependency.Dependency
import io.cryptopunks.gradle.client.dependency.Dependency as LocalDependency
import io.cryptopunks.gradle.client.dependency.Npm
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.gradle.api.tasks.testing.logging.TestLogEvent.FAILED
import tech.antibytes.gradle.configuration.runtime.AntiBytesTestConfigurationTask
import org.jetbrains.kotlin.gradle.dsl.KotlinCompile as TestCompile
import org.jetbrains.kotlin.gradle.tasks.Kotlin2JsCompile

plugins {
    id("org.jetbrains.kotlin.multiplatform")

    // Android
    id("com.android.library")

    id("tech.antibytes.gradle.configuration")
    id("tech.antibytes.gradle.coverage")
    id("tech.antibytes.kmock.kmock-gradle")

    // Serialization
    id("org.jetbrains.kotlin.plugin.serialization")
}

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
                optIn("kotlinx.serialization.InternalSerializationApi")
                optIn("kotlinx.serialization.ExperimentalSerializationApi")
                optIn("kotlin.ExperimentalUnsignedTypes")
                optIn("kotlin.RequiresOptIn")
            }
        }

        val commonMain by getting {
            dependencies {
                implementation(Dependency.multiplatform.kotlin.common)
                implementation(Dependency.multiplatform.uuid)

                implementation(project(":crypto"))
                implementation(project(":bigint"))
            }
        }
        val commonTest by getting {
            kotlin.srcDir("${buildDir.absolutePath.trimEnd('/')}/generated/antibytes/commonTest/kotlin")

            dependencies {
                implementation(Dependency.multiplatform.test.common)
                implementation(Dependency.multiplatform.test.annotations)

                implementation(LocalDependency.antibytes.test.kmp.core)
                implementation(LocalDependency.antibytes.test.kmp.annotations)
                implementation(LocalDependency.antibytes.test.kmp.fixture)
                implementation(LocalDependency.antibytes.test.kmp.kmock)


                implementation(Dependency.multiplatform.serialization.common)
                implementation(Dependency.multiplatform.serialization.json)
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
            dependsOn(commonTest)
            dependsOn(androidAndroidTestRelease)
            dependencies {
                implementation(Dependency.jvm.test.junit)
                implementation(Dependency.android.test.junit)
                implementation(Dependency.android.test.espressoCore)
                implementation(Dependency.android.test.uiAutomator)
            }
        }

        val jvmMain by getting {
            dependencies {
                implementation(Dependency.multiplatform.kotlin.jdk)
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

            }
        }

        val jsTest by getting {
            dependencies {
                implementation(Dependency.multiplatform.test.js)
            }
        }
    }
}

kmock {
    rootPackage = "io.cryptopunks.client.deck"
    freezeOnDefault = false
    allowInterfaces = true
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
}

tasks.withType(Test::class.java) {
    testLogging {
        events(FAILED)
    }
}

val generateTestConfig by tasks.creating(AntiBytesTestConfigurationTask::class.java) {
    packageName.set(
        "io.cryptopunks.client.deck"
    )

    val fixtures = File(
        projectDir.absolutePath.trimEnd(File.separatorChar) + File.separator + ".." + File.separator + "fixture"
    ).walkBottomUp().toList()

    val fields: MutableMap<String, String> = mutableMapOf()

    fixtures.forEach { file ->
        if (file.isFile) {
            val (idx, tempFieldName) = file.name.substringAfter("skat_")
                .substringBeforeLast(".json")
                .split('_', limit = 2)
            val fieldValue = file.readText()
            val (action, type) = if("_" in tempFieldName) {
                tempFieldName.split('_')
            } else {
                listOf(tempFieldName, "")
            }

            fields["$action${type.capitalizeUS()}$idx"] = "\"\"$fieldValue\"\""
        }
    }

    stringFields.set(fields)
}



tasks.withType(TestCompile::class.java) {
    if (this.name.contains("Test")) {
        this.dependsOn(generateTestConfig)
    }
}

tasks.withType(Kotlin2JsCompile::class.java) {
    if (this.name.contains("Test")) {
        this.dependsOn(generateTestConfig)
    }
}
