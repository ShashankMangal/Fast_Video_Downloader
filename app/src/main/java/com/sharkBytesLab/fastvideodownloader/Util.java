package com.sharkBytesLab.fastvideodownloader;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.widget.Toast;

import java.io.File;

public class Util {

    public static File RootDirectoryWhatsapp = new File(Environment.getExternalStorageDirectory()+"/Download/Fast Video Downloader/Whatsapp Status");
    public static String RootDirectoryFacebook = "/Fast Video Downloader/Facebook";
    public static String RootDirectoryShareChat = "/Fast Video Downloader/ShareChat";



    public static void createFileFolder(){
        if(!RootDirectoryWhatsapp.exists())
            RootDirectoryWhatsapp.mkdir();
    }

    public static void download(String downloadPath, String destinationPath, Context context, String fileName)
    {
        Toast.makeText(context, "Downloading...", Toast.LENGTH_SHORT).show();
        Uri uri = Uri.parse(downloadPath);

        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setTitle(fileName);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, destinationPath+fileName);
        ((DownloadManager)context.getSystemService(Context.DOWNLOAD_SERVICE)).enqueue(request);
    }


}
