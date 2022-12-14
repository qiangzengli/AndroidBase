plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    // 将library 发布到maven仓库
    id("maven-publish")
}

android {
    compileSdk = 33
    defaultConfig {
        minSdk = 21
        targetSdk = 28
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
    viewBinding.isEnabled = true

}

dependencies {
    // Google依赖
    api("androidx.core:core-ktx:1.9.0")
    api("com.google.android.material:material:1.7.0")
    api("androidx.appcompat:appcompat:1.5.1")
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
    api("com.github.li-xiaojun:XPopup:2.8.2")
    // RecyclerView 快速开发Adapter
    api("com.github.CymChad:BaseRecyclerViewAdapterHelper:3.0.8")
    //glide 图片加载库
    api("com.github.bumptech.glide:glide:4.14.2")
    kapt("com.github.bumptech.glide:compiler:4.14.2")
    // 解决livedata数据倒灌问题
    api("com.kunminx.arch:unpeek-livedata:7.8.0")
}

sourceSets {
    create("main") {
        java.srcDir("src/main/java")
    }
}
// 打包源码
val sourcesJar by tasks.registering(Jar::class) {
    from(sourceSets["main"].allSource)
//    from(android.sourceSets["main"].java.srcDirs)
    archiveClassifier.set("sources")
    duplicatesStrategy = DuplicatesStrategy.INCLUDE

}

publishing {
    // 配置发布产物
    publications {
        create<MavenPublication>("maven") {
            artifact(sourcesJar)
            afterEvaluate { artifact(tasks.getByName("bundleReleaseAar")) }
            groupId = "com.cowain"
            artifactId = "base"
            version = "0.0.1"
            /**
             * 转换为如下格式
             * <dependencies>
             *   <dependency>
             *       <groupId></groupId>
             *       <artifactId></artifactId>
             *       <version></version>
             *   </dependency>
             * </dependencies>
             */
            pom.withXml {
                val nodes = asNode().appendNode("dependencies")
                configurations.api.get().allDependencies.forEach {
                    val node = nodes.appendNode("dependency")
                    node.appendNode("groupId", it.group)
                    node.appendNode("artifactId", it.name)
                    node.appendNode("version", it.version)
                }
            }

        }
    }
    // 配置maven仓库
    repositories {
        // 本项目repo地址
//        maven {
//            url = uri("$rootDir/repo")
//        }
        // 本地仓库地址
        mavenLocal()
        // 远程私有仓库
//        maven {
//            // 设置maven仓库地址
//            setUrl("http://192.168.188.26:9000/lizengqiang/AndroidBase")
//            //允许非https链接
//            isAllowInsecureProtocol = true
//            credentials {
//                username = "lizengqiang"
//                password = "12345678"
//            }
//
//        }
    }
}



