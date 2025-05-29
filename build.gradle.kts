plugins {
  val kotlinVersion: String by System.getProperties()
  kotlin("plugin.serialization") version kotlinVersion
  kotlin("multiplatform") version kotlinVersion
  val kvisionVersion: String by System.getProperties()
  id("io.kvision") version kvisionVersion
  id("org.jetbrains.kotlinx.atomicfu") version "0.27.0"
}

version = "1.0.0-SNAPSHOT"
group = "com.dchaley"

repositories {
  mavenCentral()
  mavenLocal()
}

// Versions
val kvisionVersion: String by System.getProperties()

kotlin {
  js(IR) {
    browser {
      useEsModules()
      commonWebpackConfig {
        outputFileName = "main.bundle.js"
        sourceMaps = false
      }
      testTask {
        useKarma {
          useChromeHeadless()
        }
      }
    }
    binaries.executable()
    compilerOptions {
      target.set("es2015")
    }
  }
  sourceSets["jsMain"].dependencies {
    implementation("io.kvision:kvision:$kvisionVersion")
    implementation("io.kvision:kvision-bootstrap:$kvisionVersion")
    implementation("io.kvision:kvision-fontawesome:$kvisionVersion")
    implementation("io.kvision:kvision-state:$kvisionVersion")
    implementation("org.jetbrains.kotlin-wrappers:kotlin-js:1.0.0-pre.599")
    runtimeOnly("org.jetbrains.kotlin:kotlinx-atomicfu-runtime:2.1.20")
    implementation(npm("@fortawesome/fontawesome-svg-core", "6.5.1"))
    implementation(npm("@fortawesome/free-solid-svg-icons", "6.5.1"))
    implementation(npm("@fortawesome/free-regular-svg-icons", "6.5.1"))
    implementation(npm("@fortawesome/free-brands-svg-icons", "6.5.1"))
    implementation(npm("ynab", "2.2.0"))
  }
  sourceSets["jsTest"].dependencies {
    implementation(kotlin("test-js"))
    implementation("io.kvision:kvision-fontawesome:$kvisionVersion")
    implementation("io.kvision:kvision-testutils:$kvisionVersion")
    implementation("io.kvision:kvision-state:$kvisionVersion")
    implementation(npm("@fortawesome/fontawesome-svg-core", "6.5.1"))
    implementation(npm("@fortawesome/free-solid-svg-icons", "6.5.1"))
    implementation(npm("@fortawesome/free-regular-svg-icons", "6.5.1"))
    implementation(npm("@fortawesome/free-brands-svg-icons", "6.5.1"))
    implementation(npm("ynab", "2.2.0"))
  }
}
