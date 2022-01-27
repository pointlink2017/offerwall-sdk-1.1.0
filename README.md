# offerwall-sdk-1.1.0
<li>POINTLINK OFFERWALL SDK Version 1.1.0</li>
<li>Android SDK 포인트링크를 통한 오퍼월 연동을 위한 가이드 입니다.</li>
<li>SDK는 OFFERWALL의 실행하기 위한 기본 프로세스만 가지고 있습니다.</li>
<li>SDK는 aar파일로 구성되어 있습니다.</li>


# 1. Library 등록
<li>Project의 libs 폴더에 SDK(kr.co.pointlink.sdk-ver1.1.0.aar) 등록.</li>
<pre><a href="https://github.com/pointlink2017/offerwall-sdk-1.1.0/tree/main/kr.co.pointlink.sample/app/libs">POINTLINK OFFEWALL SDK 1.1.0 다운로드 받기</a></pre>

# 2. proguard 설정
<li>proguard를 통한 난독화 시 POINTLINK SDK는 이미 난독화 처리 되어 있으므로 제외 처리.</li>
<pre>-keep class kr.co.pointlink.sdk.** { *; }
-dontwarn kr.co.pointlink.sdk.**</pre>

# 3. Menifest 설정
<li>권한 설정하기</li>
<li>인터넷 사용은 필수 사항입니다.</li>
<pre><span><</span>uses-permission 
    android:name="android.permission.INTERNET" /></pre>

<li>Google Play Services 적용</li> 
<li>광고의 참여 시 광고주와 매칭을 위해 Google Advertising ID는 필수 항목입니다.</li>
<li>Google Advertising ID 사용을 위해 Firebase의 google-services.json를 적용해 주십시오.</li>
<li><span><</span>application> ~ <span><</span>/application>사이에 넣어주세요.</li>
<pre><span><</span>meta-data 
    android:name="com.google.android.gms.version" 
    android:value="@integer/google_play_services_version" /></pre>

<li>POINTLINK 오퍼월 페이지 적용</li>
<pre><span><</span>activity android:name="kr.co.pointlink.sdk.PLOfferwall"
    android:configChanges="orientation|keyboardHidden|screenSize"
    tools:replace="android:configChanges"
    android:exported="true" /></pre>

# 4. build.gradle(:app) 설정
<pre>plugins {
    id 'com.android.application'
}

android {
..
}

dependencies {
    implementation fileTree(dir: 'libs', include: ['*.aar'])
    implementation 'androidx.appcompat:appcompat:1.4.1'
    implementation 'com.google.android.material:material:1.5.0'
    implementation 'androidx.constraintlayout:constraintlayout:2.1.3'
    testImplementation 'junit:junit:4.13.2'
    androidTestImplementation 'androidx.test.ext:junit:1.1.3'
    androidTestImplementation 'androidx.test.espresso:espresso-core:3.4.0'
    implementation 'androidx.multidex:multidex:2.0.1'
    implementation 'androidx.core:core:1.7.0'

    implementation platform('com.google.firebase:firebase-bom:29.0.3')
    implementation 'com.google.firebase:firebase-core'
    implementation 'com.google.firebase:firebase-analytics'
}

apply plugin: 'com.google.gms.google-services'</pre>


# 5. build.gradle(:Publisher의 packageName) 설정
<li>각 APP의 상태에 맞춰 지정해 주세요.</li>
<pre>// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath 'com.android.tools.build:gradle:7.1.0'
        classpath 'com.google.gms:google-services:4.3.10'

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

task clean(type: Delete) {
    delete rootProject.buildDir
}</pre>


# 6. Offerwall호출
<li>userkey (Publisher가 User에게 Reward 지급을 위한 고유 식별 값) 설정</li>
<li>중복참여를 제한함으로써 어뷰징을 방지.</li>
<li>CS발생 시 대상자를 확인하고 광고 참여 여부를 판별하기 위한 연결 값.</li>
<li>Postback을 통해서 해당 참여자에게 결과를 통보할 수 있게 됩니다.</li>
<li>1명의 User는 1개의 고유한 값을 가져야 하며, 가변적인 값을 사용해서는 안됩니다.</li>
<li>개인정보(E-mail, 이름, 전화번호, 식별 가능한 ID 등)이 포함되어서는 안됩니다.</li>
<li>한글, 특수문자, 공백 등이 포함된 경우 반드시 Encode 처리를 하여 사용하여야 합니다.</li>
<pre>public class MainActivity extends AppCompatActivity {
    private String userKey;
    private String adCode;
    private String puCode;
    private String screenMode;

    private String paddingTOP;
    private String paddingLEFT;
    private String paddingRIGHT;
    private String paddingBOTTOM;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); // FULLSCREEN
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND, WindowManager.LayoutParams.FLAG_BLUR_BEHIND); // BLUR BEHIND
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT)); // TRANSPARENT BACKGROUND WHITE

        userKey = "honggildong"; // 필수 값
        puCode = "10002"; // 고정 필수 값
        adCode = ""; 
        screenMode = ""; // full or null
        paddingTOP = "100";
        paddingLEFT = "0";
        paddingRIGHT = "0";
        paddingBOTTOM = "0";

        // SDK + CALL
        Button OfferBtn = findViewById(R.id.OfferBtn);
        OfferBtn.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, kr.co.pointlink.sdk.PLOfferwall.class);
            intent.putExtra("adCode", adCode);
            intent.putExtra("userKey", userKey);
            intent.putExtra("puCode", puCode);
            intent.putExtra("screenMode", screenMode);
            intent.putExtra("paddingTOP", paddingTOP);
            intent.putExtra("paddingLEFT", paddingLEFT);
            intent.putExtra("paddingRIGHT", paddingRIGHT);
            intent.putExtra("paddingBOTTOM", paddingBOTTOM);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        });
    }
}</pre>


