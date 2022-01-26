package kr.co.pointlink.sample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

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
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT)); // BACKGROUND

        userKey = "honggildong";
        puCode = "00000";
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
}
