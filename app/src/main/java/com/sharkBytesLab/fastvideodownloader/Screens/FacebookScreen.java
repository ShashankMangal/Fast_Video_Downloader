package com.sharkBytesLab.fastvideodownloader.Screens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.sharkBytesLab.fastvideodownloader.MainActivity;
import com.sharkBytesLab.fastvideodownloader.R;
import com.sharkBytesLab.fastvideodownloader.Util;
import com.sharkBytesLab.fastvideodownloader.databinding.ActivityFacebookScreenBinding;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

public class FacebookScreen extends AppCompatActivity {

    private ActivityFacebookScreenBinding binding;
    private FacebookScreen activity;
    private ClipboardManager clipboardManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFacebookScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        activity = this;
        binding.facebookBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        });

        binding.facebookDownloadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getFacebookdata();
            }

        });

        binding.facebookPasteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                if(!clipboardManager.hasPrimaryClip())
                {
                    binding.facebookPasteBtn.setEnabled(false);
                }
                else if(clipboardManager.hasPrimaryClip())
                {
                    ClipData clipData = clipboardManager.getPrimaryClip();
                    ClipData.Item item = clipData.getItemAt(0);

                    binding.facebookUrl.setText(item.getText().toString());

                }

            }
        });
    }


    private void getFacebookdata()
    {

        URL url;

        try {
            url = new URL(binding.facebookUrl.getText().toString());
            String host = url.getHost();

            if(host.contains("fb.watch"))
            {
                new callGetFbData().execute(binding.facebookUrl.getText().toString());
            }else
            {
                Toast.makeText(getApplicationContext(), "URL is invalid!", Toast.LENGTH_SHORT).show();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }

    class callGetFbData extends AsyncTask<String, Void, Document>
    {
        Document fbDoc;


        @Override
        protected Document doInBackground(String... strings) {

            try {
                fbDoc = Jsoup.connect(strings[0]).get();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return fbDoc;
        }

        @Override
        protected void onPostExecute(Document document) {
            String videoUrl = document.select("meta[property=\"og:video\"]")
                    .last().attr("content");

            if(!videoUrl.equals(""))
                Util.download(videoUrl, Util.RootDirectoryFacebook, activity, "facebook "+ System.currentTimeMillis()+".mp4");
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(FacebookScreen.this, MainActivity.class));
    }
}