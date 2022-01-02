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
import com.sharkBytesLab.fastvideodownloader.databinding.FragmentImageBinding;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;


public class ImageFragment extends Fragment {

    private FragmentImageBinding binding;
    private ArrayList<whatsappStatusModel>list;
    private whatsappAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

  binding = DataBindingUtil.inflate(inflater, R.layout.fragment_image,container,false);

  list = new ArrayList<>();

  getData();
  binding.refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
      @Override
      public void onRefresh() {
          list = new ArrayList<>();
          getData();
          binding.refresh.setRefreshing(false);
      }
  });
  return binding.getRoot();

    }

    private void getData()
    {
        try {
            whatsappStatusModel model;

            String targetPath = Environment.getExternalStorageDirectory().getAbsolutePath().toString()+"/WhatsApp/Media/.statuses";
            File targetDirectory = new File(targetPath);
            File[] allFiles = targetDirectory.listFiles();

            String targetPathBusiness = Environment.getExternalStorageDirectory().getAbsolutePath().toString()+"/WhatsApp Business/Media/.statuses";
            File targetDirectoryBusiness = new File(targetPathBusiness);
            File[] allFilesBusiness = targetDirectoryBusiness.listFiles();

            Arrays.sort(allFiles, ((o1,o2) -> {
                if (o1.lastModified() > o2.lastModified()) return -1;
                else if (o1.lastModified() < o2.lastModified()) return +1;
                else return  0;
            }));

            for(int i =0;i<allFiles.length;i++)
            {
                File file = allFiles[i];
                if(Uri.fromFile(file).toString().endsWith(".png") || Uri.fromFile(file).toString().endsWith(".jpg"))
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
                if(Uri.fromFile(file).toString().endsWith(".png") || Uri.fromFile(file).toString().endsWith(".jpg"))
                {
                    model = new whatsappStatusModel("whatsBusiness"+i, Uri.fromFile(file), allFilesBusiness[i].getAbsolutePath(), file.getName());
                    list.add(model);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            Log.i("Image Error :",e.getMessage());
        }
        adapter = new whatsappAdapter(list, getActivity());
        binding.whatsappRecyclerView.setAdapter(adapter);
    }
}