package io.cryptopunks.gradle.client.dependency

object Npm {
    val dependency = Dependency

    object Dependency {
        val buffer = "buffer" to Version.npm.dependency.buffer

        val bigInteger = "big-integer" to Version.npm.dependency.bigInteger

        val lucasLehmer = "lucas-lehmer-test" to Version.npm.dependency.lucasLehmer
    }
}
