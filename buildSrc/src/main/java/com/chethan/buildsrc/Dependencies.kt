package com.chethan.buildsrc


object Dependencies {


    object Apps {
        const val compileSdk = 30
        const val minSdk = 23
        const val targetSdk = 30
        const val versionCode = 1
        const val versionName = "1.0.0"
    }

    const val androidGradlePlugin = "com.android.tools.build:gradle:4.1.3"

    object AndroidX {
        private const val appcompactVersion = "1.3.0-alpha02"
        private const val coreKtxVersion = "1.5.0-alpha04"

        private const val nav_version = "2.3.1"

        const val appcompat = "androidx.appcompat:appcompat:$appcompactVersion"
        const val palette = "androidx.palette:palette:1.0.0"

        const val core = "androidx.core:core:$coreKtxVersion"
        const val coreKtx = "androidx.core:core-ktx:$coreKtxVersion"

        const val material = "com.google.android.material:material:1.2.1"
        const val flexbox = "com.google.android:flexbox:1.1.0"
        const val constraintlayout = "androidx.constraintlayout:constraintlayout:2.0.3"
        const val recyclerview = "androidx.recyclerview:recyclerview:1.1.0"
        const val cardView = "androidx.cardview:cardview:1.0.0"
        const val multidex = "androidx.multidex:multidex:2.0.0"

        object Navigation {
            const val navigationFragmentKTX =
                "androidx.navigation:navigation-fragment-ktx:$nav_version"
            const val navigationUIKTX = "androidx.navigation:navigation-ui-ktx:$nav_version"
            const val navigationDynamicKTX =
                "androidx.navigation:navigation-dynamic-features-fragment:$nav_version"
            const val safeArg =
                "androidx.navigation:navigation-safe-args-gradle-plugin:$nav_version"
        }

        object Compose {
            const val snapshot = ""
            const val version = "1.0.0-alpha10"
            const val composePagingVersion = "1.0.0-alpha05"
            const val foundation = "androidx.compose.foundation:foundation:$version"
            const val layout = "androidx.compose.foundation:foundation-layout:$version"
            const val material = "androidx.compose.material:material:$version"
            const val materialIconsExtended =
                "androidx.compose.material:material-icons-extended:$version"
            const val runtime = "androidx.compose.runtime:runtime:$version"
            const val runtimeLivedata = "androidx.compose.runtime:runtime-livedata:$version"
            const val tooling = "androidx.compose.ui:ui-tooling:$version"
            const val test = "androidx.compose.ui:ui-test:$version"
            const val uiTest = "androidx.compose.ui:ui-test-junit4:$version"
            const val uiUtil = "androidx.compose.ui:ui-util:${version}"
            const val viewBinding = "androidx.compose.ui:ui-viewbinding:$version"
            const val pagingCompose = "androidx.paging:paging-compose:$composePagingVersion"
        }

        object Test {
            private const val version = "1.2.0"
            private const val mockitoVersion = "2.18.3"
            private const val versionKtxExt = "1.1.2-rc01"
            private const val kotlinxCoroutinesAndroid = "1.3.7"

            // Arch Core testing
            private const val archCoreTestVersion = "2.1.0"
            private const val runnerVersion = "1.1.0"

            const val core = "androidx.test:core:$version"
            const val rules = "androidx.test:rules:$version"
            const val junit = "androidx.test.ext:junit-ktx:${versionKtxExt}"
            const val mock = "org.mockito:mockito-core:$mockitoVersion"
            const val coroutineTest =
                "org.jetbrains.kotlinx:kotlinx-coroutines-test:$kotlinxCoroutinesAndroid"
            const val archCoreTest = "androidx.arch.core:core-testing:$archCoreTestVersion"


            const val runner = "androidx.test:runner:$runnerVersion"

            //UI Test dependencies

            private const val versionsEspresso = "3.1.0-alpha4"
            const val espressoCore = "androidx.test.espresso:espresso-core:$versionsEspresso"
            const val espressoContrib = "androidx.test.espresso:espresso-contrib:$versionsEspresso"
            const val espressoIntents = "androidx.test.espresso:espresso-intents:$versionsEspresso"

        }


        object TestImplementation {

            private const val versions_atsl_core = "1.2.0"
            private const val versions_atsl_junit = "1.1.1"
            private const val versions_atsl_runner = "1.2.0"
            private const val versions_atsl_rules = "1.2.0"

