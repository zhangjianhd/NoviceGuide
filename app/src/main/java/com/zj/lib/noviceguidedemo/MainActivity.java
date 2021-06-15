package com.zj.lib.noviceguidedemo;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zj.lib.noviceguide.DecorateInflate;
import com.zj.lib.noviceguide.NoviceGuide;
import com.zj.lib.noviceguide.NoviceGuideSet;
import com.zj.lib.noviceguidedemo.databinding.ActivityMainBinding;
import com.zj.lib.noviceguidedemo.databinding.ItemRecBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            getWindow().setStatusBarColor(getColor(R.color.transparent));
        }
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        RecyclerView.Adapter adapter = new RecyclerView.Adapter<ItemViewHolder<ItemRecBinding>>() {

            @NonNull
            @Override
            public ItemViewHolder<ItemRecBinding> onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                ItemRecBinding itemRecBinding = DataBindingUtil.inflate(LayoutInflater.from(MainActivity.this), R.layout.item_rec, viewGroup, false);
                return new ItemViewHolder(itemRecBinding);
            }

            @Override
            public void onBindViewHolder(@NonNull ItemViewHolder<ItemRecBinding> itemViewHolder, int i) {
                if (i == 0) {
                    initGuide(itemViewHolder.getDataBinding());
                }
            }

            @Override
            public int getItemCount() {
                return 4;
            }
        };
        binding.rvCourse.setAdapter(adapter);
        binding.rvCourse.setLayoutManager(new LinearLayoutManager(this));

        binding.btnGuide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                new NoviceGuide.Builder(MainActivity.this)
                        .focusView(binding.btnGuide)
                        .setRadius(1000)    //显示出圆形
                        .setRelyActivity(MainActivity.this)
                        .setLayout(R.layout.layout_btn_guide, new DecorateInflate() {
                            @Override
                            public void onInflate(final NoviceGuide noviceGuide, View inflaterView) {
                                inflaterView.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        noviceGuide.dismiss();
                                    }
                                });
                            }
                        })
                        .build()
                        .show();
            }
        });
    }

    private void initGuide(ItemRecBinding binding) {
        NoviceGuide noviceGuide1 = new NoviceGuide.Builder(MainActivity.this)
                .focusView(binding.tvBtn1)
                .setPadding(5, 5, 5, 5)
                .setRadius(15)
                .setRelyActivity(MainActivity.this)
                .setLayout(R.layout.layout_guide, null)
                .setPassId(R.id.iv_know)
                .build();

        NoviceGuide noviceGuide2 = new NoviceGuide.Builder(MainActivity.this)
                .focusView(binding.tvBtn2)
                .setPadding(5, 5, 5, 5)
                .setRadius(15)
                .setRelyActivity(MainActivity.this)
                .setLayout(R.layout.layout_guide, null)
                .setPassId(R.id.iv_know)
                .build();

        NoviceGuide noviceGuide3 = new NoviceGuide.Builder(MainActivity.this)
                .focusView(binding.tvBtn3)
                .setPadding(5, 5, 5, 5)
                .setRadius(15)
                .setRelyActivity(MainActivity.this)
                .setLayout(R.layout.layout_guide, null)
                .setPassId(R.id.iv_know)
                .build();

        NoviceGuideSet noviceGuideSet = new NoviceGuideSet();
        noviceGuideSet.addGuide(noviceGuide1);
        noviceGuideSet.addGuide(noviceGuide2);
        noviceGuideSet.addGuide(noviceGuide3);
        noviceGuideSet.show();
    }

}
