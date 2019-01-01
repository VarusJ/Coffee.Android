package studio.xmatrix.coffee.ui.add;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.text.InputFilter;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.cunoraz.tagview.Tag;
import com.cunoraz.tagview.TagView;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.ArrayList;
import java.util.List;

import studio.xmatrix.coffee.R;
import studio.xmatrix.coffee.databinding.AddActivityBinding;

public class AddHandler {
    private AddActivity activity;
    private AddActivityBinding binding;
    private PictureAdapter picAdapter;
    private ArrayList<String> tagsList;

    public AddHandler(AddActivity activity, AddActivityBinding binding) {
        this.activity = activity;
        this.binding = binding;
        initView();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initView() {
        tagsList = new ArrayList<>();
        binding.tags.setOnTagDeleteListener((TagView tagView, Tag tag, int i) -> {
            binding.tags.remove(i);
            tagsList.remove(i);
        });

        GridLayoutManager gridLayoutManager = new GridLayoutManager(activity, 3);
        binding.pics.setLayoutManager(gridLayoutManager);
        picAdapter = new PictureAdapter(activity, binding.pics);
        binding.pics.setAdapter(picAdapter);

        binding.toggle.setOnCheckedChangeListener((CompoundButton view, boolean isChecked) -> {
            if(isChecked) binding.feed.setText(R.string.feed_public);
            else binding.feed.setText(R.string.feed_private);
        });

        binding.content.setOnTouchListener((View v, MotionEvent event) -> {
            if(v == binding.content) {
                binding.content.getParent().requestDisallowInterceptTouchEvent(true);
            } else {
                binding.content.getParent().requestDisallowInterceptTouchEvent(false);
            }
            return false;
        });
    }

    public void onClickAddImage(View view) {
        PictureSelector.create(activity)
                .openGallery(PictureMimeType.ofImage())
                .maxSelectNum(15)
                .theme(R.style.pickerStyle)
                .forResult(PictureConfig.CHOOSE_REQUEST);
    }

    public void onClickAddTag(View view) {
        MaterialEditText edit = new MaterialEditText(activity);
        edit.setMaxCharacters(20);
        edit.setFilters(new InputFilter[]{new InputFilter.LengthFilter(20)});
        FrameLayout container = new FrameLayout(activity);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(100, 50, 100, 30);
        container.addView(edit);
        edit.setLayoutParams(params);

        new AlertDialog.Builder(activity).setTitle("新标签")
                .setPositiveButton("确定", (DialogInterface dialog, int which) -> {
                    if(!addTag(edit.getText().toString()))
                        Toast.makeText(activity, "该标签已存在！", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("取消", null)
                .setView(container)
                .show();
    }

    public void addImage(List<LocalMedia> list) {
        picAdapter.addPics(list);
        binding.scroller.fullScroll(View.FOCUS_DOWN);
    }

    private boolean addTag(String tagname) {
        if(tagsList.indexOf(tagname) != -1) return false;

        tagsList.add(tagname);

        Tag tag = new Tag(tagname);
        tag.isDeletable = true;
        tag.layoutColor = activity.getColor(R.color.colorTag);
        tag.tagTextColor = activity.getColor(R.color.colorBlack);
        tag.deleteIndicatorColor = activity.getColor(R.color.colorBlack);
        tag.tagTextSize = 12f;
        tag.deleteIndicatorSize = 12f;
        binding.tags.addTag(tag);

        return true;
    }

    public void send() {

        String title = binding.title.getText().toString();
        String content = binding.content.getText().toString();
        boolean feedPublic = binding.toggle.isChecked();
        ArrayList<String> tags = tagsList;
        ArrayList<Bitmap> bitmaps = picAdapter.getBitmaps();

        if(title.isEmpty()) {
            Toast.makeText(activity, "标题不能为空！", Toast.LENGTH_SHORT).show();
            return;
        }

        // TODO
        Toast.makeText(activity, "发布新动态", Toast.LENGTH_SHORT).show();
        // api
    }
}