package studio.xmatrix.coffee.ui.add;

import android.Manifest;
import android.annotation.SuppressLint;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
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
import com.luck.picture.lib.PictureSelectionModel;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.rengwuxian.materialedittext.MaterialEditText;
import studio.xmatrix.coffee.R;
import studio.xmatrix.coffee.data.common.network.Status;
import studio.xmatrix.coffee.data.model.Content;
import studio.xmatrix.coffee.data.service.resource.ContentResource;
import studio.xmatrix.coffee.databinding.AddActivityBinding;
import studio.xmatrix.coffee.inject.AppInjector;
import studio.xmatrix.coffee.inject.Injectable;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AddHandler implements Injectable {
    private AddActivity activity;
    private AddActivityBinding binding;
    private PictureAdapter picAdapter;
    private ArrayList<String> tagsList;
    private PreviewAllFragment previewAllFragment;
    private boolean isEdit = false;
    private String contentId;

    @Inject
    ViewModelProvider.Factory viewModelFactory;
    private AddViewModel viewModel;

    public AddHandler(AddActivity activity, AddActivityBinding binding) {
        this.activity = activity;
        this.binding = binding;
        AppInjector.Companion.inject(this);
        viewModel = ViewModelProviders.of(activity, viewModelFactory).get(AddViewModel.class);
        initAddView();

        Intent intent = activity.getIntent();
        if (intent != null) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
               String id = bundle.getString("id", "");
               if (!id.equals("")) {
                   isEdit = true;
                   contentId = id;
                   initEditView(id);
               }
            }
        }
    }

    private void initEditView(String id) {
        binding.addImage.setVisibility(View.GONE);
        viewModel.getDetail(id).observe(activity, res -> {
            if (res != null && res.getStatus() == Status.SUCCESS) {
                ContentResource data = res.getData();
                if (data != null && data.getState().equals("success") && data.getResource() != null) {
                    Content content = data.getResource();
                    binding.title.setText(content.getName());
                    binding.content.setText(content.getDetail());
                    tagsList.clear();
                    tagsList.addAll(content.getTags());
                } else {
                    Toast.makeText(activity, "网络错误", Toast.LENGTH_SHORT).show();
                    activity.finish();
                }
            }
        });
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initAddView() {
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
            if (isChecked) binding.feed.setText(R.string.feed_public);
            else binding.feed.setText(R.string.feed_private);
        });

        binding.content.setOnTouchListener((View v, MotionEvent event) -> {
            if (v == binding.content) {
                binding.content.getParent().requestDisallowInterceptTouchEvent(true);
            } else {
                binding.content.getParent().requestDisallowInterceptTouchEvent(false);
            }
            return false;
        });
    }

    public void onClickAddImage(View view) {
        if (!checkStoragePermission()) {
            Toast.makeText(activity, "请开启存储权限！", Toast.LENGTH_SHORT).show();
        } else {
            openPicker();
        }
    }

    public void onClickAddTag(View view) {
        MaterialEditText edit = new MaterialEditText(activity);
        edit.setMaxCharacters(15);
        edit.setFilters(new InputFilter[]{new InputFilter.LengthFilter(15)});
        edit.setTextColor(activity.getColor(R.color.colorBlack));
        FrameLayout container = new FrameLayout(activity);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(100, 50, 100, 30);
        container.addView(edit);
        edit.setLayoutParams(params);

        new AlertDialog.Builder(activity).setTitle("新标签")
                .setPositiveButton("确定", (DialogInterface dialog, int which) -> {
                    if (!addTag(edit.getText().toString()))
                        Toast.makeText(activity, "该标签已存在！", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("取消", null)
                .setView(container)
                .show();
    }

    void openPicker() {
        PictureSelectionModel pictureSelectionModel = PictureSelector.create(activity)
                .openGallery(PictureMimeType.ofImage())
                .maxSelectNum(15)
                .theme(R.style.pickerStyle);

        pictureSelectionModel.forResult(PictureConfig.CHOOSE_REQUEST);
    }

    void addImage(List<LocalMedia> list) {
        if (binding.pics.getVisibility() != View.VISIBLE)
            binding.pics.setVisibility(View.VISIBLE);
        picAdapter.addPics(list);
        binding.scroller.fullScroll(View.FOCUS_DOWN);
    }

    private boolean addTag(String tagName) {
        if (tagsList.indexOf(tagName) != -1) return false;

        tagsList.add(tagName);

        Tag tag = new Tag(tagName);
        tag.isDeletable = true;
        tag.layoutColor = activity.getColor(R.color.colorTag);
        tag.tagTextColor = activity.getColor(R.color.colorBlack);
        tag.deleteIndicatorColor = activity.getColor(R.color.colorBlack);
        tag.tagTextSize = 12f;
        tag.deleteIndicatorSize = 12f;
        binding.tags.addTag(tag);

        return true;
    }

    void send() {

        String title = Objects.requireNonNull(binding.title.getText()).toString();
        String content = binding.content.getText().toString();
        boolean feedPublic = binding.toggle.isChecked();
        ArrayList<String> tags = tagsList;
        ArrayList<Bitmap> bitmaps = picAdapter.getBitmaps();

        if (title.isEmpty()) {
            Toast.makeText(activity, "标题不能为空！", Toast.LENGTH_SHORT).show();
            return;
        }

        if (isEdit) {
            viewModel.update(contentId, title, content, tags, feedPublic).observe(activity, res -> {
                if (res != null) {
                    switch (res.getStatus()) {
                        case ERROR:
                            Toast.makeText(activity, "网络错误" + res.getMessage(), Toast.LENGTH_SHORT).show();
                            break;
                        case SUCCESS:
                            Toast.makeText(activity, "编辑成功", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent();
                            intent.putExtra("update", true);
                            activity.setResult(886 , intent);
                            activity.finish();
                            break;
                    }
                }
            });
            return;
        }

        if (bitmaps.size() == 0) {
            viewModel.addText(title, content, tags, feedPublic).observe(activity, res -> {
                if (res != null) {
                    switch (res.getStatus()) {
                        case ERROR:
                            Toast.makeText(activity, "网络错误" + res.getMessage(), Toast.LENGTH_SHORT).show();
                            break;
                        case SUCCESS:
                            Toast.makeText(activity, "发布成功", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent();
                            intent.putExtra("update", true);
                            activity.setResult(886 , intent);
                            activity.finish();
                            break;
                    }
                }
            });
        } else {
            viewModel.addAlbum(title, content, tags, feedPublic, bitmaps).observe(activity, res -> {
                if (res != null) {
                    switch (res.getStatus()) {
                        case ERROR:
                            Toast.makeText(activity, "网络错误" + res.getMessage(), Toast.LENGTH_SHORT).show();
                            break;
                        case SUCCESS:
                            Toast.makeText(activity, "发布成功", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent();
                            intent.putExtra("update", true);
                            activity.setResult(886 , intent);
                            activity.finish();
                            break;
                    }
                }
            });
        }

    }

    public void preview(int pos) {
        Objects.requireNonNull(activity.getSupportActionBar()).hide();
        activity.getWindow().setStatusBarColor(Color.BLACK);
        previewAllFragment = new PreviewAllFragment(activity, getOriginBitmaps(), pos);
        FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.preview, previewAllFragment, "previewAll");
        transaction.addToBackStack("previewAll");
        transaction.commit();
    }

    public ArrayList<Bitmap> getOriginBitmaps() {
        return picAdapter.getOriginBitmaps();
    }

    private boolean checkStoragePermission() {
        int permissionCheck = ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        return permissionCheck == PackageManager.PERMISSION_GRANTED;
    }
}
