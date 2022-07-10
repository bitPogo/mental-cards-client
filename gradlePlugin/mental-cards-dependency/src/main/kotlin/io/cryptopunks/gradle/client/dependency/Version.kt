/*
 * Copyright (c) 2021 Matthias Geisler (bitPogo) / All rights reserved.
 *
 * Use of this source code is governed by LGPL v2.1
 */

package io.cryptopunks.gradle.client.dependency

object Version {

    const val kotlin = "1.6.21"

    val gradle = Gradle
    val antibytes = Antibytes

    object Gradle {
        /**
         * [AnitBytes GradlePlugins](https://github.com/bitPogo/gradle-plugins)
         */
        const val antibytes = "2dcd1e4"

        /**
         * [Spotless](https://plugins.gradle.org/plugin/com.diffplug.gradle.spotless)
         */
        const val spotless = "6.8.0"

        /**
         * [Mozilla Rust Gradle](https://github.com/mozilla/rust-android-gradle/releases)
         */
        const val rust = "0.9.3"
    }

    object Antibytes {
        const val test = "5b65d35-bump-updates-SNAPSHOT"
        const val kfixture = "0.2.0-bump-updates-SNAPSHOT"
        const val kmock = "0.3.0-rc02-bump-updates-SNAPSHOT"
    }

    /**
     * [SQLDelight](https://github.com/cashapp/sqldelight/)
     */
    const val sqldelight = "1.5.3"

    val google = Google

    object Google {
        /**
         * [Google Hilt](https://developer.android.com/training/dependency-injection/hilt-android)
         */
        const val hilt = "2.38.1"

        /**
         * [Google Hilt Compose](https://developer.android.com/jetpack/androidx/releases/hilt)
         */
        const val hiltCompose = "1.0.0"
    }

    /**
     * [ZXing](https://github.com/zxing/zxing)
     */
    const val zxing = "3.4.1"

    val androidx = AndroidX

    object AndroidX {
        /**
         * [Annotation](https://developer.android.com/jetpack/androidx/releases/annotation)
         */
        const val annotation = "1.4.0"
    }

    val npm = NPM

    object NPM {
        val dependency = Dependency

        object Dependency {
            /**
             * [Buffer](https://www.npmjs.com/package/buffer)
             */
            const val buffer = "^6.0.3"

            /**
             * [BigInteger](https://www.npmjs.com/package/big-integer)
             */
            const val bigInteger = "^1.6.51"

            /**
             * [Lucas Lehmer](https://www.npmjs.com/package/lucas-lehmer-test?activeTab=readme)
             */
            const val lucasLehmer = "^2.0.1"
        }
    }

    /**
     * [BigNumbers](https://github.com/ionspin/kotlin-multiplatform-bignum/releases)
     */
    const val bigNumbers = "0.3.6"

    /**
     * [native library loader](https://search.maven.org/artifact/org.scijava/native-lib-loader/2.4.0/jar)
     */
    const val jarNativeBundler = "2.4.0"
}
