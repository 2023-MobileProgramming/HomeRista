plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
}

android {
    namespace = "com.coffee.homerista"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.coffee.homerista"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
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

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.6.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.1")
    implementation("androidx.preference:preference:1.2.0")

    //PreferenceFragmentCompat을 사용하려면 꼭 필요
    implementation("androidx.preference:preference-ktx:1.2.1")

    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.6.2")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")

    //데이터베이스 설정
    val room_version = "2.6.0"
    implementation("androidx.room:room-runtime:$room_version")
    annotationProcessor("androidx.room:room-compiler:$room_version") //여기까지 데이터베이스 필수 값.


    // 네비게이션을 사용하기 위해 꼭 필요
    implementation("androidx.navigation:navigation-ui-ktx:2.7.5")
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.5")

    ksp("androidx.room:room-compiler:$room_version")// To use Kotlin Symbol Processing (KSP)
    implementation("androidx.room:room-ktx:$room_version") // optional - Kotlin Extensions and Coroutines support for Room
    implementation("androidx.room:room-rxjava2:$room_version")// optional - RxJava2 support for Room
    implementation("androidx.room:room-rxjava3:$room_version")// optional - RxJava3 support for Room
    implementation("androidx.room:room-guava:$room_version")// optional - Guava support for Room, including Optional and ListenableFuture
    testImplementation("androidx.room:room-testing:$room_version")// optional - Test helpers
    implementation("androidx.room:room-paging:$room_version")// optional - Paging 3 Integration

    //viewModels() 이용하기 위한 라이브러리
    implementation ("androidx.activity:activity-ktx:1.8.1")
    implementation ("androidx.fragment:fragment-ktx:1.6.2")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}