import io.cryptopunks.gradle.client.dependency.addCustomRepositories
import tech.antibytes.gradle.dependency.Version
import io.cryptopunks.gradle.client.dependency.ensureKotlinVersion

plugins {
    id("io.cryptopunks.gradle.client.dependency")

    id("tech.antibytes.gradle.dependency")

    id("io.cryptopunks.gradle.client.script.quality-spotless")
}

allprojects {
    repositories {
        addCustomRepositories()
        mavenCentral()
        google()
        jcenter()
    }

    ensureKotlinVersion(Version.kotlin.language)
}

tasks.named<Wrapper>("wrapper") {
    gradleVersion = "7.5-rc4"
    distributionType = Wrapper.DistributionType.ALL
}
