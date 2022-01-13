package com.sharkBytesLab.fastvideodownloader.Screens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;

import com.sharkBytesLab.fastvideodownloader.MainActivity;
import com.sharkBytesLab.fastvideodownloader.Util;
import com.sharkBytesLab.fastvideodownloader.databinding.ActivityShareChatScreenBinding;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.URL;

public class ShareChatScreen extends AppCompatActivity {

    private ActivityShareChatScreenBinding binding;
    private ClipboardManager clipboardManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityShareChatScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.sharechatDownloadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getShareChatData();
            }
        });

        binding.sharechatBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        });

        binding.sharechatPasteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                if(!clipboardManager.hasPrimaryClip())
                {
                    binding.sharechatPasteBtn.setEnabled(false);
                }
                else if(clipboardManager.hasPrimaryClip())
                {
                    ClipData clipData = clipboardManager.getPrimaryClip();
                    ClipData.Item item = clipData.getItemAt(0);

                    binding.sharechatUrl.setText(item.getText().toString());

                }

            }
        });
    }

    private void getShareChatData()
    {
        URL url;
        try {
            url = new URL(binding.sharechatUrl.getText().toString());
            String host = url.getHost();

            if(host.contains("sharechat"))
                new callGetShareChatData().execute(binding.sharechatUrl.getText().toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    class callGetShareChatData extends AsyncTask<String, Void, Document>
    {
        Document scDocument;

        @Override
        protected Document doInBackground(String... strings) {
            try {
                scDocument = Jsoup.connect(strings[0]).get();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return scDocument;
        }

        @Override
        protected void onPostExecute(Document document) {
            String videoUrl = document.select("meta[property=\"og:video:secure_url\"]")
                    .last().attr("content");

            if(!videoUrl.equals(""))
                Util.download(videoUrl, Util.RootDirectoryShareChat, ShareChatScreen.this, "ShareChat "+System.currentTimeMillis()+".mp4");
        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(ShareChatScreen.this, MainActivity.class));
    }
}