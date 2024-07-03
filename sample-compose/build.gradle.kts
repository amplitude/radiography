import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  id("com.android.application")
  kotlin("android")
}

/** Use a separate property for the sample so we can test with different versions easily. */
val sampleComposeCompilerVersion = "1.5.11"

/**
 * Works = 1.5.3
 * Doesn't = 1.6.0, 1.6.3, 1.6.4
 */
val sampleComposeVersionLatest = "1.6.4" // 1.5.3, 1.6.4
android {
  compileSdk = 34

  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
  }

  defaultConfig {
    minSdk = 21
    targetSdk = 34
    applicationId = "com.squareup.radiography.sample.compose"
    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
  }

  buildFeatures {
    compose = true
  }

  composeOptions {
    kotlinCompilerExtensionVersion = sampleComposeCompilerVersion
  }

  packaging {
    resources.excludes += listOf(
      "META-INF/AL2.0",
      "META-INF/LGPL2.1"
    )
  }
    namespace = "com.squareup.radiography.sample.compose"
    testNamespace = "com.squareup.radiography.sample.compose.test"
}

tasks.withType<KotlinCompile> {
  kotlinOptions {
    jvmTarget = "1.8"
    freeCompilerArgs += listOf(
      "-Xopt-in=kotlin.RequiresOptIn"
    )
  }
}

dependencies {
  implementation(project(":radiography"))
  implementation(Dependencies.AppCompat)
  implementation(Dependencies.Compose("1.8.2").Activity())
  implementation(Dependencies.Compose(sampleComposeVersionLatest).Material)
  implementation(Dependencies.Compose(sampleComposeVersionLatest).Tooling)

  androidTestImplementation(Dependencies.Compose(sampleComposeVersionLatest).Testing)
  androidTestImplementation(Dependencies.InstrumentationTests.Rules)
  androidTestImplementation(Dependencies.InstrumentationTests.Runner)
}