# 7. 포인트링크 연동
<li>SDK 연동 전 포인트링크와 Server to Server 연동 작업이 필요 합니다.</li>
<li>Publisher 등록</li>
<pre>사전 협의를 통해서 진행됩니다.
포인트링크 연락처(pointlink@kakao.com)를 통해 사전 협의해 주세요.
Publisher 등록을 위해 필요사항을 전달하고 puCode를 받습니다.
1) 회사명 + 사업자번호 + 사업자등로증사본 + 통장사본
2) 담당자명 + 담당자 Email주소 + Publisher명</pre>

<li>Reward Postback 연동</li>
<pre>Postback은 User가 광고 참여 완료 즉시 curl을 통해서 Server to Server로 전송됩니다.
(일부 광고에 따라서는 실시간이 아닐 수 있습니다.)
<table>
<tr><td colspan="2"><strong>구분</strong></td><td><strong>설명</strong></td></tr>
<tr><td colspan="2">전송받을 Host</td><td>전송할 Host 주소 (ex, pointlink.co.kr)</td></tr>
<tr><td colspan="2">전송받을 Path</td><td>전송할 Path 주소 (ex, /postback)</td></tr>
<tr><td colspan="2">Port</td><td>전송할 Port 주소 (ex, 80)</td></tr>
<tr><td colspan="2">전송방식</td><td>POST (되도록 POST 전송이나 GET 방식도 가능)</td></tr>
<tr><td>Parameter1</td><td>adCode</td><td>Offerwall 참여한 광고의 고유 값</td></tr>
<tr><td>Parameter2</td><td>puPrice</td><td>Offerwall 참여한 광고를 통해 Publisher가 받을 금액(vat 별도)</td></tr>
<tr><td>Parameter3</td><td>userPoint</td><td>Publisher가 User에게 제공하는 포인트
(연동 진행 시 User에게 지급되는 비율이 적용되어 비율에 따라 제공됩니다.)</td></tr>
<tr><td>Parameter4</td><td>userkey</td><td>Publisher의 User 정보 (8번에서 설정한 값)</td></tr>
<tr><td>Parameter5</td><td>adTitle</td><td>참여한 광고의 제목 (base64_encode)</td></tr>
<tr><td>Parameter5</td><td>기타</td><td>협의하여 추가할 수 있음.</td></tr>
</table>
- puPrice는 Publisher에서 받을 금액(원, vat별도) 
- userPoint는 User에게 전달되는 포인트
userPoint는 puPrice에서 Publisher의 수익을 제외한 비율에서 Publisher의 포인트 비율에 맞춰 표기 됨.
ex) 1원 = 10포인트, puPirce의 90%를 User에게 지급 시 
puPrice = 100이라면, userPoint = 100 x 90% x 10포인트 = 900</pre>

<li>CPI Call 연동</li>
<pre>CPI(=Cost Per Install)광고는 
User가 APP설치 광고를 참여하여 APP을 설치 했을때 Publisher에서 체크하여 Reward Postback을 요청하게 됩니다.
</pre>


# 8. 미리보기
<li>Screen Shot - default</li>
<table><tr><td><img src="https://github.com/pointlink2017/offerwall-sdk-1.1.0/blob/main/kr.co.pointlink.sample/screen/screen5_padding0.jpg?raw=true" width="280"></td></tr></table>

<li>Screen Shot - full</li>
<table><tr><td><img src="https://github.com/pointlink2017/offerwall-sdk-1.1.0/blob/main/kr.co.pointlink.sample/screen/screen6_full.jpg?raw=true" width="280"></td></tr></table>

<li>Screen Shot - PaddingTop 100</li>
<table><tr><td><img src="https://github.com/pointlink2017/offerwall-sdk-1.1.0/blob/main/kr.co.pointlink.sample/screen/screen2_paddingtop100.jpg?raw=true" width="280"></td></tr></table>

<li>Screen Shot - PaddingTop 100 + puCode Error</li>
<table><tr><td><img src="https://github.com/pointlink2017/offerwall-sdk-1.1.0/blob/main/kr.co.pointlink.sample/screen/screen4.jpg?raw=true" width="280"></td></tr></table>
