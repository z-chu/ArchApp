@file:Suppress("unused", "SpellCheckingInspection")

package plugin.vesions


object Depends {


    object AndroidX {
        const val appcompat = "androidx.appcompat:appcompat:${Versions.appcompat}"
        const val coreKtx = "androidx.core:core-ktx:${Versions.coreKtx}"
        const val material = "com.google.android.material:material:${Versions.material}"
        const val cardview = "androidx.cardview:cardview:${Versions.cardview}"
        const val recyclerview = "androidx.recyclerview:recyclerview:${Versions.recyclerview}"
        const val navigationCommon =
            "androidx.navigation:navigation-common:${Versions.navigation}"
        const val navigationRuntime =
            "androidx.navigation:navigation-runtime:${Versions.navigation}"
        const val navigationFragment =
            "androidx.navigation:navigation-fragment:${Versions.navigation}"
        const val navigationFragmentKtx =
            "androidx.navigation:navigation-fragment-ktx:${Versions.navigation}"
        const val navigationUI = "androidx.navigation:navigation-ui:${Versions.navigation}"
        const val navigationUIKtx = "androidx.navigation:navigation-ui-ktx:${Versions.navigation}"
        const val swiperefreshlayout =
            "androidx.swiperefreshlayout:swiperefreshlayout:${Versions.swiperefreshlayout}"
        const val constraintlayout =
            "androidx.constraintlayout:constraintlayout:${Versions.coordinatorlayout}"
        const val lifecycleExtensions =
            "androidx.lifecycle:lifecycle-extensions:${Versions.lifecycle}"
        const val lifecycleRuntime =
            "androidx.lifecycle:lifecycle-runtime:${Versions.lifecycle}"
        const val lifecycleRuntimeKtx =
            "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycle}"
        const val lifecycleReactivestreams =
            "androidx.lifecycle:lifecycle-reactivestreams:${Versions.lifecycle}"
        const val lifecycleReactivestreamsKtx =
            "androidx.lifecycle:lifecycle-reactivestreams-ktx:${Versions.lifecycle}"
        const val livedataKtx =
            "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifecycle}"
        const val viewmodelKtx =
            "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle}"
        const val workRuntime = "androidx.work:work-runtime:${Versions.work}"
        const val workTesting = "androidx.work:work-testing:${Versions.work}"
        const val pagingRuntime = "androidx.paging:paging-runtime:${Versions.paging}"
        const val appStartup = "androidx.startup:startup-runtime:${Versions.appStartup}"
        const val datastorePreferences =
            "androidx.datastore:datastore-preferences:${Versions.DataStore}"
        const val coordinatorlayout =
            "androidx.coordinatorlayout:coordinatorlayout:${Versions.DataStore}"

    }

    object Room {
        const val runtime = "androidx.room:room-runtime:${Versions.room}"
        const val compiler = "androidx.room:room-compiler:${Versions.room}"
        const val ktx = "androidx.room:room-ktx:${Versions.room}"
        const val testing = "androidx.room:room-testing:${Versions.room}"
    }

    object Fragment {
        const val runtime = "androidx.fragment:fragment:${Versions.fragment}"
        const val runtimeKtx = "androidx.fragment:fragment-ktx:${Versions.fragment}"
        const val testing = "androidx.fragment:fragment-testing:${Versions.fragment}"
    }

    object Kotlin {
        const val stdlibJdk = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlin}"
        const val test = "org.jetbrains.kotlin:kotlin-test-junit:${Versions.kotlin}"
        const val plugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
    }

    object Koin {
        const val core = "org.koin:koin-core:${Versions.koin}"
        const val android = "org.koin:koin-android:${Versions.koin}"
        const val viewmodel = "org.koin:koin-androidx-viewmodel:${Versions.koin}"
    }

    object Retrofit {
        const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
        const val gsonConverter = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"
        const val rxjava3Adapter = "com.squareup.retrofit2:adapter-rxjava3:${Versions.retrofit}"
        const val mock = "com.squareup.retrofit2:retrofit-mock:${Versions.retrofit}"
    }

    object OkHttp {
        const val okio = "com.squareup.okio:okio:${Versions.okio}"
        const val okhttp = "com.squareup.okhttp3:okhttp:${Versions.okhttp}"
        const val okhttpLogging = "com.squareup.okhttp3:logging-interceptor:${Versions.okhttp}"
    }

    object Rx {
        const val rxjava = "io.reactivex.rxjava3:rxjava:${Versions.rxjava}"
        const val rxkotlin = "io.reactivex.rxjava3:rxkotlin:${Versions.rxkotlin}"
        const val rxAndroid = "io.reactivex.rxjava3:rxandroid:${Versions.rxAndroid}"
        const val rxpermissions = "com.github.tbruyelle:rxpermissions:${Versions.rxpermissions}"
    }

    object Glide {
        const val glide = "com.github.bumptech.glide:glide:${Versions.glide}"
        const val glideCompiler = "com.github.bumptech.glide:compiler:${Versions.glide}"
        const val glideIntegration =
            "com.github.bumptech.glide:okhttp3-integration:${Versions.glide}"
    }

    const val junit = "junit:junit:${Versions.junit}"
    const val androidTestJunit = "androidx.test.ext:junit:${Versions.junitExt}"
    const val espressoCore = "androidx.test.espresso:espresso-core:${Versions.espressoCore}"
    const val timber = "com.jakewharton.timber:timber:${Versions.timber}"
    const val coilKt = "io.coil-kt:coil:${Versions.coilKt}"
    const val gson = "com.google.code.gson:gson:2.8.6"
    const val autoService =
        "com.google.auto.service:auto-service-annotations:${Versions.autoService}"
    const val autoServiceApt = "com.google.auto.service:auto-service:${Versions.autoService}"

}

object Versions {
    const val autoService = "1.0-rc6"
    const val retrofit = "2.9.0"
    const val okhttpLogging = "3.9.0"
    const val appcompat = "1.2.0"
    const val coreKtx = "1.3.2"
    const val material = "1.3.0-rc01"
    const val constraintlayout = "2.0.4"
    const val navigation = "2.3.2"
    const val paging = "3.0.0-alpha01"
    const val timber = "4.7.1"
    const val kotlin = "1.3.72"
    const val koin = "2.2.2"
    const val work = "2.2.0"
    const val lifecycle = "2.2.0"
    const val room = "2.3.0-alpha01"
    const val cardview = "1.0.0"
    const val recyclerview = "1.0.0"
    const val fragment = "1.2.5"
    const val swiperefreshlayout = "1.0.0"
    const val junit = "4.12"
    const val junitExt = "1.1.1"
    const val espressoCore = "3.2.0"
    const val coilKt = "0.11.0"
    const val appStartup = "1.0.0-alpha01"
    const val DataStore = "1.0.0-alpha01"
    const val gson = "2.8.6"
    const val okio = "2.7.0"
    const val okhttp = "4.8.1"
    const val rxjava = "3.0.4"
    const val rxkotlin = "3.0.0"
    const val rxAndroid = "3.0.0"
    const val glide = "4.11.0"
    const val rxpermissions = "0.12"
    const val coordinatorlayout = "1.1.0"

}




