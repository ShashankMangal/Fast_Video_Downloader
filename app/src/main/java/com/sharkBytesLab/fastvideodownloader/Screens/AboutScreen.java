package com.sharkBytesLab.fastvideodownloader.Screens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.sharkBytesLab.fastvideodownloader.BuildConfig;
import com.sharkBytesLab.fastvideodownloader.MainActivity;
import com.sharkBytesLab.fastvideodownloader.R;
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



    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(AboutScreen.this, MainActivity.class));
        finish();
    }
}