            const val atsl_core = "androidx.test:core:$versions_atsl_core"
            const val atsl_ext_junit = "androidx.test.ext:junit:$versions_atsl_junit"
            const val atsl_runner = "androidx.test:runner:$versions_atsl_runner"
            const val atsl_rules = "androidx.test:rules:$versions_atsl_rules"


            private const val versions_espresso = "3.2.0"
            const val espresso_core = "androidx.test.espresso:espresso-core:$versions_espresso"
            const val espresso_contrib =
                "androidx.test.espresso:espresso-contrib:$versions_espresso"
            const val espresso_intents =
                "androidx.test.espresso:espresso-intents:$versions_espresso"


            private const val versions_mockito = "2.25.0"
            private const val versions_mockito_all = "1.10.19"
            private const val versions_mockito_android = "2.25.0"
            const val mockito_core = "org.mockito:mockito-core:$versions_mockito"
            const val mockito_all = "org.mockito:mockito-all:$versions_mockito_all"
            const val mockito_android = "org.mockito:mockito-android:$versions_mockito_android"


            private const val versions_junit = "4.12"
            const val junit = "junit:junit:$versions_junit"


            private const val versions_arch_core = "2.1.0"
            const val arch_core_runtime = "androidx.arch.core:core-runtime:$versions_arch_core"
            const val arch_core_testing = "androidx.arch.core:core-testing:$versions_arch_core"
        }

        object Room {
            private const val version = "2.2.6"
            const val runtime = "androidx.room:room-runtime:$version"
            const val ktx = "androidx.room:room-ktx:$version"
            const val compiler = "androidx.room:room-compiler:$version"
        }

        object Lifecycle {
            private const val version = "2.2.0"
            const val extensions = "androidx.lifecycle:lifecycle-extensions:$version"
            const val livedata = "androidx.lifecycle:lifecycle-livedata-ktx:$version"
            const val viewmodel = "androidx.lifecycle:lifecycle-viewmodel-ktx:$version"
        }

        object Fragment {
            private const val version = "1.3.0-beta01"
            const val fragment_ktx = "androidx.fragment:fragment-ktx:$version"
        }

        object Work {
            private const val version = "2.0.1"
            const val runtime = "androidx.work:work-runtime:$version"
            const val testing = "androidx.work:work-com.example.tipjar.testing:$version"
            const val ktx = "androidx.work:work-runtime-ktx:$version"
        }
    }


    object Kotlin {
        private const val version = "1.4.21"
        const val stdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:$version"
        const val gradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$version"
        const val extensions = "org.jetbrains.kotlin:kotlin-android-extensions:$version"
        const val allopen = "org.jetbrains.kotlin:kotlin-allopen:$version"
    }

    object Coroutines {
        private const val version = "1.4.3"
        const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$version"
        const val android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$version"

        const val test = "org.jetbrains.kotlinx:kotlinx-coroutines-test:$version"

    }

    object Retrofit {
        private const val version = "2.9.0"
        const val retrofit = "com.squareup.retrofit2:retrofit:$version"
        const val gson = "com.squareup.retrofit2:converter-gson:$version"
        const val mock = "com.squareup.retrofit2:retrofit-mock:$version"
    }

    object OkHttp {
        private const val version = "4.7.2"
        const val okhttp = "com.squareup.okhttp3:okhttp:$version"
        const val logging = "com.squareup.okhttp3:logging-interceptor:$version"
    }


    object HILT {
        private const val hiltVersion = "2.35"
        private const val hiltViewModel = "1.0.0-alpha02"

        const val android = "com.google.dagger:hilt-android:$hiltVersion"
        const val compiler = "com.google.dagger:hilt-android-compiler:$hiltVersion"
        const val gradle_plugin = "com.google.dagger:hilt-android-gradle-plugin:$hiltVersion"

    }


    object LINT {
        private const val version = "0.39.0"
        const val ktlint = "com.pinterest:ktlint:$version"
    }

    object LOG {
        private const val version = "4.7.1"
        const val timber = "com.jakewharton.timber:timber:$version"

        const val chunk = "com.github.ChuckerTeam.Chucker:library:3.4.0"
        const val chunkNoOp = "com.github.ChuckerTeam.Chucker:library-no-op:3.4.0"
    }


    object Glide {
        private const val version = "4.11.0"
        const val runtime = "com.github.bumptech.glide:glide:$version"
        const val compiler = "com.github.bumptech.glide:compiler:$version"
        const val okhttp3 = "com.github.bumptech.glide:okhttp3-integration:$version"
    }


}