plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
}

android {
    compileSdk = 31

    defaultConfig {
        minSdk = 21
        targetSdk = 28
//        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
//        consumerProguardFiles = "consumer-rules.pro"
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
    ndkDirectory
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    api("androidx.core:core-ktx:1.9.0")
    api("androidx.appcompat:appcompat:1.5.0")
    api("com.google.android.material:material:1.7.0")

    // Google依赖
    api("androidx.appcompat:appcompat:1.5.0")
    api("androidx.activity:activity-ktx:1.6.0")
    api("com.google.android.material:material:1.7.0")
    api("androidx.databinding:viewbinding:7.3.1")
    api("androidx.core:core-ktx:1.9.0")
    api("androidx.recyclerview:recyclerview:1.2.1")
    api("androidx.constraintlayout:constraintlayout:2.1.4")
    api("com.google.code.gson:gson:2.10")
    api("androidx.datastore:datastore-preferences:1.0.0")
//    api  "androidx.datastore:datastore-core:1.0.0"
//    api  "com.google.protobuf:protobuf-javalite:3.18.0"

    // Jetbrains依赖
    api("org.jetbrains.kotlin:kotlin-reflect:1.7.22")
//    api "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.1"

    // 三方依赖
    api("com.blankj:utilcodex:1.31.1")
    api("com.squareup.retrofit2:retrofit:2.9.0")
    api("com.squareup.okhttp3:logging-interceptor:4.10.0")
    api("com.squareup.retrofit2:converter-gson:2.9.0")
    api("com.github.liujingxing.rxhttp:rxhttp:3.0.0")
    kapt("com.github.liujingxing.rxhttp:rxhttp-compiler:3.0.0")
    //生成RxHttp类
    api("com.github.li-xiaojun:XPopup:2.8.2")
    // RecyclerView 快速开发Adapter
    api("com.github.CymChad:BaseRecyclerViewAdapterHelper:3.0.8")
    //glide 图片加载库
    api("com.github.bumptech.glide:glide:4.14.2")
    kapt("com.github.bumptech.glide:compiler:4.14.2")
//    api "com.github.getActivity:ShapeView:8.0"
//    api "io.github.florent37:shapeofview:1.4.7"
}