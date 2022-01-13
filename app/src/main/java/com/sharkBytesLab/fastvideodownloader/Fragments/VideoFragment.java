package com.sharkBytesLab.fastvideodownloader.Fragments;

import android.net.Uri;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sharkBytesLab.fastvideodownloader.Adapter.whatsappAdapter;
import com.sharkBytesLab.fastvideodownloader.Model.whatsappStatusModel;
import com.sharkBytesLab.fastvideodownloader.R;
import com.sharkBytesLab.fastvideodownloader.databinding.FragmentVideoBinding;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;


public class VideoFragment extends Fragment {

    private FragmentVideoBinding binding;
    private ArrayList<whatsappStatusModel> list;
    private whatsappAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_video,container,false);

        list = new ArrayList<>();

        getData();
        binding.refreshVideos.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                list = new ArrayList<>();
                getData();
                binding.refreshVideos.setRefreshing(false);
            }
        });
        return binding.getRoot();

    }
    private void getData()
    {
        try {
            whatsappStatusModel model;

            String targetPath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/WhatsApp/Media/.Statuses";
            File targetDirectory = new File(targetPath);
            File[] allFiles = targetDirectory.listFiles();

            String targetPathBusiness = Environment.getExternalStorageDirectory().getAbsolutePath()+"/WhatsApp Business/Media/.Statuses";
            File targetDirectoryBusiness = new File(targetPathBusiness);
            File[] allFilesBusiness = targetDirectoryBusiness.listFiles();

            Arrays.sort(allFiles, ((o1, o2) -> {
                if (o1.lastModified() > o2.lastModified()) return -1;
                else if (o1.lastModified() < o2.lastModified()) return +1;
                else return  0;
            }));

            for(int i =0;i<allFiles.length;i++)
            {
                File file = allFiles[i];
                if(Uri.fromFile(file).toString().endsWith(".mp4"))
                {
                    model = new whatsappStatusModel("whats"+i, Uri.fromFile(file), allFiles[i].getAbsolutePath(), file.getName());
                    list.add(model);
                }
            }
            Arrays.sort(allFilesBusiness, ((o1, o2) -> {
            if (o1.lastModified() > o2.lastModified()) return -1;
            else if (o1.lastModified() < o2.lastModified()) return +1;
            else return  0;
        }));

            for(int i =0;i<allFilesBusiness.length;i++)
            {
                File file = allFilesBusiness[i];
                if(Uri.fromFile(file).toString().endsWith(".mp4"))
                {
                    model = new whatsappStatusModel("whatsBusiness"+i, Uri.fromFile(file), allFilesBusiness[i].getAbsolutePath(), file.getName());
                    list.add(model);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            Log.i("Video Error :",e.getMessage());
        }
        adapter = new whatsappAdapter(list, getActivity());
        binding.whatsappRecyclerViewVideo.setAdapter(adapter);
    }
}