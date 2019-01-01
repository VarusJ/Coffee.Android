package studio.xmatrix.coffee.ui.add;

import android.content.DialogInterface;
import android.graphics.Picture;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.text.InputFilter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.luck.picture.lib.PictureSelectionModel;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.mcxtzhang.layoutmanager.flow.FlowLayoutManager;

import java.util.List;

import studio.xmatrix.coffee.R;
import studio.xmatrix.coffee.databinding.AddActivityBinding;

public class AddHandler {
    private AddActivity activity;
    private AddActivityBinding binding;
    private TagAdapter tagAdapter;
    private PictureAdapter picAdapter;

    public AddHandler(AddActivity activity, AddActivityBinding binding) {
        this.activity = activity;
        this.binding = binding;
        initView();
    }

    private void initView() {
        binding.toggle.setOnCheckedChangeListener((CompoundButton view, boolean isChecked) -> {
            if(isChecked) binding.feed.setText(R.string.feed_public);
            else binding.feed.setText(R.string.feed_private);
        });

        binding.tags.setLayoutManager(new FlowLayoutManager());
        tagAdapter = new TagAdapter(activity);
        binding.tags.setAdapter(tagAdapter);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(activity, 3);
        binding.pics.setLayoutManager(gridLayoutManager);
        picAdapter = new PictureAdapter(activity, binding.pics);
        binding.pics.setAdapter(picAdapter);

    }

    public void onClickAddImage(View view) {
        PictureSelector.create(activity)
                .openGallery(PictureMimeType.ofImage())
                .maxSelectNum(12)
                .theme(R.style.pickerStyle)
                .forResult(PictureConfig.CHOOSE_REQUEST);
    }

    public void addImage(List<LocalMedia> list) {
        picAdapter.addPics(list);
    }

    public void onClickAddTag(View view) {
        EditText edit = new EditText(activity);
        edit.setFilters(new InputFilter[]{new InputFilter.LengthFilter(20)});
        FrameLayout container = new FrameLayout(activity);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(50, 50, 50, 50);
        container.addView(edit);
        edit.setLayoutParams(params);

        new AlertDialog.Builder(activity).setTitle("新标签")
                .setPositiveButton("确定", (DialogInterface dialog, int which) -> {
                    if(!tagAdapter.addTag(edit.getText().toString())) {
                        Toast.makeText(activity, "该标签已存在！", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("取消", null)
                .setView(container)
                .show();
    }
}