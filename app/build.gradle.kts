plugins {
    id(BuildPlugins.androidApplication)
    id(BuildPlugins.kotlinAndroid)
    id(BuildPlugins.kotlinKapt)
    id(BuildPlugins.androidHilt)
}


android {
    compileSdkVersion (AndroidSdk.compile)
    defaultConfig {
        applicationId = AndroidClient.appId
        minSdkVersion (AndroidSdk.min)
        targetSdkVersion (AndroidSdk.target)
        versionCode = AndroidClient.versionCode
        versionName  = AndroidClient.versionName
        testInstrumentationRunner = AndroidClient.testRunner
    }

    buildFeatures {
        dataBinding = true
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled  = false
            proguardFiles (getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }

    }
    compileOptions {
        sourceCompatibility  =  JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}
dependencies {

    //Compile time dependencies
    kapt(Libraries.lifecycleCompiler)
    kapt(Libraries.hiltCompiler)

    // Application dependencies
    implementation(Libraries.kotlinStdLib)
    implementation(Libraries.kotlinCoroutines)
    implementation(Libraries.kotlinCoroutinesAndroid)
    implementation(Libraries.appCompat)
    implementation(Libraries.ktxCore)
    implementation(Libraries.constraintLayout)
    implementation(Libraries.viewModel)
    implementation(Libraries.liveData)
    implementation(Libraries.lifecycleExtensions)
    implementation(Libraries.cardView)
    implementation(Libraries.recyclerView)
    implementation(Libraries.material)
    implementation(Libraries.androidAnnotations)
    implementation(Libraries.glide)
    implementation(Libraries.hilt)
    implementation(Libraries.retrofit)
    implementation(Libraries.okHttpLoggingInterceptor)
    implementation(Libraries.pagingLibrary)



    //TODO: change this
    implementation ("androidx.fragment:fragment-ktx:1.2.5")


}