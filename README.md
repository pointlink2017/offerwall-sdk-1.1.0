# offerwall-sdk-1.1.0
# POINTLINK OFFERWALL SDK Version 1.1.0
# Android SDK 포인트링크를 통한 오퍼월 연동을 위한 가이드 입니다.
# SDK는 OFFERWALL의 실행하기 위한 기본 프로세스만 가지고 있을 뿐입니다.
# POINTLINK OFFEWALL은 WEBVIEW로 구성되어 있으며, Publisher에서 자체적인 WEBVIEW로도 구성이 가능합니다. (별도 협의 필요)

# 1. Setting - Library 등록
# Project의 libs 폴더에 SDK를 복사해 넣어 주세요.

# 2. Setting - proguard 설정
# proguard를 통한 난독화 시 POINTLINK SDK는 이미 난독화 처리 되어 있으므로 제외 처리해 주세요.
-keep class kr.co.pointlink.sdk.** { *; }
-dontwarn kr.co.pointlink.sdk.**

# 3. Setting - Menifest 권한 설정하기
<uses-permission android:name="android.permission.INTERNET" />

# 4. POINTLINK에서 Publisher에게 제공되는 offercode를 등록해 주세요.  (Publish 구분 고유값)
# <application> ~ </application>사이에 넣어주세요.
<meta-data android:name="offercode" android:value="제공되는 offercode" />

# 5. Google Play Services 적용 
# 광고의 참여 시 광고주와 매칭을 위해 Google Advertising ID는 필수 항목입니다.
# <application> ~ </application>사이에 넣어주세요.
<meta-data android:name="com.google.android.gms.version" android:value="@integer/google_play_services_version" />

# 6. POINTLINK 오퍼월 페이지 
<activity    
android:name="kr.co.pointlink.offerwall" android:configChanges="orientation|keyboardHidden|screenSize" />

# Setting - build.gradle(:app) 설정
# Google Advertising ID 사용을 위해 Firebase를 적용해 주십시오.
# 설치형(CPI) 광고 자동 실적 체크를 위해 Install Referrer API를 적용해 주십시오.
dependencies {
    implementation fileTree(dir: 'libs', include: ['*.jar'])    
    implementation 'androidx.appcompat:appcompat:1.4.1' 
    implementation 'com.google.android.material:material:1.5.0'
    implementation 'androidx.constraintlayout:constraintlayout:2.1.3'
    implementation platform('com.google.firebase:firebase-bom:28.4.0')
    implementation 'com.google.firebase:firebase-core' 
    implementation 'com.android.installreferrer:installreferrer:2.2'
    implementation 'com.google.firebase:firebase-analytics'
}
apply plugin: 'com.google.gms.google-services'

# Setting - build.gradle(:Publisher의 packageName) 설정
buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath 'com.android.tools.build:gradle:7.0.4'
        classpath "com.google.gms:google-services:4.3.10"
    }
}

# 7. Offerwall호출
# userkey (Publisher가 User에게 Reward 지급을 위한 고유 식별 값) 설정
# - 중복참여를 제한함으로써 어뷰징을 방지
# - CS발생 시 대상자를 확인하고 광고 참여 여부를 판별하기 위한 연결 값
# - Postback을 통해서 해당 참여자에게 결과를 통보할 수 있게 됩니다.
# - 1명의 User는 1개의 고유한 값을 가져야 하며, 가변적인 값을 사용해서는 안됩니다.
# - 개인정보(E-mail, 이름, 전화번호, 식별 가능한 ID 등)이 포함되어서는 안됩니다.
# - 한글, 특수문자, 공백 등이 포함된 경우 반드시 Encode 처리를 하여 사용하여야 합니다.

import kr.co.pointlink.sdk.PLTools;

public class PageMain extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);       
        PLTools.userkey = "userkey값";
        PLTools.PLAdList(MainActivity.this, userkey);
    }
}
