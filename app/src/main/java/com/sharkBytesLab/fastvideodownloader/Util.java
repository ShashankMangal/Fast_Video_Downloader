package com.sharkBytesLab.fastvideodownloader;

import android.os.Environment;

import java.io.File;

public class Util {

    public static File RootDirectoryWhatsapp = new File(Environment.getExternalStorageDirectory()+"/download/Fast Video Downloader/Whatsapp Status");



    public static void createFileFolder(){
        if(!RootDirectoryWhatsapp.exists())
            RootDirectoryWhatsapp.mkdir();
    }
}
