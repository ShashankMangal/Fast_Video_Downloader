package com.sharkBytesLab.fastvideodownloader;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.os.Build.VERSION.SDK_INT;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.Settings;
import android.view.View;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.sharkBytesLab.fastvideodownloader.Screens.AboutScreen;
import com.sharkBytesLab.fastvideodownloader.Screens.FacebookScreen;
import com.sharkBytesLab.fastvideodownloader.Screens.ShareChatScreen;
import com.sharkBytesLab.fastvideodownloader.Screens.WhatsappScreen;
import com.sharkBytesLab.fastvideodownloader.Screens.YoutubeScreen;
import com.sharkBytesLab.fastvideodownloader.databinding.ActivityMainBinding;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private boolean isPressed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());

        binding.whatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(getApplicationContext(), WhatsappScreen.class));
//                finish();
               Toast.makeText(getApplicationContext(), "This feature is Under Maintenance.", Toast.LENGTH_SHORT).show();
            }
        });

        binding.instagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(getApplicationContext(), "This feature is Under Maintenance.", Toast.LENGTH_SHORT).show();
            }
        });

        binding.facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), FacebookScreen.class));
                finish();
            }
        });

        binding.youtube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(getApplicationContext(), YoutubeScreen.class));
//                finish();
                Toast.makeText(getApplicationContext(), "This feature is Under Maintenance.", Toast.LENGTH_SHORT).show();
            }
        });

        binding.sharechat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ShareChatScreen.class));
                finish();
            }
        });

        binding.rateApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(getApplicationContext(), "This feature is Under Maintenance.", Toast.LENGTH_SHORT).show();

            }
        });

        binding.shareApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("text/plain");
                    String shareMsg = "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID + "\n\n";
                    intent.putExtra(Intent.EXTRA_SUBJECT, "Fast Video Downloader");
                    intent.putExtra(Intent.EXTRA_TEXT, shareMsg);
                    startActivity(Intent.createChooser(intent, "Share Via"));
                }catch(Exception e)
                {
                    Toast.makeText(getApplicationContext(), "Error Occurred :"+e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.aboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AboutScreen.class));
                finish();
            }
        });


        checkPermission();




    }

    @Override
    public void onBackPressed() {

        if(isPressed)
        {
            finishAffinity();
            System.exit(0);
        }
        else
        {
            Toast.makeText(getApplicationContext(), "Press back again to Exit.", Toast.LENGTH_SHORT).show();
            isPressed = true;
        }
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                isPressed = false;
            }
        };
        new Handler().postDelayed(runnable,2000);

    }

    private void checkPermission(){
        Dexter.withContext(this)
                .withPermissions(
                        READ_EXTERNAL_STORAGE,
                        WRITE_EXTERNAL_STORAGE
                ).withListener(new MultiplePermissionsListener() {
            @Override public void onPermissionsChecked(MultiplePermissionsReport report) {
                if(!report.areAllPermissionsGranted())
                    checkPermission();
            }
            @Override public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {/* ... */}
        }).check();
    }



}