package com.sharkBytesLab.fastvideodownloader;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.os.Build.VERSION.SDK;
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
import android.provider.Settings;
import android.view.View;
import android.widget.Toast;

import com.google.android.play.core.review.ReviewInfo;
import com.google.android.play.core.review.ReviewManager;
import com.google.android.play.core.review.ReviewManagerFactory;
import com.google.android.play.core.tasks.OnCompleteListener;
import com.google.android.play.core.tasks.OnSuccessListener;
import com.google.android.play.core.tasks.Task;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.sharkBytesLab.fastvideodownloader.Screens.FacebookScreen;
import com.sharkBytesLab.fastvideodownloader.Screens.ShareChatScreen;
import com.sharkBytesLab.fastvideodownloader.Screens.WhatsappScreen;
import com.sharkBytesLab.fastvideodownloader.databinding.ActivityMainBinding;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private ReviewInfo reviewInfo;
    private ReviewManager reviewManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        binding.whatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(getApplicationContext(), WhatsappScreen.class));
//                finish();
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
                activateReviewInfo();
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


        checkPermission();




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

    void activateReviewInfo()
    {
        reviewManager = ReviewManagerFactory.create(MainActivity.this);

        Task<ReviewInfo> task = reviewManager.requestReviewFlow();
        task.addOnCompleteListener(new OnCompleteListener<ReviewInfo>() {
            @Override
            public void onComplete(@NonNull Task<ReviewInfo> task) {
                if(task.isSuccessful())
                {
                    reviewInfo = task.getResult();

                    Task<Void> dialog = reviewManager.launchReviewFlow(MainActivity.this, reviewInfo);
                    dialog.addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void result) {
                            Toast.makeText(getApplicationContext(), "Thanks for Rating :)", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Error.", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

//    private void showPermissionDialog(){
//        if(SDK_INT >= Build.VERSION_CODES.R)
//        {
//
//            try {
//                Intent intent = new Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
//                intent.addCategory("android.intent.category.DEFAULT");
//                intent.setData(Uri.parse(String.format("package:%s", new Object[]{getApplicationContext().getPackageName()})));
//                startActivityForResult(intent, 100);
//            } catch (Exception e) {
//                Intent intent = new Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
//                startActivityForResult(intent, 100);
//            }
//        }else
//        {
//            ActivityCompat.requestPermissions(MainActivity.this, new String[]{WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE}, 99);
//        }
//    }

//    private boolean checkPermisions(){
//        if(SDK_INT >= Build.VERSION_CODES.R)
//        {
//            return Environment.isExternalStorageManager();
//        }else
//        {
//            int write = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);
//            int read = ContextCompat.checkSelfPermission(getApplicationContext(), READ_EXTERNAL_STORAGE);
//
//            return write == PackageManager.PERMISSION_GRANTED &&
//                    read == PackageManager.PERMISSION_GRANTED;
//        }
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//
//        if(requestCode == 99)
//        {
//            if(grantResults.length > 0)
//            {
//                boolean write = grantResults[0] == PackageManager.PERMISSION_GRANTED;
//                boolean read = grantResults[1] == PackageManager.PERMISSION_GRANTED;
//
//                if(read&&write)
//                {
//
//                }else
//                {
//
//                }
//            }
//        }
//
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if(requestCode == 100)
//        {
//            if(SDK_INT >= Build.VERSION_CODES.R)
//            {
//                if(Environment.isExternalStorageManager())
//                {
//
//                }else
//                {
//                    Toast.makeText(getApplicationContext(), "Permission is required!", Toast.LENGTH_SHORT).show();
//                }
//            }
//        }
//    }
}