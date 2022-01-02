package com.sharkBytesLab.fastvideodownloader.Adapter;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.sharkBytesLab.fastvideodownloader.Model.whatsappStatusModel;
import com.sharkBytesLab.fastvideodownloader.R;
import com.sharkBytesLab.fastvideodownloader.Util;
import com.sharkBytesLab.fastvideodownloader.databinding.WhatsappItemLayoutBinding;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.ArrayList;

public class whatsappAdapter extends RecyclerView.Adapter<whatsappAdapter.viewHolder> {

    private ArrayList<whatsappStatusModel> list;
    private Context context;
    private LayoutInflater inflater;
    private String saveFilePath = Util.RootDirectoryWhatsapp+"/";

    public whatsappAdapter(ArrayList<whatsappStatusModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(inflater==null)
        {
            inflater = LayoutInflater.from(parent.getContext());

        }

        return new viewHolder(DataBindingUtil.inflate(inflater, R.layout.whatsapp_item_layout, parent, false));
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        whatsappStatusModel model = list.get(position);
        if(model.getUri().toString().endsWith(".mp4"))
            holder.binding.playButton.setVisibility(View.VISIBLE);
        else
            holder.binding.playButton.setVisibility(View.GONE);

        Glide.with(context).load(model.getPath()).into(holder.binding.statusImage);

        holder.binding.downloadStory.setOnClickListener(v -> {
            Util.createFileFolder();
            final String path = model.getPath();
            final File file = new File(path);
            final File destinationFile = new File(saveFilePath);
            try {
                FileUtils.copyFileToDirectory(file, destinationFile);
            }catch(Exception e){

            }
            Toast.makeText(context, "Saved to  : "+saveFilePath, Toast.LENGTH_SHORT).show();
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        WhatsappItemLayoutBinding binding;

        public viewHolder(WhatsappItemLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
