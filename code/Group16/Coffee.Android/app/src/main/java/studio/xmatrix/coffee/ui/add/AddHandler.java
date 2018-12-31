package studio.xmatrix.coffee.ui.add;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.text.InputFilter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.mcxtzhang.layoutmanager.flow.FlowLayoutManager;
import com.suke.widget.SwitchButton;

import studio.xmatrix.coffee.R;
import studio.xmatrix.coffee.databinding.AddActivityBinding;

public class AddHandler {
    private AddActivity activity;
    private AddActivityBinding binding;
    private TagAdapter tagAdapter;

    public AddHandler(AddActivity activity, AddActivityBinding binding) {
        this.activity = activity;
        this.binding = binding;
        initView();
    }

    private void initView() {
        binding.tags.setLayoutManager(new FlowLayoutManager());
        tagAdapter = new TagAdapter(activity);
        binding.tags.setAdapter(tagAdapter);
        /*
        binding.toggle.setOnCheckedChangeListener((SwitchButton view, boolean isChecked) -> {
            if(isChecked) binding.feed.setText(R.string.feed_public);
            else binding.feed.setText(R.string.feed_private);
        });
        */
    }

    public void onClickAddImage(View view) {
        Toast.makeText(activity, "添加图片", Toast.LENGTH_SHORT).show();
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