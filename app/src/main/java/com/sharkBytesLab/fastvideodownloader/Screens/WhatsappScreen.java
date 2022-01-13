package com.sharkBytesLab.fastvideodownloader.Screens;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayoutMediator;
import com.sharkBytesLab.fastvideodownloader.Fragments.ImageFragment;
import com.sharkBytesLab.fastvideodownloader.Fragments.VideoFragment;
import com.sharkBytesLab.fastvideodownloader.MainActivity;
import com.sharkBytesLab.fastvideodownloader.R;
import com.sharkBytesLab.fastvideodownloader.databinding.ActivityWhatsappScreenBinding;

import java.util.ArrayList;
import java.util.List;

public class WhatsappScreen extends AppCompatActivity {

    private ActivityWhatsappScreenBinding binding;
    private WhatsappScreen activity;
    private viewPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_whatsapp_screen);
        activity = this;

        initview();

        binding.whatsappBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        });

    }

    private void initview() {
        adapter = new viewPagerAdapter(activity.getSupportFragmentManager(), activity.getLifecycle());
        adapter.addFragment(new ImageFragment(), "Images");
        adapter.addFragment(new VideoFragment(), "Videos");
        binding.viewPager.setAdapter(adapter);
        binding.viewPager.setOffscreenPageLimit(1);

        new TabLayoutMediator(binding.tabLayout, binding.viewPager, (tab, position) -> {
            tab.setText(adapter.fragmentTitleList.get(position));

        }).attach();

        for(int i=0; i<binding.tabLayout.getTabCount(); i++)
        {
            TextView tv =(TextView) LayoutInflater.from(activity).inflate(R.layout.custom_tab, null);
            binding.tabLayout.getTabAt(i).setText(adapter.fragmentTitleList.get(i));
        }
    }

    class viewPagerAdapter extends FragmentStateAdapter{
        private final List<Fragment> fragmentList = new ArrayList<>();
        private final List<String> fragmentTitleList = new ArrayList<>();


        public viewPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
            super(fragmentManager, lifecycle);
        }

        public void addFragment(Fragment fragment, String title)
        {
            fragmentList.add(fragment);
            fragmentTitleList.add(title);


        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {

            return fragmentList.get(position);
        }

        @Override
        public int getItemCount() {
            return fragmentList.size();
        }
    }
}