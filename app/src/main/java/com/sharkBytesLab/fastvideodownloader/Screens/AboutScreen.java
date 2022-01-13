package com.sharkBytesLab.fastvideodownloader.Screens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.sharkBytesLab.fastvideodownloader.BuildConfig;
import com.sharkBytesLab.fastvideodownloader.MainActivity;
import com.sharkBytesLab.fastvideodownloader.Policy.PrivacyPolicyActivity;
import com.sharkBytesLab.fastvideodownloader.Policy.TermsActivity;
import com.sharkBytesLab.fastvideodownloader.databinding.ActivityAboutScreenBinding;

public class AboutScreen extends AppCompatActivity {

    private ActivityAboutScreenBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityAboutScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



        binding.backToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AboutScreen.this, MainActivity.class));
                finish();
            }
        });

        binding.version.setText("Version : "+String.valueOf(BuildConfig.VERSION_NAME));

        binding.policy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AboutScreen.this, PrivacyPolicyActivity.class));
                finish();
            }
        });

        binding.terms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AboutScreen.this, TermsActivity.class));
                finish();
            }
        });



    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(AboutScreen.this, MainActivity.class));
        finish();
    }
}