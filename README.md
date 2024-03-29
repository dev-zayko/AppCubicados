# App Cubicados

_Aplicación para cubicar superficies en el area de la construcción_

### Pre-requisitos 📋

_Primero es Importar las siguientes dependencias al Gradle del proyecto_

```
plugins {
    id 'com.android.application'
}

android {
    packagingOptions {
        pickFirst 'androidsupportmultidexversion.txt'
        exclude 'META-INF/DEPENDENCIES'
        exclude 'META-INF/LICENSE'
        exclude 'META-INF/LICENSE.txt'
        exclude 'META-INF/license.txt'
        exclude 'META-INF/NOTICE'
        exclude 'META-INF/NOTICE.txt'
        exclude 'META-INF/notice.txt'
        exclude 'META-INF/ASL2.0'
        exclude("META-INF/*.kotlin_module")
    }
    compileSdkVersion 30
    buildToolsVersion "30.0.3"

    defaultConfig {
        applicationId "com.cubic.appcubicados"
        minSdkVersion 16
        targetSdkVersion 30
        multiDexEnabled true
        versionCode 1
        versionName "1.0"

        testInstrumentationRunner "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            minifyEnabled false
            proguardFiles getDefaultProguardFile('proguard-android-optimize.txt'), 'proguard-rules.pro'
        }
    }
    compileOptions {
        sourceCompatibility JavaVersion.VERSION_1_8
        targetCompatibility JavaVersion.VERSION_1_8
    }
}

dependencies {

    implementation 'androidx.appcompat:appcompat:1.3.0'
    implementation 'com.google.android.material:material:1.3.0'
    implementation 'com.github.bumptech.glide:glide:4.12.0'
    implementation 'org.jsoup:jsoup:1.13.1'
    implementation("com.squareup.okhttp3:okhttp:4.9.0")
    implementation(platform("com.squareup.okhttp3:okhttp-bom:4.9.0"))
    implementation("com.squareup.okhttp3:okhttp")
    testImplementation("com.squareup.okhttp3:mockwebserver:4.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor")
    implementation 'androidx.lifecycle:lifecycle-extensions:2.2.0'
    annotationProcessor 'com.github.bumptech.glide:compiler:4.12.0'
    implementation 'androidx.constraintlayout:constraintlayout:2.0.4'
    implementation 'com.squareup.retrofit2:retrofit:2.9.0'
    implementation 'com.google.android.material:material:1.4.0-rc01'
    implementation 'me.relex:circleindicator:2.1.6'
    implementation 'com.squareup.picasso:picasso:2.71828'
    implementation 'com.google.code.gson:gson:2.8.6'
    implementation 'com.squareup.retrofit2:converter-gson:2.9.0'
    implementation 'androidx.legacy:legacy-support-v4:1.0.0'
    testImplementation 'junit:junit:4.13.2'
    androidTestImplementation 'androidx.test.ext:junit:1.1.2'
    androidTestImplementation 'androidx.test.espresso:espresso-core:3.3.0'
    implementation 'com.srxlike.itextpdf:itextpdf:1.0.12.1'
    implementation 'com.itextpdf:itext7-core:7.1.8'
    implementation 'com.android.support:multidex:1.0.3'
}
```
_Segundo es configurar el AndroidManifest.xml

```
    <uses-permission android:name="android.permission.INTERNET" />
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
    <uses-permission android:name="android.permission.RECORD_AUDIO" />

    <uses-feature android:name="android.hardware.camera" />
    <uses-feature android:name="android.hardware.camera.autofocus" />

    <uses-permission android:name="com.google.android.providers.gsf.permission.READ_GSERVICES" />
    <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
    <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
    <uses-permission android:name="android.permission.CAMERA" />
```
