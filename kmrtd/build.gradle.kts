plugins {
    id("java-library")
    id("org.jetbrains.kotlin.jvm")
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}


kotlin {
    jvmToolchain(17)
}

configurations.all {
    exclude(group = "org.bouncycastle", module = "bcprov-jdk15on")
    exclude(group = "org.bouncycastle", module = "bcpkix-jdk15on")
}

dependencies {
    api(libs.scuba.sc.android)
    api(libs.bcprov.jdk18on)
    api(libs.bcpkix.jdk18on)
    api(libs.cert.cvc)
    // Needed for jp2 face info
    api(libs.jp2.android)

    implementation(libs.kotlinx.benchmark.runtime)

    // Per i test
    testImplementation(libs.kotlin.test)
}