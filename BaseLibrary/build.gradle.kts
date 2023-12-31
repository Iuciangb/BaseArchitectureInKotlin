plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.yy.baselibrary"
    compileSdk = 33

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        debug {
            resValue("string", "empty", "0000000")
        }
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")

            resValue("string", "empty", "")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("com.google.code.gson:gson:2.3.1")

    //kotlin
    implementation("androidx.preference:preference-ktx:1.2.0")
    implementation("androidx.core:core-ktx:1.7.0")
    implementation("androidx.navigation:navigation-ui-ktx:2.3.5")
    implementation("androidx.navigation:navigation-fragment-ktx:2.3.5")
    implementation("androidx.activity:activity-ktx:1.5.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.5.0")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.5.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.5.0")

    //retrofit
    implementation("com.squareup.retrofit2:retrofit:2.6.1")
    implementation("com.squareup.okhttp3:logging-interceptor:4.2.0")
    implementation("com.squareup.retrofit2:converter-gson:2.6.1")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}