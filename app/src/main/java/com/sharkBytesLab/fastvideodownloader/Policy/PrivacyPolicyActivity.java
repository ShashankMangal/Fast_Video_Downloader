package com.sharkBytesLab.fastvideodownloader.Policy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.sharkBytesLab.fastvideodownloader.Screens.AboutScreen;
import com.sharkBytesLab.fastvideodownloader.databinding.ActivityPrivacyPolicyBinding;

public class PrivacyPolicyActivity extends AppCompatActivity {

    private ActivityPrivacyPolicyBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityPrivacyPolicyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        try {

            binding.policyWebView.getSettings().setJavaScriptEnabled(true);
            binding.policyWebView.loadUrl("file:///android_asset/policy.html");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), AboutScreen.class));
        finish();
    }
}